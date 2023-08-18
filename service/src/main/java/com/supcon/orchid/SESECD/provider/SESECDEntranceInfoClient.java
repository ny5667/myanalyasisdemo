package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEntranceInfoClient;
import com.supcon.orchid.SESECD.DTO.SESECDEntranceInfoDTO;
import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;
import com.supcon.orchid.SESECD.entities.SESECDEntranceInfoEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEntranceInfoService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEntranceInfoWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEntranceInfoClient implements ISESECDEntranceInfoClient {

	@Autowired
	private SESECDEntranceInfoService entranceInfoService;

	@Override
	public SESECDEntranceInfoDTO getSESECDEntranceInfo(long id) {
		SESECDEntranceInfoWrapper wrapper = new SESECDEntranceInfoWrapper();
		SESECDEntranceInfo result = entranceInfoService.getEntranceInfo(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEntranceInfoDTO> getByPage(Page<SESECDEntranceInfoDTO> page) {
		Page<SESECDEntranceInfo> entityPage = new Page<SESECDEntranceInfo>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEntranceInfo> byPage = entranceInfoService.getByPage(entityPage, null);
		List<SESECDEntranceInfo> pageResult = byPage.getResult();
		List<SESECDEntranceInfoDTO> result = new ArrayList<>();
		SESECDEntranceInfoWrapper wrapper = new SESECDEntranceInfoWrapper();
		for (SESECDEntranceInfo e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEntranceInfo(String ids) {
		boolean result = true;
		try {
			entranceInfoService.deleteEntranceInfo(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}