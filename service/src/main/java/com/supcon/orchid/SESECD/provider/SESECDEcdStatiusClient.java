package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdStatiusClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdStatiusDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdStatius;
import com.supcon.orchid.SESECD.entities.SESECDEcdStatiusEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdStatiusService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdStatiusWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdStatiusClient implements ISESECDEcdStatiusClient {

	@Autowired
	private SESECDEcdStatiusService ecdStatiusService;

	@Override
	public SESECDEcdStatiusDTO getSESECDEcdStatius(long id) {
		SESECDEcdStatiusWrapper wrapper = new SESECDEcdStatiusWrapper();
		SESECDEcdStatius result = ecdStatiusService.getEcdStatius(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdStatiusDTO> getByPage(Page<SESECDEcdStatiusDTO> page) {
		Page<SESECDEcdStatius> entityPage = new Page<SESECDEcdStatius>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdStatius> byPage = ecdStatiusService.getByPage(entityPage, null);
		List<SESECDEcdStatius> pageResult = byPage.getResult();
		List<SESECDEcdStatiusDTO> result = new ArrayList<>();
		SESECDEcdStatiusWrapper wrapper = new SESECDEcdStatiusWrapper();
		for (SESECDEcdStatius e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdStatius(String ids) {
		boolean result = true;
		try {
			ecdStatiusService.deleteEcdStatius(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}