package com.supcon.orchid.SESECD.model.dto.out.icon.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyIconLibraryInfoDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 公司cid
     */
    private Long cid;
    /**
     * 图标
     */
    private String name;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 图片类型id
     */
    private Long typeId;
    /**
     * 图片类型名称
     */
    private String typeName;

}
