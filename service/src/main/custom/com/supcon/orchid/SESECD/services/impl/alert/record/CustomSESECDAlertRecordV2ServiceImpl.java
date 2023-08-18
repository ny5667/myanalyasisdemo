package com.supcon.orchid.SESECD.services.impl.alert.record;

import com.supcon.orchid.SESECD.daos.SESECDEcdAlertRecordDao;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.SESECD.model.dto.out.alarm.AmHistoryDTO;
import com.supcon.orchid.SESECD.model.dto.out.alarm.AmPolicyDTO;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordV2Service;
import com.supcon.orchid.SESECD.services.out.alarm.CustomSESECDAlarmRecordService;
import com.supcon.orchid.alarm.DTO.AlarmRecordDTO;
import com.supcon.orchid.alarm.DTO.AlarmRecordQuery;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class CustomSESECDAlertRecordV2ServiceImpl extends BaseServiceImpl implements CustomSESECDAlertRecordV2Service {

    private static final String MY_UN_HANDLE_ALARM_STATE = "未处置";

    /**
     * 应急指挥报警来源
     */
    private static final String ECD_ORIGIN_CODE = "016";

    /**
     * 报警查询页码
     */
    private static final int QUERY_PAGE_NO = 1;

    /**
     * 报警查询页显示数量
     */
    private static final int QUERY_PAGE_SIZE = 99;

    /**
     * 实时报警表
     */
    private static final String ALARM_TABLE_SOURCE_REALTIME_TABLE = "实时报警";

    /**
     * 历史报警表
     */
    private static final String ALARM_TABLE_SOURCE_HISTORY_TABLE = "历史报警";

    @Autowired
    private CustomSESECDAlarmRecordService customSESECDRealtimeRecordService;

    @Autowired
    private SESECDEcdAlertRecordDao ecdAlertRecordDao;

    /**
     * 从报警中心同步报警数据调度任务
     *
     * @param cid            公司id，用于只处理本公司的数据
     * @param alarmStaffCode staffCode用于查询报警接口进行报警点权限过滤
     * @return 返回该接口是否调用成功
     */
    @Override
    public Result<String> automaticAlarmByAlarm(Long cid, Staff alarmStaffCode) {
        log.error("进入automaticAlarmByAlarm方法");
        //查询实时报警最近一天的记录
        AlarmRecordQuery recordQuery = getAlarmRecordQuery(alarmStaffCode.getId());
        List<AlarmRecordDTO> realTimes = customSESECDRealtimeRecordService.getRealTime(recordQuery);
        //查询历史报警最近一天的记录
        List<AmHistoryDTO> amHistoryDTOS = customSESECDRealtimeRecordService.getHistoryRecordByMidnight(cid);

        List<Long> alarmIds = Stream.concat(realTimes.stream().filter(v -> v.getAlarmId() != null).map(v -> v.getAlarmId().getId()), amHistoryDTOS.stream().map(AmHistoryDTO::getAlarmId))
                .collect(Collectors.toList());
        if (alarmIds.isEmpty()) {
            return Result.data(String.format("数据库处理了%s条报警记录", 0));
        }
        //查找关联策略
        Map<Long, AmPolicyDTO> map_AmPolicy = customSESECDRealtimeRecordService.getAlarmPolicyByAlarmIds(alarmIds);

        //获取最近一天的报警记录数据
        Map<String, SESECDEcdAlertRecord> map_alertRecord = getStringVxECDVxAlertRecordMap(cid);

        Integer countRowAffect = 0;
        countRowAffect = addOrUpdateAlertRecordByRealtimeRecord(cid, realTimes, map_alertRecord, countRowAffect, map_AmPolicy);
        countRowAffect = addOrUpdateAlertRecordByHistoryRecord(cid, amHistoryDTOS, map_alertRecord, countRowAffect, map_AmPolicy);
        log.error("结束automaticAlarmByAlarm方法");
        return Result.data(String.format("数据库处理了%s条报警记录", countRowAffect));
    }

    //-----------------------------------------公共功能--------------------------------------------

    /**
     * 获取实时报警查询条件
     *
     * @param staffId
     * @return
     */
    private AlarmRecordQuery getAlarmRecordQuery(Long staffId) {
        log.error("进入获取实时报警查询条件");
        AlarmRecordQuery recordQuery = new AlarmRecordQuery();
        recordQuery.setOriginCodes(Arrays.asList(ECD_ORIGIN_CODE));

        recordQuery.setPageNo(QUERY_PAGE_NO);
        recordQuery.setPageSize(QUERY_PAGE_SIZE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -365);
        recordQuery.setBeginTime(calendar.getTime());

        recordQuery.setEndTime(new Date());

        recordQuery.setUserId(staffId);
        log.error("退出获取实时报警查询条件");
        return recordQuery;
    }

    /**
     * 获取报警记录map
     *
     * @param cid 公司cid
     * @return
     */
    private Map<String, SESECDEcdAlertRecord> getStringVxECDVxAlertRecordMap(Long cid) {

        log.error("进入获取报警记录map方法");
        StringBuilder builder = new StringBuilder();
//        builder.append("from ").append(VxECDVxAlertRecord.JPA_NAME).append(" where valid = 1 and cid = ?0 and alarmTime > ?1 ORDER BY alarmTime desc");
//        List<VxECDVxAlertRecord> alertRecords = alertRecordDao.findByHql(builder.toString(), new Object[]{cid, getMidnightOfDaysAgo()});
        builder.append("from ").append(SESECDEcdAlertRecord.JPA_NAME).append(" where valid = 1");
        List<SESECDEcdAlertRecord> alertRecords = ecdAlertRecordDao.findByHql(builder.toString());
        Map<String, SESECDEcdAlertRecord> map_alertRecord = new HashMap<>();
        for (SESECDEcdAlertRecord item :
                alertRecords) {
            String key = item.getRecordId();
            if (key == null || key.isEmpty() || item.getAlarmTime() == null) {
                continue;
            }
            SESECDEcdAlertRecord vxECDVxAlertRecord = map_alertRecord.get(key);
            if (vxECDVxAlertRecord == null || item.getAlarmTime().before(item.getAlarmTime())) {
                map_alertRecord.put(key, item);
            }
        }
        log.error("结束获取报警记录map方法");
        return map_alertRecord;
    }

    /**
     * 根据recordId来新增或更新实时记录记录
     *
     * @param cid             公司id
     * @param realTimes       实时报警
     * @param map_alertRecord 应急指挥的报警记录
     * @param countRowAffect  数据库影响行数
     * @param map_AmPolicy    报警策略数据
     */
    private Integer addOrUpdateAlertRecordByRealtimeRecord(Long cid, List<AlarmRecordDTO> realTimes, Map<String, SESECDEcdAlertRecord> map_alertRecord, Integer countRowAffect, Map<Long, AmPolicyDTO> map_AmPolicy) {
        log.error("进入根据实时报警记录来新增或更新实时记录记录方法");
        for (AlarmRecordDTO item :
                realTimes) {
            SESECDEcdAlertRecord vxECDVxAlertRecord = map_alertRecord.get(item.getRecordId());
            //同步实时报警记录
            if (vxECDVxAlertRecord == null) {
                vxECDVxAlertRecord = new SESECDEcdAlertRecord();
            }
            convertToPO(item, vxECDVxAlertRecord, cid, map_AmPolicy);
            ecdAlertRecordDao.save(vxECDVxAlertRecord);
            ecdAlertRecordDao.flush();
            countRowAffect++;
        }
        log.error("退出根据实时报警记录来新增或更新实时记录记录方法");
        log.error("countRowAffect:");
        log.error(countRowAffect + "");
        return countRowAffect;
    }

    /**
     * 实时记录转为PO
     *
     * @param item               实时报警记录
     * @param vxECDVxAlertRecord 应急指挥报警记录
     * @param cid                公司id
     * @param map_AmPolicy       报警策略数据
     */
    private void convertToPO(AlarmRecordDTO item, SESECDEcdAlertRecord vxECDVxAlertRecord, Long cid, Map<Long, AmPolicyDTO> map_AmPolicy) {
        log.error("进入实时报警DTO转为PO方法");
        if (item.getAlarmId() == null) {
            return;
        }
        vxECDVxAlertRecord.setAlarmState(MY_UN_HANDLE_ALARM_STATE);
        vxECDVxAlertRecord.setAlarmCode(item.getRecordId());// 报警编码
        vxECDVxAlertRecord.setAlarmDeviceName(item.getRelationTags());// 报警装置名称
        vxECDVxAlertRecord.setAlarmTime(item.getAlarmTime());// 报警时间
        vxECDVxAlertRecord.setDeviceLocationX(null);// 报警装置坐标经度
        if (item.getAlarmOrigin() != null) {
            vxECDVxAlertRecord.setAlarmOrigin(item.getAlarmOrigin().getAppName());// 报警来源
        }
        vxECDVxAlertRecord.setDeviceLocationY(null);// 报警装置坐标纬度
        vxECDVxAlertRecord.setAlarmContent(item.getDescription());// 报警内容
        try {
            vxECDVxAlertRecord.setRealTimeValue(new BigDecimal(item.getAlarmValue()));// 实时值
        } catch (Exception e) {
            log.error("报警值转换失败", e);
        }
        vxECDVxAlertRecord.setDuration(0);// 报警持续时间（单位秒）

        AmPolicyDTO amPolicyDTO = map_AmPolicy.get(item.getAlarmId().getId());
        if (amPolicyDTO != null) {
            vxECDVxAlertRecord.setHhv(amPolicyDTO.getHv());// 上上限
            vxECDVxAlertRecord.setLlv(amPolicyDTO.getLlv());// 下下限
            vxECDVxAlertRecord.setLv(amPolicyDTO.getLv());// 下限
            vxECDVxAlertRecord.setHv(amPolicyDTO.getHv());// 上限
            vxECDVxAlertRecord.setUnitName(amPolicyDTO.getUnitName());//单位名称
        }
        vxECDVxAlertRecord.setRecordId(item.getRecordId());// recordId
        vxECDVxAlertRecord.setCid(cid);
        vxECDVxAlertRecord.setAlarmTableSource(ALARM_TABLE_SOURCE_REALTIME_TABLE);
        if(item.getAlarmType() != null){
            vxECDVxAlertRecord.setAlarmType(item.getAlarmType().getTypename());// 报警类型
        }
        if(item.getAlarmLevel() != null){
            vxECDVxAlertRecord.setAlarmLevel(item.getAlarmLevel().getLevelname());// 报警等级
        }
        vxECDVxAlertRecord.setDurationTime(item.getDurationTime());// 报警持续时间（单位秒）
        log.error("结束实时报警DTO转为PO方法");
    }

    /**
     * 根据历史报警记录来进行更新
     *
     * @param cid             公司id
     * @param amHistoryDTOS   历史报警最近一天的记录
     * @param map_alertRecord 应急指挥中的最近一天的报警记录
     * @param countRowAffect  数据库中影响行数
     * @param map_AmPolicy    报警策略map
     */
    private Integer addOrUpdateAlertRecordByHistoryRecord(Long cid, List<AmHistoryDTO> amHistoryDTOS, Map<String, SESECDEcdAlertRecord> map_alertRecord, Integer countRowAffect, Map<Long, AmPolicyDTO> map_AmPolicy) {
        log.error("进入根据历史报警记录来进行更新方法");
        for (AmHistoryDTO item :
                amHistoryDTOS) {
            SESECDEcdAlertRecord vxECDVxAlertRecord = map_alertRecord.get(item.getRecordId());
            //存过该recordId的实时报警记录
            boolean b = vxECDVxAlertRecord != null && vxECDVxAlertRecord.getAlarmTableSource().equals(ALARM_TABLE_SOURCE_REALTIME_TABLE);
            //没有存过该recordId的报警记录
            boolean b2 = vxECDVxAlertRecord == null;
            //需要新增或更新该报警记录
            boolean b3 = (b || b2);
            if (!b3) {
                continue;
            }
            if (vxECDVxAlertRecord == null) {
                vxECDVxAlertRecord = new SESECDEcdAlertRecord();
            }
            convertToPO_AmHistory(item, cid, vxECDVxAlertRecord, map_AmPolicy);
            ecdAlertRecordDao.save(vxECDVxAlertRecord);
            ecdAlertRecordDao.flush();
            countRowAffect++;
        }
        log.error("退出根据历史报警记录来进行更新方法");
        log.error("countRowAffect:");
        log.error(countRowAffect + "");
        return countRowAffect;
    }


    /**
     * 转为历史报警实体
     *
     * @param item               报警历史数据
     * @param cid                公司cid
     * @param vxECDVxAlertRecord 应急指挥报警数据
     * @param map_AmPolicy       报警策略数据
     * @return
     */
    private void convertToPO_AmHistory(AmHistoryDTO item, Long cid, SESECDEcdAlertRecord vxECDVxAlertRecord, Map<Long, AmPolicyDTO> map_AmPolicy) {
        log.error("进入转为历史记录转PO方法");
        // 报警编码
        vxECDVxAlertRecord.setAlarmCode(item.getRecordId());
        // 报警装置名称
        vxECDVxAlertRecord.setAlarmDeviceName(item.getRelationTags());
        // 报警时间
        vxECDVxAlertRecord.setAlarmTime(item.getAlarmTime());
        // 报警装置坐标经度
        vxECDVxAlertRecord.setDeviceLocationX(null);
        // 报警装置坐标纬度
        vxECDVxAlertRecord.setDeviceLocationY(null);
        // 报警内容
        vxECDVxAlertRecord.setAlarmContent(item.getDescription());
        // 实时值
        try {
            vxECDVxAlertRecord.setRealTimeValue(new BigDecimal(item.getAlarmValue()));
        } catch (Exception e) {
            log.error("报警值转换失败", e);
        }
        // 报警持续时间（单位秒）
        if (item.getDurationSecond() != null) {
            vxECDVxAlertRecord.setDuration(new Integer(item.getDurationSecond().toString()));
        }
        AmPolicyDTO amPolicyDTO = map_AmPolicy.get(item.getAlarmId());
        if (amPolicyDTO != null) {
            // 上限
            vxECDVxAlertRecord.setHv(amPolicyDTO.getHv());
            // 上上限
            vxECDVxAlertRecord.setHhv(amPolicyDTO.getHhv());
            // 下下限
            vxECDVxAlertRecord.setLlv(amPolicyDTO.getLlv());
            // 下限
            vxECDVxAlertRecord.setLv(amPolicyDTO.getLv());
            //单位名称
            vxECDVxAlertRecord.setUnitName(amPolicyDTO.getUnitName());
        }
        // recordId
        vxECDVxAlertRecord.setRecordId(item.getRecordId());
        //报警点名
        vxECDVxAlertRecord.setAlarmName(item.getAlarmName());
        //报警状态
        vxECDVxAlertRecord.setAlarmState(item.getAlarmState());
        //报警解除时间
        vxECDVxAlertRecord.setLifeTime(item.getLifeTime());
        //持续天数
        vxECDVxAlertRecord.setDurationdays(item.getDurationDays());
        //持续时间
        vxECDVxAlertRecord.setDurationTime(item.getDurationTime());
        //报警类型
        vxECDVxAlertRecord.setAlarmType(item.getAlarmType());
        //报警等级
        vxECDVxAlertRecord.setAlarmLevel(item.getAlarmLevel());
        //报警解除方式
        vxECDVxAlertRecord.setEndType(item.getEndType());
        //解除值
        vxECDVxAlertRecord.setEndValue(item.getEndValue());
        //结束时阀值
        vxECDVxAlertRecord.setLifeValue(item.getLifeValue());
        //报警来源
        vxECDVxAlertRecord.setAlarmOrigin(item.getAlarmOrigin());
        //设置报警时阀值
        vxECDVxAlertRecord.setLimitValue(item.getLimitValue());
        vxECDVxAlertRecord.setCid(cid);
        vxECDVxAlertRecord.setAlarmTableSource(ALARM_TABLE_SOURCE_HISTORY_TABLE);
        log.error("退出转为历史记录转PO方法");
    }

}
