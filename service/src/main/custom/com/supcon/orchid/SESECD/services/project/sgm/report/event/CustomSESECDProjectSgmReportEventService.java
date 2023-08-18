package com.supcon.orchid.SESECD.services.project.sgm.report.event;

import com.supcon.orchid.SESECD.model.vo.project.sgm.report.event.SESECDAlarmRecordVO;
import com.supcon.orchid.support.Result;

public interface CustomSESECDProjectSgmReportEventService {

    /**
     * 接警数据上报
     * @param alarmRecordId 接警id
     * @return 上报结果
     */
    Result<String> Report(Long alarmRecordId);

    /**
     * 接警数据接受
     * @param input 接警事件
     * @return 数据保存成功信息
     */
    Result<String> AddOrUpdate(SESECDAlarmRecordVO input) throws Exception;



}
