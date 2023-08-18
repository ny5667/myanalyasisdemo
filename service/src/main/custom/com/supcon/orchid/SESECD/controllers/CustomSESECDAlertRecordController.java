package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.vo.GTSAlarmVO;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordService;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author: xulong2
 * @create: 2021-03-30 09:05
 * @description 自动报警接口
 **/

@Controller("CustomSESECDAlertRecordController")
public class CustomSESECDAlertRecordController {

    @Autowired
    private CustomSESECDAlertRecordService alertRecordService;


    /**
     * 定时任务触发获取标准数据服务报警接口
     * @param companyCode
     * @return
     */
    @GetMapping(value = "/public/SESECD/alertRecord/alertRecord/automaticAlarmByStandard")
    @ResponseBody
    public Result<String> automaticAlarmByStandard(@RequestParam String companyCode) {
        return alertRecordService.automaticAlarmByStandard(companyCode);
    }

    /**
     * 批量修改报警记录状态接口
     * @param ids
     * @param status
     * @return
     */
    @GetMapping(value = "/SESECD/alertRecord/alertRecord/updateAlertRecordStatus")
    @ResponseBody
    public Result<String> updateAlertRecordStatus(@RequestParam List<Long> ids, @RequestParam String status, @RequestParam String alarmType){
        return alertRecordService.updateAlertRecordStatus(ids,status,alarmType);
    }


    /**
     * GTS火灾报警推送接口
     * @return
     */
    @PostMapping(value = "/public/SESECD/alertRecord/alertRecord/GTSFireAlarm")
    @ResponseBody
    public void GTSFireAlarm(GTSAlarmVO vo){
        alertRecordService.GTSFireAlarm(vo);
    }

        /**
     * 可燃有毒气 生成接警记录
     * @return
     */
    @GetMapping(value = "/SESECD/alertRecord/alertRecord/createAlarmRecordByFireGasOrPoisonGas")
    @ResponseBody
    public Result<String> createAlarmRecordByFireGasOrPoisonGas( @RequestParam String status,@RequestParam Long deviceId, @RequestParam Long alarmTime){
        return alertRecordService.createAlarmRecordByFireGasOrPoisonGas(status,deviceId,alarmTime);
    }
}
