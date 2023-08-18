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
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Position;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 接口配置.
 */
@javax.persistence.Entity(name=SESECDSignalConfig.JPA_NAME)
@Table(name = SESECDSignalConfig.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_signalConfig")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_signalConfig_SignalConfig")
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
@XmlSeeAlso({SESECDSignalConfigConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_signalConfig_SignalConfig", modelName = "SESECD.signalConfig.SignalConfig", entityCode = "SESECD_1.0.0_signalConfig", entityName = "SESECD.entityname.randon1616052129981")
public class SESECDSignalConfig extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_signalConfig";
	public static final String MODEL_CODE = "SESECD_1.0.0_signalConfig_SignalConfig";
	public static final String DOC_TYPE = "SESECD_signalConfig_SignalConfig";
	public static final String TABLE_NAME = "ses_ecd_signal_configs";
	public static final String JPA_NAME = "SESECDSignalConfig";



	private Integer alarmTime ; // 重复报警间隔(秒)



	private Boolean enable = null; // 是否启用



	private String ip ; // IP



	private Integer port ; // 端口
	private SystemCode signalProvider
;// 融合通信供应商
	private SystemCode signalType
;// 接口类型



	private Integer sourceTerminal ; // 源终端号码

	
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
	 * 获取重复报警间隔(秒).
	 * 
	 * @return 重复报警间隔(秒)
	 */
	@Column(nullable=true

		,name="ALARM_TIME"
	)
    public Integer getAlarmTime() {
        return alarmTime;
    }
	/**
	 * 设置重复报警间隔(秒).
	 * @param alarmTime 重复报警间隔(秒)
	 */
    public void setAlarmTime(Integer alarmTime) {
        this.alarmTime = alarmTime;
    }
	/**
	 * 获取是否启用.
	 * 
	 * @return 是否启用
	 */
	@Column(nullable=true

		,name="ENABLE"
	)
    public Boolean getEnable() {
        return enable;
    }
	/**
	 * 设置是否启用.
	 * @param enable 是否启用
	 */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
	/**
	 * 获取IP.
	 * 
	 * @return IP
	 */
	@Column(nullable=true

		,length = 256
		,name="IP"
	)
    public String getIp() {
        return ip;
    }
	/**
	 * 设置IP.
	 * @param ip IP
	 */
    public void setIp(String ip) {
        this.ip = ip;
    }
	/**
	 * 获取端口.
	 * 
	 * @return 端口
	 */
	@Column(nullable=true

		,name="PORT"
	)
    public Integer getPort() {
        return port;
    }
	/**
	 * 设置端口.
	 * @param port 端口
	 */
    public void setPort(Integer port) {
        this.port = port;
    }
	/**
	 * 获取融合通信供应商.
	 * 
	 * @return 融合通信供应商
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="SIGNAL_PROVIDER", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getSignalProvider() {
		return signalProvider;
	}
	/**
	 * 设置融合通信供应商.
	 * @param signalProvider 融合通信供应商
	 */
	public void setSignalProvider(SystemCode signalProvider) {
		this.signalProvider = (SystemCode) signalProvider;
	}
	/**
	 * 获取接口类型.
	 * 
	 * @return 接口类型
	 */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity = SystemCode.class)
	@JoinColumn(name="SIGNAL_TYPE", nullable=true)
	@Fetch(FetchMode.SELECT)
	public SystemCode getSignalType() {
		return signalType;
	}
	/**
	 * 设置接口类型.
	 * @param signalType 接口类型
	 */
	public void setSignalType(SystemCode signalType) {
		this.signalType = (SystemCode) signalType;
	}
	/**
	 * 获取源终端号码.
	 * 
	 * @return 源终端号码
	 */
	@Column(nullable=true

		,name="SOURCE_TERMINAL"
	)
    public Integer getSourceTerminal() {
        return sourceTerminal;
    }
	/**
	 * 设置源终端号码.
	 * @param sourceTerminal 源终端号码
	 */
    public void setSourceTerminal(Integer sourceTerminal) {
        this.sourceTerminal = sourceTerminal;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDSignalConfig.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_signalConfig_SignalConfig,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
