package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDSignalConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDSignalConfigWrapper extends BaseEntityWrapper<SESECDSignalConfig, SESECDSignalConfigDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDSignalConfigDTO e2d(SESECDSignalConfig entity) {
		SESECDSignalConfig entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDSignalConfig.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDSignalConfigDTO.class);
	}

	@Override
	public SESECDSignalConfig d2e(SESECDSignalConfigDTO dto) {
		return BeanUtil.copy(dto, SESECDSignalConfig.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
