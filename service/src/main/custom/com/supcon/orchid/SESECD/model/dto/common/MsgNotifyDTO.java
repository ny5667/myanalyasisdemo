package com.supcon.orchid.SESECD.model.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Create by lwten on 2023/2/7 下午6:49
 */
@Getter
@Setter
public class MsgNotifyDTO extends CommReqDTO  {


    /**
     * 消息接送方
     */
    private List<String> destCaller;

    /**
     * 系统语言序号 -1 表示自定义
     */
    private int systemVoiceIndex;

    /**
     * 自定义语言文字
     */
    private String content;

    /**
     * 播放次数
     */
    private int playbackTime =1;

}
