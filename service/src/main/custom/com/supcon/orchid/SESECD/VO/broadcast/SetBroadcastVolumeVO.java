package com.supcon.orchid.SESECD.VO.broadcast;

import lombok.Data;

import java.util.List;

/**
 * 设置媒体音量
 */
@Data
public class SetBroadcastVolumeVO {
    /**
     * 音量，设置区间1-10
     */
    private Integer volume;

    /**
     * 广播点标识
     */
    private List<String> indexCodes;

}
