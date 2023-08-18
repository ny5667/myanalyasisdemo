package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDVoiceConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDVoiceConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDVoiceConfigWrapper extends BaseEntityWrapper<SESECDVoiceConfig, SESECDVoiceConfigDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDVoiceConfigDTO e2d(SESECDVoiceConfig entity) {
		SESECDVoiceConfig entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDVoiceConfig.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDVoiceConfigDTO.class);
	}

	@Override
	public SESECDVoiceConfig d2e(SESECDVoiceConfigDTO dto) {
		return BeanUtil.copy(dto, SESECDVoiceConfig.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
