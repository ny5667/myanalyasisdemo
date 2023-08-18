package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdAlertRecordClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertRecordDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecordEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertRecordService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdAlertRecordWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdAlertRecordClient implements ISESECDEcdAlertRecordClient {

	@Autowired
	private SESECDEcdAlertRecordService ecdAlertRecordService;

	@Override
	public SESECDEcdAlertRecordDTO getSESECDEcdAlertRecord(long id) {
		SESECDEcdAlertRecordWrapper wrapper = new SESECDEcdAlertRecordWrapper();
		SESECDEcdAlertRecord result = ecdAlertRecordService.getEcdAlertRecord(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdAlertRecordDTO> getByPage(Page<SESECDEcdAlertRecordDTO> page) {
		Page<SESECDEcdAlertRecord> entityPage = new Page<SESECDEcdAlertRecord>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdAlertRecord> byPage = ecdAlertRecordService.getByPage(entityPage, null);
		List<SESECDEcdAlertRecord> pageResult = byPage.getResult();
		List<SESECDEcdAlertRecordDTO> result = new ArrayList<>();
		SESECDEcdAlertRecordWrapper wrapper = new SESECDEcdAlertRecordWrapper();
		for (SESECDEcdAlertRecord e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdAlertRecord(String ids) {
		boolean result = true;
		try {
			ecdAlertRecordService.deleteEcdAlertRecord(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}