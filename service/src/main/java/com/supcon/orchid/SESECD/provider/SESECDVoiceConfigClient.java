package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDVoiceConfigClient;
import com.supcon.orchid.SESECD.DTO.SESECDVoiceConfigDTO;
import com.supcon.orchid.SESECD.entities.SESECDVoiceConfig;
import com.supcon.orchid.SESECD.entities.SESECDVoiceConfigEditEntity;
import com.supcon.orchid.SESECD.services.SESECDVoiceConfigService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDVoiceConfigWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDVoiceConfigClient implements ISESECDVoiceConfigClient {

	@Autowired
	private SESECDVoiceConfigService voiceConfigService;

	@Override
	public SESECDVoiceConfigDTO getSESECDVoiceConfig(long id) {
		SESECDVoiceConfigWrapper wrapper = new SESECDVoiceConfigWrapper();
		SESECDVoiceConfig result = voiceConfigService.getVoiceConfig(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDVoiceConfigDTO> getByPage(Page<SESECDVoiceConfigDTO> page) {
		Page<SESECDVoiceConfig> entityPage = new Page<SESECDVoiceConfig>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDVoiceConfig> byPage = voiceConfigService.getByPage(entityPage, null);
		List<SESECDVoiceConfig> pageResult = byPage.getResult();
		List<SESECDVoiceConfigDTO> result = new ArrayList<>();
		SESECDVoiceConfigWrapper wrapper = new SESECDVoiceConfigWrapper();
		for (SESECDVoiceConfig e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDVoiceConfig(String ids) {
		boolean result = true;
		try {
			voiceConfigService.deleteVoiceConfig(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}