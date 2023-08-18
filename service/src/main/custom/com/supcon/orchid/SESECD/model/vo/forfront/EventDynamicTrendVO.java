package com.supcon.orchid.SESECD.model.vo.forfront;


import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import lombok.Data;
import java.util.Date;

/**
 * 事件动态:包括应急行动和应急态势
 */
@Data
public class EventDynamicTrendVO {
    /**
     * 事件动态类型 action:应急行动  situation 态势
     */
    private String eventTrendType;

    /**
     * 发生时间
     */
    private String occurTime;

    /**
     * 为了排序的时间
     */
    private Date occurTimeForSort;

    /**
     * 具体内容
     */
    private String content;

    /**
     * 态势或行动对应的id
     */
    private Long contentId;

    /**
     * point 字符串格式
    private String pointStr;
     */

    /**
     * point 点位
     */
    private PointVO point;
}
