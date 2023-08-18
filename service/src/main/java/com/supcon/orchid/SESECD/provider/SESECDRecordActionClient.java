package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDRecordActionClient;
import com.supcon.orchid.SESECD.DTO.SESECDRecordActionDTO;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.entities.SESECDRecordActionEditEntity;
import com.supcon.orchid.SESECD.services.SESECDRecordActionService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDRecordActionWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDRecordActionClient implements ISESECDRecordActionClient {

	@Autowired
	private SESECDRecordActionService recordActionService;

	@Override
	public SESECDRecordActionDTO getSESECDRecordAction(long id) {
		SESECDRecordActionWrapper wrapper = new SESECDRecordActionWrapper();
		SESECDRecordAction result = recordActionService.getRecordAction(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDRecordActionDTO> getByPage(Page<SESECDRecordActionDTO> page) {
		Page<SESECDRecordAction> entityPage = new Page<SESECDRecordAction>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDRecordAction> byPage = recordActionService.getByPage(entityPage, null);
		List<SESECDRecordAction> pageResult = byPage.getResult();
		List<SESECDRecordActionDTO> result = new ArrayList<>();
		SESECDRecordActionWrapper wrapper = new SESECDRecordActionWrapper();
		for (SESECDRecordAction e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDRecordAction(String ids) {
		boolean result = true;
		try {
			recordActionService.deleteRecordAction(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}