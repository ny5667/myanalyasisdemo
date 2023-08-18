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
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 一张图操作记录.
 */
public class SESECDChangeLogDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private Long asscioateId ; // 业务关联id
	private String bisType ; // 业务类型
	private String content ; // 操作内容

	private SESECDAlmAlarmRecordDTO eventId;
	private Integer relateId ; // 关联业务id（废弃）


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


    public Long getAsscioateId() {
        return asscioateId;
    }

    public void setAsscioateId(Long asscioateId) {
        this.asscioateId = asscioateId;
    }

    public String getBisType() {
        return bisType;
    }

    public void setBisType(String bisType) {
        this.bisType = bisType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public SESECDAlmAlarmRecordDTO getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecordDTO eventId) {
        this.eventId = eventId;
    }

    public Integer getRelateId() {
        return relateId;
    }

    public void setRelateId(Integer relateId) {
        this.relateId = relateId;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_changeLog_ChangeLog,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
