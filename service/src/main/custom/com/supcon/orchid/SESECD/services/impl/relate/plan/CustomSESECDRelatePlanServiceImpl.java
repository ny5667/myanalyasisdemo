package com.supcon.orchid.SESECD.services.impl.relate.plan;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.vo.relate.plan.EnableChangStateVO;
import com.supcon.orchid.SESECD.daos.SESECDAllEmerMemberDao;
import com.supcon.orchid.SESECD.daos.SESECDCctvRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.model.vo.TypeAndPlanIdsVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.AlarmActionVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.AllEmerMemberVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.DetparentmentVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.SESWssEPRelatedAlarmVO;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanService;
import com.supcon.orchid.SESECD.services.common.file.CustomSESECDFileServerDocumentService;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmePeopleGroup;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyAccid;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.SESWssER.entities.SESWssERAccidentClass;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class CustomSESECDRelatePlanServiceImpl extends BaseServiceImpl implements CustomSESECDRelatePlanService {


    /**
     * 应急管理-应急预案 预案等级系统编码ENTITY_CODE
     */
    private static final String ENTITY_CODE = "SESWssEP_rank";

    @Autowired
    private SESECDAlmAlarmRecordDao alarmRecordDao;

    @Autowired
    private SqlUtils sqlUtils;

    @Autowired
    private CustomSESECDFileServerDocumentService fileServerDocumentService;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private SystemCodeService systemCodeService;

    @Autowired
    private SESECDEmePlanObjDao planObjDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomSESECDAlarmActionService alarmActionService;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private SESECDCctvRecordDao sesecdCctvRecordDao;

    @Autowired
    private SESECDAllEmerMemberDao sesecdAllEmerMemberDao;

    /**
     * 应急预案 list 接口
     * @param  eventId 事件id
     * @return
     */
    @Override
    public List<SESWssEPRelatedAlarmVO> listEmergencyPlan(Long eventId) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //查询事件所关联的预案
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESECDEmePlanObj.JPA_NAME).append(" where valid = 1 and ALARM_ID = ").append(eventId).append(sqlUtils.getSqlPartByCID());
        List<SESECDEmePlanObj> emePlanObjs = alarmRecordDao.findByHql(hql.toString());
        List<SESWssEPEmergencyPlan> emergencyPlans = new ArrayList<>();
        if (CollectionUtils.isEmpty(emePlanObjs)) {
            log.error("事件所关联的预案为空");
        } else {
            log.error("事件所关联的预案数量:{}", emePlanObjs.size());
            emergencyPlans = emePlanObjs.stream().map(obj -> obj.getPlanObj()).collect(Collectors.toList());
        }

        //查询事件所关联的指令
        hql.delete(0,hql.length());
        hql.append(" from ").append(SESECDAlarmAction.JPA_NAME).append(" where valid = 1 and ALARM_ID = ").append(eventId).append(sqlUtils.getSqlPartByCID());
        List<SESECDAlarmAction> alarmActions = alarmRecordDao.findByHql(hql.toString());

        //建立预案id - 事件指令映射
        Map<Long ,List<AlarmActionVO>> alarmActionMapping = new HashMap<>();
        for (SESECDAlarmAction alarmAction : alarmActions) {
            if(null == alarmActionMapping.get(alarmAction.getInstructions().getEmergencyPlanId().getId())){
                List<AlarmActionVO> list = new ArrayList<>();
                AlarmActionVO alarmActionVO = AlarmActionVO.AlarmActionVOPO2VO(alarmAction);
                list.add(alarmActionVO);
                alarmActionMapping.put(alarmAction.getInstructions().getEmergencyPlanId().getId(),list);
            }else {
                alarmActionMapping.get(alarmAction.getInstructions().getEmergencyPlanId().getId()).add(AlarmActionVO.AlarmActionVOPO2VO(alarmAction));
            }
        }
        log.error("预案id - 事件指令映射:{}",alarmActionMapping);
        //建立预案id - 预案指令映射


        List<SESWssEPRelatedAlarmVO> relatedAlarmVOS = emePlanObjs.stream().map((SESECDEmePlanObj obj) -> SESWssEPRelatedAlarmVO.SESWssEPRelatedAlarmPO2VO(obj.getPlanObj(), alarmActionMapping.get(obj.getPlanObj().getId()))).collect(Collectors.toList());
        SESECDAlmAlarmRecord alarmRecord = alarmRecordDao.get(eventId);
        //alarmRecord
        //推荐值排序 1事故类型 2报警等级<=>预案等级 3区域匹配（暂无）
        for (SESWssEPRelatedAlarmVO relatedAlarmVO : relatedAlarmVOS) {
            //当前事件事故类型
            hql.setLength(0);
            //SESECDAlarmEnenetrel
            hql.append(" from ").append(SESECDAlarmEnenetrel.JPA_NAME).append(" where  valid = 1 and ALARM_ID =").append(eventId).append(sqlUtils.getSqlPartByCID());
            List<SESECDAlarmEnenetrel> accidentTypeList = alarmRecordDao.findByHql(hql.toString());
            Set<SESWssERAccidentClass> eventAcc = accidentTypeList.stream().map(SESECDAlarmEnenetrel::getEnenetrel).collect(Collectors.toSet());
            //当前预案事故类型
            String sql = " from " + SESWssEPEmergencyAccid.JPA_NAME + " where valid = 1 and   PLAN_ID = " + relatedAlarmVO.getId();
            List<SESWssEPEmergencyAccid> accidList = alarmRecordDao.findByHql(sql);
            Set<SESWssERAccidentClass> planAcc = accidList.stream().map(SESWssEPEmergencyAccid::getAccidentClass).collect(Collectors.toSet());
            //判断事故类型是否匹配
            if (!CollectionUtils.isEmpty(org.apache.commons.collections4.CollectionUtils.intersection(eventAcc, planAcc))) {
                relatedAlarmVO.setScore(relatedAlarmVO.getScore() + 1);
            }

            //报警等级
            SystemCode alarmLevel = alarmRecord.getAlarmLevel();
            if (null != relatedAlarmVO.getPlanRank() && null != alarmLevel && alarmLevel.getCode().equals(relatedAlarmVO.getPlanRank().getCode())){
                relatedAlarmVO.setScore(relatedAlarmVO.getScore() + 1);
            }
        }
        //根据匹配值排序
        List<SESWssEPRelatedAlarmVO> collect = relatedAlarmVOS.stream().sorted(Comparator.comparing(SESWssEPRelatedAlarmVO::getScore).reversed()).collect(Collectors.toList());
        stopWatch.stop();
        log.error("listEmergencyPlan=====预案列表接口执行时时间：" + stopWatch.getTotalTimeSeconds() + "秒");
        return collect;
    }

    @Override
    public List<SESWssEPRelatedAlarmVO> listEmergencyPlanByAccident() {
        // 先查询表 SES_EME_ACCCLASSS  它记录了 事故类型和预案的关联关系 查出预案id,然后再查预案表
        StringBuilder planAccHQL = new StringBuilder();
        String safeProductClass = " ( 100,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121 ) ";//这几个id是生产安全类型的预案，而且id不会变
        planAccHQL.append(" from ").append(SESWssEPEmergencyAccid.JPA_NAME).append(" where valid = 1 and ACCIDENT_CLASS in ").append(safeProductClass).append(sqlUtils.getSqlPartByCID());
        List<SESWssEPEmergencyAccid>  planAccIdList = alarmRecordDao.findByHql(planAccHQL.toString());
        if (CollectionUtils.isEmpty(planAccIdList)) {
            log.error("事故类型预案关联表未查询到生产安全类型的预案");
            return new ArrayList<>();
        }
        log.error("共查询到{}个关联预案", planAccIdList.size());
        List<SESWssEPEmergencyPlan> emergencyPlanList  = new ArrayList<>();
        List<Long> planIdList = new ArrayList<>();//用于去重,因为事故类型是多选
        for (SESWssEPEmergencyAccid planAccident: planAccIdList) {
            if (null != planAccident.getPlanId() && planAccident.getPlanId().isValid() ) { //有预案,而且该预案未删除的
                if (!planIdList.contains(planAccident.getPlanId().getId())) {// 如果不包含,才加入
                    emergencyPlanList.add(planAccident.getPlanId());
                    planIdList.add(planAccident.getPlanId().getId());
                }
            }
        }

        log.error("共查询到{}个预案", emergencyPlanList.size());
        if (CollectionUtils.isEmpty(emergencyPlanList)) {
            return new ArrayList<>();
        }

        //查询预案所关联的指令
        //先查这个表把预案关联的通讯组指令id找出来(1对1)，再根据通讯组id把指令找出来(一对多)
        StringBuilder planCommunicatGroupHQL = new StringBuilder();
        String inPlanIdStr = planIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
        log.error("in sql planids:{}", inPlanIdStr);
        planCommunicatGroupHQL.append(" from ").append(SESWssEPEmePeopleGroup.JPA_NAME).append(" where valid = 1 and EMERGENCY_PLAN_ID in ( ").append(inPlanIdStr).append(" )").append(sqlUtils.getSqlPartByCID());
        List<SESWssEPEmePeopleGroup>  planCommunicatGroupPOs  = alarmRecordDao.findByHql(planCommunicatGroupHQL.toString());
        if (CollectionUtils.isEmpty(planCommunicatGroupPOs)) {
            return new ArrayList<>();
        }
        log.error("查询到{}个通讯组", planCommunicatGroupPOs.size());

        Map<Long, Long> planCommuGroupMapping = new HashMap<>(); //预案和通讯组的映射

        List<Long> sectionIds =  new ArrayList<>();
        planCommunicatGroupPOs.stream().forEach( e -> {
            planCommuGroupMapping.put(e.getEmergencyPlanId().getId(), e.getSectionId().getId());
            if (!sectionIds.contains(e.getSectionId().getId())) {
                sectionIds.add(e.getSectionId().getId());
            }
        });

        //查询通讯组下指令
        String sectionIdStr = sectionIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        log.error("sectionIdStr:{}" , sectionIdStr);
        StringBuilder communicatIntroHQL = new StringBuilder();//SES_EME_PLANACTIONS
        communicatIntroHQL.append(" from ").append(SESWssEPEmcPlanAction.JPA_NAME).append(" where valid = 1 and SECTION_ID in ( ").append(sectionIdStr).append(" )").append(sqlUtils.getSqlPartByCID());
        List<SESWssEPEmcPlanAction>  planIntroPOs = alarmRecordDao.findByHql(communicatIntroHQL.toString());

        Map<Long , List<SESWssEPEmcPlanAction>>  planIntroMapping = new HashMap<>(); //通讯组和指令的映射

        if (!CollectionUtils.isEmpty(planIntroPOs)) {

            for (int i = 0 ; i < planIntroPOs.size(); i++) {
                if (null == planIntroMapping.get(planIntroPOs.get(i).getSectionId())) {
                    //如果没有
                    List<SESWssEPEmcPlanAction> tempList = new ArrayList<>();
                    tempList.add(planIntroPOs.get(i));
                    planIntroMapping.put(Long.valueOf(planIntroPOs.get(i).getSectionId()), tempList);
                } else {
                    planIntroMapping.get(Long.valueOf(planIntroPOs.get(i).getSectionId())).add(planIntroPOs.get(i));
                }
            }
            log.error("分组后通讯组和指令有{}大类", planIntroMapping.size());
        }
        List<SESWssEPRelatedAlarmVO> planVOs = emergencyPlanList.stream().map(SESWssEPRelatedAlarmVO::SESWssEPRelatedAlarmPO2VO).collect(Collectors.toList());
        for (int i = 0 ; i < planVOs.size(); i++) {
            Long planId = planVOs.get(i).getId();
            Long sectionId = planCommuGroupMapping.get(planId);
            if (null == sectionId) {
                log.error("未通过预案id找到通讯组id");
                break;
            }
            log.error("预案id:{}, 通讯组id:{}", planId, sectionId);
            List<SESWssEPEmcPlanAction> introPOs =  planIntroMapping.get(sectionId);
            if (!CollectionUtils.isEmpty(introPOs)) {
                log.error("该条预案:{}有{}个指令", planId , introPOs.size());

                //格式转换
                List<AlarmActionVO> planIntroVOS =  new ArrayList<>();
                for (SESWssEPEmcPlanAction thePO : introPOs) {
                    AlarmActionVO planIntroVO = AlarmActionVO.PlanActionPO2VO(thePO);
                    planIntroVOS.add(planIntroVO);
                }
                planVOs.get(i).setAlarmAction(planIntroVOS);
            } else {
                log.error("该条预案:{}没有指令", planId , introPOs.size());
            }
        }
        return planVOs;
    }


    /**
     * 根据事故类型id 报警等级获取应急预案
     *
     * @param idsVO
     * @return
     */
    @Override
    public Set<SESWssEPEmergencyPlan> getContingencyPlan(TypeAndPlanIdsVO idsVO) {
        String idListStr = null;
        String planIdsStr = null;
        List<String> planIds = idsVO.getPlanIds();
        List<Long> typeIds = idsVO.getTypeIds();
        //匹配条件同时不存在 todo 若后期需要加区域匹配 这里需要同步添加
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(typeIds) && StringUtils.isEmpty(idsVO.getAlarmLevel()) && org.apache.commons.collections4.CollectionUtils.isEmpty(planIds)) {
            return null;
        }
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(typeIds)) {
            idListStr = typeIds.get(0).toString();
            for (int i = 1; i < typeIds.size(); i++) {
                idListStr += "," + typeIds.get(i);
            }
        }

        String sql = " from " + SESWssEPEmergencyAccid.JPA_NAME + " where valid = 1 and ACCIDENT_CLASS in (" + idListStr + ")";
        final List<SESWssEPEmergencyAccid> accids = almAlarmRecordDao.findByHql(sql);
        Set<SESWssEPEmergencyPlan> plans = new HashSet<>();
        for (SESWssEPEmergencyAccid accid : accids) {
            plans.add(accid.getPlanId());
        }

        if (CollectionUtils.isEmpty(planIds)) {
            log.error("初始预案为空！");
        } else {
            planIdsStr = planIds.get(0).toString();
            for (int i = 1; i < planIds.size(); i++) {
                planIdsStr += "," + planIds.get(i);
            }
        }
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESWssEPEmergencyPlan.JPA_NAME).append(" where valid = 1 and id in (").append(planIdsStr).append(")");
        List<SESWssEPEmergencyPlan> planList = almAlarmRecordDao.findByHql(hql.toString());
        for (SESWssEPEmergencyPlan plan : planList) {
            plans.add(plan);
        }
        //根据报警等级匹配预案 这里直接映射报警等级的系统编码code和预案等级的系统编码code
        if (!StringUtils.isEmpty(idsVO.getAlarmLevel())) {
            ISystemCode serviceSystemCode = systemCodeService.getSystemCodeByID(idsVO.getAlarmLevel());
            String code = serviceSystemCode.getCode();
            List<SystemCode> systemCodeByEntityCode = systemCodeService.getSystemCodeByEntityCode(ENTITY_CODE);
            Optional<SystemCode> optional = systemCodeByEntityCode.stream().filter(systemCode -> systemCode.getCode().equals(code)).findFirst();
            if (optional.isPresent()) {
                //清空原sql
                hql.setLength(0);
                hql.append(" from ").append(SESWssEPEmergencyPlan.JPA_NAME).append(" where valid = 1 and PLAN_RANK  ='").append(optional.get().getId()).append("'");
                List<SESWssEPEmergencyPlan> planListByRank = almAlarmRecordDao.findByHql(hql.toString());
                plans.addAll(planListByRank);
            }
        }
        return plans;
    }



    @Override
    public List<AllEmerMemberVO> queryEmergency() {

        List<AllEmerMemberVO> allEmerMemberVOList = new ArrayList<>();
        List<DetparentmentVO> stringList = new ArrayList<>();
        //部门   selectALL
        //父节点  -1    子节点   部门ID    0   1
        List<SESEABEabSetion> eabSetionList2 = sesecdCctvRecordDao.findByHql("from " +  SESEABEabSetion.JPA_NAME + " where valid = 1  " + sqlUtils.getSqlPartByCID());
        if (null!=eabSetionList2 && eabSetionList2.size()>0){
            for (SESEABEabSetion seseabEabSetion : eabSetionList2){
                DetparentmentVO detparentmentVO = new DetparentmentVO();
                Long id = seseabEabSetion.getBelongDepartment().getId();
                String belongDepartmentName = seseabEabSetion.getBelongDepartmentName();
                detparentmentVO.setLayRec(id);
                detparentmentVO.setBelongDepartment(belongDepartmentName);
                stringList.add(detparentmentVO);

            }

        }
//        List<DetparentmentVO> collect = stringList.stream().distinct().collect(Collectors.toList());
        List<DetparentmentVO> collect = stringList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(DetparentmentVO::getLayRec))), ArrayList::new)
                );
        log.info("得到部门数量"+collect.size());

        for (DetparentmentVO detparentmentVO : collect){
            AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
            allEmerMemberVO.setBelongDepartment(detparentmentVO.getBelongDepartment());
            allEmerMemberVO.setLeaf(true);
            allEmerMemberVO.setParentId("-1");
            allEmerMemberVO.setLayNo(1);
            allEmerMemberVO.setLayRec(detparentmentVO.getLayRec()+"");
            allEmerMemberVOList.add(allEmerMemberVO);




        }


        //查通讯组
        //  父节点     部门id         本身id   1   2
        List<SESEABEabSetion> eabSetionList = sesecdCctvRecordDao.findByHql("from " +  SESEABEabSetion.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID());
        log.info("查询通讯组数据库"+eabSetionList2.size());
        String sesecdLayRec = "";
        if (null!=eabSetionList && eabSetionList.size()>0){
            for (SESEABEabSetion seseabEabSetion : eabSetionList){
                AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
                allEmerMemberVO.setSectionName(seseabEabSetion.getSectionName());
                allEmerMemberVO.setLeaf(true);
                allEmerMemberVO.setParentId(seseabEabSetion.getBelongDepartment().getId()+"");
                allEmerMemberVO.setLayNo(2);
                //allEmerMemberVO.setLayRec(seseabEabSetion.getBelongDepartment().getId()+"-"+seseabEabSetion.getId());
                allEmerMemberVO.setLayRec(seseabEabSetion.getId()+"");
                allEmerMemberVOList.add(allEmerMemberVO);






                log.info("插入通讯组数据条数"+allEmerMemberVOList.size());
            }

        }

        //查人员表
        //父节点   组的id     子节点   本身id    1   3
        List<SESEABSetionmember> seseabSetionmemberList = sesecdCctvRecordDao.findByHql("from " +  SESEABSetionmember.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID());
        log.info("查询人员表数量"+seseabSetionmemberList.size());
        if (null!=seseabSetionmemberList && seseabSetionmemberList.size() >0){
            for (SESEABSetionmember seseabSetionmember : seseabSetionmemberList){
                AllEmerMemberVO allEmerMemberVO = new AllEmerMemberVO();
                allEmerMemberVO.setPersonId(seseabSetionmember.getPersonId().getId());
                allEmerMemberVO.setStaffCode(seseabSetionmember.getPersonId().getCode());
                allEmerMemberVO.setPersonName(seseabSetionmember.getPersonName());
                allEmerMemberVO.setMobile(seseabSetionmember.getMobile());
                allEmerMemberVO.setTelephone(seseabSetionmember.getTelephone());
                allEmerMemberVO.setLeaf(false);
                if(seseabSetionmember.getSetionId() !=null){
                    allEmerMemberVO.setParentId(seseabSetionmember.getSetionId().getId().toString());
                }                allEmerMemberVO.setLayNo(3);
                //allEmerMemberVO.setLayRec(seseabSetionmember.getSetionId().getBelongDepartment().getId()+"-"+seseabSetionmember.getSetionId().getId()+"-"+seseabSetionmember.getId());
                allEmerMemberVO.setLayRec(seseabSetionmember.getId()+"");
                allEmerMemberVOList.add(allEmerMemberVO);
                log.info("插入人员信息数据条数"+allEmerMemberVOList.size());




            }

        }
        log.info("一共插入数据调试"+allEmerMemberVOList.size());
        return allEmerMemberVOList;
    }

    /**
     * 获取全部应急预案，根据预案和事件的匹配度进行排序展示
     * 需求描述： 1.  应急指挥一张图中，通过应急指挥调度台-应急预案入口，显示所有的预案， 需支持分页
     *          2.  支持按照预案名称进行模糊查询
     *          3.  按照预案的事故类型和事故等级与事件的匹配度进行排序，原来处警中选择的预案优先展示（如果有），匹配度高的次之
     *
     * @param  emergencyName 预案名称
     * @return
     */
    @Override
    public List<SESWssEPRelatedAlarmVO> listEmergencyPlanByName(String emergencyName, Long eventId) {
        //预案模块获取全部预案
        List<SESWssEPEmergencyPlan> emergencyPlans = this.obtainEmergencyPlan(emergencyName);
        log.error(" List<SESWssEPEmergencyPlan>:-------------------"+ JSON.toJSONString(emergencyPlans));

        if(CollectionUtils.isEmpty(emergencyPlans)){
            log.error("获取预案为空");
            List<SESWssEPRelatedAlarmVO> emptyPlans = new ArrayList<>();
            return emptyPlans;
        }
        log.error("获取预案数据量：" +emergencyPlans.size());

        //获取事件关联的预案
        String planIDs = emergencyPlans.stream().map(plan -> String.valueOf(plan.getId())).collect(Collectors.joining(","));
        List<SESECDEmePlanObj> emePlanObjs = this.obtainEmergencyPlan(eventId,planIDs);
        if (CollectionUtils.isEmpty(emePlanObjs)) {
            log.error("事件id{}关联的预案为空",eventId);
            //关联预案为空,将非关联预案转换为需求格式 返回到前端
            return disconnectSort(eventId, emergencyPlans);
        }
        log.error("获取到关联事件的预案数量："+emePlanObjs.size());
        List<SESWssEPEmergencyPlan> connectedEmergency
                = emePlanObjs.stream().map(SESECDEmePlanObj::getPlanObj).collect(Collectors.toList());
        Map<Long, List<SESWssEPEmergencyPlan>> connectedEmergencyMap
                = connectedEmergency.stream().collect(Collectors.groupingBy(SESWssEPEmergencyPlan::getId));
        Map<Long, List<SESECDEmePlanObj>> planObjMap = emePlanObjs.stream().collect(Collectors.groupingBy(planObj -> (planObj.getPlanObj()).getId()));

        //获取事件不关联的预案
        List<SESWssEPRelatedAlarmVO> disconnectedEmergencyPlan =new ArrayList<>();
        List<SESWssEPEmergencyPlan> disconnect = emergencyPlans.stream().filter(plan ->
                CollectionUtils.isEmpty(connectedEmergencyMap.get(plan.getId()))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(disconnect)){
            log.error("全部预案中不关联事件的数量："+disconnect.size());
            //事件未关联的预案进行排序
            disconnectedEmergencyPlan = disconnectSort(eventId, disconnect);
        }

        //事件已经关联的预案进行排序
        List<SESWssEPRelatedAlarmVO> result = connectSort(eventId, connectedEmergency, planObjMap);

        //将未匹配到事件的预案放置排序都最后
        if (CollectionUtils.isEmpty(disconnectedEmergencyPlan)){
            log.error("事件全部关联到预案");
            return result;
        }
        log.error("合并未关联该事件（"+eventId+"）的预案数量" +disconnectedEmergencyPlan.size());
        result.addAll(disconnectedEmergencyPlan);
        return result;
    }


    /**
     * 预案开启或关闭功能实现
     * @param eventId  事件id
     * @param planId  预案id
     * @param isClose 开启状态
     * @return
     */
    //1.检查预案和事件是否关联  关联走更新
    //2.预案和事件不关联 -》事件的关联预案表新增数据，开启状态设置为开启，将预案的“预案指令”数据同步到事件的”关联指令“表当中
    //3.预案关联的指令已下达，不予修改预案开启状态
    @Override
    public EnableChangStateVO openEmergencyPlan(Long eventId, Long planId, Boolean isClose) {
        log.error("事件id：{}；预案id:{}", eventId, planId);
        log.error("修改是否关联：{}", isClose);
        List<SESECDEmePlanObj> emePlanList = this.obtainEmergencyPlan(eventId, String.valueOf(planId));
        SESECDEmePlanObj planObj = new SESECDEmePlanObj();
        String content;
        if (!CollectionUtils.isEmpty(emePlanList)) {
            planObj = emePlanList.get(0);
            if (this.judgment(planObj)) {
                log.error("该事件和预案已经关联（EmePlanObj_ID:{}），走修改逻辑", planObj.getId());
                //跟新数据逻辑
                planObj.setIsClose(isClose);
                planObjDao.update(planObj);
                //记录修改日志
                content = "【应急预案】：" + planObj.getPlanObj().getName() + "，设置预案状态" + (isClose ? "开启" : "关闭");
                this.recordOperationLog(eventId, planId, content);
                return EnableChangStateVO.UPDATE(content);
            } else {
                content = "【应急预案】：" + planObj.getPlanObj().getName() + "，关联指令已发布不予修改预案状态";
                log.error(content);
                //为防止历史数据isClose为空的情况此处进行填充
                planObj.setIsClose(true);
                planObjDao.update(planObj);
                planObjDao.update(planObj);
                this.recordOperationLog(eventId, planId, content);
                return EnableChangStateVO.FORBIDDEN(content, null);
            }
        }
        if (isClose) {//走新增逻辑
            log.error("该事件和预案未关联，走新增逻辑；确认开启状态：" + isClose);

            if (CollectionUtils.isEmpty(obtainEmergencyPlan(planId))) {
                String msg = "【应急预案】：开启预案失败，预案不存在，预案id:" + planId;
                this.recordOperationLog(eventId, planId, msg);
                return EnableChangStateVO.FORBIDDEN(msg, null);
            }
            planObj.setPlanObj(obtainEmergencyPlan(planId).get(0));
            planObj.setAlarmId(alarmRecordDao.get(eventId));
            planObj.setIsClose(true);
            planObjDao.save(planObj);
            log.error("该事件和预案关联完毕");
            log.error("将预案的“预案指令”数据同步到事件的”关联指令“表当中");
            alarmActionService.generateInstructions(alarmRecordDao.get(eventId));

            //记录日志
            content = "【应急预案】：" + planObj.getPlanObj().getName() + "，关联事件并开启";
            this.recordOperationLog(eventId, planId, content);
            return EnableChangStateVO.INERT(content);
        }

         return EnableChangStateVO.OTHER(null);
    }

    /**
     * 记录预案操作日志
     * @param eventID 事件id
     * @param planID  预案id
     * @param context  操作内容
     */
    private void recordOperationLog(Long eventID,Long planID,String context){
        //新增态势操作日志
        ChangeLogDTO logDTO = ChangeLogDTO.builder()
                .eventId(eventID)//事件id
                .content(context)  //日志记录
                .type(ChangeLogBizTypeEnum.EMERGENCY.getCode()) //所属类型编码
                .realId(planID).build(); //预案id
        applicationContext.publishEvent(new ChangeLogEvent(this,logDTO));
    }

    /**
     * 根据事件和预案，判断指令是否已下达
     * @param planObj
     * @return
     */
    private Boolean judgment(SESECDEmePlanObj planObj){
        log.error("根据事件和预案，判断指令是否已下达");
        List<SESECDAlarmAction> alarmActions = this.obtainPlanAction(planObj.getAlarmId().getId(), planObj.getPlanObj().getId());
        Set<String> sysCode = alarmActions.stream().map(alarmAction -> (alarmAction.getCommomState()).getId()).collect(Collectors.toSet());
        //002-->已下达 ；003-->执行中 004-->已完成
        if (sysCode.contains("SESECD_commonState/002") || sysCode.contains("SESECD_commonState/003") ||sysCode.contains("SESECD_commonState/004") ){
            return false;
        }
        return true;
    }

