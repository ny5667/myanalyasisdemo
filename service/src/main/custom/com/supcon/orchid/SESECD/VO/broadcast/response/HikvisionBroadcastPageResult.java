package com.supcon.orchid.SESECD.VO.broadcast.response;

import lombok.Data;

import java.util.List;

/**
 * 分页结果
 */
@Data
public class HikvisionBroadcastPageResult {

    /**
     * 总数
     */
    private String total;

    /**
     * 页码
     */
    private String pageNo;

    /**
     * 每页总数
     */
    private String pageSize;

    private List<HikvisionBroadcastPoint> list;
}
