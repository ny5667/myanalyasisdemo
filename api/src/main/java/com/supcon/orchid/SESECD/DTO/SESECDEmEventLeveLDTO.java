package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 事件分级.
 *  
 */
public class SESECDEmEventLeveLDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private String emEventLevel ; // 事件分级
	private String gradingDefinition ; // 定义
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


    public String getEmEventLevel() {
        return emEventLevel;
    }

    public void setEmEventLevel(String emEventLevel) {
        this.emEventLevel = emEventLevel;
    }

    public String getGradingDefinition() {
        return gradingDefinition;
    }

    public void setGradingDefinition(String gradingDefinition) {
        this.gradingDefinition = gradingDefinition;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emEventInfo_EmEventLeveL,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
