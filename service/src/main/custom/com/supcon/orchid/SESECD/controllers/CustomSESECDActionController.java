package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.dto.action.ActionDto;
import com.supcon.orchid.SESECD.model.vo.action.SESECDActVideoCameraVO;
import com.supcon.orchid.SESECD.model.vo.alarm.AlarmRecordVO;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomSESECDActionController {

	@Autowired
	private CustomSESECDActionService customSESECDActionService;

	@Autowired
	private CustomSESECDAlarmRecordService customSESECDAlarmRecordService;

	/**
	 * 跟踪应急行动
	 *
	 * @param alarmHandleDto
	 * @return
	 */
	@PostMapping("/SESECD/emcAction/emcAction/trackAction")
	@ResponseBody
	public Result<Boolean> trackAction(@RequestBody ActionDto actionDto) {
		return Result.data(customSESECDActionService.trackAction(actionDto));
	}

	/**
	 * 判断当前登录人是否是调度员
	 *
	 * @return
	 */
	@GetMapping("/SESECD/emcAction/emcAction/judgeDispatcher")
	@ResponseBody
	public Result<Boolean> trackAction() {
		return Result.data(customSESECDActionService.judgeDispatcher());
	}

	/**
	 * 根据演练计划id得到接警记录
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/SESECD/emcAction/emcAction/getAlarmRecordByPlanId/{id}")
	@ResponseBody
	public Result<AlarmRecordVO> getAlarmRecordByPlanId(@PathVariable("id") Long id) {
		return Result.data(customSESECDAlarmRecordService.getAlarmRecordByPlanId(id));
	}

	/**
	 * 更具行动id获取摄像头列表
	 * @param actionId
	 * @return
	 */
	@GetMapping("/SESECD/emcAction/emcAction/listCamerasByActionId/{actionId}")
	@ResponseBody
	public Result<List<SESECDActVideoCameraVO>> listCamerasByActionId(@PathVariable("actionId") Long actionId) {
		Assert.notNull(actionId,"actionId is null");
		return Result.data(customSESECDActionService.listCamerasByActionId(actionId));
	}


}
