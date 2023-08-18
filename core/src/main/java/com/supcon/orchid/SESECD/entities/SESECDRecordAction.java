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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 关联行动.
 */
@javax.persistence.Entity(name=SESECDRecordAction.JPA_NAME)
@Table(name = SESECDRecordAction.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_alarmRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_alarmRecord_RecordAction")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDRecordActionConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_alarmRecord_RecordAction", modelName = "SESECD.alarmRecord.RecordAction", entityCode = "SESECD_1.0.0_alarmRecord", entityName = "SESECD.entityname.randon1576460940310")
public class SESECDRecordAction extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_alarmRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_alarmRecord_RecordAction";
	public static final String DOC_TYPE = "SESECD_alarmRecord_RecordAction";
	public static final String TABLE_NAME = "ses_ecd_record_actions";
	public static final String JPA_NAME = "SESECDRecordAction";



	private String actionAddr ; // 行动地点



	private String actionCatogory ; // 行动分类



	private String actionState ; // 行动状态

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date actionTime ; // 行动时间
	private SESECDAlmAlarmRecord almAlarmRecord;



	private String description ; // 行动描述



	private String ownDepartment ; // 责任单位



	private String ownPerson ; // 责任人



	private String point ; // 坐标



	private String trackRecord ; // 跟踪记录

	
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
	 * 获取行动地点.
	 * 
	 * @return 行动地点
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_ADDR"
	)
    public String getActionAddr() {
        return actionAddr;
    }
	/**
	 * 设置行动地点.
	 * @param actionAddr 行动地点
	 */
    public void setActionAddr(String actionAddr) {
        this.actionAddr = actionAddr;
    }
	/**
	 * 获取行动分类.
	 * 
	 * @return 行动分类
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_CATOGORY"
	)
    public String getActionCatogory() {
        return actionCatogory;
    }
	/**
	 * 设置行动分类.
	 * @param actionCatogory 行动分类
	 */
    public void setActionCatogory(String actionCatogory) {
        this.actionCatogory = actionCatogory;
    }
	/**
	 * 获取行动状态.
	 * 
	 * @return 行动状态
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_STATE"
	)
    public String getActionState() {
        return actionState;
    }
	/**
	 * 设置行动状态.
	 * @param actionState 行动状态
	 */
    public void setActionState(String actionState) {
        this.actionState = actionState;
    }
	/**
	 * 获取行动时间.
	 * 
	 * @return 行动时间
	 */
	@Column(nullable=true

		,name="ACTION_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getActionTime() {
        return actionTime;
    }
	/**
	 * 设置行动时间.
	 * @param actionTime 行动时间
	 */
    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
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
	 * 获取行动描述.
	 * 
	 * @return 行动描述
	 */
	@Column(nullable=true

		,length = 256
		,name="DESCRIPTION"
	)
    public String getDescription() {
        return description;
    }
	/**
	 * 设置行动描述.
	 * @param description 行动描述
	 */
    public void setDescription(String description) {
        this.description = description;
    }
	/**
	 * 获取责任单位.
	 * 
	 * @return 责任单位
	 */
	@Column(nullable=true

		,length = 256
		,name="OWN_DEPARTMENT"
	)
    public String getOwnDepartment() {
        return ownDepartment;
    }
	/**
	 * 设置责任单位.
	 * @param ownDepartment 责任单位
	 */
    public void setOwnDepartment(String ownDepartment) {
        this.ownDepartment = ownDepartment;
    }
	/**
	 * 获取责任人.
	 * 
	 * @return 责任人
	 */
	@Column(nullable=true

		,length = 256
		,name="OWN_PERSON"
	)
    public String getOwnPerson() {
        return ownPerson;
    }
	/**
	 * 设置责任人.
	 * @param ownPerson 责任人
	 */
    public void setOwnPerson(String ownPerson) {
        this.ownPerson = ownPerson;
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
	 * 获取跟踪记录.
	 * 
	 * @return 跟踪记录
	 */
	@Column(nullable=true

		,length = 256
		,name="TRACK_RECORD"
	)
    public String getTrackRecord() {
        return trackRecord;
    }
	/**
	 * 设置跟踪记录.
	 * @param trackRecord 跟踪记录
	 */
    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDRecordAction.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_alarmRecord_RecordAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
