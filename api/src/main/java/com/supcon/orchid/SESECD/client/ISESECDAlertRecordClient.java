package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAlertRecordDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAlertRecordClient")
@FeignClient(name = "SESECD", contextId = "SESECDAlertRecordClient")
public interface ISESECDAlertRecordClient {

	String API_PREFIX = "/v1/SESECD/alertRecord/alertRecord";

	@GetMapping(API_PREFIX + "/get")
	SESECDAlertRecordDTO getSESECDAlertRecord(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAlertRecordDTO> getByPage(@RequestBody Page<SESECDAlertRecordDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}