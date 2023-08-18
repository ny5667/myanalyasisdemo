package com.supcon.orchid.SESECD.services.action;

import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.model.dto.action.ActionDto;
import com.supcon.orchid.SESECD.model.vo.action.SESECDActVideoCameraVO;
import com.supcon.orchid.SESECD.model.vo.alarm.AlarmRecordVO;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.support.Result;

import java.util.List;

/**
 * 应急行动
 */
public interface CustomSESECDActionService {

	/**
	 * 跟踪应急行动
	 * @param actionDto
	 * @return
	 */
	Boolean trackAction(ActionDto actionDto);

	/**
	 * 判断的当前登录人是否是调度员
	 * @return
	 */
	Boolean judgeDispatcher();

	/**
	 * 行动关联摄像头
	 * @param actionId 应急行动Id
	 * @return 摄像头列表
	 */
	List<SESECDActVideoCameraVO> listCamerasByActionId(Long actionId);


	/**
	 * 通过应急事件ID获取应急行动信息
	 *
	 * @param eventId
	 * @return
	 */
	BaseInfoVO getEmcActionInfo(Long eventId);

	/**
	 * 根据事故id获取关联行动
	 *
	 * @param alarmRecordId
	 */
	Result<List<SESECDRecordAction>> getRelationActionByAlarmRecordId(Long alarmRecordId);

	//*******************************************************自定义事件************************************************//

	/**
	 * 应急行动 自定义保存后事件
	 *
	 * @param emcAction
	 */
	void customAfterSaveEmcAction(SESECDEmcAction emcAction);

}

