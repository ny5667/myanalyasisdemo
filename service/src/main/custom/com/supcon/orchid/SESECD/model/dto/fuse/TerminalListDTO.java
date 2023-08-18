package com.supcon.orchid.SESECD.model.dto.fuse;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-22 10:36
 * @description GetTerminals接口返回数据对象，根据多个终端值获取数据
 **/
public class TerminalListDTO {
    private String cmd;
    private Integer action;
    private Integer error;
    private List<TerminalDTO> terminalist;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public List<TerminalDTO> getTerminalist() {
        return terminalist;
    }

    public void setTerminalist(List<TerminalDTO> terminalist) {
        this.terminalist = terminalist;
    }
}
