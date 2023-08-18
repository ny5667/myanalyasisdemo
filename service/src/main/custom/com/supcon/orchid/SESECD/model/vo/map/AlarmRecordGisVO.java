package com.supcon.orchid.SESECD.model.vo.map;

import lombok.Data;

import java.util.List;

@Data
public class AlarmRecordGisVO {

    /**
     * id
     */
    private String id;

    /**
     * 空间数据ID
     */
    private String spatialId;

    /**
     * 事件名称
     */
    private String accidentName;

    /**
     * 接警人
     */
    private String receiver;

    /**
     * 接警时间
     */
    private String rectime;

    /**
     * 事发地点
     */
    private String position;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 事发时间
     */
    private String happenTime;

    /**
     * 事发单位
     */
    private String hpnCompany;

    /**
     * 事故类型
     */
    private String accidentType;

    /**
     * 处理状态
     */
    private String processState;

    /**
     * 是否应急事件
     */
    private Boolean isIncident;

    /**
     * 上报人
     */
    private String alarmPerson;

    /**
     * 上报人电话
     */
    private String alarmPhone;

    /**
     * 事件等级
     */
    private String alarmLevel;

    /**
     * 受伤人数
     */
    private Integer wounderPerson;

    /**
     * 死亡人数
     */
    private Integer deathPerson;

    /**
     * 处理记录
     */
    private String process;

    /**
     * 关联预案
     */
    List<EmergencyPlanVO> emergencyPlanVoList;

}
