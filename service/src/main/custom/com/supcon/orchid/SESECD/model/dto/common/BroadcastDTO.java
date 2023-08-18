package com.supcon.orchid.SESECD.model.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 广播信息
 * @Create by lwten on 2022/11/10 下午8:05
 */
@Getter
@Setter
public class BroadcastDTO extends CommReqDTO {

    /**
     * 1发起， 0停止
     */
    private int action;

    /**
     * 文本内容
     */
    private String content;

    /**
     * 广播组
     */
    private List<String> groupIds;
}
