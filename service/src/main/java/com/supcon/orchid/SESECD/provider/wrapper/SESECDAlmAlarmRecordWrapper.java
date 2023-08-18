package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAlmAlarmRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAlmAlarmRecordWrapper extends BaseEntityWrapper<SESECDAlmAlarmRecord, SESECDAlmAlarmRecordDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAlmAlarmRecordDTO e2d(SESECDAlmAlarmRecord entity) {
		SESECDAlmAlarmRecord entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAlmAlarmRecord.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAlmAlarmRecordDTO.class);
	}

	@Override
	public SESECDAlmAlarmRecord d2e(SESECDAlmAlarmRecordDTO dto) {
		return BeanUtil.copy(dto, SESECDAlmAlarmRecord.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
