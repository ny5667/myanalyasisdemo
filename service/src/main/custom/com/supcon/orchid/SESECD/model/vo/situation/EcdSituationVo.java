package com.supcon.orchid.SESECD.model.vo.situation;

public class EcdSituationVo {
	
	private String id;
	private String spatialId;
	private String occursTime;
	private String position;
	private String describtion;
	private String reportPerson;
	private Integer woundedPerson;
	private Integer deathPerson;
	private String source;
	private String situationType;

	public String getSituationType() {
		return situationType;
	}

	public void setSituationType(String situationType) {
		this.situationType = situationType;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpatialId() {
		return spatialId;
	}
	public void setSpatialId(String spatialId) {
		this.spatialId = spatialId;
	}
	public String getOccursTime() {
		return occursTime;
	}
	public void setOccursTime(String occursTime) {
		this.occursTime = occursTime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getReportPerson() {
		return reportPerson;
	}
	
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}
	public Integer getWoundedPerson() {
		return woundedPerson;
	}
	public void setWoundedPerson(Integer woundedPerson) {
		this.woundedPerson = woundedPerson;
	}
	public Integer getDeathPerson() {
		return deathPerson;
	}
	public void setDeathPerson(Integer deathPerson) {
		this.deathPerson = deathPerson;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}
