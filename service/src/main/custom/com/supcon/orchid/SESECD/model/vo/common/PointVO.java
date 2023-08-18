package com.supcon.orchid.SESECD.model.vo.common;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESGISConfig.DTO.PointDto;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PointVO {
    private static final Logger logger = LoggerFactory.getLogger(PointVO.class);

    /**
     * spatialId
     */
    private String spatialId;

    /**
     * 圖層編碼
     */
    private String layerCode;

    /**
     * floor
     */
    private Float floor;

    /**
     * buildingPatchId
     */
    private String buildingPatchId;

    /**
     * 坐標點數組
     */
    private List<CoordinateVO> coordinates;

    /**
     * 高度
     */
    private Float height;

    public PointVO(String layerCode, List<CoordinateVO> coordinates) {
        this.layerCode = layerCode;
        this.coordinates = coordinates;
    }

    public static PointVO PointPO2VO(String pointJSON, String prefix, String bizId) {

        logger.error("bizId:{}", bizId);
        logger.error("pointjson");
        logger.error(pointJSON);

        if (StringUtils.isEmpty(pointJSON)) {
            return null;
        }
        PointVO vo = JSON.parseObject(pointJSON, PointVO.class);
        //String prefix = SESECDEmcSituation.MODEL_CODE+"_";
        vo.setSpatialId(prefix + bizId);
        return vo;
    }

    public static List<PointDto> PointVO2PO(PointVO input) {
        List<PointDto> coordinates = new ArrayList<>();
        PointDto pointDto = new PointDto();
        pointDto.setLat(input.getCoordinates().get(0).getLat());
        pointDto.setLon(input.getCoordinates().get(0).getLon());
        pointDto.setHei(new BigDecimal(input.getCoordinates().get(0).getHei()));
        coordinates.add(pointDto);
        return coordinates;
    }
}
