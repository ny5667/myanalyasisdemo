package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDDispatConfigDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDDispatConfigClient")
@FeignClient(name = "SESECD", contextId = "SESECDDispatConfigClient")
public interface ISESECDDispatConfigClient {

	String API_PREFIX = "/v1/SESECD/dispatConfig/dispatConfig";

	@GetMapping(API_PREFIX + "/get")
	SESECDDispatConfigDTO getSESECDDispatConfig(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDDispatConfigDTO> getByPage(@RequestBody Page<SESECDDispatConfigDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}