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
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警记录.
 */
@javax.persistence.Entity(name=SESECDAlertRecord.JPA_NAME)
@Table(name = SESECDAlertRecord.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alertRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alertRecord_AlertRecord")
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
@XmlSeeAlso({SESECDAlertRecordConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alertRecord_AlertRecord", modelName = "SESECD.alertRecord.AlertRecord", entityCode = "SESECD_1.0.0_alertRecord", entityName = "SESECD.entityname.randon1616139979666")
public class SESECDAlertRecord extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alertRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alertRecord_AlertRecord";
	public static final String DOC_TYPE = "SESECD_alertRecord_AlertRecord";
	public static final String TABLE_NAME = "ses_ecd_alert_records";
	public static final String JPA_NAME = "SESECDAlertRecord";
	private SystemCode alarmStaus
;// 报警状态

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date alarmTime ; // 报警时间



	private String alarmValue ; // 报警内容



	private String code ; // 报警编码



	private BigDecimal lowerLimit ; // 下限



	private String tagAddress ; // 位置



	private String tagName ; // 报警装置
	private SystemCode tagType
;// 报警来源



	private BigDecimal upperLimit ; // 上限



	private String warnId ; // 告警编号

	
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
	 * 获取报警状态.
	 * 
	 * @return 报警状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ALARM_STAUS", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getAlarmStaus() {
		return alarmStaus;
	}
	/**
	 * 设置报警状态.
	 * @param alarmStaus 报警状态
	 */
	public void setAlarmStaus(SystemCode alarmStaus) {
		this.alarmStaus = (SystemCode) alarmStaus;
	}
	/**
	 * 获取报警时间.
	 * 
	 * @return 报警时间
	 */
	@Column(nullable=true

		,name="ALARM_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getAlarmTime() {
        return alarmTime;
    }
	/**
	 * 设置报警时间.
	 * @param alarmTime 报警时间
	 */
    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
	/**
	 * 获取报警内容.
	 * 
	 * @return 报警内容
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_VALUE"
	)
    public String getAlarmValue() {
        return alarmValue;
    }
	/**
	 * 设置报警内容.
	 * @param alarmValue 报警内容
	 */
    public void setAlarmValue(String alarmValue) {
        this.alarmValue = alarmValue;
    }
	/**
	 * 获取报警编码.
	 * 
	 * @return 报警编码
	 */
	@Column(nullable=true

		,length = 256
		,name="CODE"
	)
    public String getCode() {
        return code;
    }
	/**
	 * 设置报警编码.
	 * @param code 报警编码
	 */
    public void setCode(String code) {
        this.code = code;
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
	 * 获取报警装置.
	 * 
	 * @return 报警装置
	 */
	@Column(nullable=true

		,length = 256
		,name="TAG_NAME"
	)
    public String getTagName() {
        return tagName;
    }
	/**
	 * 设置报警装置.
	 * @param tagName 报警装置
	 */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
	/**
	 * 获取报警来源.
	 * 
	 * @return 报警来源
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="TAG_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getTagType() {
		return tagType;
	}
	/**
	 * 设置报警来源.
	 * @param tagType 报警来源
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
	/**
	 * 获取告警编号.
	 * 
	 * @return 告警编号
	 */
	@Column(nullable=true

		,length = 256
		,name="WARN_ID"
	)
    public String getWarnId() {
        return warnId;
    }
	/**
	 * 设置告警编号.
	 * @param warnId 告警编号
	 */
    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDAlertRecord.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
