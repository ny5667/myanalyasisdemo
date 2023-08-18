package com.supcon.orchid.SESECD.model.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 融合通信 电话呼叫
 * @Create by lwten on 2022/11/9 下午7:46
 */
@Getter
@Setter
public class CommCallPhoneDTO extends CommReqDTO {


    /**
     * 源终端号码
     */
    private String sourceCaller;

    /**
     * 目标终端号码
     */
    private List<String> destCaller;
}
