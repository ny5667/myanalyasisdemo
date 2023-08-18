package com.supcon.orchid.SESECD.provider;

import java.util.*;
import com.supcon.orchid.SESECD.client.ISESECDAlarmActCameraClient;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmActCameraDTO;
import com.supcon.orchid.SESECD.entities.SESECDAlarmActCamera;
import com.supcon.orchid.SESECD.entities.SESECDAlarmActCameraEditEntity;
import com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService;
import com.supcon.orchid.SESECD.provider.wrapper.SESECDAlarmActCameraWrapper;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
/* CUSTOM CODE START(provider,import,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Primary
@RestController
public class SESECDAlarmActCameraClient implements ISESECDAlarmActCameraClient {

	@Autowired
	private SESECDAlarmActCameraService alarmActCameraService;

	@Override
	public SESECDAlarmActCameraDTO getSESECDAlarmActCamera(long id) {
		SESECDAlarmActCameraWrapper wrapper = new SESECDAlarmActCameraWrapper();
		SESECDAlarmActCamera result = alarmActCameraService.getAlarmActCamera(id);
		return wrapper.e2d(result);
	}

	@Override
	public Page<SESECDAlarmActCameraDTO> getByPage(Page<SESECDAlarmActCameraDTO> page) {
		Page<SESECDAlarmActCamera> entityPage = new Page<SESECDAlarmActCamera>();
		entityPage.setPageSize(page.getPageSize());
		entityPage.setPageNo(page.getPageNo());
		entityPage.setPaging(page.isPaging());
		Page<SESECDAlarmActCamera> byPage = alarmActCameraService.getByPage(entityPage, null);
		List<SESECDAlarmActCamera> pageResult = byPage.getResult();
		List<SESECDAlarmActCameraDTO> result = new ArrayList<>();
		SESECDAlarmActCameraWrapper wrapper = new SESECDAlarmActCameraWrapper();
		for (SESECDAlarmActCamera e : pageResult) {
			result.add(wrapper.e2d(e));
		}
		page.setResult(result);
		return page;
	}
/*
	@Override
	public boolean deleteSESECDAlarmActCamera(String ids) {
		boolean result = true;
		try {
			alarmActCameraService.deleteAlarmActCamera(ids);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
/*
*/
	/* CUSTOM CODE START(provider,functions,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}