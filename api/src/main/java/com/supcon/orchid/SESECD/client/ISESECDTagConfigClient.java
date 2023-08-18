package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDTagConfigDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDTagConfigClient")
@FeignClient(name = "SESECD", contextId = "SESECDTagConfigClient")
public interface ISESECDTagConfigClient {

	String API_PREFIX = "/v1/SESECD/tagConfig/tagConfig";

	@GetMapping(API_PREFIX + "/get")
	SESECDTagConfigDTO getSESECDTagConfig(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDTagConfigDTO> getByPage(@RequestBody Page<SESECDTagConfigDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}