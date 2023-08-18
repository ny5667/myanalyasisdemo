package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.vo.TypeAndPlanIdsVO;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanService;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class CustomMatchPlanController {

    @Autowired
    private CustomSESECDAlarmRecordService service;

    @Autowired
    private CustomSESECDRelatePlanService customSESECDRelatePlanService;


    /**
     * 根据事故类型id 报警等级获取应急预案
     *
     */
    @PostMapping("/SESECD/emcAction/emcAction/getContingencyPlan")
    @ResponseBody
    public Result<?> getContingencyPlan(@RequestBody TypeAndPlanIdsVO idsVO){
        Set<SESWssEPEmergencyPlan> contingencyPlans = customSESECDRelatePlanService.getContingencyPlan(idsVO);
        return Result.data(contingencyPlans);
    }
}
