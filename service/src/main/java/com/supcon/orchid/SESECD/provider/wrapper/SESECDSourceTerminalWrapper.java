package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDSourceTerminal;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDSourceTerminalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDSourceTerminalWrapper extends BaseEntityWrapper<SESECDSourceTerminal, SESECDSourceTerminalDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDSourceTerminalDTO e2d(SESECDSourceTerminal entity) {
		SESECDSourceTerminal entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDSourceTerminal.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDSourceTerminalDTO.class);
	}

	@Override
	public SESECDSourceTerminal d2e(SESECDSourceTerminalDTO dto) {
		return BeanUtil.copy(dto, SESECDSourceTerminal.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_signalConfig_SourceTerminal,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
