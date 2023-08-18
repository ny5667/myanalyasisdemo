package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDActVideoCameraClient;
import com.supcon.orchid.SESECD.DTO.SESECDActVideoCameraDTO;
import com.supcon.orchid.SESECD.entities.SESECDActVideoCamera;
import com.supcon.orchid.SESECD.entities.SESECDActVideoCameraEditEntity;
import com.supcon.orchid.SESECD.services.SESECDActVideoCameraService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDActVideoCameraWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emcAction_ActVideoCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDActVideoCameraClient implements ISESECDActVideoCameraClient {

	@Autowired
	private SESECDActVideoCameraService actVideoCameraService;

	@Override
	public SESECDActVideoCameraDTO getSESECDActVideoCamera(long id) {
		SESECDActVideoCameraWrapper wrapper = new SESECDActVideoCameraWrapper();
		SESECDActVideoCamera result = actVideoCameraService.getActVideoCamera(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDActVideoCameraDTO> getByPage(Page<SESECDActVideoCameraDTO> page) {
		Page<SESECDActVideoCamera> entityPage = new Page<SESECDActVideoCamera>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDActVideoCamera> byPage = actVideoCameraService.getByPage(entityPage, null);
		List<SESECDActVideoCamera> pageResult = byPage.getResult();
		List<SESECDActVideoCameraDTO> result = new ArrayList<>();
		SESECDActVideoCameraWrapper wrapper = new SESECDActVideoCameraWrapper();
		for (SESECDActVideoCamera e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDActVideoCamera(String ids) {
		boolean result = true;
		try {
			actVideoCameraService.deleteActVideoCamera(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emcAction_ActVideoCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}