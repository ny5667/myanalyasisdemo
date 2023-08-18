package com.supcon.orchid.SESECD.model.vo.broadcast;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class BroadcastVO {

    /**
     * 优先级 （数字1-15），数字越大优先级越高
     */
    private Integer priority = 15;

    /**
     * 音频流编码格式： “MP3”：1；  “G.722":2
     */
    private Integer encode =1;

    /**
     * 采样率 对广播设备进行实施广播：3；对视频设备兑奖通道进行广播：1；
     */
    private Integer sample =3;

    /**
     * 协议：ws协议； wss协议
     */
    private String protocol ="ws";

    /**
     * 音量
     */
    private Integer volume =8;
}
