package com.supcon.orchid.SESECD.model.dto.fuse;

/**
 * @author: xulong2
 * @create: 2021-03-22 10:38
 * @description 终端数据
 **/
public class TerminalDTO {

    private String terminalid;      //终端编码
    private String terminalname;    //终端名称
    private Integer keyindex;        //调度键号 从1开始，0表示终端不存在
    private String terminalip;
    private Integer terminalvol;
    private Integer terminaltype;
    private Integer state;          //终端状态（在线 = 1,脱机 = -1）
    private Integer talktype;
    private Integer iscaller;       //未通话=0，发起方=1，接收方=2
    private String oppid;

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getTerminalname() {
        return terminalname;
    }

    public void setTerminalname(String terminalname) {
        this.terminalname = terminalname;
    }

    public Integer getKeyindex() {
        return keyindex;
    }

    public void setKeyindex(Integer keyindex) {
        this.keyindex = keyindex;
    }

    public String getTerminalip() {
        return terminalip;
    }

    public void setTerminalip(String terminalip) {
        this.terminalip = terminalip;
    }

    public Integer getTerminalvol() {
        return terminalvol;
    }

    public void setTerminalvol(Integer terminalvol) {
        this.terminalvol = terminalvol;
    }

    public Integer getTerminaltype() {
        return terminaltype;
    }

    public void setTerminaltype(Integer terminaltype) {
        this.terminaltype = terminaltype;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTalktype() {
        return talktype;
    }

    public void setTalktype(Integer talktype) {
        this.talktype = talktype;
    }

    public Integer getIscaller() {
        return iscaller;
    }

    public void setIscaller(Integer iscaller) {
        this.iscaller = iscaller;
    }

    public String getOppid() {
        return oppid;
    }

    public void setOppid(String oppid) {
        this.oppid = oppid;
    }
}
