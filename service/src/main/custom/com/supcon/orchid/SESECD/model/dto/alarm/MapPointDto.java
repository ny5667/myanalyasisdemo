package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.List;

public class MapPointDto {
	
	private Long id;
	private MapPoint pointInfos;
	
	public static class MapPoint{
		
		private String buildingPatchId;
		private Integer floor;
		private String layerCode;
		private List<Coordinates> coordinates;
		private String oldLayerCode;
		
		public static class Coordinates{
			
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


		public String getBuildingPatchId() {
			return buildingPatchId;
		}

		public void setBuildingPatchId(String buildingPatchId) {
			this.buildingPatchId = buildingPatchId;
		}

		public String getLayerCode() {
			return layerCode;
		}

		public void setLayerCode(String layerCode) {
			this.layerCode = layerCode;
		}

		public Integer getFloor() {
			return floor;
		}

		public void setFloor(Integer floor) {
			this.floor = floor;
		}

		public List<Coordinates> getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(List<Coordinates> coordinates) {
			this.coordinates = coordinates;
		}

		public String getOldLayerCode() {
			return oldLayerCode;
		}

		public void setOldLayerCode(String oldLayerCode) {
			this.oldLayerCode = oldLayerCode;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MapPoint getPointInfos() {
		return pointInfos;
	}

	public void setPointInfos(MapPoint pointInfos) {
		this.pointInfos = pointInfos;
	}

	
	
	
}
