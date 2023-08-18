package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.PositionDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 报警记录.
 */
public class SESECDEcdAlertRecordDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcFullEntityDTO {
	private static final long serialVersionUID = 1L;
	private String alarmCode ; // 报警编码
	private String alarmContent ; // 报警详情
	private String alarmDeviceName ; // 报警装置名称
	private String alarmLevel ; // 报警等级
	private String alarmName ; // 报警点名
	private String alarmOrigin ; // 报警来源
	private String alarmState ; // 报警状态
	private String alarmTableSource ; // 报警表来源
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
	private Date lifeTime ; // 报警结束时间
	private String lifeValue ; // 解除时阈值
	private String limitValue ; // 报警时阀值
	private String llv ; // 下下限阈值
	private String lv ; // 下限阈值
	private String mapPoint ; // 坐标点
	private BigDecimal realTimeValue ; // 报警值
	private String recordId ; // recordId
	private String unitName ; // 单位名称

	private List<SESECDEcdAlertVideoDTO> ecdAlertVideoList;

	public void setEcdAlertVideoList(List<SESECDEcdAlertVideoDTO> ecdAlertVideoList){
		this.ecdAlertVideoList = ecdAlertVideoList;
	}
	public List<SESECDEcdAlertVideoDTO> getEcdAlertVideoList(){
		return ecdAlertVideoList;
	}
	private List<SESECDEcdAlertImgDTO> ecdAlertImgList;

	public void setEcdAlertImgList(List<SESECDEcdAlertImgDTO> ecdAlertImgList){
		this.ecdAlertImgList = ecdAlertImgList;
	}
	public List<SESECDEcdAlertImgDTO> getEcdAlertImgList(){
		return ecdAlertImgList;
	}

	private Document document;

	private String bapAttachmentInfo;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}


    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    @javax.persistence.Lob
    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmDeviceName() {
        return alarmDeviceName;
    }

    public void setAlarmDeviceName(String alarmDeviceName) {
        this.alarmDeviceName = alarmDeviceName;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmOrigin() {
        return alarmOrigin;
    }

    public void setAlarmOrigin(String alarmOrigin) {
        this.alarmOrigin = alarmOrigin;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getAlarmTableSource() {
        return alarmTableSource;
    }

    public void setAlarmTableSource(String alarmTableSource) {
        this.alarmTableSource = alarmTableSource;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public BigDecimal getDeviceLocationX() {
        return deviceLocationX;
    }

    public void setDeviceLocationX(BigDecimal deviceLocationX) {
        this.deviceLocationX = deviceLocationX;
    }

    public BigDecimal getDeviceLocationY() {
        return deviceLocationY;
    }

    public void setDeviceLocationY(BigDecimal deviceLocationY) {
        this.deviceLocationY = deviceLocationY;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationdays() {
        return durationdays;
    }

    public void setDurationdays(Integer durationdays) {
        this.durationdays = durationdays;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getHhv() {
        return hhv;
    }

    public void setHhv(String hhv) {
        this.hhv = hhv;
    }

    public String getHv() {
        return hv;
    }

    public void setHv(String hv) {
        this.hv = hv;
    }

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }

    public String getLifeValue() {
        return lifeValue;
    }

    public void setLifeValue(String lifeValue) {
        this.lifeValue = lifeValue;
    }

    public String getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }

    public String getLlv() {
        return llv;
    }

    public void setLlv(String llv) {
        this.llv = llv;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }

    public BigDecimal getRealTimeValue() {
        return realTimeValue;
    }

    public void setRealTimeValue(BigDecimal realTimeValue) {
        this.realTimeValue = realTimeValue;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}


	private String virtualId;
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	@Override
	protected String _getEntityName() {
		return null;
	}

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_ecdAlertRecord_EcdAlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
