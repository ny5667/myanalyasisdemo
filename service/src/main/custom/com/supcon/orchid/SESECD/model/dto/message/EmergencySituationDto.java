package com.supcon.orchid.SESECD.model.dto.message;

public class EmergencySituationDto {
	
	private String reportPerson;
	private String occursTime;
	private String position;
	private String describtion;
	private Integer woundedPerson;
	private Integer  deathPerson;
	private String accidentName;
	public String getReportPerson() {
		return reportPerson;
	}
	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
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
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
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
	public String getAccidentName() {
		return accidentName;
	}
	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
	}
	
	
	
}
