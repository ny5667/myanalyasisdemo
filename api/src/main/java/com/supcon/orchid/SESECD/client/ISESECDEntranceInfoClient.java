package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEntranceInfoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEntranceInfoClient")
@FeignClient(name = "SESECD", contextId = "SESECDEntranceInfoClient")
public interface ISESECDEntranceInfoClient {

	String API_PREFIX = "/v1/SESECD/doorAccessControl/entranceInfo";

	@GetMapping(API_PREFIX + "/get")
	SESECDEntranceInfoDTO getSESECDEntranceInfo(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEntranceInfoDTO> getByPage(@RequestBody Page<SESECDEntranceInfoDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}