package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEcdAction;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEcdActionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEcdActionWrapper extends BaseEntityWrapper<SESECDEcdAction, SESECDEcdActionDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEcdActionDTO e2d(SESECDEcdAction entity) {
		SESECDEcdAction entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEcdAction.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEcdActionDTO.class);
	}

	@Override
	public SESECDEcdAction d2e(SESECDEcdActionDTO dto) {
		return BeanUtil.copy(dto, SESECDEcdAction.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
