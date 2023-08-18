package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmEventLeveLClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmEventLeveLDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveL;
import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveLEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmEventLeveLService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmEventLeveLWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmEventLeveLClient implements ISESECDEmEventLeveLClient {

	@Autowired
	private SESECDEmEventLeveLService emEventLeveLService;

	@Override
	public SESECDEmEventLeveLDTO getSESECDEmEventLeveL(long id) {
		SESECDEmEventLeveLWrapper wrapper = new SESECDEmEventLeveLWrapper();
		SESECDEmEventLeveL result = emEventLeveLService.getEmEventLeveL(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmEventLeveLDTO> getByPage(Page<SESECDEmEventLeveLDTO> page) {
		Page<SESECDEmEventLeveL> entityPage = new Page<SESECDEmEventLeveL>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmEventLeveL> byPage = emEventLeveLService.getByPage(entityPage, null);
		List<SESECDEmEventLeveL> pageResult = byPage.getResult();
		List<SESECDEmEventLeveLDTO> result = new ArrayList<>();
		SESECDEmEventLeveLWrapper wrapper = new SESECDEmEventLeveLWrapper();
		for (SESECDEmEventLeveL e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmEventLeveL(String ids) {
		boolean result = true;
		try {
			emEventLeveLService.deleteEmEventLeveL(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}