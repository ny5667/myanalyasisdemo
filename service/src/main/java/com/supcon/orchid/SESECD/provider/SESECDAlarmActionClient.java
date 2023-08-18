package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAlarmActionClient;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmActionDTO;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESECD.entities.SESECDAlarmActionEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAlarmActionService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAlarmActionWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAlarmActionClient implements ISESECDAlarmActionClient {

	@Autowired
	private SESECDAlarmActionService alarmActionService;

	@Override
	public SESECDAlarmActionDTO getSESECDAlarmAction(long id) {
		SESECDAlarmActionWrapper wrapper = new SESECDAlarmActionWrapper();
		SESECDAlarmAction result = alarmActionService.getAlarmAction(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAlarmActionDTO> getByPage(Page<SESECDAlarmActionDTO> page) {
		Page<SESECDAlarmAction> entityPage = new Page<SESECDAlarmAction>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAlarmAction> byPage = alarmActionService.getByPage(entityPage, null);
		List<SESECDAlarmAction> pageResult = byPage.getResult();
		List<SESECDAlarmActionDTO> result = new ArrayList<>();
		SESECDAlarmActionWrapper wrapper = new SESECDAlarmActionWrapper();
		for (SESECDAlarmAction e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAlarmAction(String ids) {
		boolean result = true;
		try {
			alarmActionService.deleteAlarmAction(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}