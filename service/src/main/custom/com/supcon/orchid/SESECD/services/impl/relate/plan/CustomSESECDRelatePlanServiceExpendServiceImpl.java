package com.supcon.orchid.SESECD.services.impl.relate.plan;

import com.supcon.orchid.SESECD.daos.SESECDAlarmActionDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.model.vo.relate.plan.Statistics;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanServiceExpendService;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class CustomSESECDRelatePlanServiceExpendServiceImpl extends BaseServiceImpl implements CustomSESECDRelatePlanServiceExpendService {

    @Autowired
    private SESECDAlarmActionDao alarmActionDao;

    @Autowired
    private SESECDEmePlanObjDao planObjDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 统计当前开启状态预案的数量
     * @param eventID 接警id
     * @return
     */
    @Override
    public Integer statisticsEmergencyPlanNum(Long eventID) {
        String SQL = "SELECT ALARM_ID AS id ,COUNT(ALARM_ID) AS num  FROM SES_ECD_EME_PLAN_OBJS \n" +
                "WHERE ALARM_ID = ? AND IS_CLOSE = 1 AND VALID = 1 GROUP BY ALARM_ID";
        log.error("统计当前开启状态预案的数量：" + SQL + "，事件ID："+eventID);
        return statistics(SQL, eventID);
    }

    /**
     * 统计当前下达状态的指令数量
     * @param eventID 接警id
     * @return
     */
    @Override
    public Integer statisticsAlarmActionNum(Long eventID) {
        String SQL = "SELECT EVENT_ID  AS id ,COUNT(EVENT_ID) AS num  \n" +
                     " FROM ses_ecd_emc_actions WHERE VALID = 1 AND EVENT_ID = ? \n" +
                     " GROUP BY EVENT_ID";
        log.error("统计当前下达状态的指令数量：" + SQL + "，事件ID："+eventID);
        return statistics(SQL, eventID);
    }

    /**
     * 统计策略
     * @param SQL
     * @param eventID
     * @return
     */
    private Integer statistics(String SQL,Long eventID){
        List<Statistics> statistics = jdbcTemplate.query(SQL, new Object[]{eventID}, new BeanPropertyRowMapper<>(Statistics.class));
        if (CollectionUtils.isEmpty(statistics)) {
            log.error("数量为0或数据库IS_CLOSE为空");
            return 0;
        }
        return statistics.get(0).getNum();
    }




}

