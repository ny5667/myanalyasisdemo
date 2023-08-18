package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDRecorSituationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDRecorSituationWrapper extends BaseEntityWrapper<SESECDRecorSituation, SESECDRecorSituationDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDRecorSituationDTO e2d(SESECDRecorSituation entity) {
		SESECDRecorSituation entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDRecorSituation.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDRecorSituationDTO.class);
	}

	@Override
	public SESECDRecorSituation d2e(SESECDRecorSituationDTO dto) {
		return BeanUtil.copy(dto, SESECDRecorSituation.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
