package com.supcon.orchid.SESECD.services.impl.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.*;
import com.supcon.orchid.SESECD.entities.*;
import com.supcon.orchid.SESECD.model.dto.action.ActionDto;
import com.supcon.orchid.SESECD.model.dto.action.EcdWsDTO;
import com.supcon.orchid.SESECD.model.dto.alarm.EmcActionDto;
import com.supcon.orchid.SESECD.model.vo.action.SESECDActVideoCameraVO;
import com.supcon.orchid.SESECD.model.vo.alarm.AlarmRecordVO;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.services.SESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.notify.DefaultNotifyImpl;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.SesCommonFunc.dto.WebSocketParamDTO;
import com.supcon.orchid.VideoPlayWeb.entities.VideoPlayWebCameraConfig;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.utils.JsonUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 应急行动
 */
@Service
@Transactional
public class CustomSESECDActionServiceImpl extends BaseServiceImpl<SESECDEmcAction> implements CustomSESECDActionService {

    @Autowired
    private SESECDEmcActionDao emcActionDao;
    @Autowired
    private SystemCodeService systemCodeService;
    @Autowired
    private SESECDDispatConfigDao sesecdDispatConfigDao;
    @Autowired
    private SESECDAlarmActionService alarmActionService;
    @Autowired
    private SESECDAlarmActionDao alarmActionDao;
    @Autowired
    private DefaultNotifyImpl defaultNotify;
    @Autowired
    private NotifyFacade notifyFacade;
    @Autowired
    private CustomSESECDSavePointService savePointService;
    @Autowired
    private CustomSESECDAlarmRecordService alarmRecordService;
    @Autowired
    private SqlUtils sqlUtils;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private SESECDMainPeopleDao mainPeopleDao;

    @Autowired
    private SESECDEmcActionDao actionDao;

    @Autowired
    private SESECDMainDepartmentDao mainDepartmentDao;

