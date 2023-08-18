package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAlmAlarmRecordDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAlmAlarmRecordClient")
@FeignClient(name = "SESECD", contextId = "SESECDAlmAlarmRecordClient")
public interface ISESECDAlmAlarmRecordClient {

	String API_PREFIX = "/v1/SESECD/alarmRecord/almAlarmRecord";

	@GetMapping(API_PREFIX + "/get")
	SESECDAlmAlarmRecordDTO getSESECDAlmAlarmRecord(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAlmAlarmRecordDTO> getByPage(@RequestBody Page<SESECDAlmAlarmRecordDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}