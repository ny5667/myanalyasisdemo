package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdAlertVideoClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertVideoDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideoEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertVideoService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdAlertVideoWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdAlertVideoClient implements ISESECDEcdAlertVideoClient {

	@Autowired
	private SESECDEcdAlertVideoService ecdAlertVideoService;

	@Override
	public SESECDEcdAlertVideoDTO getSESECDEcdAlertVideo(long id) {
		SESECDEcdAlertVideoWrapper wrapper = new SESECDEcdAlertVideoWrapper();
		SESECDEcdAlertVideo result = ecdAlertVideoService.getEcdAlertVideo(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdAlertVideoDTO> getByPage(Page<SESECDEcdAlertVideoDTO> page) {
		Page<SESECDEcdAlertVideo> entityPage = new Page<SESECDEcdAlertVideo>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdAlertVideo> byPage = ecdAlertVideoService.getByPage(entityPage, null);
		List<SESECDEcdAlertVideo> pageResult = byPage.getResult();
		List<SESECDEcdAlertVideoDTO> result = new ArrayList<>();
		SESECDEcdAlertVideoWrapper wrapper = new SESECDEcdAlertVideoWrapper();
		for (SESECDEcdAlertVideo e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdAlertVideo(String ids) {
		boolean result = true;
		try {
			ecdAlertVideoService.deleteEcdAlertVideo(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}