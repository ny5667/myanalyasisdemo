package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDSourceTerminalDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDSourceTerminalClient")
@FeignClient(name = "SESECD", contextId = "SESECDSourceTerminalClient")
public interface ISESECDSourceTerminalClient {

	String API_PREFIX = "/v1/SESECD/signalConfig/sourceTerminal";

	@GetMapping(API_PREFIX + "/get")
	SESECDSourceTerminalDTO getSESECDSourceTerminal(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDSourceTerminalDTO> getByPage(@RequestBody Page<SESECDSourceTerminalDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}