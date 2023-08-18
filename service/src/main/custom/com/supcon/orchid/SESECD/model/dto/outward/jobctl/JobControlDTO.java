package com.supcon.orchid.SESECD.model.dto.outward.jobctl;

import lombok.Data;

@Data
public class JobControlDTO {
    /**
     *  经度
     */
    private String longitude;
    /**
     * 维度
     */
    private String latitude;
    /**
     * 作业时间
     */
    private String workTime;
    /**
     * 距离半径 米
     */
    private String radius;
}
