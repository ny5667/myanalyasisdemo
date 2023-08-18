package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDBroadcastInfoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDBroadcastInfoClient")
@FeignClient(name = "SESECD", contextId = "SESECDBroadcastInfoClient")
public interface ISESECDBroadcastInfoClient {

	String API_PREFIX = "/v1/SESECD/broadcastIntercom/broadcastInfo";

	@GetMapping(API_PREFIX + "/get")
	SESECDBroadcastInfoDTO getSESECDBroadcastInfo(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDBroadcastInfoDTO> getByPage(@RequestBody Page<SESECDBroadcastInfoDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}