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
import com.supcon.orchid.SESWssER.entities.SESWssERAccidentClass;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 事故案例.
 */
@javax.persistence.Entity(name=SESECDAccident.JPA_NAME)
@Table(name = SESECDAccident.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_Accident")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDAccidentConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_Accident", modelName = "SESECD.alarmRecord.Accident", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDAccident extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_Accident";
	public static final String DOC_TYPE = "SESECD_alarmRecord_Accident";
	public static final String TABLE_NAME = "ecd_accidents";
	public static final String JPA_NAME = "SESECDAccident";
	public static final String ATTACHMENT_PROPERTY_CODE = "SESECD_1.0.0_alarmRecord_Accident_attachment";



	private String accidentCause ; // 事故原因
	private SESWssERAccidentClass accidentType;
	
	private String attachmentAttachementInfo;
	
	private Document attachmentDocument;
	
    private List<String> attachmentFileAddPaths; //新添加附件的路径
	private List<Long> attachmentFileDeleteIds;//已删除附件ID
	private List<Long> attachmentMultiFileIds;//已添加附件ID
	private List<String> attachmentMultiFileNames;//已添加附件name
	private List<String> attachmentMultiFileIcons;//已添加附件的图标
	private List<String> attachmentMultiFileUrls;//已添加附件的路径
    private List<String>  attachmentMultiCreators;//创建人员
    private List<Date>  attachmentMultiCreateTimes;//创建时间
    private List<String>  attachmentMultiSizes;//大小
    private List<String>  attachmentMultiPreviewTimes;//预览次数
    private List<String>  attachmentMultiDownloadTimes;//下载次数

    	@Transient
        public List<String> getAttachmentMultiCreators(){
             return this.attachmentMultiCreators;
        }

        public void setAttachmentMultiCreators(List<String> attachmentMultiCreators){
            this.attachmentMultiCreators = attachmentMultiCreators;
        }

    	@Transient
        public List<Date> getAttachmentMultiCreateTimes(){
             return this.attachmentMultiCreateTimes;
        }

        public void setAttachmentMultiCreateTimes(List<Date> attachmentMultiCreateTimes){
            this.attachmentMultiCreateTimes = attachmentMultiCreateTimes;
        }

    	@Transient
        public List<String> getAttachmentMultiSizes(){
             return this.attachmentMultiSizes;
        }

        public void setAttachmentMultiSizes(List<String> attachmentMultiSizes){
            this.attachmentMultiSizes = attachmentMultiSizes;
        }

    	@Transient
        public List<String> getAttachmentMultiPreviewTimes(){
             return this.attachmentMultiPreviewTimes;
        }

        public void setAttachmentMultiPreviewTimes(List<String> attachmentMultiPreviewTimes){
            this.attachmentMultiPreviewTimes = attachmentMultiPreviewTimes;
        }

    	@Transient
        public List<String> getAttachmentMultiDownloadTimes(){
             return this.attachmentMultiDownloadTimes;
        }

        public void setAttachmentMultiDownloadTimes(List<String> attachmentMultiDownloadTimes){
            this.attachmentMultiDownloadTimes = attachmentMultiDownloadTimes;
        }

    @Transient
    public List<String> getAttachmentMultiFileUrls(){
        return this.attachmentMultiFileUrls;
    }

    public void setAttachmentMultiFileUrls(List<String> attachmentMultiFileUrls){
        this.attachmentMultiFileUrls = attachmentMultiFileUrls;
    }

	@Transient
	public List<String> getAttachmentFileAddPaths(){
    	return this.attachmentFileAddPaths;
    }

	public void setAttachmentFileAddPaths(List<String> attachmentFileAddPaths){
    		this.attachmentFileAddPaths = attachmentFileAddPaths;
    }

	@Transient
    public List<Long> getAttachmentFileDeleteIds(){
		return this.attachmentFileDeleteIds;
	}

    public void setAttachmentFileDeleteIds(List<Long> attachmentFileDeleteIds){
		this.attachmentFileDeleteIds = attachmentFileDeleteIds;
	}

	@Transient
	public List<Long> getAttachmentMultiFileIds(){
		return this.attachmentMultiFileIds;
	}
	public void setAttachmentMultiFileIds(List<Long> attachmentMultiFileIds){
		this.attachmentMultiFileIds = attachmentMultiFileIds;
	}

	@Transient
	public List<String> getAttachmentMultiFileNames(){
		return this.attachmentMultiFileNames;
	}
	
	public void setAttachmentMultiFileNames(List<String> attachmentMultiFileNames){
		this.attachmentMultiFileNames = attachmentMultiFileNames;
	}

	@Transient
	public List<String> getAttachmentMultiFileIcons(){
		return this.attachmentMultiFileIcons;
	}
	public void setAttachmentMultiFileIcons(List<String> attachmentMultiFileIcons){
		this.attachmentMultiFileIcons = attachmentMultiFileIcons;
	}




	private String casualtySituation ; // 伤亡情况



	private String emergencyResponseProcess ; // 应急处置过程



	private String occurrenceProcess ; // 发生过程



	private String preventionAdvice ; // 防范建议

	
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
	 * 获取事故原因.
	 * 事故原因
	 * @return 事故原因
	 */
	@Column(nullable=true

		,length = 256
		,name="ACCIDENT_CAUSE"
	)
    public String getAccidentCause() {
        return accidentCause;
    }
	/**
	 * 设置事故原因.
	 * @param accidentCause 事故原因
	 */
    public void setAccidentCause(String accidentCause) {
        this.accidentCause = accidentCause;
    }
	@ManyToOne
	@JoinColumn(name = "ACCIDENT_TYPE", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESWssERAccidentClass getAccidentType() {
        return accidentType;
    }
    public void setAccidentType(SESWssERAccidentClass accidentType) {
        this.accidentType = accidentType;
    }
	
	public void setAttachmentAttachementInfo(String attachmentAttachementInfo){
		this.attachmentAttachementInfo = attachmentAttachementInfo;
	}
	
	@Transient
	public String getAttachmentAttachementInfo(){
		return this.attachmentAttachementInfo;
	}
	
	public void setAttachmentDocument(Document attachmentDocument){
		this.attachmentDocument = attachmentDocument;
	}
	
	@Transient
	public Document getAttachmentDocument(){
		return this.attachmentDocument;
	}

	/**
	 * 获取伤亡情况.
	 * 伤亡情况
	 * @return 伤亡情况
	 */
	@Column(nullable=true

		,length = 256
		,name="CASUALTY_SITUATION"
	)
    public String getCasualtySituation() {
        return casualtySituation;
    }
	/**
	 * 设置伤亡情况.
	 * @param casualtySituation 伤亡情况
	 */
    public void setCasualtySituation(String casualtySituation) {
        this.casualtySituation = casualtySituation;
    }
	/**
	 * 获取应急处置过程.
	 * 应急处置过程
	 * @return 应急处置过程
	 */
	@Column(nullable=true

		,length = 256
		,name="EMERGENCY_RESPONSE_PROCESS"
	)
    public String getEmergencyResponseProcess() {
        return emergencyResponseProcess;
    }
	/**
	 * 设置应急处置过程.
	 * @param emergencyResponseProcess 应急处置过程
	 */
    public void setEmergencyResponseProcess(String emergencyResponseProcess) {
        this.emergencyResponseProcess = emergencyResponseProcess;
    }
	/**
	 * 获取发生过程.
	 * 发生过程
	 * @return 发生过程
	 */
	@Column(nullable=true

		,length = 256
		,name="OCCURRENCE_PROCESS"
	)
    public String getOccurrenceProcess() {
        return occurrenceProcess;
    }
	/**
	 * 设置发生过程.
	 * @param occurrenceProcess 发生过程
	 */
    public void setOccurrenceProcess(String occurrenceProcess) {
        this.occurrenceProcess = occurrenceProcess;
    }
	/**
	 * 获取防范建议.
	 * 防范建议
	 * @return 防范建议
	 */
	@Column(nullable=true

		,length = 256
		,name="PREVENTION_ADVICE"
	)
    public String getPreventionAdvice() {
        return preventionAdvice;
    }
	/**
	 * 设置防范建议.
	 * @param preventionAdvice 防范建议
	 */
    public void setPreventionAdvice(String preventionAdvice) {
        this.preventionAdvice = preventionAdvice;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDAccident.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_Accident,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
