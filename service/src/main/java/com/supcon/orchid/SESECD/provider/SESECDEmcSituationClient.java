package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmcSituationClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmcSituationDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituationEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmcSituationService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmcSituationWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmcSituationClient implements ISESECDEmcSituationClient {

	@Autowired
	private SESECDEmcSituationService emcSituationService;

	@Override
	public SESECDEmcSituationDTO getSESECDEmcSituation(long id) {
		SESECDEmcSituationWrapper wrapper = new SESECDEmcSituationWrapper();
		SESECDEmcSituation result = emcSituationService.getEmcSituation(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmcSituationDTO> getByPage(Page<SESECDEmcSituationDTO> page) {
		Page<SESECDEmcSituation> entityPage = new Page<SESECDEmcSituation>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmcSituation> byPage = emcSituationService.getByPage(entityPage, null);
		List<SESECDEmcSituation> pageResult = byPage.getResult();
		List<SESECDEmcSituationDTO> result = new ArrayList<>();
		SESECDEmcSituationWrapper wrapper = new SESECDEmcSituationWrapper();
		for (SESECDEmcSituation e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmcSituation(String ids) {
		boolean result = true;
		try {
			emcSituationService.deleteEmcSituation(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}