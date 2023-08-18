package com.supcon.orchid.SESECD.services.mixmessage;

import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;

public interface CustomSESECDSendMessageService {


    /**
     * 应急事件 发送消息后记录消息
     *
     * @param requestDTO
     */
    void customAfterSendingMsg(MixMessageReqDTO requestDTO);

}
