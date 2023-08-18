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
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.SESWssER.entities.SESWssERAccidentClass;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.SESED.entities.SESEDPlan;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Company;		
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigFrequentplace;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Position;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Company;		
import com.supcon.orchid.foundation.entities.Company;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 接警记录.
 */
@javax.persistence.Entity(name=SESECDAlmAlarmRecord.JPA_NAME)
@Table(name = SESECDAlmAlarmRecord.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_AlmAlarmRecord")
@AttributeOverrides({
		@AttributeOverride(name="createStaffId", column=@Column(name = "CREATE_STAFF_ID")),
		@AttributeOverride(name="createTime", column=@Column(name = "CREATE_TIME")),
		@AttributeOverride(name="deleteStaffId", column=@Column(name = "DELETE_STAFF_ID")),
		@AttributeOverride(name="deleteTime", column=@Column(name = "DELETE_TIME")),
		@AttributeOverride(name="effectStaffId", column=@Column(name = "EFFECT_STAFF_ID")),
		@AttributeOverride(name="effectTime", column=@Column(name = "EFFECT_TIME")),
		@AttributeOverride(name="extraCol", column=@Column(name = "EXTRA_COL")),
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="modifyStaffId", column=@Column(name = "MODIFY_STAFF_ID")),
		@AttributeOverride(name="modifyTime", column=@Column(name = "MODIFY_TIME")),
		@AttributeOverride(name="ownerDepartmentId", column=@Column(name = "OWNER_DEPARTMENT_ID")),
		@AttributeOverride(name="ownerPositionId", column=@Column(name = "OWNER_POSITION_ID")),
		@AttributeOverride(name="ownerStaffId", column=@Column(name = "OWNER_STAFF_ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="status", column=@Column(name = "STATUS")),
		@AttributeOverride(name="valid", column=@Column(name = "VALID")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDAlmAlarmRecordConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord", modelName = "SESECD.alarmRecord.AlmAlarmRecord", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDAlmAlarmRecord extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord";
	public static final String DOC_TYPE = "SESECD_alarmRecord_AlmAlarmRecord";
	public static final String TABLE_NAME = "ses_ecd_alarm_records";
	public static final String JPA_NAME = "SESECDAlmAlarmRecord";
	public static final String ENCLOSURE_PROPERTY_CODE = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_enclosure";
	private SystemCode accidentClass
;// 事故类别



	private Integer accidentId ; // 事故Id
	private SystemCode accidentLevel
;// 事故等级



	private String accidentName ; // 事件名称
	private SystemCode accidentReason
;// 事故原因
	private SESWssERAccidentClass accidentType;



	private String alarmIds ; // 应急事件IDS
	private SystemCode alarmLevel
;// 报警等级
	private Staff alarmPerson;



	private String alarmPhone ; // 报警人电话



	private String alarmSource ; // 来源
	private SystemCode alarmType
;// 报警类别
	private SESECDAlertRecord alertRecordId;



	private Integer deathPerson ; // 死亡人数



	private String description ; // 事件描述
	private SESEDPlan drillPlanId;
	private SESECDEmEventLeveL emEventLeveL;
	private SESECDEmEventType emEventType;
	
	private String enclosureAttachementInfo;
	
	private Document enclosureDocument;
	
    private List<String> enclosureFileAddPaths; //新添加附件的路径
	private List<Long> enclosureFileDeleteIds;//已删除附件ID
	private List<Long> enclosureMultiFileIds;//已添加附件ID
	private List<String> enclosureMultiFileNames;//已添加附件name
	private List<String> enclosureMultiFileIcons;//已添加附件的图标
	private List<String> enclosureMultiFileUrls;//已添加附件的路径
    private List<String>  enclosureMultiCreators;//创建人员
    private List<Date>  enclosureMultiCreateTimes;//创建时间
    private List<String>  enclosureMultiSizes;//大小
    private List<String>  enclosureMultiPreviewTimes;//预览次数
    private List<String>  enclosureMultiDownloadTimes;//下载次数

    	@Transient
        public List<String> getEnclosureMultiCreators(){
             return this.enclosureMultiCreators;
        }

        public void setEnclosureMultiCreators(List<String> enclosureMultiCreators){
            this.enclosureMultiCreators = enclosureMultiCreators;
        }

    	@Transient
        public List<Date> getEnclosureMultiCreateTimes(){
             return this.enclosureMultiCreateTimes;
        }

        public void setEnclosureMultiCreateTimes(List<Date> enclosureMultiCreateTimes){
            this.enclosureMultiCreateTimes = enclosureMultiCreateTimes;
        }

    	@Transient
        public List<String> getEnclosureMultiSizes(){
             return this.enclosureMultiSizes;
        }

        public void setEnclosureMultiSizes(List<String> enclosureMultiSizes){
            this.enclosureMultiSizes = enclosureMultiSizes;
        }

    	@Transient
        public List<String> getEnclosureMultiPreviewTimes(){
             return this.enclosureMultiPreviewTimes;
        }

        public void setEnclosureMultiPreviewTimes(List<String> enclosureMultiPreviewTimes){
            this.enclosureMultiPreviewTimes = enclosureMultiPreviewTimes;
        }

    	@Transient
        public List<String> getEnclosureMultiDownloadTimes(){
             return this.enclosureMultiDownloadTimes;
        }

        public void setEnclosureMultiDownloadTimes(List<String> enclosureMultiDownloadTimes){
            this.enclosureMultiDownloadTimes = enclosureMultiDownloadTimes;
        }

    @Transient
    public List<String> getEnclosureMultiFileUrls(){
        return this.enclosureMultiFileUrls;
    }

    public void setEnclosureMultiFileUrls(List<String> enclosureMultiFileUrls){
        this.enclosureMultiFileUrls = enclosureMultiFileUrls;
    }

	@Transient
	public List<String> getEnclosureFileAddPaths(){
    	return this.enclosureFileAddPaths;
    }

	public void setEnclosureFileAddPaths(List<String> enclosureFileAddPaths){
    		this.enclosureFileAddPaths = enclosureFileAddPaths;
    }

	@Transient
    public List<Long> getEnclosureFileDeleteIds(){
		return this.enclosureFileDeleteIds;
	}

    public void setEnclosureFileDeleteIds(List<Long> enclosureFileDeleteIds){
		this.enclosureFileDeleteIds = enclosureFileDeleteIds;
	}

	@Transient
	public List<Long> getEnclosureMultiFileIds(){
		return this.enclosureMultiFileIds;
	}
	public void setEnclosureMultiFileIds(List<Long> enclosureMultiFileIds){
		this.enclosureMultiFileIds = enclosureMultiFileIds;
	}

	@Transient
	public List<String> getEnclosureMultiFileNames(){
		return this.enclosureMultiFileNames;
	}
	
	public void setEnclosureMultiFileNames(List<String> enclosureMultiFileNames){
		this.enclosureMultiFileNames = enclosureMultiFileNames;
	}

	@Transient
	public List<String> getEnclosureMultiFileIcons(){
		return this.enclosureMultiFileIcons;
	}
	public void setEnclosureMultiFileIcons(List<String> enclosureMultiFileIcons){
		this.enclosureMultiFileIcons = enclosureMultiFileIcons;
	}

	private SystemCode eventDataSource
;// 事件数据来源



	private String eventNo ; // 事件编号
	private SystemCode eventSource
		= new SystemCode("SESECD_eventSource/001")
		;// 事件来源
	private SystemCode eventType
;// 事件类型
	private SystemCode evTypes
;// 事件分类

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date happenTime ; // 事发时间



	private String hei ; // 地图高度
	private Department hpmDepartment;
	private Company hpnCompany;



	private Boolean isEmergency = null; // 进入应急处置



	private Boolean isIncident = null; // 是否应急事件

		private Boolean isOver = 
			Boolean.valueOf("false")
		;// 是否结束



	private Boolean isSimulation = null; // 演练事件



	private Boolean isTurnAlarm = null; // 是否归档



	private String lat ; // 地图纬度
	private SESGISConfigFrequentplace locationIncident;



	private String lon ; // 地图经度



	private String mapPoint ; // 坐标
	private Staff mesPerson;



	private Boolean openLabel = null; // 是否打开扩展信息

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date overTime ; // 结束时间



	private String point ; // 坐标点



	private String pointInfo ; // 坐标



	private String position ; // 发生位置



	private String process ; // 处理记录
	private SystemCode processState
;// 处理状态
	private Staff receiver;

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date rectime ; // 接警时间



	private Long reportEventId ; // 上报事件接警id
	private SystemCode responseLevel
;// 响应等级
	private Company threeCompany;



	private String threeEPInfo ; // 三级公司应急预案
	private Company twoCompany;



	private String twoEPInfo ; // 二级公司应急预案



	private Boolean voiceConfigFlag = null; // 语音配置标记
	private SESECDVoiceConfig voiceConfigId;



	private Integer wounderPerson ; // 受伤人数

	@JsonBackReference("communicationList")
	@JSONField(serialize = false)
	private List<SESECDCommunication> communicationList;
	
	public void setCommunicationList(List<SESECDCommunication> communicationList){
		this.communicationList = communicationList;
	}
	@Transient
	public List<SESECDCommunication> getCommunicationList(){
		return communicationList;
	}
	@JsonBackReference("alarmEnenetrelList")
	@JSONField(serialize = false)
	private List<SESECDAlarmEnenetrel> alarmEnenetrelList;
	
	public void setAlarmEnenetrelList(List<SESECDAlarmEnenetrel> alarmEnenetrelList){
		this.alarmEnenetrelList = alarmEnenetrelList;
	}
	@Transient
	public List<SESECDAlarmEnenetrel> getAlarmEnenetrelList(){
		return alarmEnenetrelList;
	}
	@JsonBackReference("recorSituationList")
	@JSONField(serialize = false)
	private List<SESECDRecorSituation> recorSituationList;
	
	public void setRecorSituationList(List<SESECDRecorSituation> recorSituationList){
		this.recorSituationList = recorSituationList;
	}
	@Transient
	public List<SESECDRecorSituation> getRecorSituationList(){
		return recorSituationList;
	}
	@JsonBackReference("mesPersonList")
	@JSONField(serialize = false)
	private List<SESECDMesPerson> mesPersonList;
	
	public void setMesPersonList(List<SESECDMesPerson> mesPersonList){
		this.mesPersonList = mesPersonList;
	}
	@Transient
	public List<SESECDMesPerson> getMesPersonList(){
		return mesPersonList;
	}
	@JsonBackReference("emePlanObjList")
	@JSONField(serialize = false)
	private List<SESECDEmePlanObj> emePlanObjList;
	
	public void setEmePlanObjList(List<SESECDEmePlanObj> emePlanObjList){
		this.emePlanObjList = emePlanObjList;
	}
	@Transient
	public List<SESECDEmePlanObj> getEmePlanObjList(){
		return emePlanObjList;
	}
	@JsonBackReference("alarmActionList")
	@JSONField(serialize = false)
	private List<SESECDAlarmAction> alarmActionList;
	
	public void setAlarmActionList(List<SESECDAlarmAction> alarmActionList){
		this.alarmActionList = alarmActionList;
	}
	@Transient
	public List<SESECDAlarmAction> getAlarmActionList(){
		return alarmActionList;
	}
	@JsonBackReference("recordActionList")
	@JSONField(serialize = false)
	private List<SESECDRecordAction> recordActionList;
	
	public void setRecordActionList(List<SESECDRecordAction> recordActionList){
		this.recordActionList = recordActionList;
	}
	@Transient
	public List<SESECDRecordAction> getRecordActionList(){
		return recordActionList;
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
	 * 获取事故类别.
	 * 
	 * @return 事故类别
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACCIDENT_CLASS", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAccidentClass() {
		return accidentClass;
	}
	/**
	 * 设置事故类别.
	 * @param accidentClass 事故类别
	 */
	public void setAccidentClass(SystemCode accidentClass) {
		this.accidentClass = (SystemCode) accidentClass;
	}
	/**
	 * 获取事故Id.
	 * 
	 * @return 事故Id
	 */
	@Column(nullable=true

		,name="ACCIDENT_ID"
	)
    public Integer getAccidentId() {
        return accidentId;
    }
	/**
	 * 设置事故Id.
	 * @param accidentId 事故Id
	 */
    public void setAccidentId(Integer accidentId) {
        this.accidentId = accidentId;
    }
	/**
	 * 获取事故等级.
	 * 
	 * @return 事故等级
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACCIDENT_LEVEL", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAccidentLevel() {
		return accidentLevel;
	}
	/**
	 * 设置事故等级.
	 * @param accidentLevel 事故等级
	 */
	public void setAccidentLevel(SystemCode accidentLevel) {
		this.accidentLevel = (SystemCode) accidentLevel;
	}
	/**
	 * 获取事件名称.
	 * 
	 * @return 事件名称
	 */
	@Column(nullable=true

		,length = 256
		,name="ACCIDENT_NAME"
	)
    public String getAccidentName() {
        return accidentName;
    }
	/**
	 * 设置事件名称.
	 * @param accidentName 事件名称
	 */
    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }
	/**
	 * 获取事故原因.
	 * 
	 * @return 事故原因
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACCIDENT_REASON", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAccidentReason() {
		return accidentReason;
	}
	/**
	 * 设置事故原因.
	 * @param accidentReason 事故原因
	 */
	public void setAccidentReason(SystemCode accidentReason) {
		this.accidentReason = (SystemCode) accidentReason;
	}
	@OneToOne
	@JoinColumn(name = "ACCIDENT_TYPE", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESWssERAccidentClass getAccidentType() {
        return accidentType;
    }
    public void setAccidentType(SESWssERAccidentClass accidentType) {
        this.accidentType = accidentType;
    }
	/**
	 * 获取应急事件IDS.
	 * 
	 * @return 应急事件IDS
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_IDS"
	)
    public String getAlarmIds() {
        return alarmIds;
    }
	/**
	 * 设置应急事件IDS.
	 * @param alarmIds 应急事件IDS
	 */
    public void setAlarmIds(String alarmIds) {
        this.alarmIds = alarmIds;
    }
	/**
	 * 获取报警等级.
	 * 
	 * @return 报警等级
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ALARM_LEVEL", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAlarmLevel() {
		return alarmLevel;
	}
	/**
	 * 设置报警等级.
	 * @param alarmLevel 报警等级
	 */
	public void setAlarmLevel(SystemCode alarmLevel) {
		this.alarmLevel = (SystemCode) alarmLevel;
	}
	@OneToOne
	@JoinColumn(name = "ALARM_PERSON", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getAlarmPerson() {
        return alarmPerson;
    }
    public void setAlarmPerson(Staff alarmPerson) {
        this.alarmPerson = alarmPerson;
    }
	/**
	 * 获取报警人电话.
	 *  
	 * @return 报警人电话
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_PHONE"
	)
    public String getAlarmPhone() {
        return alarmPhone;
    }
	/**
	 * 设置报警人电话.
	 * @param alarmPhone 报警人电话
	 */
    public void setAlarmPhone(String alarmPhone) {
        this.alarmPhone = alarmPhone;
    }
	/**
	 * 获取来源.
	 * 
	 * @return 来源
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_SOURCE"
	)
    public String getAlarmSource() {
        return alarmSource;
    }
	/**
	 * 设置来源.
	 * @param alarmSource 来源
	 */
    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }
	/**
	 * 获取报警类别.
	 * 
	 * @return 报警类别
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ALARM_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAlarmType() {
		return alarmType;
	}
	/**
	 * 设置报警类别.
	 * @param alarmType 报警类别
	 */
	public void setAlarmType(SystemCode alarmType) {
		this.alarmType = (SystemCode) alarmType;
	}
	@ManyToOne
	@JoinColumn(name = "ALERT_RECORD_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDAlertRecord getAlertRecordId() {
        return alertRecordId;
    }
    public void setAlertRecordId(SESECDAlertRecord alertRecordId) {
        this.alertRecordId = alertRecordId;
    }
	/**
	 * 获取死亡人数.
	 * 
	 * @return 死亡人数
	 */
	@Column(nullable=true

		,name="DEATH_PERSON"
	)
    public Integer getDeathPerson() {
        return deathPerson;
    }
	/**
	 * 设置死亡人数.
	 * @param deathPerson 死亡人数
	 */
    public void setDeathPerson(Integer deathPerson) {
        this.deathPerson = deathPerson;
    }
	/**
	 * 获取事件描述.
	 * 
	 * @return 事件描述
	 */
	@Column(nullable=true

		,length = 256
		,name="DESCRIPTION"
	)
    public String getDescription() {
        return description;
    }
	/**
	 * 设置事件描述.
	 * @param description 事件描述
	 */
    public void setDescription(String description) {
        this.description = description;
    }
	@OneToOne
	@JoinColumn(name = "DRILL_PLAN_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESEDPlan getDrillPlanId() {
        return drillPlanId;
    }
    public void setDrillPlanId(SESEDPlan drillPlanId) {
        this.drillPlanId = drillPlanId;
    }
	@OneToOne
	@JoinColumn(name = "EM_EVENT_LEVEL", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDEmEventLeveL getEmEventLeveL() {
        return emEventLeveL;
    }
    public void setEmEventLeveL(SESECDEmEventLeveL emEventLeveL) {
        this.emEventLeveL = emEventLeveL;
    }
	@OneToOne
	@JoinColumn(name = "EM_EVENT_TYPE", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDEmEventType getEmEventType() {
        return emEventType;
    }
    public void setEmEventType(SESECDEmEventType emEventType) {
        this.emEventType = emEventType;
    }
	
	public void setEnclosureAttachementInfo(String enclosureAttachementInfo){
		this.enclosureAttachementInfo = enclosureAttachementInfo;
	}
	
	@Transient
	public String getEnclosureAttachementInfo(){
		return this.enclosureAttachementInfo;
	}
	
	public void setEnclosureDocument(Document enclosureDocument){
		this.enclosureDocument = enclosureDocument;
	}
	
	@Transient
	public Document getEnclosureDocument(){
		return this.enclosureDocument;
	}

	/**
	 * 获取事件数据来源.
	 * 
	 * @return 事件数据来源
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="EVENT_DATA_SOURCE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getEventDataSource() {
		return eventDataSource;
	}
	/**
	 * 设置事件数据来源.
	 * @param eventDataSource 事件数据来源
	 */
	public void setEventDataSource(SystemCode eventDataSource) {
		this.eventDataSource = (SystemCode) eventDataSource;
	}
	/**
	 * 获取事件编号.
	 * 
	 * @return 事件编号
	 */
	@Column(nullable=true

		,length = 256
		,name="EVENT_NO"
	)
    public String getEventNo() {
        return eventNo;
    }
	/**
	 * 设置事件编号.
	 * @param eventNo 事件编号
	 */
    public void setEventNo(String eventNo) {
        this.eventNo = eventNo;
    }
	/**
	 * 获取事件来源.
	 * 
	 * @return 事件来源
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="EVENT_SOURCE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getEventSource() {
		return eventSource;
	}
	/**
	 * 设置事件来源.
	 * @param eventSource 事件来源
	 */
	public void setEventSource(SystemCode eventSource) {
		this.eventSource = (SystemCode) eventSource;
	}
	/**
	 * 获取事件类型.
	 * 
	 * @return 事件类型
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="EVENT_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getEventType() {
		return eventType;
	}
	/**
	 * 设置事件类型.
	 * @param eventType 事件类型
	 */
	public void setEventType(SystemCode eventType) {
		this.eventType = (SystemCode) eventType;
	}
	/**
	 * 获取事件分类.
	 * 
	 * @return 事件分类
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="EV_TYPES", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getEvTypes() {
		return evTypes;
	}
	/**
	 * 设置事件分类.
	 * @param evTypes 事件分类
	 */
	public void setEvTypes(SystemCode evTypes) {
		this.evTypes = (SystemCode) evTypes;
	}
	/**
	 * 获取事发时间.
	 * 
	 * @return 事发时间
	 */
	@Column(nullable=true

		,name="HAPPEN_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getHappenTime() {
        return happenTime;
    }
	/**
	 * 设置事发时间.
	 * @param happenTime 事发时间
	 */
    public void setHappenTime(Date happenTime) {
        this.happenTime = happenTime;
    }
	/**
	 * 获取地图高度.
	 * 
	 * @return 地图高度
	 */
	@Column(nullable=true

		,length = 256
		,name="HEI"
	)
    public String getHei() {
        return hei;
    }
	/**
	 * 设置地图高度.
	 * @param hei 地图高度
	 */
    public void setHei(String hei) {
        this.hei = hei;
    }
	@OneToOne
	@JoinColumn(name = "HPM_DEPARTMENT", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Department getHpmDepartment() {
        return hpmDepartment;
    }
    public void setHpmDepartment(Department hpmDepartment) {
        this.hpmDepartment = hpmDepartment;
    }
	@OneToOne
	@JoinColumn(name = "HPN_COMPANY", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Company getHpnCompany() {
        return hpnCompany;
    }
    public void setHpnCompany(Company hpnCompany) {
        this.hpnCompany = hpnCompany;
    }
	/**
	 * 获取进入应急处置.
	 * 
	 * @return 进入应急处置
	 */
	@Column(nullable=true

		,name="IS_EMERGENCY"
	)
    public Boolean getIsEmergency() {
        return isEmergency;
    }
	/**
	 * 设置进入应急处置.
	 * @param isEmergency 进入应急处置
	 */
    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
	/**
	 * 获取是否应急事件.
	 * 
	 * @return 是否应急事件
	 */
	@Column(nullable=true

		,name="IS_INCIDENT"
	)
    public Boolean getIsIncident() {
        return isIncident;
    }
	/**
	 * 设置是否应急事件.
	 * @param isIncident 是否应急事件
	 */
    public void setIsIncident(Boolean isIncident) {
        this.isIncident = isIncident;
    }
	/**
	 * 获取是否结束.
	 * 
	 * @return 是否结束
	 */
	@Column(nullable=true

		,name="IS_OVER"
	)
    public Boolean getIsOver() {
        return isOver;
    }
	/**
	 * 设置是否结束.
	 * @param isOver 是否结束
	 */
    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }
	/**
	 * 获取演练事件.
	 * 
	 * @return 演练事件
	 */
	@Column(nullable=true

		,name="IS_SIMULATION"
	)
    public Boolean getIsSimulation() {
        return isSimulation;
    }
	/**
	 * 设置演练事件.
	 * @param isSimulation 演练事件
	 */
    public void setIsSimulation(Boolean isSimulation) {
        this.isSimulation = isSimulation;
    }
	/**
	 * 获取是否归档.
	 * 
	 * @return 是否归档
	 */
	@Column(nullable=true

		,name="IS_TURN_ALARM"
	)
    public Boolean getIsTurnAlarm() {
        return isTurnAlarm;
    }
	/**
	 * 设置是否归档.
	 * @param isTurnAlarm 是否归档
	 */
    public void setIsTurnAlarm(Boolean isTurnAlarm) {
        this.isTurnAlarm = isTurnAlarm;
    }
	/**
	 * 获取地图纬度.
	 * 
	 * @return 地图纬度
	 */
	@Column(nullable=true

		,length = 256
		,name="LAT"
	)
    public String getLat() {
        return lat;
    }
	/**
	 * 设置地图纬度.
	 * @param lat 地图纬度
	 */
    public void setLat(String lat) {
        this.lat = lat;
    }
	@OneToOne
	@JoinColumn(name = "LOCATION_INCIDENT", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESGISConfigFrequentplace getLocationIncident() {
        return locationIncident;
    }
    public void setLocationIncident(SESGISConfigFrequentplace locationIncident) {
        this.locationIncident = locationIncident;
    }
	/**
	 * 获取地图经度.
	 * 
	 * @return 地图经度
	 */
	@Column(nullable=true

		,length = 256
		,name="LON"
	)
    public String getLon() {
        return lon;
    }
	/**
	 * 设置地图经度.
	 * @param lon 地图经度
	 */
    public void setLon(String lon) {
        this.lon = lon;
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
	@JoinColumn(name = "MES_PERSON", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getMesPerson() {
        return mesPerson;
    }
    public void setMesPerson(Staff mesPerson) {
        this.mesPerson = mesPerson;
    }
	/**
	 * 获取是否打开扩展信息.
	 * 
	 * @return 是否打开扩展信息
	 */
	@Column(nullable=true

		,name="OPEN_LABEL"
	)
    public Boolean getOpenLabel() {
        return openLabel;
    }
	/**
	 * 设置是否打开扩展信息.
	 * @param openLabel 是否打开扩展信息
	 */
    public void setOpenLabel(Boolean openLabel) {
        this.openLabel = openLabel;
    }
	/**
	 * 获取结束时间.
	 * 
	 * @return 结束时间
	 */
	@Column(nullable=true

		,name="OVER_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOverTime() {
        return overTime;
    }
	/**
	 * 设置结束时间.
	 * @param overTime 结束时间
	 */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }
	/**
	 * 获取坐标点.
	 * 
	 * @return 坐标点
	 */
	@Column(nullable=true

		,length = 256
		,name="POINT"
	)
    public String getPoint() {
        return point;
    }
	/**
	 * 设置坐标点.
	 * @param point 坐标点
	 */
    public void setPoint(String point) {
        this.point = point;
    }
	/**
	 * 获取坐标.
	 * 
	 * @return 坐标
	 */
	@Column(nullable=true

		,length = 256
		,name="POINT_INFO"
	)
    public String getPointInfo() {
        return pointInfo;
    }
	/**
	 * 设置坐标.
	 * @param pointInfo 坐标
	 */
    public void setPointInfo(String pointInfo) {
        this.pointInfo = pointInfo;
    }
	/**
	 * 获取发生位置.
	 * 
	 * @return 发生位置
	 */
	@Column(nullable=true

		,length = 256
		,name="POSITION"
	)
    public String getPosition() {
        return position;
    }
	/**
	 * 设置发生位置.
	 * @param position 发生位置
	 */
    public void setPosition(String position) {
        this.position = position;
    }
	/**
	 * 获取处理记录.
	 * 
	 * @return 处理记录
	 */
	@Column(nullable=true

		,length = 256
		,name="PROCESS"
	)
    public String getProcess() {
        return process;
    }
	/**
	 * 设置处理记录.
	 * @param process 处理记录
	 */
    public void setProcess(String process) {
        this.process = process;
    }
	/**
	 * 获取处理状态.
	 * 
	 * @return 处理状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="PROCESS_STATE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getProcessState() {
		return processState;
	}
	/**
	 * 设置处理状态.
	 * @param processState 处理状态
	 */
	public void setProcessState(SystemCode processState) {
		this.processState = (SystemCode) processState;
	}
	@OneToOne
	@JoinColumn(name = "RECEIVER", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getReceiver() {
        return receiver;
    }
    public void setReceiver(Staff receiver) {
        this.receiver = receiver;
    }
	/**
	 * 获取接警时间.
	 * 
	 * @return 接警时间
	 */
	@Column(nullable=true

		,name="RECTIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getRectime() {
        return rectime;
    }
	/**
	 * 设置接警时间.
	 * @param rectime 接警时间
	 */
    public void setRectime(Date rectime) {
        this.rectime = rectime;
    }
	/**
	 * 获取上报事件接警id.
	 * 
	 * @return 上报事件接警id
	 */
	@Column(nullable=true

		,name="REPORT_EVENT_ID"
	)
    public Long getReportEventId() {
        return reportEventId;
    }
	/**
	 * 设置上报事件接警id.
	 * @param reportEventId 上报事件接警id
	 */
    public void setReportEventId(Long reportEventId) {
        this.reportEventId = reportEventId;
    }
	/**
	 * 获取响应等级.
	 * 
	 * @return 响应等级
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="RESPONSE_LEVEL", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getResponseLevel() {
		return responseLevel;
	}
	/**
	 * 设置响应等级.
	 * @param responseLevel 响应等级
	 */
	public void setResponseLevel(SystemCode responseLevel) {
		this.responseLevel = (SystemCode) responseLevel;
	}
	@OneToOne
	@JoinColumn(name = "THREE_COMPANY", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	
						
    public Company getThreeCompany() {
        return threeCompany;
    }
    public void setThreeCompany(Company threeCompany) {
        this.threeCompany = threeCompany;
    }
	/**
	 * 获取三级公司应急预案.
	 * 
	 * @return 三级公司应急预案
	 */
	@Column(nullable=true

		,length = 256
		,name="THREEEPINFO"
	)
    public String getThreeEPInfo() {
        return threeEPInfo;
    }
	/**
	 * 设置三级公司应急预案.
	 * @param threeEPInfo 三级公司应急预案
	 */
    public void setThreeEPInfo(String threeEPInfo) {
        this.threeEPInfo = threeEPInfo;
    }
	@OneToOne
	@JoinColumn(name = "TWO_COMPANY", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	
						
    public Company getTwoCompany() {
        return twoCompany;
    }
    public void setTwoCompany(Company twoCompany) {
        this.twoCompany = twoCompany;
    }
	/**
	 * 获取二级公司应急预案.
	 * 
	 * @return 二级公司应急预案
	 */
	@Column(nullable=true

		,length = 256
		,name="TWOEPINFO"
	)
    public String getTwoEPInfo() {
        return twoEPInfo;
    }
	/**
	 * 设置二级公司应急预案.
	 * @param twoEPInfo 二级公司应急预案
	 */
    public void setTwoEPInfo(String twoEPInfo) {
        this.twoEPInfo = twoEPInfo;
    }
	/**
	 * 获取语音配置标记.
	 * 
	 * @return 语音配置标记
	 */
	@Column(nullable=true

		,name="VOICE_CONFIG_FLAG"
	)
    public Boolean getVoiceConfigFlag() {
        return voiceConfigFlag;
    }
	/**
	 * 设置语音配置标记.
	 * @param voiceConfigFlag 语音配置标记
	 */
    public void setVoiceConfigFlag(Boolean voiceConfigFlag) {
        this.voiceConfigFlag = voiceConfigFlag;
    }
	@OneToOne
	@JoinColumn(name = "VOICE_CONFIG_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDVoiceConfig getVoiceConfigId() {
        return voiceConfigId;
    }
    public void setVoiceConfigId(SESECDVoiceConfig voiceConfigId) {
        this.voiceConfigId = voiceConfigId;
    }
	/**
	 * 获取受伤人数.
	 * 
	 * @return 受伤人数
	 */
	@Column(nullable=true

		,name="WOUNDER_PERSON"
	)
    public Integer getWounderPerson() {
        return wounderPerson;
    }
	/**
	 * 设置受伤人数.
	 * @param wounderPerson 受伤人数
	 */
    public void setWounderPerson(Integer wounderPerson) {
        this.wounderPerson = wounderPerson;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDAlmAlarmRecord.class.getName();
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
	private String alarmIdIconGisDeleteIds;
	private String alarmIdIconGisAddIds;
	private String alarmIdIconGismultiselectIDs;
	private String alarmIdIconGismultiselectNames;

	@Transient
	public String getAlarmIdIconGisDeleteIds(){
		return this.alarmIdIconGisDeleteIds;
	}

	public void setAlarmIdIconGisDeleteIds(String deleteIds){
		this.alarmIdIconGisDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdIconGismultiselectIDs(){
		return this.alarmIdIconGismultiselectIDs;
	}

	public void setAlarmIdIconGismultiselectIDs(String multiselectIDs){
		this.alarmIdIconGismultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdIconGismultiselectNames(){
		return this.alarmIdIconGismultiselectNames;
	}

	public void setAlarmIdIconGismultiselectNames(String multiselectNames){
		this.alarmIdIconGismultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdOwnPersonDeleteIds(){
		return this.alarmIdOwnPersonDeleteIds;
	}

	public void setAlarmIdOwnPersonDeleteIds(String deleteIds){
		this.alarmIdOwnPersonDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdOwnPersonmultiselectIDs(){
		return this.alarmIdOwnPersonmultiselectIDs;
	}

	public void setAlarmIdOwnPersonmultiselectIDs(String multiselectIDs){
		this.alarmIdOwnPersonmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdOwnPersonmultiselectNames(){
		return this.alarmIdOwnPersonmultiselectNames;
	}

	public void setAlarmIdOwnPersonmultiselectNames(String multiselectNames){
		this.alarmIdOwnPersonmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdOwnerDeleteIds(){
		return this.alarmIdOwnerDeleteIds;
	}

	public void setAlarmIdOwnerDeleteIds(String deleteIds){
		this.alarmIdOwnerDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdOwnermultiselectIDs(){
		return this.alarmIdOwnermultiselectIDs;
	}

	public void setAlarmIdOwnermultiselectIDs(String multiselectIDs){
		this.alarmIdOwnermultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdOwnermultiselectNames(){
		return this.alarmIdOwnermultiselectNames;
	}

	public void setAlarmIdOwnermultiselectNames(String multiselectNames){
		this.alarmIdOwnermultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdEmergencyPlanDeleteIds(){
		return this.alarmIdEmergencyPlanDeleteIds;
	}

	public void setAlarmIdEmergencyPlanDeleteIds(String deleteIds){
		this.alarmIdEmergencyPlanDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdEmergencyPlanmultiselectIDs(){
		return this.alarmIdEmergencyPlanmultiselectIDs;
	}

	public void setAlarmIdEmergencyPlanmultiselectIDs(String multiselectIDs){
		this.alarmIdEmergencyPlanmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdEmergencyPlanmultiselectNames(){
		return this.alarmIdEmergencyPlanmultiselectNames;
	}

	public void setAlarmIdEmergencyPlanmultiselectNames(String multiselectNames){
		this.alarmIdEmergencyPlanmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdIdDeleteIds(){
		return this.alarmIdIdDeleteIds;
	}

	public void setAlarmIdIdDeleteIds(String deleteIds){
		this.alarmIdIdDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdIdmultiselectIDs(){
		return this.alarmIdIdmultiselectIDs;
	}

	public void setAlarmIdIdmultiselectIDs(String multiselectIDs){
		this.alarmIdIdmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdIdmultiselectNames(){
		return this.alarmIdIdmultiselectNames;
	}

	public void setAlarmIdIdmultiselectNames(String multiselectNames){
		this.alarmIdIdmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdSectionObjDeleteIds(){
		return this.alarmIdSectionObjDeleteIds;
	}

	public void setAlarmIdSectionObjDeleteIds(String deleteIds){
		this.alarmIdSectionObjDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdSectionObjmultiselectIDs(){
		return this.alarmIdSectionObjmultiselectIDs;
	}

	public void setAlarmIdSectionObjmultiselectIDs(String multiselectIDs){
		this.alarmIdSectionObjmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdSectionObjmultiselectNames(){
		return this.alarmIdSectionObjmultiselectNames;
	}

	public void setAlarmIdSectionObjmultiselectNames(String multiselectNames){
		this.alarmIdSectionObjmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdInstructionsDeleteIds(){
		return this.alarmIdInstructionsDeleteIds;
	}

	public void setAlarmIdInstructionsDeleteIds(String deleteIds){
		this.alarmIdInstructionsDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdInstructionsmultiselectIDs(){
		return this.alarmIdInstructionsmultiselectIDs;
	}

	public void setAlarmIdInstructionsmultiselectIDs(String multiselectIDs){
		this.alarmIdInstructionsmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdInstructionsmultiselectNames(){
		return this.alarmIdInstructionsmultiselectNames;
	}

	public void setAlarmIdInstructionsmultiselectNames(String multiselectNames){
		this.alarmIdInstructionsmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdEnenetrelDeleteIds(){
		return this.alarmIdEnenetrelDeleteIds;
	}

	public void setAlarmIdEnenetrelDeleteIds(String deleteIds){
		this.alarmIdEnenetrelDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdEnenetrelmultiselectIDs(){
		return this.alarmIdEnenetrelmultiselectIDs;
	}

	public void setAlarmIdEnenetrelmultiselectIDs(String multiselectIDs){
		this.alarmIdEnenetrelmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdEnenetrelmultiselectNames(){
		return this.alarmIdEnenetrelmultiselectNames;
	}

	public void setAlarmIdEnenetrelmultiselectNames(String multiselectNames){
		this.alarmIdEnenetrelmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmIdPlanObjDeleteIds(){
		return this.alarmIdPlanObjDeleteIds;
	}

	public void setAlarmIdPlanObjDeleteIds(String deleteIds){
		this.alarmIdPlanObjDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmIdPlanObjmultiselectIDs(){
		return this.alarmIdPlanObjmultiselectIDs;
	}

	public void setAlarmIdPlanObjmultiselectIDs(String multiselectIDs){
		this.alarmIdPlanObjmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmIdPlanObjmultiselectNames(){
		return this.alarmIdPlanObjmultiselectNames;
	}

	public void setAlarmIdPlanObjmultiselectNames(String multiselectNames){
		this.alarmIdPlanObjmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getAlarmRecordIdPersonIdDeleteIds(){
		return this.alarmRecordIdPersonIdDeleteIds;
	}

	public void setAlarmRecordIdPersonIdDeleteIds(String deleteIds){
		this.alarmRecordIdPersonIdDeleteIds = deleteIds;
	}

	@Transient
	public String getAlarmRecordIdPersonIdmultiselectIDs(){
		return this.alarmRecordIdPersonIdmultiselectIDs;
	}

	public void setAlarmRecordIdPersonIdmultiselectIDs(String multiselectIDs){
		this.alarmRecordIdPersonIdmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getAlarmRecordIdPersonIdmultiselectNames(){
		return this.alarmRecordIdPersonIdmultiselectNames;
	}

	public void setAlarmRecordIdPersonIdmultiselectNames(String multiselectNames){
		this.alarmRecordIdPersonIdmultiselectNames = multiselectNames;
	}

	@Transient
	public String getAlarmRecordIdPersonIdAddIds(){
		return this.alarmRecordIdPersonIdAddIds;
	}

	public void setAlarmRecordIdPersonIdAddIds(String addIds){
		this.alarmRecordIdPersonIdAddIds = addIds;
	}

	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_bigintparama")
	private Integer bigintparama;
	
	@Column(name="BIGINTPARAMA")
    public Integer getBigintparama() {
        return bigintparama;
    }
	
    public void setBigintparama(Integer bigintparama) {
        this.bigintparama = bigintparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_bigintparamb")
	private Integer bigintparamb;
	
	@Column(name="BIGINTPARAMB")
    public Integer getBigintparamb() {
        return bigintparamb;
    }
	
    public void setBigintparamb(Integer bigintparamb) {
        this.bigintparamb = bigintparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_charparama")
	private String charparama;
	
	@Column(name="CHARPARAMA")
    public String getCharparama() {
        return charparama;
    }
	
    public void setCharparama(String charparama) {
        this.charparama = charparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_charparamb")
	private String charparamb;
	
	@Column(name="CHARPARAMB")
    public String getCharparamb() {
        return charparamb;
    }
	
    public void setCharparamb(String charparamb) {
        this.charparamb = charparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_charparamc")
	private String charparamc;
	
	@Column(name="CHARPARAMC")
    public String getCharparamc() {
        return charparamc;
    }
	
    public void setCharparamc(String charparamc) {
        this.charparamc = charparamc;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_charparamd")
	private String charparamd;
	
	@Column(name="CHARPARAMD")
    public String getCharparamd() {
        return charparamd;
    }
	
    public void setCharparamd(String charparamd) {
        this.charparamd = charparamd;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_dateparama")
	private Date dateparama;
	
	@Column(name="DATEPARAMA")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparama() {
        return dateparama;
    }
	
    public void setDateparama(Date dateparama) {
        this.dateparama = dateparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_dateparamb")
	private Date dateparamb;
	
	@Column(name="DATEPARAMB")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparamb() {
        return dateparamb;
    }
	
    public void setDateparamb(Date dateparamb) {
        this.dateparamb = dateparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_numberparama")
	private BigDecimal numberparama;
	
	@Column(name="NUMBERPARAMA")
    public BigDecimal getNumberparama() {
        return numberparama;
    }
	
    public void setNumberparama(BigDecimal numberparama) {
        this.numberparama = numberparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_numberparamb")
	private BigDecimal numberparamb;
	
	@Column(name="NUMBERPARAMB")
    public BigDecimal getNumberparamb() {
        return numberparamb;
    }
	
    public void setNumberparamb(BigDecimal numberparamb) {
        this.numberparamb = numberparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_objparama", complex = true)
	private Long objparama;
	
	@Column(name="OBJPARAMA")
	public Long getObjparama() {
		return objparama;
	}
	
	public void setObjparama(Long objparama) {
		this.objparama = objparama;
	}

	private String objparamaMainDisplay;
	
	@Transient
	public String getObjparamaMainDisplay() {
		return objparamaMainDisplay;
	}
	
	public void setObjparamaMainDisplay(String objparamaMainDisplay) {
		this.objparamaMainDisplay = objparamaMainDisplay;
	}
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_objparamb", complex = true)
	private Long objparamb;
	
	@Column(name="OBJPARAMB")
	public Long getObjparamb() {
		return objparamb;
	}
	
	public void setObjparamb(Long objparamb) {
		this.objparamb = objparamb;
	}

	private String objparambMainDisplay;
	
	@Transient
	public String getObjparambMainDisplay() {
		return objparambMainDisplay;
	}
	
	public void setObjparambMainDisplay(String objparambMainDisplay) {
		this.objparambMainDisplay = objparambMainDisplay;
	}
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_scparama", complex = true)
	private String scparama;
	
	@Column(name="SCPARAMA")
	public String getScparama() {
		return scparama;
	}
	
	public void setScparama(String scparama) {
		this.scparama = scparama;
	}

	private String scparamaMainDisplay;
	
	@Transient
	public String getScparamaMainDisplay() {
		return scparamaMainDisplay;
	}
	
	public void setScparamaMainDisplay(String scparamaMainDisplay) {
		this.scparamaMainDisplay = scparamaMainDisplay;
	}
	@BAPCustomComponent(code = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord_scparamb", complex = true)
	private String scparamb;
	
	@Column(name="SCPARAMB")
	public String getScparamb() {
		return scparamb;
	}
	
	public void setScparamb(String scparamb) {
		this.scparamb = scparamb;
	}

	private String scparambMainDisplay;
	
	@Transient
	public String getScparambMainDisplay() {
		return scparambMainDisplay;
	}
	
	public void setScparambMainDisplay(String scparambMainDisplay) {
		this.scparambMainDisplay = scparambMainDisplay;
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
