package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdActionClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdActionDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdAction;
import com.supcon.orchid.SESECD.entities.SESECDEcdActionEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdActionService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdActionWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdActionClient implements ISESECDEcdActionClient {

	@Autowired
	private SESECDEcdActionService ecdActionService;

	@Override
	public SESECDEcdActionDTO getSESECDEcdAction(long id) {
		SESECDEcdActionWrapper wrapper = new SESECDEcdActionWrapper();
		SESECDEcdAction result = ecdActionService.getEcdAction(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdActionDTO> getByPage(Page<SESECDEcdActionDTO> page) {
		Page<SESECDEcdAction> entityPage = new Page<SESECDEcdAction>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdAction> byPage = ecdActionService.getByPage(entityPage, null);
		List<SESECDEcdAction> pageResult = byPage.getResult();
		List<SESECDEcdActionDTO> result = new ArrayList<>();
		SESECDEcdActionWrapper wrapper = new SESECDEcdActionWrapper();
		for (SESECDEcdAction e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdAction(String ids) {
		boolean result = true;
		try {
			ecdActionService.deleteEcdAction(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}