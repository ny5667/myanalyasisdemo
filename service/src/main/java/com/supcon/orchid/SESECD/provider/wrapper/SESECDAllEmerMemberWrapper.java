package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDAllEmerMember;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDAllEmerMemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDAllEmerMemberWrapper extends BaseEntityWrapper<SESECDAllEmerMember, SESECDAllEmerMemberDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDAllEmerMemberDTO e2d(SESECDAllEmerMember entity) {
		SESECDAllEmerMember entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDAllEmerMember.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDAllEmerMemberDTO.class);
	}

	@Override
	public SESECDAllEmerMember d2e(SESECDAllEmerMemberDTO dto) {
		return BeanUtil.copy(dto, SESECDAllEmerMember.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
