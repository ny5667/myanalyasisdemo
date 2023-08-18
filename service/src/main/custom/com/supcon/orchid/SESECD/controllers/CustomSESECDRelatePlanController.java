package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.annotation.StopWatchTime;
import com.supcon.orchid.SESECD.model.vo.relate.plan.EnableChangStateVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.SESWssEPRelatedAlarmVO;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanService;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanServiceExpendService;
import com.supcon.orchid.SESECD.utils.HttpRequestUtil;
import com.supcon.orchid.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class CustomSESECDRelatePlanController {

    @Autowired
    private CustomSESECDRelatePlanService relatePlanService;

    @Autowired
    private CustomSESECDRelatePlanServiceExpendService expendService;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 事件关联应急预案 list 接口
     * @param  eventId 事件id
     * @return
     */
    @StopWatchTime
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/list")
    @ResponseBody
    public Result<List<SESWssEPRelatedAlarmVO>> listEmergencyPlan(@RequestParam("eventId") Long eventId) {
        if (null == eventId) {
            return Result.fail("input param invalid");
        }

        logger.error("应急行动列表接口被调用,入参:{}", eventId);
        List<SESWssEPRelatedAlarmVO> relatedAlarmVOS =  relatePlanService.listEmergencyPlan(eventId);
        return Result.data(relatedAlarmVOS);
    }

    /**
     * 获取全部应急预案，根据预案和事件的匹配度进行排序展示
     * @param  emergencyName 预案名称
     * @return
     */
    //@StopWatchTime
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/listEmergencyPlanByName")
    @ResponseBody
    public Result<List<SESWssEPRelatedAlarmVO>> listEmergencyPlanByName(String emergencyName,@RequestParam("eventId") @NotNull Long eventId) {
        logger.error("应急行动列表接口被调用,预案名称：{}；事件id{}", emergencyName,eventId);
        List<SESWssEPRelatedAlarmVO> relatedAlarmVOS =  relatePlanService.listEmergencyPlanByName(emergencyName,eventId);
        return Result.data(relatedAlarmVOS);
    }

    /**
     * 获取某一类事故类型的预案
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/listsafeproductplan")
    @ResponseBody
    public Result<List<SESWssEPRelatedAlarmVO>> listEmergencyPlanByAccidentId() {
        logger.error("获取生产安全事故类型的预案接口被调用");
        List<SESWssEPRelatedAlarmVO> plans =  relatePlanService.listEmergencyPlanByAccident();
        return Result.data(plans);
    }

    /**
     * 预案开启或关闭功能实现
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/openEmergencyPlan")
    @ResponseBody
    public Result openEmergencyPlan(@RequestParam @NotNull Long eventId , @RequestParam @NotNull Long planId , @RequestParam @NotNull Boolean isClose) {
        logger.error("预案开启或关闭功能实现接口被调用");
        if (isClose == null){
            return Result.fail(206,"参数为空");
        }
        EnableChangStateVO result = relatePlanService.openEmergencyPlan(eventId, planId, isClose);
        return Result.data(result);
    }

    /**
     * 统计当前开启状态预案的数量
     * @param eventId 接警id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/statisticsEmergencyPlanNum")
    @ResponseBody
    public Result statisticsEmergencyPlanNum(@RequestParam @NotNull Long eventId ) {
        logger.error("统计当前开启状态预案的数量,入参：{}",eventId);
        Integer num = expendService.statisticsEmergencyPlanNum(eventId);
        if (num == 0){
            Result result = new Result<>();
            result.setData(num);
            result.setCode(200);
            result.setMessage("数据库数量为0或字段IS_ClOSE为空");
            return result;
        }
        return Result.data(num);
    }

    /**
     * 统计当前下达状态的指令数量
     * @param eventId 接警id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/emergencyPlan/statisticsAlarmActionNum")
    @ResponseBody
    public Result statisticsAlarmActionNum(@RequestParam @NotNull Long eventId ) {
        logger.error("统计当前下达状态的指令数量,入参：{}",eventId);
        Integer num = expendService.statisticsAlarmActionNum(eventId);
        if (num == 0){
            Result result = new Result<>();
            result.setData(num);
            result.setCode(200);
            result.setMessage("数据库数量为0或均为未下达");
            return result;
        }
        return Result.data(num);
    }


}
