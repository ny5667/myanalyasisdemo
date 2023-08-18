package com.supcon.orchid.SESECD.services.forfront;

import com.supcon.orchid.SESECD.model.vo.forfront.ChangeLogVO;
import com.supcon.orchid.SESECD.model.vo.forfront.EventDynamicTrendVO;

import java.util.List;

public interface CustomSESECDForFrontService {

    /**
     * 事件动态 : 应急行动+应急态势
     *
     * @param eventId
     * @return
     */
    List<EventDynamicTrendVO> listEventDynamicTrends(Long eventId);


    /**
     * 右侧动态面板操作记录
     *
     * @param eventId
     * @return
     */
    List<ChangeLogVO> changeLog(Long eventId);

}
