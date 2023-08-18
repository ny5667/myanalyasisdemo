package com.supcon.orchid.SESECD.services.impl.map;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.map.GeometryAnalysisVO;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESGISConfig.DTO.*;
import com.supcon.orchid.SESGISConfig.client.ICustomGisAnalysisClient;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CustomSESECDSavePointServiceImpl extends BaseServiceImpl implements CustomSESECDSavePointService {

    @Autowired
    private ICustomGisAnalysisClient iCustomGisAnalysisClient;

    private static final String COMMON_POINT_LAYER = "Common_Point";

    @Override
    public Boolean savePoint2PG(String spatialId, PointVO point, String layer) throws Exception {

        batchDeleteFeatureInfo(spatialId, layer);

        DoFeatureDto pointPO = new DoFeatureDto();
        pointPO.setLayerName(COMMON_POINT_LAYER);
        pointPO.setId(spatialId);
        pointPO.setLayer(layer);
        List<PointDto> coordinates = PointVO.PointVO2PO(point);
        pointPO.setPoints(coordinates);

        if (null != point.getHeight()) {
            pointPO.setHeight(new BigDecimal(point.getHeight()));
        }
        if (null != point.getFloor()) {
            pointPO.setFloor(Double.valueOf(point.getFloor()));
        }

        log.error("点的内容如下");
        log.error(JSON.toJSONString(pointPO));
        BaseInfoVo addPointResult = iCustomGisAnalysisClient.addPointFeature(pointPO);//新增空间点
        if (!addPointResult.isSuccess()) {
            log.error("点保存失败");
            throw new Exception("save point failed");
        }
        log.error("点的保存结果:");
        log.error(JSON.toJSONString(addPointResult));
        return true;
    }

    @Override
    public void batchDeleteFeatureInfo(String spatialId, String layer) throws Exception {
        BatchDeleteFeatureDto batchDeleteFeatureDto = new BatchDeleteFeatureDto();
        batchDeleteFeatureDto.setIds(Arrays.asList(spatialId));
        batchDeleteFeatureDto.setLayerName(COMMON_POINT_LAYER);
        batchDeleteFeatureDto.setLayerCode(layer);
        log.error("删除点的内容如下");
        log.error(JsonHelper.writeValue(batchDeleteFeatureDto));
        BaseInfoVo baseInfoVo = iCustomGisAnalysisClient.batchDeleteFeatureInfo(batchDeleteFeatureDto);
        log.error("点删除返回结果");
        log.error(JsonHelper.writeValue(baseInfoVo));
        if (!baseInfoVo.isSuccess()) {
            log.error("点删除失败");
            throw new Exception("delete point failed");
        }
    }

    @Override
    public List<GeometryAnalysisVO> GetBufferedResourceList(com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO dto) {
        log.error("调用地图分析服务周边资源接口：");
        log.error(JsonHelper.writeValue(dto));
        Result<BaseInfoVo> baseInfoVoResult = iCustomGisAnalysisClient.GetBufferedResourceList(dto);
        log.error("周边资源接口返回：");
        log.error(JsonHelper.writeValue(baseInfoVoResult));

        String LOCAL_JSON = JsonHelper.writeValue(baseInfoVoResult.getData().getResult());

        List<GeometryAnalysisVO> geometryAnalysisVOS = JsonHelper.parseArray(LOCAL_JSON, new TypeReference<List<GeometryAnalysisVO>>(){});

        log.error(JsonHelper.writeValue(geometryAnalysisVOS));

        System.out.println(geometryAnalysisVOS.get(0));
        System.out.println(geometryAnalysisVOS.get(1));

        for (GeometryAnalysisVO item :
                geometryAnalysisVOS) {
            System.out.println(item);
        }

        return geometryAnalysisVOS;
    }

}
