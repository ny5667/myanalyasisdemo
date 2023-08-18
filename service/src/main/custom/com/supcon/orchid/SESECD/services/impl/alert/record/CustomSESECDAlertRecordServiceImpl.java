package com.supcon.orchid.SESECD.services.impl.alert.record;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.SESECD.model.vo.GTSAlarmVO;
import com.supcon.orchid.SESECD.model.vo.ReadTagsVO;
import com.supcon.orchid.SESECD.model.vo.TagDataVO;
import com.supcon.orchid.SESECD.daos.SESECDAlertRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDSignalConfigDao;
import com.supcon.orchid.SESECD.daos.SESECDTagConfigDao;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.entities.SESECDTagConfig;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordService;
import com.supcon.orchid.SESECD.services.converged.comm.CustomSESECDCommunicationService;
import com.supcon.orchid.SESECD.utils.DateLocalUtils;
import com.supcon.orchid.SESECD.utils.HttpRequestUtil;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.BAPException;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.utils.DateUtils;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xulong2
 * @create: 2021-03-30 09:06
 * @description
 **/
@Service("CustomSESECDAlertRecordServiceImpl")
@Transactional
public class CustomSESECDAlertRecordServiceImpl extends BaseServiceImpl implements CustomSESECDAlertRecordService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private SESECDTagConfigDao tagConfigDao;
    @Autowired
    private CustomSESECDCommunicationService communicationService;
    @Autowired
    private SESECDAlertRecordDao alertRecordDao;
    @Autowired
    private StaffService staffService;
    @Autowired
    private SESECDAlmAlarmRecordDao alarmRecordDao;
    @Autowired
    private SqlUtils sqlUtils;
    @Autowired
    private SESECDSignalConfigDao signalConfigDao;


    private final static String HTTP_PREFIX = "/StdDataService/REST/";
    private final static String READ = "ReadTagsSync";      //
    private final static String WRITE = "WriteTagsSync";


    @Override
    public Result<String> automaticAlarmByStandard(String companyCode) {
        if (StringUtils.isEmpty(companyCode)) {
            throw new BAPException("公司编码为空!");
        }
        // long cid = companyService.getCompanyByCode(companyCode).getId();
        long cid = Objects.nonNull(companyService.getCompanyByCode(companyCode)) ? companyService.getCompanyByCode(companyCode).getId() : 1000;
        //获取位号配置
        List<SESECDSignalConfig> configList = getSignalConfig("SESECD_signalType/002", cid);
        if (Objects.isNull(configList)) {
            throw new BAPException("无标准数据服务接口地址配置!");
//            return Result.data("无标准数据服务接口地址配置!");
        }
        List<SESECDTagConfig> tagConfigList = getSESECDTagConfigList("SESECD_tagType/001", cid);
        if (Objects.isNull(tagConfigList) || tagConfigList.size() == 0) {
            throw new BAPException("无位号报警配置!");
//            return Result.data("无位号报警配置!");
        }
        //根据子公司分组
        Map<Long, SESECDSignalConfig> cidConfigMap = configList.stream().collect(Collectors.toMap(e -> e.getCid(), e -> e, (k1, k2) -> k2));
        for (SESECDSignalConfig config : cidConfigMap.values()) {
            Map<String, SESECDTagConfig> tagMap = tagConfigList.stream().filter(e -> config.getCid().equals(e.getCid())).collect(Collectors.toMap(e -> e.getTagName(), e -> e, (k1, k2) -> k2));
            if (Objects.isNull(tagMap) || tagMap.size() == 0) {
                continue;
            }
            //读取位号值
            List<TagDataVO> tagDataList = readTagValue(config, tagConfigList);
            if (Objects.isNull(tagConfigList)) {
                log.error("标准数据服务位号值读取失败，请检查接口是否正常!");
                continue;
//            return Result.data("标准数据服务位号值读取失败，请检查接口是否正常!");
            }
            saveAlertRecord(tagMap, tagDataList, config.getAlarmTime(), cid);
        }
        return Result.data("ok");
    }


    public List<SESECDSignalConfig> getSignalConfig(String type, long cid) {
        String sql = "from " + SESECDSignalConfig.JPA_NAME + " WHERE VALID = 1 AND ENABLE = 1 AND signalType = '" + type + "' " + sqlUtils.getSqlPartByCID(cid);
        List<SESECDSignalConfig> list = signalConfigDao.findByHql(sql);
        if (Objects.isNull(list) || list.size() == 0) {
            return null;
        }
        return list;
    }


    /**
     * 保存报警信息
     *
     * @param tagMap
     * @param tagDataList
     * @param alarmTime
     * @return
     */
    private Result<String> saveAlertRecord(Map<String, SESECDTagConfig> tagMap, List<TagDataVO> tagDataList, Integer alarmTime, long cid) {
        //查询重复报警时间内产生的报警
        String sql = "from " + SESECDAlertRecord.JPA_NAME + " where valid = 1 and tagType = 'SESECD_tagType/001' and cid = " + cid + " and alarmTime > ?0 ORDER BY alarmTime desc";
        List<SESECDAlertRecord> recordList = alertRecordDao.findByHql(sql, new Object[]{DateLocalUtils.getNextDate(new Date(), -1)});
        //CollectionUtils.isEmpty(recordList){
        //  Result.data("报警数据为空");
        //   }

//        if (recordList.size() == 0 || Objects.isNull(recordList)) {
//            return Result.data("报警数据为空");
//        }
        for (TagDataVO vo : tagDataList) {
            SESECDTagConfig tag = tagMap.get(vo.getName());
            if (Objects.isNull(tag)) {
                log.error(">>> 找不到对应位号:{}，该步报警路过", vo.getName());
                continue;
            }
            double value;
            double upper;
            double lower;
            try {
                value = Double.valueOf(vo.getValue());
                upper = tag.getUpperLimit().doubleValue();
                lower = tag.getLowerLimit().doubleValue();
            } catch (Exception e) {
                log.error(">>> 单位转换报错！", e);
                continue;
            }
            //读取的值不在警告范围内报警
            if (value >= lower && value <= upper) {
                log.error(">>> 位号:{}，值为正常，跳过。", vo.getName());
                continue;
            }
            Date date = stringDataToData(vo.gettTimeStamp());
            //重复报警间隔
            SESECDAlertRecord record =
                    recordList.stream().filter(e -> Objects.nonNull(e.getTagName()) && e.getTagName().equals(tag.getTagName())).findFirst().orElse(null);
            if (Objects.nonNull(record) && Objects.nonNull(alarmTime)) {
                if (date.getTime() - record.getAlarmTime().getTime() < alarmTime * 1000) {
                    log.error(">>> 位号：{}。在报警间隔时间内，不处理跳过。", vo.getName());
                    continue;
                }
            }
            SESECDAlertRecord alertRecord = getAlertRecord(tag, tag.getCid(), vo.getValue());
            alertRecord.setAlarmTime(date);
            alertRecordDao.save(alertRecord);
            alertRecordDao.flush();
        }

        return Result.data("ok!");

    }

    /**
     * 将位号返回的时间返回Date格式
     *
     * @param timeStamp
     * @return
     */
    private Date stringDataToData(String timeStamp) {
        if (StringUtils.isEmpty(timeStamp)) {
            return null;
        }
        timeStamp = timeStamp.substring(6, timeStamp.length() - 7);
        return new Date(Long.valueOf(timeStamp));
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return format.format(date);
    }

    private SESECDAlertRecord getAlertRecord(SESECDTagConfig tag, long cid, String value) {
        SESECDAlertRecord record = new SESECDAlertRecord();
        record.setCode(DateUtils.format(new Date(), "yyyyMMdd") + "_" + LocalTime.now().toString().replace(":", "").replace(".", ""));
        record.setUpperLimit(tag.getUpperLimit());
        record.setLowerLimit(tag.getLowerLimit());
        record.setTagAddress(tag.getTagAddress());
        record.setTagName(tag.getTagName());
        SystemCode type = new SystemCode();
        type.setId("SESECD_tagType/001");
        record.setTagType(type);
        SystemCode status = new SystemCode();
        status.setId("SESECD_alarmStaus/001");
        record.setAlarmStaus(status);
        record.setCid(cid);
        Company load = companyService.load(cid);

        record.setAlarmValue(value);
        return record;
    }


    /**
     * 根据位号配置读取标准数据服务中的位号值
     *
     * @param signalConfig
     * @param tagConfigList
     * @return
     */
    private List<TagDataVO> readTagValue(SESECDSignalConfig signalConfig, List<SESECDTagConfig> tagConfigList) {
        try {
            List<String> tagNameList = tagConfigList.stream().map(SESECDTagConfig::getTagName).collect(Collectors.toList());
            String url = "http://" + signalConfig.getIp() + ":" + signalConfig.getPort() + HTTP_PREFIX + READ;
            Map<String, Object> localMap = new HashMap<>();
            localMap.put("names", tagNameList);

            log.error(">>> 请求测点地址：{}", url);
            log.error(">>> 请求测点参数：{}", new ObjectMapper().writeValueAsString(localMap));

            String json = HttpRequestUtil.sendHttpPost(url, JSON.toJSONString(localMap), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
            log.error(">>> 请求测点返回：{}", json);

            ReadTagsVO readTagsVO = JSON.parseObject(json, ReadTagsVO.class);
            return readTagsVO.getReadTagsSyncResult();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }


    /**
     * 获取位号配置
     *
     * @param type
     * @param cid
     * @return
     */

    private List<SESECDTagConfig> getSESECDTagConfigList(String type, long cid) {
        String sql = "from " + SESECDTagConfig.JPA_NAME + " where valid = 1 and tagType = '" + type + "' " + sqlUtils.getSqlPartByCID(cid);
        return tagConfigDao.findByHql(sql);
    }

    @Override
    public Result<String> createAlarmRecordByFireGasOrPoisonGas(String status, Long deviceId, Long alarmTime) {
        switch (status) {
            case "1":
                //return updateAlertStatus(ids,"SESECD_alarmStaus/003");
            case "2":
                // return updateAlertStatus(ids,"SESECD_alarmStaus/004");
            case "3":
                SESECDAlmAlarmRecord alarmRecord = initAlarmRecord(deviceId, alarmTime);
                if (null != alarmRecord.getAccidentName()) {
                    alarmRecordDao.merge(alarmRecord);
                    alarmRecordDao.flush();
                }
                return Result.data("ok");
            default:
                return Result.data(203, "参数异常!", "");
        }
    }

    private SESECDAlmAlarmRecord initAlarmRecord(Long deviceId, Long alarmTime) {
        SESECDAlmAlarmRecord record = new SESECDAlmAlarmRecord();
        String sql = "select  dt.NAME as dtName,f.name as fName ,f.FREQUENT_PLACE as fPlace,d.GAS as dGas" +
                " from  SM_ALARM_FACILITIES d " +
                " left join BASE_DEPARTMENT dt " +
                " on  d.ORG = dt.ID " +
                " left join SESGISCONFIG_FREQUENTPLACES f" +
                " on d.DEVICE_POSITION = f.ID" +
                " where d.ID = " + deviceId;
        log.info("initAlarmRecord sql:{}", sql);
        List<Object[]> alarmList = alertRecordDao.createNativeQuery(sql).list();
        if (CollectionUtils.isEmpty(alarmList)) {
            return record;
        }
        Object[] alarm = alarmList.get(0);
        log.info("alarm:{}", JSON.toJSONString(alarm));
        //“所属单位”+“报警区域”+发生+“气体名称”++“泄露”，例如，当气化装置的1#气化

        StringBuffer accidentName = new StringBuffer();
        if (null != alarm[0]) {
            accidentName.append(alarm[0]);
        }
        if (null != alarm[1]) {
            accidentName.append(alarm[1]);
        }
        accidentName.append("发生");
        if (null != alarm[3]) {
            accidentName.append(alarm[3]);
        }
        accidentName.append("泄露");
        //报警事件名称
        record.setAccidentName(accidentName.toString());
        //报警事件发生时间
        Date happenTime = new Date(alarmTime);
        record.setHappenTime(happenTime);
        //事件描述
        record.setDescription(accidentName.toString());
        //事发地点
        if (null != alarm[1]) {
            record.setPosition(alarm[1].toString());
        }
        if (null != alarm[2]) {
            //坐标
            record.setMapPoint(alarm[2].toString());
        }
        //其他属性
        record.setRectime(new Date());
        record.setIsSimulation(false);
        record.setIsIncident(false);
        Staff staff = staffService.load(getCurrentStaff().getId());
        record.setReceiver(staff);

        return record;

    }


    @Override
    public Result<String> updateAlertRecordStatus(List<Long> ids, String status, String alarmType) {
        if (Objects.isNull(ids) || ids.size() == 0 || StringUtils.isEmpty(status)) {
            return Result.data(203, "参数异常!", "参数异常!");
        }

        switch (status) {
            case "1":
                return updateAlertStatus(ids, "SESECD_alarmStaus/003");
            case "2":
                return updateAlertStatus(ids, "SESECD_alarmStaus/004");
            case "3":
                return createAlarmRecord(ids, alarmType);
            default:
                return Result.data(203, "参数异常!", "");
        }
    }


    /**
     * 生成接警记录
     *
     * @param ids
     * @param alarmType
     * @return
     */
    private Result<String> createAlarmRecord(List<Long> ids, String alarmType) {
        for (Long id : ids) {
            if ("1".equals(alarmType)) {
                SESECDAlertRecord load = alertRecordDao.load(id);
                if (Objects.isNull(load)) {
                    continue;
                }
                SESECDAlmAlarmRecord alarmRecord = getAlarmRecord(load);
                alarmRecord.setIsOver(false);
                SystemCode code = new SystemCode();
                code.setId("SESECD_alarmStaus/002");
                load.setAlarmStaus(code);
                alertRecordDao.merge(load);
                alertRecordDao.flush();
                alarmRecordDao.merge(alarmRecord);
                alarmRecordDao.flush();
            }
        }
        return Result.data("ok");
    }


    /**
     * 根据报警生成接警
     *
     * @param alertRecord
     * @return
     */
    private SESECDAlmAlarmRecord getAlarmRecord(SESECDAlertRecord alertRecord) {
        //TODO 生成接警事件
        SESECDAlmAlarmRecord record = new SESECDAlmAlarmRecord();
        record.setAccidentName(alertRecord.getCode() + "_生成接警事件");
        record.setHappenTime(alertRecord.getAlarmTime());
        record.setRectime(new Date());
        record.setIsSimulation(false);
        record.setIsIncident(false);
        record.setPosition(alertRecord.getTagAddress());
        StringBuilder builder = new StringBuilder();
        builder.append("报警装置：").append(alertRecord.getTagName()).append(",报警内容：").append(alertRecord.getAlarmValue());
        if ("SESECD_tagType/001".equals(alertRecord.getTagType().getId())) {
            builder.append(",报警下限：").append(alertRecord.getLowerLimit()).append(",报警上限：").append(alertRecord.getUpperLimit());
        }
        record.setDescription(builder.toString());
        Staff staff = staffService.load(getCurrentStaff().getId());
        record.setReceiver(staff);
        record.setAlertRecordId(alertRecord);
        record.setHpnCompany(companyService.load(alertRecord.getCid()));
        return record;
    }


    /**
     * 批量修改报警记录状态，修改为误报或者重复报警
     *
     * @param ids
     * @param status
     * @return
     */
    private Result<String> updateAlertStatus(List<Long> ids, String status) {
//        String sqlIds = ids.toString().substring(1,ids.toString().length()-1);
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(SESECDAlertRecord.TABLE_NAME).append(" set ALARM_STAUS = '")
                .append(status).append("' where id in (:ids)");
        NativeQuery nativeQuery = alertRecordDao.createNativeQuery(sql.toString());
        nativeQuery.setParameter("ids", ids);
        nativeQuery.executeUpdate();
        return Result.data("ok");
    }


    @Override
    public void GTSFireAlarm(GTSAlarmVO vo) {
        //非火灾报警不记录
        if (!"1".equals(vo.getWarnType())) {
            return;
        }
        String sql = "from " + SESECDTagConfig.JPA_NAME + " WHERE VALID = 1 AND tagType = 'SESECD_tagType/002' and tagName = '" + vo.getDevId() + "' " + sqlUtils.getSqlPartByCID();
        List<SESECDTagConfig> configList = tagConfigDao.findByHql(sql);
        if (Objects.isNull(configList) || configList.size() == 0) {
            return;
        }
        SESECDTagConfig tag = configList.get(0);
        SESECDSignalConfig signalConfig = communicationService.getSignalConfig("SESECD_signalType/003", 1000);
        Date happenTime = DateLocalUtils.string2Date(vo.getHappenTime(), DateLocalUtils.YYYY_MM_DD_HH_MM_SS);
        if (Objects.nonNull(signalConfig)) {
            //判断重复报警
            String recordSql = "from " + SESECDAlertRecord.JPA_NAME + " where valid = 1 " + sqlUtils.getSqlPartByCID() + " and tagType = 'SESECD_tagType/002' and tagName = '" + vo.getDevId() + "' and alarmTime > ?0 ORDER BY alarmTime desc";
            List<SESECDAlertRecord> recordList = alertRecordDao.findByHql(recordSql, new Object[]{DateLocalUtils.getNextDate(new Date(), -1)});
            if (Objects.nonNull(recordList) && recordList.size() > 0) {
                //在重复报警时间内不报警
                if (happenTime.getTime() - recordList.get(0).getAlarmTime().getTime() < signalConfig.getAlarmTime() * 1000) {
                    return;
                }
            }
        }
        SESECDAlertRecord alertRecord = getAlertRecord(tag, 1000, vo.getWarnContent());
        alertRecord.setAlarmTime(happenTime);
        alertRecord.setWarnId(vo.getWarnId());
        alertRecordDao.save(alertRecord);
        alertRecordDao.flush();
    }

}
