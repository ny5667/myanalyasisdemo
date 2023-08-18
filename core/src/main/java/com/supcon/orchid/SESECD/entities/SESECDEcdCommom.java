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
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 指令.
 */
@javax.persistence.Entity(name=SESECDEcdCommom.JPA_NAME)
@Table(name = SESECDEcdCommom.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_ecdPanel")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_ecdPanel_EcdCommom")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEcdCommomConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_ecdPanel_EcdCommom", modelName = "SESECD.ecdPanel.EcdCommom", entityCode = "SESECD_1.0.0_ecdPanel", entityName = "SESECD.entityname.randon1578365471050")
public class SESECDEcdCommom extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_ecdPanel";
	public static final String MODEL_CODE = "SESECD_1.0.0_ecdPanel_EcdCommom";
	public static final String DOC_TYPE = "SESECD_ecdPanel_EcdCommom";
	public static final String TABLE_NAME = "ses_ecd_ecd_commoms";
	public static final String JPA_NAME = "SESECDEcdCommom";



	private String actionAddress ; // 行动地点
	private SystemCode actionCatogory
;// 行动分类



	private String actionDescription ; // 行动描述



	private String actionName ; // 行动名称
	private SESECDAlarmAction commomId;
	private SystemCode commomState
;// 状态
	private SESWssEPEmergencyPlan emcPlanId;
	private SESECDAlmAlarmRecord eventId;



	private String instLayer ; // 坐标



	private Boolean isMapPoint = null; // 是否已录入坐标



	private String isMapPointTxt ; // 是否已录入坐标



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
		,name="ACTION_ADDRESS"
	)
    public String getActionAddress() {
        return actionAddress;
    }
	/**
	 * 设置行动地点.
	 * @param actionAddress 行动地点
	 */
    public void setActionAddress(String actionAddress) {
        this.actionAddress = actionAddress;
    }
	/**
	 * 获取行动分类.
	 * 
	 * @return 行动分类
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="ACTION_CATOGORY", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getActionCatogory() {
		return actionCatogory;
	}
	/**
	 * 设置行动分类.
	 * @param actionCatogory 行动分类
	 */
	public void setActionCatogory(SystemCode actionCatogory) {
		this.actionCatogory = (SystemCode) actionCatogory;
	}
	/**
	 * 获取行动描述.
	 * 
	 * @return 行动描述
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_DESCRIPTION"
	)
    public String getActionDescription() {
        return actionDescription;
    }
	/**
	 * 设置行动描述.
	 * @param actionDescription 行动描述
	 */
    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
	/**
	 * 获取行动名称.
	 * 
	 * @return 行动名称
	 */
	@Column(nullable=true

		,length = 256
		,name="ACTION_NAME"
	)
    public String getActionName() {
        return actionName;
    }
	/**
	 * 设置行动名称.
	 * @param actionName 行动名称
	 */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
	@OneToOne
	@JoinColumn(name = "COMMOM_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESECDAlarmAction getCommomId() {
        return commomId;
    }
    public void setCommomId(SESECDAlarmAction commomId) {
        this.commomId = commomId;
    }
	/**
	 * 获取状态.
	 * 
	 * @return 状态
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="COMMOM_STATE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getCommomState() {
		return commomState;
	}
	/**
	 * 设置状态.
	 * @param commomState 状态
	 */
	public void setCommomState(SystemCode commomState) {
		this.commomState = (SystemCode) commomState;
	}
	@OneToOne
	@JoinColumn(name = "EMC_PLAN_ID", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	
						
    public SESWssEPEmergencyPlan getEmcPlanId() {
        return emcPlanId;
    }
    public void setEmcPlanId(SESWssEPEmergencyPlan emcPlanId) {
        this.emcPlanId = emcPlanId;
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
	 * 获取坐标.
	 * 
	 * @return 坐标
	 */
	@Column(nullable=true

		,name="INST_LAYER"
	)
    public String getInstLayer() {
        return instLayer;
    }
	/**
	 * 设置坐标.
	 * @param instLayer 坐标
	 */
    public void setInstLayer(String instLayer) {
        this.instLayer = instLayer;
    }
	/**
	 * 获取是否已录入坐标.
	 * 
	 * @return 是否已录入坐标
	 */
	@Column(nullable=true

		,name="IS_MAP_POINT"
	)
    public Boolean getIsMapPoint() {
        return isMapPoint;
    }
	/**
	 * 设置是否已录入坐标.
	 * @param isMapPoint 是否已录入坐标
	 */
    public void setIsMapPoint(Boolean isMapPoint) {
        this.isMapPoint = isMapPoint;
    }
	/**
	 * 获取是否已录入坐标.
	 * 
	 * @return 是否已录入坐标
	 */
	@Column(nullable=true

		,length = 256
		,name="IS_MAP_POINT_TXT"
	)
    public String getIsMapPointTxt() {
        return isMapPointTxt;
    }
	/**
	 * 设置是否已录入坐标.
	 * @param isMapPointTxt 是否已录入坐标
	 */
    public void setIsMapPointTxt(String isMapPointTxt) {
        this.isMapPointTxt = isMapPointTxt;
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
		return SESECDEcdCommom.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
