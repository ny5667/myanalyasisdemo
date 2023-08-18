package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAlarmEnenetrelClient;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmEnenetrelDTO;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrelEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAlarmEnenetrelWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAlarmEnenetrelClient implements ISESECDAlarmEnenetrelClient {

	@Autowired
	private SESECDAlarmEnenetrelService alarmEnenetrelService;

	@Override
	public SESECDAlarmEnenetrelDTO getSESECDAlarmEnenetrel(long id) {
		SESECDAlarmEnenetrelWrapper wrapper = new SESECDAlarmEnenetrelWrapper();
		SESECDAlarmEnenetrel result = alarmEnenetrelService.getAlarmEnenetrel(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAlarmEnenetrelDTO> getByPage(Page<SESECDAlarmEnenetrelDTO> page) {
		Page<SESECDAlarmEnenetrel> entityPage = new Page<SESECDAlarmEnenetrel>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAlarmEnenetrel> byPage = alarmEnenetrelService.getByPage(entityPage, null);
		List<SESECDAlarmEnenetrel> pageResult = byPage.getResult();
		List<SESECDAlarmEnenetrelDTO> result = new ArrayList<>();
		SESECDAlarmEnenetrelWrapper wrapper = new SESECDAlarmEnenetrelWrapper();
		for (SESECDAlarmEnenetrel e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAlarmEnenetrel(String ids) {
		boolean result = true;
		try {
			alarmEnenetrelService.deleteAlarmEnenetrel(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}