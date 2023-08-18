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
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警装置配置.
 */
@javax.persistence.Entity(name=SESECDTagConfig.JPA_NAME)
@Table(name = SESECDTagConfig.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_tagConfig")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_tagConfig_TagConfig")
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
@XmlSeeAlso({SESECDTagConfigConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_tagConfig_TagConfig", modelName = "SESECD.tagConfig.TagConfig", entityCode = "SESECD_1.0.0_tagConfig", entityName = "SESECD.entityname.randon1616138863441")
public class SESECDTagConfig extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_tagConfig";
	public static final String MODEL_CODE = "SESECD_1.0.0_tagConfig_TagConfig";
	public static final String DOC_TYPE = "SESECD_tagConfig_TagConfig";
	public static final String TABLE_NAME = "ses_ecd_tag_configs";
	public static final String JPA_NAME = "SESECDTagConfig";



	private BigDecimal lowerLimit ; // 下限



	private String tagAddress ; // 位置


    @AuditBusinessKey
	private String tagName ; // 监听装置(位号)
	private SystemCode tagType
;// 报警类型



	private BigDecimal upperLimit ; // 上限

	
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
	 * 获取下限.
	 * 
	 * @return 下限
	 */
	@Column(nullable=true
			,precision=19,scale=6
		,name="LOWER_LIMIT"
	)
    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }
	/**
	 * 设置下限.
	 * @param lowerLimit 下限
	 */
    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
	/**
	 * 获取位置.
	 * 
	 * @return 位置
	 */
	@Column(nullable=true

		,length = 256
		,name="TAG_ADDRESS"
	)
    public String getTagAddress() {
        return tagAddress;
    }
	/**
	 * 设置位置.
	 * @param tagAddress 位置
	 */
    public void setTagAddress(String tagAddress) {
        this.tagAddress = tagAddress;
    }
	/**
	 * 获取监听装置(位号).
	 * 
	 * @return 监听装置(位号)
	 */
	@Column(

		length = 64
		,name="TAG_NAME"
	)
	@Index(name="index_TagConfig_tagName")	
    public String getTagName() {
        return tagName;
    }
	/**
	 * 设置监听装置(位号).
	 * @param tagName 监听装置(位号)
	 */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
	/**
	 * 获取报警类型.
	 * 
	 * @return 报警类型
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="TAG_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getTagType() {
		return tagType;
	}
	/**
	 * 设置报警类型.
	 * @param tagType 报警类型
	 */
	public void setTagType(SystemCode tagType) {
		this.tagType = (SystemCode) tagType;
	}
	/**
	 * 获取上限.
	 * 
	 * @return 上限
	 */
	@Column(nullable=true
			,precision=19,scale=6
		,name="UPPER_LIMIT"
	)
    public BigDecimal getUpperLimit() {
        return upperLimit;
    }
	/**
	 * 设置上限.
	 * @param upperLimit 上限
	 */
    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDTagConfig.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_tagConfig_TagConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
