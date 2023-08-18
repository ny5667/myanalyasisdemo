package com.supcon.orchid.SESECD.services.notify;

import com.supcon.orchid.SesCommonFunc.dto.WebSocketParamDTO;

/**
 * 消息通知顶层接口
 * <p>
 *     定义顶层通知接口
 * </p>
 */
public interface NotifyStrategy {

    /**
     * 消息通知集成接口
     * @param msgModelDTO
     * @return
     */
    String notify(MsgModelDTO msgModelDTO);


    /**
     * ws通知
     * @param param
     */
    default void sendWebSocket(WebSocketParamDTO param) {
        throw new UnsupportedOperationException("no apply this operation");
    }

}
