package com.supcon.orchid.SESECD.services.situation;

import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.support.Result;

import java.util.List;

/**
 * 应急态势
 */
public interface CustomSESECDEmcSituationService {


    /**
     * 通过应急事件ID获取应急态势信息
     *
     * @param eventId
     * @return
     */
    @Deprecated
    BaseInfoVO getEmcStiuationInfo(Long eventId);

    /**
     * 通过应急事件ID获取应急态势信息
     * @param eventId
     * @return
     */
    BaseInfoVO getEmcSituationInfoByEventId(Long eventId);

    /**
     * 根据事故id获取关联的态势
     *
     * @param alarmRecordId
     */
    Result<List<SESECDRecorSituation>> getRelationSituationByAlarmRecordId(Long alarmRecordId);

    //*******************************************************自定义事件************************************************//

    /**
     * 应急态势 自定义保存后事件
     *
     * @param emcSituation
     * @param objects
     */
    void customAfterSaveEmcSituation(SESECDEmcSituation emcSituation, Object... objects);

}
