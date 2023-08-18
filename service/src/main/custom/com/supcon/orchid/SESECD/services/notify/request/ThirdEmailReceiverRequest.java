package com.supcon.orchid.SESECD.services.notify.request;

import lombok.Data;

@Data
public class ThirdEmailReceiverRequest {
    /**
     * 人员姓名
     */
    private String receiver;
    /**
     * 人员邮箱地址
     */
    private String address;
}
