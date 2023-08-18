package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAlarmEnenetrelDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAlarmEnenetrelClient")
@FeignClient(name = "SESECD", contextId = "SESECDAlarmEnenetrelClient")
public interface ISESECDAlarmEnenetrelClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/alarmEnenetrel";

	@GetMapping(API_PREFIX + "/get")
	SESECDAlarmEnenetrelDTO getSESECDAlarmEnenetrel(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAlarmEnenetrelDTO> getByPage(@RequestBody Page<SESECDAlarmEnenetrelDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}