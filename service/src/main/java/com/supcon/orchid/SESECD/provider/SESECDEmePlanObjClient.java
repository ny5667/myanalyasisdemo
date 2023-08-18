package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmePlanObjClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmePlanObjDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObjEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmePlanObjService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmePlanObjWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmePlanObjClient implements ISESECDEmePlanObjClient {

	@Autowired
	private SESECDEmePlanObjService emePlanObjService;

	@Override
	public SESECDEmePlanObjDTO getSESECDEmePlanObj(long id) {
		SESECDEmePlanObjWrapper wrapper = new SESECDEmePlanObjWrapper();
		SESECDEmePlanObj result = emePlanObjService.getEmePlanObj(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmePlanObjDTO> getByPage(Page<SESECDEmePlanObjDTO> page) {
		Page<SESECDEmePlanObj> entityPage = new Page<SESECDEmePlanObj>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmePlanObj> byPage = emePlanObjService.getByPage(entityPage, null);
		List<SESECDEmePlanObj> pageResult = byPage.getResult();
		List<SESECDEmePlanObjDTO> result = new ArrayList<>();
		SESECDEmePlanObjWrapper wrapper = new SESECDEmePlanObjWrapper();
		for (SESECDEmePlanObj e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmePlanObj(String ids) {
		boolean result = true;
		try {
			emePlanObjService.deleteEmePlanObj(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}