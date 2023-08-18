package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.foundation.entities.Company;
import lombok.Data;

@Data
public class CompanyVO {
    /**
     * id
     */
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司编码
     */
    private String code;
    /**
     * 公司层级
     */
    private String fullPath;
    /**
     * 公司简称
     */
    private String shortName;
    /**
     * 描述
     */
    private String description;
    /**
     * 上级公司id
     */
    private Long parentId;

    public static CompanyVO CompanyVOPO2VO(Company input) {
        if(input == null){
            return null;
        }
        CompanyVO vo = new CompanyVO();
        vo.setId(input.getId());
        vo.setName(input.getName());
        vo.setCode(input.getCode());
        vo.setDescription(input.getDescription());
        vo.setFullPath(input.getLayRec());
        vo.setShortName(input.getShortName());
        vo.setParentId(input.getParentId());
        return vo;
    }
}