//--------------------------------------------------------公共功能------------------------------------------------------//


    /**
     * 事件关联的预案进行排序
     * @param eventId 事件id
     * @param connectedEmergency 关联事件的预案
     * @param planObjMap  事件预案关联表
     * @return
     */
    private List<SESWssEPRelatedAlarmVO> connectSort(Long eventId , List<SESWssEPEmergencyPlan> connectedEmergency ,Map<Long, List<SESECDEmePlanObj>> planObjMap){
        //根据事件id 获取关联全部指令
        List<SESECDAlarmAction> alarmActions =  this.obtainAlarmAction(eventId);
        List<SESWssEPRelatedAlarmVO> relatedAlarms = new ArrayList<>();

        //建立预案与指令的关联
        if(CollectionUtils.isEmpty(alarmActions)){
            log.error("事件id{}关联的指令为空",eventId);
            relatedAlarms = connectedEmergency.stream().map(emergency ->
                            SESWssEPRelatedAlarmVO.SESWssEPRelatedAlarmPO2VO(emergency, new ArrayList<>(),planObjMap))
                    .collect(Collectors.toList());
        }else {
            log.error("事件id{}关联的指令数量",alarmActions.size());
            Map<Long, List<AlarmActionVO>> alarmActionMap = this.reflectAlarmAction(alarmActions);
            relatedAlarms = connectedEmergency.stream().map(emergency ->
                            SESWssEPRelatedAlarmVO.SESWssEPRelatedAlarmPO2VO(emergency, alarmActionMap.get(emergency.getId()),planObjMap))
                    .collect(Collectors.toList());
        }

        //设置匹配度
        this.enhance(eventId,relatedAlarms);

        //根据匹配值排序
        List<SESWssEPRelatedAlarmVO> result = relatedAlarms.stream().sorted(Comparator
                .comparing(SESWssEPRelatedAlarmVO::getScore).reversed()).collect(Collectors.toList());
        return result;
    }

    /**
     * 未匹配的预案进行排序
     * @param alarmId
     * @param emergencyPlans
     * @return
     */
    private List<SESWssEPRelatedAlarmVO> disconnectSort(Long alarmId , List<SESWssEPEmergencyPlan> emergencyPlans){
        //根据预案id 获取“预案指令”数据
        String ids = emergencyPlans.stream().map(plan -> String.valueOf(plan.getId())).collect(Collectors.joining(","));
        List<SESWssEPEmcPlanAction> planActions = this.obtainPlanAction(ids);
        Map<Long, List<AlarmActionVO>> planActionMap = this.reflectPlanAction(planActions);
        List<SESWssEPRelatedAlarmVO> disconnect = emergencyPlans.stream().map(disconnected -> SESWssEPRelatedAlarmVO
                .SESWssEPRelatedAlarmPO2VO(disconnected,planActionMap.get(disconnected.getId()),0)).collect(Collectors.toList());
        this.enhance(alarmId,disconnect);
        List<SESWssEPRelatedAlarmVO> collect = disconnect.stream().sorted(Comparator
                .comparing(SESWssEPRelatedAlarmVO::getScore).reversed()).collect(Collectors.toList());
        return collect;
    }



    /**
     * 根据预案与事件的关联性，设置匹配度（主要判断事故类型和报警等级是否一致）
     * @param alarmId
     * @param relatedAlarms
     */
    //推荐值排序 1事故类型 2报警等级<=>预案等级 3区域匹配（暂无）
    public void enhance(Long alarmId , List<SESWssEPRelatedAlarmVO> relatedAlarms){
        SESECDAlmAlarmRecord alarmRecord = alarmRecordDao.get(alarmId);
        //获取当前时间事故类型
        Set<SESWssERAccidentClass> eventAcc = obtainAlarmEnenetrel(alarmId);

        for (SESWssEPRelatedAlarmVO relatedAlarmVO : relatedAlarms) {
            //当前预案事故类型
            Set<SESWssERAccidentClass> planAcc = this.obtainAccidentClass(relatedAlarmVO.getId());

            //判断事故类型是否匹配
            if (!CollectionUtils.isEmpty(org.apache.commons.collections4.CollectionUtils.intersection(eventAcc, planAcc))) {
                relatedAlarmVO.setScore(relatedAlarmVO.getScore() + 1);
            }

            //报警等级
            SystemCode alarmLevel = alarmRecord.getAlarmLevel();
            if (null != relatedAlarmVO.getPlanRank() && null != alarmLevel && alarmLevel.getCode().equals(relatedAlarmVO.getPlanRank().getCode())){
                relatedAlarmVO.setScore(relatedAlarmVO.getScore() + 1);
            }
        }
    }

    /**
     * 映射指令和预案的关系
     */
    public Map<Long ,List<AlarmActionVO>> reflectPlanAction(List<SESWssEPEmcPlanAction> planActions){
        log.error("映射指令和预案的关系....");
        //建立预案id - 事件指令映射
        Map<Long ,List<AlarmActionVO>> alarmActionMap = new HashMap<>();
        for (SESWssEPEmcPlanAction planAction : planActions) {
            if(null == alarmActionMap.get(planAction.getEmergencyPlanId().getId())){
                List<AlarmActionVO> list = new ArrayList<>();
                AlarmActionVO alarmActionVO = AlarmActionVO.AlarmActionVOPO2VO(planAction);
                list.add(alarmActionVO);
                alarmActionMap.put(planAction.getEmergencyPlanId().getId(),list);
            }else {
                alarmActionMap.get(planAction.getEmergencyPlanId().getId()).add(AlarmActionVO.AlarmActionVOPO2VO(planAction));
            }
        }
        log.error("预案id - 事件指令映射:{}",alarmActionMap);
        return alarmActionMap;
    }

    /**
     * 映射指令和预案的关系
     */
    public Map<Long ,List<AlarmActionVO>> reflectAlarmAction(List<SESECDAlarmAction> alarmActions){
        log.error("映射指令和预案的关系....");
        //建立预案id - 事件指令映射
        Map<Long ,List<AlarmActionVO>> alarmActionMap = new HashMap<>();
        for (SESECDAlarmAction alarmAction : alarmActions) {
            if(null == alarmActionMap.get(alarmAction.getInstructions().getEmergencyPlanId().getId())){
                List<AlarmActionVO> list = new ArrayList<>();
                AlarmActionVO alarmActionVO = AlarmActionVO.AlarmActionVOPO2VO(alarmAction);
                list.add(alarmActionVO);
                alarmActionMap.put(alarmAction.getInstructions().getEmergencyPlanId().getId(),list);
            }else {
                alarmActionMap.get(alarmAction.getInstructions().getEmergencyPlanId().getId()).add(AlarmActionVO.AlarmActionVOPO2VO(alarmAction));
            }
        }
        log.error("预案id - 事件指令映射:{}",alarmActionMap);
        return alarmActionMap;
    }

    /**
     * 根据事件，预案id 获取指令
     * @param eventID
     * @param planID
     */
    public List<SESECDAlarmAction> obtainPlanAction(Long eventID ,Long planID){
        log.error("根据事件：{}，预案：{}，获取指令" +eventID,planID);
        //当前预案事故类型
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM ").append(SESECDAlarmAction.JPA_NAME).append(" WHERE VALID = 1 ");
        hql.append(sqlUtils.getSqlPartByCID());
        if (!ObjectUtils.isEmpty(eventID)) hql.append("AND ALARM_ID =").append(eventID);
        if (!ObjectUtils.isEmpty(planID)) hql.append(" AND EMERGENCY_PLAN =").append(planID);
        log.error("根据事件id获取事故类型HQL:" +hql);
        List<SESECDAlarmAction> planActions = alarmRecordDao.findByHql(hql.toString());
        return planActions;
    }
    /**
     * 根据预案id集合 获取“预案指令”表数据
     * @param ids
     */
    public  List<SESWssEPEmcPlanAction> obtainPlanAction(String ids){
        log.error("根据预案id 获取“预案指令”表数据 ,预案集合:{}:" +ids);
        //当前预案事故类型
        StringBuilder hql = new StringBuilder();
        hql.append(" FROM ").append(SESWssEPEmcPlanAction.JPA_NAME).append(" where  valid = 1 ");
        hql.append(sqlUtils.getSqlPartByCID());
        hql.append(" AND EMERGENCY_PLAN_ID in(").append(ids).append(")");
        log.error("根据事件id获取事故类型HQL:" +hql);
        List<SESWssEPEmcPlanAction> planActions = alarmRecordDao.findByHql(hql.toString());
        return planActions;
    }

    /**
     * 根据预案id 获取事故类型
     * @param emergencyID
     */
    public Set<SESWssERAccidentClass> obtainAccidentClass(Long emergencyID){
        log.error("根据预案id 获取事故类型 ,预案id:{}:" +emergencyID);
        //当前预案事故类型
        String sql = " FROM " + SESWssEPEmergencyAccid.JPA_NAME + " WHERE VALID = 1 AND PLAN_ID = " + emergencyID;
        log.error("根据预案id 获取事故类型 SQL:" +sql);
        List<SESWssEPEmergencyAccid> accidList = alarmRecordDao.findByHql(sql);
        Set<SESWssERAccidentClass> planAcc = accidList.stream().map(SESWssEPEmergencyAccid::getAccidentClass).collect(Collectors.toSet());
        return planAcc;
    }

    /**
     * 根据事件id获取事故类型
     * @param alarmId
     */
    public Set<SESWssERAccidentClass> obtainAlarmEnenetrel(Long alarmId){
        log.error("根据事件id获取事故类型,事件id:{}",alarmId);
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESECDAlarmEnenetrel.JPA_NAME).append(" where  valid = 1 and ALARM_ID =")
           .append(alarmId);
        hql.append(sqlUtils.getSqlPartByCID());
        log.error("根据事件id获取事故类型HQL:" +hql);
        List<SESECDAlarmEnenetrel> accidentTypeList = alarmRecordDao.findByHql(hql.toString());
        Set<SESWssERAccidentClass> eventAcc = accidentTypeList.stream().map(SESECDAlarmEnenetrel::getEnenetrel).collect(Collectors.toSet());
        return eventAcc;
    }

    /**
     * 获取事件关联的预案
     */
    public List<SESECDEmePlanObj> obtainEmergencyPlan(Long alarmId ,String planId){
        log.error("根据事件id 获取关联全部指令，入参alarmId:{}",alarmId);
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESECDEmePlanObj.JPA_NAME).append(" where valid = 1 and ALARM_ID = ").append(alarmId);
        hql.append(sqlUtils.getSqlPartByCID());
        if (!ObjectUtils.isEmpty(planId)) hql.append(" AND PLAN_OBJ in (" + planId + ")");
        log.error("查询全部预案Hql:" + hql);
        List<SESECDEmePlanObj> emergencyPlans = alarmRecordDao.findByHql(hql.toString());
        return emergencyPlans;
    }
    /**
     * 根据事件id 获取关联全部指令
     * @param alarmId
     * @return
     */
    public List<SESECDAlarmAction> obtainAlarmAction(Long alarmId ){
        log.error("根据事件id 获取关联全部指令，入参alarmId:{}",alarmId);
        StringBuffer hql = new StringBuffer();
        hql.append(" from ").append(SESECDAlarmAction.JPA_NAME).append(" where valid = 1 and ALARM_ID = ").append(alarmId);
        hql.append(sqlUtils.getSqlPartByCID());
        log.error("查询全部预案Hql:" + hql);
        List<SESECDAlarmAction> alarmActions = new ArrayList<>();
        try{
            alarmActions = alarmRecordDao.findByHql(hql.toString());
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return alarmActions;
    }

    /**
     * 模糊查询全部预案
     * @param emergencyName
     * @return
     */
    public List<SESWssEPEmergencyPlan> obtainEmergencyPlan(String emergencyName){
        log.error("获取全部预案，入参emergencyName:{}",emergencyName);
        StringBuffer sql = new StringBuffer();
        sql.append(" FROM ").append(SESWssEPEmergencyPlan.JPA_NAME).append(" WHERE VALID = 1").append(" AND DOCUMENT_STATUS = '已发布'");
        sql.append(sqlUtils.getSqlPartByCID());
        if (!StringUtils.isEmpty(emergencyName)){
            sql.append(" AND PLAN_NAME LIKE '%").append(emergencyName).append("%'");
        }
        log.error("查询全部预案Hql:" + sql);
        List<SESWssEPEmergencyPlan> result = almAlarmRecordDao.findByHql(sql.toString());
        //预案模块中进行获取
//        Result<List<SESWssEPEmergencyPlan>> result = sesWssERClient.getAllEmergencyPlan(emergencyName);
        return result;
    }

    /**
     * 根据预案id获取预案数据
     * @param emergencyID
     * @return
     */
    //查询事件所关联的预案
    public List<SESWssEPEmergencyPlan> obtainEmergencyPlan(Long emergencyID) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESWssEPEmergencyPlan.JPA_NAME).append(" where ID = ").append(emergencyID);
        List<SESWssEPEmergencyPlan> emePlanObjs = alarmRecordDao.findByHql(hql.toString());
        return emePlanObjs;
    }




    /*private Map<Long, List<EabSectionVO>> getLongListMap(StringBuilder hql) {
        //查询所有关联预案的通讯组指令
        hql.delete(0, hql.length());
        hql.append(" from ").append(SESWssEPEmcPlanAction.JPA_NAME).append(" where valid = 1").append(sqlUtils.getSqlPartByCID());
        List<SESWssEPEmcPlanAction> planActions = planActionService.findEmcPlanActionsByHql(hql.toString());
        Map<String, List<EmcPlanActionVO>> planActionVOMapping = new HashMap<>();
        if (CollectionUtils.isEmpty(planActions)) {
            log.error("关联预案的通讯组指令 为空");
        } else {
            log.error("关联预案的通讯组指令 数量:{}", planActions.size());
            //以通讯组id进行分组 通讯组id---通讯组指令VO
            planActionVOMapping = planActions.stream().map(EmcPlanActionVO::EmcPlanActionVOPO2VO).collect(Collectors.groupingBy(EmcPlanActionVO::getSectionId));
        }

        //查询所有关联预案的通讯组
        hql.delete(0, hql.length());
        hql.append(" from ").append(SESWssEPEmePeopleGroup.JPA_NAME).append(" where valid = 1").append(sqlUtils.getSqlPartByCID());
        List<SESWssEPEmePeopleGroup> emePeopleGroupsByHql = groupService.findEmePeopleGroupsByHql(hql.toString());
        Map<Long, List<EabSectionVO>> sectionVOMapping = new HashMap<>();
        if (CollectionUtils.isEmpty(emePeopleGroupsByHql)) {
            log.error("通讯组 为空");
        } else {
            log.error("通讯组 数量:{}", emePeopleGroupsByHql.size());
//            Map<String, List<EmcPlanActionVO>> finalPlanActionVOMapping = planActionVOMapping;
//            List<EabSectionVO> collect = emePeopleGroupsByHql.stream().map((SESWssEPEmePeopleGroup input) -> EabSectionVO.EabSectionVOPO2VO(input.getSectionId(), null, finalPlanActionVOMapping)).collect(Collectors.toList());
            for (SESWssEPEmePeopleGroup group : emePeopleGroupsByHql) {
                if (null == sectionVOMapping.get(group.getEmergencyPlanId().getId())) {
                    List<EabSectionVO> list = new ArrayList<>();
                    list.add(group.getSectionId() != null ? EabSectionVO.EabSectionVOPO2VO(group.getSectionId(), null, planActionVOMapping) : null);
                } else {
                    sectionVOMapping.get(group.getEmergencyPlanId().getId()).add(group.getSectionId() != null ? EabSectionVO.EabSectionVOPO2VO(group.getSectionId(), null, planActionVOMapping) : null);
                }
            }
        }
        return sectionVOMapping;


        List<SESWssEPRelatedAlarmVO> relatedAlarmVOS = emePlanObjs.stream().map((SESECDEmePlanObj obj) -> SESWssEPRelatedAlarmVO.SESWssEPRelatedAlarmPO2VO(obj.getPlanObj(), sectionVOMapping)).collect(Collectors.toList());



        return relatedAlarmVOS;

        return null;
    }*/
}
