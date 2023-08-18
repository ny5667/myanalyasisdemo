package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDCommunicationClient;
import com.supcon.orchid.SESECD.DTO.SESECDCommunicationDTO;
import com.supcon.orchid.SESECD.entities.SESECDCommunication;
import com.supcon.orchid.SESECD.entities.SESECDCommunicationEditEntity;
import com.supcon.orchid.SESECD.services.SESECDCommunicationService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDCommunicationWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDCommunicationClient implements ISESECDCommunicationClient {

	@Autowired
	private SESECDCommunicationService communicationService;

	@Override
	public SESECDCommunicationDTO getSESECDCommunication(long id) {
		SESECDCommunicationWrapper wrapper = new SESECDCommunicationWrapper();
		SESECDCommunication result = communicationService.getCommunication(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDCommunicationDTO> getByPage(Page<SESECDCommunicationDTO> page) {
		Page<SESECDCommunication> entityPage = new Page<SESECDCommunication>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDCommunication> byPage = communicationService.getByPage(entityPage, null);
		List<SESECDCommunication> pageResult = byPage.getResult();
		List<SESECDCommunicationDTO> result = new ArrayList<>();
		SESECDCommunicationWrapper wrapper = new SESECDCommunicationWrapper();
		for (SESECDCommunication e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDCommunication(String ids) {
		boolean result = true;
		try {
			communicationService.deleteCommunication(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}