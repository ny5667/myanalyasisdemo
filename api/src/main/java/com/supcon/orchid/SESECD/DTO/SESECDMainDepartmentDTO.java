package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABEabSetionDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 责任单位.
 */
public class SESECDMainDepartmentDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESECDEmcActionDTO actionId;

	private DepartmentDTO ownDepartment;

	private SESEABEabSetionDTO ownDepartmentN;


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
    public DepartmentDTO getOwnDepartment() {
        return ownDepartment;
    }
    public void setOwnDepartment(DepartmentDTO ownDepartment) {
        this.ownDepartment = ownDepartment;
    }
    public SESEABEabSetionDTO getOwnDepartmentN() {
        return ownDepartmentN;
    }
    public void setOwnDepartmentN(SESEABEabSetionDTO ownDepartmentN) {
        this.ownDepartmentN = ownDepartmentN;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emcAction_MainDepartment,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
