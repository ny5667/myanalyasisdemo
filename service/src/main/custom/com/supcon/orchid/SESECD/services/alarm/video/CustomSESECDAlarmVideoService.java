package com.supcon.orchid.SESECD.services.alarm.video;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;

public interface CustomSESECDAlarmVideoService {

    /**
     * 生成视频录像
     *
     * @param almAlarmRecord    接到报警对象
     * @param objects
     */
    void generateCctvRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects);

}
