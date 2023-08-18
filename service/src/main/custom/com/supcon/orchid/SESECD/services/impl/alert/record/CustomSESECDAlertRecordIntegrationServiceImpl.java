package com.supcon.orchid.SESECD.services.impl.alert.record;

import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertImgDao;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertVideoDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.vo.alertRecord.AlertRecordInputVO;
import com.supcon.orchid.SESECD.model.vo.alertRecord.RequestVO;
import com.supcon.orchid.SESECD.model.vo.alertRecord.VxECDVxAlertRecordVO;
import com.supcon.orchid.SESECD.model.vo.common.CoordinateVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.services.alert.record.CustomSESECDAlertRecordIntegrationService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.support.Result;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Transactional
@Service
public class CustomSESECDAlertRecordIntegrationServiceImpl extends BaseServiceImpl implements CustomSESECDAlertRecordIntegrationService {

    /**
     * 实时报警表
     */
    private static final String ALARM_TABLE_SOURCE_THIRD = "第三方报警";

    private static final String MY_UN_HANDLE_ALARM_STATE = "未处置";

    /**
     * 已处置状态
     */
    private static final String MY_HANDLE_ALARM_STATE = "已处置";

    private static final String MY_MISINFORMATION_ALARM_STATE = "误报";
    private static final String MY_REPEATER_ALARM_STATE = "重复报警";

    private static final String AlertRecordLayer = "AlertRecordLayer";

    @Autowired
    private SESECDEcdAlertRecordDao alertRecordDao;



    @Autowired
    private SESECDEcdAlertImgDao alertImgDao;

    @Autowired
    private SESECDEcdAlertVideoDao alertVideoDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private SESECDAlmAlarmRecordDao alarmRecordDao;

    @Autowired
    private SqlUtils sqlUtils;

    @Autowired
    private CustomSESECDSavePointService savePointService;



