package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDSignalConfigDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDSignalConfigClient")
@FeignClient(name = "SESECD", contextId = "SESECDSignalConfigClient")
public interface ISESECDSignalConfigClient {

	String API_PREFIX = "/v1/SESECD/signalConfig/signalConfig";

	@GetMapping(API_PREFIX + "/get")
	SESECDSignalConfigDTO getSESECDSignalConfig(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDSignalConfigDTO> getByPage(@RequestBody Page<SESECDSignalConfigDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}