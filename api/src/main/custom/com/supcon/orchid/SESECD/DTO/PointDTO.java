package com.supcon.orchid.SESECD.DTO;

import java.math.BigDecimal;

public class PointDTO {
    private String lon;
    private String lat;
    private BigDecimal hei;

    public PointDTO() {
    }

    public PointDTO(String lon, String lat, BigDecimal hei) {
        this.lon = lon;
        this.lat = lat;
        this.hei = hei;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public BigDecimal getHei() {
        return this.hei;
    }

    public void setHei(BigDecimal hei) {
        this.hei = hei;
    }
}