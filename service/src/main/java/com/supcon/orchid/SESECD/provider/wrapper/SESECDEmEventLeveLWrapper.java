package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveL;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEmEventLeveLDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEmEventLeveLWrapper extends BaseEntityWrapper<SESECDEmEventLeveL, SESECDEmEventLeveLDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEmEventLeveLDTO e2d(SESECDEmEventLeveL entity) {
		SESECDEmEventLeveL entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEmEventLeveL.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEmEventLeveLDTO.class);
	}

	@Override
	public SESECDEmEventLeveL d2e(SESECDEmEventLeveLDTO dto) {
		return BeanUtil.copy(dto, SESECDEmEventLeveL.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
