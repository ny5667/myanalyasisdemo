package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertImgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEcdAlertImgWrapper extends BaseEntityWrapper<SESECDEcdAlertImg, SESECDEcdAlertImgDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEcdAlertImgDTO e2d(SESECDEcdAlertImg entity) {
		SESECDEcdAlertImg entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEcdAlertImg.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEcdAlertImgDTO.class);
	}

	@Override
	public SESECDEcdAlertImg d2e(SESECDEcdAlertImgDTO dto) {
		return BeanUtil.copy(dto, SESECDEcdAlertImg.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
