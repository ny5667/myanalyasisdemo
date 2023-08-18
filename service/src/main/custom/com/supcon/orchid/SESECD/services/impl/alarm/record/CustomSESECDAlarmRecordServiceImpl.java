package com.supcon.orchid.SESECD.services.impl.alarm.record;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEmcActionDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.model.vo.MapVO;
import com.supcon.orchid.SESECD.model.vo.alarm.AlarmRecordVO;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmService;
import com.supcon.orchid.SESECD.services.alarm.video.CustomSESECDAlarmVideoService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.SESECD.utils.EventNumUtils;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.SESED.entities.SESEDPlan;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigFrequentplaceDTO;
import com.supcon.orchid.SESGISConfig.client.ISESGISConfigFrequentplaceClient;
import com.supcon.orchid.SESWssEP.client.ISESWssEPEmePeopleGroupClient;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.supfusion.ws.client.NoticeApiClient;
import com.supcon.supfusion.ws.client.dto.WebSocketResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("CustomSESECDAlarmRecordService")
@Transactional
public class CustomSESECDAlarmRecordServiceImpl extends BaseServiceImpl<SESECDAlmAlarmRecord> implements CustomSESECDAlarmRecordService {

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;
    @Autowired
    private SESECDEmcActionDao actionDao;

    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;

    @Autowired
    private ISESWssEPEmePeopleGroupClient emePeopleGroupClient;
    @Autowired
    private SqlUtils sqlUtils;
    @Autowired
    private ISESGISConfigFrequentplaceClient frequentplaceClient;
    @Autowired
    private NoticeApiClient noticeApiClient;
    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    @Autowired
    private CustomSESECDAlarmService customSESECDAlarmService;

    @Autowired
    private CustomSESECDAlarmActionService customSESECDAlarmActionService;

    @Autowired
    private CustomSESECDAlarmVideoService customSESECDAlarmVideoService;

    @Override
    public AlarmRecordVO getAlarmRecordByPlanId(Long id) {
        AlarmRecordVO alarmRecordVO = new AlarmRecordVO();
        List<SESECDAlmAlarmRecord> alarmRecords = almAlarmRecordDao.findByHql("from " + SESECDAlmAlarmRecord.JPA_NAME + " where valid = 1 and drillPlanId = " + id);
        if (CollectionUtils.isNotEmpty(alarmRecords)) {
            SESECDAlmAlarmRecord sesecdAlmAlarmRecord = alarmRecords.get(0);
            Date rectime = sesecdAlmAlarmRecord.getRectime();
            alarmRecordVO.setRectime(DateUtils.date2Str(rectime, "yyyy-MM-dd HH:mm:ss"));
            Date overtime = sesecdAlmAlarmRecord.getOverTime();
            if (overtime != null) {
                String overTime = DateUtils.date2Str(overtime, "yyyy-MM-dd HH:mm:ss");
                alarmRecordVO.setOverTime(overTime);
            }
        }

        return alarmRecordVO;
    }

    /**
     * 通过接警Id获取通讯组Ids
     *
     * @param alarmRecordId
     * @return
     */
    @Override
    public List<Long> getSectionIdListByAlarmRecordId(Long alarmRecordId) {
        List<SESECDEmePlanObj> SESECDEmePlanObjs = emePlanObjDao.findByHql("from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and ALARM_ID = ?0", new Object[]{alarmRecordId});
        if (SESECDEmePlanObjs == null || SESECDEmePlanObjs.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> planIdList = SESECDEmePlanObjs.stream().map(plan -> plan.getPlanObj().getId().toString()).distinct().collect(Collectors.toList());
        List<Long> sectionIdList = emePeopleGroupClient.getSectionIdListByPlanIds(String.join(",", planIdList));
        return sectionIdList;
    }


    @Override
    public Result getCommonPlaceById(Long id) {
        Result result = new Result();
//        CommonPlaceAndChannelVO commonPlaceAndChannelVO = new CommonPlaceAndChannelVO();
//        List<CameraVO> cameraVOS = new ArrayList<>();
//        //调用华谊定制模块 查询常用地址和摄像头之间的配置关系
//        SESECDAlmAlarmRecord alarmRecord = almAlarmRecordDao.load(id);
//        SESGISConfigFrequentplace locationIncident = alarmRecord.getLocationIncident();
//        //获取常用地址和摄像头关联配置
//        StringBuffer sb = new StringBuffer();
//        sb.append("FROM ").append(GXHYDZPlaceConfig.JPA_NAME).append(" WHERE VALID  = 1 AND FPID = ").append(locationIncident.getId());
//        List<GXHYDZPlaceConfig> placeConfigList = almAlarmRecordDao.findByHql(sb.toString());
//        if(placeConfigList.isEmpty()){
//            result.setCode(200);
//            result.setMessage("当前常用地址无摄像头配置");
//            return result;
//        }
//        GXHYDZPlaceConfig placeConfig = placeConfigList.get(0);
//        sb.delete(0,sb.length());
//        sb.append("FROM ").append(GXHYDZWebcamConfig.JPA_NAME).append(" WHERE VALID  = 1 AND  PLACE_ID = ").append(placeConfig.getId());
//        List<GXHYDZWebcamConfig>  webcamConfigList = almAlarmRecordDao.findByHql(sb.toString());
//
//        //获取摄像头信息
//        webcamConfigList.stream().forEach(web ->{
//            CameraVO cameraVO = new CameraVO();
//            cameraVO.setChannel(web.getWebcamId().getChannel());
//            cameraVO.setName(web.getWebcamId().getName());
//            cameraVO.setPoint(web.getWebcamId().getPoint());
//            cameraVOS.add(cameraVO);
//        });
//        commonPlaceAndChannelVO.setCommonPlaceId(placeConfig.getId());
//        commonPlaceAndChannelVO.setCommonPlaceName(placeConfig.getFpname());
//        commonPlaceAndChannelVO.setCameraVOList(cameraVOS);
//        result.setData(commonPlaceAndChannelVO);
        result.setCode(200);
        return result;
    }

    @Override
    public Result getDevicePositionMap(Long id) {
        Result result = new Result();
        result.setCode(200);
        log.info("getDevicePositionMap positionId:{}", id);
        SESGISConfigFrequentplaceDTO sesgisConfigFrequentplace = frequentplaceClient.getSESGISConfigFrequentplace(id);
        log.info("getDevicePositionMap frequentplace:{}", JSON.toJSONString(sesgisConfigFrequentplace));
        result.setData(sesgisConfigFrequentplace);
        MapVO mapVO = JsonHelper.parseJson(sesgisConfigFrequentplace.getFrequentPlace(), MapVO.class);
        result.setData(mapVO);
        return result;
    }

    @Override
    public Result sendWsMsg(Long id) {
        Result result = new Result();
        String tenantId = RpcContext.getContext().getTenantId();
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = "dt";
        }
        WebSocketResponseDTO gatesCustom = noticeApiClient.pushTopicMessages("commonPlace", tenantId, id);
        result.setData(gatesCustom);
        result.setCode(200);
        return result;
    }

