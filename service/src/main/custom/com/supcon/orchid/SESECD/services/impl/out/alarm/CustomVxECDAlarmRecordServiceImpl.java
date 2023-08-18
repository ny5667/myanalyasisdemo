package com.supcon.orchid.SESECD.services.impl.out.alarm;

import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.model.dto.out.alarm.AmHistoryDTO;
import com.supcon.orchid.SESECD.model.dto.out.alarm.AmPolicyDTO;
import com.supcon.orchid.SESECD.services.out.alarm.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.alarm.DTO.AlarmRecordDTO;
import com.supcon.orchid.alarm.DTO.AlarmRecordQuery;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class CustomVxECDAlarmRecordServiceImpl extends BaseServiceImpl implements CustomSESECDAlarmRecordService {

    @Autowired
    private com.supcon.orchid.alarm.client.IAlarmRecordClient alarmRecordClient;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<AlarmRecordDTO> getRealTime(AlarmRecordQuery recordQuery) {
        log.error("查询实时报警：");
        log.error(JsonHelper.writeValue(recordQuery));
        List<AlarmRecordDTO> realTimes = new ArrayList<>();
        try {
            realTimes = alarmRecordClient.getRealTime(recordQuery);
        } catch (Exception e) {
            log.error("实时报警调用失败", e);
        }
        log.error("查询实时报警返回：");
        log.error("报警记录有{}条数据", realTimes.size());
        log.error(JsonHelper.writeValue(realTimes));
        return realTimes;
    }

    @Override
    public List<AmHistoryDTO> getHistoryRecordByMidnight(Long cid) {

        //查询历史报警最近一天的记录
        Map<String, Object> map = new HashMap<>();
        map.put("alarmTime", getMidnightOfDaysAgo());
        map.put("cid", cid);

        List<AmHistoryDTO> amHistoryDTOS = namedParameterJdbcTemplate.query(ConstDict.SQL_AM_HISTORY, map, new BeanPropertyRowMapper<>(AmHistoryDTO.class));
        log.error("历史报警记录数据：");
        log.error("历史报警记录有{}条数据", amHistoryDTOS.size());

        return amHistoryDTOS;
    }

    @Override
    public Map<Long, AmPolicyDTO> getAlarmPolicyByAlarmIds(List<Long> alarmIds) {
        SqlParameterSource parameters = new MapSqlParameterSource("alarmIds", alarmIds);
        List<AmPolicyDTO> amPolicyDTOS = namedParameterJdbcTemplate.query(ConstDict.SQL_AM_POLICY, parameters, new BeanPropertyRowMapper<>(AmPolicyDTO.class));
        Map<Long, AmPolicyDTO> map_AmPolicy = new HashMap<>();
        for (AmPolicyDTO item :
                amPolicyDTOS) {
            map_AmPolicy.put(item.getAlarmId(), item);
        }
        return map_AmPolicy;
    }

    //-----------------------------------------公共功能--------------------------------------------

    /**
     * 获取一天前零点
     *
     * @return
     */
    private Date getMidnightOfDaysAgo() {
        LocalDateTime midnightOfDaysAgo = DateUtils.getMidnightOfDaysAgo(1);
        Date date = DateUtils.toDate(midnightOfDaysAgo);
        return date;
    }

}
