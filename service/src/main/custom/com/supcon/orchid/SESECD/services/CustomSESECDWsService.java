package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.model.dto.message.SendMessageDto;

public interface CustomSESECDWsService {

    /**
     * 获取消息标题和内容
     *
     * @param id
     * @return
     */
    SendMessageDto getMsgDataByEventId(Long id);

    /**
     * 向应急通讯小组成员推送应急指挥的消息
     *
     * @param sendMessageDto
     * @return
     */
    @Deprecated
    String sendMsgToPerson(SendMessageDto sendMessageDto);

}
