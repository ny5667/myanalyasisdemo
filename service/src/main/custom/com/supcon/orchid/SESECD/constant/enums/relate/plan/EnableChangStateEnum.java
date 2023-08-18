package com.supcon.orchid.SESECD.constant.enums.relate.plan;

import lombok.Getter;


@Getter
public enum EnableChangStateEnum {
    UPDATE(true, "状态已修改",""),
    INERT(true, "关联新增","" ),
    FORBIDDEN(false,"禁止修改",""),

    OTHER(true,"","");



    Boolean code;
    String data;
    String msg;

    EnableChangStateEnum(Boolean code, String msg,String data) {
        this.code = code;
        this.msg = msg;
        this.data =data;
    }
}
