package com.supcon.orchid.SESECD.services.alarm.record;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.model.vo.TypeAndPlanIdsVO;
import com.supcon.orchid.SESECD.model.vo.alarm.AlarmRecordVO;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.support.Result;

import java.util.List;
import java.util.Set;

public interface CustomSESECDAlarmRecordService {


    /**
     * 根据演练计划id得到接警记录
     * @return
     */
    AlarmRecordVO getAlarmRecordByPlanId(Long id);

    /**
     * 通过接警Id获取通讯组Ids
     *
     * @param alarmRecordId
     * @return
     */
    List<Long> getSectionIdListByAlarmRecordId(Long alarmRecordId);


    /**
     * 根据应急事件Id 获取常用地址和摄像同相关信息
     *
     * @param id
     */
    Result getCommonPlaceById(Long id);

    /**
     * 获取常用地址信息
     *
     * @param id
     */
    Result getDevicePositionMap(Long id);

    /**
     * 发送WS 消息
     *
     * @param id
     */
    Result sendWsMsg(Long id);

    /**
     * 根据接警找到预案，再根据预案找到关联通讯录，找到通讯录内所有人，返回人员编码
     *
     * @param alarmRecord 接警
     * @return 人员编码
     */
    List<String> getStaffCodes(SESECDAlmAlarmRecord alarmRecord);

    /*---------------------------------------------平台事件方法-开始-------------------------------------------------*/


    /**
     * 保存前发送通知
     *
     * @param almAlarmRecord
     * @param objects
     */
    void beforeSaveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects);

    /**
     * 保存后删除坐标
     * @param almAlarmRecord
     * @param objects
     */
    void afterSaveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects);

    /*---------------------------------------------平台事件方法-结束-------------------------------------------------*/
}
