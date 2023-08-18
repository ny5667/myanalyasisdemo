package com.supcon.orchid.SESECD.services.map;

import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.map.GeometryAnalysisVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

public interface CustomSESECDSavePointService {

    /**
     * 保存点到pg中
     *
     * @param spatialId 空间数据库ID
     * @param point     点
     * @param layer     专题图名称
     * @return
     * @throws Exception
     */
    Boolean savePoint2PG(String spatialId, PointVO point, String layer) throws Exception;

    /**
     * 删除点到pg中
     *
     * @param spatialId 空间数据库ID
     * @param layer     专题图名称
     */
    void batchDeleteFeatureInfo(String spatialId, String layer) throws Exception;

    /**
     * 地图周边资源查询
     *
     * @param dto
     * @return
     */
    List<GeometryAnalysisVO> GetBufferedResourceList(@RequestBody com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO dto);

}

