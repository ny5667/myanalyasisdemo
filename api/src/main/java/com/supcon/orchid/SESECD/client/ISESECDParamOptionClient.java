package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDParamOptionDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDParamOptionClient")
@FeignClient(name = "SESECD", contextId = "SESECDParamOptionClient")
public interface ISESECDParamOptionClient {

	String API_PREFIX = "/v1/SESECD/paramConfig/paramOption";

	@GetMapping(API_PREFIX + "/get")
	SESECDParamOptionDTO getSESECDParamOption(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDParamOptionDTO> getByPage(@RequestBody Page<SESECDParamOptionDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}