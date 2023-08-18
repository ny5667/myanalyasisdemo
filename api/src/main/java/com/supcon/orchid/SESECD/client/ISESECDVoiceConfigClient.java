package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDVoiceConfigDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDVoiceConfigClient")
@FeignClient(name = "SESECD", contextId = "SESECDVoiceConfigClient")
public interface ISESECDVoiceConfigClient {

	String API_PREFIX = "/v1/SESECD/voiceConfig/voiceConfig";

	@GetMapping(API_PREFIX + "/get")
	SESECDVoiceConfigDTO getSESECDVoiceConfig(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDVoiceConfigDTO> getByPage(@RequestBody Page<SESECDVoiceConfigDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}