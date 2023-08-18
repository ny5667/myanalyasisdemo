package com.supcon.orchid.SESECD.model.dto.alarm;

import java.math.BigDecimal;

/**
 * Created by ludunyue on 2020/4/19.
 */
public class LayerDto {

    private BigDecimal lon;
    private BigDecimal lat;
    private BigDecimal hei;

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getHei() {
        return hei;
    }

    public void setHei(BigDecimal hei) {
        this.hei = hei;
    }
}
