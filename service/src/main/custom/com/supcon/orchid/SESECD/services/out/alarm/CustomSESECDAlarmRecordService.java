package com.supcon.orchid.SESECD.services.out.alarm;

import com.supcon.orchid.SESECD.model.dto.out.alarm.AmHistoryDTO;
import com.supcon.orchid.SESECD.model.dto.out.alarm.AmPolicyDTO;
import com.supcon.orchid.alarm.DTO.AlarmRecordDTO;
import com.supcon.orchid.alarm.DTO.AlarmRecordQuery;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface CustomSESECDAlarmRecordService {

    /**
     * 获取实时报警记录
     * @param recordQuery
     * @return
     */
    List<AlarmRecordDTO> getRealTime(@RequestBody AlarmRecordQuery recordQuery);

    /**
     * 查找最近一天的历史报警记录
     * @param cid 公司cid
     * @return 历史报警列表
     */
    List<AmHistoryDTO> getHistoryRecordByMidnight(Long cid);

    /**
     * 根据报警点id列表，查询报警策率数据
     * @param alarmIds 报警点id列表
     * @return 报警策率列表
     */
    Map<Long, AmPolicyDTO> getAlarmPolicyByAlarmIds(List<Long> alarmIds);

}
