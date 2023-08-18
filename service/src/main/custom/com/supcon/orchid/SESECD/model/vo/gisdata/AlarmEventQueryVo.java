package com.supcon.orchid.SESECD.model.vo.gisdata;

import java.math.BigDecimal;

/**
 * Created by ludunyue on 2020/4/16.
 */
public class AlarmEventQueryVo {

    private Integer pageNo;
    private Integer pageSize;
    private BigDecimal lat;
    private BigDecimal lon;
    private BigDecimal radius;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }
}
