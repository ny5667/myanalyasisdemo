package com.supcon.orchid.SESECD.model.vo.forfront;

import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 */
@Data
public class ChangeLogVO {
    /**
     * 操作类型
     *
     * @see com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum
     */
    private String bizType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 具体内容
     */
    private String content;

    /**
     * 业务关联
     */
    private Integer realId;

    /**
     * point 业务关联点位
     */
    private PointVO point;

    /**
     * 关联事件id
     */
    private Long eventId;

}
