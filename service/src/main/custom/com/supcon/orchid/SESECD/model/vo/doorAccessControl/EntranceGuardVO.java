package com.supcon.orchid.SESECD.model.vo.doorAccessControl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntranceGuardVO {
    /**
     * 门禁id
     */
    private String id;

    /**
     * 空间数据库ID
     */
    private String spatialId;

    /**
     * 开关状态
     */
    private String entranceGuardStatus;

    /**
     * 门禁名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

}
