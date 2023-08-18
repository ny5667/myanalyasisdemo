package com.supcon.orchid.SESECD.model.dto.alarm;

import java.math.BigDecimal;
import java.util.Date;

public class SituationInfoDto {

    private Long eventId;
    private String describtion;
    private Date occursTime;
    private String position;
    private Long stateId;
    private Long reportPerson;
    private String reportPersonName;
    // 受伤人数
    private Integer woundedPerson;
    // 死亡人数
    private Integer deathPerson;
    // 图标地址
    private String iconObjUrl;
    // 图标名称
    private String iconObjName;

    private String situationTypeCode;
    private String situationTypeName;

    public String getSituationTypeCode() {
        return situationTypeCode;
    }

    public void setSituationTypeCode(String situationTypeCode) {
        this.situationTypeCode = situationTypeCode;
    }

    public String getSituationTypeName() {
        return situationTypeName;
    }

    public void setSituationTypeName(String situationTypeName) {
        this.situationTypeName = situationTypeName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public Date getOccursTime() {
        return occursTime;
    }

    public void setOccursTime(Date occursTime) {
        this.occursTime = occursTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(Long reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public Integer getWoundedPerson() {
        return woundedPerson;
    }

    public void setWoundedPerson(Integer woundedPerson) {
        this.woundedPerson = woundedPerson;
    }

    public Integer getDeathPerson() {
        return deathPerson;
    }

    public void setDeathPerson(Integer deathPerson) {
        this.deathPerson = deathPerson;
    }

    public String getIconObjUrl() {
        return iconObjUrl;
    }

    public void setIconObjUrl(String iconObjUrl) {
        this.iconObjUrl = iconObjUrl;
    }

    public String getIconObjName() {
        return iconObjName;
    }

    public void setIconObjName(String iconObjName) {
        this.iconObjName = iconObjName;
    }
}
