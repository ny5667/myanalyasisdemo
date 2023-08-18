package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmEventTypeClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmEventTypeDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmEventType;
import com.supcon.orchid.SESECD.entities.SESECDEmEventTypeEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmEventTypeService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmEventTypeWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmEventTypeClient implements ISESECDEmEventTypeClient {

	@Autowired
	private SESECDEmEventTypeService emEventTypeService;

	@Override
	public SESECDEmEventTypeDTO getSESECDEmEventType(long id) {
		SESECDEmEventTypeWrapper wrapper = new SESECDEmEventTypeWrapper();
		SESECDEmEventType result = emEventTypeService.getEmEventType(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmEventTypeDTO> getByPage(Page<SESECDEmEventTypeDTO> page) {
		Page<SESECDEmEventType> entityPage = new Page<SESECDEmEventType>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmEventType> byPage = emEventTypeService.getByPage(entityPage, null);
		List<SESECDEmEventType> pageResult = byPage.getResult();
		List<SESECDEmEventTypeDTO> result = new ArrayList<>();
		SESECDEmEventTypeWrapper wrapper = new SESECDEmEventTypeWrapper();
		for (SESECDEmEventType e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmEventType(String ids) {
		boolean result = true;
		try {
			emEventTypeService.deleteEmEventType(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}