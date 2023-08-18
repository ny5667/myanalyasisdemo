package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.gisdata.AlarmEventGisVo;
import com.supcon.orchid.SESECD.model.vo.gisdata.AlarmEventQueryVo;
import com.supcon.orchid.SESECD.model.vo.map.AlarmEventQueryVO;
import com.supcon.orchid.SESECD.model.vo.map.AlarmRecordGisVO;
import com.supcon.orchid.SESECD.services.map.CustomSESECDGISDataService;
import com.supcon.orchid.SesCommonFunc.dto.ConditionDTO;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



import java.util.List;

@Controller
public class CustomSESECDGISDatasController {
	
	@Autowired
	private CustomSESECDGISDataService GISDatasService;
	
	/**
	*  根据空间数据IDs提供应急事件业务数据服务
	* @param
	* @return
	*/
    @PostMapping("/SESECD/alarmRecord/alarmRecord/getAlarmEventInfoInfo")
    @ResponseBody
    public BaseInfoVo getAlarmEventInfoInfoByFeatures(@RequestBody ConditionDTO dto){
    	return GISDatasService.getAlarmEventInfoInfoByFeatures(dto);
    }

	/**
	 *  根据空间数据IDs提供接警事件业务数据服务
	 * @param
	 * @return
	 */
	@PostMapping("/SESECD/alarmRecord/alarmRecord/getAlarmWarningInfoInfo")
	@ResponseBody
	public BaseInfoVo getAlarmWarningInfoInfoByFeatures(@RequestBody ConditionDTO dto){
		return GISDatasService.getAlarmWarningInfoInfoByFeatures(dto);
	}
    
    /**
	*  根据空间数据IDs提供指令业务数据服务
	* @param
	* @return
	*/
    @PostMapping("/SESECD/alarmRecord/alarmRecord/getInstructionInfoInfo")
    @ResponseBody
    public BaseInfoVo getInstructionInfoInfoByFeatures(@RequestBody ConditionDTO dto){
    	return GISDatasService.getInstructionInfoInfoByFeatures(dto);
    }
    
    /**
	*  根据空间数据IDs提供应急行动业务数据服务
	* @param
	* @return
	*/
    @PostMapping("/SESECD/alarmRecord/alarmRecord/getEcdActionInfoInfo")
    @ResponseBody
    public BaseInfoVo getEcdActionInfoInfoByFeatures(@RequestBody ConditionDTO dto){
    	return GISDatasService.getEcdActionInfoInfoByFeatures(dto);
    }
    
    /**
	*  根据空间数据IDs提供应急态势业务数据服务
	* @param
	* @return
	*/
    @PostMapping("/SESECD/alarmRecord/alarmRecord/getEcdSituationnfoInfo")
    @ResponseBody
    public BaseInfoVo getEcdSituationnfoInfoByFeatures(@RequestBody ConditionDTO dto){
    	return GISDatasService.getEcdSituationnfoInfoByFeatures(dto);
    }

	/**
	 *  根据空间数据IDs提供指令业务数据服务
	 * @param
	 * @return
	 */
	@PostMapping("/SESECD/alarmRecord/alarmRecord/getAlertRecordInfo")
	@ResponseBody
	public BaseInfoVo getAlertRecordInfoInfoByFeatures(@RequestBody ConditionDTO dto){
		return GISDatasService.getAlertRecordInfoInfoByFeatures(dto);
	}

	/**
	 *  根据空间数据IDs提供应急态势业务数据服务
	 * @param
	 * @return
	 */
	@PostMapping("/SESECD/alarmRecord/alarmRecord/listAlarmEventByGisAndRadius")
	@ResponseBody
	public List<AlarmRecordGisVO> listAlarmEventByGisAndRadius(@RequestBody AlarmEventQueryVO dto){
		return GISDatasService.listAlarmEventByGisAndRadius(dto);
	}

}
