package com.supcon.orchid.SESECD.model.vo.forfront.icon;

import lombok.Data;

@Data
public class IconLibraryVO {
    /**
     * id
     */
    private Long id;
    /**
     * 图标名称
     */
    private String name;
    /**
     * 地址
     */
    private String url;
    /**
     * 类型
     */
    private String typeName;
}
