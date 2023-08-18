package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAccident;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAccidentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAccidentWrapper extends BaseEntityWrapper<SESECDAccident, SESECDAccidentDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAccidentDTO e2d(SESECDAccident entity) {
		SESECDAccident entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAccident.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAccidentDTO.class);
	}

	@Override
	public SESECDAccident d2e(SESECDAccidentDTO dto) {
		return BeanUtil.copy(dto, SESECDAccident.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
