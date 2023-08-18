package com.supcon.orchid.SESECD.model.vo.doorAccessControl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BaseShellVo<T> {
    /**
     * 封装字段含义和类型
     */
    private List<HeadVo> head;

    /**
     * 传输数据
     */
    private List<T> data;
}
