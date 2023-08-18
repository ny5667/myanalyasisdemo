package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDEmcSituationService;
import com.supcon.orchid.services.BAPException;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.lang.Long;


/**
 *
 * @author ludunyue
 * @date 2020/3/24
 */
@Controller
public class CustomSESECDAlarmRecordController {

    @Autowired
    private CustomSESECDAlarmRecordService customSESECDAlarmRecordService;

    @Autowired
    private CustomSESECDEmcSituationService customSESECDEmcSituationService;

    @Autowired
    private CustomSESECDActionService customSESECDActionService;

    /**
     * 通过接警Id获取通讯组Ids
     * @param alarmRecordId
     * @return
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getSectionIdListByAlarmRecordId")
    @ResponseBody
    public Result<List<Long>> getSectionIdListByAlarmRecordId(Long alarmRecordId){
        if (null == alarmRecordId) {
            throw new BAPException("alarmRecordId 不能为空！");
        }
        return Result.data(customSESECDAlarmRecordService.getSectionIdListByAlarmRecordId(alarmRecordId));
    }


    /**
     * 根据事故id获取关联的态势
     * @param id
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getRelationSituationById")
    @ResponseBody
    public Result<List<SESECDRecorSituation>> getRelationSituationById(Long id){
        return customSESECDEmcSituationService.getRelationSituationByAlarmRecordId(id);
    }

    /**
     * 根据事故id获取关联行动
     * @param id
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getRelationActionById")
    @ResponseBody
    public Result<List<SESECDRecordAction>> getRelationActionById(Long id){
        return customSESECDActionService.getRelationActionByAlarmRecordId(id);
    }

    /**
     * 根据应急事件Id 获取常用地址和摄像同相关信息
     *
     * @param id
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getCommonPlaceById")
    @ResponseBody
    public Result getCommonPlaceById(Long id){
        return customSESECDAlarmRecordService.getCommonPlaceById(id);
    }

    /**
     *  获取常用地址信息
     * @param id
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getDevicePositionMap")
    @ResponseBody
    public Result getDevicePositionMap(Long id){
        return customSESECDAlarmRecordService.getDevicePositionMap(id);
    }

    /**
     * 消息中心地址获取
     * @return
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/getWsAddress")
    @ResponseBody
    public String getWsAddress() {
        return "/inter-api/ws/v1/notice/commonPlace";
    }


    /**
     *  发送ws 消息
     * @return
     */
    @GetMapping("/SESECD/emcAction/CustomSESECDAlarmRecord/sendWsMsg")
    @ResponseBody
    public Result sendWsMsg(Long id) {
       return customSESECDAlarmRecordService.sendWsMsg(id);
    }

}
