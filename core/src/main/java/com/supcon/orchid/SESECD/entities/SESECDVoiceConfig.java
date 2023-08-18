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
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Position;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 语音配置.
 */
@javax.persistence.Entity(name=SESECDVoiceConfig.JPA_NAME)
@Table(name = SESECDVoiceConfig.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_voiceConfig")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_voiceConfig_VoiceConfig")
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
@XmlSeeAlso({SESECDVoiceConfigConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_voiceConfig_VoiceConfig", modelName = "SESECD.voiceConfig.VoiceConfig", entityCode = "SESECD_1.0.0_voiceConfig", entityName = "SESECD.entityname.randon1676006291416")
public class SESECDVoiceConfig extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_voiceConfig";
	public static final String MODEL_CODE = "SESECD_1.0.0_voiceConfig_VoiceConfig";
	public static final String DOC_TYPE = "SESECD_voiceConfig_VoiceConfig";
	public static final String TABLE_NAME = "ecd_voice_configs";
	public static final String JPA_NAME = "SESECDVoiceConfig";



	private String contentText ; // 自定义语言文字



	private Boolean customContnet = null; // 是否自定义语音文本内容



	private String systemVoiceIndex ; // 融合通讯音频文件序号



	private String voiceRemark ; // 备注

	@JsonBackReference("voiceMemberList")
	@JSONField(serialize = false)
	private List<SESECDVoiceMember> voiceMemberList;
	
	public void setVoiceMemberList(List<SESECDVoiceMember> voiceMemberList){
		this.voiceMemberList = voiceMemberList;
	}
	@Transient
	public List<SESECDVoiceMember> getVoiceMemberList(){
		return voiceMemberList;
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
	 * 获取自定义语言文字.
	 * 
	 * @return 自定义语言文字
	 */
	@Column(nullable=true

		,name="CONTENT_TEXT"
	)
    @javax.persistence.Lob
    public String getContentText() {
        return contentText;
    }
	/**
	 * 设置自定义语言文字.
	 * @param contentText 自定义语言文字
	 */
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
	/**
	 * 获取是否自定义语音文本内容.
	 * 
	 * @return 是否自定义语音文本内容
	 */
	@Column(nullable=true

		,name="CUSTOM_CONTNET"
	)
    public Boolean getCustomContnet() {
        return customContnet;
    }
	/**
	 * 设置是否自定义语音文本内容.
	 * @param customContnet 是否自定义语音文本内容
	 */
    public void setCustomContnet(Boolean customContnet) {
        this.customContnet = customContnet;
    }
	/**
	 * 获取融合通讯音频文件序号.
	 * 
	 * @return 融合通讯音频文件序号
	 */
	@Column(nullable=true

		,length = 256
		,name="SYSTEM_VOICE_INDEX"
	)
    public String getSystemVoiceIndex() {
        return systemVoiceIndex;
    }
	/**
	 * 设置融合通讯音频文件序号.
	 * @param systemVoiceIndex 融合通讯音频文件序号
	 */
    public void setSystemVoiceIndex(String systemVoiceIndex) {
        this.systemVoiceIndex = systemVoiceIndex;
    }
	/**
	 * 获取备注.
	 * 
	 * @return 备注
	 */
	@Column(nullable=true

		,length = 256
		,name="VOICE_REMARK"
	)
    public String getVoiceRemark() {
        return voiceRemark;
    }
	/**
	 * 设置备注.
	 * @param voiceRemark 备注
	 */
    public void setVoiceRemark(String voiceRemark) {
        this.voiceRemark = voiceRemark;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDVoiceConfig.class.getName();
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
	private String voiceConfigIdEabSectionIdDeleteIds;
	private String voiceConfigIdEabSectionIdAddIds;
	private String voiceConfigIdEabSectionIdmultiselectIDs;
	private String voiceConfigIdEabSectionIdmultiselectNames;

	@Transient
	public String getVoiceConfigIdEabSectionIdDeleteIds(){
		return this.voiceConfigIdEabSectionIdDeleteIds;
	}

	public void setVoiceConfigIdEabSectionIdDeleteIds(String deleteIds){
		this.voiceConfigIdEabSectionIdDeleteIds = deleteIds;
	}

	@Transient
	public String getVoiceConfigIdEabSectionIdmultiselectIDs(){
		return this.voiceConfigIdEabSectionIdmultiselectIDs;
	}

	public void setVoiceConfigIdEabSectionIdmultiselectIDs(String multiselectIDs){
		this.voiceConfigIdEabSectionIdmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getVoiceConfigIdEabSectionIdmultiselectNames(){
		return this.voiceConfigIdEabSectionIdmultiselectNames;
	}

	public void setVoiceConfigIdEabSectionIdmultiselectNames(String multiselectNames){
		this.voiceConfigIdEabSectionIdmultiselectNames = multiselectNames;
	}

	@Transient
	public String getVoiceConfigIdEabSectionIdAddIds(){
		return this.voiceConfigIdEabSectionIdAddIds;
	}

	public void setVoiceConfigIdEabSectionIdAddIds(String addIds){
		this.voiceConfigIdEabSectionIdAddIds = addIds;
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_voiceConfig_VoiceConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
