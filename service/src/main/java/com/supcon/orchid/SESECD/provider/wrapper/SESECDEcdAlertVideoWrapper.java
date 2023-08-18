package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertVideoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEcdAlertVideoWrapper extends BaseEntityWrapper<SESECDEcdAlertVideo, SESECDEcdAlertVideoDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEcdAlertVideoDTO e2d(SESECDEcdAlertVideo entity) {
		SESECDEcdAlertVideo entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEcdAlertVideo.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEcdAlertVideoDTO.class);
	}

	@Override
	public SESECDEcdAlertVideo d2e(SESECDEcdAlertVideoDTO dto) {
		return BeanUtil.copy(dto, SESECDEcdAlertVideo.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertVideo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
