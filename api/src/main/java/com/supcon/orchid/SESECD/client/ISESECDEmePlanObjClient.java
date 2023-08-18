package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEmePlanObjDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEmePlanObjClient")
@FeignClient(name = "SESECD", contextId = "SESECDEmePlanObjClient")
public interface ISESECDEmePlanObjClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/emePlanObj";

	@GetMapping(API_PREFIX + "/get")
	SESECDEmePlanObjDTO getSESECDEmePlanObj(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEmePlanObjDTO> getByPage(@RequestBody Page<SESECDEmePlanObjDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}