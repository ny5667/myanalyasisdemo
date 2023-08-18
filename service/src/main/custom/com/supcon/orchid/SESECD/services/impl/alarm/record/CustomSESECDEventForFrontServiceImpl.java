package com.supcon.orchid.SESECD.services.impl.alarm.record;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.model.vo.IdVO;
import com.supcon.orchid.SESECD.model.vo.alarm.alarmGisVO;
import com.supcon.orchid.SESECD.model.vo.common.CoordinateVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.model.vo.forfront.EventDetailVO;
import com.supcon.orchid.SESECD.model.vo.forfront.ScreenSendVO;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.common.file.CustomSESECDFileServerDocumentService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDEventForFrontService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.SESECD.utils.EventNumUtils;
import com.supcon.orchid.SESECD.utils.FileUtils;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESGISConfig.client.ICustomGisAnalysisClient;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.foundation.utils.ByteArrayMultipartFile;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.orchid.utils.UUIDUtil;
import com.supcon.supfusion.file.server.api.vo.DocumentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDEventForFrontServiceImpl extends BaseServiceImpl implements CustomSESECDEventForFrontService {

    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDEventForFrontServiceImpl.class);

    @Autowired
    private SESECDAlmAlarmRecordDao eventdao;


    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ICustomGisAnalysisClient iCustomGisAnalysisClient;

    @Autowired
    CustomSESECDAlarmRecordService customSESECDAlarmRecordService;

    @Autowired
    CustomSESECDAlarmActionService customSESECDAlarmActionService;

    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private CustomSESECDFileServerDocumentService fileServerDocumentService;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    @Autowired
    private SystemCodeService systemCodeService;

    @Override
    public List<EventDetailVO> listEvents(String accidentName) throws Exception {
        Long currentCompanyId = getCurrentCompanyId();
        if (null == currentCompanyId) {
            throw new Exception("cannot get cid");
        }
        logger.error("当前cid:{}", currentCompanyId);
        //查询所有事件
        StringBuilder alarmRecordHQL = new StringBuilder();
		/*
		String cidsql = sqlUtils.getSqlPartByCID();
		log.error(cidsql);
		 */
        //alarmRecordHQL.append(" from ").append(SESECDAlmAlarmRecord.JPA_NAME).append(" where valid = 1 ").append(cidsql);
        alarmRecordHQL.append(" from ").append(SESECDAlmAlarmRecord.JPA_NAME).append(" where valid = 1 and isOver = false ").append(" and cid = ").append(currentCompanyId);
        if (!StringUtils.isEmpty(accidentName)) {
            alarmRecordHQL.append(" AND ACCIDENT_NAME LIKE '%").append(accidentName).append("%'");
        }
        List<SESECDAlmAlarmRecord> eventPOs = eventdao.findByHql(alarmRecordHQL.toString());
        if (CollectionUtils.isEmpty(eventPOs)) {
            logger.error("查不到事件");
            return null;
        }
        logger.error("共查询到{}个事件", eventPOs.size());
        List<EventDetailVO> vos = eventPOs.stream().map(EventDetailVO::EventPO2VO).collect(Collectors.toList());
        return vos;
    }


    @Override
    public EventDetailVO getEventDetail(Long eventId) {

        //查询事件
        StringBuilder alarmRecordHQL = new StringBuilder();
        alarmRecordHQL.append(" from ").append(SESECDAlmAlarmRecord.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(eventId);
        List<SESECDAlmAlarmRecord> eventPOs = eventdao.findByHql(alarmRecordHQL.toString());//应该是只有1个的，保险起见
        if (CollectionUtils.isEmpty(eventPOs)) {
            logger.error("查不到事件,id可能有误");
            return null;
        }
        SESECDAlmAlarmRecord eventPO = eventPOs.get(0);
        EventDetailVO result = EventDetailVO.EventPO2VO(eventPO);
        return result;
    }


    /**
     * 新增事件
     *
     * @param inputVO
     * @return
     */
    @Override
    public Result<String> addEvent(EventDetailVO inputVO) throws Exception {
        inputVO.setId(null);
        SESECDAlmAlarmRecord eventPO = new SESECDAlmAlarmRecord();
        //自动生成唯一编号
        eventPO.setEventNo(EventNumUtils.getEventNum());
        //处理事件数据来源
        ISystemCode serviceSystemCode;
        log.error("新增接警，上报来源："+inputVO.getSource());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(inputVO.getSource())){
            //SESECD_eventDataSource/001
            String codeId = "SESECD_eventDataSource/"+inputVO.getSource();
            serviceSystemCode = systemCodeService.getSystemCodeByID(codeId);
            if (null == serviceSystemCode){
                serviceSystemCode = systemCodeService.getSystemCodeByID("SESECD_eventDataSource/001");
            }
        }else {
            log.error("入参未指定数据来源：默认为人工报警");
            serviceSystemCode = systemCodeService.getSystemCodeByID("SESECD_eventDataSource/001");
        }
        eventPO.setEventDataSource((SystemCode)serviceSystemCode);

        eventPO.setAccidentName(inputVO.getAccidentName());//事件名称
        eventPO.setDescription(inputVO.getDescription());//事件描述
        eventPO.setRectime(new Date());//接警时间  当前时间
        //接警人
        Staff receiver = new Staff();
        receiver.setId(inputVO.getReceiver().getId());
        eventPO.setReceiver(receiver);
        //处理状态
        //SystemCode processState = SystemCodeVO.CreateSystemCodePOById(inputVO.getProcessState().getId());
        SystemCode processState = SystemCodeVO.CreateSystemCodePOById("SESECD_processState/002");//写死 SESECD_processState/002 未处理  03处理中 04误报 01已解决 05重复报警
        eventPO.setProcessState(processState);
        //坐标 是必须的
        if (inputVO.getPoint() == null && null != inputVO.getAlarmId()) {
            log.error("进入根据报警点Id设置坐标点：");
            log.error(inputVO.getAlarmId() + "");
            setDefaultPointMap(inputVO);
        }
        if (null != inputVO.getPoint()) {
            eventPO.setPoint(JSON.toJSONString(inputVO.getPoint()));
            eventPO.setMapPoint(JSON.toJSONString(inputVO.getPoint()));
        }

        eventPO.setIsIncident(false);//是否应急事件 如果是true就到了应急事件了，而不是接警管理
        eventPO.setIsTurnAlarm(false);//是否归档


        eventPO.setHappenTime(DateUtils.MySimpleDateFormat.parse(inputVO.getHappenTime()));//发生事件
        //eventPO.setEventType(SystemCodeVO.CreateSystemCodePOById(inputVO.getEventType().getId()));
        eventPO.setEventType(SystemCodeVO.CreateSystemCodePOById("SESECD_eventType/001"));//001应急事件 002演练事件 写死
        //eventPO.setHpnCompany();

        logger.error("保存接警事件的日志:{}", JSON.toJSONString(eventPO));
        eventdao.save(eventPO);
        eventdao.flush();
        logger.error("保存接警事件完成,id:{}", eventPO.getId());

        //保存关联预案
        savePlanIds(inputVO, eventPO);

        //开始保存点位数据
        logger.error("开始保存点");
        String spatialId = SESECDAlmAlarmRecord.MODEL_CODE + "_" + eventPO.getId();
        if (null != inputVO.getPoint()) {
            customSESECDSavePointService.savePoint2PG(spatialId, inputVO.getPoint(), ConstDict.warningLayer);
        }
        return Result.data(spatialId);
    }


    /**
     * 截图发送
     *
     * @param screenSendVO
     */
    @Override
    public void screenshotSend(ScreenSendVO screenSendVO) throws IOException {
        //截取base64码
        String[] split = screenSendVO.getBase64().split(",");
        String decode = split[split.length - 1];
        //base64转file
        File file = FileUtils.base64ToFile(decode, "screen-" + UUIDUtil.getUUID() + ".png");
        //tip:使用平台附件时，构造MultipartFile的name属性一定是file 否则平台附件失败
        ByteArrayMultipartFile byteArrayMultipartFile = FileUtils.byteArrayMultipartFileBuild(file);
        //保存文件并返回路径
        DocumentVO documentVO = fileServerDocumentService.save(byteArrayMultipartFile, screenSendVO.getEventId());
        String path = documentVO.getPath();
        //path: /3388106504127696/1684313862089/test.png
        //参考： 文件真正访问地址http://192.168.220.77:8080/inter-api/file-server/v1/file/pdfStreamHandeler?id=3388106504127696&filePath=/3388106504127696/1684313862089/test.png
        log.error("screenshotSend=====path: " + path);
        if (!CollectionUtils.isEmpty(screenSendVO.getStaffCodes())) {
            SESECDAlmAlarmRecord alarmRecord = eventdao.get(screenSendVO.getEventId());
            String completePath = "/inter-api/file-server/v1/file/pdfStreamHandeler?id=" + screenSendVO.getEventId() + "&filePath=" + path;
            log.error("screenshotSend=====completePath: " + completePath);
            Map<String, String> modelParams = new HashMap<>();
            modelParams.put("path", completePath);
            modelParams.put("content", alarmRecord.getAccidentName());
            MsgModelDTO msgModelDTO = MsgModelDTO.builder().msgType(ConstDict.SCREENSHOT).receivers(screenSendVO.getStaffCodes()).param(modelParams).build();
            notifyFacade.handleNotify(msgModelDTO);
        }
    }

    //-----------------------------------------------公共方法区---------------------------------------------


    /**
     * 设置默认点数据/根据报警去关联查询
     *
     * @param inputVO
     * @throws IOException
     */
    private void setDefaultPointMap(EventDetailVO inputVO) throws IOException {
        if (inputVO.getAlarmId() == null) {
            return;
        }
        String sql = "select t.alarmId,t.gisMap from ( SELECT shp.POINT_ID alarmId, shi.GISMAP gisMap from SES_HD_POINTREL shp left join SES_HD_INFOS shi on shi.id = shp.HAZARD_INFO_ID where shp.VALID = 1 and shi.VALID = 1 union SELECT saip.ALARM_POINT alarmId, saf.GIS_MAPN gisMap from SM_ALARM_ITEM_POINTS saip left join SM_ALARM_FACILITIES saf on saf.id = saip.FACI_ID WHERE saip.VALID = 1 and saf.VALID = 1 ) t where t.alarmId = :alarmId";
        Map<String, Object> map = new HashMap<>();
        map.put("alarmId", inputVO.getAlarmId());
        List<alarmGisVO> alarmGisVOS = namedParameterJdbcTemplate.query(sql, map, new BeanPropertyRowMapper<>(alarmGisVO.class));
        log.error("查找到关联报警点数据条数：");
        log.error(alarmGisVOS.size() + "");
        if (alarmGisVOS.isEmpty()) {
            return;
        }
        alarmGisVO alarmGisVO = alarmGisVOS.get(0);
        String EXAMPLE_JSON = alarmGisVO.getGisMap();
        if (EXAMPLE_JSON == null || EXAMPLE_JSON.isEmpty()) {
            return;
        }
        CoordinateVO coordinateVO = null;

        ObjectMapper objectMapper = new ObjectMapper();
        Object obj = objectMapper.readValue(EXAMPLE_JSON, Object.class);
        if (obj instanceof Map) {
            Map<String, Object> map1 = (Map<String, Object>) obj;
            Object coordinates = map1.get("coordinates");

            Map o = (HashMap) ((ArrayList) coordinates).get(0);
            String lon = o.get("lon").toString();
            String lat = o.get("lat").toString();
            String hei = o.get("hei").toString();
            Float aFloat = new Float(hei);

            coordinateVO = new CoordinateVO(lon, lat, aFloat);
        }
        log.error("坐标信息：");
        if (coordinateVO != null) {
            log.error(JsonHelper.writeValue(coordinateVO));
        }
        if (coordinateVO != null) {
            List<CoordinateVO> coordinateVOS = Collections.singletonList(coordinateVO);
            PointVO pointVO = new PointVO("warningLayer", coordinateVOS);
            inputVO.setPoint(pointVO);
        }
    }


    /**
     * 保存关联预案
     *
     * @param inputVO 前端VO
     * @param eventPO 接警PO
     * @throws JsonProcessingException
     */
    private void savePlanIds(EventDetailVO inputVO, SESECDAlmAlarmRecord eventPO) throws JsonProcessingException {
        //如果预案id为空，则设置为所有预案的id
        String sql_plan = "SELECT id from SES_EME_PLANS where valid = 1";
        List<IdVO> planIds = jdbcTemplate.query(sql_plan, new BeanPropertyRowMapper<>(IdVO.class));
        List<Long> collect1 = planIds.stream().map(IdVO::getId).collect(Collectors.toList());
        inputVO.setPlanIds(collect1);

        if (inputVO.getPlanIds() == null || inputVO.getPlanIds().isEmpty()) {
            return;
        }
        List<Long> collect = inputVO.getPlanIds().stream().distinct().collect(Collectors.toList());
        String sql = "SELECT id from SES_EME_PLANS where id in (:ids)";
        SqlParameterSource parameters = new MapSqlParameterSource("ids", collect);
        List<IdVO> query = namedParameterJdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(IdVO.class));
        log.error("预案传入的id数据：");
        log.error(new ObjectMapper().writeValueAsString(inputVO.getPlanIds()));
        Assert.isTrue(query.size() == inputVO.getPlanIds().size(), "预案传的id不正确！");

        for (Long planId :
                inputVO.getPlanIds()) {
            SESECDEmePlanObj emePlanObj = ConvertToPO_EmePlan(eventPO, planId);
            emePlanObjDao.save(emePlanObj);
            emePlanObjDao.flush();
        }

        //生成指令
        customSESECDAlarmActionService.generateInstructions(eventPO);
    }

    /**
     * 获取接警关联预案实体
     *
     * @param eventPO
     * @param planId
     * @return
     */
    private SESECDEmePlanObj ConvertToPO_EmePlan(SESECDAlmAlarmRecord eventPO, Long planId) {
        SESECDEmePlanObj emePlanObj = new SESECDEmePlanObj();
        emePlanObj.setAlarmId(eventPO);
        SESWssEPEmergencyPlan emergencyPlan = new SESWssEPEmergencyPlan();
        emergencyPlan.setId(planId);
        emePlanObj.setPlanObj(emergencyPlan);
        return emePlanObj;
    }


}
