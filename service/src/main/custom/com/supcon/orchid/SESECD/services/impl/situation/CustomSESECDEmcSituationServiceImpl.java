package com.supcon.orchid.SESECD.services.impl.situation;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDDispatConfigDao;
import com.supcon.orchid.SESECD.daos.SESECDEmcSituationDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.*;
import com.supcon.orchid.SESECD.model.dto.alarm.SituationDto;
import com.supcon.orchid.SESECD.model.dto.alarm.SituationInfoDto;
import com.supcon.orchid.SESECD.model.dto.message.EcdWsDto;
import com.supcon.orchid.SESECD.model.dto.message.EmergencySituationDto;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmService;
import com.supcon.orchid.SESECD.services.notify.DefaultNotifyImpl;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDEmcSituationService;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.SESWssEP.client.ISESWssEPEmePeopleGroupClient;
import com.supcon.orchid.SesCommonFunc.dto.WebSocketParamDTO;
import com.supcon.orchid.ec.services.MsModuleRelationService;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.utils.DateUtils;
import com.supcon.orchid.utils.JsonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 应急态势
 */
@Service
@Transactional
public class CustomSESECDEmcSituationServiceImpl extends BaseServiceImpl<SESECDEmcSituation> implements CustomSESECDEmcSituationService {

    @Autowired
    private SESECDEmcSituationDao emcSituationDao;
    @Autowired
    private DefaultNotifyImpl defaultNotify;
    @Autowired
    private NotifyFacade notifyFacade;
    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private SESECDDispatConfigDao sesecdDispatConfigDao;

    @Autowired
    private MsModuleRelationService msModuleRelationService;

    @Autowired
    private ISESWssEPEmePeopleGroupClient emePeopleGroupClient;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomSESECDAlarmRecordService customSESECDAlarmRecordService;

    @Autowired
    private SqlUtils sqlUtils;

    @Autowired
    private SESECDEmcSituationDao situationDao;

