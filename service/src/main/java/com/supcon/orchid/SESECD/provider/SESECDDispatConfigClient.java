package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDDispatConfigClient;
import com.supcon.orchid.SESECD.DTO.SESECDDispatConfigDTO;
import com.supcon.orchid.SESECD.entities.SESECDDispatConfig;
import com.supcon.orchid.SESECD.entities.SESECDDispatConfigEditEntity;
import com.supcon.orchid.SESECD.services.SESECDDispatConfigService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDDispatConfigWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDDispatConfigClient implements ISESECDDispatConfigClient {

	@Autowired
	private SESECDDispatConfigService dispatConfigService;

	@Override
	public SESECDDispatConfigDTO getSESECDDispatConfig(long id) {
		SESECDDispatConfigWrapper wrapper = new SESECDDispatConfigWrapper();
		SESECDDispatConfig result = dispatConfigService.getDispatConfig(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDDispatConfigDTO> getByPage(Page<SESECDDispatConfigDTO> page) {
		Page<SESECDDispatConfig> entityPage = new Page<SESECDDispatConfig>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDDispatConfig> byPage = dispatConfigService.getByPage(entityPage, null);
		List<SESECDDispatConfig> pageResult = byPage.getResult();
		List<SESECDDispatConfigDTO> result = new ArrayList<>();
		SESECDDispatConfigWrapper wrapper = new SESECDDispatConfigWrapper();
		for (SESECDDispatConfig e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDDispatConfig(String ids) {
		boolean result = true;
		try {
			dispatConfigService.deleteDispatConfig(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}