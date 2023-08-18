package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 推送人员.
 */
public class SESECDMesPersonDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESECDAlmAlarmRecordDTO alarmRecordId;

	private StaffDTO personId;


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

    public SESECDAlmAlarmRecordDTO getAlarmRecordId() {
        return alarmRecordId;
    }
    public void setAlarmRecordId(SESECDAlmAlarmRecordDTO alarmRecordId) {
        this.alarmRecordId = alarmRecordId;
    }
    public StaffDTO getPersonId() {
        return personId;
    }
    public void setPersonId(StaffDTO personId) {
        this.personId = personId;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_MesPerson,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
