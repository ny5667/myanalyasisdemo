package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDMesPersonClient;
import com.supcon.orchid.SESECD.DTO.SESECDMesPersonDTO;
import com.supcon.orchid.SESECD.entities.SESECDMesPerson;
import com.supcon.orchid.SESECD.entities.SESECDMesPersonEditEntity;
import com.supcon.orchid.SESECD.services.SESECDMesPersonService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDMesPersonWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDMesPersonClient implements ISESECDMesPersonClient {

	@Autowired
	private SESECDMesPersonService mesPersonService;

	@Override
	public SESECDMesPersonDTO getSESECDMesPerson(long id) {
		SESECDMesPersonWrapper wrapper = new SESECDMesPersonWrapper();
		SESECDMesPerson result = mesPersonService.getMesPerson(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDMesPersonDTO> getByPage(Page<SESECDMesPersonDTO> page) {
		Page<SESECDMesPerson> entityPage = new Page<SESECDMesPerson>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDMesPerson> byPage = mesPersonService.getByPage(entityPage, null);
		List<SESECDMesPerson> pageResult = byPage.getResult();
		List<SESECDMesPersonDTO> result = new ArrayList<>();
		SESECDMesPersonWrapper wrapper = new SESECDMesPersonWrapper();
		for (SESECDMesPerson e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDMesPerson(String ids) {
		boolean result = true;
		try {
			mesPersonService.deleteMesPerson(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}