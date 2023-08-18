package com.supcon.orchid.SESECD.model.dto.alarm;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeLevelDTO {
    /**
     * 1:降级 2：升级
     */
    @NotNull(message = "operation is empty")
    private Integer operation;
    /**
     * 事件id
     */
    @NotNull(message = "eventId is empty")
    private Long eventId;
}
