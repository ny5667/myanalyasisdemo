package com.supcon.orchid.SESECD.controllers.project.sgm.report.event;

import com.supcon.orchid.SESECD.model.vo.project.sgm.report.event.SESECDAlarmRecordVO;
import com.supcon.orchid.SESECD.services.project.sgm.report.event.CustomSESECDProjectSgmReportEventService;
import com.supcon.orchid.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomSESECDProjectSgmReportEventController {

    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDProjectSgmReportEventController.class);

    @Autowired
    private CustomSESECDProjectSgmReportEventService customSESECDSgmReportEventService;

    /**
     * 接警数据上报
     * @param alarmRecordId 接警id
     * @return 上报结果
     */
    @GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/sgmReportEvent/report/{alarmRecordId}")
    public Result<String> Report(@PathVariable Long alarmRecordId) {
        try{
            Assert.notNull(alarmRecordId,"alarmRecordId is null");
            return customSESECDSgmReportEventService.Report(alarmRecordId);
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return Result.fail(ex.getMessage());
        }
    }

    /**
     * 接警数据接受
     * @param input 接警事件
     * @return 数据保存成功信息
     */
    @PostMapping(value = "/public/SESECD/alarmRecord/almAlarmRecord/sgmReportEvent/addOrUpdate")
    public Result<String> Save(@RequestBody SESECDAlarmRecordVO input) throws Exception {
        Assert.notNull(input,"vo is null");
        Assert.hasLength(input.getParentCompanyCode(),"父公司编码为空");
        Assert.notNull(input.getId(),"id is null");
        Assert.hasLength(input.getMapPoint(),"pointInfo is empty");
        return customSESECDSgmReportEventService.AddOrUpdate(input);
    }

}
