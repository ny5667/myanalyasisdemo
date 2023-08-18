package com.supcon.orchid.SESECD.services.alarm.record;

import com.supcon.orchid.SESECD.model.vo.forfront.EventDetailVO;
import com.supcon.orchid.SESECD.model.vo.forfront.ScreenSendVO;
import com.supcon.orchid.support.Result;

import java.io.IOException;
import java.util.List;

public interface CustomSESECDEventForFrontService {

    /**
     * 根據cid列出所有事件
     * @return
     * @param accidentName
     */
    List<EventDetailVO> listEvents(String accidentName) throws  Exception;

    /**
     * 事件详情
     * @param eventId
     * @return
     */
    EventDetailVO getEventDetail(Long eventId);

    /**
     * 新增接警事件
     * @param eventVO
     * @return
     */
    Result<String> addEvent(EventDetailVO eventVO) throws  Exception ;



    /**
     * 截图发送
     * @param screenSendVO
     */
    void screenshotSend(ScreenSendVO screenSendVO) throws IOException;

}
