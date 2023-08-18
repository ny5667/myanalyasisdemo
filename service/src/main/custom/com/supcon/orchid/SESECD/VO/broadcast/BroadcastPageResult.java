package com.supcon.orchid.SESECD.VO.broadcast;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 分页参数
 */
@Data
public class BroadcastPageResult<T> {

    /**
     * 总数
     */
    private Integer total;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页总数
     */
    private Integer pageSize;

    /**
     * 数据
     */
    private List<T> data;

}
