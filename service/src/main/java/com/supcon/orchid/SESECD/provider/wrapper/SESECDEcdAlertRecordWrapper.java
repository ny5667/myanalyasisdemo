package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEcdAlertRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEcdAlertRecordWrapper extends BaseEntityWrapper<SESECDEcdAlertRecord, SESECDEcdAlertRecordDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEcdAlertRecordDTO e2d(SESECDEcdAlertRecord entity) {
		SESECDEcdAlertRecord entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEcdAlertRecord.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEcdAlertRecordDTO.class);
	}

	@Override
	public SESECDEcdAlertRecord d2e(SESECDEcdAlertRecordDTO dto) {
		return BeanUtil.copy(dto, SESECDEcdAlertRecord.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
