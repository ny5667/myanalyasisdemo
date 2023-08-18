package com.supcon.orchid.SESECD.services.impl.alarm.video;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.model.vo.common.CoordinateVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.map.GeometryAnalysisVO;
import com.supcon.orchid.SESECD.services.alarm.video.CustomSESECDAlarmVideoService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.services.BaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomSESECDAlarmVideoServiceImpl extends BaseServiceImpl implements CustomSESECDAlarmVideoService {


    @Autowired
    CustomSESECDSavePointService customSESECDSavePointService;

    @Override
    public void generateCctvRecord(SESECDAlmAlarmRecord almAlarmRecord, Object... objects) {
        log.error("进入生成视频方法");
        String mapPoint = almAlarmRecord.getMapPoint();
        if (org.apache.commons.lang.StringUtils.isEmpty(mapPoint)) {
            return;
        }
        PointVO pointVO = PointVO.PointPO2VO(almAlarmRecord.getMapPoint(), SESECDAlmAlarmRecord.MODEL_CODE, almAlarmRecord.getId().toString());
        if (pointVO == null || CollectionUtils.isEmpty(pointVO.getCoordinates())) {
            return;
        }
        CoordinateVO coordinateVO = pointVO.getCoordinates().get(0);
        BigDecimal lon = new BigDecimal(coordinateVO.getLon());
        BigDecimal lat = new BigDecimal(coordinateVO.getLat());

        //查询周边应急事件
        com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO dto = new com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO();
        dto.setLon(lon);
        dto.setLat(lat);
        dto.setRadius(new BigDecimal(2000));
        dto.setLayers("cameraLayer");
        List<GeometryAnalysisVO> geometryAnalysisVOS = customSESECDSavePointService.GetBufferedResourceList(dto);

        List<String> idList = new ArrayList<>();
        for (GeometryAnalysisVO item : geometryAnalysisVOS) {
            String spatialId = item.getId();
            String id = item.getId().substring(spatialId.lastIndexOf('_') + 1);
            idList.add(id);
        }
        log.error("退出生成视频方法");
       /* StringBuilder sb = new StringBuilder();
        sb.append("FROM ").append(SESCCTVCameraConfig.JPA_NAME)
                .append(" WHERE CID = ").append(getCurrentCompanyId())
                .append(" AND VALID = 1 ")
                .append(" AND ID IN (").append(String.join(",",idList)).append(")");

        Query query = almAlarmRecordDao.createQuery(sb.toString());
        List<SESCCTVCameraConfig> cameraConfigs = query.list();

        for (SESCCTVCameraConfig item : cameraConfigs){
            if(item.getChannel() == null){
                continue;
            }
            if(StringUtils.isEmpty(item.getChannel().getChannel())){
                continue;
            }
            try {
                Integer channelI = Integer.valueOf(item.getChannel().getChannel());
                Map<String, Object> params1 = new HashMap<>();
                params1.put("channel",channelI);
                params1.put("duration",60);
                params1.put("type","ECD");
                params1.put("business",almAlarmRecord.getId().toString());
                log.info("params1:{}",JsonHelper.writeValue(params1));
                channelConfigClient.StartRecordTypeBusiness(params1);
            }catch (Exception e){
                log.error("关联摄像头录视频报错：");
                log.error(e.getMessage(),e);
            }
        }*/
    }
}
