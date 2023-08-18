package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联行动.
 */
public class SESECDRecordActionDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String actionAddr ; // 行动地点
	private String actionCatogory ; // 行动分类
	private String actionState ; // 行动状态
	private Date actionTime ; // 行动时间

	private SESECDAlmAlarmRecordDTO almAlarmRecord;
	private String description ; // 行动描述
	private String ownDepartment ; // 责任单位
	private String ownPerson ; // 责任人
	private String point ; // 坐标
	private String trackRecord ; // 跟踪记录


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

    public String getActionCatogory() {
        return actionCatogory;
    }

    public void setActionCatogory(String actionCatogory) {
        this.actionCatogory = actionCatogory;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
    public SESECDAlmAlarmRecordDTO getAlmAlarmRecord() {
        return almAlarmRecord;
    }
    public void setAlmAlarmRecord(SESECDAlmAlarmRecordDTO almAlarmRecord) {
        this.almAlarmRecord = almAlarmRecord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnDepartment() {
        return ownDepartment;
    }

    public void setOwnDepartment(String ownDepartment) {
        this.ownDepartment = ownDepartment;
    }

    public String getOwnPerson() {
        return ownPerson;
    }

    public void setOwnPerson(String ownPerson) {
        this.ownPerson = ownPerson;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
