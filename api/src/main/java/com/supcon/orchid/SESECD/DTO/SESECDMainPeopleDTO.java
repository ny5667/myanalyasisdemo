package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABSetionmemberDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 责任人.
 */
public class SESECDMainPeopleDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESECDEmcActionDTO actionId;

	private StaffDTO owner;

	private SESEABSetionmemberDTO ownPerson;


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

    public SESECDEmcActionDTO getActionId() {
        return actionId;
    }
    public void setActionId(SESECDEmcActionDTO actionId) {
        this.actionId = actionId;
    }
    public StaffDTO getOwner() {
        return owner;
    }
    public void setOwner(StaffDTO owner) {
        this.owner = owner;
    }
    public SESEABSetionmemberDTO getOwnPerson() {
        return ownPerson;
    }
    public void setOwnPerson(SESEABSetionmemberDTO ownPerson) {
        this.ownPerson = ownPerson;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emcAction_MainPeople,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
