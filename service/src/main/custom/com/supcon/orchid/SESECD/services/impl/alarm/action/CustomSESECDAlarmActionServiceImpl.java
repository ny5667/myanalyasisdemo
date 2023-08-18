package com.supcon.orchid.SESECD.services.impl.alarm.action;


import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABEabSetionDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABSetionmemberDTO;
import com.supcon.orchid.SESEAB.client.ISESEABEabSetionClient;
import com.supcon.orchid.SESEAB.client.ISESEABSetionmemberClient;
import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
import com.supcon.orchid.SESECD.client.SESECDMapCenterClient;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.*;
import com.supcon.orchid.SESECD.entities.*;
import com.supcon.orchid.SESECD.model.dto.alarm.CommomDto;
import com.supcon.orchid.SESECD.model.dto.alarm.MapPointDto;
import com.supcon.orchid.SESECD.model.dto.alarm.mapPointObjDto;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.dto.message.EcdWsDto;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.notify.DefaultNotifyImpl;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import com.supcon.orchid.SESWssEP.DTO.SESWssEPEmcPlanActionDTO;
import com.supcon.orchid.SESWssEP.client.ISESWssEPEmcPlanActionClient;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.SesCommonFunc.dto.WebSocketParamDTO;
import com.supcon.orchid.VideoPlayWeb.entities.VideoPlayWebCameraConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.utils.DateUtils;
import com.supcon.orchid.utils.JsonUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 接警记录-关联指令
 */
@Service("CustomSESECDAlarmActionService")
@Transactional
public class CustomSESECDAlarmActionServiceImpl extends BaseServiceImpl<SESECDAlarmAction> implements CustomSESECDAlarmActionService {

    /**
     * 指令状态 未下达
     */
    public static String COMMON_STATE_SYSTEM_CODE_ID = "SESECD_commonState/001";

    @Autowired
    SESECDAlarmActionDao alarmActionDao;
    @Autowired
    private DefaultNotifyImpl defaultNotify;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private ISESWssEPEmcPlanActionClient planActionClient;

    @Autowired
    private SESECDAlarmActCameraDao actCameraDao;

    @Autowired
    private ISESEABSetionmemberClient iseseabSetionmemberClient;

    @Autowired
    private SystemCodeService systemCodeService;

    @Autowired
    private SESECDEmcActionDao emcActionDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    @Autowired
    private ISESEABEabSetionClient iseseabEabSetionClient;

    @Autowired
    private SESECDMainDepartmentDao mainDepartmentDao;

    @Autowired
    private SESECDMainPeopleDao mainPeopleDao;

    @Autowired
    private SESECDActionOwnersDao actionOwnersDao;

    /**
     * 消息通知
     */
    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private SESECDActVideoCameraDao actVideoCameraDao;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private SESECDMapCenterClient mapCenterClient;

    @Override
    public void generateInstructions(SESECDAlmAlarmRecord almAlarmRecord) {
        log.error("进入：根据接警预案设置指令");
        //同步数据到数据库，新增该alarmRecord的id在数据库中会找不到
        almAlarmRecordDao.flush();

        //查出行动，不重复新增数据
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDAlarmAction.JPA_NAME).append(" where valid = 1 and alarmId = ?0");
        List<SESECDAlarmAction> actions = alarmActionDao.findByHql(builder.toString(), new Object[]{almAlarmRecord});
        Map<Long, SESECDAlarmAction> actionMap = new HashMap<>();
        actions.forEach(c -> {
            if (c.getInstructions() == null) {
                return;
            }
            actionMap.put(c.getInstructions().getId(), c);
        });


