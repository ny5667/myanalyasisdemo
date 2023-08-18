package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 语音呼叫
 * @Create by lwten on 2022/11/10 下午6:54
 */
@Getter
@Setter
public class CallPhoneVO {

    /**
     * 融合通信配置ID ，可为空，若为空，自动识别符合要求的配置项
     */
    private Long id;

    /**
     * 终端号码
     */
    private String terminalNo;

    /**
     * 目标电话 可以多个
     */
    private List<String> destCaller;
}
