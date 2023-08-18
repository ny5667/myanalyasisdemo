package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDChangeLogClient;
import com.supcon.orchid.SESECD.DTO.SESECDChangeLogDTO;
import com.supcon.orchid.SESECD.entities.SESECDChangeLog;
import com.supcon.orchid.SESECD.entities.SESECDChangeLogEditEntity;
import com.supcon.orchid.SESECD.services.SESECDChangeLogService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDChangeLogWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDChangeLogClient implements ISESECDChangeLogClient {

	@Autowired
	private SESECDChangeLogService changeLogService;

	@Override
	public SESECDChangeLogDTO getSESECDChangeLog(long id) {
		SESECDChangeLogWrapper wrapper = new SESECDChangeLogWrapper();
		SESECDChangeLog result = changeLogService.getChangeLog(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDChangeLogDTO> getByPage(Page<SESECDChangeLogDTO> page) {
		Page<SESECDChangeLog> entityPage = new Page<SESECDChangeLog>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDChangeLog> byPage = changeLogService.getByPage(entityPage, null);
		List<SESECDChangeLog> pageResult = byPage.getResult();
		List<SESECDChangeLogDTO> result = new ArrayList<>();
		SESECDChangeLogWrapper wrapper = new SESECDChangeLogWrapper();
		for (SESECDChangeLog e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDChangeLog(String ids) {
		boolean result = true;
		try {
			changeLogService.deleteChangeLog(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}