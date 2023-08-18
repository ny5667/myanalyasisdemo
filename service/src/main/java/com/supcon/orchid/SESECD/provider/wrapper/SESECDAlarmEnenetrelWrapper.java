package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmEnenetrelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAlarmEnenetrelWrapper extends BaseEntityWrapper<SESECDAlarmEnenetrel, SESECDAlarmEnenetrelDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAlarmEnenetrelDTO e2d(SESECDAlarmEnenetrel entity) {
		SESECDAlarmEnenetrel entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAlarmEnenetrel.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAlarmEnenetrelDTO.class);
	}

	@Override
	public SESECDAlarmEnenetrel d2e(SESECDAlarmEnenetrelDTO dto) {
		return BeanUtil.copy(dto, SESECDAlarmEnenetrel.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
