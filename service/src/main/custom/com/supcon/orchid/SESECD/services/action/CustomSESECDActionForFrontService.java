package com.supcon.orchid.SESECD.services.action;

import com.supcon.orchid.SESECD.model.vo.forfront.EventActionVO;
import com.supcon.orchid.SESECD.model.vo.forfront.TrackVO;

import java.util.List;

public interface CustomSESECDActionForFrontService {


    /**
     * 列出应急行动
     *
     * @param eventId
     * @return
     */
    List<EventActionVO> listEventAction(Long eventId);

    /**
     * 新增应急行动接
     *
     * @param actionVO
     * @return
     */
    Integer addAction(EventActionVO actionVO) throws Exception;


    /**
     * 修改应急行动
     *
     * @param actionVO
     * @return
     */
    Integer updateAction(EventActionVO actionVO) throws Exception;


    /**
     * 删除应急行动
     *
     * @param actionId
     * @return
     */
    Integer deleteAction(Long actionId) throws Exception;

    /**
     * 仅更新更急行动
     * @param trackVO
     */
    void track(TrackVO trackVO);

}
