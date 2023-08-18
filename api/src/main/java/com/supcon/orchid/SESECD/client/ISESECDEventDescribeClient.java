package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEventDescribeDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_eventDescription_EventDescribe,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEventDescribeClient")
@FeignClient(name = "SESECD", contextId = "SESECDEventDescribeClient")
public interface ISESECDEventDescribeClient {

	String API_PREFIX = "/v1/SESECD/eventDescription/eventDescribe";

	@GetMapping(API_PREFIX + "/get")
	SESECDEventDescribeDTO getSESECDEventDescribe(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEventDescribeDTO> getByPage(@RequestBody Page<SESECDEventDescribeDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_eventDescription_EventDescribe,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}