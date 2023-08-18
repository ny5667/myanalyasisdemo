package com.supcon.orchid.SESECD.controllers;

import java.util.List;

import com.supcon.orchid.SESECD.model.dto.alarm.*;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionService;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.emerency.address.book.CustomSESECDEmergencyAddressBookService;
import com.supcon.orchid.SESECD.services.emerency.resource.CustomSESECDEmergencyResourceService;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDEmcSituationService;
import com.supcon.orchid.foundation.entities.SystemCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmService;
import com.supcon.orchid.support.Result;

import javax.validation.Valid;

/**
 *
 * @menu 事件
 */
@Controller
public class CustomSESECDAlarmController {

    @Autowired
    private CustomSESECDAlarmService customSESECDAlarmService;

    @Autowired CustomSESECDEmergencyResourceService customSESECDEmergencyResourceService;

    @Autowired
    private CustomSESECDAlarmActionService customSESECDAlarmActionService;

    @Autowired
    private CustomSESECDEmcSituationService customSESECDEmcSituationService;

    @Autowired
    private CustomSESECDActionService customSESECDActionService;

    @Autowired
    private CustomSESECDEmergencyAddressBookService customSESECDEmergencyAddressBookService;

    /**
     * 接警确认
     *
     * @param alarmCheckDTO
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/alarmCheck")
    @ResponseBody
    public Result<Void> alarmCheck(@RequestBody AlarmCheckDTO alarmCheckDTO) {
        customSESECDAlarmService.alarmCheck(alarmCheckDTO);
        return Result.data(null);
    }


    /**
     * 处警
     *
     * @param alarmHandleDto
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/changeAlarmState")
    @ResponseBody
    public Result<Boolean> changeAlarmState(@RequestBody AlarmHandleDto alarmHandleDto) {

        BaseInfoVO state = customSESECDAlarmService.changeAlarmState(alarmHandleDto);
        if (state.isSuccess()) {
            return Result.success("SUCCESS");
        } else {
            return Result.fail(state.getErrMsg());
        }
    }

    /**
     * 关闭应急事件
     *
     * @param eventIds
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/overEvents")
    @ResponseBody
    public Result<Boolean> changeAlarmState(@RequestBody List<Long> eventIds) {
        return Result.data(customSESECDAlarmService.overEvents(eventIds));
    }

    /**
     * 通过应急事件查询应急通讯小组成员信息
     *
     * @param eventId
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEmerStaffByEmerEventId")
    @ResponseBody
    public Result<BaseInfoVO> getEmerStaffByEmerEventId(Long eventId) {
        BaseInfoVO emerStaffByEmerEventId = customSESECDEmergencyAddressBookService.getEmerStaffByEmerEventId(eventId);
        if (emerStaffByEmerEventId.isSuccess()) {
            return Result.data(emerStaffByEmerEventId);
        } else {
            return Result.fail(emerStaffByEmerEventId.getErrMsg());
        }
    }

    /**
     * 通过应急事件的事故类型过滤查询应急专家信息
     *
     * @param eventId
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEmerExportsByEmerEventId")
    @ResponseBody
    public Result<BaseInfoVO> getEmerExportsByEmerEventId(Long eventId) {
        BaseInfoVO emerStaffByEmerEventId = customSESECDEmergencyResourceService.getEmerExportsByEmerEventId(eventId);
        if (emerStaffByEmerEventId.isSuccess()) {
            return Result.data(emerStaffByEmerEventId);
        } else {
            return Result.fail(emerStaffByEmerEventId.getErrMsg());
        }
    }

    /**
     * 将消息推送给消息中心
     * @param messageDto
     * @return
     */
    @PostMapping(value = "/SESECD/alarmRecord/alarmRecord/pulishMessage", produces = {
            "application/json;charset=UTF-8"})
    @ResponseBody
    public Result<String> pulishMessage(@RequestBody MessageDto messageDto) {
        Long eventId = messageDto.getEventId();
        List<String> staffCodes = messageDto.getStaffCodes();
        String pulishMessage = customSESECDAlarmService.pulishMessage(eventId, staffCodes);
        if ("SUCCESS".equals(pulishMessage)) {
            return Result.data("SUCCESS");
        } else {
            return Result.fail(pulishMessage);
        }

    }

