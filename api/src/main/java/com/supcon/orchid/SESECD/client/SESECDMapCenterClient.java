package com.supcon.orchid.SESECD.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * 地图空间坐标调用接口
 **/
@FeignClient(name = "SESGISConfig", contextId = "SESECDMapCenterClient")
public interface SESECDMapCenterClient {

	String API_PREFIX = "/SESGISConfig/operaFeature/operaFeature";
	
	/***
	 * @Description 新增修改坐标信息
	 * @Param [param]
	 * @return java.lang.Object
	 **/
	@RequestMapping(value = API_PREFIX + "/insertMutilGeometry", method = RequestMethod.POST)
	Object insertMutilGeometry(Map<String, Object> param);

	/***
	 * @Description 删除坐标信息
	 * @Param [param]
	 * @return java.lang.Object
	 **/
	@RequestMapping(value = API_PREFIX + "/batchDeleteFeatureInfo", method = RequestMethod.POST)
	Object deleteFeature(List<Map<String, Object>> params);

	/***
	 * @Description 查询坐标信息
	 * @Param [param]
	 * @return java.lang.Object
	 **/
	@RequestMapping(value = API_PREFIX + "/queryFeatureInfo", method = RequestMethod.POST)
	Object queryFeatureInfo(Map<String, Object> param);
}
