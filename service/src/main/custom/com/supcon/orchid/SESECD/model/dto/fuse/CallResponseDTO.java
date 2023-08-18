package com.supcon.orchid.SESECD.model.dto.fuse;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-18 17:06
 * @description 融合通信呼叫返回信息
 **/
public class CallResponseDTO {
    private String cmd;
    private String action;
    private Integer error;
    private String sourcaller;
    private List<String> destcalled;
    private String errorex;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getSourcaller() {
        return sourcaller;
    }

    public void setSourcaller(String sourcaller) {
        this.sourcaller = sourcaller;
    }

    public List<String> getDestcalled() {
        return destcalled;
    }

    public void setDestcalled(List<String> destcalled) {
        this.destcalled = destcalled;
    }

    public String getErrorex() {
        return errorex;
    }

    public void setErrorex(String errorex) {
        this.errorex = errorex;
    }
}
