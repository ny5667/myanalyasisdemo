package com.supcon.orchid.SESECD.model.vo.broadcast.request;

import lombok.Data;

/**
 * 海康：开机广播接口参数
 */
@Data
public class HikvisionStartBroadcastH5URLResponse {

    /**
     * 广播URL
     */
    private String szBroadCastUrl;

    /**
     * 广播点位，多个点位以逗号分割
     */
    private String szBroadcastIndexcodes;
}
