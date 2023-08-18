package com.supcon.orchid.SESECD.constant.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 发送方式枚举类
 * 若新增三方实现吗，可继续增加
 */
@Getter
public enum NotifyWayEnum {
    SMS("sms"),
    EMAIL("email");

    String type;

    NotifyWayEnum(String type) {
        this.type = type;
    }

    public static NotifyWayEnum getByCode(String code) {
        return Arrays.stream(values()).filter(enableEnum -> {
            return enableEnum.getType().equals(code);
        }).findFirst().get();
    }
}
