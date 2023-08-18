package com.supcon.orchid.SESECD.model.vo.alertRecord;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VxECDVxAlertRecordVO {

    /**
     * id
     */
    private Long id;

    /**
     * 空间id
     */
    private String spatialId;

    /**
     * 报警编码
     */
    private String alarmCode;

    /**
     * 报警装置名称
     */
    private String alarmDeviceName;

    /**
     * 报警产生时间
     */
    private String alarmTime;

    /**
     * 报警装置坐标经度
     */
    private BigDecimal deviceLocationX;

    /**
     * 恢复值
     */
    private String endValue;

    /**
     * 上限阈值
     */
    private String hv;

    /**
     * 下限阈值
     */
    private String lv;

    /**
     * 下下限阈值
     */
    private String llv;

    /**
     * 报警装置坐标纬度
     */
    private BigDecimal deviceLocationY;

    /**
     * 报警详情
     */
    private String alarmContent;

    /**
     * 报警值
     */
    private BigDecimal realTimeValue;

    /**
     * 报警持续时间（单位秒）
     */
    private Integer duration;

    /**
     * recordId
     */
    private String recordId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 恢复类型
     */
    private String endType;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警等级
     */
    private String alarmLevel;

    /**
     * 解除时阈值
     */
    private String lifeValue;

    /**
     * 持续天数
     */
    private Integer durationdays;

    /**
     * 持续时间
     */
    private String durationTime;

    /**
     * 报警来源
     */
    private String alarmOrigin;

    /**
     * 上上限阈值
     */
    private String hhv;

    /**
     * 报警结束时间
     */
    private String lifeTime;

    /**
     * 报警点名
     */
    private String alarmName;

    /**
     * 报警状态
     */
    private String alarmState;

    /**
     * 报警表来源
     */
    private String alarmTableSource;

    /**
     * 报警时阀值
     */
    private String limitValue;

}
