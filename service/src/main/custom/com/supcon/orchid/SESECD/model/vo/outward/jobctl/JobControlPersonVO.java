package com.supcon.orchid.SESECD.model.vo.outward.jobctl;

import lombok.Data;

@Data
public class JobControlPersonVO {

    /**
     *  员工名称
     */
    private String uname;
    /**
     *  员工性别
     */
    private String usex;
    /**
     *  员工邮箱
     */
    private String uemail;
    /**
     * 员工电话
     */
    private String umobile;

}
