package com.supcon.orchid.SESECD.services.alarm.action;

import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.model.dto.alarm.MapPointDto;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.support.Result;

import java.util.List;

/**
 * 接警记录-关联指令
 */
public interface CustomSESECDAlarmActionService {


    /**
     * 根据预案设置指令
     * @param almAlarmRecord
     */
    void generateInstructions(SESECDAlmAlarmRecord almAlarmRecord);


    /**
     * 下达指令更改应急事件关联指令状态并将指令下达生成应急行动
     *
     * @param commomIds
     * @return
     */
    BaseInfoVO releaseCommom(List<Long> commomIds);

    /**
     * 通过应急事件ID获取应急事件关联的指令
     *
     * @param eventId
     * @return
     */
    BaseInfoVO getCommomInfo(Long eventId);

    /**
     * 保存应急指令坐标到应急行动业务数据坐标字段
     *
     * @param mapPointDto
     * @return
     */
    String saveMapPoint(MapPointDto mapPointDto);

    //*******************************************************自定义事件************************************************//


    /**
     * 关联指令 自定义保存后事件
     *
     * @param alarmAction
     * @param objects
     */
    void customAfterSaveAlarmAction(SESECDAlarmAction alarmAction, Object... objects);


}
