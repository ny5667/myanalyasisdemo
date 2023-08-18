package com.supcon.orchid.SESECD.entities;

import javax.persistence.Table;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import org.hibernate.annotations.Index;
import javax.persistence.Column;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Lob;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.supcon.orchid.annotation.BAPEntity;
import javax.persistence.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Where;
import com.supcon.orchid.audit.annotation.DataAudit;
import com.supcon.orchid.orm.entities.IAuditEntity;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.ws.adapter.DateAdapter;
//import com.supcon.orchid.foundation.adapter.BAPAdapter;
import com.supcon.orchid.foundation.adapter.DocumentAdaptor;
import com.supcon.orchid.orm.entities.ICompany;
import com.supcon.orchid.orm.entities.ICId;
import com.supcon.orchid.annotation.BAPCustomComponent;
import com.supcon.orchid.annotation.BAPIsMainDisplay;
import com.supcon.orchid.annotation.BAPIsMneCode;
import com.supcon.orchid.annotation.BAPModelCode;
import com.supcon.orchid.annotation.BAPPicture;
import com.supcon.orchid.annotation.BAPSystemCodeMultable;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.orm.entities.jaxb.BAPFoundationAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.alibaba.fastjson.annotation.JSONField;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;		
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;		
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;		
import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联指令.
 */
