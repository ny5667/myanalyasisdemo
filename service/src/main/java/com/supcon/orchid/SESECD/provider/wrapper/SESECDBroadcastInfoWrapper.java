package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfo;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDBroadcastInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDBroadcastInfoWrapper extends BaseEntityWrapper<SESECDBroadcastInfo, SESECDBroadcastInfoDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDBroadcastInfoDTO e2d(SESECDBroadcastInfo entity) {
		SESECDBroadcastInfo entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDBroadcastInfo.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDBroadcastInfoDTO.class);
	}

	@Override
	public SESECDBroadcastInfo d2e(SESECDBroadcastInfoDTO dto) {
		return BeanUtil.copy(dto, SESECDBroadcastInfo.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
