package com.supcon.orchid.SESECD.services.alarm.record;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.model.dto.alarm.*;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.foundation.entities.SystemCode;

import java.util.Date;
import java.util.List;


public interface CustomSESECDAlarmService {

    /**
     * 处警
     *
     * @param alarmHandleDto
     * @return
     */
    BaseInfoVO changeAlarmState(AlarmHandleDto alarmHandleDto);

    /**
     * 关闭应急事件
     *
     * @param ids
     * @return
     */
    Boolean overEvents(List<Long> ids);

    /**
     * 保存后推送接警信息到消息中心并且发送给应急通讯小组人员
     *
     * @param almAlarmRecord
     */
    void publishMsg(SESECDAlmAlarmRecord almAlarmRecord, Boolean isEnd);

    /**
     * 将消息推送给消息中心
     *
     * @param eventId
     * @param staffCodes
     */
    String pulishMessage(Long eventId, List<String> staffCodes);

    /**
     * 通过应急事件ID获取应急事件相关信息及最新一条态势信息及最新一条行动信息
     *
     * @param eventId
     * @return
     */
    EcdInfoByIdDto getecdAndInfoCurrentInfoById(Long eventId);


    /**
     * 保存后推送应急行动消息推送到消息中心并且发送给应急通讯小组人员
     *
     * @param emcAction
     */
    void publishMessageToAction(SESECDEmcAction emcAction);

    /**
     * 通过应急事件ID获取应急事件信息、应急指令信息、应急态势信息及应急行动信息
     *
     * @param eventId
     * @return
     */
    EcdInfosDto getEcdInfosByEventId(Long eventId);

    /**
     * 接警事件关联了 演练计划，将演练计划状态更改为 执行中
     *
     * @param planId
     */
    void updateDrillPlanState(Long planId, Date happenTime);

    /**
     * 改变事件等级
     *
     * @param changeLevelDTO
     * @return
     */
    SystemCode changeLevel(ChangeLevelDTO changeLevelDTO);

    /**
     * 接警确认
     * @param alarmCheckDTO
     * @return
     */
    void alarmCheck(AlarmCheckDTO alarmCheckDTO);
}
