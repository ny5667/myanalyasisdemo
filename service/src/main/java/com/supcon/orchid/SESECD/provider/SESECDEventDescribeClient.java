package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEventDescribeClient;
import com.supcon.orchid.SESECD.DTO.SESECDEventDescribeDTO;
import com.supcon.orchid.SESECD.entities.SESECDEventDescribe;
import com.supcon.orchid.SESECD.entities.SESECDEventDescribeEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEventDescribeService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEventDescribeWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_eventDescription_EventDescribe,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEventDescribeClient implements ISESECDEventDescribeClient {

	@Autowired
	private SESECDEventDescribeService eventDescribeService;

	@Override
	public SESECDEventDescribeDTO getSESECDEventDescribe(long id) {
		SESECDEventDescribeWrapper wrapper = new SESECDEventDescribeWrapper();
		SESECDEventDescribe result = eventDescribeService.getEventDescribe(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEventDescribeDTO> getByPage(Page<SESECDEventDescribeDTO> page) {
		Page<SESECDEventDescribe> entityPage = new Page<SESECDEventDescribe>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEventDescribe> byPage = eventDescribeService.getByPage(entityPage, null);
		List<SESECDEventDescribe> pageResult = byPage.getResult();
		List<SESECDEventDescribeDTO> result = new ArrayList<>();
		SESECDEventDescribeWrapper wrapper = new SESECDEventDescribeWrapper();
		for (SESECDEventDescribe e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEventDescribe(String ids) {
		boolean result = true;
		try {
			eventDescribeService.deleteEventDescribe(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_eventDescription_EventDescribe,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}