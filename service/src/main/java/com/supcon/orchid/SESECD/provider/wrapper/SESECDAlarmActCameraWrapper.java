package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAlarmActCamera;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmActCameraDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAlarmActCameraWrapper extends BaseEntityWrapper<SESECDAlarmActCamera, SESECDAlarmActCameraDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAlarmActCameraDTO e2d(SESECDAlarmActCamera entity) {
		SESECDAlarmActCamera entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAlarmActCamera.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAlarmActCameraDTO.class);
	}

	@Override
	public SESECDAlarmActCamera d2e(SESECDAlarmActCameraDTO dto) {
		return BeanUtil.copy(dto, SESECDAlarmActCamera.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_AlarmActCamera,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
