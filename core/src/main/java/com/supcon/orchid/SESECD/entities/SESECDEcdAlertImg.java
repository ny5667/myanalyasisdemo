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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警设备现场图片.
 */
@javax.persistence.Entity(name=SESECDEcdAlertImg.JPA_NAME)
@Table(name = SESECDEcdAlertImg.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_ecdAlertRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_ecdAlertRecord_EcdAlertImg")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEcdAlertImgConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_ecdAlertRecord_EcdAlertImg", modelName = "SESECD.ecdAlertRecord.EcdAlertImg", entityCode = "SESECD_1.0.0_ecdAlertRecord", entityName = "SESECD.entityname.randon1685597767652")
public class SESECDEcdAlertImg extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_ecdAlertRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_ecdAlertRecord_EcdAlertImg";
	public static final String DOC_TYPE = "SESECD_ecdAlertRecord_EcdAlertImg";
	public static final String TABLE_NAME = "ecd_ecd_alert_imgs";
	public static final String JPA_NAME = "SESECDEcdAlertImg";
	private SESECDEcdAlertRecord alertRecord;



	private String url ; // 图片地址

	
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
	
	@ManyToOne
	@JoinColumn(name = "ALERT_RECORD", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDEcdAlertRecord getAlertRecord() {
        return alertRecord;
    }
    public void setAlertRecord(SESECDEcdAlertRecord alertRecord) {
        this.alertRecord = alertRecord;
    }
	/**
	 * 获取图片地址.
	 * 
	 * @return 图片地址
	 */
	@Column(nullable=true

		,name="URL"
	)
    @javax.persistence.Lob
    public String getUrl() {
        return url;
    }
	/**
	 * 设置图片地址.
	 * @param url 图片地址
	 */
    public void setUrl(String url) {
        this.url = url;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEcdAlertImg.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertImg,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
