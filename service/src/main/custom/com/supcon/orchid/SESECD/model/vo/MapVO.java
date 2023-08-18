package com.supcon.orchid.SESECD.model.vo;

/**
 * @ClassName MapDto
 * @Description TODO
 * @Author shaochengcheng
 * @Date 2021/5/25 14:20
 */
public class MapVO {

    private String buildingPatchId;
    private String floor;
    private String layerCode;
    private String oldLayerCode;
    private MapCoordinatesVO[] coordinates;

    private int extHeight;

    public int getExtHeight() {
        return extHeight;
    }

    public void setExtHeight(int extHeight) {
        this.extHeight = extHeight;
    }
  
    public String getBuildingPatchId() {
        return buildingPatchId;
    }

    public void setBuildingPatchId(String buildingPatchId) {
        this.buildingPatchId = buildingPatchId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLayerCode() {
        return layerCode;
    }

    public void setLayerCode(String layerCode) {
        this.layerCode = layerCode;
    }

    public String getOldLayerCode() {
        return oldLayerCode;
    }

    public void setOldLayerCode(String oldLayerCode) {
        this.oldLayerCode = oldLayerCode;
    }

    public MapCoordinatesVO[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(MapCoordinatesVO[] coordinates) {
        this.coordinates = coordinates;
    }
}
