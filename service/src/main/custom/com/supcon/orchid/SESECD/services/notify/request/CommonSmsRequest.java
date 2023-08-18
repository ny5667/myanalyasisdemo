package com.supcon.orchid.SESECD.services.notify.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 三方发短信统一请求体
 * 使用过程中若缺少 则新增需要字段
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommonSmsRequest {
    /**
     * 手机号码集合
     */
    private List<String> mobileList;
    /**
     * 短信内容
     */
    private String content;
}
