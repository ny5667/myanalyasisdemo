package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdCommomClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdCommomDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdCommom;
import com.supcon.orchid.SESECD.entities.SESECDEcdCommomEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdCommomService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdCommomWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdCommomClient implements ISESECDEcdCommomClient {

	@Autowired
	private SESECDEcdCommomService ecdCommomService;

	@Override
	public SESECDEcdCommomDTO getSESECDEcdCommom(long id) {
		SESECDEcdCommomWrapper wrapper = new SESECDEcdCommomWrapper();
		SESECDEcdCommom result = ecdCommomService.getEcdCommom(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdCommomDTO> getByPage(Page<SESECDEcdCommomDTO> page) {
		Page<SESECDEcdCommom> entityPage = new Page<SESECDEcdCommom>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdCommom> byPage = ecdCommomService.getByPage(entityPage, null);
		List<SESECDEcdCommom> pageResult = byPage.getResult();
		List<SESECDEcdCommomDTO> result = new ArrayList<>();
		SESECDEcdCommomWrapper wrapper = new SESECDEcdCommomWrapper();
		for (SESECDEcdCommom e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdCommom(String ids) {
		boolean result = true;
		try {
			ecdCommomService.deleteEcdCommom(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}