package com.supcon.orchid.SESECD.model.vo.broadcast;

import lombok.Data;

import java.util.List;

@Data
public class StartBroadcastVO {

    /**
     * 广播地址
     */
    private String url;

    /**
     * 广播点位
     */
    private List<String> indexCodes;
}
