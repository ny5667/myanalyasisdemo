package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdActionDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdActionClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdActionClient")
public interface ISESECDEcdActionClient {

	String API_PREFIX = "/v1/SESECD/ecdPanel/ecdAction";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdActionDTO getSESECDEcdAction(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdActionDTO> getByPage(@RequestBody Page<SESECDEcdActionDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}