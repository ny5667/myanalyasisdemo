package com.supcon.orchid.SESECD.services.impl.action;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.SESECDEmcActionDao;
import com.supcon.orchid.SESECD.daos.SESECDEmcSituationDao;
import com.supcon.orchid.SESECD.daos.SESECDMainDepartmentDao;
import com.supcon.orchid.SESECD.daos.SESECDMainPeopleDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;
import com.supcon.orchid.SESECD.entities.SESECDMainPeople;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.vo.common.*;
import com.supcon.orchid.SESECD.model.vo.forfront.EventActionVO;
import com.supcon.orchid.SESECD.model.vo.forfront.TrackVO;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionService;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionForFrontService;
import com.supcon.orchid.SESECD.services.impl.forfront.CustomSESECDForFrontServiceImpl;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDActionForFrontServiceImpl extends BaseServiceImpl implements CustomSESECDActionForFrontService {

    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDForFrontServiceImpl.class);

    @Autowired
    private SESECDEmcSituationDao situationDao;

    @Autowired
    private SESECDMainDepartmentDao mainDepartmentDao;

    @Autowired
    private SESECDEmcActionDao actionDao;

    @Autowired
    private SESECDMainPeopleDao mainPeopleDao;

    @Autowired
    private CustomSESECDActionService customSESECDActionService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    @Autowired
    private SystemCodeService systemCodeService;

    /**
     * 列出某个事件下的应急行动
     *
     * @param eventId
     * @return
     */
    @Override
    public List<EventActionVO> listEventAction(Long eventId) {
        //可参考: com/supcon/orchid/SESECD/services/impl/CustomSESECDAlarmServiceImpl.java#getEmcActionInfo 957行
        List<EventActionVO> eventActionVOS = new ArrayList<>();

        //查询应急行动
        StringBuilder eventActionHQL = new StringBuilder();//SES_ECD_EMC_ACTIONS
        eventActionHQL.append(" from ").append(SESECDEmcAction.JPA_NAME).append(" where valid = 1 ").append(" and event_id = ").append(eventId);
        List<SESECDEmcAction> actionList = situationDao.findByHql(eventActionHQL.toString());
		/*
		logger.error("db返回");
		logger.error(JSON.toJSONString(actionList));
		 */
        if (CollectionUtils.isEmpty(actionList)) {
            logger.error("应急行动数量为空");
        } else {
            logger.error("应急行动数量:{}", actionList.size());
            eventActionVOS = actionList.stream().map(
                    (SESECDEmcAction input) ->
                            EventActionVO.ActionPO2VO(input)
            ).collect(Collectors.toList());
            //查询责任人
            Map<Long, List<SESECDMainPeople>> actionMainPeopleMapping = getActionMainPeropleMappingByActions(actionList);
            if (!CollectionUtils.isEmpty(actionMainPeopleMapping)) {
                eventActionVOS.forEach(e -> {
                    List<SESECDMainPeople> theActionMainPeoples = actionMainPeopleMapping.get(e.getId());
                    if (CollectionUtils.isEmpty(theActionMainPeoples)) {
                        logger.error("该行动{}没有责任人", e.getId());
                    } else {
                        logger.error("该行动{}有{}个责任人", e.getId(), theActionMainPeoples.size());
                        List<MainPeopleVO> mainPeopleVOS = new ArrayList<>();
                        theActionMainPeoples.stream().forEach(e2 -> {
                            SetionMemberVO theSetionMemberVO = new SetionMemberVO();
                            theSetionMemberVO.setId(e2.getId());
                            StaffVO theSetionMemberStaff = StaffVO.CreateStaffVOByName(e2.getOwnPerson().getPersonId().getId(), e2.getOwnPerson().getPersonName());
                            theSetionMemberVO.setStaff(theSetionMemberStaff);
                            theSetionMemberVO.setId(e2.getOwnPerson().getId());

                            MainPeopleVO theMainPeople = new MainPeopleVO();
                            theMainPeople.setId(e2.getId());
                            theMainPeople.setOwnPerson(theSetionMemberVO);
                            mainPeopleVOS.add(theMainPeople);
                        });
                        e.setOwnMainPeople(mainPeopleVOS);
                    }
                });
            }
            //查询通讯组--责任单位
            Map<Long, List<SESECDMainDepartment>> actionMainDepartMapping = getActionMainDepartMappingByActions(actionList);//查询带事件和责任单位的映射mapping
            logger.error("actionmaindepartmaping:");
            logger.error(JSON.toJSONString(actionMainDepartMapping));
            if (!CollectionUtils.isEmpty(actionMainDepartMapping)) {
                eventActionVOS.forEach(e -> {
                    List<SESECDMainDepartment> theActionMainDepartList = actionMainDepartMapping.get(e.getId());
                    if (CollectionUtils.isEmpty(theActionMainDepartList)) {
                        logger.error("该行动{}没有责任单位", e.getId());
                    } else {
                        logger.error("该行动{}有{}个责任单位", e.getId(), theActionMainDepartList.size());
                        //List<DepartmentVO> departmentVOS = new ArrayList<>();
                        List<MainDepartVO> mainDepartVOS = new ArrayList<>();
                        theActionMainDepartList.stream().forEach(e2 -> {
                            if (null != e2.getOwnDepartmentN() && null != e2.getOwnDepartmentN().getOrgDept()) { //这个ownDepartmentN其实是SESEABSetion的id(很坑)
                                //DepartmentVO  theDepartmentVo = DepartmentVO.DepartmentPO2VO(e2.getOwnDepartmentN().getBelongDepartment());
                                EabSetionVO theEabSetionVO = EabSetionVO.SetionPO2VO(e2.getOwnDepartmentN());

                                MainDepartVO theMainDepart = new MainDepartVO();
                                theMainDepart.setId(e2.getId());
                                theMainDepart.setEabSetion(theEabSetionVO);
                                mainDepartVOS.add(theMainDepart);
                            } else {
                                logger.error("departmentn is null");
                            }
                        });
                        //e.setOwnDepartment(departmentVOS);
                        e.setOwnMainDepart(mainDepartVOS);
                    }
                });
            }

        }
        return eventActionVOS;
    }


    /**
     * 新增应急行动
     *
     * @param inputVO
     * @return
     */
    @Override
    public Integer addAction(EventActionVO inputVO) throws Exception {
        // MsgModelDTO msgModelDTO = MsgModelDTO.createMsgModel("Alarm", codes, JSON.toJSONString(mapData)); 发消息
        inputVO.setId(null);//保存是add而不是update

        SESECDEmcAction actionPO = new SESECDEmcAction();
        actionPO.setDescription(inputVO.getDescription());
        actionPO.setActionAddr(inputVO.getActionAddr());
        //事件
        SESECDAlmAlarmRecord event = new SESECDAlmAlarmRecord();//事件
        event.setId(inputVO.getEvent().getId());
        actionPO.setEventId(event);

        actionPO.setActionState(SystemCodeVO.CreateSystemCodePOById(inputVO.getActionState().getId()));
        if (!StringUtils.isEmpty(inputVO.getActionTime())) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date actionTime = fmt.parse(inputVO.getActionTime());
            actionPO.setActionTime(actionTime);
        } else {
            throw new Exception(" missing occurstime");
        }
        if (null != inputVO.getIcon() && null != inputVO.getIcon().getId()) {
            SESGISConfigIconLibrary iconObj = new SESGISConfigIconLibrary();
            iconObj.setId(inputVO.getIcon().getId());
            actionPO.setIconObjGis(iconObj);
        } else {
//            throw new Exception("missing icon");
            actionPO.setIconObjGis(null);
        }
        actionPO.setActionCatogory(SystemCodeVO.CreateSystemCodePOById(inputVO.getActionCatogory().getId()));
        actionPO.setTrackRecord(inputVO.getTrackRecord());
        if (null != inputVO.getPoint()) {//坐标非必须
            actionPO.setPoint(JSON.toJSONString(inputVO.getPoint()));
        }
        logger.error("保存应急行动的日志:{}", JSON.toJSONString(actionPO));
        actionDao.save(actionPO);
        actionDao.flush();
        logger.error("保存应急行动完成,id为：{}", actionPO.getId());

        //保存责任人
        if (!CollectionUtils.isEmpty(inputVO.getOwnMainPeople())) {
            insertMainPerson(actionPO, inputVO.getOwnMainPeople());
        } else {
            throw new Exception("missing own persons");
        }
        if (!CollectionUtils.isEmpty(inputVO.getOwnMainDepart())) {
            insertMainDepartment(actionPO, inputVO.getOwnMainDepart());
        }

        logger.error("开始保存点");
        if (null != inputVO.getPoint()) {
            String spatialId = SESECDEmcAction.MODEL_CODE + "_" + actionPO.getId();
            customSESECDSavePointService.savePoint2PG(spatialId, inputVO.getPoint(), "emeActionLayer");
        }
        //发送消息
        customSESECDActionService.customAfterSaveEmcAction(actionPO);
        //新增行动操作日志
        ChangeLogDTO changeLogDTO = ChangeLogDTO.builder().content("【应急行动】 : " +actionPO.getDescription()).type(ChangeLogBizTypeEnum.ACTION.getCode()).eventId(actionPO.getEventId().getId()).realId(actionPO.getId()).build();
        ChangeLogEvent changeEvent = new ChangeLogEvent(this, changeLogDTO);
        applicationContext.publishEvent(changeEvent);
        return 1;
    }


    /**
     * 修改应急行动
     *
     * @param inputVO
     * @return
     */
    @Override
    public Integer updateAction(EventActionVO inputVO) throws Exception {
        String content = "【应急行动】： ";
        if (null == inputVO || null == inputVO.getId()) {
            return 0;
        }
        //先查出来
        StringBuilder eventActionHQL = new StringBuilder();//SES_ECD_EMC_ACTIONS
        eventActionHQL.append(" from ").append(SESECDEmcAction.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(inputVO.getId());
        List<SESECDEmcAction> actionList = situationDao.findByHql(eventActionHQL.toString());
        if (CollectionUtils.isEmpty(actionList)) {
            logger.error("未查询到应急事件");
            return 0;
        }
        SESECDEmcAction actionPO = actionList.get(0);
        //开始执行修改
        if (!StringUtils.isEmpty(inputVO.getDescription())) {
            actionPO.setDescription(inputVO.getDescription());
            content += inputVO.getDescription() + ", ";
        }
        if (!StringUtils.isEmpty(inputVO.getActionAddr())) {
            actionPO.setActionAddr(inputVO.getActionAddr());
        } else {
            actionPO.setActionAddr(null);
        }
        if (null != inputVO.getActionState()) {
            actionPO.setActionState(SystemCodeVO.CreateSystemCodePOById(inputVO.getActionState().getId()));
            content += "状态： " + InternationalResource.get(systemCodeService.getSystemCodeByID(inputVO.getActionState().getId()).getValue(), getCurrentLanguage()) + ", ";
        }
        if (!StringUtils.isEmpty(inputVO.getActionTime())) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date actionTime = fmt.parse(inputVO.getActionTime());
            actionPO.setActionTime(actionTime);
        }
        if (null != inputVO.getIcon() && null != inputVO.getIcon().getId()) {
            SESGISConfigIconLibrary iconObj = new SESGISConfigIconLibrary();
            iconObj.setId(inputVO.getIcon().getId());
            actionPO.setIconObjGis(iconObj);
        }
        if (null != inputVO.getActionCatogory()) {
            actionPO.setActionCatogory(SystemCodeVO.CreateSystemCodePOById(inputVO.getActionCatogory().getId()));
        }
        if (!StringUtils.isEmpty(inputVO.getTrackRecord())) {
            actionPO.setTrackRecord(inputVO.getTrackRecord());
            content += "跟踪记录： " + inputVO.getTrackRecord() +", 跟踪人："+getCurrentStaff().getName()+" ,";
        } else {
            actionPO.setTrackRecord(null);
        }
        if (null != inputVO.getPoint()) {//坐标非必须
            actionPO.setPoint(JSON.toJSONString(inputVO.getPoint()));
        }
        logger.error("修改应急行动的日志:{}", JSON.toJSONString(actionPO));
        actionDao.save(actionPO);
        actionDao.flush();
        logger.error("修改应急行动完成,id为：{}", actionPO.getId());

        //开始修改责任人 做法： 把事件之前的责任人全部删除，然后再新增
        if (!CollectionUtils.isEmpty(inputVO.getOwnMainPeople())) {
            Integer deleteMainPeopleEffetRows = this.deleteActionMainPeople(actionPO.getId());
            logger.error("删除了{}个责任人", deleteMainPeopleEffetRows);
            Integer addMainPersonEffetRow = insertMainPerson(actionPO, inputVO.getOwnMainPeople());
            logger.error("新增了{}个责任人", addMainPersonEffetRow);
        } else {
            logger.error("本次修改无需变更责任人");
        }

        if (!CollectionUtils.isEmpty(inputVO.getOwnMainDepart())) {
            Integer deleteMainDepartEffetRows = this.deleteMainDepart(actionPO.getId());
            logger.error("删除了{}个责任部门", deleteMainDepartEffetRows);
            Integer addMainDepartEffetRows = insertMainDepartment(actionPO, inputVO.getOwnMainDepart());
            logger.error("新增了{}个部门", addMainDepartEffetRows);
        } else {
            logger.error("本次修改无需变更责任单位");
        }
        //发消息
        customSESECDActionService.customAfterSaveEmcAction(actionPO);
        //记录行动描述 ，状态   跟踪，
        ChangeLogDTO changeLogDTO;
        changeLogDTO = ChangeLogDTO.builder().content(content).type(ChangeLogBizTypeEnum.ACTION.getCode()).eventId(actionPO.getEventId().getId()).realId(actionPO.getId()).build();
        ChangeLogEvent changeEvent = new ChangeLogEvent(this, changeLogDTO);
        applicationContext.publishEvent(changeEvent);


        return 1;
    }


    /**
     * 删除应急行动
     *
     * @param actionId
     * @return
     */
    @Override
    public Integer deleteAction(Long actionId) throws Exception {
        logger.error("删除的应急行动的id:{}", actionId);
        //先查出来
        StringBuilder emergencyActionHQL = new StringBuilder();
        emergencyActionHQL.append(" from ").append(SESECDEmcAction.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(actionId);
        List<SESECDEmcAction> actionList = actionDao.findByHql(emergencyActionHQL.toString());
        if (CollectionUtils.isEmpty(actionList)) {
            logger.error("未查询到该应急行动");
            return 0;
        }
        SESECDEmcAction po = actionList.get(0);
        po.setValid(false);
        //开始删除行动
        actionDao.save(po);
        actionDao.flush();
        logger.error("行动删除完成");

        Integer deleteMainPeopleEffetRows = deleteActionMainPeople(actionId);//删除应急行动下的责任人
        logger.error("共删除了{}个责任人", deleteMainPeopleEffetRows);

        Integer deleteMainDepartmentEffetRows = deleteMainDepart(actionId);//删除应急行动下的责任单位
        logger.error("共删除了{}个责任单位", deleteMainDepartmentEffetRows);

        //发消息
        customSESECDActionService.customAfterSaveEmcAction(po);//是否应该加一个delete的ws消息?

        return 1;

		/*
		StringBuilder builder = new StringBuilder();
		builder.append(" update ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 and monitorReport in (:monitorReport)");
		Query query =  reportDetailsDao.createQuery(builder.toString());
		query.setParameterList("monitorReport",map.values()).executeUpdate();
		 */
    }

    /**
     * 仅地图更新应急行动跟踪记录
     * @param trackVO
     */
    @Override
    public void track(TrackVO trackVO) {
        SESECDEmcAction sesecdEmcAction = actionDao.get(trackVO.getId());
        sesecdEmcAction.setTrackRecord(trackVO.getTrackRecord());

        actionDao.update(sesecdEmcAction);

        ChangeLogDTO changeLogDTO;
        String content = "【应急行动】 :跟踪记录： " + trackVO.getTrackRecord() + ", 跟踪人：" + getCurrentStaff().getName();
        changeLogDTO = ChangeLogDTO.builder().content(content).type(ChangeLogBizTypeEnum.ACTION.getCode()).eventId(sesecdEmcAction.getEventId().getId()).realId(trackVO.getId()).build();
        ChangeLogEvent changeEvent = new ChangeLogEvent(this, changeLogDTO);
        applicationContext.publishEvent(changeEvent);
    }

    /*------------------------------------公共功能-----------------------------------------*/

    /**
     * 生成行动和人员的映射mapping
     *
     * @param actionList
     * @return
     */
    private Map<Long, List<SESECDMainPeople>> getActionMainPeropleMappingByActions(List<SESECDEmcAction> actionList) {
        List<Long> actionIds = actionList.stream().map(SESECDEmcAction::getId).collect(Collectors.toList());//获取所有行动的ID
        if (!CollectionUtils.isEmpty(actionIds)) {
            logger.error("共有{}个行动", actionIds.size());
            logger.error(JSON.toJSONString(actionIds));
            //开始组装行动和责任人的mapping,减少数据库查询次数
            Map<Long, List<SESECDMainPeople>> actionMainPeopleMapping = new HashMap<>();//行动和责任人的mapping映射
            String inActionsSQL = createInSQL(actionIds);
            logger.error("inactionssql:{}", inActionsSQL);
            if (StringUtils.isEmpty(inActionsSQL)) {
                return null;
            }
            String mainPeopleHQL = "from " + SESECDMainPeople.JPA_NAME + " where valid = 1 and action_id in " + inActionsSQL;
            List<SESECDMainPeople> mainPeopleList = situationDao.findByHql(mainPeopleHQL);
            logger.error("{}个行动中共查询到{}个责任人", actionIds.size(), mainPeopleList.size());
            if (!CollectionUtils.isEmpty(mainPeopleList)) {
                for (SESECDMainPeople mainPerson : mainPeopleList) {
                    if (null == actionMainPeopleMapping.get(mainPerson.getActionId().getId())) {
                        List<SESECDMainPeople> theMainPersonList = new ArrayList<>();
                        theMainPersonList.add(mainPerson);
                        actionMainPeopleMapping.put(mainPerson.getActionId().getId(), theMainPersonList);
                    } else {
                        actionMainPeopleMapping.get(mainPerson.getActionId().getId()).add(mainPerson);
                    }
                }
            }
            return actionMainPeopleMapping;
        } else {
            return null;
        }
    }


    private Map<Long, List<SESECDMainDepartment>> getActionMainDepartMappingByActions(List<SESECDEmcAction> actionList) {
        List<Long> actionIds = actionList.stream().map(SESECDEmcAction::getId).collect(Collectors.toList());//获取所有行动的ID
        if (!CollectionUtils.isEmpty(actionIds)) {
            logger.error("共有{}个行动", actionIds.size());
            logger.error(JSON.toJSONString(actionIds));
            //开始组装行动和责任单位的mapping,减少数据库查询次数
            Map<Long, List<SESECDMainDepartment>> actionMainDepartMapping = new HashMap<>();//行动和责任人的mapping映射
            String inActionsSQL = createInSQL(actionIds);
            logger.error("inactionssql:{}", inActionsSQL);
            if (StringUtils.isEmpty(inActionsSQL)) {
                return null;
            }
            String mainDepartHQL = "from " + SESECDMainDepartment.JPA_NAME + " where valid = 1 and action_id in " + inActionsSQL;
            List<SESECDMainDepartment> mainDepartList = mainDepartmentDao.findByHql(mainDepartHQL);
            logger.error("{}个行动中共查询到{}个责任单位", actionIds.size(), mainDepartList.size());

            if (!CollectionUtils.isEmpty(mainDepartList)) {
                for (SESECDMainDepartment mainDepartment : mainDepartList) {
                    if (null == actionMainDepartMapping.get(mainDepartment.getActionId().getId())) {
                        List<SESECDMainDepartment> theMainDepartList = new ArrayList<>();
                        theMainDepartList.add(mainDepartment);
                        actionMainDepartMapping.put(mainDepartment.getActionId().getId(), theMainDepartList);
                    } else {
                        actionMainDepartMapping.get(mainDepartment.getActionId().getId()).add(mainDepartment);
                    }
                }
            }
            return actionMainDepartMapping;
        } else {
            logger.error("没有行动");
            return null;
        }
    }


    /**
     * 插入应急事件下的责任人
     *
     * @param actionPO
     * @param mainPeopleVOS
     * @return
     */
    private Integer insertMainPerson(SESECDEmcAction actionPO, List<MainPeopleVO> mainPeopleVOS) {
        logger.error("开始保存责任人");
        //插入责任人表
        logger.error("共有{}个责任人", mainPeopleVOS.size());
        //mainpeople里面的ownperson传setionmember的id即可
        mainPeopleVOS.forEach(e -> {
            SESECDMainPeople mainPeoplePo = new SESECDMainPeople();
            if (null != actionPO && null != actionPO.getId()) {
                mainPeoplePo.setActionId(actionPO);//此时actionPO已经有了id
                SESEABSetionmember oneMember = new SESEABSetionmember();
                oneMember.setId(e.getOwnPerson().getId());
                mainPeoplePo.setOwnPerson(oneMember);
                mainPeopleDao.save(mainPeoplePo);
            }
        });
        logger.error("责任人保存完成");
        return mainPeopleVOS.size();

    }

    private String createInSQL(List<Long> actionIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        actionIds.forEach(e -> {
            sb.append(e).append(",");
        });
        String temp = sb.toString();
        String finalString = temp.substring(0, temp.length() - 1) + ")";//去掉最后一个逗号
        return finalString;
    }


    /**
     * 保存责任单位
     *
     * @param actionPO
     * @param mainDepartVOS
     */
    private Integer insertMainDepartment(SESECDEmcAction actionPO, List<MainDepartVO> mainDepartVOS) {
        logger.error("开始保存责任单位");
        logger.error("共有{}个责任单位:{}", mainDepartVOS.size(), JSON.toJSONString(mainDepartVOS));
        //注意: 存的是setion里面的department
        mainDepartVOS.forEach(e -> {
            SESEABEabSetion theEabSetion = new SESEABEabSetion();
            theEabSetion.setId(e.getEabSetion().getId());//传入的应该是setionId


            //maindepartment里面的owndepartmentN穿setion的id即可
            SESECDMainDepartment mainDepartment = new SESECDMainDepartment();
            mainDepartment.setOwnDepartmentN(theEabSetion);
            mainDepartment.setActionId(actionPO);
            mainDepartmentDao.save(mainDepartment);
        });
        logger.error("责任单位保存完成");
        return mainDepartVOS.size();
    }


    /**
     * 删除某个应急行动下的责任人
     *
     * @param actionId
     * @return
     */
    private Integer deleteActionMainPeople(Long actionId) throws Exception {
        logger.error("开始删除责任人");
        String mainPeopleHQL = "from " + SESECDMainPeople.JPA_NAME + " where valid = 1 and actionId = " + actionId;
        List<SESECDMainPeople> mainPeopleList = situationDao.findByHql(mainPeopleHQL);
        if (!CollectionUtils.isEmpty(mainPeopleList)) {
            logger.error("该应急行动:{}中共查询到{}个责任人", actionId, mainPeopleList.size());
            mainPeopleList.forEach(e -> {
                e.setValid(false);
                mainPeopleDao.save(e);
            });
            logger.error("责任人删除成功");
            return mainPeopleList.size();
        } else {
            logger.error("该应急行动未查询到责任人");
            return 0;
        }
    }


    /**
     * 删除应急行动下的责任单位
     *
     * @param actionId
     * @return
     */
    private Integer deleteMainDepart(Long actionId) {
        logger.error("开始删除责任单位");
        String mainDepartHQL = "from " + SESECDMainDepartment.JPA_NAME + " where valid = 1 and actionId = " + actionId;
        List<SESECDMainDepartment> mainDepartList = mainDepartmentDao.findByHql(mainDepartHQL);
        if (!CollectionUtils.isEmpty(mainDepartList)) {
            logger.error("该应急行动共查询到{}个责任人", mainDepartList.size());
            mainDepartList.forEach(e -> {
                e.setValid(false);
                mainDepartmentDao.save(e);
            });
            logger.error("责任单位删除成功");
            return mainDepartList.size();
        } else {
            logger.error("该应急行动未查询到责任单位");
            return 0;
        }
    }

}
