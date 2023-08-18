package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigFrequentplaceDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigIconLibraryDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急态势.
 */
public class SESECDEmcSituationDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;

	private SESGISConfigFrequentplaceDTO commonAddress;
	private Integer deathPerson ; // 死亡人数
	private String describtion ; // 态势描述

	private String enclosureAttachementInfo;

	private Document enclosureDocument;

	private String enclosureFileAddPaths; //新添加附件的路径
	private String enclosureFileDeleteIds;//已删除附件ID
	private String enclosureMultiFileIds;//已添加附件ID
	private String enclosureMultiFileNames;//已添加附件name

	public String getEnclosureFileAddPaths(){
		return this.enclosureFileAddPaths;
	}

	public void setEnclosureFileAddPaths(String enclosureFileAddPaths){
		this.enclosureFileAddPaths = enclosureFileAddPaths;
	}

	public String getEnclosureFileDeleteIds(){
		return this.enclosureFileDeleteIds;
	}

	public void setEnclosureFileDeleteIds(String enclosureFileDeleteIds){
		this.enclosureFileDeleteIds = enclosureFileDeleteIds;
	}

	public String getEnclosureMultiFileIds(){
		return this.enclosureMultiFileIds;
	}

	public void setEnclosureMultiFileIds(String enclosureMultiFileIds){
		this.enclosureMultiFileIds = enclosureMultiFileIds;
	}

	public String getEnclosureMultiFileNames(){
		return this.enclosureMultiFileNames;
	}

	public void setEnclosureMultiFileNames(String enclosureMultiFileNames){
		this.enclosureMultiFileNames = enclosureMultiFileNames;
	}



	private SESECDAlmAlarmRecordDTO eventId;
	private String icon;

	private Document iconDocument;

	private SESGISConfigIconLibraryDTO iconObjGis;
	private Date occursTime ; // 发生时间
	private String point ; // 坐标
	private String position ; // 事发地点

	private StaffDTO reportPerson;
	private SystemCode situationType
