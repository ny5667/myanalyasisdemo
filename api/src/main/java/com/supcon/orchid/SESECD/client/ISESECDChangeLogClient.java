package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDChangeLogDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDChangeLogClient")
@FeignClient(name = "SESECD", contextId = "SESECDChangeLogClient")
public interface ISESECDChangeLogClient {

	String API_PREFIX = "/v1/SESECD/changeLog/changeLog";

	@GetMapping(API_PREFIX + "/get")
	SESECDChangeLogDTO getSESECDChangeLog(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDChangeLogDTO> getByPage(@RequestBody Page<SESECDChangeLogDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}