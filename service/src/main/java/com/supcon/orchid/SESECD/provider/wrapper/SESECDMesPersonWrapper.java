package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDMesPerson;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDMesPersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDMesPersonWrapper extends BaseEntityWrapper<SESECDMesPerson, SESECDMesPersonDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDMesPersonDTO e2d(SESECDMesPerson entity) {
		SESECDMesPerson entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDMesPerson.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDMesPersonDTO.class);
	}

	@Override
	public SESECDMesPerson d2e(SESECDMesPersonDTO dto) {
		return BeanUtil.copy(dto, SESECDMesPerson.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
