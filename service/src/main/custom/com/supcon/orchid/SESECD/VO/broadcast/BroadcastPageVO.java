package com.supcon.orchid.SESECD.VO.broadcast;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广播分页获取对象
 */
@Data
@NoArgsConstructor
public class BroadcastPageVO {
    /**
     * 广播点名称
     */
    private String audioChannelName;

    /**
     * 广播点唯一标识
     */
    private String audioChannelIndexCode;

    /**
     * 区域唯一标识
     */
    private String regionIndexCode;

    /**
     * 分页页码
     */
    private String pageNo;

    /**
     * 分页大小
     */
    private String pageSize;

}
