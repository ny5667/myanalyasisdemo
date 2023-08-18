package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Create by lwten on 2022/11/10 下午8:48
 */
@Getter
@Setter
public class BroadcastVO {

    /**
     * 配置ID
     */
    private Long configId;

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
