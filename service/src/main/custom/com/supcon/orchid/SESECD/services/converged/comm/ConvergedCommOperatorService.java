package com.supcon.orchid.SESECD.services.converged.comm;

import com.supcon.orchid.SESECD.model.dto.common.*;

import java.util.List;

/**
 * 融合通信 基本操作接口
 * @Create by lwten on 2022/11/9 下午5:01
 */
public interface ConvergedCommOperatorService {

    /**
     * 供应商
     * @return
     */
    String getProvider();

    /**
     * 电话呼叫
     * @param commCallPhone
     */
    void callPhone(CommCallPhoneDTO commCallPhone);

    /**
     * 查询广播组
     * @param commReq
     * @return
     */
    List<BroadcastGroupDTO> listBroadcastGroup(CommReqDTO commReq);

    /**
     * 操作广播
     * @param broadcast
     */
    void operatorBroadcast(BroadcastDTO broadcast);

    /**
     * 消息通知
     * @param msgNotify
     */
    void notifyMsg(MsgNotifyDTO msgNotify);
}
