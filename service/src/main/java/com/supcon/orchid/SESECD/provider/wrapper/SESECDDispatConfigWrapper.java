package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDDispatConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDDispatConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDDispatConfigWrapper extends BaseEntityWrapper<SESECDDispatConfig, SESECDDispatConfigDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDDispatConfigDTO e2d(SESECDDispatConfig entity) {
		SESECDDispatConfig entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDDispatConfig.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDDispatConfigDTO.class);
	}

	@Override
	public SESECDDispatConfig d2e(SESECDDispatConfigDTO dto) {
		return BeanUtil.copy(dto, SESECDDispatConfig.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_dispatConfig_DispatConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
