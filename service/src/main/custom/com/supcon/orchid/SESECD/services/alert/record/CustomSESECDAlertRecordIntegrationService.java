package com.supcon.orchid.SESECD.services.alert.record;

import com.supcon.orchid.SESECD.model.vo.alertRecord.*;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.support.Result;

import java.util.List;

public interface CustomSESECDAlertRecordIntegrationService {

    /**
     * 查询报警记录接口
     * @param inputVO
     * @return
     */
    Result<Page<VxECDVxAlertRecordVO>> listAlertRecord(RequestVO inputVO);

    /**
     * 新增报警记录
     * @param inputVO
     * @return
     */
    Result<String> addOrUpdateAlertRecord(AlertRecordInputVO inputVO);

    /**
     * 批量修改报警记录状态接口
     * @param ids
     * @param status
     * @return
     */
    Result<String> updateAlertRecordStatus(List<Long> ids, String status);

}