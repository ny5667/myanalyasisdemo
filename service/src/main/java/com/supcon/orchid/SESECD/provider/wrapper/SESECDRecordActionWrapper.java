package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDRecordActionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDRecordActionWrapper extends BaseEntityWrapper<SESECDRecordAction, SESECDRecordActionDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDRecordActionDTO e2d(SESECDRecordAction entity) {
		SESECDRecordAction entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDRecordAction.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDRecordActionDTO.class);
	}

	@Override
	public SESECDRecordAction d2e(SESECDRecordActionDTO dto) {
		return BeanUtil.copy(dto, SESECDRecordAction.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
