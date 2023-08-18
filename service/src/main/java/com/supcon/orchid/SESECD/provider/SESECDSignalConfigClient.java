package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDSignalConfigClient;
import com.supcon.orchid.SESECD.DTO.SESECDSignalConfigDTO;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfigEditEntity;
import com.supcon.orchid.SESECD.services.SESECDSignalConfigService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDSignalConfigWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDSignalConfigClient implements ISESECDSignalConfigClient {

	@Autowired
	private SESECDSignalConfigService signalConfigService;

	@Override
	public SESECDSignalConfigDTO getSESECDSignalConfig(long id) {
		SESECDSignalConfigWrapper wrapper = new SESECDSignalConfigWrapper();
		SESECDSignalConfig result = signalConfigService.getSignalConfig(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDSignalConfigDTO> getByPage(Page<SESECDSignalConfigDTO> page) {
		Page<SESECDSignalConfig> entityPage = new Page<SESECDSignalConfig>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDSignalConfig> byPage = signalConfigService.getByPage(entityPage, null);
		List<SESECDSignalConfig> pageResult = byPage.getResult();
		List<SESECDSignalConfigDTO> result = new ArrayList<>();
		SESECDSignalConfigWrapper wrapper = new SESECDSignalConfigWrapper();
		for (SESECDSignalConfig e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDSignalConfig(String ids) {
		boolean result = true;
		try {
			signalConfigService.deleteSignalConfig(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}