@javax.persistence.Entity(name=SESECDAlarmAction.JPA_NAME)
@Table(name = SESECDAlarmAction.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_AlarmAction")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDAlarmActionConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_AlarmAction", modelName = "SESECD.alarmRecord.AlarmAction", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDAlarmAction extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_AlarmAction";
	public static final String DOC_TYPE = "SESECD_alarmRecord_AlarmAction";
	public static final String TABLE_NAME = "ses_ecd_alarm_actions";
	public static final String JPA_NAME = "SESECDAlarmAction";



	private String actionAddress ; // 行动地点
	private SystemCode actionCatogory
;// 行动分类



	private String actionDescription ; // 行动描述



	private String actionName ; // 行动名称
	private SESECDAlmAlarmRecord alarmId;
	private SystemCode commomState
;// 指令状态
	private SESWssEPEmergencyPlan emergencyPlan;
	private SESGISConfigIconLibrary iconGis;
	private SESWssEPEmcPlanAction instructions;



	private Boolean isMapPoint = null; // 是否已录入坐标



	private String mapPoint ; // 坐标
	private Staff owner;
	private SESEABSetionmember ownPerson;



	private String sectionId ; // 通讯组Id
	private SESEABEabSetion sectionObj;

	@JsonBackReference("actionOwnersList")
	@JSONField(serialize = false)
	private List<SESECDActionOwners> actionOwnersList;
	
	public void setActionOwnersList(List<SESECDActionOwners> actionOwnersList){
		this.actionOwnersList = actionOwnersList;
	}
	@Transient
	public List<SESECDActionOwners> getActionOwnersList(){
		return actionOwnersList;
	}
	@JsonBackReference("alarmActCameraList")
	@JSONField(serialize = false)
	private List<SESECDAlarmActCamera> alarmActCameraList;
	
	public void setAlarmActCameraList(List<SESECDAlarmActCamera> alarmActCameraList){
		this.alarmActCameraList = alarmActCameraList;
	}
	@Transient
	public List<SESECDAlarmActCamera> getAlarmActCameraList(){
		return alarmActCameraList;
	}
	
	private Document document;
	
	private String bapAttachmentInfo;
	
	@Transient
	@XmlJavaTypeAdapter(DocumentAdaptor.class)
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Transient
	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}
	
	/**
	 * 获取行动地点.
	 * 
	 * @return 行动地点
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_ADDRESS"
	)
    public String getActionAddress() {
        return actionAddress;
    }
	/**
	 * 设置行动地点.
	 * @param actionAddress 行动地点
	 */
    public void setActionAddress(String actionAddress) {
        this.actionAddress = actionAddress;
    }
	/**
	 * 获取行动分类.
	 * 
	 * @return 行动分类
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACTION_CATOGORY", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getActionCatogory() {
		return actionCatogory;
	}
	/**
	 * 设置行动分类.
	 * @param actionCatogory 行动分类
	 */
	public void setActionCatogory(SystemCode actionCatogory) {
		this.actionCatogory = (SystemCode) actionCatogory;
	}
	/**
	 * 获取行动描述.
	 * 
	 * @return 行动描述
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_DESCRIPTION"
	)
    public String getActionDescription() {
        return actionDescription;
    }
	/**
	 * 设置行动描述.
	 * @param actionDescription 行动描述
	 */
    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
	/**
	 * 获取行动名称.
	 * 
	 * @return 行动名称
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_NAME"
	)
    public String getActionName() {
        return actionName;
    }
	/**
	 * 设置行动名称.
	 * @param actionName 行动名称
	 */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
	@ManyToOne
	@JoinColumn(name = "ALARM_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDAlmAlarmRecord getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(SESECDAlmAlarmRecord alarmId) {
        this.alarmId = alarmId;
    }
	/**
	 * 获取指令状态.
	 * 
	 * @return 指令状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="COMMOM_STATE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getCommomState() {
		return commomState;
	}
	/**
	 * 设置指令状态.
	 * @param commomState 指令状态
	 */
	public void setCommomState(SystemCode commomState) {
		this.commomState = (SystemCode) commomState;
	}
	@ManyToOne
	@JoinColumn(name = "EMERGENCY_PLAN", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESWssEPEmergencyPlan getEmergencyPlan() {
        return emergencyPlan;
    }
    public void setEmergencyPlan(SESWssEPEmergencyPlan emergencyPlan) {
        this.emergencyPlan = emergencyPlan;
    }
	@OneToOne
	@JoinColumn(name = "ICON_GIS", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESGISConfigIconLibrary getIconGis() {
        return iconGis;
    }
    public void setIconGis(SESGISConfigIconLibrary iconGis) {
        this.iconGis = iconGis;
    }
	@OneToOne
	@JoinColumn(name = "INSTRUCTIONS", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESWssEPEmcPlanAction getInstructions() {
        return instructions;
    }
    public void setInstructions(SESWssEPEmcPlanAction instructions) {
        this.instructions = instructions;
    }
	/**
	 * 获取是否已录入坐标.
	 * 
	 * @return 是否已录入坐标
	 */
	@Column(nullable=true

		,name="IS_MAP_POINT"
	)
    public Boolean getIsMapPoint() {
        return isMapPoint;
    }
	/**
	 * 设置是否已录入坐标.
	 * @param isMapPoint 是否已录入坐标
	 */
    public void setIsMapPoint(Boolean isMapPoint) {
        this.isMapPoint = isMapPoint;
    }
	/**
	 * 获取坐标.
	 * 
	 * @return 坐标
	 */
	@Column(nullable=true

		,name="MAP_POINT"
	)
    public String getMapPoint() {
        return mapPoint;
    }
	/**
	 * 设置坐标.
	 * @param mapPoint 坐标
	 */
    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }
	@OneToOne
	@JoinColumn(name = "OWNER", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getOwner() {
        return owner;
    }
    public void setOwner(Staff owner) {
        this.owner = owner;
    }
	@OneToOne
	@JoinColumn(name = "OWN_PERSON", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESEABSetionmember getOwnPerson() {
        return ownPerson;
    }
    public void setOwnPerson(SESEABSetionmember ownPerson) {
        this.ownPerson = ownPerson;
    }
	/**
	 * 获取通讯组Id.
	 * 
	 * @return 通讯组Id
	 */
	@Column(nullable=true

		,length = 256
		,name="SECTION_ID"
	)
    public String getSectionId() {
        return sectionId;
    }
	/**
	 * 设置通讯组Id.
	 * @param sectionId 通讯组Id
	 */
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
	@OneToOne
	@JoinColumn(name = "SECTION_OBJ", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESEABEabSetion getSectionObj() {
        return sectionObj;
    }
    public void setSectionObj(SESEABEabSetion sectionObj) {
        this.sectionObj = sectionObj;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDAlarmAction.class.getName();
	}
	
	
	private Company company;

	@Transient
	private String cidName;
	@Transient
	public String getCidName() {
        return cidName;
    }

    public void setCidName(String cidName) {
        this.cidName = cidName;
    }
	
	@Override
	@OneToOne(fetch=FetchType.EAGER, targetEntity=Company.class, optional=true)
	@JoinColumn(name=ICId.P_CID, insertable=false, updatable=false)
	@Fetch(FetchMode.SELECT)
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	public Company getCompany() {
		return this.company;
	}
	
	@Override
	public void setCompany(Company company) {
		this.company =company;
	}
	private String actionIdOwnerDeleteIds;
	private String actionIdOwnerAddIds;
	private String actionIdOwnermultiselectIDs;
	private String actionIdOwnermultiselectNames;

	@Transient
	public String getActionIdOwnerDeleteIds(){
		return this.actionIdOwnerDeleteIds;
	}

	public void setActionIdOwnerDeleteIds(String deleteIds){
		this.actionIdOwnerDeleteIds = deleteIds;
	}

	@Transient
	public String getActionIdOwnermultiselectIDs(){
		return this.actionIdOwnermultiselectIDs;
	}

	public void setActionIdOwnermultiselectIDs(String multiselectIDs){
		this.actionIdOwnermultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getActionIdOwnermultiselectNames(){
		return this.actionIdOwnermultiselectNames;
	}

	public void setActionIdOwnermultiselectNames(String multiselectNames){
		this.actionIdOwnermultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getActionIdOwnPersonDeleteIds(){
		return this.actionIdOwnPersonDeleteIds;
	}

	public void setActionIdOwnPersonDeleteIds(String deleteIds){
		this.actionIdOwnPersonDeleteIds = deleteIds;
	}

	@Transient
	public String getActionIdOwnPersonmultiselectIDs(){
		return this.actionIdOwnPersonmultiselectIDs;
	}

	public void setActionIdOwnPersonmultiselectIDs(String multiselectIDs){
		this.actionIdOwnPersonmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getActionIdOwnPersonmultiselectNames(){
		return this.actionIdOwnPersonmultiselectNames;
	}

	public void setActionIdOwnPersonmultiselectNames(String multiselectNames){
		this.actionIdOwnPersonmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmActionIdVideoIdDeleteIds(){
		return this.alarmActionIdVideoIdDeleteIds;
	}

	public void setAlarmActionIdVideoIdDeleteIds(String deleteIds){
		this.alarmActionIdVideoIdDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmActionIdVideoIdmultiselectIDs(){
		return this.alarmActionIdVideoIdmultiselectIDs;
	}

	public void setAlarmActionIdVideoIdmultiselectIDs(String multiselectIDs){
		this.alarmActionIdVideoIdmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmActionIdVideoIdmultiselectNames(){
		return this.alarmActionIdVideoIdmultiselectNames;
	}

	public void setAlarmActionIdVideoIdmultiselectNames(String multiselectNames){
		this.alarmActionIdVideoIdmultiselectNames = multiselectNames;
	}

	@Transient
	public String getAlarmActionIdVideoIdAddIds(){
		return this.alarmActionIdVideoIdAddIds;
	}

	public void setAlarmActionIdVideoIdAddIds(String addIds){
		this.alarmActionIdVideoIdAddIds = addIds;
	}

	
	private String virtualId;
	@Transient
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}
	// 在此区域内自行添加JAVA代码,此处添加的JAVA代码必须不涉及持久化操作,
	// 如果加入get方法,请添加@javax.persistence.Transient注解
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_AlarmAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
