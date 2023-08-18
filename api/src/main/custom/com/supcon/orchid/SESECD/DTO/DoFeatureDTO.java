package com.supcon.orchid.SESECD.DTO;

import java.math.BigDecimal;
import java.util.List;

public class DoFeatureDTO {
    private String layerName;
    private String id;
    private List<PointDTO> points;
    private Double floor;
    private BigDecimal height;
    private String layer;
    private String buildingPatchId;
    private BigDecimal extHeight;
    private BigDecimal roll;
    private BigDecimal pitch;
    private BigDecimal heading;

    public DoFeatureDTO() {
    }

    public String getLayerName() {
        return this.layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PointDTO> getPoints() {
        return this.points;
    }

    public void setPoints(List<PointDTO> points) {
        this.points = points;
    }

    public Double getFloor() {
        return this.floor;
    }

    public void setFloor(Double floor) {
        this.floor = floor;
    }

    public BigDecimal getHeight() {
        return this.height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getLayer() {
        return this.layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getBuildingPatchId() {
        return this.buildingPatchId;
    }

    public void setBuildingPatchId(String buildingPatchId) {
        this.buildingPatchId = buildingPatchId;
    }

    public BigDecimal getExtHeight() {
        return this.extHeight;
    }

    public void setExtHeight(BigDecimal extHeight) {
        this.extHeight = extHeight;
    }

    public BigDecimal getRoll() {
        return this.roll;
    }

    public void setRoll(BigDecimal roll) {
        this.roll = roll;
    }

    public BigDecimal getPitch() {
        return this.pitch;
    }

    public void setPitch(BigDecimal pitch) {
        this.pitch = pitch;
    }

    public BigDecimal getHeading() {
        return this.heading;
    }

    public void setHeading(BigDecimal heading) {
        this.heading = heading;
    }
}
