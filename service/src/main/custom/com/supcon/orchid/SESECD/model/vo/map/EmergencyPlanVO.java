package com.supcon.orchid.SESECD.model.vo.map;

import lombok.Data;

@Data
public class EmergencyPlanVO {

    /**
     * id
     */
    private String id;
    /**
     * 预案名称
     */
    private String planName;

    /**
     * 预案类型
     */
    private String planType;
    /**
     * 预案版本号
     */
    private String planVersion;

}
