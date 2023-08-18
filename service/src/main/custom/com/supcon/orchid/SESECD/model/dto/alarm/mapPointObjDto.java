package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.List;
public class mapPointObjDto {
	
	private String buildingPatchId;
	private Integer floor;
	private List<MapPoints> coordinates;
	private String layerCode;
	private String oldLayerCode;
	
	public static class MapPoints{
		
		private Float lon;
		private Float lat;
		private Float hei;
		public Float getLon() {
			return lon;
		}
		public void setLon(Float lon) {
			this.lon = lon;
		}
		public Float getLat() {
			return lat;
		}
		public void setLat(Float lat) {
			this.lat = lat;
		}
		public Float getHei() {
			return hei;
		}
		public void setHei(Float hei) {
			this.hei = hei;
		}
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

	public List<MapPoints> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<MapPoints> coordinates) {
		this.coordinates = coordinates;
	}


	public String getBuildingPatchId() {
		return buildingPatchId;
	}

	public void setBuildingPatchId(String buildingPatchId) {
		this.buildingPatchId = buildingPatchId;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	
	
}
