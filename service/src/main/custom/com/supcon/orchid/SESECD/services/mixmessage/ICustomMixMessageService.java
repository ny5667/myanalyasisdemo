package com.supcon.orchid.SESECD.services.mixmessage;

import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;

/**
 * <p>
 *     融合通信接口
 * </p>
 *
 * @author lufengdong
 * @create 2023-04-28 10:58
 */
public interface ICustomMixMessageService {
    /**
     * 发送消息通知
     * @param requestDTO
     */
    void sendMsg(MixMessageReqDTO requestDTO);
}
