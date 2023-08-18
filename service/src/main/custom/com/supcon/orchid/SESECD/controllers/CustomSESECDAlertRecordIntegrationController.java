package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.vo.alertRecord.AlertRecordInputVO;
import com.supcon.orchid.SESECD.model.vo.alertRecord.RequestVO;
import com.supcon.orchid.SESECD.model.vo.alertRecord.VxECDVxAlertRecordVO;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordIntegrationService;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordV2Service;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 报警记录
 *
 * @menu 报警记录
 */
@RestController
public class CustomSESECDAlertRecordIntegrationController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomSESECDAlertRecordIntegrationService customVxECDAlertRecordIntegrationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomSESECDAlertRecordV2Service customSESECDAlertRecordV2Service;


    /**
     * 报警记录列表
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/SESECD/ecdAlertRecord/ecdAlertRecord/listAlertRecord")
    public Result<Page<VxECDVxAlertRecordVO>> listAlertRecord(@RequestBody RequestVO vo) {
        Assert.notNull(vo, "vo is null");
        Assert.notNull(vo.getPageNo(), "pageNo is null");
        Assert.notNull(vo.getPageSize(), "pageSize is null");
        return customVxECDAlertRecordIntegrationService.listAlertRecord(vo);
    }

    /**
     * 新增报警记录
     *
     * @param inputVO
     * @return
     */
    @PostMapping(value = "/public/VxECD/alertRecord/vxAlertRecord/addOrUpdate")
    public Result<String> addOrUpdateAlertRecord(@RequestBody AlertRecordInputVO inputVO) {
        log.error("进入保存报警记录controller方法");
        log.error(JsonHelper.writeValue(inputVO));
        validateInput(inputVO);
        return customVxECDAlertRecordIntegrationService.addOrUpdateAlertRecord(inputVO);
    }

    /**
     * 新增报警记录
     *
     * @param inputVO
     * @return
     */
    @PostMapping(value = "/public/SESECD/ecdAlertRecord/ecdAlertRecord/addOrUpdate")
    public Result<String> addOrUpdateAlertRecord2(@RequestBody AlertRecordInputVO inputVO) {
        log.error("进入保存报警记录controller方法");
        log.error(JsonHelper.writeValue(inputVO));
        validateInput(inputVO);
        return customVxECDAlertRecordIntegrationService.addOrUpdateAlertRecord(inputVO);
    }

    /**
     * 批量修改报警记录状态接口
     *
     * @param ids
     * @param status
     * @return
     */
    @GetMapping(value = "/SESECD/ecdAlertRecord/ecdAlertRecord/updateAlertRecordStatus")
    @ResponseBody
    public Result<String> updateAlertRecordStatus(@RequestParam List<Long> ids, @RequestParam String status) {
        return customVxECDAlertRecordIntegrationService.updateAlertRecordStatus(ids, status);
    }

    /**
     * 从报警中心同步报警数据调度任务
     * @param companyCode 公司编码，用于只处理本公司的数据
     * @param alarmStaffCode staffCode用于查询报警接口进行报警点权限过滤
     * @return 返回该接口是否调用成功
     */
    @GetMapping(value = "/public/SESECD/ecdAlertRecord/ecdAlertRecord/automaticAlarmByAlarm")
    public Result automaticAlarmByAlarm(@RequestParam String companyCode, @RequestParam String alarmStaffCode) {
        log.error("触发报警装置调度controller方法");
        Assert.hasLength(companyCode, "companyCode is empty");
        Company companyByCode = companyService.getCompanyByCode(companyCode);
        Assert.notNull(companyByCode, "companyCode can not find entity");
        Assert.hasLength(alarmStaffCode,"alarmStaffCode is empty");
        //报警授权过滤用户，如果该用户为空，则默认用管理员账号
        if(alarmStaffCode == null || alarmStaffCode.isEmpty()){
            alarmStaffCode = "default_person";
        }
        Staff staffByCode = staffService.getStaffByCode(alarmStaffCode);
        Assert.notNull(staffByCode,"alarmStaffCode can not find staff");
        return customSESECDAlertRecordV2Service.automaticAlarmByAlarm(companyByCode.getId(), staffByCode);
    }

    /*--------------------------------------------------公共功能--------------------------------------------------*/

    /**
     * 验证字段格式正确
     *
     * @param inputVO
     */
    private void validateInput(AlertRecordInputVO inputVO) {
        Assert.notNull(inputVO, "vo is null");
        //上限
        Assert.notNull(inputVO.getUpperLimit(), "upperLimit is null");
        // 报警编码
        Assert.hasLength(inputVO.getAlarmCode(), "alarmCode is empty");
        // 报警装置名称
        Assert.hasLength(inputVO.getAlarmDeviceName(), "alarmDeviceName is empty");
        // 报警时间
        Assert.notNull(inputVO.getAlarmTime(), "alarmTime is null");
        // 报警装置坐标经度
        Assert.notNull(inputVO.getDeviceLocationX(), "deviceLocationX is null");
        // 报警来源
        Assert.hasLength(inputVO.getAlarmSource(), "alarmSource is empty");
        // 报警装置坐标纬度
        Assert.notNull(inputVO.getDeviceLocationY(), "deviceLocationY is null");
        // 报警内容
        Assert.hasLength(inputVO.getAlarmContent(), "alarmContent is empty");
        // 实时值
        Assert.notNull(inputVO.getRealTimeValue(), "realTimeValue is null");
        // 报警持续时间（单位秒）
        Assert.notNull(inputVO.getDuration(), "duration is null");
        // 上上限
        Assert.notNull(inputVO.getUpperUpperLimit(), "upperUpperLimit is null");
        // 下下限
        Assert.notNull(inputVO.getLowerLowerLimit(), "lowerLowerLimit is null");
        // 下限
        Assert.notNull(inputVO.getLowerLimit(), "lowerLimit is null");
        //公司编码
        if (inputVO.getCompanyCode() == null || inputVO.getCompanyCode().isEmpty()) {
            inputVO.setCompanyCode(com.supcon.orchid.SESECD.constant.consist.ConstDict.DEFAULT_ORG_COMPANY);
        }
        Assert.hasLength(inputVO.getCompanyCode(), "companyCode is empty");
        Company companyByCode = companyService.getCompanyByCode(inputVO.getCompanyCode());
        Assert.notNull(companyByCode, "companyCode can't find entity:" + inputVO.getCompanyCode());
    }

}
