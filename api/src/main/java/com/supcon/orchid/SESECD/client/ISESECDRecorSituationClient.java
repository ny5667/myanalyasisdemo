package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDRecorSituationDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDRecorSituationClient")
@FeignClient(name = "SESECD", contextId = "SESECDRecorSituationClient")
public interface ISESECDRecorSituationClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/recorSituation";

	@GetMapping(API_PREFIX + "/get")
	SESECDRecorSituationDTO getSESECDRecorSituation(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDRecorSituationDTO> getByPage(@RequestBody Page<SESECDRecorSituationDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}