package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESWssEP.DTO.SESWssEPEmergencyPlanDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 指令.
 */
public class SESECDEcdCommomDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String actionAddress ; // 行动地点
	private SystemCode actionCatogory
;// 行动分类
	private String actionDescription ; // 行动描述
	private String actionName ; // 行动名称

	private SESECDAlarmActionDTO commomId;
	private SystemCode commomState
;// 状态

	private SESWssEPEmergencyPlanDTO emcPlanId;

	private SESECDAlmAlarmRecordDTO eventId;
	private String instLayer ; // 坐标
	private Boolean isMapPoint = false; // 是否已录入坐标
	private String isMapPointTxt ; // 是否已录入坐标
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


    public String getActionAddress() {
        return actionAddress;
    }

    public void setActionAddress(String actionAddress) {
        this.actionAddress = actionAddress;
    }

	public SystemCode getActionCatogory() {
		return actionCatogory;
	}

	public void setActionCatogory(SystemCode actionCatogory) {
		this.actionCatogory = (SystemCode) actionCatogory;
	}

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public SESECDAlarmActionDTO getCommomId() {
        return commomId;
    }
    public void setCommomId(SESECDAlarmActionDTO commomId) {
        this.commomId = commomId;
    }

	public SystemCode getCommomState() {
		return commomState;
	}

	public void setCommomState(SystemCode commomState) {
		this.commomState = (SystemCode) commomState;
	}
    public SESWssEPEmergencyPlanDTO getEmcPlanId() {
        return emcPlanId;
    }
    public void setEmcPlanId(SESWssEPEmergencyPlanDTO emcPlanId) {
        this.emcPlanId = emcPlanId;
    }
    public SESECDAlmAlarmRecordDTO getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecordDTO eventId) {
        this.eventId = eventId;
    }

    public String getInstLayer() {
        return instLayer;
    }

    public void setInstLayer(String instLayer) {
        this.instLayer = instLayer;
    }

    public Boolean getIsMapPoint() {
        return isMapPoint;
    }

    public void setIsMapPoint(Boolean isMapPoint) {
        this.isMapPoint = isMapPoint;
    }

    public String getIsMapPointTxt() {
        return isMapPointTxt;
    }

    public void setIsMapPointTxt(String isMapPointTxt) {
        this.isMapPointTxt = isMapPointTxt;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
