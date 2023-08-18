package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDParamOptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDParamOptionWrapper extends BaseEntityWrapper<SESECDParamOption, SESECDParamOptionDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDParamOptionDTO e2d(SESECDParamOption entity) {
		SESECDParamOption entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDParamOption.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDParamOptionDTO.class);
	}

	@Override
	public SESECDParamOption d2e(SESECDParamOptionDTO dto) {
		return BeanUtil.copy(dto, SESECDParamOption.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