    /**
     * 跟踪应急行动
     *
     * @param actionDto
     * @return
     */
    public Boolean trackAction(ActionDto actionDto) {
        if (CollectionUtils.isEmpty(actionDto.getIds())) {
            return true;
        }

        List<Long> ids = actionDto.getIds();
        String actionState = actionDto.getActionState();
        String trackRecord = actionDto.getTrackRecord();

        for (Long id : ids) {
            SESECDEmcAction sesecdEmcAction = emcActionDao.get(id);
            sesecdEmcAction.setTrackRecord(trackRecord);
            try {
                if (StringUtils.isNotEmpty(actionState)) {
                    SystemCode systemCodeByID = (SystemCode) systemCodeService.getSystemCodeByID(actionState);
                    sesecdEmcAction.setActionState(systemCodeByID);
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
            emcActionDao.merge(sesecdEmcAction);
        }
        return true;
    }


    @Override
    public Boolean judgeDispatcher() {

        List<SESECDDispatConfig> sesecdDispatConfigs = sesecdDispatConfigDao.findByHql("from " + SESECDDispatConfig.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID());
        List<String> longList = new ArrayList<>();
        if (null != sesecdDispatConfigs && sesecdDispatConfigs.size() > 0) {
            for (SESECDDispatConfig sesecdDispatConfig : sesecdDispatConfigs) {
                String name = sesecdDispatConfig.getPerson().getName();
                log.info("调度员姓名" + name);
                log.info("调度员id" + sesecdDispatConfig.getPerson().getId());
                log.info("调度员code" + sesecdDispatConfig.getPerson().getCode());
                longList.add(name);
            }
        }
        log.info("调度员的人数" + longList.size());
        boolean contains = longList.contains(getCurrentStaff().getName());
        log.info("当前登录人ID" + getCurrentStaff().getId());
        log.info("当前登录人姓名" + getCurrentStaff().getName());
        log.info("是否是调度员" + contains);
        return contains;
    }



    @Override
    public List<SESECDActVideoCameraVO> listCamerasByActionId(Long actionId) {
        SESECDEmcAction sesecdEmcAction = emcActionDao.get(actionId);
        Assert.notNull(sesecdEmcAction, "actionId can not find entity");

        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDActVideoCamera.JPA_NAME).append(" where valid = 1 and emcAction = ?0");
        List<SESECDActVideoCamera> cameras = emcActionDao.findByHql(builder.toString(), new Object[]{sesecdEmcAction});

        if (cameras.isEmpty()) {
            return Collections.emptyList();
        }
        List<SESECDActVideoCameraVO> list = new ArrayList<>();
        cameras.forEach(c -> {
            VideoPlayWebCameraConfig camera = c.getCamera();
            if (camera == null) {
                return;
            }
            SESECDActVideoCameraVO vo = getSesecdActVideoCameraVO(camera);
            list.add(vo);
        });
        return list;
    }


    /**
     * 通过应急事件ID获取应急行动信息
     *
     * @param eventId
     * @return
     */
    @Override
    public BaseInfoVO getEmcActionInfo(Long eventId) {
        List<EmcActionDto> EmcActionDtos = null;
        SESECDAlmAlarmRecord sesecdAlmAlarmRecord = almAlarmRecordDao.get(eventId);
        String hql = " from " + SESECDEmcAction.JPA_NAME + " where  valid = 1 and cid = ? and eventId = ?";
        List<SESECDEmcAction> SESECDEmcActions = emcActionDao.findByHql(hql,
                new Object[]{getCurrentCompanyId(), sesecdAlmAlarmRecord});
        if (null != SESECDEmcActions && SESECDEmcActions.size() > 0) {
            EmcActionDtos = new ArrayList<>();
            EmcActionDto emcActionDto = null;
            for (SESECDEmcAction sesecdEmcAction : SESECDEmcActions) {
                emcActionDto = new EmcActionDto();
                // 行动地点
                emcActionDto.setActionAddr(sesecdEmcAction.getActionAddr());
                // 应急行动ID
                emcActionDto.setActionId(sesecdEmcAction.getId());
                SystemCode actionState = sesecdEmcAction.getActionState();
                if (null != actionState) {
                    // 行动状态
                    emcActionDto.setActionStateId(actionState.getId());
                    // 行动状态
                    emcActionDto.setActionStateValue(
                            InternationalResource.get(actionState.getValue(), getCurrentLanguage()));
                }
                try {
                    // 行动时间
                    emcActionDto.setBeginTime(com.supcon.orchid.utils.DateUtils.format(sesecdEmcAction.getActionTime(), "yyyy-MM-dd HH:mm:ss"));
                } catch (Exception e) {
                    // 开始时间
                    emcActionDto.setBeginTime("");
                }
                // 行动描述
                emcActionDto.setDescription(sesecdEmcAction.getDescription());
                // 应急事件ID
                emcActionDto.setEventId(sesecdEmcAction.getEventId() == null ? null : sesecdEmcAction.getEventId().getId());
//                List<SESECDMainPeople> mainPeople = mainPeopleDao.findByProperty("actionId", sesecdEmcAction);
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
                    emcActionDto.setOwners(owners);
                }
                // emcActionDto.setOwners(sesecdEmcAction.getOwnPerson());

                if (null != sesecdEmcAction.getActionCatogory()) {
                    emcActionDto.setActionCatogoryId(sesecdEmcAction.getActionCatogory().getId());
                    emcActionDto.setActionCatogoryName(sesecdEmcAction.getActionCatogory().getFullPathName());
                }
                emcActionDto.setTrackRecord(sesecdEmcAction.getTrackRecord());

                EmcActionDtos.add(emcActionDto);
            }
        }
        return BaseInfoVO.ok(EmcActionDtos);
    }


