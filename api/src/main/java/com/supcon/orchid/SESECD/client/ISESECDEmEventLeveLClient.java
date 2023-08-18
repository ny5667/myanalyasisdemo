package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEmEventLeveLDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEmEventLeveLClient")
@FeignClient(name = "SESECD", contextId = "SESECDEmEventLeveLClient")
public interface ISESECDEmEventLeveLClient {

	String API_PREFIX = "/v1/SESECD/emEventInfo/emEventLeveL";

	@GetMapping(API_PREFIX + "/get")
	SESECDEmEventLeveLDTO getSESECDEmEventLeveL(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEmEventLeveLDTO> getByPage(@RequestBody Page<SESECDEmEventLeveLDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}