    /**
     * 通过应急事件ID获取应急态势信息
     *
     * @param eventId
     * @return
     */
    @Override
    public BaseInfoVO getEmcStiuationInfo(Long eventId) {
        List<SituationDto> situationDtos = null;
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        String hql = " from " + SESECDEmcSituation.JPA_NAME + " where valid = 1 and cid = ? and eventId = ?";
        List<SESECDEmcSituation> SESECDEmcSituations = emcSituationDao.findByHql(hql,
                new Object[]{getCurrentCompanyId(), sesecdAlmAlarmRecord});
        log.info("全部态势应急态势数量" + SESECDEmcSituations.size());
        if (null != SESECDEmcSituations && SESECDEmcSituations.size() > 0) {
            List<SESECDDispatConfig> sesecdDispatConfigs = sesecdDispatConfigDao.findByHql("from " + SESECDDispatConfig.JPA_NAME + " where valid = 1 and cid = " + getCurrentCompanyId());
            List<String> longList = new ArrayList<>();
            if (null != sesecdDispatConfigs && sesecdDispatConfigs.size() > 0) {
                for (SESECDDispatConfig sesecdDispatConfig : sesecdDispatConfigs) {
                    String name = sesecdDispatConfig.getPerson().getName();
                    log.info("调度员姓名" + name);
                    longList.add(name);
                }
            }
            log.info("调度员的人数" + longList.size());
            boolean contains = longList.contains(getCurrentStaff().getName());
            log.info("当前登录人ID" + getCurrentStaff().getId());
            log.info("当前登录人姓名" + getCurrentStaff().getName());
            log.info("是否是调度员" + contains);
            SituationDto situationDto = null;
            situationDtos = new ArrayList<>();
            for (SESECDEmcSituation sesecdEmcSituation : SESECDEmcSituations) {
                if (!contains && "SESECD_situation_type/002".equals(sesecdEmcSituation.getSituationType().getId())) {
                    continue;
                }
                //                if(Objects.isNull(sesecdEmcSituation.getSituationType()) || "SESECD_situation_type/002".equals(sesecdEmcSituation.getSituationType().getId())){
//                    continue;
//                }
                situationDto = new SituationDto();
                //态势状态
                //态势状态
//                if ("SESECD_situation_type/002".equals(sesecdEmcSituation.getSituationType().getId())){
//                    situationDto.setSituationType("未发布");
//                }else {
//                    situationDto.setSituationType("已发布");
//                }
                situationDto.setSituationType(sesecdEmcSituation.getSituationType().getId());
                // 态势描述
                situationDto.setDescribtion(sesecdEmcSituation.getDescribtion());
                try {
                    // 发生时间
                    situationDto
                            .setOccursTime(DateUtils.format(sesecdEmcSituation.getOccursTime(), "yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    // 发生时间
                    situationDto.setOccursTime("");
                }

                // 发生地点
                //situationDto.setPosition(sesecdEmcSituation.getPosition());
                situationDto.setPosition(Objects.nonNull(sesecdEmcSituation.getCommonAddress()) ? sesecdEmcSituation.getCommonAddress().getName() : null);
                Staff reportPerson = sesecdEmcSituation.getReportPerson();
                if (null != reportPerson) {
                    // 上报人
                    situationDto.setReportPerson(reportPerson.getId());
                    situationDto.setReportPersonName(reportPerson.getName());
                }

                // 应急事件ID
                situationDto.setEventId(
                        sesecdEmcSituation.getEventId() == null ? null : sesecdEmcSituation.getEventId().getId());
                // 应急态势ID
                situationDto.setStateId(sesecdEmcSituation.getId());
                situationDtos.add(situationDto);
            }
        }
        return BaseInfoVO.ok(situationDtos);
    }


    @Override
    public BaseInfoVO getEmcSituationInfoByEventId(Long eventId) {
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        Assert.notNull(sesecdAlmAlarmRecord, "eventId can not find entity");
        String hql = " from " + SESECDEmcSituation.JPA_NAME + " where valid = 1 and cid = ? and eventId = ?";
        List<SESECDEmcSituation> SESECDEmcSituations = emcSituationDao.findByHql(hql,
                new Object[]{getCurrentCompanyId(), sesecdAlmAlarmRecord});
        if (SESECDEmcSituations.isEmpty()) {
            return BaseInfoVO.ok(Collections.emptyList());
        }
        List<SituationInfoDto> situationDtos = new ArrayList<>();
        for (SESECDEmcSituation sesecdEmcSituation : SESECDEmcSituations) {
            SituationInfoDto situationDto = new SituationInfoDto();
            copyToDTO(eventId, sesecdEmcSituation, situationDto);
            situationDtos.add(situationDto);
        }
        return BaseInfoVO.ok(situationDtos);
    }


    @Override
    public Result<List<SESECDRecorSituation>> getRelationSituationByAlarmRecordId(Long alarmRecordId) {
        SESECDAlmAlarmRecord alarmRecord = almAlarmRecordDao.load(alarmRecordId);
        String sql = "from " + SESECDEmcSituation.JPA_NAME + " where valid = 1   " + sqlUtils.getSqlPartByCID() + " and eventId = ?0";
        List<SESECDEmcSituation> situationList = situationDao.findByHql(sql, new Object[]{alarmRecord});
        if (Objects.isNull(situationList) || situationList.size() == 0) {
            return new Result<>();
        }
        List<SESECDRecorSituation> list = new ArrayList<>();
        for (SESECDEmcSituation situation : situationList) {
            SESECDRecorSituation recordSituation = getRecordSituation(situation);
            list.add(recordSituation);
        }
        return Result.data(list);
    }

    //*******************************************************自定义事件************************************************//
    @Override
    public void customAfterSaveEmcSituation(SESECDEmcSituation emcSituation, Object... objects) {
        log.error("发送保存态势消息被调用");
        emcSituationDao.flush();
        emcSituationDao.clear();
        log.error("获取应急态势前");
//        SESECDEmcSituation EmcSituation = emcSituationDao.get(emcSituation.getId());
        SESECDEmcSituation EmcSituation = emcSituation;
        log.error("获取应急态势后");
        //try {
        //    customSESECDAlarmService.publishMessageToSituation(EmcSituation);
        //} catch (Exception e) {
        //    log.error(e.getMessage(), e);
        //}

        try {
            EcdWsDto ecdWsDto = new EcdWsDto();
            ecdWsDto.setSituationId(EmcSituation.getId());
            ecdWsDto.setEventId(EmcSituation.getEventId() == null ? null : EmcSituation.getEventId().getId());
            executor.execute(() -> {
                sendWebSocket(ecdWsDto);
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        List<String> staffCodes = customSESECDAlarmRecordService.getStaffCodes(emcSituation.getEventId());
        Map<String, String> modelParam = new HashedMap();
        modelParam.put("situationId", EmcSituation.getId().toString());
        modelParam.put("eventId", EmcSituation.getEventId() == null ? null : EmcSituation.getEventId().getId().toString());
        log.error(JSON.toJSONString(staffCodes));
        log.error(JSON.toJSONString(modelParam));
        executor.execute(() -> {
            sendMsgModel(ConstDict.MSG_TYPE_EMC_SITUATION, staffCodes, modelParam);
        });
    }

    //-------------------------------------------------公共功能--------------------------------------------------

    /**
     * websocket推送消息
     *
     * @param websocketMsg
     */
    private void sendWebSocket(EcdWsDto websocketMsg) {
        WebSocketParamDTO param = WebSocketParamDTO.createParam(ConstDict.ECD_WEB_SOCKET,
                Objects.isNull(websocketMsg) ? StringUtils.EMPTY : JsonUtils.objectToJson(websocketMsg));
        try {
            defaultNotify.sendWebSocket(param);
            //msgServiceClient.sendWebSocket(param);
        } catch (Exception e) {
            log.error("WebSocket Error!", e);
        }
    }


    /**
     * 发送消息到指定用户
     *
     * @param msgType
     * @param receivers
     * @param modelParam
     * @return
     */
    public void sendMsgModel(String msgType, List<String> receivers, Map<String, String> modelParam) {
        if (null == receivers || receivers.size() == 0) {
            return;
        }
        //MsgModelDTO msgModelDTO = MsgModelDTO.createMsgModel(msgType, receivers, JsonHelper.writeValue(modelParam));
        MsgModelDTO msgModelDTO = MsgModelDTO.build(msgType, receivers, modelParam, null);
        try {
            log.error("发送消息到消息中心！");
            String ret = notifyFacade.handleNotify(msgModelDTO);
            log.error("消息中心返回内容！" + ret);
        } catch (Exception e) {
            log.error("sendMsgModel Error", e);
        }
    }

    /**
     * po复制为dto
     *
     * @param eventId
     * @param sesecdEmcSituation
     * @param situationDto
     */
    private void copyToDTO(Long eventId, SESECDEmcSituation sesecdEmcSituation, SituationInfoDto situationDto) {
        // 态势描述
        situationDto.setDescribtion(sesecdEmcSituation.getDescribtion());
        // 发生时间
        situationDto.setOccursTime(sesecdEmcSituation.getOccursTime());
        // 发生地点
        situationDto.setPosition(sesecdEmcSituation.getPosition());
        if (null != sesecdEmcSituation.getReportPerson()) {
            // 上报人
            situationDto.setReportPerson(sesecdEmcSituation.getReportPerson().getId());
            situationDto.setReportPersonName(sesecdEmcSituation.getReportPerson().getName());
        }
        // 应急事件ID
        situationDto.setEventId(eventId);
        // 应急态势ID
        situationDto.setStateId(sesecdEmcSituation.getId());
        if (sesecdEmcSituation.getIconObjGis() != null) {
            situationDto.setIconObjName(sesecdEmcSituation.getIconObjGis().getName());
            situationDto.setIconObjUrl(sesecdEmcSituation.getIconObjGis().getIconUrl());
        }
        situationDto.setWoundedPerson(sesecdEmcSituation.getWoundedPerson());
        situationDto.setDeathPerson(sesecdEmcSituation.getDeathPerson());
        if (Objects.nonNull(sesecdEmcSituation.getSituationType())) {
            situationDto.setSituationTypeCode(sesecdEmcSituation.getSituationType().getId());
            situationDto.setSituationTypeName(InternationalResource.get(sesecdEmcSituation.getSituationType().getValue(), getCurrentLanguage()));
        }
    }


    /**
     * 保存后推送应急态势消息推送到消息中心并且发送给应急通讯小组人员
     *
     * @param emcSituation
     */
    public void publishMessageToSituation(SESECDEmcSituation emcSituation) {
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            return;
        }
        if (null != emcSituation) {
            // 发生时间
            String occursTime = emcSituation.getOccursTime() == null ? ""
                    : DateUtils.format(emcSituation.getOccursTime(), "yyyy-MM-dd HH:mm:ss");
            // 态势描述
            String describtion = emcSituation.getDescribtion();
            // 死亡人数
            Integer deathPerson = emcSituation.getDeathPerson();
            // 受伤人数
            Integer woundedPerson = emcSituation.getWoundedPerson();
            // 发生地点
            String position = emcSituation.getPosition();
            // 上报人
            String reportPerson = emcSituation.getReportPerson() == null ? ""
                    : emcSituation.getReportPerson().getName();
            SESECDAlmAlarmRecord almAlarmRecord = emcSituation.getEventId();
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
                    EmergencySituationDto situationDto = new EmergencySituationDto();
                    situationDto.setAccidentName(accidentName);
                    situationDto.setAccidentName(accidentName);
                    situationDto.setWoundedPerson(woundedPerson);
                    situationDto.setDeathPerson(deathPerson);
                    situationDto.setOccursTime(occursTime);
                    situationDto.setPosition(position);
                    situationDto.setReportPerson(reportPerson);
                    situationDto.setDescribtion(describtion);
                    Map<String, String> param = JsonHelper.convertMap(situationDto);
                    executor.execute(() -> {
                        sendMessage("ReceiveAlarm", staffCodes, param, null);
                    });
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
     * 封装返回信息
     *
     * @param situation
     * @return
     */
    private SESECDRecorSituation getRecordSituation(SESECDEmcSituation situation) {
        SESECDRecorSituation recordSituation = new SESECDRecorSituation();
        recordSituation.setAddresss(situation.getCommonAddress() == null ? "" : situation.getCommonAddress().getName());
        recordSituation.setDeathPerson(situation.getDeathPerson() == null ? "" : situation.getDeathPerson().toString());
        recordSituation.setDescribtion(situation.getDescribtion());
        recordSituation.setOccursTime(situation.getOccursTime());
        recordSituation.setPoint(situation.getPoint());
        recordSituation.setReportPerson(situation.getReportPerson() == null ? "" : situation.getReportPerson().getName());
        if (Objects.nonNull(situation.getSituationType())) {
            recordSituation.setSituationType(InternationalResource.get(InternationalResource.get(situation.getSituationType().getValue(), getCurrentLanguage())));
        }
        recordSituation.setWoundedPerson(situation.getWoundedPerson() == null ? "" : situation.getWoundedPerson().toString());
        return recordSituation;
    }



}
