package com.supcon.orchid.SESECD.model.dto.out.alarm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AmHistoryDTO {

    /**
     * 报警点Id
     */
    private Long alarmId;

    /**
     * 报警策略
     */
    private Long policyId;

    /**
     * 报警记录标识
     */
    private String recordId;

    /**
     * 报警点名
     */
    private String alarmName;

    /**
     * 持续时长（秒）
     */
    private Long durationSecond;

    /**
     * 报警位号
     */
    private String relationTags;

    /**
     * 报警状态
     */
    private String alarmState;

    /**
     * 报警产生时间
     */
    private Date alarmTime;

    /**
     * 报警解除时间
     */
    private Date lifeTime;

    /**
     * 持续天数
     */
    private  Integer durationDays;

    /**
     * 持续时间
     */
    private String durationTime;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警等级
     */
    private String alarmLevel;

    /**
     * 报警值
     */
    private String alarmValue;

    /**
     * 报警详情
     */
    private String description;

    /**
     * 报警解除方式
     */
    private String endType;

    /**
     * 解除值
     */
    private String endValue;

    /**
     * 结束时阀值
     */
    private String lifeValue;

    /**
     * 报警来源
     */
    private String alarmOrigin;

    /**
     *
     * 报警时阀值
     */
    private String limitValue;

}
