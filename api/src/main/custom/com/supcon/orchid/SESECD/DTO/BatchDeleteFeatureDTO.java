package com.supcon.orchid.SESECD.DTO;

import java.util.List;

public class BatchDeleteFeatureDTO {
    private String layerName;
    private String layerCode;
    private List<String> ids;

    public BatchDeleteFeatureDTO() {
    }

    public String getLayerName() {
        return this.layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerCode() {
        return this.layerCode;
    }

    public void setLayerCode(String layerCode) {
        this.layerCode = layerCode;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
