package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDMainPeopleDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDMainPeopleClient")
@FeignClient(name = "SESECD", contextId = "SESECDMainPeopleClient")
public interface ISESECDMainPeopleClient {

	String API_PREFIX = "/v1/SESECD/emcAction/mainPeople";

	@GetMapping(API_PREFIX + "/get")
	SESECDMainPeopleDTO getSESECDMainPeople(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDMainPeopleDTO> getByPage(@RequestBody Page<SESECDMainPeopleDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}