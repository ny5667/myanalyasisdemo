package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmerExpertsClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmerExpertsDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmerExperts;
import com.supcon.orchid.SESECD.entities.SESECDEmerExpertsEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmerExpertsService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmerExpertsWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_addrBook_EmerExperts,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmerExpertsClient implements ISESECDEmerExpertsClient {

	@Autowired
	private SESECDEmerExpertsService emerExpertsService;

	@Override
	public SESECDEmerExpertsDTO getSESECDEmerExperts(long id) {
		SESECDEmerExpertsWrapper wrapper = new SESECDEmerExpertsWrapper();
		SESECDEmerExperts result = emerExpertsService.getEmerExperts(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmerExpertsDTO> getByPage(Page<SESECDEmerExpertsDTO> page) {
		Page<SESECDEmerExperts> entityPage = new Page<SESECDEmerExperts>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmerExperts> byPage = emerExpertsService.getByPage(entityPage, null);
		List<SESECDEmerExperts> pageResult = byPage.getResult();
		List<SESECDEmerExpertsDTO> result = new ArrayList<>();
		SESECDEmerExpertsWrapper wrapper = new SESECDEmerExpertsWrapper();
		for (SESECDEmerExperts e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmerExperts(String ids) {
		boolean result = true;
		try {
			emerExpertsService.deleteEmerExperts(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_addrBook_EmerExperts,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}