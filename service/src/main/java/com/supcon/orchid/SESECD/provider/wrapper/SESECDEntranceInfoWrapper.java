package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEntranceInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEntranceInfoWrapper extends BaseEntityWrapper<SESECDEntranceInfo, SESECDEntranceInfoDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEntranceInfoDTO e2d(SESECDEntranceInfo entity) {
		SESECDEntranceInfo entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEntranceInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEntranceInfoDTO.class);
	}

	@Override
	public SESECDEntranceInfo d2e(SESECDEntranceInfoDTO dto) {
		return BeanUtil.copy(dto, SESECDEntranceInfo.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_doorAccessControl_EntranceInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
