package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDSentenceClient;
import com.supcon.orchid.SESECD.DTO.SESECDSentenceDTO;
import com.supcon.orchid.SESECD.entities.SESECDSentence;
import com.supcon.orchid.SESECD.entities.SESECDSentenceEditEntity;
import com.supcon.orchid.SESECD.services.SESECDSentenceService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDSentenceWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_situationSentence_Sentence,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDSentenceClient implements ISESECDSentenceClient {

	@Autowired
	private SESECDSentenceService sentenceService;

	@Override
	public SESECDSentenceDTO getSESECDSentence(long id) {
		SESECDSentenceWrapper wrapper = new SESECDSentenceWrapper();
		SESECDSentence result = sentenceService.getSentence(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDSentenceDTO> getByPage(Page<SESECDSentenceDTO> page) {
		Page<SESECDSentence> entityPage = new Page<SESECDSentence>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDSentence> byPage = sentenceService.getByPage(entityPage, null);
		List<SESECDSentence> pageResult = byPage.getResult();
		List<SESECDSentenceDTO> result = new ArrayList<>();
		SESECDSentenceWrapper wrapper = new SESECDSentenceWrapper();
		for (SESECDSentence e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDSentence(String ids) {
		boolean result = true;
		try {
			sentenceService.deleteSentence(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_situationSentence_Sentence,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}