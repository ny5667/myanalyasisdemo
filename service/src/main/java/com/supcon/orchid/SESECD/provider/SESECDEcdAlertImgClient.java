package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEcdAlertImgClient;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertImgDTO;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImgEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertImgService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEcdAlertImgWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEcdAlertImgClient implements ISESECDEcdAlertImgClient {

	@Autowired
	private SESECDEcdAlertImgService ecdAlertImgService;

	@Override
	public SESECDEcdAlertImgDTO getSESECDEcdAlertImg(long id) {
		SESECDEcdAlertImgWrapper wrapper = new SESECDEcdAlertImgWrapper();
		SESECDEcdAlertImg result = ecdAlertImgService.getEcdAlertImg(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEcdAlertImgDTO> getByPage(Page<SESECDEcdAlertImgDTO> page) {
		Page<SESECDEcdAlertImg> entityPage = new Page<SESECDEcdAlertImg>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEcdAlertImg> byPage = ecdAlertImgService.getByPage(entityPage, null);
		List<SESECDEcdAlertImg> pageResult = byPage.getResult();
		List<SESECDEcdAlertImgDTO> result = new ArrayList<>();
		SESECDEcdAlertImgWrapper wrapper = new SESECDEcdAlertImgWrapper();
		for (SESECDEcdAlertImg e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEcdAlertImg(String ids) {
		boolean result = true;
		try {
			ecdAlertImgService.deleteEcdAlertImg(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}