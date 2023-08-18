package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDCommunicationDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDCommunicationClient")
@FeignClient(name = "SESECD", contextId = "SESECDCommunicationClient")
public interface ISESECDCommunicationClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/communication";

	@GetMapping(API_PREFIX + "/get")
	SESECDCommunicationDTO getSESECDCommunication(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDCommunicationDTO> getByPage(@RequestBody Page<SESECDCommunicationDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}