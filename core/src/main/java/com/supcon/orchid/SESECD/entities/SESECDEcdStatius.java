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
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急态势.
 */
@javax.persistence.Entity(name=SESECDEcdStatius.JPA_NAME)
@Table(name = SESECDEcdStatius.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_ecdPanel")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_ecdPanel_EcdStatius")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEcdStatiusConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_ecdPanel_EcdStatius", modelName = "SESECD.ecdPanel.EcdStatius", entityCode = "SESECD_1.0.0_ecdPanel", entityName = "SESECD.entityname.randon1578365471050")
public class SESECDEcdStatius extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_ecdPanel";
	public static final String MODEL_CODE = "SESECD_1.0.0_ecdPanel_EcdStatius";
	public static final String DOC_TYPE = "SESECD_ecdPanel_EcdStatius";
	public static final String TABLE_NAME = "ses_ecd_ecd_statiuss";
	public static final String JPA_NAME = "SESECDEcdStatius";



	private String describtion ; // 态势描述
	private SESECDAlmAlarmRecord eventId;

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date occursTime ; // 发生时间



	private String position ; // 发生地点
	private Staff reportPerson;
	private SESECDEmcSituation stateId;

	
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
	@OneToOne
	@JoinColumn(name = "EVENT_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESECDAlmAlarmRecord getEventId() {
        return eventId;
    }
    public void setEventId(SESECDAlmAlarmRecord eventId) {
        this.eventId = eventId;
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
	 * 获取发生地点.
	 * 
	 * @return 发生地点
	 */
	@Column(nullable=true

		,length = 256
		,name="POSITION"
	)
    public String getPosition() {
        return position;
    }
	/**
	 * 设置发生地点.
	 * @param position 发生地点
	 */
    public void setPosition(String position) {
        this.position = position;
    }
	@OneToOne
	@JoinColumn(name = "REPORT_PERSON", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	
						
    public Staff getReportPerson() {
        return reportPerson;
    }
    public void setReportPerson(Staff reportPerson) {
        this.reportPerson = reportPerson;
    }
	@OneToOne
	@JoinColumn(name = "STATE_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESECDEmcSituation getStateId() {
        return stateId;
    }
    public void setStateId(SESECDEmcSituation stateId) {
        this.stateId = stateId;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEcdStatius.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_ecdPanel_EcdStatius,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
