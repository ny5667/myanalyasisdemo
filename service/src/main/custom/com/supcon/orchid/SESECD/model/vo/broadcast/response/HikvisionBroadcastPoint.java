package com.supcon.orchid.SESECD.model.vo.broadcast.response;

import lombok.Data;

@Data
public class HikvisionBroadcastPoint {

    /**
     * 广播点名称
     */
    private String audioChannelName;

    /**
     * 广播点唯一标识
     */
    private String audioChannelIndexCode;

    /**
     * 广播点类型
     */
    private String audioChannelType;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 通道号
     */
    private String channelNo;

    /**
     * 关联设备唯一标识
     */
    private String parentDevIndexCode;

    /**
     * 关联设备协议类型
     */
    private String parentDevTreaty;

    /**
     * 区域唯一标识
     */
    private String regionIndexCode;

    /**
     * 广播区域唯一标识
     */
    private String audioArea;


    /**
     * 广播点在线状态
     */
    private Integer state;


    /**
     * 区域名称
     */
    private String regionName;

    /**
     * 广播点音量
     */
    private String volume;

    /**
     * 是否广播主机数据
     */
    private String isMachineSync;

}
