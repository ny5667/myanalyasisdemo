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
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigFrequentplace;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Position;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急态势.
 */
@javax.persistence.Entity(name=SESECDEmcSituation.JPA_NAME)
@Table(name = SESECDEmcSituation.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_emcSituation")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_emcSituation_EmcSituation")
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
@XmlSeeAlso({SESECDEmcSituationConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_emcSituation_EmcSituation", modelName = "SESECD.emcSituation.EmcSituation", entityCode = "SESECD_1.0.0_emcSituation", entityName = "SESECD.entityname.randon1577176777829")
public class SESECDEmcSituation extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_emcSituation";
	public static final String MODEL_CODE = "SESECD_1.0.0_emcSituation_EmcSituation";
	public static final String DOC_TYPE = "SESECD_emcSituation_EmcSituation";
	public static final String TABLE_NAME = "ses_ecd_emc_situations";
	public static final String JPA_NAME = "SESECDEmcSituation";
	public static final String ENCLOSURE_PROPERTY_CODE = "SESECD_1.0.0_emcSituation_EmcSituation_enclosure";
    public static final String ICON_PROPERTY_CODE = "SESECD_1.0.0_emcSituation_EmcSituation_icon";
	private SESGISConfigFrequentplace commonAddress;



	private Integer deathPerson ; // 死亡人数



	private String describtion ; // 态势描述
	
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

	private SESECDAlmAlarmRecord eventId;
	@BAPPicture
	private String icon; // 图标
	
	private Document iconDocument;
	private List<Document> iconDocumentList;
	private String iconAttachementInfo;
	private List<String> iconFileAddPaths; //新添加图片的路径
    	private List<Long> iconFileDeleteIds;//已删除图片ID
    	private List<Long> iconMultiFileIds;//已添加图片ID
    	private List<String> iconMultiFileNames;//已添加图片name
    	private List<String> iconMultiFileIcons;//已添加图片的图标
    	private List<String> iconMultiFileUrls;//已添加图片的路径
    	private List<String>  iconMultiCreators;//创建人员
    	private List<Date>  iconMultiCreateTimes;//创建时间
    	private List<String>  iconMultiSizes;//大小
    	private List<String>  iconMultiPreviewTimes;//预览次数
    	private List<String>  iconMultiDownloadTimes;//下载次数
    	@Transient
        public List<String> getIconMultiCreators(){
             return this.iconMultiCreators;
        }

        public void setIconMultiCreators(List<String> iconMultiCreators){
            this.iconMultiCreators = iconMultiCreators;
        }

    	@Transient
        public List<Date> getIconMultiCreateTimes(){
             return this.iconMultiCreateTimes;
        }

        public void setIconMultiCreateTimes(List<Date> iconMultiCreateTimes){
            this.iconMultiCreateTimes = iconMultiCreateTimes;
        }

    	@Transient
        public List<String> getIconMultiSizes(){
             return this.iconMultiSizes;
        }

        public void setIconMultiSizes(List<String> iconMultiSizes){
            this.iconMultiSizes = iconMultiSizes;
        }

    	@Transient
        public List<String> getIconMultiPreviewTimes(){
             return this.iconMultiPreviewTimes;
        }

        public void setIconMultiPreviewTimes(List<String> iconMultiPreviewTimes){
            this.iconMultiPreviewTimes = iconMultiPreviewTimes;
        }

    	@Transient
        public List<String> getIconMultiDownloadTimes(){
             return this.iconMultiDownloadTimes;
        }

        public void setIconMultiDownloadTimes(List<String> iconMultiDownloadTimes){
            this.iconMultiDownloadTimes = iconMultiDownloadTimes;
        }

    	@Transient
        public List<String> getIconMultiFileUrls(){
             return this.iconMultiFileUrls;
        }

        public void setIconMultiFileUrls(List<String> iconMultiFileUrls){
            this.iconMultiFileUrls = iconMultiFileUrls;
        }

    	@Transient
        	public List<String> getIconFileAddPaths(){
            	return this.iconFileAddPaths;
            }

        	public void setIconFileAddPaths(List<String> iconFileAddPaths){
            		this.iconFileAddPaths = iconFileAddPaths;
            }

        	@Transient
            public List<Long> getIconFileDeleteIds(){
        		return this.iconFileDeleteIds;
        	}

            public void setIconFileDeleteIds(List<Long> iconFileDeleteIds){
        		this.iconFileDeleteIds = iconFileDeleteIds;
        	}

        	@Transient
        	public List<Long> getIconMultiFileIds(){
        		return this.iconMultiFileIds;
        	}
        	public void setIconMultiFileIds(List<Long> iconMultiFileIds){
        		this.iconMultiFileIds = iconMultiFileIds;
        	}

        	@Transient
        	public List<String> getIconMultiFileNames(){
        		return this.iconMultiFileNames;
        	}

        	public void setIconMultiFileNames(List<String> iconMultiFileNames){
        		this.iconMultiFileNames = iconMultiFileNames;
        	}

        	@Transient
        	public List<String> getIconMultiFileIcons(){
        		return this.iconMultiFileIcons;
        	}
        	public void setIconMultiFileIcons(List<String> iconMultiFileIcons){
        		this.iconMultiFileIcons = iconMultiFileIcons;
        	}
        	public void setIconAttachementInfo(String iconAttachementInfo){
            		this.iconAttachementInfo = iconAttachementInfo;
            }

            @Transient
            public String getIconAttachementInfo(){
            	return this.iconAttachementInfo;
            }
	private SESGISConfigIconLibrary iconObjGis;

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date occursTime ; // 发生时间



	private String point ; // 坐标



	private String position ; // 事发地点
	private Staff reportPerson;
	private SystemCode situationType
;// 态势状态
	private SystemCode source
		= new SystemCode("SESECD_source/001")
		;// 来源



	private String voicePath ; // 语音存放路径



	private Integer woundedPerson ; // 受伤人数

	
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
	
	@OneToOne
	@JoinColumn(name = "COMMON_ADDRESS", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESGISConfigFrequentplace getCommonAddress() {
        return commonAddress;
    }
    public void setCommonAddress(SESGISConfigFrequentplace commonAddress) {
        this.commonAddress = commonAddress;
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
	 * 获取态势描述.
	 * 
	 * @return 态势描述
	 */
	@Column(nullable=true

		,length = 256
		,name="DESCRIBTION"
	)
    public String getDescribtion() {
        return describtion;
    }
	/**
	 * 设置态势描述.
	 * @param describtion 态势描述
	 */
    public void setDescribtion(String describtion) {
        this.describtion = describtion;
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

	@OneToOne
	@JoinColumn(name = "EVENT_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDAlmAlarmRecord getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecord eventId) {
        this.eventId = eventId;
    }
	/**
	 * 获取图标.
	 * 
	 * @return 图标
	 */
	@Column(name="ICON")
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置图标.
	 * @param icon 图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Transient
	public Document getIconDocument() {
		return iconDocument;
	}
	
	public void setIconDocument(Document iconDocument) {
		this.iconDocument = iconDocument;
	}

	@Transient
    public List<Document> getIconDocumentList() {
    	return iconDocumentList;
    }

    public void setIconDocumentList(List<Document> iconDocumentList) {
    	this.iconDocumentList = iconDocumentList;
    }
	
	@OneToOne
	@JoinColumn(name = "ICON_OBJ_GIS", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESGISConfigIconLibrary getIconObjGis() {
        return iconObjGis;
    }
    public void setIconObjGis(SESGISConfigIconLibrary iconObjGis) {
        this.iconObjGis = iconObjGis;
    }
	/**
	 * 获取发生时间.
	 * 
	 * @return 发生时间
	 */
	@Column(nullable=true

		,name="OCCURS_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOccursTime() {
        return occursTime;
    }
	/**
	 * 设置发生时间.
	 * @param occursTime 发生时间
	 */
    public void setOccursTime(Date occursTime) {
        this.occursTime = occursTime;
    }
	/**
	 * 获取坐标.
	 * 
	 * @return 坐标
	 */
	@Column(nullable=true

		,name="POINT"
	)
    public String getPoint() {
        return point;
    }
	/**
	 * 设置坐标.
	 * @param point 坐标
	 */
    public void setPoint(String point) {
        this.point = point;
    }
	/**
	 * 获取事发地点.
	 * 
	 * @return 事发地点
	 */
	@Column(nullable=true

		,length = 256
		,name="POSITION"
	)
    public String getPosition() {
        return position;
    }
	/**
	 * 设置事发地点.
	 * @param position 事发地点
	 */
    public void setPosition(String position) {
        this.position = position;
    }
	@OneToOne
	@JoinColumn(name = "REPORT_PERSON", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getReportPerson() {
        return reportPerson;
    }
    public void setReportPerson(Staff reportPerson) {
        this.reportPerson = reportPerson;
    }
	/**
	 * 获取态势状态.
	 * 
	 * @return 态势状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="SITUATION_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getSituationType() {
		return situationType;
	}
	/**
	 * 设置态势状态.
	 * @param situationType 态势状态
	 */
	public void setSituationType(SystemCode situationType) {
		this.situationType = (SystemCode) situationType;
	}
	/**
	 * 获取来源.
	 * 
	 * @return 来源
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="SOURCE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getSource() {
		return source;
	}
	/**
	 * 设置来源.
	 * @param source 来源
	 */
	public void setSource(SystemCode source) {
		this.source = (SystemCode) source;
	}
	/**
	 * 获取语音存放路径.
	 * 
	 * @return 语音存放路径
	 */
	@Column(nullable=true

		,length = 256
		,name="VOICE_PATH"
	)
    public String getVoicePath() {
        return voicePath;
    }
	/**
	 * 设置语音存放路径.
	 * @param voicePath 语音存放路径
	 */
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
	/**
	 * 获取受伤人数.
	 * 
	 * @return 受伤人数
	 */
	@Column(nullable=true

		,name="WOUNDED_PERSON"
	)
    public Integer getWoundedPerson() {
        return woundedPerson;
    }
	/**
	 * 设置受伤人数.
	 * @param woundedPerson 受伤人数
	 */
    public void setWoundedPerson(Integer woundedPerson) {
        this.woundedPerson = woundedPerson;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEmcSituation.class.getName();
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

	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_bigintparama")
	private Integer bigintparama;
	
	@Column(name="BIGINTPARAMA")
    public Integer getBigintparama() {
        return bigintparama;
    }
	
    public void setBigintparama(Integer bigintparama) {
        this.bigintparama = bigintparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_bigintparamb")
	private Integer bigintparamb;
	
	@Column(name="BIGINTPARAMB")
    public Integer getBigintparamb() {
        return bigintparamb;
    }
	
    public void setBigintparamb(Integer bigintparamb) {
        this.bigintparamb = bigintparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_charparama")
	private String charparama;
	
	@Column(name="CHARPARAMA")
    public String getCharparama() {
        return charparama;
    }
	
    public void setCharparama(String charparama) {
        this.charparama = charparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_charparamb")
	private String charparamb;
	
	@Column(name="CHARPARAMB")
    public String getCharparamb() {
        return charparamb;
    }
	
    public void setCharparamb(String charparamb) {
        this.charparamb = charparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_charparamc")
	private String charparamc;
	
	@Column(name="CHARPARAMC")
    public String getCharparamc() {
        return charparamc;
    }
	
    public void setCharparamc(String charparamc) {
        this.charparamc = charparamc;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_charparamd")
	private String charparamd;
	
	@Column(name="CHARPARAMD")
    public String getCharparamd() {
        return charparamd;
    }
	
    public void setCharparamd(String charparamd) {
        this.charparamd = charparamd;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_dateparama")
	private Date dateparama;
	
	@Column(name="DATEPARAMA")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparama() {
        return dateparama;
    }
	
    public void setDateparama(Date dateparama) {
        this.dateparama = dateparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_dateparamb")
	private Date dateparamb;
	
	@Column(name="DATEPARAMB")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateparamb() {
        return dateparamb;
    }
	
    public void setDateparamb(Date dateparamb) {
        this.dateparamb = dateparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_numberparama")
	private BigDecimal numberparama;
	
	@Column(name="NUMBERPARAMA")
    public BigDecimal getNumberparama() {
        return numberparama;
    }
	
    public void setNumberparama(BigDecimal numberparama) {
        this.numberparama = numberparama;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_numberparamb")
	private BigDecimal numberparamb;
	
	@Column(name="NUMBERPARAMB")
    public BigDecimal getNumberparamb() {
        return numberparamb;
    }
	
    public void setNumberparamb(BigDecimal numberparamb) {
        this.numberparamb = numberparamb;
    }
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_objparama", complex = true)
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
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_objparamb", complex = true)
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
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_scparama", complex = true)
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
	@BAPCustomComponent(code = "SESECD_1.0.0_emcSituation_EmcSituation_scparamb", complex = true)
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
