package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDActionOwnersDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDActionOwnersClient")
@FeignClient(name = "SESECD", contextId = "SESECDActionOwnersClient")
public interface ISESECDActionOwnersClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/actionOwners";

	@GetMapping(API_PREFIX + "/get")
	SESECDActionOwnersDTO getSESECDActionOwners(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDActionOwnersDTO> getByPage(@RequestBody Page<SESECDActionOwnersDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}