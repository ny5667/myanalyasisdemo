package com.supcon.orchid.SESECD.model.vo.emergencyplan;

import com.supcon.orchid.SESEAB.entities.SESEABSetionmember;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import lombok.Data;

@Data
public class SectionMemberVO {
    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;
    /**
     * 成员
     */
    private StaffVO name;
    /**
     * 角色
     */
    private SystemCodeVO role;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 电话
     */
    private String telephone;

    public static SectionMemberVO SectionMemberVOPO2VO(SESEABSetionmember input) {
        if(input == null){
            return null;
        }
        SectionMemberVO vo = new SectionMemberVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setName(StaffVO.StaffPO2VO(input.getPersonId()));
        vo.setRole(SystemCodeVO.CreateSystemCodeVO(input.getRole()));
        vo.setMobile(input.getMobile());
        vo.setTelephone(input.getTelephone());
        return vo;
    }


}

