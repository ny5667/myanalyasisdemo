package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDRecorSituationClient;
import com.supcon.orchid.SESECD.DTO.SESECDRecorSituationDTO;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituationEditEntity;
import com.supcon.orchid.SESECD.services.SESECDRecorSituationService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDRecorSituationWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDRecorSituationClient implements ISESECDRecorSituationClient {

	@Autowired
	private SESECDRecorSituationService recorSituationService;

	@Override
	public SESECDRecorSituationDTO getSESECDRecorSituation(long id) {
		SESECDRecorSituationWrapper wrapper = new SESECDRecorSituationWrapper();
		SESECDRecorSituation result = recorSituationService.getRecorSituation(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDRecorSituationDTO> getByPage(Page<SESECDRecorSituationDTO> page) {
		Page<SESECDRecorSituation> entityPage = new Page<SESECDRecorSituation>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDRecorSituation> byPage = recorSituationService.getByPage(entityPage, null);
		List<SESECDRecorSituation> pageResult = byPage.getResult();
		List<SESECDRecorSituationDTO> result = new ArrayList<>();
		SESECDRecorSituationWrapper wrapper = new SESECDRecorSituationWrapper();
		for (SESECDRecorSituation e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDRecorSituation(String ids) {
		boolean result = true;
		try {
			recorSituationService.deleteRecorSituation(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}