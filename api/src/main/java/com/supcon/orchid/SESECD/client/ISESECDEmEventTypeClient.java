package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEmEventTypeDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEmEventTypeClient")
@FeignClient(name = "SESECD", contextId = "SESECDEmEventTypeClient")
public interface ISESECDEmEventTypeClient {

	String API_PREFIX = "/v1/SESECD/emEventInfo/emEventType";

	@GetMapping(API_PREFIX + "/get")
	SESECDEmEventTypeDTO getSESECDEmEventType(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEmEventTypeDTO> getByPage(@RequestBody Page<SESECDEmEventTypeDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}