package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急态势.
 */
public class SESECDEcdStatiusDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String describtion ; // 态势描述

	private SESECDAlmAlarmRecordDTO eventId;
	private Date occursTime ; // 发生时间
	private String position ; // 发生地点

	private StaffDTO reportPerson;

	private SESECDEmcSituationDTO stateId;


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


    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }
    public SESECDAlmAlarmRecordDTO getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecordDTO eventId) {
        this.eventId = eventId;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOccursTime() {
        return occursTime;
    }

    public void setOccursTime(Date occursTime) {
        this.occursTime = occursTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public StaffDTO getReportPerson() {
        return reportPerson;
    }
    public void setReportPerson(StaffDTO reportPerson) {
        this.reportPerson = reportPerson;
    }
    public SESECDEmcSituationDTO getStateId() {
        return stateId;
    }
    public void setStateId(SESECDEmcSituationDTO stateId) {
        this.stateId = stateId;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
