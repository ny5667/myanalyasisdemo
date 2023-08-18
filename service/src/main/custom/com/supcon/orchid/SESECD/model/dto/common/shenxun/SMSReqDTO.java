package com.supcon.orchid.SESECD.model.dto.common.shenxun;

import lombok.Getter;
import lombok.Setter;

/**
 * @Create by lwten on 2023/2/7 下午8:32
 */
@Getter
@Setter
public class SMSReqDTO {

    private String cmd="sms";
    private String action="send";
    private String mobile;
    private int rule=1;
    private int systemVoice;
    private String vcontent;
    private int playCnt;
    private String eventId;

}
