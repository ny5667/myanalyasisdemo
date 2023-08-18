package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESWssER.DTO.SESWssERAccidentClassDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 事故案例.
 */
public class SESECDAccidentDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String accidentCause ; // 事故原因

	private SESWssERAccidentClassDTO accidentType;

	private String attachmentAttachementInfo;

	private Document attachmentDocument;

	private String attachmentFileAddPaths; //新添加附件的路径
	private String attachmentFileDeleteIds;//已删除附件ID
	private String attachmentMultiFileIds;//已添加附件ID
	private String attachmentMultiFileNames;//已添加附件name

	public String getAttachmentFileAddPaths(){
		return this.attachmentFileAddPaths;
	}

	public void setAttachmentFileAddPaths(String attachmentFileAddPaths){
		this.attachmentFileAddPaths = attachmentFileAddPaths;
	}

	public String getAttachmentFileDeleteIds(){
		return this.attachmentFileDeleteIds;
	}

	public void setAttachmentFileDeleteIds(String attachmentFileDeleteIds){
		this.attachmentFileDeleteIds = attachmentFileDeleteIds;
	}

	public String getAttachmentMultiFileIds(){
		return this.attachmentMultiFileIds;
	}

	public void setAttachmentMultiFileIds(String attachmentMultiFileIds){
		this.attachmentMultiFileIds = attachmentMultiFileIds;
	}

	public String getAttachmentMultiFileNames(){
		return this.attachmentMultiFileNames;
	}

	public void setAttachmentMultiFileNames(String attachmentMultiFileNames){
		this.attachmentMultiFileNames = attachmentMultiFileNames;
	}


	private String casualtySituation ; // 伤亡情况
	private String emergencyResponseProcess ; // 应急处置过程
	private String occurrenceProcess ; // 发生过程
	private String preventionAdvice ; // 防范建议


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


    public String getAccidentCause() {
        return accidentCause;
    }

    public void setAccidentCause(String accidentCause) {
        this.accidentCause = accidentCause;
    }
    public SESWssERAccidentClassDTO getAccidentType() {
        return accidentType;
    }
    public void setAccidentType(SESWssERAccidentClassDTO accidentType) {
        this.accidentType = accidentType;
    }

	public void setAttachmentAttachementInfo(String attachmentAttachementInfo){
		this.attachmentAttachementInfo = attachmentAttachementInfo;
	}

	public String getAttachmentAttachementInfo(){
		return this.attachmentAttachementInfo;
	}

	public void setAttachmentDocument(Document attachmentDocument){
		this.attachmentDocument = attachmentDocument;
	}

	public Document getAttachmentDocument(){
		return this.attachmentDocument;
	}


    public String getCasualtySituation() {
        return casualtySituation;
    }

    public void setCasualtySituation(String casualtySituation) {
        this.casualtySituation = casualtySituation;
    }

    public String getEmergencyResponseProcess() {
        return emergencyResponseProcess;
    }

    public void setEmergencyResponseProcess(String emergencyResponseProcess) {
        this.emergencyResponseProcess = emergencyResponseProcess;
    }

    public String getOccurrenceProcess() {
        return occurrenceProcess;
    }

    public void setOccurrenceProcess(String occurrenceProcess) {
        this.occurrenceProcess = occurrenceProcess;
    }

    public String getPreventionAdvice() {
        return preventionAdvice;
    }

    public void setPreventionAdvice(String preventionAdvice) {
        this.preventionAdvice = preventionAdvice;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
