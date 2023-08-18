package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDMainDepartmentClient;
import com.supcon.orchid.SESECD.DTO.SESECDMainDepartmentDTO;
import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;
import com.supcon.orchid.SESECD.entities.SESECDMainDepartmentEditEntity;
import com.supcon.orchid.SESECD.services.SESECDMainDepartmentService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDMainDepartmentWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDMainDepartmentClient implements ISESECDMainDepartmentClient {

	@Autowired
	private SESECDMainDepartmentService mainDepartmentService;

	@Override
	public SESECDMainDepartmentDTO getSESECDMainDepartment(long id) {
		SESECDMainDepartmentWrapper wrapper = new SESECDMainDepartmentWrapper();
		SESECDMainDepartment result = mainDepartmentService.getMainDepartment(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDMainDepartmentDTO> getByPage(Page<SESECDMainDepartmentDTO> page) {
		Page<SESECDMainDepartment> entityPage = new Page<SESECDMainDepartment>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDMainDepartment> byPage = mainDepartmentService.getByPage(entityPage, null);
		List<SESECDMainDepartment> pageResult = byPage.getResult();
		List<SESECDMainDepartmentDTO> result = new ArrayList<>();
		SESECDMainDepartmentWrapper wrapper = new SESECDMainDepartmentWrapper();
		for (SESECDMainDepartment e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDMainDepartment(String ids) {
		boolean result = true;
		try {
			mainDepartmentService.deleteMainDepartment(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}