    @Override
    public Result<Page<VxECDVxAlertRecordVO>> listAlertRecord(RequestVO inputVO) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDEcdAlertRecord.JPA_NAME).append(" where valid = 1").append(sqlUtils.getSqlPartByCID());
        int offset = (inputVO.getPageNo() - 1) * inputVO.getPageSize();
        List<SESECDEcdAlertRecord> list = alertRecordDao.createQuery(builder.toString()).setFirstResult(offset).setMaxResults(inputVO.getPageSize()).list();

        builder.delete(0,builder.length());
        builder.append("select count(*) from ").append(SESECDEcdAlertRecord.JPA_NAME).append(" where valid = 1").append(sqlUtils.getSqlPartByCID());
        long countResult = (long) alertRecordDao.createQuery(builder.toString()).getSingleResult();

        Page<VxECDVxAlertRecordVO> page = new Page<>();
        page.setTotalCount(countResult);
        if(list.isEmpty()){
            return Result.data(page);
        }
        List<VxECDVxAlertRecordVO> recordVOS = new ArrayList<>();
        for(SESECDEcdAlertRecord item : list){
            VxECDVxAlertRecordVO myRecord = getVxECDVxAlertRecordVO(item);
            recordVOS.add(myRecord);
        }
        page.setResult(recordVOS);
        return Result.data(page);
    }

    @Override
    public Result<String> addOrUpdateAlertRecord(AlertRecordInputVO inputVO) {

        log.error("进入保存报警记录service方法");
        log.error(JsonHelper.writeValue(inputVO));

        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDEcdAlertRecord.JPA_NAME).append(" where valid = 1 and alarmCode = ?0");
        List<SESECDEcdAlertRecord> alertRecords = alertRecordDao.findByHql(builder.toString(), new Object[]{inputVO.getAlarmCode()});

        SESECDEcdAlertRecord po = null;
        if (!alertRecords.isEmpty()) {
            po = alertRecords.get(0);
        } else {
            po = new SESECDEcdAlertRecord();
        }

        convertToPO(inputVO, po);

        CoordinateVO coordinateVO = new CoordinateVO(inputVO.getDeviceLocationX().toString(), inputVO.getDeviceLocationY().toString(), 0f);
        PointVO pointVO = new PointVO(AlertRecordLayer, Collections.singletonList(coordinateVO));
        po.setMapPoint(JsonHelper.writeValue(pointVO));//坐标点

        alertRecordDao.save(po);
        alertRecordDao.flush();

        saveImagesAndVideos(inputVO, po);

        String spatialId = SESECDEcdAlertRecord.MODEL_CODE + "_" + po.getId();
        log.error("开始保存点");
        try {
            savePointService.savePoint2PG(spatialId, pointVO, AlertRecordLayer);
        } catch (Exception e) {
            log.error("保存坐标报错！");
            throw new RuntimeException(e);
        }

        log.error("处理完保存报警记录service方法");
        return Result.success("");
    }

    @Override
    public Result<String> updateAlertRecordStatus(List<Long> ids, String status) {
        if (Objects.isNull(ids) || ids.size() == 0 || StringUtils.isEmpty(status)) {
            return Result.data(203, "参数异常!", "参数异常!");
        }
        switch (status) {
            case "1":
                return updateAlertStatus(ids, MY_MISINFORMATION_ALARM_STATE);//更新未误报报警状态
            case "2":
                return updateAlertStatus(ids, MY_REPEATER_ALARM_STATE);//更新未重复报警状态
            case "3":
                return createAlarmRecord(ids);//生成接警记录
            default:
                return Result.data(203, "参数异常!", "");
        }
    }

    /*--------------------------------------------------公共功能--------------------------------------------------*/


    /**
     * 创建报警记录VO
     * @param item
     * @return
     */
    private VxECDVxAlertRecordVO getVxECDVxAlertRecordVO(SESECDEcdAlertRecord item) {
        // 创建一个新的 VxECDVxAlertRecordVO 对象，并为每个字段设置默认值
        VxECDVxAlertRecordVO myRecord = new VxECDVxAlertRecordVO();
        myRecord.setId(item.getId());// id
        myRecord.setSpatialId(SESECDEcdAlertRecord.JPA_NAME + "_" + item.getId().toString());//空间id
        myRecord.setAlarmCode(item.getAlarmCode()); // 报警编码，默认为空字符串
        myRecord.setAlarmDeviceName(item.getAlarmDeviceName()); // 报警装置名称，默认为空字符串
        if(item.getAlarmTime() != null){
            myRecord.setAlarmTime(DateUtils.MySimpleDateFormat.format(item.getAlarmTime())); // 报警产生时间，默认为 null
        }
        myRecord.setDeviceLocationX(item.getDeviceLocationX()); // 报警装置坐标经度，默认为 0
        myRecord.setEndValue(item.getEndValue()); // 恢复值，默认为空字符串
        myRecord.setHv(item.getHv()); // 上限阈值，默认为空字符串
        myRecord.setLv(item.getLv()); // 下限阈值，默认为空字符串
        myRecord.setLlv(item.getLlv()); // 下下限阈值，默认为空字符串
        myRecord.setDeviceLocationY(item.getDeviceLocationY()); // 报警装置坐标纬度，默认为 0
        myRecord.setAlarmContent(item.getAlarmContent()); // 报警详情，默认为空字符串
        myRecord.setRealTimeValue(item.getRealTimeValue()); // 报警值，默认为 0
        myRecord.setDuration(item.getDuration()); // 报警持续时间（单位秒），默认为 0
        myRecord.setRecordId(item.getRecordId()); // recordId，默认为空字符串
        myRecord.setUnitName(item.getUnitName()); // 单位名称，默认为空字符串
        myRecord.setEndType(item.getEndType()); // 恢复类型，默认为空字符串
        myRecord.setAlarmType(item.getAlarmType()); // 报警类型，默认为空字符串
        myRecord.setAlarmLevel(item.getAlarmLevel()); // 报警等级，默认为空字符串
        myRecord.setLifeValue(item.getLifeValue()); // 解除时阈值，默认为空字符串
        myRecord.setDurationdays(item.getDurationdays()); // 持续天数，默认为 0
        myRecord.setDurationTime(item.getDurationTime()); // 持续时间，默认为空字符串
        myRecord.setAlarmOrigin(item.getAlarmOrigin()); // 报警来源，默认为空字符串
        myRecord.setHhv(item.getHhv()); // 上上限阈值，默认为空字符串
        if(item.getLifeTime() != null){
            myRecord.setLifeTime(DateUtils.MySimpleDateFormat.format(item.getLifeTime())); // 报警结束时间，默认为 null
        }
        myRecord.setAlarmName(item.getAlarmName()); // 报警点名，默认为空字符串
        myRecord.setAlarmState(item.getAlarmState()); // 报警状态，默认为空字符串
        myRecord.setAlarmTableSource(item.getAlarmTableSource()); // 报警表来源，默认为空字符串
        myRecord.setLimitValue(item.getLimitValue()); // 报警时阀值，默认为空字符串
        return myRecord;
    }

    /**
     * 转换数据
     *
     * @param inputVO
     * @param po
     * @return
     */
    private SESECDEcdAlertRecord convertToPO(AlertRecordInputVO inputVO, SESECDEcdAlertRecord po) {
        log.error("进入转换方法");
        log.error(JsonHelper.writeValue(inputVO));
//        VxECDVxAlertRecord po = new VxECDVxAlertRecord();

        // 报警编码
        po.setAlarmCode(inputVO.getAlarmCode());
        // 报警装置名称
        po.setAlarmDeviceName(inputVO.getAlarmDeviceName());
        // 报警时间
        po.setAlarmTime(inputVO.getAlarmTime());
        // 报警装置坐标经度
        po.setDeviceLocationX(inputVO.getDeviceLocationX());
        // 报警来源
        po.setAlarmOrigin(inputVO.getAlarmSource());
        // 报警装置坐标纬度
        po.setDeviceLocationY(inputVO.getDeviceLocationY());
        // 报警内容
        po.setAlarmContent(inputVO.getAlarmContent());
        // 实时值
        po.setRealTimeValue(inputVO.getRealTimeValue());
        // 报警持续时间（单位秒）
        po.setDuration(inputVO.getDuration());
        // 上限
        if(inputVO.getUpperLimit() != null){
            po.setHv(String.valueOf(inputVO.getUpperLimit()));
        }
        // 上上限
        if(inputVO.getUpperUpperLimit() != null){
            po.setHhv(String.valueOf(inputVO.getUpperUpperLimit()));
        }
        // 下下限
        if(inputVO.getLowerLowerLimit() != null){
            po.setLlv(String.valueOf(inputVO.getLowerLowerLimit()));
        }
        // 下限
        if(inputVO.getLowerLimit() != null){
            po.setLv(String.valueOf(inputVO.getLowerLimit()));
        }
        po.setAlarmTableSource(ALARM_TABLE_SOURCE_THIRD);
        po.setAlarmState(MY_UN_HANDLE_ALARM_STATE);//报警状态
        Company companyByCode = companyService.getCompanyByCode(inputVO.getCompanyCode());
        po.setCid(companyByCode.getId());
        log.error("结束转换方法");
        return po;
    }


    /**
     * 保存图片和视频
     * @param inputVO
     * @param po
     */
    private void saveImagesAndVideos(AlertRecordInputVO inputVO, SESECDEcdAlertRecord po) {
        log.error("进入保存图片和视频");
        StringBuilder builder = new StringBuilder();
        if(inputVO.getImages() != null && !inputVO.getImages().isEmpty()){
            //删除所有图片
            builder.delete(0, builder.length());
            builder.append("delete from ").append(SESECDEcdAlertImg.JPA_NAME).append(" where ALERT_RECORD = ?0");
            log.error("id:");
            log.error("" + po.getId());
            int i = alertImgDao.createQuery(builder.toString(), po.getId()).executeUpdate();
            //新增图片
            for (String item :
                    inputVO.getImages()) {
                SESECDEcdAlertImg imgPo = new SESECDEcdAlertImg();
                imgPo.setUrl(item);
                imgPo.setAlertRecord(po);
                alertImgDao.save(imgPo);
                alertImgDao.flush();
            }
        }

        if(inputVO.getVideos() != null && !inputVO.getVideos().isEmpty()){
            //删除视频
            builder.delete(0, builder.length());
            builder.append("delete from ").append(SESECDEcdAlertVideo.JPA_NAME).append(" where ALERT_RECORD = ?0");
            int i = alertVideoDao.createQuery(builder.toString(), po.getId()).executeUpdate();
            //新增视频
            for (String item :
                    inputVO.getVideos()) {
                SESECDEcdAlertVideo po_v = new SESECDEcdAlertVideo();
                po_v.setUrl(item);
                po_v.setAlertRecord(po);
                alertVideoDao.save(po_v);
                alertVideoDao.flush();
            }
        }
        log.error("退出保存图片和视频");
    }


    /**
     * 批量修改报警记录状态，修改为误报或者重复报警
     * @param ids
     * @param status
     * @return
     */
    private Result<String> updateAlertStatus(List<Long> ids, String status) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(SESECDEcdAlertRecord.TABLE_NAME).append(" set ALARM_STATE = '")
                .append(status).append("' where id in (:ids)");
        NativeQuery nativeQuery = alertRecordDao.createNativeQuery(sql.toString());
        nativeQuery.setParameter("ids", ids);
        nativeQuery.executeUpdate();
        return Result.data("ok");
    }

    @Autowired
    private ApplicationContext applicationContext;
    /**
     * 生成接警
     * @param ids
     * @return
     */
    private Result<String> createAlarmRecord(List<Long> ids) {
        for (Long id :
                ids) {
            SESECDEcdAlertRecord load = alertRecordDao.load(id);
            if(load == null){
                continue;
            }
            //新增接警
            SESECDAlmAlarmRecord alarmRecord = getAlarmRecord(load);
            alarmRecordDao.save(alarmRecord);
            alarmRecordDao.flush();

            //更新状态
            load.setAlarmState(MY_HANDLE_ALARM_STATE);//报警状态
            alertRecordDao.save(load);
            alertRecordDao.flush();

            //接警记录操作
            ChangeLogDTO logDTO = ChangeLogDTO.builder().content("【接警】 接警人：" + getCurrentStaff().getName()).eventId(alarmRecord.getId()).type(ChangeLogBizTypeEnum.ALARM.getCode()).build();
            applicationContext.publishEvent(new ChangeLogEvent(this,logDTO));
        }
        return Result.data("ok");
    }


    /**
     * 获取报警记录
     * @param load
     * @return
     */
    private SESECDAlmAlarmRecord getAlarmRecord(SESECDEcdAlertRecord load) {
        SESECDAlmAlarmRecord alarmRecord = new SESECDAlmAlarmRecord();
        alarmRecord.setAccidentName(load.getAlarmContent()+ "_生成接警事件");//设置事件名称
        alarmRecord.setHappenTime(new Date());//事发时间
        alarmRecord.setPosition(load.getAlarmDeviceName());//发生位置
        alarmRecord.setDescription(load.getAlarmContent());//事件描述

        Staff staff = staffService.get(getCurrentStaff().getId());
        alarmRecord.setReceiver(staff);//接警人
        alarmRecord.setHpnCompany(companyService.get(getCurrentCompanyId()));//事发公司
        alarmRecord.setIsOver(false);//是否结束
        alarmRecord.setIsIncident(false);//是否应急事件
        return alarmRecord;
    }

}
