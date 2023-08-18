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
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_addrBook_EmerExperts,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急专家.
 */
@javax.persistence.Entity(name=SESECDEmerExperts.JPA_NAME)
@Table(name = SESECDEmerExperts.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_addrBook")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_addrBook_EmerExperts")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEmerExpertsConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_addrBook_EmerExperts", modelName = "SESECD.addrBook.EmerExperts", entityCode = "SESECD_1.0.0_addrBook", entityName = "SESECD.entityname.randon1578313293886")
public class SESECDEmerExperts extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_addrBook";
	public static final String MODEL_CODE = "SESECD_1.0.0_addrBook_EmerExperts";
	public static final String DOC_TYPE = "SESECD_addrBook_EmerExperts";
	public static final String TABLE_NAME = "ecd_emer_expertss";
	public static final String JPA_NAME = "SESECDEmerExperts";



	private String accidentType ; // 擅长事故类型



	private String expertField ; // 专业领域



	private String expertType ; // 专家类型



	private String name ; // 姓名



	private String phone ; // 手机号码



	private String speciality ; // 专业特长



	private String tel ; // 电话



	private String workplace ; // 工作单位

	
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
	 * 获取擅长事故类型.
	 * 
	 * @return 擅长事故类型
	 */
	@Column(nullable=true

		,length = 256
		,name="ACCIDENT_TYPE"
	)
    public String getAccidentType() {
        return accidentType;
    }
	/**
	 * 设置擅长事故类型.
	 * @param accidentType 擅长事故类型
	 */
    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }
	/**
	 * 获取专业领域.
	 * 
	 * @return 专业领域
	 */
	@Column(nullable=true

		,length = 256
		,name="EXPERT_FIELD"
	)
    public String getExpertField() {
        return expertField;
    }
	/**
	 * 设置专业领域.
	 * @param expertField 专业领域
	 */
    public void setExpertField(String expertField) {
        this.expertField = expertField;
    }
	/**
	 * 获取专家类型.
	 * 
	 * @return 专家类型
	 */
	@Column(nullable=true

		,length = 256
		,name="EXPERT_TYPE"
	)
    public String getExpertType() {
        return expertType;
    }
	/**
	 * 设置专家类型.
	 * @param expertType 专家类型
	 */
    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }
	/**
	 * 获取姓名.
	 * 
	 * @return 姓名
	 */
	@Column(nullable=true

		,length = 256
		,name="NAME"
	)
    public String getName() {
        return name;
    }
	/**
	 * 设置姓名.
	 * @param name 姓名
	 */
    public void setName(String name) {
        this.name = name;
    }
	/**
	 * 获取手机号码.
	 * 
	 * @return 手机号码
	 */
	@Column(nullable=true

		,length = 256
		,name="PHONE"
	)
    public String getPhone() {
        return phone;
    }
	/**
	 * 设置手机号码.
	 * @param phone 手机号码
	 */
    public void setPhone(String phone) {
        this.phone = phone;
    }
	/**
	 * 获取专业特长.
	 * 
	 * @return 专业特长
	 */
	@Column(nullable=true

		,length = 256
		,name="SPECIALITY"
	)
    public String getSpeciality() {
        return speciality;
    }
	/**
	 * 设置专业特长.
	 * @param speciality 专业特长
	 */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
	/**
	 * 获取电话.
	 * 
	 * @return 电话
	 */
	@Column(nullable=true

		,length = 256
		,name="TEL"
	)
    public String getTel() {
        return tel;
    }
	/**
	 * 设置电话.
	 * @param tel 电话
	 */
    public void setTel(String tel) {
        this.tel = tel;
    }
	/**
	 * 获取工作单位.
	 * 
	 * @return 工作单位
	 */
	@Column(nullable=true

		,length = 256
		,name="WORKPLACE"
	)
    public String getWorkplace() {
        return workplace;
    }
	/**
	 * 设置工作单位.
	 * @param workplace 工作单位
	 */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEmerExperts.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_addrBook_EmerExperts,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
