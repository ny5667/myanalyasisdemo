package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAlmAlarmRecordClient;
import com.supcon.orchid.SESECD.DTO.SESECDAlmAlarmRecordDTO;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecordEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAlmAlarmRecordWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAlmAlarmRecordClient implements ISESECDAlmAlarmRecordClient {

	@Autowired
	private SESECDAlmAlarmRecordService almAlarmRecordService;

	@Override
	public SESECDAlmAlarmRecordDTO getSESECDAlmAlarmRecord(long id) {
		SESECDAlmAlarmRecordWrapper wrapper = new SESECDAlmAlarmRecordWrapper();
		SESECDAlmAlarmRecord result = almAlarmRecordService.getAlmAlarmRecord(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAlmAlarmRecordDTO> getByPage(Page<SESECDAlmAlarmRecordDTO> page) {
		Page<SESECDAlmAlarmRecord> entityPage = new Page<SESECDAlmAlarmRecord>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAlmAlarmRecord> byPage = almAlarmRecordService.getByPage(entityPage, null);
		List<SESECDAlmAlarmRecord> pageResult = byPage.getResult();
		List<SESECDAlmAlarmRecordDTO> result = new ArrayList<>();
		SESECDAlmAlarmRecordWrapper wrapper = new SESECDAlmAlarmRecordWrapper();
		for (SESECDAlmAlarmRecord e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAlmAlarmRecord(String ids) {
		boolean result = true;
		try {
			almAlarmRecordService.deleteAlmAlarmRecord(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}