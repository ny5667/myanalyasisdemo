package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 通信记录.
 */
public class SESECDCommunicationDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESECDAlmAlarmRecordDTO almAlarmRecord;
	private String messageContent ; // 消息内容
	private String sendingMethod ; // 发送方式
	private Date sendingTime ; // 发送时间


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

    public SESECDAlmAlarmRecordDTO getAlmAlarmRecord() {
        return almAlarmRecord;
    }
    public void setAlmAlarmRecord(SESECDAlmAlarmRecordDTO almAlarmRecord) {
        this.almAlarmRecord = almAlarmRecord;
    }

    @javax.persistence.Lob
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSendingMethod() {
        return sendingMethod;
    }

    public void setSendingMethod(String sendingMethod) {
        this.sendingMethod = sendingMethod;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_Communication,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
