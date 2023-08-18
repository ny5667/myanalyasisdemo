package com.supcon.orchid.SESECD.strategy.AbstratcStrategy;


import com.supcon.orchid.SESECD.model.vo.broadcast.*;

import java.util.List;

/**
 * 发送广播相关接口
 */
public interface BroadcastStrategy {

    /**
     * 获取企业类型
     * @return
     */
    String getType();

    /**
     * 获取H5实施广播URL
     * @param broadcastVO
     * @return
     */
    BroadcastH5URLResponse broadcastURLs();

    /**
     * 开启广播
     * @param startBroadcastVO
     */
    void startBroadcast(StartBroadcastVO startBroadcastVO);

    /**
     * 关闭广播
     */
    void stopBroadcast();

    /**
     * 调节广播音量
     */
    void setAudioVolumeParam(SetBroadcastVolumeVO broadcastVolumeVO);

    /**
     * 分页获取广播接口
     * @param pageVO
     */
    List<BroadcastInfoDTO> fetchAudioChannel(BroadcastPageVO pageVO);
}
