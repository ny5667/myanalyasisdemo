package com.supcon.orchid.SESECD.model.vo.map;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlarmEventQueryVO {

    /**
     * 当前页码
     */
    private Integer pageNo;

    /**
     * 当前页数
     */
    private Integer pageSize;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 查询半径
     */
    private BigDecimal radius;

}
