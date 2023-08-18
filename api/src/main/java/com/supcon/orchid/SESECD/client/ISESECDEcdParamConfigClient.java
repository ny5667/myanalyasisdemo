package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdParamConfigDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdParamConfigClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdParamConfigClient")
public interface ISESECDEcdParamConfigClient {

	String API_PREFIX = "/v1/SESECD/paramConfig/ecdParamConfig";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdParamConfigDTO getSESECDEcdParamConfig(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdParamConfigDTO> getByPage(@RequestBody Page<SESECDEcdParamConfigDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}