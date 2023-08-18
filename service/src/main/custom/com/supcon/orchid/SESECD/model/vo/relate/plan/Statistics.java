package com.supcon.orchid.SESECD.model.vo.relate.plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics{
    /**
     * 事件id
     */
    private Long id;

    /**
     * 数量
     */
    private Integer num;
}