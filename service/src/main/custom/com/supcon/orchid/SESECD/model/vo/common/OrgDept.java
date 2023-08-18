package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.SESEAB.entities.SESEABOrgDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrgDept {
    /**
     * id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    public static OrgDept OrgDeptPO2VO(SESEABOrgDept input){
        if (Objects.isNull(input)) {
            log.error("所属组织为为空："+ Objects.isNull(input));
            return  null;
        }
        OrgDept orgDept = new OrgDept();
        orgDept.setId(input.getId());
        orgDept.setCode(input.getCode());
        orgDept.setName(input.getName());
        return orgDept;
    }
}
