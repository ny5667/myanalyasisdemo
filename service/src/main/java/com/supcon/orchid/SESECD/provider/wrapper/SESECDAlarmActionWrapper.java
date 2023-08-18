package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAlarmActionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAlarmActionWrapper extends BaseEntityWrapper<SESECDAlarmAction, SESECDAlarmActionDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAlarmActionDTO e2d(SESECDAlarmAction entity) {
		SESECDAlarmAction entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAlarmAction.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAlarmActionDTO.class);
	}

	@Override
	public SESECDAlarmAction d2e(SESECDAlarmActionDTO dto) {
		return BeanUtil.copy(dto, SESECDAlarmAction.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
