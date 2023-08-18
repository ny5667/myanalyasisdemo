package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAllEmerMemberClient;
import com.supcon.orchid.SESECD.DTO.SESECDAllEmerMemberDTO;
import com.supcon.orchid.SESECD.entities.SESECDAllEmerMember;
import com.supcon.orchid.SESECD.entities.SESECDAllEmerMemberEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAllEmerMemberService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAllEmerMemberWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAllEmerMemberClient implements ISESECDAllEmerMemberClient {

	@Autowired
	private SESECDAllEmerMemberService allEmerMemberService;

	@Override
	public SESECDAllEmerMemberDTO getSESECDAllEmerMember(long id) {
		SESECDAllEmerMemberWrapper wrapper = new SESECDAllEmerMemberWrapper();
		SESECDAllEmerMember result = allEmerMemberService.getAllEmerMember(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAllEmerMemberDTO> getByPage(Page<SESECDAllEmerMemberDTO> page) {
		Page<SESECDAllEmerMember> entityPage = new Page<SESECDAllEmerMember>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAllEmerMember> byPage = allEmerMemberService.getByPage(entityPage, null);
		List<SESECDAllEmerMember> pageResult = byPage.getResult();
		List<SESECDAllEmerMemberDTO> result = new ArrayList<>();
		SESECDAllEmerMemberWrapper wrapper = new SESECDAllEmerMemberWrapper();
		for (SESECDAllEmerMember e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAllEmerMember(String ids) {
		boolean result = true;
		try {
			allEmerMemberService.deleteAllEmerMember(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}