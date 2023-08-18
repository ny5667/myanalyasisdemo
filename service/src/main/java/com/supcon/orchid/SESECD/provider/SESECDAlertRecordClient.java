package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAlertRecordClient;
import com.supcon.orchid.SESECD.DTO.SESECDAlertRecordDTO;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecordEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAlertRecordService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAlertRecordWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAlertRecordClient implements ISESECDAlertRecordClient {

	@Autowired
	private SESECDAlertRecordService alertRecordService;

	@Override
	public SESECDAlertRecordDTO getSESECDAlertRecord(long id) {
		SESECDAlertRecordWrapper wrapper = new SESECDAlertRecordWrapper();
		SESECDAlertRecord result = alertRecordService.getAlertRecord(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAlertRecordDTO> getByPage(Page<SESECDAlertRecordDTO> page) {
		Page<SESECDAlertRecord> entityPage = new Page<SESECDAlertRecord>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAlertRecord> byPage = alertRecordService.getByPage(entityPage, null);
		List<SESECDAlertRecord> pageResult = byPage.getResult();
		List<SESECDAlertRecordDTO> result = new ArrayList<>();
		SESECDAlertRecordWrapper wrapper = new SESECDAlertRecordWrapper();
		for (SESECDAlertRecord e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAlertRecord(String ids) {
		boolean result = true;
		try {
			alertRecordService.deleteAlertRecord(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}