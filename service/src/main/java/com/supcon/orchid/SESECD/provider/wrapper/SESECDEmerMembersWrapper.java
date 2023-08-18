package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDEmerMembers;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDEmerMembersDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDEmerMembersWrapper extends BaseEntityWrapper<SESECDEmerMembers, SESECDEmerMembersDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDEmerMembersDTO e2d(SESECDEmerMembers entity) {
		SESECDEmerMembers entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDEmerMembers.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDEmerMembersDTO.class);
	}

	@Override
	public SESECDEmerMembers d2e(SESECDEmerMembersDTO dto) {
		return BeanUtil.copy(dto, SESECDEmerMembers.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
