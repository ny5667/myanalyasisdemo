package com.supcon.orchid.SESECD.model.dto.alarm;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlarmCheckDTO {

    /**
     * 接警id
     */
    @NotNull(message = "SESECD.custom.random1692248934977")
    private Long alarmId;

    /**
     * 响应分级
     */
    private String  responseLevel;

    /**
     * 事件分类
     */
    private String evType;
}
