package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertRecordDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEcdAlertRecordClient")
@FeignClient(name = "SESECD", contextId = "SESECDEcdAlertRecordClient")
public interface ISESECDEcdAlertRecordClient {

	String API_PREFIX = "/v1/SESECD/ecdAlertRecord/ecdAlertRecord";

	@GetMapping(API_PREFIX + "/get")
	SESECDEcdAlertRecordDTO getSESECDEcdAlertRecord(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEcdAlertRecordDTO> getByPage(@RequestBody Page<SESECDEcdAlertRecordDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}