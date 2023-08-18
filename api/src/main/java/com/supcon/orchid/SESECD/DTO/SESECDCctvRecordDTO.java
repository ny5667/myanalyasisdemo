package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 录像回放.
 */
public class SESECDCctvRecordDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String duration ; // 时长
	private String hls ; // 地址
	private String important ; // 重要性
	private String name ; // 通道号

	private SESECDAlmAlarmRecordDTO recordId;
	private String startAt ; // 开始时间


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


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public SESECDAlmAlarmRecordDTO getRecordId() {
        return recordId;
    }
    public void setRecordId(SESECDAlmAlarmRecordDTO recordId) {
        this.recordId = recordId;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
