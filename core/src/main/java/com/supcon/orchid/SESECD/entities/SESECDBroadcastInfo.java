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
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 广播配置信息.
 * 广播配置信息
 */
@javax.persistence.Entity(name=SESECDBroadcastInfo.JPA_NAME)
@Table(name = SESECDBroadcastInfo.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_broadcastIntercom")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_broadcastIntercom_BroadcastInfo")
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
@XmlSeeAlso({SESECDBroadcastInfoConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_broadcastIntercom_BroadcastInfo", modelName = "SESECD.broadcastIntercom.BroadcastInfo", entityCode = "SESECD_1.0.0_broadcastIntercom", entityName = "SESECD.entityname.randon1687656570445")
public class SESECDBroadcastInfo extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_broadcastIntercom";
	public static final String MODEL_CODE = "SESECD_1.0.0_broadcastIntercom_BroadcastInfo";
	public static final String DOC_TYPE = "SESECD_broadcastIntercom_BroadcastInfo";
	public static final String TABLE_NAME = "ecd_broadcast_infos";
	public static final String JPA_NAME = "SESECDBroadcastInfo";



	private String coordinate ; // 广播坐标



	private String deviceCode ; // 设备编码



	private String deviceLocation ; // 设备位置



	private String deviceName ; // 设备名称



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
	 * 获取广播坐标.
	 * 
	 * @return 广播坐标
	 */
	@Column(nullable=true

		,name="COORDINATE"
	)
    public String getCoordinate() {
        return coordinate;
    }
	/**
	 * 设置广播坐标.
	 * @param coordinate 广播坐标
	 */
    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
	/**
	 * 获取设备编码.
	 * 
	 * @return 设备编码
	 */
	@Column(nullable=true

		,length = 256
		,name="DEVICE_CODE"
	)
    public String getDeviceCode() {
        return deviceCode;
    }
	/**
	 * 设置设备编码.
	 * @param deviceCode 设备编码
	 */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
	/**
	 * 获取设备位置.
	 * 
	 * @return 设备位置
	 */
	@Column(nullable=true

		,length = 256
		,name="DEVICE_LOCATION"
	)
    public String getDeviceLocation() {
        return deviceLocation;
    }
	/**
	 * 设置设备位置.
	 * @param deviceLocation 设备位置
	 */
    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }
	/**
	 * 获取设备名称.
	 * 
	 * @return 设备名称
	 */
	@Column(nullable=true

		,length = 256
		,name="DEVICE_NAME"
	)
    public String getDeviceName() {
        return deviceName;
    }
	/**
	 * 设置设备名称.
	 * @param deviceName 设备名称
	 */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
	/**
	 * 获取备注.
	 * 
	 * @return 备注
	 */
	@Column(nullable=true

		,name="REMARK"
	)
    @javax.persistence.Lob
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
		return SESECDBroadcastInfo.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_broadcastIntercom_BroadcastInfo,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
