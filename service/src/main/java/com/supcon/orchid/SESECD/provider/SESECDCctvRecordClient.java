package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDCctvRecordClient;
import com.supcon.orchid.SESECD.DTO.SESECDCctvRecordDTO;
import com.supcon.orchid.SESECD.entities.SESECDCctvRecord;
import com.supcon.orchid.SESECD.entities.SESECDCctvRecordEditEntity;
import com.supcon.orchid.SESECD.services.SESECDCctvRecordService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDCctvRecordWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDCctvRecordClient implements ISESECDCctvRecordClient {

	@Autowired
	private SESECDCctvRecordService cctvRecordService;

	@Override
	public SESECDCctvRecordDTO getSESECDCctvRecord(long id) {
		SESECDCctvRecordWrapper wrapper = new SESECDCctvRecordWrapper();
		SESECDCctvRecord result = cctvRecordService.getCctvRecord(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDCctvRecordDTO> getByPage(Page<SESECDCctvRecordDTO> page) {
		Page<SESECDCctvRecord> entityPage = new Page<SESECDCctvRecord>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDCctvRecord> byPage = cctvRecordService.getByPage(entityPage, null);
		List<SESECDCctvRecord> pageResult = byPage.getResult();
		List<SESECDCctvRecordDTO> result = new ArrayList<>();
		SESECDCctvRecordWrapper wrapper = new SESECDCctvRecordWrapper();
		for (SESECDCctvRecord e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDCctvRecord(String ids) {
		boolean result = true;
		try {
			cctvRecordService.deleteCctvRecord(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}