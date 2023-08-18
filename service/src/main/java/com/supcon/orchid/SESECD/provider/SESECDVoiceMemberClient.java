package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDVoiceMemberClient;
import com.supcon.orchid.SESECD.DTO.SESECDVoiceMemberDTO;
import com.supcon.orchid.SESECD.entities.SESECDVoiceMember;
import com.supcon.orchid.SESECD.entities.SESECDVoiceMemberEditEntity;
import com.supcon.orchid.SESECD.services.SESECDVoiceMemberService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDVoiceMemberWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_voiceConfig_VoiceMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDVoiceMemberClient implements ISESECDVoiceMemberClient {

	@Autowired
	private SESECDVoiceMemberService voiceMemberService;

	@Override
	public SESECDVoiceMemberDTO getSESECDVoiceMember(long id) {
		SESECDVoiceMemberWrapper wrapper = new SESECDVoiceMemberWrapper();
		SESECDVoiceMember result = voiceMemberService.getVoiceMember(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDVoiceMemberDTO> getByPage(Page<SESECDVoiceMemberDTO> page) {
		Page<SESECDVoiceMember> entityPage = new Page<SESECDVoiceMember>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDVoiceMember> byPage = voiceMemberService.getByPage(entityPage, null);
		List<SESECDVoiceMember> pageResult = byPage.getResult();
		List<SESECDVoiceMemberDTO> result = new ArrayList<>();
		SESECDVoiceMemberWrapper wrapper = new SESECDVoiceMemberWrapper();
		for (SESECDVoiceMember e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDVoiceMember(String ids) {
		boolean result = true;
		try {
			voiceMemberService.deleteVoiceMember(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_voiceConfig_VoiceMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}