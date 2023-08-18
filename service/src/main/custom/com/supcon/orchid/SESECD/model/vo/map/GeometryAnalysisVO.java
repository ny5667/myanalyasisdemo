package com.supcon.orchid.SESECD.model.vo.map;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

public class GeometryAnalysisVO {
    private String id;
    private BigDecimal height;
    private Double floor;
    private List<PointVO> geom;
    private String layer;
    private BigDecimal  distance;
    private String buildingPatchId;
    private BigDecimal extHeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Double getFloor() {
        return floor;
    }

    public void setFloor(Double floor) {
        this.floor = floor;
    }

    public List<PointVO> getGeom() {
        return geom;
    }

    public void setGeom(List<PointVO> geom) {
        this.geom = geom;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public String getBuildingPatchId() {
        return buildingPatchId;
    }

    public void setBuildingPatchId(String buildingPatchId) {
        this.buildingPatchId = buildingPatchId;
    }

    public BigDecimal getExtHeight() {
        return extHeight;
    }

    public void setExtHeight(BigDecimal extHeight) {
        this.extHeight = extHeight;
    }
}
