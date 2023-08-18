package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDBroadcastInfoClient;
import com.supcon.orchid.SESECD.DTO.SESECDBroadcastInfoDTO;
import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfo;
import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfoEditEntity;
import com.supcon.orchid.SESECD.services.SESECDBroadcastInfoService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDBroadcastInfoWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDBroadcastInfoClient implements ISESECDBroadcastInfoClient {

	@Autowired
	private SESECDBroadcastInfoService broadcastInfoService;

	@Override
	public SESECDBroadcastInfoDTO getSESECDBroadcastInfo(long id) {
		SESECDBroadcastInfoWrapper wrapper = new SESECDBroadcastInfoWrapper();
		SESECDBroadcastInfo result = broadcastInfoService.getBroadcastInfo(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDBroadcastInfoDTO> getByPage(Page<SESECDBroadcastInfoDTO> page) {
		Page<SESECDBroadcastInfo> entityPage = new Page<SESECDBroadcastInfo>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDBroadcastInfo> byPage = broadcastInfoService.getByPage(entityPage, null);
		List<SESECDBroadcastInfo> pageResult = byPage.getResult();
		List<SESECDBroadcastInfoDTO> result = new ArrayList<>();
		SESECDBroadcastInfoWrapper wrapper = new SESECDBroadcastInfoWrapper();
		for (SESECDBroadcastInfo e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDBroadcastInfo(String ids) {
		boolean result = true;
		try {
			broadcastInfoService.deleteBroadcastInfo(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}