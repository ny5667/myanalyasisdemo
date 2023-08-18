package com.supcon.orchid.SESECD.VO.broadcast;

public class HikvisionConstants {
    /**
     * 分页获取广播点接口地址
     */
    public static final String BROADCAST_POINT_PAGE = "/api/ibas/resource/v1/fetchAudioChannel";

    /**
     * 获取H5实时广播URL
     */
    public static final String H5_URL = "/api/ibas/media/v1/broadcastURLs";

    /**
     * 开启广播接口；
     * @return
     */
    public static final String START_BROADCAST = "";

    /**
     * 关闭广播接口
     * @return
     */
    public static final String STOP_BROADCAST = "";

    /**
     * 调节广播接口
     * @return
     */
    public static final String SET_VOLUME = "/api/ibas/resource/v1/setAudioVolumeParam";
}