    /**
     * 通过应急事件ID获取应急事件关联的指令
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getCommomInfo")
    @ResponseBody
    public Result<BaseInfoVO> getCommomInfo(Long eventId) {
        Assert.notNull(eventId,"eventId is null");
        BaseInfoVO CommomInfo = customSESECDAlarmActionService.getCommomInfo(eventId);
        if (CommomInfo.isSuccess()) {
            return Result.data(CommomInfo);
        } else {
            return Result.fail(CommomInfo.getErrMsg());
        }
    }

    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEmcStiuationInfo")
    @ResponseBody
    public Result<BaseInfoVO> getEmcStiuationInfo(Long eventId) {
        BaseInfoVO EmcStiuationInfo = customSESECDEmcSituationService.getEmcStiuationInfo(eventId);
        if (EmcStiuationInfo.isSuccess()) {
            return Result.data(EmcStiuationInfo);
        } else {
            return Result.fail(EmcStiuationInfo.getErrMsg());
        }
    }

    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEmcSituationInfoByEventId")
    @ResponseBody
    public Result<BaseInfoVO> getEmcSituationInfoByEventId(Long eventId) {
        Assert.notNull(eventId,"eventId is null");
        BaseInfoVO EmcStiuationInfo = customSESECDEmcSituationService.getEmcSituationInfoByEventId(eventId);
        if (EmcStiuationInfo.isSuccess()) {
            return Result.data(EmcStiuationInfo);
        } else {
            return Result.fail(EmcStiuationInfo.getErrMsg());
        }
    }

    /**
     * 通过应急事件ID获取应急行动信息
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEmcActionInfo")
    @ResponseBody
    public Result<BaseInfoVO> getEmcActionInfo(Long eventId) {
        BaseInfoVO EmcActionInfo = customSESECDActionService.getEmcActionInfo(eventId);
        if (EmcActionInfo.isSuccess()) {
            return Result.data(EmcActionInfo);
        } else {
            return Result.fail(EmcActionInfo.getErrMsg());
        }
    }


    /**
     * 下达指令更改应急事件关联指令状态并将指令下达生成应急行动
     * @param commomIds
     * @return
     */
    @PostMapping(value = "/SESECD/alarmRecord/alarmRecord/releaseCommom", produces = {
            "application/json;charset=UTF-8"})
    @ResponseBody
    public Result<String> releaseCommom(@RequestBody List<Long> commomIds) {
        Assert.notEmpty(commomIds,"commonIds is empty");
        BaseInfoVO releaseCommom = customSESECDAlarmActionService.releaseCommom(commomIds);
        if (releaseCommom.isSuccess()) {
            return Result.data("SUCCESS");
        } else {
            return Result.fail(releaseCommom.getErrMsg());
        }
    }

    /**
     * 通过应急事件ID获取应急事件相关信息及最新一条态势信息及最新一条行动信息
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getCurrentInfoById")
    @ResponseBody
    public Result<EcdInfoByIdDto> getecdAndInfoCurrentInfoById(Long eventId) {
        return Result.data(customSESECDAlarmService.getecdAndInfoCurrentInfoById(eventId));

    }

    /**
     * 通过应急事件ID获取应急事件信息、应急指令信息、应急态势信息及应急行动信息
     *
     * @param eventId
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/getEcdInfosByEventId")
    @ResponseBody
    public Result<EcdInfosDto> getEcdInfosByEventId(Long eventId) {
        return Result.data(customSESECDAlarmService.getEcdInfosByEventId(eventId));

    }

    /**
     * 保存应急指令坐标到应急行动业务数据坐标字段
     *
     * @param mapPointDto 应急指令坐标坐标
     * @return
     */
    @PostMapping(value = "/SESECD/alarmRecord/alarmRecord/saveMapPoint", produces = {
            "application/json;charset=UTF-8"})
    @ResponseBody
    public Result<String> saveMapPoint(@RequestBody MapPointDto mapPointDto) {

        String saveMapPoint = customSESECDAlarmActionService.saveMapPoint(mapPointDto);
        if (saveMapPoint.equals("success")) {
            return Result.data("SUCCESS");
        } else {
            return Result.fail("FAIL");
        }
    }


    /**
     * 修改事件报警等级
     * @param changeLevelDTO
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/changeLevel")
    @ResponseBody
    public Result<SystemCode> changeLevel(@RequestBody @Valid ChangeLevelDTO changeLevelDTO) {
        return Result.data(customSESECDAlarmService.changeLevel(changeLevelDTO));
    }
}
