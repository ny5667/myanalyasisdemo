package com.supcon.orchid.SESECD.model.vo.common;

import lombok.Data;

/**
 * 坐标
 */
@Data
public class CoordinateVO {
    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 高度
     */
    private Float hei;

    public CoordinateVO(String lon, String lat, Float hei) {
        this.lon = lon;
        this.lat = lat;
        this.hei = hei;
    }
}