        //查询当前接警的预案
        String hql = "from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and alarmId = ?0";
        List<SESECDEmePlanObj> planObjList = alarmActionDao.findByHql(hql, new Object[]{almAlarmRecord});//关联预案
        if (Objects.isNull(planObjList) || planObjList.size() == 0) {
            log.error("预案为空，返回");
            return;
        }
        for (SESECDEmePlanObj emePlanObj : planObjList) {
            SESWssEPEmergencyPlan planObj = emePlanObj.getPlanObj();
            if (Objects.isNull(planObj)) {
                continue;
            }
            List<SESWssEPEmcPlanActionDTO> planActionDTOList;
            try {
                planActionDTOList = planActionClient.listPlanActionByPlanId(planObj.getId(), getCurrentCompanyId());
            } catch (Exception ex) {
                throw new RuntimeException("获取预案指令报错", ex);
            }
            Collections.sort(planActionDTOList, new Comparator<SESWssEPEmcPlanActionDTO>() {
                @Override
                public int compare(SESWssEPEmcPlanActionDTO person1, SESWssEPEmcPlanActionDTO person2) {
                    if (person1 == null || person1.getSort() == null) {
                        return 1;
                    }
                    if (person2 == null || person2.getSort() == null) {
                        return -1;
                    }
                    // 根据 age 字段进行比较
                    return Integer.compare(person1.getSort(), person2.getSort());
                }
            });

            if (planActionDTOList.isEmpty()) {
                continue;
            }
            for (SESWssEPEmcPlanActionDTO item : planActionDTOList) {
                if (null == item) {
                    continue;
                }
                //已存在，不新增
                if (actionMap.get(item.getId()) != null) {
                    continue;
                }
                //关联指令
                SESECDAlarmAction alarmAction = new SESECDAlarmAction();
                copy2Po(almAlarmRecord, item, alarmAction, planObj);
                alarmActionDao.save(alarmAction);
                alarmActionDao.flush();

                if (item.getVideoId() != null && !item.getVideoId().isEmpty()) {
                    String[] ids = item.getVideoId().split(",");
                    for (String videoId : ids) {
                        SESECDAlarmActCamera camera = new SESECDAlarmActCamera();
                        VideoPlayWebCameraConfig videoPlayWebCameraConfig = new VideoPlayWebCameraConfig();
                        videoPlayWebCameraConfig.setId(Long.valueOf(videoId));

                        camera.setVideoId(videoPlayWebCameraConfig);
                        camera.setAlarmActionId(alarmAction);
                        actCameraDao.save(camera);
                        actCameraDao.flush();
                    }
                }

            }
        }
        log.error("退出生成接到警报生成指令");
    }


    /**
     * 下达指令更改应急事件关联指令状态并将指令下达生成应急行动
     *
     * @param commomIds
     * @return
     */
    @Override
    public BaseInfoVO releaseCommom(List<Long> commomIds) {
        List<String> msgStaffCodes = new ArrayList<>();
        for (Long commomId : commomIds) {
            SESECDAlarmAction sesecdAlarmAction = alarmActionDao.get(commomId);
            log.error("commomId 的值" + commomId);
            log.info("前端传过来的数据*******************************" + commomIds.size());
            SESWssEPEmcPlanAction instructions = sesecdAlarmAction.getInstructions();
            String sectionId = instructions.getSectionId();
            List<MemberDTO> membersBySetionIds = iseseabSetionmemberClient.getMembersBySetionIds(sectionId);
            if (null != membersBySetionIds && membersBySetionIds.size() > 0) {
                for (MemberDTO memberDTO : membersBySetionIds) {
                    String name = memberDTO.getStaffCode();
                    log.info("人员编码code" + name);
                    msgStaffCodes.add(name);

                }
            }
            log.info("通讯组人员的个数" + msgStaffCodes.size());

            // 更改指令状态
            SystemCode commomState = (SystemCode) systemCodeService.getSystemCode("SESECD_commonState/002");
            sesecdAlarmAction.setCommomState(commomState);
            alarmActionDao.merge(sesecdAlarmAction);

            // 下达指令形成应急行动
            SESECDEmcAction sesecdEmcAction = getSesecdEmcAction(sesecdAlarmAction);
            log.info("应急行动保存到数据库的次数*************************************");
            emcActionDao.save(sesecdEmcAction);
            String name = "";
            if (null != sesecdAlarmAction.getOwnPerson() && StringUtils.isNotEmpty(sesecdAlarmAction.getOwnPerson().getPersonName())) {
                name = sesecdAlarmAction.getOwnPerson().getPersonName();
            }

            //记录操作记录 发布指令
            ChangeLogDTO logDTO = ChangeLogDTO.builder().type(ChangeLogBizTypeEnum.COMMAND.getCode()).eventId(sesecdAlarmAction.getAlarmId().getId())
                    .content("【发布指令】 指令执行人： " + name)
                    .build();
            applicationContext.publishEvent(new ChangeLogEvent(this, logDTO));
            //新增行动
            logDTO.setContent(sesecdEmcAction.getDescription());
            logDTO.setType(ChangeLogBizTypeEnum.ACTION.getCode());
            logDTO.setRealId(sesecdEmcAction.getId());
            applicationContext.publishEvent(new ChangeLogEvent(this, logDTO));

            //空间数据库
            log.error("开始保存点");
            if (null != sesecdEmcAction.getPoint()) {
                String spatialId = SESECDEmcAction.MODEL_CODE + "_" + sesecdEmcAction.getId();
                PointVO pointVO = PointVO.PointPO2VO(sesecdEmcAction.getPoint(), SESECDEmcAction.MODEL_CODE + "_", sesecdEmcAction.getId().toString());
                try {
                    customSESECDSavePointService.savePoint2PG(spatialId, pointVO, "emeActionLayer");
                } catch (Exception e) {
                    log.error("空间数据库保存失败！");
                    throw new RuntimeException(e);
                }
            }

            saveMainPeopleAndMainDepartmentAndCamera(sesecdAlarmAction, sesecdEmcAction);

            NotifyMsg(msgStaffCodes, sesecdAlarmAction);
        }

        return BaseInfoVO.ok(null);
    }


    /**
     * 通过应急事件ID获取应急事件关联的指令
     *
     * @param eventId
     * @return
     */
    @Override
    public BaseInfoVO getCommomInfo(Long eventId) {
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        Assert.notNull(sesecdAlmAlarmRecord, "eventId can not find entity");
        List<SESECDAlarmAction> SESECDAlarmActions = alarmActionDao.findByHql("from " + SESECDAlarmAction.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{sesecdAlmAlarmRecord});
        if (SESECDAlarmActions.isEmpty()) {
            return BaseInfoVO.ok(Collections.emptyList());
        }
        List<CommomDto> CommomDtos = new ArrayList<>();
        for (SESECDAlarmAction sesecdAlarmAction : SESECDAlarmActions) {
            CommomDto commomDto = new CommomDto();
            copyToCommonDTO(sesecdAlarmAction, commomDto);

            CommomDtos.add(commomDto);
        }
        return BaseInfoVO.ok(CommomDtos);
    }


    /**
     * 保存应急指令坐标到应急行动业务数据坐标字段
     *
     * @param mapPointDto
     * @return
     */
    @Override
    public String saveMapPoint(MapPointDto mapPointDto) {
        if (null != mapPointDto) {
            Long id = mapPointDto.getId();
            if (null != id) {
                alarmActionDao.flush();
                alarmActionDao.clear();
                SESECDAlarmAction common = alarmActionDao.load(id);
                common.setIsMapPoint(true);
                MapPointDto.MapPoint mapPoint = mapPointDto.getPointInfos();
                String mapPointString = "";
                if (null != mapPoint) {
                    mapPointString = JSON.toJSONString(mapPoint);
                }
                common.setMapPoint(mapPointString);
                alarmActionDao.save(common);
                Map<String, Object> param = new HashMap<>(2);
                param.put("id", SESECDAlarmAction.MODEL_CODE + "_" + id);
                param.put("pointInfos", mapPointString);
                mapCenterClient.insertMutilGeometry(param);
            } else {
                return "fault";
            }
        } else {
            return "fault";
        }
        return "success";
    }


    //*******************************************************自定义事件************************************************//

    @Override
    public void customAfterSaveAlarmAction(SESECDAlarmAction alarmAction, Object... objects) {
        alarmActionDao.flush();
        alarmActionDao.clear();
        SESECDAlarmAction AlarmAction = alarmActionDao.load(alarmAction.getId());
        try {
            EcdWsDto ecdWsDto = new EcdWsDto();
            ecdWsDto.setInstructionId(AlarmAction.getId());
            ecdWsDto.setEventId(AlarmAction.getAlarmId() == null ? null : AlarmAction.getAlarmId().getId());
            sendWebSocket(ecdWsDto);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    //-------------------------------------------------公共功能--------------------------------------------------//


    /**
     * 复制到po
     *
     * @param almAlarmRecord 接到报警po
     * @param item           预案行动dto
     * @param alarmAction    接到报警指令po
     */
    private void copy2Po(SESECDAlmAlarmRecord almAlarmRecord, SESWssEPEmcPlanActionDTO item, SESECDAlarmAction alarmAction, SESWssEPEmergencyPlan planObj) {
        alarmAction.setActionAddress(item.getActionAddress());
        alarmAction.setActionCatogory(item.getActionCatogory());
        alarmAction.setActionDescription(item.getActionDescription());
        alarmAction.setActionName(item.getActionName());
        alarmAction.setAlarmId(almAlarmRecord);
        SystemCode systemCode = new SystemCode();
        systemCode.setId(COMMON_STATE_SYSTEM_CODE_ID);//SESECD_commonState/001：指令状态 未下达
        alarmAction.setCommomState(systemCode);
        log.error("预案id:");
        log.error(planObj.getId() + "");
        alarmAction.setEmergencyPlan(planObj);

        String sectionId = item.getSectionId();
        if (StringUtils.isNotBlank(sectionId)) {
            List<SESEABSetionmember> seseabSetionmemberList = almAlarmRecordDao.findByHql("from " + SESEABSetionmember.JPA_NAME + " where role in( 'SESEAB_Setionmember_role/001','SESEAB_Setionmember_role/003' ) and setionId = '" + sectionId + "'");
            if (CollectionUtils.isNotEmpty(seseabSetionmemberList)) {
                // 此处直接存入应急通信录组组长对象，然后通过前端拖动实现通信录名称，通讯组的负责人，通讯组负责人的手机号在前端的展示
                alarmAction.setOwnPerson(seseabSetionmemberList.get(0));
                //业务不熟，保留原先存储人员逻辑
                SESEABSetionmember seseabSetionmember = seseabSetionmemberList.get(0);
                Staff staff = new Staff();
                String personIdTxt = seseabSetionmember.getPersonIdTxt();
                if (StringUtils.isNotBlank(personIdTxt)) {
                    staff.setId(Long.valueOf(seseabSetionmember.getPersonIdTxt()));
                }
                //alarmAction.setOwner(staff); //作废
                alarmAction.setOwnPerson(seseabSetionmember);//使用中
            }
        }

        SESWssEPEmcPlanAction planAction = new SESWssEPEmcPlanAction();
        planAction.setId(item.getId());
        alarmAction.setInstructions(planAction);
        alarmAction.setTableInfoId(almAlarmRecord.getId());

        //图标库
        if (item.getIconPathGis() != null && item.getIconPathGis().getId() != null) {
            SESGISConfigIconLibrary po = new SESGISConfigIconLibrary();
            po.setId(item.getIconPathGis().getId());
            alarmAction.setIconGis(po);
        }
        //坐标
        alarmAction.setMapPoint(item.getIncidentPlanActLayer());
        //通讯组指令
        alarmAction.setSectionId(item.getSectionId());
        alarmAction.setSort(item.getSort());//排序
    }


    /**
     * 指令po转获取应急行动po
     *
     * @param alarmAction 指令po
     * @return 行动po
     */
    private SESECDEmcAction getSesecdEmcAction(SESECDAlarmAction alarmAction) {
        SESECDEmcAction emcAction = new SESECDEmcAction();
        //指令ID
        emcAction.setCommonId(alarmAction.getId());
        // 行动地点
        emcAction.setActionAddr(alarmAction.getActionAddress());
        // 行动描述
        emcAction.setDescription(alarmAction.getActionDescription());
        // 行动时间
        emcAction.setActionTime(new Date());
        SystemCode systemCode = (SystemCode) systemCodeService.getSystemCode("SESECD_actionState/001");
        // 行动状态
        emcAction.setActionState(systemCode);
        // 行动分类
        emcAction.setActionCatogory(alarmAction.getActionCatogory());
        // 图标
        emcAction.setIconObjGis(alarmAction.getIconGis());
        // 应急事件
        emcAction.setEventId(alarmAction.getAlarmId());
        String mapPoint = alarmAction.getMapPoint();
        String jsonString = "";
        if (!StringUtils.isEmpty(mapPoint)) {
            log.info("指令下达拿到的坐标" + mapPoint);
            mapPointObjDto mapPointObjDto = JSON.parseObject(mapPoint, mapPointObjDto.class);
            mapPointObjDto.setLayerCode("emeActionLayer");
            mapPointObjDto.setOldLayerCode("emeActionLayer");
            jsonString = JSON.toJSONString(mapPointObjDto);
            emcAction.setPoint(jsonString);
        }


        emcAction.setValid(true);
        emcAction.setCid(getCurrentCompanyId());
        Staff currentStaff = (Staff) getCurrentStaff();
        emcAction.setCreateStaffId(currentStaff.getId());
        emcAction.setCreatePositionId(currentStaff.getMainPositionId());
        emcAction.setOwnerPositionId(currentStaff.getMainPositionId());
        emcAction.setOwnPerson(Objects.nonNull(alarmAction.getOwner()) ? alarmAction.getOwner().getName() : null);
        emcAction.setSort(alarmAction.getSort());//排序
        return emcAction;
    }


    /**
     * 保存主要负责人和责任单位和摄像头
     *
     * @param sesecdAlarmAction 应急指令po
     * @param sesecdEmcAction   应急行动po
     */
    private void saveMainPeopleAndMainDepartmentAndCamera(SESECDAlarmAction sesecdAlarmAction, SESECDEmcAction sesecdEmcAction) {
        String sectionIdStr = sesecdAlarmAction.getSectionId();
        log.error("-sectionIdStr:" + sectionIdStr);
        log.error("-sectionIdStr是否为空：" + Objects.isNull(sectionIdStr));
        log.error("SESECDAlarmAction 是否为空：" + Objects.isNull(sesecdAlarmAction));
        log.error("SESECDEmcAction 是否为空：" + Objects.isNull(sesecdEmcAction));
        if (!StringUtils.isEmpty(sectionIdStr)) {
            //责任单位 --- 责任单位默认通讯组
            log.error("sectionIdStr:" + sectionIdStr);
            log.error("sectionIdStr是否为空：" + Objects.isNull(sectionIdStr));
            SESEABEabSetionDTO seseabEabSetionDTO = iseseabEabSetionClient.getSESEABEabSetion(Long.parseLong(sectionIdStr));
            log.error("远程调用SESEABEabSetionDTO的内容：" + seseabEabSetionDTO);
            if (seseabEabSetionDTO != null) {
                SESEABEabSetion seseabEabSetion = BeanUtil.copy(seseabEabSetionDTO, SESEABEabSetion.class);
                SESECDMainDepartment mainDepartment = new SESECDMainDepartment();
                mainDepartment.setActionId(sesecdEmcAction);
                mainDepartment.setOwnDepartmentN(seseabEabSetion);
                mainDepartmentDao.save(mainDepartment);
            } else {
                log.error("通讯组为空");
            }

            //责任人 --- 负责人默认通讯组组长
            List<MemberDTO> memberDTOS = iseseabSetionmemberClient.getMembersBySetionIds(sectionIdStr);
            log.error("memberDTOS是否为空" + CollectionUtils.isEmpty(memberDTOS));
            if (!CollectionUtils.isEmpty(memberDTOS)) {
                //过滤出组长/领导人
                List<MemberDTO> memberDTOList = memberDTOS.stream().filter(memberDTO ->
                        "SESEAB_Setionmember_role/001".equals(memberDTO.getSysRoleCode()) || "SESEAB_Setionmember_role/003".equals(memberDTO.getSysRoleCode()) )
                        .collect(Collectors.toList());
                for (MemberDTO memberDTO : memberDTOList) {
                    SESEABSetionmemberDTO seseabSetionmemberDTO = iseseabSetionmemberClient.getSESEABSetionmember(memberDTO.getId());
                    SESEABSetionmember setionmember = BeanUtil.copy(seseabSetionmemberDTO, SESEABSetionmember.class);
                    SESECDMainPeople mainPeople = new SESECDMainPeople();
                    mainPeople.setActionId(sesecdEmcAction);
                    mainPeople.setOwnPerson(setionmember);
                    mainPeopleDao.save(mainPeople);
                }
            }
        }

        SESEABSetionmember ownPerson1 = sesecdAlarmAction.getOwnPerson();
        if (ownPerson1 != null) {
            SESECDMainPeople mainPeoplePO = new SESECDMainPeople();
            copyToMainPeople(sesecdEmcAction, ownPerson1, mainPeoplePO);
            log.error("sesecdEmcAction:{}内容：", JsonHelper.writeValue(sesecdEmcAction));
            mainPeopleDao.save(mainPeoplePO);
        }

        //指令摄像头
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDAlarmActCamera.JPA_NAME).append(" where valid = 1 and alarmActionId = ?0");
        List<SESECDAlarmActCamera> actCameras = almAlarmRecordDao.findByHql(builder.toString(), new Object[]{sesecdAlarmAction});
        log.error("List<SESECDAlarmActCamera> 是否为空" + CollectionUtils.isEmpty(actCameras));
        actCameras.forEach(c -> {

            SESECDActVideoCamera newCamera = new SESECDActVideoCamera();

            VideoPlayWebCameraConfig camera = c.getVideoId();

            newCamera.setCamera(camera);
            newCamera.setEmcAction(sesecdEmcAction);

            newCamera.setCid(getCurrentCompanyId());
            newCamera.setValid(true);
            newCamera.setCreateStaffId(getCurrentStaff().getId());

            actVideoCameraDao.save(newCamera);
            actVideoCameraDao.flush();
        });

    }

    /**
     * 增加应急行动责任人
     * @param sesecdEmcAction    应急指令
     * @param ownPerson1         应急指令责任人
     * @param mainPeoplePO         应急行动责任人
     */
    private void copyToMainPeople(SESECDEmcAction sesecdEmcAction, SESEABSetionmember ownPerson1, SESECDMainPeople mainPeoplePO) {
        mainPeoplePO.setOwnPerson(ownPerson1);
        mainPeoplePO.setActionId(sesecdEmcAction);
        mainPeoplePO.setCid(getCurrentCompanyId());
        mainPeoplePO.setValid(true);
        mainPeoplePO.setCreateStaffId(getCurrentStaff().getId());
    }


    /**
     * 发送通知
     *
     * @param stringList
     * @param sesecdAlarmAction
     */
    private void NotifyMsg(List<String> stringList, SESECDAlarmAction sesecdAlarmAction) {
        EcdWsDto ecdWsDto = new EcdWsDto();
        ecdWsDto.setActionId(sesecdAlarmAction.getId());
        ecdWsDto.setEventId(sesecdAlarmAction.getAlarmId() == null ? null : sesecdAlarmAction.getAlarmId().getId());

        WebSocketParamDTO webSocketParamDTO = WebSocketParamDTO.createParam(ConstDict.ECD_WEB_SOCKET, JsonUtils.objectToJson(ecdWsDto));
        try {
            defaultNotify.sendWebSocket(webSocketParamDTO);
            log.info("发送消息推送成功************************************************************************");
        } catch (Exception e) {
            log.error("WebSocket Error!", e);
        }

        log.error("开始指令下达通知************************************************************************");

        Map<String, String> modelParam = new HashedMap();
        modelParam.put("accidentName", sesecdAlarmAction.getAlarmId() != null ? sesecdAlarmAction.getAlarmId().getAccidentName() : null);
        modelParam.put("actionAddr", sesecdAlarmAction.getActionAddress());
        modelParam.put("actionTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        modelParam.put("actionState", "待执行");
        modelParam.put("description", sesecdAlarmAction.getActionDescription());
        String message = JSON.toJSON(modelParam).toString();
        log.error("通知内容*{}", message);
        Map<String, Object> extraParams = new HashMap<>(2);
        extraParams.put("title", "应急行动通知");
        executor.execute(() -> {
            sendMessage(ConstDict.MSG_TYPE_ALARM_ACTION, stringList, modelParam, extraParams);
        });

    }

    /**
     * 发送消息
     *
     * @param msgType
     * @param staffCodes
     * @param param      模板填充参数
     * @param extraParam 额外扩展参数
     */
    private void sendMessage(String msgType, List<String> staffCodes, Map<String, String> param, Map<String, Object> extraParam) {
        // 将发送的信息组织到dto通过公用方法发送信息
        try {
            MsgModelDTO modelDTO = MsgModelDTO.build(msgType, staffCodes, param, extraParam);
            notifyFacade.handleNotify(modelDTO);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

    }


    /**
     * 获取指令DTO
     *
     * @param sesecdAlarmAction
     * @param commomDto
     */
    private void copyToCommonDTO(SESECDAlarmAction sesecdAlarmAction, CommomDto commomDto) {
        SystemCode commomState = sesecdAlarmAction.getCommomState();
        // 指令状态
        if (null != commomState) {
            commomDto.setCommomStateId(commomState.getId());
            commomDto.setCommomStateValue(
                    InternationalResource.get(commomState.getValue(), getCurrentLanguage()));
        }
        // 事发地点
        commomDto.setActionAddress(sesecdAlarmAction.getActionAddress());
        // 行动名称
        commomDto.setActionName(sesecdAlarmAction.getActionName());
        // 指令ID
        commomDto.setCommomId(sesecdAlarmAction.getId());
        SystemCode actionCatogory = sesecdAlarmAction.getActionCatogory();
        if (null != actionCatogory) {
            // 行动分类
            commomDto.setActionCatogoryId(actionCatogory.getId());
            // 行动分类
            commomDto.setActionCatogoryValue(
                    InternationalResource.get(actionCatogory.getValue(), getCurrentLanguage()));
        }
        // 行动描述
        commomDto.setActionDescription(sesecdAlarmAction.getActionDescription());
        SESWssEPEmcPlanAction instructions = sesecdAlarmAction.getInstructions();
        if (null != instructions) {
            // 应急预案ID
            commomDto.setPlanId(instructions.getEmergencyPlanId() == null ? null
                    : instructions.getEmergencyPlanId().getId());
        }
        // 应急事件ID
        commomDto.setEventId(
                sesecdAlarmAction.getAlarmId() == null ? null : sesecdAlarmAction.getAlarmId().getId());

        List<SESECDActionOwners> ationOwners = actionOwnersDao.findByHql("from " + SESECDActionOwners.JPA_NAME + " where valid = 1 and actionId = ?0", new Object[]{sesecdAlarmAction});
        if (null != ationOwners && ationOwners.size() > 0) {
            String owners = "";
            for (SESECDActionOwners SESECDActionOwner : ationOwners) {
                SESEABSetionmember owner = SESECDActionOwner.getOwnPerson();
                if (null != owner && null != owner.getPersonId()) {
                    owners += owner.getPersonId().getName() + ",";
                }
            }
            if (owners.length() > 0) {
                owners = owners.substring(0, owners.length() - 1);
            }
            commomDto.setOwners(owners);
        }
        if (sesecdAlarmAction.getIsMapPoint() != null && sesecdAlarmAction.getIsMapPoint()) {
            commomDto.setIsMapPoint(true);
        } else {
            commomDto.setIsMapPoint(false);
        }
    }


    /**
     * websocket推送消息
     *
     * @param websocketMsg
     */
    private void sendWebSocket(EcdWsDto websocketMsg) {
        WebSocketParamDTO param = WebSocketParamDTO.createParam(ConstDict.ECD_WEB_SOCKET,
                Objects.isNull(websocketMsg) ? org.apache.commons.lang.StringUtils.EMPTY : JsonUtils.objectToJson(websocketMsg));
        try {
            defaultNotify.sendWebSocket(param);
            //msgServiceClient.sendWebSocket(param);
        } catch (Exception e) {
            log.error("WebSocket Error!", e);
        }
    }


}