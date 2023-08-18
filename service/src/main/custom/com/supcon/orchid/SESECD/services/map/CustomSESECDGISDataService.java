package com.supcon.orchid.SESECD.services.map;

import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.map.AlarmEventQueryVO;
import com.supcon.orchid.SESECD.model.vo.map.AlarmRecordGisVO;
import com.supcon.orchid.SesCommonFunc.dto.ConditionDTO;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;

import java.util.List;

public interface CustomSESECDGISDataService  {

    /**
     * 根据空间数据IDs提供接警事件业务数据服务
     *
     * @param dto
     * @return
     */
    BaseInfoVo getAlarmWarningInfoInfoByFeatures(ConditionDTO dto);

    /**
     * 根据空间数据IDs提供应急事件业务数据服务
     *
     * @return
     */
    BaseInfoVo getAlarmEventInfoInfoByFeatures(ConditionDTO dto);

    /**
     * 根据空间数据IDs提供指令业务数据服务
     *
     * @return
     */
    BaseInfoVo getInstructionInfoInfoByFeatures(ConditionDTO dto);

    /**
     * 根据空间数据IDs提供应急行动业务数据服务
     *
     * @param dto
     * @return
     */
    BaseInfoVo getEcdActionInfoInfoByFeatures(ConditionDTO dto);

    /**
     * 根据空间数据IDs提供应急态势业务数据服务
     *
     * @param dto
     * @return
     */
    BaseInfoVo getEcdSituationnfoInfoByFeatures(ConditionDTO dto);


    /**
     * 根据空间数据IDs提供报警记录业务数据服务
     * @param dto
     * @return
     */
    BaseInfoVo getAlertRecordInfoInfoByFeatures(ConditionDTO dto);

    /**
     * 按坐标和半径查询应急事件
     *
     * @param query
     * @return
     */
    List<AlarmRecordGisVO> listAlarmEventByGisAndRadius(AlarmEventQueryVO query);

}
