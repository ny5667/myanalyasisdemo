package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdCommomDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdCommomClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdCommomClient")
public interface ISESECDEcdCommomClient {

	String API_PREFIX = "/v1/SESECD/ecdPanel/ecdCommom";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdCommomDTO getSESECDEcdCommom(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdCommomDTO> getByPage(@RequestBody Page<SESECDEcdCommomDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}