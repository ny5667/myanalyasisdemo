package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.support.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.supcon.orchid.SESECD.DTO.*;

@FeignClient(
        name = "gisAnalysis"
)
public interface ICustomEcdGisAnalysisClient {
    String API_PREFIX = "/gisAnalysis/gisAnalysis";

    @PostMapping(
            value = {"/gisAnalysis/gisAnalysis/addPointFeature"},
            produces = {"application/json"}
    )
    String addPointFeature(@RequestBody DoFeatureDTO doFeatureDto);

    @PostMapping(
            value = {"/gisAnalysis/gisAnalysis/batchDeleteFeatureInfo"},
            produces = {"application/json"}
    )
    String batchDeleteFeatureInfo(@RequestBody BatchDeleteFeatureDTO doFeatureDto);

}
