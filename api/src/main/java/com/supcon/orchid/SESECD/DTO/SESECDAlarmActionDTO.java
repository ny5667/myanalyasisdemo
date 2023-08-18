package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESWssEP.DTO.SESWssEPEmergencyPlanDTO;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigIconLibraryDTO;
import com.supcon.orchid.SESWssEP.DTO.SESWssEPEmcPlanActionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABSetionmemberDTO;
import com.supcon.orchid.SESEAB.DTO.SESEABEabSetionDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联指令.
 */
public class SESECDAlarmActionDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String actionAddress ; // 行动地点
	private SystemCode actionCatogory
;// 行动分类
	private String actionDescription ; // 行动描述
	private String actionName ; // 行动名称

	private SESECDAlmAlarmRecordDTO alarmId;
	private SystemCode commomState
;// 指令状态

	private SESWssEPEmergencyPlanDTO emergencyPlan;

	private SESGISConfigIconLibraryDTO iconGis;

	private SESWssEPEmcPlanActionDTO instructions;
	private Boolean isMapPoint = false; // 是否已录入坐标
	private String mapPoint ; // 坐标

	private StaffDTO owner;

	private SESEABSetionmemberDTO ownPerson;
	private String sectionId ; // 通讯组Id

	private SESEABEabSetionDTO sectionObj;

	private List<SESECDActionOwnersDTO> actionOwnersList;

	public void setActionOwnersList(List<SESECDActionOwnersDTO> actionOwnersList){
		this.actionOwnersList = actionOwnersList;
	}
	public List<SESECDActionOwnersDTO> getActionOwnersList(){
		return actionOwnersList;
	}
	private List<SESECDAlarmActCameraDTO> alarmActCameraList;

	public void setAlarmActCameraList(List<SESECDAlarmActCameraDTO> alarmActCameraList){
		this.alarmActCameraList = alarmActCameraList;
	}
	public List<SESECDAlarmActCameraDTO> getAlarmActCameraList(){
		return alarmActCameraList;
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
    public SESECDAlmAlarmRecordDTO getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(SESECDAlmAlarmRecordDTO alarmId) {
        this.alarmId = alarmId;
    }

	public SystemCode getCommomState() {
		return commomState;
	}

	public void setCommomState(SystemCode commomState) {
		this.commomState = (SystemCode) commomState;
	}
    public SESWssEPEmergencyPlanDTO getEmergencyPlan() {
        return emergencyPlan;
    }
    public void setEmergencyPlan(SESWssEPEmergencyPlanDTO emergencyPlan) {
        this.emergencyPlan = emergencyPlan;
    }
    public SESGISConfigIconLibraryDTO getIconGis() {
        return iconGis;
    }
    public void setIconGis(SESGISConfigIconLibraryDTO iconGis) {
        this.iconGis = iconGis;
    }
    public SESWssEPEmcPlanActionDTO getInstructions() {
        return instructions;
    }
    public void setInstructions(SESWssEPEmcPlanActionDTO instructions) {
        this.instructions = instructions;
    }

    public Boolean getIsMapPoint() {
        return isMapPoint;
    }

    public void setIsMapPoint(Boolean isMapPoint) {
        this.isMapPoint = isMapPoint;
    }

    public String getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
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

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public SESEABEabSetionDTO getSectionObj() {
        return sectionObj;
    }
    public void setSectionObj(SESEABEabSetionDTO sectionObj) {
        this.sectionObj = sectionObj;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
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
	private String alarmActionIdVideoIdDeleteIds;
	private String alarmActionIdVideoIdAddIds;
	private String alarmActionIdVideoIdmultiselectIDs;
	private String alarmActionIdVideoIdmultiselectNames;

	public String getAlarmActionIdVideoIdDeleteIds(){
		return this.alarmActionIdVideoIdDeleteIds;
	}

	public void setAlarmActionIdVideoIdDeleteIds(String deleteIds){
		this.alarmActionIdVideoIdDeleteIds = deleteIds;
	}

	public String getAlarmActionIdVideoIdmultiselectIDs(){
		return this.alarmActionIdVideoIdmultiselectIDs;
	}

	public void setAlarmActionIdVideoIdmultiselectIDs(String multiselectIDs){
		this.alarmActionIdVideoIdmultiselectIDs = multiselectIDs;
	}

	public String getAlarmActionIdVideoIdmultiselectNames(){
		return this.alarmActionIdVideoIdmultiselectNames;
	}

	public void setAlarmActionIdVideoIdmultiselectNames(String multiselectNames){
		this.alarmActionIdVideoIdmultiselectNames = multiselectNames;
	}

	public String getAlarmActionIdVideoIdAddIds(){
		return this.alarmActionIdVideoIdAddIds;
	}

	public void setAlarmActionIdVideoIdAddIds(String addIds){
		this.alarmActionIdVideoIdAddIds = addIds;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
