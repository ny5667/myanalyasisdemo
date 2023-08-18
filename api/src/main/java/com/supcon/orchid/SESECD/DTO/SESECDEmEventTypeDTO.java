package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 事件分类.
 *  
 */
public class SESECDEmEventTypeDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String emEventType ; // 事件分类
		private Boolean isInitialize =
			Boolean.valueOf("false")
		;// 是否默认数据


	private Document document;

	private String bapAttachmentInfo;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}


    public String getEmEventType() {
        return emEventType;
    }

    public void setEmEventType(String emEventType) {
        this.emEventType = emEventType;
    }

    public Boolean getIsInitialize() {
        return isInitialize;
    }

    public void setIsInitialize(Boolean isInitialize) {
        this.isInitialize = isInitialize;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}


	private String virtualId;
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	@Override
	protected String _getEntityName() {
		return null;
	}

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emEventInfo_EmEventType,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
