package com.supcon.orchid.SESECD.services.relate.plan;

import com.supcon.orchid.SESECD.model.vo.relate.plan.EnableChangStateVO;
import com.supcon.orchid.SESECD.model.vo.TypeAndPlanIdsVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.AllEmerMemberVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.SESWssEPRelatedAlarmVO;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;

import java.util.List;
import java.util.Set;

public interface CustomSESECDRelatePlanService {
    /**
     * 应急预案 list 接口
     * @param  eventId 事件id
     * @return
     */
    List<SESWssEPRelatedAlarmVO> listEmergencyPlan(Long eventId);

    /**
     * 获取某一类事故类型的预案
     * @return
     */
    List<SESWssEPRelatedAlarmVO> listEmergencyPlanByAccident();

    /**
     * 根据事故类型id获取应急预案
     */
    Set<SESWssEPEmergencyPlan> getContingencyPlan(TypeAndPlanIdsVO idsVO);

    /**
     * 查找所有应急小组
     * @return
     */
    List<AllEmerMemberVO> queryEmergency();


    /**
     * 获取全部应急预案，根据预案和事件的匹配度进行排序展示
     * @param  emergencyName 预案名称
     * @return
     */
    List<SESWssEPRelatedAlarmVO> listEmergencyPlanByName(String emergencyName,Long eventId);


    /**
     * 预案开启或关闭功能实现
     * @param eventId  事件id
     * @param planId  预案id
     * @param isClose 开启状态
     * @return
     */
    EnableChangStateVO openEmergencyPlan(Long eventId, Long planId, Boolean isClose);
}
