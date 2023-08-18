package com.supcon.orchid.SESECD.services.relate.plan;


public interface CustomSESECDRelatePlanServiceExpendService {

    /**
     * 统计当前开启状态预案的数量
     * @param eventID 接警id
     * @return
     */
    Integer statisticsEmergencyPlanNum(Long eventID);

    /**
     * 统计当前下达状态的指令数量
     * @param eventID 接警id
     * @return
     */
    Integer statisticsAlarmActionNum(Long eventID);
}
