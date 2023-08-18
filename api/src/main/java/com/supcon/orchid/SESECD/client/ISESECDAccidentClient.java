package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAccidentDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAccidentClient")
@FeignClient(name = "SESECD", contextId = "SESECDAccidentClient")
public interface ISESECDAccidentClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/accident";

	@GetMapping(API_PREFIX + "/get")
	SESECDAccidentDTO getSESECDAccident(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAccidentDTO> getByPage(@RequestBody Page<SESECDAccidentDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}