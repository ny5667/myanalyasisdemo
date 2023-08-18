package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDMainPeopleClient;
import com.supcon.orchid.SESECD.DTO.SESECDMainPeopleDTO;
import com.supcon.orchid.SESECD.entities.SESECDMainPeople;
import com.supcon.orchid.SESECD.entities.SESECDMainPeopleEditEntity;
import com.supcon.orchid.SESECD.services.SESECDMainPeopleService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDMainPeopleWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDMainPeopleClient implements ISESECDMainPeopleClient {

	@Autowired
	private SESECDMainPeopleService mainPeopleService;

	@Override
	public SESECDMainPeopleDTO getSESECDMainPeople(long id) {
		SESECDMainPeopleWrapper wrapper = new SESECDMainPeopleWrapper();
		SESECDMainPeople result = mainPeopleService.getMainPeople(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDMainPeopleDTO> getByPage(Page<SESECDMainPeopleDTO> page) {
		Page<SESECDMainPeople> entityPage = new Page<SESECDMainPeople>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDMainPeople> byPage = mainPeopleService.getByPage(entityPage, null);
		List<SESECDMainPeople> pageResult = byPage.getResult();
		List<SESECDMainPeopleDTO> result = new ArrayList<>();
		SESECDMainPeopleWrapper wrapper = new SESECDMainPeopleWrapper();
		for (SESECDMainPeople e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDMainPeople(String ids) {
		boolean result = true;
		try {
			mainPeopleService.deleteMainPeople(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}