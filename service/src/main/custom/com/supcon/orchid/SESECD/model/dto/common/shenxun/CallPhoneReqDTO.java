package com.supcon.orchid.SESECD.model.dto.common.shenxun;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 申讯 语音呼叫 请求
 * @Create by lwten on 2022/11/9 下午8:27
 */
@Getter
@Setter
public class CallPhoneReqDTO {

    /**
     * 指令
     */
    private String cmd="terminalOperator";

    /**
     * 拨号/组呼(会议) = 1
     * 通话中止 =  2
     * 通话保持 = 3
     * 通话取回 = 4
     * 通话转接/三方通话 = 5
     * 通话强拆= 6
     * 通话强插= 7
     * 通话监听= 8
     */
    private int action=1;

    /**
     * 源终端号码
     */
    private String sourCaller;

    /**
     * 目标终端号码
     */
    List<String> destCalled;

}
