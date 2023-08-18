package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDSourceTerminalClient;
import com.supcon.orchid.SESECD.DTO.SESECDSourceTerminalDTO;
import com.supcon.orchid.SESECD.entities.SESECDSourceTerminal;
import com.supcon.orchid.SESECD.entities.SESECDSourceTerminalEditEntity;
import com.supcon.orchid.SESECD.services.SESECDSourceTerminalService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDSourceTerminalWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDSourceTerminalClient implements ISESECDSourceTerminalClient {

	@Autowired
	private SESECDSourceTerminalService sourceTerminalService;

	@Override
	public SESECDSourceTerminalDTO getSESECDSourceTerminal(long id) {
		SESECDSourceTerminalWrapper wrapper = new SESECDSourceTerminalWrapper();
		SESECDSourceTerminal result = sourceTerminalService.getSourceTerminal(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDSourceTerminalDTO> getByPage(Page<SESECDSourceTerminalDTO> page) {
		Page<SESECDSourceTerminal> entityPage = new Page<SESECDSourceTerminal>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDSourceTerminal> byPage = sourceTerminalService.getByPage(entityPage, null);
		List<SESECDSourceTerminal> pageResult = byPage.getResult();
		List<SESECDSourceTerminalDTO> result = new ArrayList<>();
		SESECDSourceTerminalWrapper wrapper = new SESECDSourceTerminalWrapper();
		for (SESECDSourceTerminal e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDSourceTerminal(String ids) {
		boolean result = true;
		try {
			sourceTerminalService.deleteSourceTerminal(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}