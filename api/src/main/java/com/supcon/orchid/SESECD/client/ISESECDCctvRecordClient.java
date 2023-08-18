package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDCctvRecordDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDCctvRecordClient")
@FeignClient(name = "SESECD", contextId = "SESECDCctvRecordClient")
public interface ISESECDCctvRecordClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/cctvRecord";

	@GetMapping(API_PREFIX + "/get")
	SESECDCctvRecordDTO getSESECDCctvRecord(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDCctvRecordDTO> getByPage(@RequestBody Page<SESECDCctvRecordDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}