package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.foundation.entities.Staff;
import lombok.Data;

@Data
public class StaffVO {
//com\supcon\greendill\foundation\com.supcon.greendill.foundation.core\6.1.8.00\com.supcon.greendill.foundation.core-6.1.8.00.jar!\com\supcon\orchid\foundation\entities\Staff.class

    /**
     * id
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮件
     */
    private String email;

    /**
     * 身份证
     */
    private String idCard;

    public static StaffVO StaffPO2VO(Staff input) {
        if(input == null){
            return null;
        }
        StaffVO reportPersonVO = new StaffVO();
        reportPersonVO.setCode(input.getCode());
        reportPersonVO.setEmail(input.getEmail());
        reportPersonVO.setId(input.getId());
        reportPersonVO.setMobile(input.getMobile());
        reportPersonVO.setName(input.getName());
        reportPersonVO.setIdCard(input.getIdCard());
        return reportPersonVO;
    }

    public static StaffVO  CreateStaffVOByName(Long id, String staffName) {
        StaffVO staffVO = new StaffVO();
        staffVO.setName(staffName);
        staffVO.setId(id);
        return staffVO;
    }
}
