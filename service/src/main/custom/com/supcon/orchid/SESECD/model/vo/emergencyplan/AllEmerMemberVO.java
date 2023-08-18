package com.supcon.orchid.SESECD.model.vo.emergencyplan;

import lombok.Getter;
import lombok.Setter;

/**
 * @version video 6.0
 * @name     所有应急小组输出数据
 * @author： luoheng
 * @date： 2021-03-22 15:11
 */
@Getter
@Setter
public class AllEmerMemberVO {

    /**
     * 所属部门id
     */
    private String belongDepartment;

    /**
     *  手机号
     */
    private String mobile;

    /**
     * 人员ID
     */
    private Long personId;

    /**
     * 人员姓名
     */
    private String personName;
    
    /**
     * 人员编码
     */
    private String staffCode;

    /**
     * 通讯组名称
     */
    private String sectionName;

    /**
     * 应急电话
     */
    private String telephone;

    /**
     * 是否是子节点
     */
    private Boolean leaf;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 菜单等级
     */
    private Integer layNo;

    /**
     * 子节点
     */
    private String layRec;


}
