package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDActVideoCameraDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_emcAction_ActVideoCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDActVideoCameraClient")
@FeignClient(name = "SESECD", contextId = "SESECDActVideoCameraClient")
public interface ISESECDActVideoCameraClient {

	String API_PREFIX = "/v1/SESECD/emcAction/actVideoCamera";

	@GetMapping(API_PREFIX + "/get")
	SESECDActVideoCameraDTO getSESECDActVideoCamera(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDActVideoCameraDTO> getByPage(@RequestBody Page<SESECDActVideoCameraDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_emcAction_ActVideoCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}