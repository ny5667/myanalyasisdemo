package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEmEventType;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEmEventTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEmEventTypeWrapper extends BaseEntityWrapper<SESECDEmEventType, SESECDEmEventTypeDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEmEventTypeDTO e2d(SESECDEmEventType entity) {
		SESECDEmEventType entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEmEventType.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEmEventTypeDTO.class);
	}

	@Override
	public SESECDEmEventType d2e(SESECDEmEventTypeDTO dto) {
		return BeanUtil.copy(dto, SESECDEmEventType.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
