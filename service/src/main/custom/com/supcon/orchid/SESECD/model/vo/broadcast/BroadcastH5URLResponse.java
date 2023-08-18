package com.supcon.orchid.SESECD.model.vo.broadcast;

import lombok.Data;

/**
 * 获取H5实时广播URL相应结果
 */
@Data
public class BroadcastH5URLResponse {
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 实时喊话url
     */
    private String url;
}
