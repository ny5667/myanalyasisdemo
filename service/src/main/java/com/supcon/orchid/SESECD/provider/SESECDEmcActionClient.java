package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmcActionClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmcActionDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.entities.SESECDEmcActionEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmcActionService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmcActionWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmcActionClient implements ISESECDEmcActionClient {

	@Autowired
	private SESECDEmcActionService emcActionService;

	@Override
	public SESECDEmcActionDTO getSESECDEmcAction(long id) {
		SESECDEmcActionWrapper wrapper = new SESECDEmcActionWrapper();
		SESECDEmcAction result = emcActionService.getEmcAction(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmcActionDTO> getByPage(Page<SESECDEmcActionDTO> page) {
		Page<SESECDEmcAction> entityPage = new Page<SESECDEmcAction>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmcAction> byPage = emcActionService.getByPage(entityPage, null);
		List<SESECDEmcAction> pageResult = byPage.getResult();
		List<SESECDEmcActionDTO> result = new ArrayList<>();
		SESECDEmcActionWrapper wrapper = new SESECDEmcActionWrapper();
		for (SESECDEmcAction e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmcAction(String ids) {
		boolean result = true;
		try {
			emcActionService.deleteEmcAction(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}