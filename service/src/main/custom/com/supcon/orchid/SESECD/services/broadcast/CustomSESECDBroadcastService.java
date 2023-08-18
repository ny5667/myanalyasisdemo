package com.supcon.orchid.SESECD.services.broadcast;


import com.supcon.orchid.SESECD.model.vo.broadcast.*;

public interface CustomSESECDBroadcastService {

    /**
     * 用海康获取H5实时广播URL接口
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
     * 停止广播
     */
    void stopBroadcast();

    /**
     * 设置广播音量
     * @param broadcastVolumeVO
     */
    void setAudioVolumeParam(SetBroadcastVolumeVO broadcastVolumeVO);

    /**
     * 获取广播详情列表\
     * @return
     */
    BroadcastPageResult getBroadcastInfoList(BroadcastPageVO pageVO);
}
