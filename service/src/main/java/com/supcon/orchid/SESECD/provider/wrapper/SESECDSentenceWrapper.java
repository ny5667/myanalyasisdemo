package com.supcon.orchid.SESECD.provider.wrapper;

import com.supcon.orchid.SESECD.entities.SESECDSentence;
import com.supcon.orchid.ec.utils.BeanUtil;
import com.supcon.orchid.support.BaseEntityWrapper;
import com.supcon.orchid.SESECD.DTO.SESECDSentenceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/* CUSTOM CODE START(wapper,import,SESECD_1.0.0_situationSentence_Sentence,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public class SESECDSentenceWrapper extends BaseEntityWrapper<SESECDSentence, SESECDSentenceDTO> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public SESECDSentenceDTO e2d(SESECDSentence entity) {
		SESECDSentence entity2=entity;
		try{
			String entityStr = JSON.toJSONString(entity);
			entity2 =JSONObject.parseObject(entityStr, SESECDSentence.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return BeanUtil.copy(entity2, SESECDSentenceDTO.class);
	}

	@Override
	public SESECDSentence d2e(SESECDSentenceDTO dto) {
		return BeanUtil.copy(dto, SESECDSentence.class);
	}

	/* CUSTOM CODE START(wapper,functions,SESECD_1.0.0_situationSentence_Sentence,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
