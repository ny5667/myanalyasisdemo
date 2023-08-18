package com.supcon.orchid.SESECD.model.dto.common.shenxun;

import lombok.Getter;
import lombok.Setter;

/**
 * @Create by lwten on 2022/11/10 下午8:15
 */
@Getter
@Setter
public class SendBroadcastDTO {

    private String cmd="sms";

    /**
     * send=发起，stop=停止
     */
    private String action;

    /**
     * 广播组
     */
    private String mobile;

    private int rule=8;

    private int systemVoice=-1;

    /**
     * 文本内容
     */
    private String vcontent;
}
