package com.supcon.orchid.SESECD.services.impl.out.alarm;

import com.supcon.orchid.SESECD.services.out.alarm.CustomSESECDRealtimeRecordService;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomVxECDRealtimeRecordServiceImpl extends BaseServiceImpl implements CustomSESECDRealtimeRecordService {


    @Autowired
    private CompanyService companyService;

    /**
     * 从报警中心同步报警数据调度任务
     * @param cid 公司id，用于只处理本公司的数据
     * @param alarmStaffCode staffCode用于查询报警接口进行报警点权限过滤
     * @return 返回该接口是否调用成功
     */
    @Override
    public Result<String> automaticAlarmByAlarm(Long cid, String alarmStaffCode) {

        log.error("进入automaticAlarmByAlarm方法");

        //查询实时报警最近一天的记录
//        AlarmRecordQuery recordQuery = getAlarmRecordQuery(staffByCode);




        return null;
    }

}
