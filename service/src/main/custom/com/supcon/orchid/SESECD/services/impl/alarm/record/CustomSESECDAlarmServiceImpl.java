package com.supcon.orchid.SESECD.services.impl.alarm.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESECD.DTO.BatchDeleteFeatureDTO;
import com.supcon.orchid.SESECD.DTO.DoFeatureDTO;
import com.supcon.orchid.SESECD.DTO.PointDTO;
import com.supcon.orchid.SESECD.client.ICustomEcdGisAnalysisClient;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.*;
import com.supcon.orchid.SESECD.entities.*;
import com.supcon.orchid.SESECD.model.dto.alarm.*;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfoByIdDto.EcdActionState;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfosDto.EcdAction;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfosDto.EcdAction.ActionState;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfosDto.EcdSituation;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfosDto.Instruction;
import com.supcon.orchid.SESECD.model.dto.alarm.EcdInfosDto.Instruction.CommonState;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.dto.common.MsgNotifyDTO;
import com.supcon.orchid.SESECD.model.dto.message.EmergencyActionDto;
import com.supcon.orchid.SESECD.model.dto.message.EmergencySituationDto;
import com.supcon.orchid.SESECD.model.vo.MemberVO;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.common.CoordinateVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.map.GeometryAnalysisVO;
import com.supcon.orchid.SESECD.services.CustomSESECDWsService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmService;
import com.supcon.orchid.SESECD.services.converged.comm.ConvergedCommStrategyService;
import com.supcon.orchid.SESECD.services.converged.comm.CustomSESECDCommunicationService;
import com.supcon.orchid.SESECD.services.impl.outward.screen.ScreenCommandFacade;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESED.entities.SESEDPlan;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import com.supcon.orchid.SESWssEP.client.ISESWssEPEmePeopleGroupClient;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;
import com.supcon.orchid.SESWssER.DTO.ExpertDTO;
import com.supcon.orchid.SESWssER.client.ISESWssERExpertClient;
import com.supcon.orchid.alarm.entities.AlarmRecord;
import com.supcon.orchid.ec.services.MsModuleRelationService;
import com.supcon.orchid.foundation.entities.Document;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.enums.DBTYPE;
import com.supcon.orchid.foundation.services.DataSourceService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.orm.dao.ExtendedGenericDao;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.utils.DateUtils;
import com.supcon.supfusion.systemconfig.api.tenantconfig.annotation.ClassSystemConfigAnno;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service("CustomSESECDAlarmService")
@Transactional
@ClassSystemConfigAnno
public class CustomSESECDAlarmServiceImpl extends BaseServiceImpl<SESECDAlmAlarmRecord>
        implements CustomSESECDAlarmService {

    private static final Double DEFAULT_HEI = 0.02512746D;

    private static final String incidentPlanActLayer = "incidentPlanActLayer";
    private static Map<Integer, String> alarmLevelMap = new HashMap<>();

    static {
        alarmLevelMap.put(1, "SESECD_alarmLevel/001");
        alarmLevelMap.put(2, "SESECD_alarmLevel/002");
        alarmLevelMap.put(3, "SESECD_alarmLevel/003");
        alarmLevelMap.put(4, "SESECD_alarmLevel/004");
    }

    /**
     * 消息通知
     */
    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private CustomSESECDCommunicationService communicationService;

    @Value("${SESECD/SESECD.isPls:}")
    private String isPlsString;

    @Value("${SESECD/SESECD.isRoute:}")
    private String isRouteString;

    @Value("${SESECD/SESECD.isWind:}")
    private String isWindString;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;
    @Autowired
    private SystemCodeService systemCodeService;
    @Autowired
    private ISESWssEPEmePeopleGroupClient emePeopleGroupClient;
    @Autowired
    private MsModuleRelationService msModuleRelationService;
    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;
    @Autowired
    private StaffService staffService;

    @Autowired
    private SESECDAlarmActionDao alarmActionDao;
    @Autowired
    private SESECDEmcSituationDao emcSituationDao;
    @Autowired
    private SESECDEmcActionDao emcActionDao;
    @Autowired
    private SESECDMainPeopleDao mainPeopleDao;
    @Autowired
    private SESECDMainDepartmentDao mainDepartmentDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    DataSourceService dataSourceService;

    @Autowired
    private SESECDVoiceConfigDao voiceConfigDao;

    @Autowired
    private ConvergedCommStrategyService convergedCommStrategyService;

    @Autowired
    private ICustomEcdGisAnalysisClient customEcdGisAnalysisClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ScreenCommandFacade screenCommandFacade;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    /**
     * 处警
     *
     * @param alarmHandleDto
     * @return
     */
    @Override
    public BaseInfoVO changeAlarmState(AlarmHandleDto alarmHandleDto) {
        BaseInfoVO baseInfoVo = new BaseInfoVO();
        baseInfoVo.setSuccess(false);
        if (alarmHandleDto == null) {
            return baseInfoVo;
        }
        if (alarmHandleDto.getAlarmIds() == null || alarmHandleDto.getAlarmIds().size() == 0) {
            return baseInfoVo;
        }
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            baseInfoVo.setErrMsg(InternationalResource.get("SESECD.custom.randon1588166051902"));
            return baseInfoVo;
        }

        SESECDVoiceConfig sesecdVoiceConfig = null;
        if (alarmHandleDto.getVoiceConfigId() != null) {
            sesecdVoiceConfig = voiceConfigDao.get(alarmHandleDto.getVoiceConfigId());
        }

        String process = alarmHandleDto.getProcess();
        Boolean isIncident = alarmHandleDto.getIncident();
        for (Long id : alarmHandleDto.getAlarmIds()) {
            SESECDAlmAlarmRecord AlarmRecord = almAlarmRecordDao.get(id);
            convertToEntityByChangeAlarmDto(alarmHandleDto, sesecdVoiceConfig, process, isIncident, AlarmRecord);
            almAlarmRecordDao.merge(AlarmRecord);

            if (AlarmRecord.getDrillPlanId() != null) {
                Long drillPlanId = AlarmRecord.getDrillPlanId().getId();
                Date happenTime = AlarmRecord.getHappenTime();
                updateDrillPlanState(drillPlanId, happenTime, "SESED_planState/002");
            }

            updateAlarmActionByPlanAction(AlarmRecord);
            if (isIncident) {
                sendNotifyMsgByVoiceConfig(sesecdVoiceConfig, AlarmRecord);
            }

            //处警消息发送
            Map<String, String> map = new HashMap<>();
            map.put("accidentName", AlarmRecord.getAccidentName());
            MsgModelDTO msgModelDTO = MsgModelDTO.build("EmergencyEventCreate", alarmHandleDto.getMesCode(), map, null);
            notifyFacade.handleNotify(msgModelDTO);
        }
        baseInfoVo.setSuccess(true);
        //处警且进入应急处置进入大屏
        if (alarmHandleDto.getIncident()) {
            log.error("changeAlarmState===== 处境进入应急处置大屏");
            screenCommandFacade.on();
        }
        return baseInfoVo;
    }


    /**
     * 关闭应急事件
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean overEvents(List<Long> ids) {
        if (null != ids && ids.size() > 0) {
            for (Long id : ids) {
                SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(id);
                sesecdAlmAlarmRecord.setIsOver(true);
                sesecdAlmAlarmRecord.setOverTime(new Date());
                almAlarmRecordDao.merge(sesecdAlmAlarmRecord);
                publishMsg(sesecdAlmAlarmRecord, true);
                //TODO 关闭应急事件 修改演练计划 状态 SESED_planState/003
                if (null != sesecdAlmAlarmRecord.getDrillPlanId()) {
                    //updateDrillPlanState(sesecdAlmAlarmRecord.getDrillPlanId().getId(),"SESED_planState/003");
                    updateDrillPlanState2(sesecdAlmAlarmRecord.getDrillPlanId().getId(), sesecdAlmAlarmRecord.getHappenTime(), sesecdAlmAlarmRecord.getOverTime(), "SESED_planState/003");

                }

            }
        }
        //事件关闭退出大屏
        screenCommandFacade.off();
        return true;
    }


    /**
     * 保存后推送接警信息到消息中心并且发送给应急通讯小组人员
     *
     * @param almAlarmRecord
     */
    @Override
    public void publishMsg(SESECDAlmAlarmRecord almAlarmRecord, Boolean isEnd) {
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            return;
        }
        if (null != almAlarmRecord) {
            // 事件名称
            String accidentName = almAlarmRecord.getAccidentName();
            // 接警人
            String recevier = almAlarmRecord.getReceiver() == null ? "" : almAlarmRecord.getReceiver().getName();
            // 接警时间
            String Rectime = almAlarmRecord.getRectime() == null ? ""
                    : DateUtils.format(almAlarmRecord.getRectime(), "yyyy-MM-dd HH:mm:ss");
            // 事发时间
            String happenTime = almAlarmRecord.getHappenTime() == null ? ""
                    : DateUtils.format(almAlarmRecord.getHappenTime(), "yyyy-MM-dd HH:mm:ss");
            // 事发地点
            String position = almAlarmRecord.getPosition();
            // 事件描述
            String description = almAlarmRecord.getDescription();
//            List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByProperty("alarmId", almAlarmRecord);
            List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByHql("from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{almAlarmRecord});

            if (null != emePlanObjs && emePlanObjs.size() > 0) {
                String planIds = "";
                for (SESECDEmePlanObj sesecdEmePlanObj : emePlanObjs) {
                    if (null != sesecdEmePlanObj && null != sesecdEmePlanObj.getPlanObj()) {
                        planIds = sesecdEmePlanObj.getPlanObj().getId() + ",";
                    }
                }
                if (planIds.length() > 0) {
                    planIds = planIds.substring(0, planIds.length() - 1);
                }
                List<Long> staffIdsByPlanIds = emePeopleGroupClient.getStaffIdsByPlanIds(planIds,
                        getCurrentCompanyId());
                List<String> staffCodes = new ArrayList<>();
                if (null != staffIdsByPlanIds && staffIdsByPlanIds.size() > 0) {
                    for (Long id : staffIdsByPlanIds) {
                        String code = staffService.get(id).getCode();
                        staffCodes.add(code);
                    }
                }
                AlarmMsgDto alarmMsgDto = new AlarmMsgDto();
                alarmMsgDto.setAccidentName(accidentName);
                alarmMsgDto.setDescription(description);
                alarmMsgDto.setHappenTime(happenTime);
                alarmMsgDto.setPosition(position);
                alarmMsgDto.setRecevier(recevier);
                alarmMsgDto.setRectime(Rectime);
                Map<String, String> param = JsonHelper.convertMap(alarmMsgDto);
                log.error("通知的人员：{}", staffCodes);
                //邮件通知额外参数
                Map<String, Object> extraParams = new HashMap<>(2);
                extraParams.put("title", "接警事件生成通知");
                if (isEnd) {
                    sendMessage(ConstDict.MSG_TYPE_EMERGENCY_EVENT_END, staffCodes, param, extraParams);
                } else {
                    sendMessage(ConstDict.MSG_TYPE_EMERGENCY_EVENT_CREATE, staffCodes, param, extraParams);
                }

            }
        }
    }

    /**
     * 保存后推送应急行动消息推送到消息中心并且发送给应急通讯小组人员
     *
     * @param emcAction
     */
    @Override
    public void publishMessageToAction(SESECDEmcAction emcAction) {
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            return;
        }
        if (null != emcAction) {
            // 发生地点
            String actionAddr = emcAction.getActionAddr();
            // 发生时间
            String actionTime = emcAction.getActionTime() == null ? ""
                    : DateUtils.format(emcAction.getActionTime(), "yyyy-MM-dd HH:mm:ss");
            // 描述
            String description = emcAction.getDescription();
            // 行动状态
            String actionState = emcAction.getActionState().getZhCnValue();
            SESECDAlmAlarmRecord almAlarmRecord = emcAction.getEventId();
            if (null != almAlarmRecord) {
                // 事件名称
                String accidentName = almAlarmRecord.getAccidentName();
//                List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByProperty("alarmId", almAlarmRecord);
                List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByHql("from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{almAlarmRecord});
                if (null != emePlanObjs && emePlanObjs.size() > 0) {
                    String planIds = "";
                    for (SESECDEmePlanObj sesecdEmePlanObj : emePlanObjs) {
                        if (null != sesecdEmePlanObj && null != sesecdEmePlanObj.getPlanObj()) {
                            planIds = sesecdEmePlanObj.getPlanObj().getId() + ",";
                        }
                    }
                    if (planIds.length() > 0) {
                        planIds = planIds.substring(0, planIds.length() - 1);
                    }
                    List<Long> staffIdsByPlanIds = emePeopleGroupClient.getStaffIdsByPlanIds(planIds,
                            getCurrentCompanyId());
                    List<String> staffCodes = new ArrayList<>();
                    if (null != staffIdsByPlanIds && staffIdsByPlanIds.size() > 0) {
                        for (Long id : staffIdsByPlanIds) {
                            String code = staffService.get(id).getCode();
                            staffCodes.add(code);
                        }
                    }
                    EmergencyActionDto actionDto = new EmergencyActionDto();
                    actionDto.setAccidentName(accidentName);
                    actionDto.setActionAddr(actionAddr);
                    actionDto.setActionTime(actionTime);
                    actionDto.setActionState(actionState);
                    actionDto.setDescription(description);
                    Map<String, String> param = JsonHelper.convertMap(actionDto);
                    sendMessage("ReceiveAlarm", staffCodes, param, null);
                }
            }
        }
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
     * 将消息推送给消息中心
     *
     * @param eventId
     * @param staffCodes
     */
    @Override
    public String pulishMessage(Long eventId, List<String> staffCodes) {
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            return InternationalResource.get("SESECD.custom.randon1577699799118", getCurrentLanguage());
        }
        SESECDAlmAlarmRecord almAlarmRecord = almAlarmRecordDao.get(eventId);
        if (null != almAlarmRecord) {
            // 事件名称
            String accidentName = almAlarmRecord.getAccidentName();
            // 接警人
            String recevier = almAlarmRecord.getReceiver() == null ? "" : almAlarmRecord.getReceiver().getName();
            // 接警时间
            String Rectime = almAlarmRecord.getRectime() == null ? ""
                    : DateUtils.format(almAlarmRecord.getRectime(), "yyyy-MM-dd HH:mm:ss");
            // 事发时间
            String happenTime = almAlarmRecord.getHappenTime() == null ? ""
                    : DateUtils.format(almAlarmRecord.getHappenTime(), "yyyy-MM-dd HH:mm:ss");
            // 事发地点
            String position = almAlarmRecord.getPosition();
            // 事件描述
            String description = almAlarmRecord.getDescription();
            try {
                // 将发送的信息组织到dto通过公用方法发送信息
                MsgModelDTO mesModel = new MsgModelDTO();
                mesModel.setMsgType("ReceiveAlarm");
                mesModel.setReceivers(staffCodes);
                AlarmMsgDto alarmMsgDto = new AlarmMsgDto();
                alarmMsgDto.setAccidentName(accidentName);
                alarmMsgDto.setDescription(description);
                alarmMsgDto.setHappenTime(happenTime);
                alarmMsgDto.setPosition(position);
                alarmMsgDto.setRecevier(recevier);
                alarmMsgDto.setRectime(Rectime);
                Map<String, String> param = JsonHelper.convertMap(alarmMsgDto);
                mesModel.setParam(param);
                notifyFacade.handleNotify(mesModel);
                //commFuncMsgServiceClient.sendMsgModel(mesModel);
            } catch (Exception e) {
                log.error(e.toString());
                return InternationalResource.get("SESECD.custom.randon1578320498494", getCurrentLanguage());
            }
        }
        return "SUCCESS";
    }





    /**
     * 通过应急事件ID获取应急事件相关信息及最新一条态势信息及最新一条行动信息
     *
     * @param eventId
     * @return
     */
    @Override
    public EcdInfoByIdDto getecdAndInfoCurrentInfoById(Long eventId) {
        EcdInfoByIdDto ecdInfoByIdDto = null;
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        if (null != sesecdAlmAlarmRecord) {
            ecdInfoByIdDto = new EcdInfoByIdDto();
            // 应急事件名称
            ecdInfoByIdDto.setAccidentName(sesecdAlmAlarmRecord.getAccidentName());
            try {
                // 发生时间
                ecdInfoByIdDto
                        .setHappenTime(DateUtils.format(sesecdAlmAlarmRecord.getHappenTime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                ecdInfoByIdDto.setHappenTime("");
            }
            // 事发单位
            ecdInfoByIdDto.setHpnCompany(
                    sesecdAlmAlarmRecord.getHpnCompany() == null ? "" : sesecdAlmAlarmRecord.getHpnCompany().getName());
            String shql = " from " + SESECDEmcSituation.JPA_NAME
                    + " where valid = 1 and cid = ? and eventId = ? order by occursTime desc";
            List<SESECDEmcSituation> SESECDEmcSituations = emcSituationDao.findByHql(shql,
                    new Object[]{getCurrentCompanyId(), sesecdAlmAlarmRecord});
            if (null != SESECDEmcSituations && SESECDEmcSituations.size() > 0) {
                SESECDEmcSituation sesecdEmcSituation = SESECDEmcSituations.get(0);
                SESGISConfigIconLibrary siconObj = sesecdEmcSituation.getIconObjGis();
                if (null != siconObj) {
                    ecdInfoByIdDto.setsImgUrl(siconObj.getIconUrl());
                }
                // 应急态势描述
                ecdInfoByIdDto.setsDescribtion(sesecdEmcSituation.getDescribtion());
                try {
                    // 应急态势发生时间
                    ecdInfoByIdDto
                            .setOccursTime(DateUtils.format(sesecdEmcSituation.getOccursTime(), "yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    ecdInfoByIdDto.setOccursTime("");
                }
                // 应急态势发生地点
                ecdInfoByIdDto.setPosition(sesecdEmcSituation.getPosition());
            }
            String ahql = " from " + SESECDEmcAction.JPA_NAME
                    + " where  valid = 1 and cid = ? and eventId = ? order by actionTime desc";
            List<SESECDEmcAction> SESECDEmcActions = emcActionDao.findByHql(ahql,
                    new Object[]{getCurrentCompanyId(), sesecdAlmAlarmRecord});
            if (null != SESECDEmcActions && SESECDEmcActions.size() > 0) {
                SESECDEmcAction sesecdEmcAction = SESECDEmcActions.get(0);
                SESGISConfigIconLibrary aiconObj = sesecdEmcAction.getIconObjGis();
                if (null != aiconObj) {
                    ecdInfoByIdDto.setaImgUrl(aiconObj.getIconUrl());
                }
                // 应急行动发生地点
                ecdInfoByIdDto.setActionAddr(sesecdEmcAction.getActionAddr());
                // 应急行动描述
                ecdInfoByIdDto.setaDescription(sesecdEmcAction.getDescription());
                try {
                    // 应急行动发生时间
                    ecdInfoByIdDto
                            .setActionTime(DateUtils.format(sesecdEmcAction.getActionTime(), "yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    ecdInfoByIdDto.setActionTime("");
                }
                // 行动状态
                SystemCode actionState = sesecdEmcAction.getActionState();
                if (null != actionState) {
                    EcdActionState ActionState = new EcdActionState();
                    String id = actionState.getId();
                    ActionState.setId(id);
                    ActionState.setCode(id.substring(0, id.indexOf("/")));
                    switch (id) {
                        case "SESECD_actionState/001":
                            ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155049978",
                                    getCurrentLanguage()));
                            break;
                        case "SESECD_actionState/002":
                            ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155077107",
                                    getCurrentLanguage()));
                            break;
                        case "SESECD_actionState/003":
                            ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155087751",
                                    getCurrentLanguage()));
                            break;
                        case "SESECD_actionState/004":
                            ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155099327",
                                    getCurrentLanguage()));
                            break;
                        default:
                            ActionState.setValue("");
                            break;
                    }
                    ecdInfoByIdDto.setActionState(ActionState);
                }
            }
        }
        return ecdInfoByIdDto;
    }

    /**
     * 通过应急事件ID获取应急事件信息、应急指令信息、应急态势信息及应急行动信息
     *
     * @param eventId
     * @return
     */
    @Override
    public EcdInfosDto getEcdInfosByEventId(Long eventId) {
        EcdInfosDto ecdInfosDto = null;
        Instruction instruction;
        List<Instruction> instructions;
        EcdAction ecdAction;
        List<EcdAction> ecdActions;
        EcdSituation ecdSituation;
        List<EcdSituation> ecdSituations;
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        if (null != sesecdAlmAlarmRecord) {
            String incidentImgUrl = getImgUrl("incidentLayer");
            ecdInfosDto = new EcdInfosDto();
            ecdInfosDto.setIsOver(sesecdAlmAlarmRecord.getIsOver());
            log.error("该事件是否已结束：{}", sesecdAlmAlarmRecord.getIsOver());
            ecdInfosDto.setImgUrl(incidentImgUrl);
            // 应急事件名称
            ecdInfosDto.setAccidentName(sesecdAlmAlarmRecord.getAccidentName());
            try {
                // 发生时间
                ecdInfosDto
                        .setHappenTime(DateUtils.format(sesecdAlmAlarmRecord.getHappenTime(), "yyyy-MM-dd HH:mm:ss"));
            } catch (Exception e) {
                ecdInfosDto.setHappenTime("");
            }
            // 事发单位
            ecdInfosDto.setHpnCompany(
                    sesecdAlmAlarmRecord.getHpnCompany() == null ? "" : sesecdAlmAlarmRecord.getHpnCompany().getName());
            // ID
            ecdInfosDto.setId(sesecdAlmAlarmRecord.getId());
            // 空间ID
            ecdInfosDto.setGisAccidentId(SESECDAlmAlarmRecord.MODEL_CODE + "_" + sesecdAlmAlarmRecord.getId());

            String ihql = " from " + SESECDAlarmAction.JPA_NAME + " where valid = 1 " + " and alarmId = ? order by id";
            List<SESECDAlarmAction> alarmActions = alarmActionDao.findByHql(ihql, new Object[]{sesecdAlmAlarmRecord});
            if (null != alarmActions && alarmActions.size() > 0) {
                instructions = new ArrayList<>();
                int i = 1;
                for (SESECDAlarmAction sesecdAlarmAction : alarmActions) {
                    instruction = new Instruction();
                    SESGISConfigIconLibrary iconGis = sesecdAlarmAction.getIconGis();
                    if (null != iconGis) {
                        instruction.setImgUrl(iconGis.getIconUrl());
                    }
                    // ID
                    instruction.setId(sesecdAlarmAction.getId());
                    // 空间ID
                    instruction.setGisInstructionId(SESECDAlarmAction.MODEL_CODE + "_" + sesecdAlarmAction.getId());
                    // 行动地点
                    instruction.setAddress(sesecdAlarmAction.getActionAddress());
                    // 行动分类
                    SystemCode actionCatogory = sesecdAlarmAction.getActionCatogory();
                    if (null != actionCatogory) {
                        String id = actionCatogory.getId();
                        switch (id) {
                            case "SESWssEP_EmcPlanAction_actionCategory/001":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506060643", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/002":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506564163", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/003":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506590154", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/004":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506627731", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/005":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506820817", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/006":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506837884", getCurrentLanguage()));
                                break;
                            case "SESWssEP_EmcPlanAction_actionCategory/007":
                                instruction.setCatogory(InternationalResource.get("SESWssEP.systemCodevalue.randon1572506857884", getCurrentLanguage()));
                                break;
                            default:
                                instruction.setCatogory("");
                                break;
                        }
                    }
                    // 行动描述
                    instruction.setDescription(sesecdAlarmAction.getActionDescription());
                    // 行动名称
                    instruction.setName(sesecdAlarmAction.getActionName());
                    // 行动状态
                    SystemCode commomState = sesecdAlarmAction.getCommomState();
                    if (null != commomState) {
                        CommonState CommonState = new CommonState();
                        String id = commomState.getId();
                        CommonState.setId(id);
                        CommonState.setCode(id.substring(0, id.indexOf("/")));
                        switch (id) {
                            case "SESECD_commonState/001":
                                CommonState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1578378078402",
                                        getCurrentLanguage()));
                                break;
                            case "SESECD_commonState/002":
                                CommonState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1578378088246",
                                        getCurrentLanguage()));
                                break;
                            case "SESECD_commonState/003":
                                CommonState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155077107",
                                        getCurrentLanguage()));
                                break;
                            case "SESECD_commonState/004":
                                CommonState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1578378106993",
                                        getCurrentLanguage()));
                                break;
                            default:
                                CommonState.setValue("");
                                break;
                        }
                        instruction.setCommonState(CommonState);
                    }
                    instruction.setRowNum(i);
                    i++;
                    instructions.add(instruction);
                }
                ecdInfosDto.setInstruction(instructions);
            }

            //查询应急行动
            String ahql = " from " + SESECDEmcAction.JPA_NAME + " where valid = 1 " + " and eventId = ? order by actionTime";
            List<SESECDEmcAction> SESECDEmcActions = emcActionDao.findByHql(ahql, new Object[]{sesecdAlmAlarmRecord});
            if (null != SESECDEmcActions && SESECDEmcActions.size() > 0) {
                ecdActions = new ArrayList<>();
                int i = 1;
                for (SESECDEmcAction sesecdEmcAction : SESECDEmcActions) {
                    ecdAction = new EcdAction();
                    SESGISConfigIconLibrary iconObj = sesecdEmcAction.getIconObjGis();
                    if (null != iconObj) {
                        ecdAction.setImgUrl(iconObj.getIconUrl());
                    }
                    // 行动地点
                    ecdAction.setActionAddr(sesecdEmcAction.getActionAddr());
                    try {
                        // 行动时间
                        ecdAction.setActionTime(sesecdEmcAction.getActionTime() == null ? ""
                                : DateUtils.format(sesecdEmcAction.getActionTime(), "yyyy-MM-dd HH:mm:ss"));
                    } catch (Exception e) {
                        ecdAction.setActionTime("");
                    }
                    // 行动描述
                    ecdAction.setDescription(sesecdEmcAction.getDescription());
                    // ID
                    ecdAction.setId(sesecdEmcAction.getId());
                    // 空间ID
                    ecdAction.setGisEcdActionId(SESECDEmcAction.MODEL_CODE + "_" + sesecdEmcAction.getId());
                    // 行动状态
                    SystemCode actionState = sesecdEmcAction.getActionState();
                    if (null != actionState) {
                        ActionState ActionState = new ActionState();
                        String id = actionState.getId();
                        ActionState.setId(id);
                        ActionState.setCode(id.substring(0, id.indexOf("/")));
                        switch (id) {
                            case "SESECD_actionState/001":
                                ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155049978", getCurrentLanguage()));
                                break;
                            case "SESECD_actionState/002":
                                ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155077107", getCurrentLanguage()));
                                break;
                            case "SESECD_actionState/003":
                                ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155087751", getCurrentLanguage()));
                                break;
                            case "SESECD_actionState/004":
                                ActionState.setValue(InternationalResource.get("SESECD.systemCodevalue.randon1577155099327", getCurrentLanguage()));
                                break;
                            default:
                                ActionState.setValue("");
                                break;
                        }
                        ecdAction.setActionState(ActionState);
                    }

                    // 负责人
//                    List<SESECDMainPeople> mainPeople = mainPeopleDao.findByProperty("actionId", sesecdEmcAction);
                    List<SESECDMainPeople> mainPeople = mainPeopleDao.findByHql("from " + SESECDMainPeople.JPA_NAME + " where valid = 1 and actionId = ?0", new Object[]{sesecdEmcAction});
                    if (null != mainPeople && mainPeople.size() > 0) {
                        String owners = "";
                        for (SESECDMainPeople people : mainPeople) {
                            if (null != people.getOwnPerson() && null != people.getOwnPerson().getPersonId()) {
                                owners += people.getOwnPerson().getPersonId().getName() + ",";
                            }
                        }
                        if (owners.length() > 0) {
                            owners = owners.substring(0, owners.length() - 1);
                        }
                        ecdAction.setOwners(owners);
                    }
                    // 负责单位
//                    List<SESECDMainDepartment> mainDepartment = mainDepartmentDao.findByProperty("actionId",
//                            sesecdEmcAction);
                    List<SESECDMainDepartment> mainDepartment = mainDepartmentDao.findByHql("from " + SESECDMainDepartment.JPA_NAME + " where valid = 1 and actionId = ?0", new Object[]{sesecdEmcAction});
                    if (null != mainDepartment && mainDepartment.size() > 0) {
                        String ownDepartments = "";
                        for (SESECDMainDepartment department : mainDepartment) {
                            if (null != department.getOwnDepartmentN() && null != department.getOwnDepartmentN().getBelongDepartment()) {
                                ownDepartments += department.getOwnDepartmentN().getBelongDepartment().getName() + ",";
                            }
                        }
                        if (ownDepartments.length() > 0) {
                            ownDepartments = ownDepartments.substring(0, ownDepartments.length() - 1);
                        }
                        ecdAction.setOwnDepartments(ownDepartments);
                    }
                    ecdAction.setRowNum(i);
                    i++;
                    ecdActions.add(ecdAction);
                }
                ecdInfosDto.setEcdAction(ecdActions);
            }

            //查询态势
            String shql = " from " + SESECDEmcSituation.JPA_NAME + " where valid = 1 " + " and eventId = ? order by occursTime";
            List<SESECDEmcSituation> situationPOs = emcSituationDao.findByHql(shql, new Object[]{sesecdAlmAlarmRecord});

            if (null != situationPOs && situationPOs.size() > 0) {
                log.error("该事件共查询到{}个态势", situationPOs.size());
                ecdSituations = new ArrayList<>();
                int i = 1;
                for (SESECDEmcSituation sesecdEmcSituation : situationPOs) {
                    if (Objects.isNull(sesecdEmcSituation.getSituationType()) || "SESECD_situation_type/002".equals(sesecdEmcSituation.getSituationType().getId())) {
                        log.error("该态势未发布,不展示");
                        continue;
                    }
                    ecdSituation = new EcdSituation();
                    SESGISConfigIconLibrary iconObj = sesecdEmcSituation.getIconObjGis();
                    if (null != iconObj) {
                        ecdSituation.setImgUrl(iconObj.getIconUrl());
                    }
                    // ID
                    ecdSituation.setId(sesecdEmcSituation.getId());
                    // 空间ID
                    ecdSituation.setGisSituationId(SESECDEmcSituation.MODEL_CODE + "_" + sesecdEmcSituation.getId());
                    // 发生地点
                    ecdSituation.setPosition(sesecdEmcSituation.getPosition());
                    // 态势描述
                    ecdSituation.setDescription(sesecdEmcSituation.getDescribtion());
                    try {
                        // 发生时间
                        ecdSituation.setOccursTime(DateUtils.format(sesecdEmcSituation.getOccursTime(), "yyyy-MM-dd HH:mm:ss"));
                    } catch (Exception e) {
                        ecdSituation.setOccursTime("");
                    }
                    // 受伤人数
                    ecdSituation.setWoundedPerson(sesecdEmcSituation.getWoundedPerson());
                    // 死亡人数
                    ecdSituation.setDeathPerson(sesecdEmcSituation.getDeathPerson());
                    ecdSituation.setRowNum(i);
                    i++;
                    ecdSituations.add(ecdSituation);
                }
                ecdInfosDto.setEcdSituation(ecdSituations);
            } else {
                log.error("该事件没有应急态势");
            }

        }
        ecdInfosDto.setIsPls("true".equals(isPlsString));
        ecdInfosDto.setIsRoute("true".equals(isRouteString));
        ecdInfosDto.setIsWind("true".equals(isWindString));
        return ecdInfosDto;
    }

    /**
     * 获取地图展示所需要的图标
     *
     * @return
     */
    private String getImgUrl(String layerCode) {
        String imgUrl = "";
        String sql = "SELECT DEFAULT_ICON_PATH FROM SESGIS_CUSTOM_THEMATICS WHERE CODE = ? AND VALID = 1";
        List list = alarmActionDao.createNativeQuery(sql, new Object[]{layerCode}).list();
        if (null != list && list.size() > 0) {
            Object object = list.get(0);
            if (null != object) {
                imgUrl = object.toString();
            }
        }
        return imgUrl;
    }

    /**
     * 接警事件关联了 演练计划，将演练计划状态更改为 执行中
     *
     * @param planId
     */
    @Override
    public void updateDrillPlanState(Long planId, Date happenTime) {
        updateDrillPlanState(planId, happenTime, "SESED_planState/002");
    }

    //--------------------------------------------------公共功能----------------------------------------------------------


    private void updateDrillPlanState(Long planId, Date happenTime, String planState) {
        log.error("进入设置演练时间方法");
        try {
            List<Long> idList = new ArrayList<Long>();
            idList.add(planId);
            String formatHappenTime = null;
            if (happenTime != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formatHappenTime = sdf.format(happenTime);
            }
            DBTYPE dataType = null;
            try {
                dataType = dataSourceService.checkDBType();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = null;
            if (DBTYPE.ORACLE.equals(dataType)) {
                sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '" + planState + "',begTime = TO_DATE('" + formatHappenTime + "','YY/MM/DD HH24:MI:SS') where id in (:businessKeys)";
            } else {
                sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '" + planState + "',begTime = '" + formatHappenTime + "' where id in (:businessKeys)";
            }
            //String sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '"+planState+"',begTime = '"+formatHappenTime+ "' where id in (:businessKeys)";
            //String sql = "update " + SESEDPlan.TABLE_NAME + " SET PLAN_STATE = '"+planState+"',BEG_TIME = '"+formatHappenTime+ "' where id in (:businessKeys)";
            log.info("演练计划SQl语句*******************************************" + sql);
            Query query = alarmActionDao.createQuery(sql);
            query.setParameterList("businessKeys", idList);
            query.executeUpdate();
        } catch (Exception e) {
            log.info("updateDrillPlanState Exception:{}", e.getMessage());
            e.printStackTrace();
        }
        log.error("退出设置演练时间方法");
    }


    private void updateDrillPlanState2(Long planId, Date happenTime, Date overTime, String planState) {
        try {
            List<Long> idList = new ArrayList<Long>();
            idList.add(planId);

            String formatOverTime = null;
            if (overTime != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formatOverTime = sdf.format(overTime);
            }
            DBTYPE dataType = null;
            try {
                dataType = dataSourceService.checkDBType();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = null;
            if (DBTYPE.ORACLE.equals(dataType)) {
                sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '" + planState + "',endTime =TO_DATE('" + formatOverTime + "','YY/MM/DD HH24:MI:SS') where id in (:businessKeys)";
            } else {
                sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '" + planState + "',endTime = '" + formatOverTime + "' where id in (:businessKeys)";
            }
            //String sql = "update " + SESEDPlan.JPA_NAME + " SET planState = '"+planState+"' ,begTime = '"+happenTime+ " ',endTime = '"+formatOverTime+"' where id in (:businessKeys)";
            log.info("演练计划SQl语句*******************************************" + sql);
            Query query = alarmActionDao.createQuery(sql);
            query.setParameterList("businessKeys", idList);
            query.executeUpdate();
        } catch (Exception e) {
            log.info("updateDrillPlanState Exception:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 通过语音配置发送消息
     *
     * @param sesecdVoiceConfig 语音配置
     * @param AlarmRecord       接警记录
     */
    private void sendNotifyMsgByVoiceConfig(SESECDVoiceConfig sesecdVoiceConfig, SESECDAlmAlarmRecord AlarmRecord) {
        if (sesecdVoiceConfig == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDEmePlanObj.JPA_NAME).append(" where valid = 1 and alarmId = ?0");
        List<SESECDEmePlanObj> SESECDEmePlanObjs = voiceConfigDao.findByHql(builder.toString(), new Object[]{AlarmRecord});

        List<String> memberMobiles = new ArrayList<>();
        try {
            String planIds = SESECDEmePlanObjs.stream().map(v -> v.getPlanObj().getId().toString()).collect(Collectors.joining(","));
            List<MemberDTO> members = emePeopleGroupClient.getMembersByPlanIds(planIds);
            memberMobiles = members.stream().map(v -> v.getMobile()).collect(Collectors.toList());
            for (MemberDTO memberDTO :
                    members) {
                if (memberDTO.getTerminalNumber() != null && !memberDTO.getTerminalNumber().trim().isEmpty()) {
                    memberMobiles.add(memberDTO.getTerminalNumber());
                } else {
                    if (memberDTO.getMobile() != null && !memberDTO.getMobile().trim().isEmpty()) {
                        memberMobiles.add(memberDTO.getMobile());
                    }
                    if (memberDTO.getTelephone() != null && !memberDTO.getTelephone().trim().isEmpty()) {
                        memberMobiles.add(memberDTO.getTelephone());
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        if (!memberMobiles.isEmpty()) {
            //TODO 发送消息
            MsgNotifyDTO msgNotifyDTO = new MsgNotifyDTO();
            msgNotifyDTO.setDestCaller(memberMobiles);
            if (sesecdVoiceConfig.getCustomContnet() != null && sesecdVoiceConfig.getCustomContnet()) {
                msgNotifyDTO.setSystemVoiceIndex(-1);
            } else {
                msgNotifyDTO.setSystemVoiceIndex(0);
            }
            msgNotifyDTO.setContent(sesecdVoiceConfig.getContentText());

            try {
                log.error(">>> notifyMsg parm:" + new ObjectMapper().writeValueAsString(msgNotifyDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            convergedCommStrategyService.notifyMsg(null, msgNotifyDTO);
        }
    }

    @Autowired
    private CustomSESECDSavePointService savePointService;

    /**
     * 查找关联指令，把关系的预案坐标和图标更新到接警行动中来
     *
     * @param AlarmRecord 接警记录
     */
    private void updateAlarmActionByPlanAction(SESECDAlmAlarmRecord AlarmRecord) {
        List<SESECDAlarmAction> sESECDAlarmActions = alarmActionDao.findByHql("from " + SESECDAlarmAction.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{AlarmRecord});
        if (null != sESECDAlarmActions && sESECDAlarmActions.size() > 0) {
            for (SESECDAlarmAction sesecdAlarmAction : sESECDAlarmActions) {
                SESWssEPEmcPlanAction instructions = sesecdAlarmAction.getInstructions();
                if (null == instructions) {
                    continue;
                }
                sesecdAlarmAction.setMapPoint(instructions.getIncidentPlanActLayer() == null ? null
                        : instructions.getIncidentPlanActLayer());
                SESGISConfigIconLibrary iconPath = instructions.getIconPathGis();
                sesecdAlarmAction.setIconGis(iconPath);
                alarmActionDao.save(sesecdAlarmAction);
                log.info("保存坐标成功" + sesecdAlarmAction.getId());
//                            String instrucLayer = instructions.getInstrucLayer();
//                            MapPointDto mapPointDto = JSON.parseObject(instrucLayer, MapPointDto.class);
//                            mapPointDto.setId(sesecdAlarmAction.getId());
                //空间数据库
                log.error("开始保存点");
                if (sesecdAlarmAction.getMapPoint() != null) {
                    String spatialId = SESECDAlarmAction.MODEL_CODE + "_" + sesecdAlarmAction.getId();
                    PointVO pointVO = PointVO.PointPO2VO(sesecdAlarmAction.getMapPoint(), SESECDAlarmAction.MODEL_CODE + "_", sesecdAlarmAction.getId().toString());
                    try {
                        savePointService.savePoint2PG(spatialId, pointVO, incidentPlanActLayer);//应急事件 图层
                    } catch (Exception e) {
                        log.error("空间数据库保存失败！");
                        throw new RuntimeException(e);
                    }
                }


                log.info("空间数据库" + sesecdAlarmAction.getId());
            }
        }
    }


    /**
     * 转换到PO实体 - 处理接警
     *
     * @param alarmHandleDto    前端数据
     * @param sesecdVoiceConfig 报警语音配置
     * @param process           设置处理记录
     * @param isIncident        是否应急事件
     * @param AlarmRecord       接警记录
     */
    private void convertToEntityByChangeAlarmDto(AlarmHandleDto alarmHandleDto, SESECDVoiceConfig sesecdVoiceConfig, String process, Boolean isIncident, SESECDAlmAlarmRecord AlarmRecord) {
        AlarmRecord.setProcess(process);
        AlarmRecord.setIsIncident(isIncident);
        if (alarmHandleDto.getProcessState() != null && !alarmHandleDto.getProcessState().isEmpty()) {
            SystemCode systemCode1 = new SystemCode();
            systemCode1.setId(alarmHandleDto.getProcessState());
            AlarmRecord.setProcessState(systemCode1);
        }
        if (sesecdVoiceConfig != null) {
            AlarmRecord.setVoiceConfigId(sesecdVoiceConfig);
        }
        if (alarmHandleDto.getEventType() != null && !alarmHandleDto.getEventType().isEmpty()) {
            SystemCode systemCode2 = new SystemCode();
            systemCode2.setId(alarmHandleDto.getEventType());
            AlarmRecord.setEventType(systemCode2);
        }
        if (alarmHandleDto.getAlarmLevel() != null && !alarmHandleDto.getAlarmLevel().isEmpty()) {
            SystemCode systemCode_level = new SystemCode();
            systemCode_level.setId(alarmHandleDto.getAlarmLevel());
            AlarmRecord.setAlarmLevel(systemCode_level);
        }
        SESEDPlan sesedPlan = new SESEDPlan();
        sesedPlan.setId(alarmHandleDto.getDrillPlanId());
        AlarmRecord.setDrillPlanId(sesedPlan);
    }

    /**
     * 改变事件等级（报警等级）
     *
     * @param changeLevelDTO
     * @return
     */
    @Override
    public SystemCode changeLevel(ChangeLevelDTO changeLevelDTO) {
        SESECDAlmAlarmRecord record = almAlarmRecordDao.get(changeLevelDTO.getEventId());
        SystemCode systemCode = record.getAlarmLevel();
        if (systemCode.getCode().equalsIgnoreCase("001") && changeLevelDTO.getOperation() == 1) {
            return systemCode;
        }
        if (systemCode.getCode().equalsIgnoreCase("004") && changeLevelDTO.getOperation() == 2) {
            return systemCode;
        }
        int intLevel = Integer.parseInt(systemCode.getCode());
        //降级
        if (changeLevelDTO.getOperation().equals(1)) {
            intLevel = Integer.parseInt(systemCode.getCode()) - 1;
        } else if (changeLevelDTO.getOperation().equals(2)) {
            intLevel = Integer.parseInt(systemCode.getCode()) + 1;
        }
        String code = alarmLevelMap.get(intLevel);
        SystemCode systemCodeByID = (SystemCode) systemCodeService.getSystemCodeByID(code);
        record.setAlarmLevel(systemCodeByID);
        almAlarmRecordDao.update(record);
        //等级操作日志
        ChangeLogDTO changeLogDTO = ChangeLogDTO.builder().content("【事件等级】： " + InternationalResource.get(systemCodeByID.getValue(), getCurrentLanguage())).type(ChangeLogBizTypeEnum.LEVEL.getCode()).eventId(record.getId()).build();
        ChangeLogEvent changeEvent = new ChangeLogEvent(this, changeLogDTO);
        applicationContext.publishEvent(changeEvent);
        return systemCodeByID;
    }

    /**
     * 接警确认
     * @param alarmCheckDTO
     * @return
     */
    @Override
    public void alarmCheck(AlarmCheckDTO alarmCheckDTO) {
        log.error("接警参数： " + JSONObject.toJSONString(alarmCheckDTO));
        Long alarmId = alarmCheckDTO.getAlarmId();
        SESECDAlmAlarmRecord almAlarmRecord = almAlarmRecordDao.get(alarmId);
        //响应等级
        if (StringUtils.isNotBlank(alarmCheckDTO.getResponseLevel())) {
            ISystemCode serviceSystemCode = systemCodeService.getSystemCodeByID(alarmCheckDTO.getResponseLevel());
            almAlarmRecord.setResponseLevel((SystemCode) serviceSystemCode);

            //事件等级默认与响应等级同一等级
            String code = serviceSystemCode.getCode();
            String alarmCode = "SESECD_alarmLevel/";
            alarmCode += code;
            log.error("事件等级code拼接id: " + alarmCode);
            ISystemCode alarmSysCode = systemCodeService.getSystemCodeByID(alarmCode);
            almAlarmRecord.setAlarmLevel((SystemCode) alarmSysCode);
        }
        //事件分类
        if (StringUtils.isNotBlank(alarmCheckDTO.getEvType())) {
            ISystemCode serviceSystemCode = systemCodeService.getSystemCodeByID(alarmCheckDTO.getEvType());
            almAlarmRecord.setEvTypes((SystemCode) serviceSystemCode);
        }
        //设置为应急事件
        almAlarmRecord.setIsIncident(true);
        //设置事件类型
        ISystemCode eventType = systemCodeService.getSystemCodeByID("SESECD_eventType/001");
        almAlarmRecord.setEventType((SystemCode) eventType);
        updateAlarmActionByPlanAction(almAlarmRecord);
        almAlarmRecordDao.update(almAlarmRecord);
        //处警且进入应急处置进入大屏
        log.error("changeAlarmState===== 处境进入应急处置大屏");
        screenCommandFacade.on();

    }
}
