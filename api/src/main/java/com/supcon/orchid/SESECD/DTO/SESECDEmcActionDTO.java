package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigIconLibraryDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急行动.
 */
public class SESECDEmcActionDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private String actionAddr ; // 行动地点
	private SystemCode actionCatogory
;// 行动分类
	private SystemCode actionState
;// 行动状态
	private Date actionTime ; // 行动时间
	private Date beginTime ; // 实际开始时间
	private Long commonId ; // 指令Id
	private String description ; // 行动描述

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


	private Date endTime ; // 实际结束时间

	private SESECDAlmAlarmRecordDTO eventId;
	private String icon;

	private Document iconDocument;

	private SESGISConfigIconLibraryDTO iconObjGis;
	private Boolean isPoint = false; // 是否已录入坐标
	private String ownPerson ; // 责任人
	private BigDecimal planTime ; // 预计耗时
	private String point ; // 坐标
	private String trackRecord ; // 跟踪记录

	private List<SESECDActVideoCameraDTO> actVideoCameraList;

	public void setActVideoCameraList(List<SESECDActVideoCameraDTO> actVideoCameraList){
		this.actVideoCameraList = actVideoCameraList;
	}
	public List<SESECDActVideoCameraDTO> getActVideoCameraList(){
		return actVideoCameraList;
	}
	private List<SESECDMainPeopleDTO> mainPeopleList;

	public void setMainPeopleList(List<SESECDMainPeopleDTO> mainPeopleList){
		this.mainPeopleList = mainPeopleList;
	}
	public List<SESECDMainPeopleDTO> getMainPeopleList(){
		return mainPeopleList;
	}
	private List<SESECDMainDepartmentDTO> mainDepartmentList;

	public void setMainDepartmentList(List<SESECDMainDepartmentDTO> mainDepartmentList){
		this.mainDepartmentList = mainDepartmentList;
	}
	public List<SESECDMainDepartmentDTO> getMainDepartmentList(){
		return mainDepartmentList;
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


    public String getActionAddr() {
        return actionAddr;
    }

    public void setActionAddr(String actionAddr) {
        this.actionAddr = actionAddr;
    }

	public SystemCode getActionCatogory() {
		return actionCatogory;
	}

	public void setActionCatogory(SystemCode actionCatogory) {
		this.actionCatogory = (SystemCode) actionCatogory;
	}

	public SystemCode getActionState() {
		return actionState;
	}

	public void setActionState(SystemCode actionState) {
		this.actionState = (SystemCode) actionState;
	}

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Long getCommonId() {
        return commonId;
    }

    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Boolean getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(Boolean isPoint) {
        this.isPoint = isPoint;
    }

    public String getOwnPerson() {
        return ownPerson;
    }

    public void setOwnPerson(String ownPerson) {
        this.ownPerson = ownPerson;
    }

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}
	private String emcActionCameraDeleteIds;
	private String emcActionCameraAddIds;
	private String emcActionCameramultiselectIDs;
	private String emcActionCameramultiselectNames;

	public String getEmcActionCameraDeleteIds(){
		return this.emcActionCameraDeleteIds;
	}

	public void setEmcActionCameraDeleteIds(String deleteIds){
		this.emcActionCameraDeleteIds = deleteIds;
	}

	public String getEmcActionCameramultiselectIDs(){
		return this.emcActionCameramultiselectIDs;
	}

	public void setEmcActionCameramultiselectIDs(String multiselectIDs){
		this.emcActionCameramultiselectIDs = multiselectIDs;
	}

	public String getEmcActionCameramultiselectNames(){
		return this.emcActionCameramultiselectNames;
	}

	public void setEmcActionCameramultiselectNames(String multiselectNames){
		this.emcActionCameramultiselectNames = multiselectNames;
	}

	public String getEmcActionCameraAddIds(){
		return this.emcActionCameraAddIds;
	}

	public void setEmcActionCameraAddIds(String addIds){
		this.emcActionCameraAddIds = addIds;
	}
	private String actionIdOwnDepartmentNDeleteIds;
	private String actionIdOwnDepartmentNAddIds;
	private String actionIdOwnDepartmentNmultiselectIDs;
	private String actionIdOwnDepartmentNmultiselectNames;

	public String getActionIdOwnDepartmentNDeleteIds(){
		return this.actionIdOwnDepartmentNDeleteIds;
	}

	public void setActionIdOwnDepartmentNDeleteIds(String deleteIds){
		this.actionIdOwnDepartmentNDeleteIds = deleteIds;
	}

	public String getActionIdOwnDepartmentNmultiselectIDs(){
		return this.actionIdOwnDepartmentNmultiselectIDs;
	}

	public void setActionIdOwnDepartmentNmultiselectIDs(String multiselectIDs){
		this.actionIdOwnDepartmentNmultiselectIDs = multiselectIDs;
	}

	public String getActionIdOwnDepartmentNmultiselectNames(){
		return this.actionIdOwnDepartmentNmultiselectNames;
	}

	public void setActionIdOwnDepartmentNmultiselectNames(String multiselectNames){
		this.actionIdOwnDepartmentNmultiselectNames = multiselectNames;
	}

	public String getActionIdOwnDepartmentNAddIds(){
		return this.actionIdOwnDepartmentNAddIds;
	}

	public void setActionIdOwnDepartmentNAddIds(String addIds){
		this.actionIdOwnDepartmentNAddIds = addIds;
	}
	private String actionIdOwnDepartmentDeleteIds;
	private String actionIdOwnDepartmentAddIds;
	private String actionIdOwnDepartmentmultiselectIDs;
	private String actionIdOwnDepartmentmultiselectNames;

	public String getActionIdOwnDepartmentDeleteIds(){
		return this.actionIdOwnDepartmentDeleteIds;
	}

	public void setActionIdOwnDepartmentDeleteIds(String deleteIds){
		this.actionIdOwnDepartmentDeleteIds = deleteIds;
	}

	public String getActionIdOwnDepartmentmultiselectIDs(){
		return this.actionIdOwnDepartmentmultiselectIDs;
	}

	public void setActionIdOwnDepartmentmultiselectIDs(String multiselectIDs){
		this.actionIdOwnDepartmentmultiselectIDs = multiselectIDs;
	}

	public String getActionIdOwnDepartmentmultiselectNames(){
		return this.actionIdOwnDepartmentmultiselectNames;
	}

	public void setActionIdOwnDepartmentmultiselectNames(String multiselectNames){
		this.actionIdOwnDepartmentmultiselectNames = multiselectNames;
	}

	public String getActionIdOwnDepartmentAddIds(){
		return this.actionIdOwnDepartmentAddIds;
	}

	public void setActionIdOwnDepartmentAddIds(String addIds){
		this.actionIdOwnDepartmentAddIds = addIds;
	}
	private String actionIdOwnPersonDeleteIds;
	private String actionIdOwnPersonAddIds;
	private String actionIdOwnPersonmultiselectIDs;
	private String actionIdOwnPersonmultiselectNames;

	public String getActionIdOwnPersonDeleteIds(){
		return this.actionIdOwnPersonDeleteIds;
	}

	public void setActionIdOwnPersonDeleteIds(String deleteIds){
		this.actionIdOwnPersonDeleteIds = deleteIds;
	}

	public String getActionIdOwnPersonmultiselectIDs(){
		return this.actionIdOwnPersonmultiselectIDs;
	}

	public void setActionIdOwnPersonmultiselectIDs(String multiselectIDs){
		this.actionIdOwnPersonmultiselectIDs = multiselectIDs;
	}

	public String getActionIdOwnPersonmultiselectNames(){
		return this.actionIdOwnPersonmultiselectNames;
	}

	public void setActionIdOwnPersonmultiselectNames(String multiselectNames){
		this.actionIdOwnPersonmultiselectNames = multiselectNames;
	}

	public String getActionIdOwnPersonAddIds(){
		return this.actionIdOwnPersonAddIds;
	}

	public void setActionIdOwnPersonAddIds(String addIds){
		this.actionIdOwnPersonAddIds = addIds;
	}
	private String actionIdOwnerDeleteIds;
	private String actionIdOwnerAddIds;
	private String actionIdOwnermultiselectIDs;
	private String actionIdOwnermultiselectNames;

	public String getActionIdOwnerDeleteIds(){
		return this.actionIdOwnerDeleteIds;
	}

	public void setActionIdOwnerDeleteIds(String deleteIds){
		this.actionIdOwnerDeleteIds = deleteIds;
	}

	public String getActionIdOwnermultiselectIDs(){
		return this.actionIdOwnermultiselectIDs;
	}

	public void setActionIdOwnermultiselectIDs(String multiselectIDs){
		this.actionIdOwnermultiselectIDs = multiselectIDs;
	}

	public String getActionIdOwnermultiselectNames(){
		return this.actionIdOwnermultiselectNames;
	}

	public void setActionIdOwnermultiselectNames(String multiselectNames){
		this.actionIdOwnermultiselectNames = multiselectNames;
	}

	public String getActionIdOwnerAddIds(){
		return this.actionIdOwnerAddIds;
	}

	public void setActionIdOwnerAddIds(String addIds){
		this.actionIdOwnerAddIds = addIds;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_emcAction_EmcAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
