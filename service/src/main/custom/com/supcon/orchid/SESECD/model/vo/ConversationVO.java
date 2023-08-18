package com.supcon.orchid.SESECD.model.vo;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-18 15:41
 * @description 融合通信通话VO
 **/
public class ConversationVO {

    private int action;     //通话类型
    private String sourcaller;      //源终端机号码
    private List<String> destcalled;    //呼叫目标机号码

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
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
}
