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
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警记录.
 */
public class SESECDAlertRecordDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private SystemCode alarmStaus
;// 报警状态
	private Date alarmTime ; // 报警时间
	private String alarmValue ; // 报警内容
	private String code ; // 报警编码
	private BigDecimal lowerLimit ; // 下限
	private String tagAddress ; // 位置
	private String tagName ; // 报警装置
	private SystemCode tagType
;// 报警来源
	private BigDecimal upperLimit ; // 上限
	private String warnId ; // 告警编号


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


	public SystemCode getAlarmStaus() {
		return alarmStaus;
	}

	public void setAlarmStaus(SystemCode alarmStaus) {
		this.alarmStaus = (SystemCode) alarmStaus;
	}

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(String alarmValue) {
        this.alarmValue = alarmValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getTagAddress() {
        return tagAddress;
    }

    public void setTagAddress(String tagAddress) {
        this.tagAddress = tagAddress;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

	public SystemCode getTagType() {
		return tagType;
	}

	public void setTagType(SystemCode tagType) {
		this.tagType = (SystemCode) tagType;
	}

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getWarnId() {
        return warnId;
    }

    public void setWarnId(String warnId) {
        this.warnId = warnId;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
