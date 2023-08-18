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
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警记录.
 */
@javax.persistence.Entity(name=SESECDEcdAlertRecord.JPA_NAME)
@Table(name = SESECDEcdAlertRecord.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_ecdAlertRecord")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord")
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
@XmlSeeAlso({SESECDEcdAlertRecordConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord", modelName = "SESECD.ecdAlertRecord.EcdAlertRecord", entityCode = "SESECD_1.0.0_ecdAlertRecord", entityName = "SESECD.entityname.randon1685597767652")
public class SESECDEcdAlertRecord extends com.supcon.orchid.ec.entities.abstracts.AbstractEcFullEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_ecdAlertRecord";
	public static final String MODEL_CODE = "SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord";
	public static final String DOC_TYPE = "SESECD_ecdAlertRecord_EcdAlertRecord";
	public static final String TABLE_NAME = "ecd_ecd_alert_records";
	public static final String JPA_NAME = "SESECDEcdAlertRecord";



	private String alarmCode ; // 报警编码



	private String alarmContent ; // 报警详情



	private String alarmDeviceName ; // 报警装置名称



	private String alarmLevel ; // 报警等级



	private String alarmName ; // 报警点名



	private String alarmOrigin ; // 报警来源



	private String alarmState ; // 报警状态



	private String alarmTableSource ; // 报警表来源

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date alarmTime ; // 报警产生时间



	private String alarmType ; // 报警类型



	private BigDecimal deviceLocationX ; // 报警装置坐标经度



	private BigDecimal deviceLocationY ; // 报警装置坐标纬度



	private Integer duration ; // 报警持续时间（单位秒）



	private Integer durationdays ; // 持续天数



	private String durationTime ; // 持续时间



	private String endType ; // 恢复类型



	private String endValue ; // 恢复值



	private String hhv ; // 上上限阈值



	private String hv ; // 上限阈值

	@XmlJavaTypeAdapter(DateAdapter.class)


	private Date lifeTime ; // 报警结束时间



	private String lifeValue ; // 解除时阈值



	private String limitValue ; // 报警时阀值



	private String llv ; // 下下限阈值



	private String lv ; // 下限阈值



	private String mapPoint ; // 坐标点



	private BigDecimal realTimeValue ; // 报警值



	private String recordId ; // recordId



	private String unitName ; // 单位名称

	@JsonBackReference("ecdAlertVideoList")
	@JSONField(serialize = false)
	private List<SESECDEcdAlertVideo> ecdAlertVideoList;
	
	public void setEcdAlertVideoList(List<SESECDEcdAlertVideo> ecdAlertVideoList){
		this.ecdAlertVideoList = ecdAlertVideoList;
	}
	@Transient
	public List<SESECDEcdAlertVideo> getEcdAlertVideoList(){
		return ecdAlertVideoList;
	}
	@JsonBackReference("ecdAlertImgList")
	@JSONField(serialize = false)
	private List<SESECDEcdAlertImg> ecdAlertImgList;
	
	public void setEcdAlertImgList(List<SESECDEcdAlertImg> ecdAlertImgList){
		this.ecdAlertImgList = ecdAlertImgList;
	}
	@Transient
	public List<SESECDEcdAlertImg> getEcdAlertImgList(){
		return ecdAlertImgList;
	}
	
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
	 * 获取报警编码.
	 * 
	 * @return 报警编码
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_CODE"
	)
    public String getAlarmCode() {
        return alarmCode;
    }
	/**
	 * 设置报警编码.
	 * @param alarmCode 报警编码
	 */
    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }
	/**
	 * 获取报警详情.
	 * 
	 * @return 报警详情
	 */
	@Column(nullable=true

		,name="ALARM_CONTENT"
	)
    @javax.persistence.Lob
    public String getAlarmContent() {
        return alarmContent;
    }
	/**
	 * 设置报警详情.
	 * @param alarmContent 报警详情
	 */
    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }
	/**
	 * 获取报警装置名称.
	 * 
	 * @return 报警装置名称
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_DEVICE_NAME"
	)
    public String getAlarmDeviceName() {
        return alarmDeviceName;
    }
	/**
	 * 设置报警装置名称.
	 * @param alarmDeviceName 报警装置名称
	 */
    public void setAlarmDeviceName(String alarmDeviceName) {
        this.alarmDeviceName = alarmDeviceName;
    }
	/**
	 * 获取报警等级.
	 * 
	 * @return 报警等级
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_LEVEL"
	)
    public String getAlarmLevel() {
        return alarmLevel;
    }
	/**
	 * 设置报警等级.
	 * @param alarmLevel 报警等级
	 */
    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }
	/**
	 * 获取报警点名.
	 * 
	 * @return 报警点名
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_NAME"
	)
    public String getAlarmName() {
        return alarmName;
    }
	/**
	 * 设置报警点名.
	 * @param alarmName 报警点名
	 */
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }
	/**
	 * 获取报警来源.
	 * 
	 * @return 报警来源
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_ORIGIN"
	)
    public String getAlarmOrigin() {
        return alarmOrigin;
    }
	/**
	 * 设置报警来源.
	 * @param alarmOrigin 报警来源
	 */
    public void setAlarmOrigin(String alarmOrigin) {
        this.alarmOrigin = alarmOrigin;
    }
	/**
	 * 获取报警状态.
	 * 
	 * @return 报警状态
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_STATE"
	)
    public String getAlarmState() {
        return alarmState;
    }
	/**
	 * 设置报警状态.
	 * @param alarmState 报警状态
	 */
    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }
	/**
	 * 获取报警表来源.
	 * 
	 * @return 报警表来源
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_TABLE_SOURCE"
	)
    public String getAlarmTableSource() {
        return alarmTableSource;
    }
	/**
	 * 设置报警表来源.
	 * @param alarmTableSource 报警表来源
	 */
    public void setAlarmTableSource(String alarmTableSource) {
        this.alarmTableSource = alarmTableSource;
    }
	/**
	 * 获取报警产生时间.
	 * 
	 * @return 报警产生时间
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
	 * 设置报警产生时间.
	 * @param alarmTime 报警产生时间
	 */
    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }
	/**
	 * 获取报警类型.
	 * 
	 * @return 报警类型
	 */
	@Column(nullable=true

		,length = 256
		,name="ALARM_TYPE"
	)
    public String getAlarmType() {
        return alarmType;
    }
	/**
	 * 设置报警类型.
	 * @param alarmType 报警类型
	 */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
	/**
	 * 获取报警装置坐标经度.
	 * 
	 * @return 报警装置坐标经度
	 */
	@Column(nullable=true
			,precision=19,scale=6
		,name="DEVICE_LOCATIONX"
	)
    public BigDecimal getDeviceLocationX() {
        return deviceLocationX;
    }
	/**
	 * 设置报警装置坐标经度.
	 * @param deviceLocationX 报警装置坐标经度
	 */
    public void setDeviceLocationX(BigDecimal deviceLocationX) {
        this.deviceLocationX = deviceLocationX;
    }
	/**
	 * 获取报警装置坐标纬度.
	 * 
	 * @return 报警装置坐标纬度
	 */
	@Column(nullable=true
			,precision=19,scale=6
		,name="DEVICE_LOCATIONY"
	)
    public BigDecimal getDeviceLocationY() {
        return deviceLocationY;
    }
	/**
	 * 设置报警装置坐标纬度.
	 * @param deviceLocationY 报警装置坐标纬度
	 */
    public void setDeviceLocationY(BigDecimal deviceLocationY) {
        this.deviceLocationY = deviceLocationY;
    }
	/**
	 * 获取报警持续时间（单位秒）.
	 * 
	 * @return 报警持续时间（单位秒）
	 */
	@Column(nullable=true

		,name="DURATION"
	)
    public Integer getDuration() {
        return duration;
    }
	/**
	 * 设置报警持续时间（单位秒）.
	 * @param duration 报警持续时间（单位秒）
	 */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
	/**
	 * 获取持续天数.
	 * 
	 * @return 持续天数
	 */
	@Column(nullable=true

		,name="DURATIONDAYS"
	)
    public Integer getDurationdays() {
        return durationdays;
    }
	/**
	 * 设置持续天数.
	 * @param durationdays 持续天数
	 */
    public void setDurationdays(Integer durationdays) {
        this.durationdays = durationdays;
    }
	/**
	 * 获取持续时间.
	 * 
	 * @return 持续时间
	 */
	@Column(nullable=true

		,length = 256
		,name="DURATION_TIME"
	)
    public String getDurationTime() {
        return durationTime;
    }
	/**
	 * 设置持续时间.
	 * @param durationTime 持续时间
	 */
    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
	/**
	 * 获取恢复类型.
	 * 
	 * @return 恢复类型
	 */
	@Column(nullable=true

		,length = 256
		,name="END_TYPE"
	)
    public String getEndType() {
        return endType;
    }
	/**
	 * 设置恢复类型.
	 * @param endType 恢复类型
	 */
    public void setEndType(String endType) {
        this.endType = endType;
    }
	/**
	 * 获取恢复值.
	 * 
	 * @return 恢复值
	 */
	@Column(nullable=true

		,length = 256
		,name="END_VALUE"
	)
    public String getEndValue() {
        return endValue;
    }
	/**
	 * 设置恢复值.
	 * @param endValue 恢复值
	 */
    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }
	/**
	 * 获取上上限阈值.
	 * 
	 * @return 上上限阈值
	 */
	@Column(nullable=true

		,length = 256
		,name="HHV"
	)
    public String getHhv() {
        return hhv;
    }
	/**
	 * 设置上上限阈值.
	 * @param hhv 上上限阈值
	 */
    public void setHhv(String hhv) {
        this.hhv = hhv;
    }
	/**
	 * 获取上限阈值.
	 * 
	 * @return 上限阈值
	 */
	@Column(nullable=true

		,length = 256
		,name="HV"
	)
    public String getHv() {
        return hv;
    }
	/**
	 * 设置上限阈值.
	 * @param hv 上限阈值
	 */
    public void setHv(String hv) {
        this.hv = hv;
    }
	/**
	 * 获取报警结束时间.
	 * 
	 * @return 报警结束时间
	 */
	@Column(nullable=true

		,name="LIFE_TIME"
	)
	@XmlTransient
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getLifeTime() {
        return lifeTime;
    }
	/**
	 * 设置报警结束时间.
	 * @param lifeTime 报警结束时间
	 */
    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }
	/**
	 * 获取解除时阈值.
	 * 
	 * @return 解除时阈值
	 */
	@Column(nullable=true

		,length = 256
		,name="LIFE_VALUE"
	)
    public String getLifeValue() {
        return lifeValue;
    }
	/**
	 * 设置解除时阈值.
	 * @param lifeValue 解除时阈值
	 */
    public void setLifeValue(String lifeValue) {
        this.lifeValue = lifeValue;
    }
	/**
	 * 获取报警时阀值.
	 * 
	 * @return 报警时阀值
	 */
	@Column(nullable=true

		,length = 256
		,name="LIMIT_VALUE"
	)
    public String getLimitValue() {
        return limitValue;
    }
	/**
	 * 设置报警时阀值.
	 * @param limitValue 报警时阀值
	 */
    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }
	/**
	 * 获取下下限阈值.
	 * 
	 * @return 下下限阈值
	 */
	@Column(nullable=true

		,length = 256
		,name="LLV"
	)
    public String getLlv() {
        return llv;
    }
	/**
	 * 设置下下限阈值.
	 * @param llv 下下限阈值
	 */
    public void setLlv(String llv) {
        this.llv = llv;
    }
	/**
	 * 获取下限阈值.
	 * 
	 * @return 下限阈值
	 */
	@Column(nullable=true

		,length = 256
		,name="LV"
	)
    public String getLv() {
        return lv;
    }
	/**
	 * 设置下限阈值.
	 * @param lv 下限阈值
	 */
    public void setLv(String lv) {
        this.lv = lv;
    }
	/**
	 * 获取坐标点.
	 * 
	 * @return 坐标点
	 */
	@Column(nullable=true

		,name="MAP_POINT"
	)
    public String getMapPoint() {
        return mapPoint;
    }
	/**
	 * 设置坐标点.
	 * @param mapPoint 坐标点
	 */
    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }
	/**
	 * 获取报警值.
	 * 
	 * @return 报警值
	 */
	@Column(nullable=true
			,precision=19,scale=6
		,name="REAL_TIME_VALUE"
	)
    public BigDecimal getRealTimeValue() {
        return realTimeValue;
    }
	/**
	 * 设置报警值.
	 * @param realTimeValue 报警值
	 */
    public void setRealTimeValue(BigDecimal realTimeValue) {
        this.realTimeValue = realTimeValue;
    }
	/**
	 * 获取recordId.
	 * 
	 * @return recordId
	 */
	@Column(nullable=true

		,length = 256
		,name="RECORD_ID"
	)
    public String getRecordId() {
        return recordId;
    }
	/**
	 * 设置recordId.
	 * @param recordId recordId
	 */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
	/**
	 * 获取单位名称.
	 * 
	 * @return 单位名称
	 */
	@Column(nullable=true

		,length = 256
		,name="UNIT_NAME"
	)
    public String getUnitName() {
        return unitName;
    }
	/**
	 * 设置单位名称.
	 * @param unitName 单位名称
	 */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEcdAlertRecord.class.getName();
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
