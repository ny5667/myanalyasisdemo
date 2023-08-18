package com.supcon.orchid.SESECD.model.dto.action;

import lombok.Data;

@Data
public class EcdWsDTO {
    /**
     * 行动id
     */
    private Long actionId;
    private Long situationId;
    /**
     * 事件id
     */
    private Long eventId;
    private Long instructionId;
}
