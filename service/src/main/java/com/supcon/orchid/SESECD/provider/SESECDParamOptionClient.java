package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDParamOptionClient;
import com.supcon.orchid.SESECD.DTO.SESECDParamOptionDTO;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.SESECD.entities.SESECDParamOptionEditEntity;
import com.supcon.orchid.SESECD.services.SESECDParamOptionService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDParamOptionWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDParamOptionClient implements ISESECDParamOptionClient {

	@Autowired
	private SESECDParamOptionService paramOptionService;

	@Override
	public SESECDParamOptionDTO getSESECDParamOption(long id) {
		SESECDParamOptionWrapper wrapper = new SESECDParamOptionWrapper();
		SESECDParamOption result = paramOptionService.getParamOption(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDParamOptionDTO> getByPage(Page<SESECDParamOptionDTO> page) {
		Page<SESECDParamOption> entityPage = new Page<SESECDParamOption>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDParamOption> byPage = paramOptionService.getByPage(entityPage, null);
		List<SESECDParamOption> pageResult = byPage.getResult();
		List<SESECDParamOptionDTO> result = new ArrayList<>();
		SESECDParamOptionWrapper wrapper = new SESECDParamOptionWrapper();
		for (SESECDParamOption e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDParamOption(String ids) {
		boolean result = true;
		try {
			paramOptionService.deleteParamOption(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}