package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.SESWssER.DTO.SESWssERAccidentClassDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.SESED.DTO.SESEDPlanDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.CompanyDTO;
import com.supcon.orchid.SESGISConfig.DTO.SESGISConfigFrequentplaceDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.CompanyDTO;
import com.supcon.orchid.foundation.ws.entities.CompanyDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 接警记录.
 */
public class SESECDAlmAlarmRecordDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private SystemCode accidentClass
;// 事故类别
	private Integer accidentId ; // 事故Id
	private SystemCode accidentLevel
;// 事故等级
	private String accidentName ; // 事件名称
	private SystemCode accidentReason
;// 事故原因

	private SESWssERAccidentClassDTO accidentType;
	private String alarmIds ; // 应急事件IDS
	private SystemCode alarmLevel
;// 报警等级

	private StaffDTO alarmPerson;
	private String alarmPhone ; // 报警人电话
	private String alarmSource ; // 来源
	private SystemCode alarmType
;// 报警类别

	private SESECDAlertRecordDTO alertRecordId;
	private Integer deathPerson ; // 死亡人数
	private String description ; // 事件描述

	private SESEDPlanDTO drillPlanId;

	private SESECDEmEventLeveLDTO emEventLeveL;

	private SESECDEmEventTypeDTO emEventType;

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


	private SystemCode eventDataSource
;// 事件数据来源
	private String eventNo ; // 事件编号
	private SystemCode eventSource
		= new SystemCode("SESECD_eventSource/001")
		;// 事件来源
	private SystemCode eventType
;// 事件类型
	private Date happenTime ; // 事发时间
	private String hei ; // 地图高度

	private DepartmentDTO hpmDepartment;

	private CompanyDTO hpnCompany;
	private Boolean isEmergency = false; // 进入应急处置
	private Boolean isIncident = false; // 是否应急事件
		private Boolean isOver =
			Boolean.valueOf("false")
		;// 是否结束
	private Boolean isSimulation = false; // 演练事件
	private Boolean isTurnAlarm = false; // 是否归档
	private String lat ; // 地图纬度

	private SESGISConfigFrequentplaceDTO locationIncident;
	private String lon ; // 地图经度
	private String mapPoint ; // 坐标

	private StaffDTO mesPerson;
	private Boolean openLabel = false; // 是否打开扩展信息
	private Date overTime ; // 结束时间
	private String point ; // 坐标点
	private String pointInfo ; // 坐标
	private String position ; // 发生位置
	private String process ; // 处理记录
	private SystemCode processState
