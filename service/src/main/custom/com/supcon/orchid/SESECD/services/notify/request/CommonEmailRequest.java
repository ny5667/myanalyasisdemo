package com.supcon.orchid.SESECD.services.notify.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 三方发邮件统一请求体
 * 使用过程中若缺少 则新增需要字段
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommonEmailRequest {
    /**
     * 邮件标题
     */
    private String title;
    /**
     * 邮件内容
     */
    private String content;
    /**
     * 收件人集合
     */
    private List<ThirdEmailReceiverRequest> toList;
    /**
     * 抄送人集合
     */
    private List<ThirdEmailReceiverRequest> ccList;
}

