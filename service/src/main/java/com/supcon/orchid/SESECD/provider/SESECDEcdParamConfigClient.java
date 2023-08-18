package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdParamConfigClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdParamConfigDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfigEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdParamConfigService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdParamConfigWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdParamConfigClient implements ISESECDEcdParamConfigClient {

	@Autowired
	private SESECDEcdParamConfigService ecdParamConfigService;

	@Override
	public SESECDEcdParamConfigDTO getSESECDEcdParamConfig(long id) {
		SESECDEcdParamConfigWrapper wrapper = new SESECDEcdParamConfigWrapper();
		SESECDEcdParamConfig result = ecdParamConfigService.getEcdParamConfig(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdParamConfigDTO> getByPage(Page<SESECDEcdParamConfigDTO> page) {
		Page<SESECDEcdParamConfig> entityPage = new Page<SESECDEcdParamConfig>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdParamConfig> byPage = ecdParamConfigService.getByPage(entityPage, null);
		List<SESECDEcdParamConfig> pageResult = byPage.getResult();
		List<SESECDEcdParamConfigDTO> result = new ArrayList<>();
		SESECDEcdParamConfigWrapper wrapper = new SESECDEcdParamConfigWrapper();
		for (SESECDEcdParamConfig e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdParamConfig(String ids) {
		boolean result = true;
		try {
			ecdParamConfigService.deleteEcdParamConfig(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}