    @Override
    public Result<List<SESECDRecordAction>> getRelationActionByAlarmRecordId(Long alarmRecordId) {
        SESECDAlmAlarmRecord alarmRecord = almAlarmRecordDao.load(alarmRecordId);
        String sql = "from " + SESECDEmcAction.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID() + " and eventId = ?0";
        List<SESECDEmcAction> actionList = actionDao.findByHql(sql, new Object[]{alarmRecord});
        if (Objects.isNull(actionList) || actionList.size() == 0) {
            return new Result<>();
        }
        sql = "from " + SESECDMainPeople.JPA_NAME + " where valid = 1  " + sqlUtils.getSqlPartByCID();
        List<SESECDMainPeople> peopleList = mainPeopleDao.findByHql(sql);
        sql = "from " + SESECDMainDepartment.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID();
        List<SESECDMainDepartment> departmentList = mainDepartmentDao.findByHql(sql);
        List<SESECDRecordAction> list = new ArrayList<>();
        for (SESECDEmcAction action : actionList) {
            SESECDRecordAction recordAction = getRecordAction(action, peopleList, departmentList);
            list.add(recordAction);
        }
        return Result.data(list);
    }

    //*******************************************************自定义事件************************************************//
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public void customAfterSaveEmcAction(SESECDEmcAction emcAction) {
        log.info("=============afterSaveVxEmcAction==============");
        //有关联指令id的情况下需要同步关联指令的状态
        if (null != emcAction.getCommonId() && null != emcAction.getActionState()) {
            //关联指令
            SESECDAlarmAction alarmAction = alarmActionService.getAlarmAction(emcAction.getCommonId());
            //根据行动状态设置关联指令状态
            String stateId = emcAction.getActionState().getId();
            //VxECD_actionState/002：执行中 ；VxECD_actionState/003：已执行；VxECD_commandState/004：已取消
            if ("SESECD_actionState/002".equals(stateId)) {
                SystemCode commandState = (SystemCode) systemCodeService.getSystemCode("SESECD_commandState/003");
                alarmAction.setCommomState(commandState);
            } else if ("SESECD_actionState/003".equals(stateId)) {
                SystemCode commandState = (SystemCode) systemCodeService.getSystemCode("SESECD_commandState/004");
                alarmAction.setCommomState(commandState);
                emcAction.setEndTime(new Date());//实际结束时间
            }
            emcActionDao.save(emcAction);
            emcActionDao.flush();
            emcActionDao.clear();
            alarmActionDao.save(alarmAction);
            alarmActionDao.flush();
            alarmActionDao.clear();
        }
        //websocket发送消息推送到应急指挥
        executor.execute(() -> {
            sendWebSocket(emcAction);
        });
        executor.execute(() -> {
            sendToMembersMsgModel(emcAction);
        });
    }


    //*******************************************************私有公共功能************************************************//

    /**
     * 获取发送消息
     *
     * @param action
     * @return
     */
    private Map<String, String> prepareParam(SESECDEmcAction action) {
        Map<String, String> modelParam = new HashedMap();
        modelParam.put("actionId", action.getId().toString());
        modelParam.put("eventId", action.getEventId() == null ? "" : action.getEventId().getId().toString());//接警记录
        modelParam.put("accidentName", action.getEventId() != null ? action.getEventId().getAccidentName() : "");//事件名称
        modelParam.put("actionAddr", action.getActionAddr());//行动地点
        String actionTime = "";
        if (action.getActionTime() != null) {
            actionTime = DateUtils.MySimpleDateFormat.format(action.getActionTime());
        }
        modelParam.put("actionTime", actionTime);//行动时间
        modelParam.put("description", action.getDescription());//行动描述
        modelParam.put("actionState", action.getActionState() != null ? InternationalResource.get(action.getActionState().getValue(), getCurrentLanguage()) : "");//行动状态
        return modelParam;
    }

    /**
     * 通过ws发送通知
     *
     * @param emcAction
     */
    private void sendWebSocket(SESECDEmcAction emcAction) {
        EcdWsDTO ecdWsDto = new EcdWsDTO();
        ecdWsDto.setActionId(emcAction.getId());
        ecdWsDto.setEventId(emcAction.getEventId() == null ? null : emcAction.getEventId().getId());
        WebSocketParamDTO param = WebSocketParamDTO.createParam(ConstDict.ECD_WEB_SOCKET, JsonUtils.objectToJson(ecdWsDto));
        try {
            defaultNotify.sendWebSocket(param);
            log.info("远程调用sendWebSocket 通知成功，通知参数：" + JsonUtils.objectToJson(param));
        } catch (Exception e) {
            log.error("远程调用sendWebSocket 异常==============", e);
            log.error("请求参数：" + JSONObject.toJSONString(param));
        }
    }


