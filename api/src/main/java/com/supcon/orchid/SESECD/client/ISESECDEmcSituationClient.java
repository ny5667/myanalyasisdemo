package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEmcSituationDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEmcSituationClient")
@FeignClient(name = "SESECD", contextId = "SESECDEmcSituationClient")
public interface ISESECDEmcSituationClient {

	String API_PREFIX = "/v1/SESECD/emcSituation/emcSituation";

	@GetMapping(API_PREFIX + "/get")
	SESECDEmcSituationDTO getSESECDEmcSituation(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEmcSituationDTO> getByPage(@RequestBody Page<SESECDEmcSituationDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}