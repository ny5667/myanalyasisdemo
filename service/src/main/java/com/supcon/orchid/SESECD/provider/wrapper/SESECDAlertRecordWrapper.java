package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAlertRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAlertRecordWrapper extends BaseEntityWrapper<SESECDAlertRecord, SESECDAlertRecordDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAlertRecordDTO e2d(SESECDAlertRecord entity) {
		SESECDAlertRecord entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAlertRecord.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAlertRecordDTO.class);
	}

	@Override
	public SESECDAlertRecord d2e(SESECDAlertRecordDTO dto) {
		return BeanUtil.copy(dto, SESECDAlertRecord.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
