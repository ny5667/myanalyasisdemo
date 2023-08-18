package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAlarmActCameraDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAlarmActCameraClient")
@FeignClient(name = "SESECD", contextId = "SESECDAlarmActCameraClient")
public interface ISESECDAlarmActCameraClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/alarmActCamera";

	@GetMapping(API_PREFIX + "/get")
	SESECDAlarmActCameraDTO getSESECDAlarmActCamera(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAlarmActCameraDTO> getByPage(@RequestBody Page<SESECDAlarmActCameraDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}