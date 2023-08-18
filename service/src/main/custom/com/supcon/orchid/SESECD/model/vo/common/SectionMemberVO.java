package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Data;

/**
 * 通讯成员
 */
@Data
public class SectionMemberVO {
    /**
     * 通讯组成员id
     */
    private Long id;

    /**
     * staff
     */
    private StaffVO staff;
}
