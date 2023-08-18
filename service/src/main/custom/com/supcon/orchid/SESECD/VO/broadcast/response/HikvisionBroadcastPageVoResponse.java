package com.supcon.orchid.SESECD.VO.broadcast.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 海康广播点分页请求结果
 */
@Data
@NoArgsConstructor
public class HikvisionBroadcastPageVoResponse {
    private String code ;

    private String msg;

    private HikvisionBroadcastPageResult data;
}
