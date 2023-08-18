package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEmcActionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEmcActionWrapper extends BaseEntityWrapper<SESECDEmcAction, SESECDEmcActionDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEmcActionDTO e2d(SESECDEmcAction entity) {
		SESECDEmcAction entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEmcAction.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEmcActionDTO.class);
	}

	@Override
	public SESECDEmcAction d2e(SESECDEmcActionDTO dto) {
		return BeanUtil.copy(dto, SESECDEmcAction.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
