package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEcdParamConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEcdParamConfigWrapper extends BaseEntityWrapper<SESECDEcdParamConfig, SESECDEcdParamConfigDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEcdParamConfigDTO e2d(SESECDEcdParamConfig entity) {
		SESECDEcdParamConfig entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEcdParamConfig.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEcdParamConfigDTO.class);
	}

	@Override
	public SESECDEcdParamConfig d2e(SESECDEcdParamConfigDTO dto) {
		return BeanUtil.copy(dto, SESECDEcdParamConfig.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_paramConfig_EcdParamConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
