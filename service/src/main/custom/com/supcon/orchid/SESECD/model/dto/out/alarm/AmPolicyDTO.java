package com.supcon.orchid.SESECD.model.dto.out.alarm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmPolicyDTO {

    /**
     * 报警策略Id
     */
    private Long id;

    /**
     * 报警点Id
     */
    private Long alarmId;

    /**
     * 上限阈值
     */
    private String hv;

    /**
     * 上上限阈值
     */
    private String hhv;

    /**
     * 下限阈值
     */
    private String lv;

    /**
     *
     * 下下限阈值
     */
    private String llv;

    /**
     * 单位名称
     */
    private String unitName;

}
