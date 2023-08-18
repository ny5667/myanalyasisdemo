package com.supcon.orchid.SESECD.model.vo.map;

import lombok.Data;

@Data
public class AlertRecordVO {

    /**
     * id
     */
    private String id;

    /**
     * 空间数据ID
     */
    private String spatialId;

    /**
     * 报警装置名称
     */
    private String alarmDeviceName;

    /**
     * 报警详情
     */
    private String alarmContent;


}
