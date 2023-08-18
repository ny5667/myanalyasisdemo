package com.supcon.orchid.SESECD.model.vo.relate.plan;

import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class EnableChangStateVO<T> {

    private Boolean code;
    private T data;
    private String msg;

    public static <T> EnableChangStateVO UPDATE(T data) {
        return new EnableChangStateVO(true, "状态已修改",data);
    }

    public static <T> EnableChangStateVO INERT(T data) {
        return new EnableChangStateVO(true, "关联新增",data);
    }

    public static <T> EnableChangStateVO FORBIDDEN(String msg, T data) {
        return new EnableChangStateVO(false,msg,data);
    }
    public static <T> EnableChangStateVO OTHER(T data) {
        return new EnableChangStateVO(true,"",data);
    }

    EnableChangStateVO(Boolean code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data =data;
    }
}
