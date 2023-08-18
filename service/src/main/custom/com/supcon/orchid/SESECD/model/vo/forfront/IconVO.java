package com.supcon.orchid.SESECD.model.vo.forfront;

import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class IconVO {

    private static final Logger logger = LoggerFactory.getLogger(IconVO.class);

    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;
    /**
     * 图标名称
     */
    private String name;
    /**
     * 图标路径
     */
    private PictureFileVO pathUrl;
    /**
     * 图标http路径
     */
    private String url;
    /**
     * 图标描述
     */
    private String remark;
    /**
     * 图标类型
     */
    private String typeId;

    /**
     * 转换为图标库VO
     *
     * @param input 图标库PO
     * @return 图标库VO
     */
    public static IconVO IconPO2VO(SESGISConfigIconLibrary input) {
        if (input == null) {
            return null;
        }
        IconVO vo = new IconVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setName(input.getName());
        vo.setPathUrl(null);
        vo.setUrl(input.getIconUrl());
        return vo;
    }

}
