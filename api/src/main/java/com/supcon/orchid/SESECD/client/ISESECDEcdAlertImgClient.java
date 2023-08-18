package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertImgDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdAlertImgClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdAlertImgClient")
public interface ISESECDEcdAlertImgClient {

	String API_PREFIX = "/v1/SESECD/ecdAlertRecord/ecdAlertImg";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdAlertImgDTO getSESECDEcdAlertImg(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdAlertImgDTO> getByPage(@RequestBody Page<SESECDEcdAlertImgDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}