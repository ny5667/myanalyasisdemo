package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESWssEP.DTO.SESWssEPEmergencyPlanDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联预案.
 */
public class SESECDEmePlanObjDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESECDAlmAlarmRecordDTO alarmId;
		private Boolean isClose =
			Boolean.valueOf("true")
		;// 是否启用

	private SESWssEPEmergencyPlanDTO planObj;


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

    public SESECDAlmAlarmRecordDTO getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(SESECDAlmAlarmRecordDTO alarmId) {
        this.alarmId = alarmId;
    }

    public Boolean getIsClose() {
        return isClose;
    }

    public void setIsClose(Boolean isClose) {
        this.isClose = isClose;
    }
    public SESWssEPEmergencyPlanDTO getPlanObj() {
        return planObj;
    }
    public void setPlanObj(SESWssEPEmergencyPlanDTO planObj) {
        this.planObj = planObj;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