;// 态势状态
	private SystemCode source
		= new SystemCode("SESECD_source/001")
		;// 来源
	private String voicePath ; // 语音存放路径
	private Integer woundedPerson ; // 受伤人数


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

    public SESGISConfigFrequentplaceDTO getCommonAddress() {
        return commonAddress;
    }
    public void setCommonAddress(SESGISConfigFrequentplaceDTO commonAddress) {
        this.commonAddress = commonAddress;
    }

    public Integer getDeathPerson() {
        return deathPerson;
    }

    public void setDeathPerson(Integer deathPerson) {
        this.deathPerson = deathPerson;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

	public void setEnclosureAttachementInfo(String enclosureAttachementInfo){
		this.enclosureAttachementInfo = enclosureAttachementInfo;
	}

	public String getEnclosureAttachementInfo(){
		return this.enclosureAttachementInfo;
	}

	public void setEnclosureDocument(Document enclosureDocument){
		this.enclosureDocument = enclosureDocument;
	}

	public Document getEnclosureDocument(){
		return this.enclosureDocument;
	}

    public SESECDAlmAlarmRecordDTO getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecordDTO eventId) {
        this.eventId = eventId;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Document getIconDocument() {
		return iconDocument;
	}

	public void setIconDocument(Document iconDocument) {
		this.iconDocument = iconDocument;
	}

    public SESGISConfigIconLibraryDTO getIconObjGis() {
        return iconObjGis;
    }
    public void setIconObjGis(SESGISConfigIconLibraryDTO iconObjGis) {
        this.iconObjGis = iconObjGis;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOccursTime() {
        return occursTime;
    }

    public void setOccursTime(Date occursTime) {
        this.occursTime = occursTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public StaffDTO getReportPerson() {
        return reportPerson;
    }
    public void setReportPerson(StaffDTO reportPerson) {
        this.reportPerson = reportPerson;
    }

	public SystemCode getSituationType() {
		return situationType;
	}

	public void setSituationType(SystemCode situationType) {
		this.situationType = (SystemCode) situationType;
	}

	public SystemCode getSource() {
		return source;
	}

	public void setSource(SystemCode source) {
		this.source = (SystemCode) source;
	}

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public Integer getWoundedPerson() {
        return woundedPerson;
    }

    public void setWoundedPerson(Integer woundedPerson) {
        this.woundedPerson = woundedPerson;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}

	private Integer bigintparama;

    public Integer getBigintparama() {
        return bigintparama;
    }

    public void setBigintparama(Integer bigintparama) {
        this.bigintparama = bigintparama;
    }
	private Integer bigintparamb;

    public Integer getBigintparamb() {
        return bigintparamb;
    }

    public void setBigintparamb(Integer bigintparamb) {
        this.bigintparamb = bigintparamb;
    }
	private String charparama;

    public String getCharparama() {
        return charparama;
    }

    public void setCharparama(String charparama) {
        this.charparama = charparama;
    }
	private String charparamb;

    public String getCharparamb() {
        return charparamb;
    }

    public void setCharparamb(String charparamb) {
        this.charparamb = charparamb;
    }
	private String charparamc;

    public String getCharparamc() {
        return charparamc;
    }

    public void setCharparamc(String charparamc) {
        this.charparamc = charparamc;
    }
	private String charparamd;

    public String getCharparamd() {
        return charparamd;
    }

    public void setCharparamd(String charparamd) {
        this.charparamd = charparamd;
    }
	private Date dateparama;

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparama() {
        return dateparama;
    }

    public void setDateparama(Date dateparama) {
        this.dateparama = dateparama;
    }
	private Date dateparamb;

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparamb() {
        return dateparamb;
    }

    public void setDateparamb(Date dateparamb) {
        this.dateparamb = dateparamb;
    }
	private BigDecimal numberparama;

    public BigDecimal getNumberparama() {
        return numberparama;
    }

    public void setNumberparama(BigDecimal numberparama) {
        this.numberparama = numberparama;
    }
	private BigDecimal numberparamb;

    public BigDecimal getNumberparamb() {
        return numberparamb;
    }

    public void setNumberparamb(BigDecimal numberparamb) {
        this.numberparamb = numberparamb;
    }
	private Long objparama;

	public Long getObjparama() {
		return objparama;
	}

	public void setObjparama(Long objparama) {
		this.objparama = objparama;
	}

	private String objparamaMainDisplay;

	public String getObjparamaMainDisplay() {
		return objparamaMainDisplay;
	}

	public void setObjparamaMainDisplay(String objparamaMainDisplay) {
		this.objparamaMainDisplay = objparamaMainDisplay;
	}
	private Long objparamb;

	public Long getObjparamb() {
		return objparamb;
	}

	public void setObjparamb(Long objparamb) {
		this.objparamb = objparamb;
	}

	private String objparambMainDisplay;

	public String getObjparambMainDisplay() {
		return objparambMainDisplay;
	}

	public void setObjparambMainDisplay(String objparambMainDisplay) {
		this.objparambMainDisplay = objparambMainDisplay;
	}
	private String scparama;

	public String getScparama() {
		return scparama;
	}

	public void setScparama(String scparama) {
		this.scparama = scparama;
	}

	private String scparamaMainDisplay;

	public String getScparamaMainDisplay() {
		return scparamaMainDisplay;
	}

	public void setScparamaMainDisplay(String scparamaMainDisplay) {
		this.scparamaMainDisplay = scparamaMainDisplay;
	}
	private String scparamb;

	public String getScparamb() {
		return scparamb;
	}

	public void setScparamb(String scparamb) {
		this.scparamb = scparamb;
	}

	private String scparambMainDisplay;

	public String getScparambMainDisplay() {
		return scparambMainDisplay;
	}

	public void setScparambMainDisplay(String scparambMainDisplay) {
		this.scparambMainDisplay = scparambMainDisplay;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
