package com.supcon.orchid.SESECD.constant.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 是否启用枚举
 */
@Getter
public enum EnableEnum {
    disable("0", "未启动"),
    enable("1", "启动");

    String code;
    String msg;

    EnableEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static EnableEnum getByCode(String code) {
        return Arrays.stream(values()).filter(enableEnum -> enableEnum.getCode().equals(code)).findFirst().get();
    }
}
