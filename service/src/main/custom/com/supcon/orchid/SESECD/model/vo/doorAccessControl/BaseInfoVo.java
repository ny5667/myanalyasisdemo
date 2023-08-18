package com.supcon.orchid.SESECD.model.vo.doorAccessControl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfoVo {

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 结果对象
     */
    private Object result;

    /**
     * 成功结果
     */
    private boolean success;

    public static BaseInfoVo OK (Object result) {
        return new BaseInfoVo("", result, true);
    }
}
