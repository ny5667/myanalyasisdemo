package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDTagConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDTagConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDTagConfigWrapper extends BaseEntityWrapper<SESECDTagConfig, SESECDTagConfigDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDTagConfigDTO e2d(SESECDTagConfig entity) {
		SESECDTagConfig entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDTagConfig.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDTagConfigDTO.class);
	}

	@Override
	public SESECDTagConfig d2e(SESECDTagConfigDTO dto) {
		return BeanUtil.copy(dto, SESECDTagConfig.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
