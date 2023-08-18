package com.supcon.orchid.SESECD.model.dto.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 融合通信请求基础类
 * @Create by lwten on 2022/11/9 下午8:20
 */
@Getter
@Setter
public class CommReqDTO {

    private String ip;

    private int port;
}
