package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdStatiusDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdStatiusClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdStatiusClient")
public interface ISESECDEcdStatiusClient {

	String API_PREFIX = "/v1/SESECD/ecdPanel/ecdStatius";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdStatiusDTO getSESECDEcdStatius(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdStatiusDTO> getByPage(@RequestBody Page<SESECDEcdStatiusDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}