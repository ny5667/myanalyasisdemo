package com.supcon.orchid.SESECD.model.dto.convergedcomm;

import lombok.Getter;
import lombok.Setter;

/**
 * 融合通信参数
 * @Create by lwten on 2022/11/10 下午4:11
 */
@Getter
@Setter
public class SignalConfigDTO {

    private String ip;

    private int port;

    private String sourceCaller;

    private String signalProvider;
}
