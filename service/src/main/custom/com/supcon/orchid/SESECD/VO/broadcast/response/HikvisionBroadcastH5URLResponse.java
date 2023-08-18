package com.supcon.orchid.SESECD.VO.broadcast.response;

import lombok.Data;

/**
 * 海康获取H5实时广播URL相应结果
 */
@Data
public class HikvisionBroadcastH5URLResponse {
    private String code;

    private String msg;

    private String data;
}