    /**
     * 给应急成员小组发消息
     */
    private void sendToMembersMsgModel(SESECDEmcAction emcAction) {
        MsgModelDTO build = null;

        List<String> staffCode = alarmRecordService.getStaffCodes(emcAction.getEventId());
        if (CollectionUtils.isEmpty(staffCode)) {
            return;
        }
        Map<String, String> prepareParam = prepareParam(emcAction);
        log.info("prepareParam参数=============：" + JSON.toJSONString(prepareParam) + "接受人员receiveStaffid：" + JSON.toJSONString(staffCode));
        if (staffCode.size() == 0) {
            return;
        }
        build = MsgModelDTO.builder().param(prepareParam).msgType(ConstDict.MSG_TYPE_EMC_ACTION).receivers(staffCode).build();
        try {
            notifyFacade.handleNotify(build);
        } catch (Exception e) {
            log.error("远程调用sendMsgModel Error，参数：" + JSON.toJSONString(build), e);
        }
        log.info("sendToMembersMsgModel 发送成功");
    }

    /**
     * po转vo
     *
     * @param camera
     * @return
     */
    private SESECDActVideoCameraVO getSesecdActVideoCameraVO(VideoPlayWebCameraConfig camera) {
        SESECDActVideoCameraVO vo = new SESECDActVideoCameraVO();
        vo.setChannel(camera.getChannel());//通道号
        vo.setCode(camera.getCode());//编码
        vo.setId(camera.getId());//id
        vo.setName(camera.getName());//名称
        return vo;
    }


    private SESECDRecordAction getRecordAction(SESECDEmcAction action, List<SESECDMainPeople> peopleList, List<SESECDMainDepartment> departmentList) {
        SESECDRecordAction recordAction = new SESECDRecordAction();
        recordAction.setActionAddr(action.getActionAddr());
        if (Objects.nonNull(action.getActionCatogory())) {
            recordAction.setActionCatogory(InternationalResource.get(InternationalResource.get(action.getActionCatogory().getValue(), getCurrentLanguage())));
        }
        if (Objects.nonNull(action.getActionState())) {
            recordAction.setActionState(InternationalResource.get(InternationalResource.get(action.getActionState().getValue(), getCurrentLanguage())));
        }
        recordAction.setActionTime(action.getActionTime());
        recordAction.setDescription(action.getDescription());
        recordAction.setPoint(action.getPoint());
        recordAction.setTrackRecord(action.getTrackRecord());
        if (Objects.nonNull(peopleList)) {
            StringBuilder name = new StringBuilder();
            List<SESEABSetionmember> collect = peopleList.stream().filter(e -> Objects.nonNull(e.getOwnPerson())
                            && Objects.nonNull(e.getActionId())
                            && e.getActionId().getId().equals(action.getId()))
                    .map(SESECDMainPeople::getOwnPerson).collect(Collectors.toList());
            for (SESEABSetionmember person : collect) {
                name.append(Objects.isNull(person.getPersonId()) ? "" : person.getPersonId().getName()).append(",");
            }
            recordAction.setOwnPerson(name.toString().substring(0, name.toString().length() - 1));
        }
        if (Objects.nonNull(departmentList)) {
            StringBuilder name = new StringBuilder();
            List<SESEABEabSetion> collect = departmentList.stream().filter(e -> Objects.nonNull(e.getOwnDepartmentN())
                            && Objects.nonNull(e.getActionId())
                            && e.getActionId().getId().equals(action.getId()))
                    .map(SESECDMainDepartment::getOwnDepartmentN).collect(Collectors.toList());
            for (SESEABEabSetion dept : collect) {
                if (Objects.isNull(dept) || Objects.isNull(dept.getBelongDepartment())) {
                    continue;
                }
                name.append(dept.getBelongDepartment().getName()).append(",");
            }
            recordAction.setOwnDepartment(name.toString().substring(0, name.length() - 1));
        }
        return recordAction;
    }

}
