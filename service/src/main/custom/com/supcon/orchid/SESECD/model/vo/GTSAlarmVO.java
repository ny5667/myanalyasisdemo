package com.supcon.orchid.SESECD.model.vo;

/**
 * @author: xulong2
 * @create: 2021-03-31 16:08
 * @description GTS报警VO
 **/
public class GTSAlarmVO {
    private String warnId;  //告警编号
    private String transId; //传输装置编号
    private String panelId; //消防设施编号
    private String buildingId;  //建、构筑物编号
    private String projectId;  //联网用户编号
    private String centerId;    //监控中心编号
    private String happenTime;  //发生时间,格式：2018-07-18 12:12:12
    private String warnContent; //报警信息json,告警详情,可自由定义数量，至少一组
    private String warnType;    //告警类型
    private String devId;       //部件id,实际发生告警的设备部件
    private String producerCode;    //平台厂商编码

    public String getWarnId() {
        return warnId;
    }

    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }
}
