package com.supcon.orchid.SESECD.model.vo.doorAccessControl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 主用与告知前端字段含义和类型
 */
@Data
@AllArgsConstructor
public class HeadVo {

    /**
     * 字段名
     */
    private String name;

    /**
     * 标签类型
     */
    private String fieldtype;

    /**
     * 字段标签
     */
    private String labelname;
}
