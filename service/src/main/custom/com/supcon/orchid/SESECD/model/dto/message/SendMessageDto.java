package com.supcon.orchid.SESECD.model.dto.message;

import java.util.List;

public class SendMessageDto {

    private String msgTitle;
    private String msgContent;

    private String accidentName;
    private String happenTime;
    private String position;
    private String hpnCompany;
    private String description;
    private List<String> codes;

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getAccidentName() {
        return accidentName;
    }

    public void setAccidentName(String accidentName) {
        this.accidentName = accidentName;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHpnCompany() {
        return hpnCompany;
    }

    public void setHpnCompany(String hpnCompany) {
        this.hpnCompany = hpnCompany;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }


}
