package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDEmerMembersClient;
import com.supcon.orchid.SESECD.DTO.SESECDEmerMembersDTO;
import com.supcon.orchid.SESECD.entities.SESECDEmerMembers;
import com.supcon.orchid.SESECD.entities.SESECDEmerMembersEditEntity;
import com.supcon.orchid.SESECD.services.SESECDEmerMembersService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDEmerMembersWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDEmerMembersClient implements ISESECDEmerMembersClient {

	@Autowired
	private SESECDEmerMembersService emerMembersService;

	@Override
	public SESECDEmerMembersDTO getSESECDEmerMembers(long id) {
		SESECDEmerMembersWrapper wrapper = new SESECDEmerMembersWrapper();
		SESECDEmerMembers result = emerMembersService.getEmerMembers(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDEmerMembersDTO> getByPage(Page<SESECDEmerMembersDTO> page) {
		Page<SESECDEmerMembers> entityPage = new Page<SESECDEmerMembers>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDEmerMembers> byPage = emerMembersService.getByPage(entityPage, null);
		List<SESECDEmerMembers> pageResult = byPage.getResult();
		List<SESECDEmerMembersDTO> result = new ArrayList<>();
		SESECDEmerMembersWrapper wrapper = new SESECDEmerMembersWrapper();
		for (SESECDEmerMembers e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDEmerMembers(String ids) {
		boolean result = true;
		try {
			emerMembersService.deleteEmerMembers(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}