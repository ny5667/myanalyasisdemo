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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 配置参数项.
 */
@javax.persistence.Entity(name=SESECDParamOption.JPA_NAME)
@Table(name = SESECDParamOption.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_paramConfig")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_paramConfig_ParamOption")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDParamOptionConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_paramConfig_ParamOption", modelName = "SESECD.paramConfig.ParamOption", entityCode = "SESECD_1.0.0_paramConfig", entityName = "SESECD.entityname.randon1685348810371")
public class SESECDParamOption extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_paramConfig";
	public static final String MODEL_CODE = "SESECD_1.0.0_paramConfig_ParamOption";
	public static final String DOC_TYPE = "SESECD_paramConfig_ParamOption";
	public static final String TABLE_NAME = "ecd_param_options";
	public static final String JPA_NAME = "SESECDParamOption";



	private String optionCode ; // 编码



	private String optionName ; // 名称



	private String optionValue ; // 配置值
	private SESECDEcdParamConfig paramConfig;



	private String remark ; // 备注

	
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
	 * 获取编码.
	 * 
	 * @return 编码
	 */
	@Column(nullable=true

		,length = 256
		,name="OPTION_CODE"
	)
    public String getOptionCode() {
        return optionCode;
    }
	/**
	 * 设置编码.
	 * @param optionCode 编码
	 */
    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }
	/**
	 * 获取名称.
	 * 
	 * @return 名称
	 */
	@Column(nullable=true

		,length = 256
		,name="OPTION_NAME"
	)
    public String getOptionName() {
        return optionName;
    }
	/**
	 * 设置名称.
	 * @param optionName 名称
	 */
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
	/**
	 * 获取配置值.
	 * 
	 * @return 配置值
	 */
	@Column(nullable=true

		,length = 256
		,name="OPTION_VALUE"
	)
    public String getOptionValue() {
        return optionValue;
    }
	/**
	 * 设置配置值.
	 * @param optionValue 配置值
	 */
    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
	@ManyToOne
	@JoinColumn(name = "PARAM_CONFIG", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDEcdParamConfig getParamConfig() {
        return paramConfig;
    }
    public void setParamConfig(SESECDEcdParamConfig paramConfig) {
        this.paramConfig = paramConfig;
    }
	/**
	 * 获取备注.
	 * 
	 * @return 备注
	 */
	@Column(nullable=true

		,length = 256
		,name="REMARK"
	)
    public String getRemark() {
        return remark;
    }
	/**
	 * 设置备注.
	 * @param remark 备注
	 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDParamOption.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_paramConfig_ParamOption,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
