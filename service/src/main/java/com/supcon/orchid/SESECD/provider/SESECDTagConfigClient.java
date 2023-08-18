package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDTagConfigClient;
import com.supcon.orchid.SESECD.DTO.SESECDTagConfigDTO;
import com.supcon.orchid.SESECD.entities.SESECDTagConfig;
import com.supcon.orchid.SESECD.entities.SESECDTagConfigEditEntity;
import com.supcon.orchid.SESECD.services.SESECDTagConfigService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDTagConfigWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDTagConfigClient implements ISESECDTagConfigClient {

	@Autowired
	private SESECDTagConfigService tagConfigService;

	@Override
	public SESECDTagConfigDTO getSESECDTagConfig(long id) {
		SESECDTagConfigWrapper wrapper = new SESECDTagConfigWrapper();
		SESECDTagConfig result = tagConfigService.getTagConfig(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDTagConfigDTO> getByPage(Page<SESECDTagConfigDTO> page) {
		Page<SESECDTagConfig> entityPage = new Page<SESECDTagConfig>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDTagConfig> byPage = tagConfigService.getByPage(entityPage, null);
		List<SESECDTagConfig> pageResult = byPage.getResult();
		List<SESECDTagConfigDTO> result = new ArrayList<>();
		SESECDTagConfigWrapper wrapper = new SESECDTagConfigWrapper();
		for (SESECDTagConfig e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDTagConfig(String ids) {
		boolean result = true;
		try {
			tagConfigService.deleteTagConfig(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}