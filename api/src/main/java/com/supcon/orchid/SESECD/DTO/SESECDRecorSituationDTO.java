package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联态势.
 */
public class SESECDRecorSituationDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String addresss ; // 发生地点

	private SESECDAlmAlarmRecordDTO almAlarmRecord;
	private String deathPerson ; // 死亡人数
	private String describtion ; // 态势描述
	private Date occursTime ; // 发生时间
	private String point ; // 坐标
	private String reportPerson ; // 上报人
	private String situationType ; // 态势状态
	private String woundedPerson ; // 受伤人数


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


    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }
    public SESECDAlmAlarmRecordDTO getAlmAlarmRecord() {
        return almAlarmRecord;
    }
    public void setAlmAlarmRecord(SESECDAlmAlarmRecordDTO almAlarmRecord) {
        this.almAlarmRecord = almAlarmRecord;
    }

    public String getDeathPerson() {
        return deathPerson;
    }

    public void setDeathPerson(String deathPerson) {
        this.deathPerson = deathPerson;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOccursTime() {
        return occursTime;
    }

    public void setOccursTime(Date occursTime) {
        this.occursTime = occursTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getSituationType() {
        return situationType;
    }

    public void setSituationType(String situationType) {
        this.situationType = situationType;
    }

    public String getWoundedPerson() {
        return woundedPerson;
    }

    public void setWoundedPerson(String woundedPerson) {
        this.woundedPerson = woundedPerson;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
