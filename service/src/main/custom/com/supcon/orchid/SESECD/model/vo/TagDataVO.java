package com.supcon.orchid.SESECD.model.vo;

/**
 * @author: xulong2
 * @create: 2021-03-30 10:28
 * @description 标准数据服务位号接收数据
 **/
public class TagDataVO {

    private String Name;
    private String Value;
    private String dwQuality;
    private String tTimeStamp;
    private String bResult;
    private String hTag;
    private String uiResult;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDwQuality() {
        return dwQuality;
    }

    public void setDwQuality(String dwQuality) {
        this.dwQuality = dwQuality;
    }

    public String gettTimeStamp() {
        return tTimeStamp;
    }

    public void settTimeStamp(String tTimeStamp) {
        this.tTimeStamp = tTimeStamp;
    }

    public String getbResult() {
        return bResult;
    }

    public void setbResult(String bResult) {
        this.bResult = bResult;
    }

    public String gethTag() {
        return hTag;
    }

    public void sethTag(String hTag) {
        this.hTag = hTag;
    }

    public String getUiResult() {
        return uiResult;
    }

    public void setUiResult(String uiResult) {
        this.uiResult = uiResult;
    }
}