;// 处理状态

	private StaffDTO receiver;
	private Date rectime ; // 接警时间
	private Long reportEventId ; // 上报事件接警id

	private CompanyDTO threeCompany;
	private String threeEPInfo ; // 三级公司应急预案

	private CompanyDTO twoCompany;
	private String twoEPInfo ; // 二级公司应急预案
	private Boolean voiceConfigFlag = false; // 语音配置标记

	private SESECDVoiceConfigDTO voiceConfigId;
	private Integer wounderPerson ; // 受伤人数

	private List<SESECDCommunicationDTO> communicationList;

	public void setCommunicationList(List<SESECDCommunicationDTO> communicationList){
		this.communicationList = communicationList;
	}
	public List<SESECDCommunicationDTO> getCommunicationList(){
		return communicationList;
	}
	private List<SESECDAlarmEnenetrelDTO> alarmEnenetrelList;

	public void setAlarmEnenetrelList(List<SESECDAlarmEnenetrelDTO> alarmEnenetrelList){
		this.alarmEnenetrelList = alarmEnenetrelList;
	}
	public List<SESECDAlarmEnenetrelDTO> getAlarmEnenetrelList(){
		return alarmEnenetrelList;
	}
	private List<SESECDRecorSituationDTO> recorSituationList;

	public void setRecorSituationList(List<SESECDRecorSituationDTO> recorSituationList){
		this.recorSituationList = recorSituationList;
	}
	public List<SESECDRecorSituationDTO> getRecorSituationList(){
		return recorSituationList;
	}
	private List<SESECDMesPersonDTO> mesPersonList;

	public void setMesPersonList(List<SESECDMesPersonDTO> mesPersonList){
		this.mesPersonList = mesPersonList;
	}
	public List<SESECDMesPersonDTO> getMesPersonList(){
		return mesPersonList;
	}
	private List<SESECDEmePlanObjDTO> emePlanObjList;

	public void setEmePlanObjList(List<SESECDEmePlanObjDTO> emePlanObjList){
		this.emePlanObjList = emePlanObjList;
	}
	public List<SESECDEmePlanObjDTO> getEmePlanObjList(){
		return emePlanObjList;
	}
	private List<SESECDAlarmActionDTO> alarmActionList;

	public void setAlarmActionList(List<SESECDAlarmActionDTO> alarmActionList){
		this.alarmActionList = alarmActionList;
	}
	public List<SESECDAlarmActionDTO> getAlarmActionList(){
		return alarmActionList;
	}
	private List<SESECDRecordActionDTO> recordActionList;

	public void setRecordActionList(List<SESECDRecordActionDTO> recordActionList){
		this.recordActionList = recordActionList;
	}
	public List<SESECDRecordActionDTO> getRecordActionList(){
		return recordActionList;
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


	public SystemCode getAccidentClass() {
		return accidentClass;
	}

	public void setAccidentClass(SystemCode accidentClass) {
		this.accidentClass = (SystemCode) accidentClass;
	}

    public Integer getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(Integer accidentId) {
        this.accidentId = accidentId;
    }

	public SystemCode getAccidentLevel() {
		return accidentLevel;
	}

	public void setAccidentLevel(SystemCode accidentLevel) {
		this.accidentLevel = (SystemCode) accidentLevel;
	}

    public String getAccidentName() {
        return accidentName;
    }

    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }

	public SystemCode getAccidentReason() {
		return accidentReason;
	}

	public void setAccidentReason(SystemCode accidentReason) {
		this.accidentReason = (SystemCode) accidentReason;
	}
    public SESWssERAccidentClassDTO getAccidentType() {
        return accidentType;
    }
    public void setAccidentType(SESWssERAccidentClassDTO accidentType) {
        this.accidentType = accidentType;
    }

    public String getAlarmIds() {
        return alarmIds;
    }

    public void setAlarmIds(String alarmIds) {
        this.alarmIds = alarmIds;
    }

	public SystemCode getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(SystemCode alarmLevel) {
		this.alarmLevel = (SystemCode) alarmLevel;
	}
    public StaffDTO getAlarmPerson() {
        return alarmPerson;
    }
    public void setAlarmPerson(StaffDTO alarmPerson) {
        this.alarmPerson = alarmPerson;
    }

    public String getAlarmPhone() {
        return alarmPhone;
    }

    public void setAlarmPhone(String alarmPhone) {
        this.alarmPhone = alarmPhone;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

	public SystemCode getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(SystemCode alarmType) {
		this.alarmType = (SystemCode) alarmType;
	}
    public SESECDAlertRecordDTO getAlertRecordId() {
        return alertRecordId;
    }
    public void setAlertRecordId(SESECDAlertRecordDTO alertRecordId) {
        this.alertRecordId = alertRecordId;
    }

    public Integer getDeathPerson() {
        return deathPerson;
    }

    public void setDeathPerson(Integer deathPerson) {
        this.deathPerson = deathPerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public SESEDPlanDTO getDrillPlanId() {
        return drillPlanId;
    }
    public void setDrillPlanId(SESEDPlanDTO drillPlanId) {
        this.drillPlanId = drillPlanId;
    }
    public SESECDEmEventLeveLDTO getEmEventLeveL() {
        return emEventLeveL;
    }
    public void setEmEventLeveL(SESECDEmEventLeveLDTO emEventLeveL) {
        this.emEventLeveL = emEventLeveL;
    }
    public SESECDEmEventTypeDTO getEmEventType() {
        return emEventType;
    }
    public void setEmEventType(SESECDEmEventTypeDTO emEventType) {
        this.emEventType = emEventType;
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


	public SystemCode getEventDataSource() {
		return eventDataSource;
	}

	public void setEventDataSource(SystemCode eventDataSource) {
		this.eventDataSource = (SystemCode) eventDataSource;
	}

    public String getEventNo() {
        return eventNo;
    }

    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }

	public SystemCode getEventSource() {
		return eventSource;
	}

	public void setEventSource(SystemCode eventSource) {
		this.eventSource = (SystemCode) eventSource;
	}

	public SystemCode getEventType() {
		return eventType;
	}

	public void setEventType(SystemCode eventType) {
		this.eventType = (SystemCode) eventType;
	}

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }

    public String getHei() {
        return hei;
    }

    public void setHei(String hei) {
        this.hei = hei;
    }
    public DepartmentDTO getHpmDepartment() {
        return hpmDepartment;
    }
    public void setHpmDepartment(DepartmentDTO hpmDepartment) {
        this.hpmDepartment = hpmDepartment;
    }
    public CompanyDTO getHpnCompany() {
        return hpnCompany;
    }
    public void setHpnCompany(CompanyDTO hpnCompany) {
        this.hpnCompany = hpnCompany;
    }

    public Boolean getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public Boolean getIsIncident() {
        return isIncident;
    }

    public void setIsIncident(Boolean isIncident) {
        this.isIncident = isIncident;
    }

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }

    public Boolean getIsSimulation() {
        return isSimulation;
    }

    public void setIsSimulation(Boolean isSimulation) {
        this.isSimulation = isSimulation;
    }

    public Boolean getIsTurnAlarm() {
        return isTurnAlarm;
    }

    public void setIsTurnAlarm(Boolean isTurnAlarm) {
        this.isTurnAlarm = isTurnAlarm;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
    public SESGISConfigFrequentplaceDTO getLocationIncident() {
        return locationIncident;
    }
    public void setLocationIncident(SESGISConfigFrequentplaceDTO locationIncident) {
        this.locationIncident = locationIncident;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }
    public StaffDTO getMesPerson() {
        return mesPerson;
    }
    public void setMesPerson(StaffDTO mesPerson) {
        this.mesPerson = mesPerson;
    }

    public Boolean getOpenLabel() {
        return openLabel;
    }

    public void setOpenLabel(Boolean openLabel) {
        this.openLabel = openLabel;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPointInfo() {
        return pointInfo;
    }

    public void setPointInfo(String pointInfo) {
        this.pointInfo = pointInfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

	public SystemCode getProcessState() {
		return processState;
	}

	public void setProcessState(SystemCode processState) {
		this.processState = (SystemCode) processState;
	}
    public StaffDTO getReceiver() {
        return receiver;
    }
    public void setReceiver(StaffDTO receiver) {
        this.receiver = receiver;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getRectime() {
        return rectime;
    }

    public void setRectime(Date rectime) {
        this.rectime = rectime;
    }

    public Long getReportEventId() {
        return reportEventId;
    }

    public void setReportEventId(Long reportEventId) {
        this.reportEventId = reportEventId;
    }
    public CompanyDTO getThreeCompany() {
        return threeCompany;
    }
    public void setThreeCompany(CompanyDTO threeCompany) {
        this.threeCompany = threeCompany;
    }

    public String getThreeEPInfo() {
        return threeEPInfo;
    }

    public void setThreeEPInfo(String threeEPInfo) {
        this.threeEPInfo = threeEPInfo;
    }
    public CompanyDTO getTwoCompany() {
        return twoCompany;
    }
    public void setTwoCompany(CompanyDTO twoCompany) {
        this.twoCompany = twoCompany;
    }

    public String getTwoEPInfo() {
        return twoEPInfo;
    }

    public void setTwoEPInfo(String twoEPInfo) {
        this.twoEPInfo = twoEPInfo;
    }

    public Boolean getVoiceConfigFlag() {
        return voiceConfigFlag;
    }

    public void setVoiceConfigFlag(Boolean voiceConfigFlag) {
        this.voiceConfigFlag = voiceConfigFlag;
    }
    public SESECDVoiceConfigDTO getVoiceConfigId() {
        return voiceConfigId;
    }
    public void setVoiceConfigId(SESECDVoiceConfigDTO voiceConfigId) {
        this.voiceConfigId = voiceConfigId;
    }

    public Integer getWounderPerson() {
        return wounderPerson;
    }

    public void setWounderPerson(Integer wounderPerson) {
        this.wounderPerson = wounderPerson;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}
	private String alarmIdIconGisDeleteIds;
	private String alarmIdIconGisAddIds;
	private String alarmIdIconGismultiselectIDs;
	private String alarmIdIconGismultiselectNames;

	public String getAlarmIdIconGisDeleteIds(){
		return this.alarmIdIconGisDeleteIds;
	}

	public void setAlarmIdIconGisDeleteIds(String deleteIds){
		this.alarmIdIconGisDeleteIds = deleteIds;
	}

	public String getAlarmIdIconGismultiselectIDs(){
		return this.alarmIdIconGismultiselectIDs;
	}

	public void setAlarmIdIconGismultiselectIDs(String multiselectIDs){
		this.alarmIdIconGismultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdIconGismultiselectNames(){
		return this.alarmIdIconGismultiselectNames;
	}

	public void setAlarmIdIconGismultiselectNames(String multiselectNames){
		this.alarmIdIconGismultiselectNames = multiselectNames;
	}

	public String getAlarmIdIconGisAddIds(){
		return this.alarmIdIconGisAddIds;
	}

	public void setAlarmIdIconGisAddIds(String addIds){
		this.alarmIdIconGisAddIds = addIds;
	}
	private String alarmIdOwnPersonDeleteIds;
	private String alarmIdOwnPersonAddIds;
	private String alarmIdOwnPersonmultiselectIDs;
	private String alarmIdOwnPersonmultiselectNames;

	public String getAlarmIdOwnPersonDeleteIds(){
		return this.alarmIdOwnPersonDeleteIds;
	}

	public void setAlarmIdOwnPersonDeleteIds(String deleteIds){
		this.alarmIdOwnPersonDeleteIds = deleteIds;
	}

	public String getAlarmIdOwnPersonmultiselectIDs(){
		return this.alarmIdOwnPersonmultiselectIDs;
	}

	public void setAlarmIdOwnPersonmultiselectIDs(String multiselectIDs){
		this.alarmIdOwnPersonmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdOwnPersonmultiselectNames(){
		return this.alarmIdOwnPersonmultiselectNames;
	}

	public void setAlarmIdOwnPersonmultiselectNames(String multiselectNames){
		this.alarmIdOwnPersonmultiselectNames = multiselectNames;
	}

	public String getAlarmIdOwnPersonAddIds(){
		return this.alarmIdOwnPersonAddIds;
	}

	public void setAlarmIdOwnPersonAddIds(String addIds){
		this.alarmIdOwnPersonAddIds = addIds;
	}
	private String alarmIdOwnerDeleteIds;
	private String alarmIdOwnerAddIds;
	private String alarmIdOwnermultiselectIDs;
	private String alarmIdOwnermultiselectNames;

	public String getAlarmIdOwnerDeleteIds(){
		return this.alarmIdOwnerDeleteIds;
	}

	public void setAlarmIdOwnerDeleteIds(String deleteIds){
		this.alarmIdOwnerDeleteIds = deleteIds;
	}

	public String getAlarmIdOwnermultiselectIDs(){
		return this.alarmIdOwnermultiselectIDs;
	}

	public void setAlarmIdOwnermultiselectIDs(String multiselectIDs){
		this.alarmIdOwnermultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdOwnermultiselectNames(){
		return this.alarmIdOwnermultiselectNames;
	}

	public void setAlarmIdOwnermultiselectNames(String multiselectNames){
		this.alarmIdOwnermultiselectNames = multiselectNames;
	}

	public String getAlarmIdOwnerAddIds(){
		return this.alarmIdOwnerAddIds;
	}

	public void setAlarmIdOwnerAddIds(String addIds){
		this.alarmIdOwnerAddIds = addIds;
	}
	private String alarmIdEmergencyPlanDeleteIds;
	private String alarmIdEmergencyPlanAddIds;
	private String alarmIdEmergencyPlanmultiselectIDs;
	private String alarmIdEmergencyPlanmultiselectNames;

	public String getAlarmIdEmergencyPlanDeleteIds(){
		return this.alarmIdEmergencyPlanDeleteIds;
	}

	public void setAlarmIdEmergencyPlanDeleteIds(String deleteIds){
		this.alarmIdEmergencyPlanDeleteIds = deleteIds;
	}

	public String getAlarmIdEmergencyPlanmultiselectIDs(){
		return this.alarmIdEmergencyPlanmultiselectIDs;
	}

	public void setAlarmIdEmergencyPlanmultiselectIDs(String multiselectIDs){
		this.alarmIdEmergencyPlanmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdEmergencyPlanmultiselectNames(){
		return this.alarmIdEmergencyPlanmultiselectNames;
	}

	public void setAlarmIdEmergencyPlanmultiselectNames(String multiselectNames){
		this.alarmIdEmergencyPlanmultiselectNames = multiselectNames;
	}

	public String getAlarmIdEmergencyPlanAddIds(){
		return this.alarmIdEmergencyPlanAddIds;
	}

	public void setAlarmIdEmergencyPlanAddIds(String addIds){
		this.alarmIdEmergencyPlanAddIds = addIds;
	}
	private String alarmIdIdDeleteIds;
	private String alarmIdIdAddIds;
	private String alarmIdIdmultiselectIDs;
	private String alarmIdIdmultiselectNames;

	public String getAlarmIdIdDeleteIds(){
		return this.alarmIdIdDeleteIds;
	}

	public void setAlarmIdIdDeleteIds(String deleteIds){
		this.alarmIdIdDeleteIds = deleteIds;
	}

	public String getAlarmIdIdmultiselectIDs(){
		return this.alarmIdIdmultiselectIDs;
	}

	public void setAlarmIdIdmultiselectIDs(String multiselectIDs){
		this.alarmIdIdmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdIdmultiselectNames(){
		return this.alarmIdIdmultiselectNames;
	}

	public void setAlarmIdIdmultiselectNames(String multiselectNames){
		this.alarmIdIdmultiselectNames = multiselectNames;
	}

	public String getAlarmIdIdAddIds(){
		return this.alarmIdIdAddIds;
	}

	public void setAlarmIdIdAddIds(String addIds){
		this.alarmIdIdAddIds = addIds;
	}
	private String alarmIdSectionObjDeleteIds;
	private String alarmIdSectionObjAddIds;
	private String alarmIdSectionObjmultiselectIDs;
	private String alarmIdSectionObjmultiselectNames;

	public String getAlarmIdSectionObjDeleteIds(){
		return this.alarmIdSectionObjDeleteIds;
	}

	public void setAlarmIdSectionObjDeleteIds(String deleteIds){
		this.alarmIdSectionObjDeleteIds = deleteIds;
	}

	public String getAlarmIdSectionObjmultiselectIDs(){
		return this.alarmIdSectionObjmultiselectIDs;
	}

	public void setAlarmIdSectionObjmultiselectIDs(String multiselectIDs){
		this.alarmIdSectionObjmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdSectionObjmultiselectNames(){
		return this.alarmIdSectionObjmultiselectNames;
	}

	public void setAlarmIdSectionObjmultiselectNames(String multiselectNames){
		this.alarmIdSectionObjmultiselectNames = multiselectNames;
	}

	public String getAlarmIdSectionObjAddIds(){
		return this.alarmIdSectionObjAddIds;
	}

	public void setAlarmIdSectionObjAddIds(String addIds){
		this.alarmIdSectionObjAddIds = addIds;
	}
	private String alarmIdInstructionsDeleteIds;
	private String alarmIdInstructionsAddIds;
	private String alarmIdInstructionsmultiselectIDs;
	private String alarmIdInstructionsmultiselectNames;

	public String getAlarmIdInstructionsDeleteIds(){
		return this.alarmIdInstructionsDeleteIds;
	}

	public void setAlarmIdInstructionsDeleteIds(String deleteIds){
		this.alarmIdInstructionsDeleteIds = deleteIds;
	}

	public String getAlarmIdInstructionsmultiselectIDs(){
		return this.alarmIdInstructionsmultiselectIDs;
	}

	public void setAlarmIdInstructionsmultiselectIDs(String multiselectIDs){
		this.alarmIdInstructionsmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdInstructionsmultiselectNames(){
		return this.alarmIdInstructionsmultiselectNames;
	}

	public void setAlarmIdInstructionsmultiselectNames(String multiselectNames){
		this.alarmIdInstructionsmultiselectNames = multiselectNames;
	}

	public String getAlarmIdInstructionsAddIds(){
		return this.alarmIdInstructionsAddIds;
	}

	public void setAlarmIdInstructionsAddIds(String addIds){
		this.alarmIdInstructionsAddIds = addIds;
	}
	private String alarmIdEnenetrelDeleteIds;
	private String alarmIdEnenetrelAddIds;
	private String alarmIdEnenetrelmultiselectIDs;
	private String alarmIdEnenetrelmultiselectNames;

	public String getAlarmIdEnenetrelDeleteIds(){
		return this.alarmIdEnenetrelDeleteIds;
	}

	public void setAlarmIdEnenetrelDeleteIds(String deleteIds){
		this.alarmIdEnenetrelDeleteIds = deleteIds;
	}

	public String getAlarmIdEnenetrelmultiselectIDs(){
		return this.alarmIdEnenetrelmultiselectIDs;
	}

	public void setAlarmIdEnenetrelmultiselectIDs(String multiselectIDs){
		this.alarmIdEnenetrelmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdEnenetrelmultiselectNames(){
		return this.alarmIdEnenetrelmultiselectNames;
	}

	public void setAlarmIdEnenetrelmultiselectNames(String multiselectNames){
		this.alarmIdEnenetrelmultiselectNames = multiselectNames;
	}

	public String getAlarmIdEnenetrelAddIds(){
		return this.alarmIdEnenetrelAddIds;
	}

	public void setAlarmIdEnenetrelAddIds(String addIds){
		this.alarmIdEnenetrelAddIds = addIds;
	}
	private String alarmIdPlanObjDeleteIds;
	private String alarmIdPlanObjAddIds;
	private String alarmIdPlanObjmultiselectIDs;
	private String alarmIdPlanObjmultiselectNames;

	public String getAlarmIdPlanObjDeleteIds(){
		return this.alarmIdPlanObjDeleteIds;
	}

	public void setAlarmIdPlanObjDeleteIds(String deleteIds){
		this.alarmIdPlanObjDeleteIds = deleteIds;
	}

	public String getAlarmIdPlanObjmultiselectIDs(){
		return this.alarmIdPlanObjmultiselectIDs;
	}

	public void setAlarmIdPlanObjmultiselectIDs(String multiselectIDs){
		this.alarmIdPlanObjmultiselectIDs = multiselectIDs;
	}

	public String getAlarmIdPlanObjmultiselectNames(){
		return this.alarmIdPlanObjmultiselectNames;
	}

	public void setAlarmIdPlanObjmultiselectNames(String multiselectNames){
		this.alarmIdPlanObjmultiselectNames = multiselectNames;
	}

	public String getAlarmIdPlanObjAddIds(){
		return this.alarmIdPlanObjAddIds;
	}

	public void setAlarmIdPlanObjAddIds(String addIds){
		this.alarmIdPlanObjAddIds = addIds;
	}
	private String alarmRecordIdPersonIdDeleteIds;
	private String alarmRecordIdPersonIdAddIds;
	private String alarmRecordIdPersonIdmultiselectIDs;
	private String alarmRecordIdPersonIdmultiselectNames;

	public String getAlarmRecordIdPersonIdDeleteIds(){
		return this.alarmRecordIdPersonIdDeleteIds;
	}

	public void setAlarmRecordIdPersonIdDeleteIds(String deleteIds){
		this.alarmRecordIdPersonIdDeleteIds = deleteIds;
	}

	public String getAlarmRecordIdPersonIdmultiselectIDs(){
		return this.alarmRecordIdPersonIdmultiselectIDs;
	}

	public void setAlarmRecordIdPersonIdmultiselectIDs(String multiselectIDs){
		this.alarmRecordIdPersonIdmultiselectIDs = multiselectIDs;
	}

	public String getAlarmRecordIdPersonIdmultiselectNames(){
		return this.alarmRecordIdPersonIdmultiselectNames;
	}

	public void setAlarmRecordIdPersonIdmultiselectNames(String multiselectNames){
		this.alarmRecordIdPersonIdmultiselectNames = multiselectNames;
	}

	public String getAlarmRecordIdPersonIdAddIds(){
		return this.alarmRecordIdPersonIdAddIds;
	}

	public void setAlarmRecordIdPersonIdAddIds(String addIds){
		this.alarmRecordIdPersonIdAddIds = addIds;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
