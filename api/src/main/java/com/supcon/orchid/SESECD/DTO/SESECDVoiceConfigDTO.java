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
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 语音配置.
 */
public class SESECDVoiceConfigDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private String contentText ; // 自定义语言文字
	private Boolean customContnet = false; // 是否自定义语音文本内容
	private String systemVoiceIndex ; // 融合通讯音频文件序号
	private String voiceRemark ; // 备注

	private List<SESECDVoiceMemberDTO> voiceMemberList;

	public void setVoiceMemberList(List<SESECDVoiceMemberDTO> voiceMemberList){
		this.voiceMemberList = voiceMemberList;
	}
	public List<SESECDVoiceMemberDTO> getVoiceMemberList(){
		return voiceMemberList;
	}

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


    @javax.persistence.Lob
    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Boolean getCustomContnet() {
        return customContnet;
    }

    public void setCustomContnet(Boolean customContnet) {
        this.customContnet = customContnet;
    }

    public String getSystemVoiceIndex() {
        return systemVoiceIndex;
    }

    public void setSystemVoiceIndex(String systemVoiceIndex) {
        this.systemVoiceIndex = systemVoiceIndex;
    }

    public String getVoiceRemark() {
        return voiceRemark;
    }

    public void setVoiceRemark(String voiceRemark) {
        this.voiceRemark = voiceRemark;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}
	private String voiceConfigIdEabSectionIdDeleteIds;
	private String voiceConfigIdEabSectionIdAddIds;
	private String voiceConfigIdEabSectionIdmultiselectIDs;
	private String voiceConfigIdEabSectionIdmultiselectNames;

	public String getVoiceConfigIdEabSectionIdDeleteIds(){
		return this.voiceConfigIdEabSectionIdDeleteIds;
	}

	public void setVoiceConfigIdEabSectionIdDeleteIds(String deleteIds){
		this.voiceConfigIdEabSectionIdDeleteIds = deleteIds;
	}

	public String getVoiceConfigIdEabSectionIdmultiselectIDs(){
		return this.voiceConfigIdEabSectionIdmultiselectIDs;
	}

	public void setVoiceConfigIdEabSectionIdmultiselectIDs(String multiselectIDs){
		this.voiceConfigIdEabSectionIdmultiselectIDs = multiselectIDs;
	}

	public String getVoiceConfigIdEabSectionIdmultiselectNames(){
		return this.voiceConfigIdEabSectionIdmultiselectNames;
	}

	public void setVoiceConfigIdEabSectionIdmultiselectNames(String multiselectNames){
		this.voiceConfigIdEabSectionIdmultiselectNames = multiselectNames;
	}

	public String getVoiceConfigIdEabSectionIdAddIds(){
		return this.voiceConfigIdEabSectionIdAddIds;
	}

	public void setVoiceConfigIdEabSectionIdAddIds(String addIds){
		this.voiceConfigIdEabSectionIdAddIds = addIds;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
