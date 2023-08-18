package com.supcon.orchid.SESECD.VO.broadcast.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 广播分页获取对象
 */
@Data
@NoArgsConstructor
@ToString
public class HikvisionBroadcastPageVoRequest {
    /**
     * 广播点名称
     */
    private String audioChannelName;

    /**
     * 广播点唯一标识
     */
    private String audioChannelIndexCode;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 区域唯一标识
     */
    private String regionIndexCode;

    /**
     * 广播区域唯一标识
     */
    private String audioArea;

    /**
     * 分页页码
     */
    private String pageNo;

    /**
     * 分页大小
     */
    private String pageSize;

    /**
     * 是否包含下级区域
     */
    private Integer hasChild;

    /**
     * 是否广播主机绒布数据
     */
    private Integer isMachineSync;
}
