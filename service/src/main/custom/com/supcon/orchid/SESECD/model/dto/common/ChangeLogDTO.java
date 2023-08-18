package com.supcon.orchid.SESECD.model.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作记录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeLogDTO {
    /**
     * 事件id
     */
    private Long eventId;

    /**
     * 记录内容
     */
    private String content;

    /**
     * 业务类型（态势 行动 接警）
     */
    private String type;

    /**
     * 业务关联id
     */
    private Long realId;

}
