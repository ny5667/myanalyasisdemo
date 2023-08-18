package com.supcon.orchid.SESECD.constant.enums;

import lombok.Getter;

/**
 * 操作记录业务类型
 */
@Getter
public enum ChangeLogBizTypeEnum {
    /**
     * 发布指令
     */
    COMMAND("command"),
    /**
     * 接警
     */
    ALARM("alarm"),
    /**
     * 行动
     */
    ACTION("action"),
    /**
     * 态势
     */
    SITUATION("situation"),

    /**
     * 事件等级
     */
    LEVEL("eventLevel"),

    /**
     * 预案
     */
    EMERGENCY("emergency");

    private String code;

    ChangeLogBizTypeEnum(String code) {
        this.code = code;
    }
}