    /**
     * 根据接警找到预案，再根据预案找到关联通讯录，找到通讯录内所有人，返回人员编码
     *
     * @param alarmRecord 接警
     * @return 人员编码
     */
    @Override
    public List<String> getStaffCodes(SESECDAlmAlarmRecord alarmRecord) {
        //关联预案

        List<SESECDEmePlanObj> emePlanObjs = almAlarmRecordDao.findByHql("from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{alarmRecord});
        if (emePlanObjs.isEmpty()) {
            return null;
        }
        String planIds = "";
        for (SESECDEmePlanObj sesecdEmePlanObj : emePlanObjs) {
            if (null != sesecdEmePlanObj && null != sesecdEmePlanObj.getPlanObj()) {
                planIds = sesecdEmePlanObj.getPlanObj().getId() + ",";
            }
        }
        if (planIds.length() > 0) {
            planIds = planIds.substring(0, planIds.length() - 1);
        }
        List<Long> staffIdsByPlanIds = emePeopleGroupClient.getStaffIdsByPlanIds(planIds, getCurrentCompanyId());
        Set<String> staffCodes = new HashSet<>();
        if (null != staffIdsByPlanIds && staffIdsByPlanIds.size() > 0) {
            for (Long id : staffIdsByPlanIds) {
                String code = staffService.get(id).getCode();
                staffCodes.add(code);
            }
        }
        return new ArrayList<>(staffCodes);
    }

    /*---------------------------------------------平台事件方法-开始-------------------------------------------------*/


    @Override
    public void beforeSaveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects) {
        if (null != almAlarmRecord) {
            if (Objects.isNull(almAlarmRecord.getIsIncident())) {
                almAlarmRecord.setIsIncident(false);
            }

            //生成事件编号
            if (StringUtils.isBlank(almAlarmRecord.getEventNo())) {
                String eventNum = EventNumUtils.getEventNum();
                almAlarmRecord.setEventNo(eventNum);
            }
        }
    }

    @Override
    public void afterSaveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects) {
        log.error("进入接到报警保存后事件");
        //先删除坐标，避免保存报错
        String spatialId = almAlarmRecord.MODEL_CODE + "_" + almAlarmRecord.getId();
        try {
            customSESECDSavePointService.batchDeleteFeatureInfo(spatialId, ConstDict.warningLayer);
            log.error("删除接到报警图层成功");
            log.error("spatialId:" + spatialId);
            log.error("layer:" + ConstDict.warningLayer);
        } catch (Exception e) {
            throw new RuntimeException("删除接到警报图层报错", e);
        }
        try {
            customSESECDSavePointService.batchDeleteFeatureInfo(spatialId, ConstDict.incidentLayer);
            log.error("删除应急事件图层成功");
            log.error("spatialId:" + spatialId);
            log.error("layer:" + ConstDict.incidentLayer);
        } catch (Exception e) {
            throw new RuntimeException("删除应急事件图层报错", e);
        }

        if(almAlarmRecord.getVersion() == 1){
            log.error("事件生成开始通知");
            customSESECDAlarmService.publishMsg(almAlarmRecord,false);
        }
//        customSESECDAlarmVideoService.generateCctvRecord(almAlarmRecord,objects);
        //根据预案设置指令
        customSESECDAlarmActionService.generateInstructions(almAlarmRecord);

        //TODO  如果关联了演练计划 将演练计划状态设置为 执行中
        SESEDPlan drillPlanId = almAlarmRecord.getDrillPlanId();
        if(null !=drillPlanId ){
            customSESECDAlarmService.updateDrillPlanState(drillPlanId.getId(),almAlarmRecord.getHappenTime());
        }

        log.error("退出接到报警保存事件");
    }

    /*---------------------------------------------平台事件方法-结束-------------------------------------------------*/

}
