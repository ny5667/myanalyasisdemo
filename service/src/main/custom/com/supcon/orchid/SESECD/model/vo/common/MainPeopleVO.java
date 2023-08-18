package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Data;

@Data
public class MainPeopleVO {

    /**
     * 责任人表id
     */
    private Long id;

    /**
     * 通讯组成员对象
     */
    private SetionMemberVO ownPerson;
}
