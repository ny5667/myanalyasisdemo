package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Data;

/**
 * 文件VO
 */
@Data
public class PictureFileVO {
    /**
     * id
     */
    private Long id;
    /**
     * linkId
     */
    private Long linkId;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
}
