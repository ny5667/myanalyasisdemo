package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDMainDepartmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDMainDepartmentWrapper extends BaseEntityWrapper<SESECDMainDepartment, SESECDMainDepartmentDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDMainDepartmentDTO e2d(SESECDMainDepartment entity) {
		SESECDMainDepartment entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDMainDepartment.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDMainDepartmentDTO.class);
	}

	@Override
	public SESECDMainDepartment d2e(SESECDMainDepartmentDTO dto) {
		return BeanUtil.copy(dto, SESECDMainDepartment.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
