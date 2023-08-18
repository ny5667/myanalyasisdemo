package com.supcon.orchid.SESECD.model.vo.alarm;

import lombok.Getter;
import lombok.Setter;


/**
 * @version video 6.0
 * @name
 * @author： luoheng
 * @date： 2021-05-24 10:40
 */
@Getter
@Setter
public class AlarmRecordVO {

    /**
     *  接警时间
     */
    private String rectime;

    /**
     * 事件结束时间
     */
    private String overTime;
}
