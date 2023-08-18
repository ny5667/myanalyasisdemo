package com.supcon.orchid.SESECD.DTO;

import java.math.BigDecimal;

/**
 * @author: xulong2
 * @create: 2021-03-12 10:05
 * @description
 **/
public class AlarmApiDto {
    private Long incidentId;
    private Long alarmId;
    private String accidentName;
    private Long alarmPersonId;
    private Long receiverId;
    private Long hpmDepartmentId;
    private String description;
    private Long happenTime;
    private String mapPoint;
    private String position;
    private BigDecimal lon;
    private BigDecimal lat;
    private BigDecimal hei;

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public Long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Long alarmId) {
        this.alarmId = alarmId;
    }

    public String getAccidentName() {
        return accidentName;
    }

    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }

    public Long getAlarmPersonId() {
        return alarmPersonId;
    }

    public void setAlarmPersonId(Long alarmPersonId) {
        this.alarmPersonId = alarmPersonId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getHpmDepartmentId() {
        return hpmDepartmentId;
    }

    public void setHpmDepartmentId(Long hpmDepartmentId) {
        this.hpmDepartmentId = hpmDepartmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(Long happenTime) {
        this.happenTime = happenTime;
    }

    public String getMapPoint() {
        return mapPoint;
    }

    public void setMapPoint(String mapPoint) {
        this.mapPoint = mapPoint;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getHei() {
        return hei;
    }

    public void setHei(BigDecimal hei) {
        this.hei = hei;
    }
}