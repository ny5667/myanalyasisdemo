package com.supcon.orchid.SESECD.services.alert.record;

import com.supcon.orchid.SESECD.model.vo.GTSAlarmVO;
import com.supcon.orchid.support.Result;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-30 09:06
 * @description
 **/
public interface CustomSESECDAlertRecordService {
    /**
     * 定时任务触发获取标准数据服务报警接口
     * @param companyCode
     * @return
     */
    Result<String> automaticAlarmByStandard(String companyCode);

    /**
     * 批量修改报警记录状态接口
     * @param ids
     * @param status
     * @param alarmType 报警来源 1:应急指挥；2:有毒可燃气
     * @return
     */
    Result<String> updateAlertRecordStatus(List<Long> ids, String status,String alarmType);

    /**
     * GTS火灾报警推送接口
     * @return
     */
    void GTSFireAlarm(GTSAlarmVO vo);
  
   Result<String> createAlarmRecordByFireGasOrPoisonGas(String status ,Long deviceName,Long alarmTime);

}
