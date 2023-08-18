package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急行动.
 */
public class SESECDEcdActionDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String actionAddr ; // 行动地点

	private SESECDEmcActionDTO actionId;
	private SystemCode actionState
;// 行动状态
	private Date beginTime ; // 实际开始时间
	private String description ; // 行动描述

	private SESECDAlmAlarmRecordDTO eventId;
	private String owners ; // 负责人


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


    public String getActionAddr() {
        return actionAddr;
    }

    public void setActionAddr(String actionAddr) {
        this.actionAddr = actionAddr;
    }
    public SESECDEmcActionDTO getActionId() {
        return actionId;
    }
    public void setActionId(SESECDEmcActionDTO actionId) {
        this.actionId = actionId;
    }

	public SystemCode getActionState() {
		return actionState;
	}

	public void setActionState(SystemCode actionState) {
		this.actionState = (SystemCode) actionState;
	}

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public SESECDAlmAlarmRecordDTO getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecordDTO eventId) {
        this.eventId = eventId;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
