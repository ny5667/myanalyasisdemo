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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 录像回放.
 */
@javax.persistence.Entity(name=SESECDCctvRecord.JPA_NAME)
@Table(name = SESECDCctvRecord.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_CctvRecord")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDCctvRecordConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_CctvRecord", modelName = "SESECD.modelname.randon1599803983106", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDCctvRecord extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_CctvRecord";
	public static final String DOC_TYPE = "SESECD_alarmRecord_CctvRecord";
	public static final String TABLE_NAME = "ses_ecd_alarm_cctvrecord";
	public static final String JPA_NAME = "SESECDCctvRecord";



	private String duration ; // 时长



	private String hls ; // 地址



	private String important ; // 重要性



	private String name ; // 通道号
	private SESECDAlmAlarmRecord recordId;



	private String startAt ; // 开始时间

	
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
	 * 获取时长.
	 * 
	 * @return 时长
	 */
	@Column(nullable=true

		,length = 256
		,name="DURATION"
	)
    public String getDuration() {
        return duration;
    }
	/**
	 * 设置时长.
	 * @param duration 时长
	 */
    public void setDuration(String duration) {
        this.duration = duration;
    }
	/**
	 * 获取地址.
	 * 
	 * @return 地址
	 */
	@Column(nullable=true

		,length = 256
		,name="HLS"
	)
    public String getHls() {
        return hls;
    }
	/**
	 * 设置地址.
	 * @param hls 地址
	 */
    public void setHls(String hls) {
        this.hls = hls;
    }
	/**
	 * 获取重要性.
	 * 
	 * @return 重要性
	 */
	@Column(nullable=true

		,length = 256
		,name="IMPORTANT"
	)
    public String getImportant() {
        return important;
    }
	/**
	 * 设置重要性.
	 * @param important 重要性
	 */
    public void setImportant(String important) {
        this.important = important;
    }
	/**
	 * 获取通道号.
	 * 
	 * @return 通道号
	 */
	@Column(nullable=true

		,length = 256
		,name="NAME"
	)
    public String getName() {
        return name;
    }
	/**
	 * 设置通道号.
	 * @param name 通道号
	 */
    public void setName(String name) {
        this.name = name;
    }
	@ManyToOne
	@JoinColumn(name = "RECORD_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDAlmAlarmRecord getRecordId() {
        return recordId;
    }
    public void setRecordId(SESECDAlmAlarmRecord recordId) {
        this.recordId = recordId;
    }
	/**
	 * 获取开始时间.
	 * 
	 * @return 开始时间
	 */
	@Column(nullable=true

		,length = 256
		,name="START_AT"
	)
    public String getStartAt() {
        return startAt;
    }
	/**
	 * 设置开始时间.
	 * @param startAt 开始时间
	 */
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDCctvRecord.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_CctvRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
