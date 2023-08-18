package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.foundation.entities.Department;
import lombok.Data;

/**
 * 所属部门
 */
@Data
public class DepartmentVO {
    /**
     * id
     */
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 所属公司id
     */
    private Long companyId;
    /**
     * 描述
     */
    private String description;
    /**
     * 组织路径
     */
    private String fullPath;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 上级部门id
     */
    private Long parentId;
    /**
     * 负责人id
     */
    private Long managerId;


    public static DepartmentVO DepartmentPO2VO(Department input) {
        if(input == null){
            return null;
        }
        DepartmentVO vo = new DepartmentVO();
        vo.setId(input.getId());
        vo.setName(input.getName());
        vo.setCode(input.getCode());
        vo.setDescription(input.getDescription());
        vo.setFullPath(input.getFullPathName());
        vo.setCompanyId(input.getCid());
        vo.setManagerId(input.getManager()!=null?input.getManager().getId():null);
        vo.setParentId(input.getParentId());
        return vo;
    }
}
