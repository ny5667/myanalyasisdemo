package com.supcon.orchid.SESECD.services.situation;

import com.supcon.orchid.SESECD.model.vo.forfront.EventSituationVO;

import java.util.List;

public interface CustomSESECDSituationForFrontService {


    /**
     * 列出态势
     *
     * @param eventId
     * @return
     */
    List<EventSituationVO> listEventSituation(Long eventId);

    /**
     * 添加态势
     * @param eventSituationVO
     * @return
     */
    Integer addSituation(EventSituationVO eventSituationVO) throws Exception;


    /**
     * 修改态势
     * @param eventSituationVO
     * @return
     */
    Integer updateSituation(EventSituationVO eventSituationVO) throws Exception;


    /**
     * 删除态势
     * @param situationId
     * @return
     */
    Integer deleteSituation(Long situationId);


    /**
     * 发布态势
     * @param situationId
     * @return
     */
    Integer releaseSituation(Long situationId);

}
