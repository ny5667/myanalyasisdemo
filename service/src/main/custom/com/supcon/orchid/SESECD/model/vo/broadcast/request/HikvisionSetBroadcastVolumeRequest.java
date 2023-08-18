package com.supcon.orchid.SESECD.model.vo.broadcast.request;

import lombok.Data;

/**
 * 海康调节广播点音量
 */
@Data
public class HikvisionSetBroadcastVolumeRequest {

    /**
     * 广播点唯一标识
     */
    private String audioPointIndexCode;

    /**
     * 广播点音量0-10
     */
    private Integer volume;
}
