package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAccidentClient;
import com.supcon.orchid.SESECD.DTO.SESECDAccidentDTO;
import com.supcon.orchid.SESECD.entities.SESECDAccident;
import com.supcon.orchid.SESECD.entities.SESECDAccidentEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAccidentService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAccidentWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAccidentClient implements ISESECDAccidentClient {

	@Autowired
	private SESECDAccidentService accidentService;

	@Override
	public SESECDAccidentDTO getSESECDAccident(long id) {
		SESECDAccidentWrapper wrapper = new SESECDAccidentWrapper();
		SESECDAccident result = accidentService.getAccident(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAccidentDTO> getByPage(Page<SESECDAccidentDTO> page) {
		Page<SESECDAccident> entityPage = new Page<SESECDAccident>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAccident> byPage = accidentService.getByPage(entityPage, null);
		List<SESECDAccident> pageResult = byPage.getResult();
		List<SESECDAccidentDTO> result = new ArrayList<>();
		SESECDAccidentWrapper wrapper = new SESECDAccidentWrapper();
		for (SESECDAccident e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAccident(String ids) {
		boolean result = true;
		try {
			accidentService.deleteAccident(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}