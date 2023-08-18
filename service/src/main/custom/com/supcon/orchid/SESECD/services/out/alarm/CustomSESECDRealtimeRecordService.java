package com.supcon.orchid.SESECD.services.out.alarm;

import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.support.Result;

public interface CustomSESECDRealtimeRecordService {

    /**
     * 从报警中心同步报警数据调度任务
     * @param cid 公司id，用于只处理本公司的数据
     * @param alarmStaffCode staffCode用于查询报警接口进行报警点权限过滤
     * @return 返回该接口是否调用成功
     */
    Result<String> automaticAlarmByAlarm(Long cid, String alarmStaffCode);

}
