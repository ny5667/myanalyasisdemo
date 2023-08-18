package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDActionOwnersClient;
import com.supcon.orchid.SESECD.DTO.SESECDActionOwnersDTO;
import com.supcon.orchid.SESECD.entities.SESECDActionOwners;
import com.supcon.orchid.SESECD.entities.SESECDActionOwnersEditEntity;
import com.supcon.orchid.SESECD.services.SESECDActionOwnersService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDActionOwnersWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDActionOwnersClient implements ISESECDActionOwnersClient {

	@Autowired
	private SESECDActionOwnersService actionOwnersService;

	@Override
	public SESECDActionOwnersDTO getSESECDActionOwners(long id) {
		SESECDActionOwnersWrapper wrapper = new SESECDActionOwnersWrapper();
		SESECDActionOwners result = actionOwnersService.getActionOwners(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDActionOwnersDTO> getByPage(Page<SESECDActionOwnersDTO> page) {
		Page<SESECDActionOwners> entityPage = new Page<SESECDActionOwners>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDActionOwners> byPage = actionOwnersService.getByPage(entityPage, null);
		List<SESECDActionOwners> pageResult = byPage.getResult();
		List<SESECDActionOwnersDTO> result = new ArrayList<>();
		SESECDActionOwnersWrapper wrapper = new SESECDActionOwnersWrapper();
		for (SESECDActionOwners e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDActionOwners(String ids) {
		boolean result = true;
		try {
			actionOwnersService.deleteActionOwners(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}