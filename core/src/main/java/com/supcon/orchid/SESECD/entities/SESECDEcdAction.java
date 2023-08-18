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
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急行动.
 */
@javax.persistence.Entity(name=SESECDEcdAction.JPA_NAME)
@Table(name = SESECDEcdAction.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_ecdPanel")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_ecdPanel_EcdAction")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEcdActionConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_ecdPanel_EcdAction", modelName = "SESECD.ecdPanel.EcdAction", entityCode = "SESECD_1.0.0_ecdPanel", entityName = "SESECD.entityname.randon1578365471050")
public class SESECDEcdAction extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_ecdPanel";
	public static final String MODEL_CODE = "SESECD_1.0.0_ecdPanel_EcdAction";
	public static final String DOC_TYPE = "SESECD_ecdPanel_EcdAction";
	public static final String TABLE_NAME = "ses_ecd_ecd_actions";
	public static final String JPA_NAME = "SESECDEcdAction";



	private String actionAddr ; // 行动地点
	private SESECDEmcAction actionId;
	private SystemCode actionState
;// 行动状态

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date beginTime ; // 实际开始时间



	private String description ; // 行动描述
	private SESECDAlmAlarmRecord eventId;



	private String owners ; // 负责人

	
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
	@OneToOne
	@JoinColumn(name = "ACTION_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESECDEmcAction getActionId() {
        return actionId;
    }
    public void setActionId(SESECDEmcAction actionId) {
        this.actionId = actionId;
    }
	/**
	 * 获取行动状态.
	 * 
	 * @return 行动状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACTION_STATE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getActionState() {
		return actionState;
	}
	/**
	 * 设置行动状态.
	 * @param actionState 行动状态
	 */
	public void setActionState(SystemCode actionState) {
		this.actionState = (SystemCode) actionState;
	}
	/**
	 * 获取实际开始时间.
	 * 
	 * @return 实际开始时间
	 */
	@Column(nullable=true

		,name="BEGIN_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBeginTime() {
        return beginTime;
    }
	/**
	 * 设置实际开始时间.
	 * @param beginTime 实际开始时间
	 */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
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
	 * 获取负责人.
	 * 
	 * @return 负责人
	 */
	@Column(nullable=true

		,length = 256
		,name="OWNERS"
	)
    public String getOwners() {
        return owners;
    }
	/**
	 * 设置负责人.
	 * @param owners 负责人
	 */
    public void setOwners(String owners) {
        this.owners = owners;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEcdAction.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_ecdPanel_EcdAction,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
