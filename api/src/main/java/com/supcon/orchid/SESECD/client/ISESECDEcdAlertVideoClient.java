package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertVideoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdAlertVideoClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdAlertVideoClient")
public interface ISESECDEcdAlertVideoClient {

	String API_PREFIX = "/v1/SESECD/ecdAlertRecord/ecdAlertVideo";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdAlertVideoDTO getSESECDEcdAlertVideo(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdAlertVideoDTO> getByPage(@RequestBody Page<SESECDEcdAlertVideoDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}