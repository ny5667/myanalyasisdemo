package com.supcon.orchid.SESECD.controllers;

import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESECD.model.dto.outward.jobctl.JobControlDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.services.notify.request.CommonSmsRequest;
import com.supcon.orchid.SESECD.services.outward.jobctl.CustomSESECDOutwardService;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/SESECD/alarmRecord/alarmRecord")
public class CustomSESECDOutwardController {

    @Autowired
    private CustomSESECDOutwardService jobControlService;


    @Autowired
    private NotifyFacade notifyFacade;

    /**
     * 作业受控
     *
     * @param jobControlDTO
     * @return
     */
    @PostMapping("/JobControl")
    @ResponseBody
    public Result<BaseInfoVo> getJobControl(@RequestBody JobControlDTO jobControlDTO) {
        BaseInfoVo jobControl = jobControlService.getJobControl(jobControlDTO);
        return Result.data(jobControl);
    }

    /**
     * 短信通知
     *
     * @param
     * @return
     */
    @PostMapping("/sms")
    @ResponseBody
    public Result<String> notice(@RequestBody CommonSmsRequest params) {
        String ret = notifyFacade.handleSMS(params);
        return Result.data(ret);
    }

}
