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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联态势.
 */
@javax.persistence.Entity(name=SESECDRecorSituation.JPA_NAME)
@Table(name = SESECDRecorSituation.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_RecorSituation")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDRecorSituationConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_RecorSituation", modelName = "SESECD.alarmRecord.RecorSituation", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDRecorSituation extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_RecorSituation";
	public static final String DOC_TYPE = "SESECD_alarmRecord_RecorSituation";
	public static final String TABLE_NAME = "ses_ecd_recor_situations";
	public static final String JPA_NAME = "SESECDRecorSituation";



	private String addresss ; // 发生地点
	private SESECDAlmAlarmRecord almAlarmRecord;



	private String deathPerson ; // 死亡人数



	private String describtion ; // 态势描述

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date occursTime ; // 发生时间



	private String point ; // 坐标



	private String reportPerson ; // 上报人



	private String situationType ; // 态势状态



	private String woundedPerson ; // 受伤人数

	
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
	 * 获取发生地点.
	 * 
	 * @return 发生地点
	 */
	@Column(nullable=true

		,length = 256
		,name="ADDRESSS"
	)
    public String getAddresss() {
        return addresss;
    }
	/**
	 * 设置发生地点.
	 * @param addresss 发生地点
	 */
    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }
	@ManyToOne
	@JoinColumn(name = "ALM_ALARM_RECORD", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDAlmAlarmRecord getAlmAlarmRecord() {
        return almAlarmRecord;
    }
    public void setAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord) {
        this.almAlarmRecord = almAlarmRecord;
    }
	/**
	 * 获取死亡人数.
	 * 
	 * @return 死亡人数
	 */
	@Column(nullable=true

		,length = 256
		,name="DEATH_PERSON"
	)
    public String getDeathPerson() {
        return deathPerson;
    }
	/**
	 * 设置死亡人数.
	 * @param deathPerson 死亡人数
	 */
    public void setDeathPerson(String deathPerson) {
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

		,length = 256
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
	 * 获取上报人.
	 * 
	 * @return 上报人
	 */
	@Column(nullable=true

		,length = 256
		,name="REPORT_PERSON"
	)
    public String getReportPerson() {
        return reportPerson;
    }
	/**
	 * 设置上报人.
	 * @param reportPerson 上报人
	 */
    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }
	/**
	 * 获取态势状态.
	 * 
	 * @return 态势状态
	 */
	@Column(nullable=true

		,length = 256
		,name="SITUATION_TYPE"
	)
    public String getSituationType() {
        return situationType;
    }
	/**
	 * 设置态势状态.
	 * @param situationType 态势状态
	 */
    public void setSituationType(String situationType) {
        this.situationType = situationType;
    }
	/**
	 * 获取受伤人数.
	 * 
	 * @return 受伤人数
	 */
	@Column(nullable=true

		,length = 256
		,name="WOUNDED_PERSON"
	)
    public String getWoundedPerson() {
        return woundedPerson;
    }
	/**
	 * 设置受伤人数.
	 * @param woundedPerson 受伤人数
	 */
    public void setWoundedPerson(String woundedPerson) {
        this.woundedPerson = woundedPerson;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDRecorSituation.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_RecorSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
