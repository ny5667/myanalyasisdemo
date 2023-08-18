package com.supcon.orchid.SESECD.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class TypeAndPlanIdsVO {
    /**
     * 初始预案
     */
    private List<String> planIds;
    /**
     * 事故类型id
     */
    private List<Long> typeIds;
    /**
     * 报警等级
     */
    private String alarmLevel;

    /**
     * 坐标 todo 暂时不用 空间数据库暂不提供点是否在区域查询方法
     */
    private String mapPoint;
}
