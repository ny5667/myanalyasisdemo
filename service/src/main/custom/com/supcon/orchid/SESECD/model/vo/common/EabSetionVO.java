package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.SESEAB.entities.SESEABEabSetion;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.EmcPlanActionVO;
import com.supcon.orchid.SESECD.model.vo.emergencyplan.SectionMemberVO;
import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class EabSetionVO {
    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;
    /**
     * 所属部门
     */
    private DepartmentVO belongDepartment;
    /**
     * 职责描述
     */
    private String dutyDescription;
    /**
     * 通讯组名称
     */
    private String sectionName;
    /**
     * 通讯编码
     */
    private String sectionPostcode;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 小组成员
     */
    private List<SectionMemberVO> sectionMembers;
    /**
     * 行动指令
     */
    private List<EmcPlanActionVO> emcPlanActionVOS;

    private OrgDept orgDept;

    /**
     *
     * @param input
     * @param sectionMemberVOS
     * @param emcPlanActionVOS
     * @return
     */
    public static EabSetionVO EabSectionVOPO2VO(SESEABEabSetion input, Map<Long,List<SectionMemberVO>> sectionMemberVOS , Map<String,List<EmcPlanActionVO>> emcPlanActionVOS) {
        if(input == null){
            return null;
        }
        EabSetionVO vo = new EabSetionVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        vo.setOrgDept(OrgDept.OrgDeptPO2VO(input.getOrgDept()));
        vo.setDutyDescription(input.getDutyDescription());
        vo.setSectionName(input.getSectionName());
        vo.setSectionPostcode(input.getSectionPostcode());
        vo.setTelephone(input.getTelephone());
        vo.setSectionMembers(null != input.getId()?sectionMemberVOS.get(input.getId()):null);
        vo.setEmcPlanActionVOS(null != input.getId()?emcPlanActionVOS.get(input.getId()):null);
        return vo;
    }


    public static EabSetionVO SetionPO2VO(SESEABEabSetion input) {
        EabSetionVO vo = new EabSetionVO();
        vo.setId(input.getId());
        vo.setDutyDescription(input.getDutyDescription());
        vo.setSectionName(input.getSectionName());
        vo.setTelephone(input.getTelephone());
        vo.setSectionPostcode(input.getSectionPostcode());
        if (null != input.getBelongDepartment()) {
            vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        }
        vo.setOrgDept(OrgDept.OrgDeptPO2VO(input.getOrgDept()));
        return vo;
    }

}
