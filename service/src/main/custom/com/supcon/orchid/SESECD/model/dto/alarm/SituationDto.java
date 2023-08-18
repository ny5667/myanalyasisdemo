package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.Date;

public class SituationDto {
	
	private Long eventId;
	private String describtion;
	private String occursTime;
	private Date occursTimeDate;
	private String position;
	private Long stateId;
	private Long reportPerson;
	private String reportPersonName;
    private String situationType;//态势状态
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public String getOccursTime() {
		return occursTime;
	}
	public void setOccursTime(String occursTime) {
		this.occursTime = occursTime;
	}

	public Date getOccursTimeDate() {
		return occursTimeDate;
	}

	public void setOccursTimeDate(Date occursTimeDate) {
		this.occursTimeDate = occursTimeDate;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public Long getReportPerson() {
		return reportPerson;
	}
	public String getReportPersonName() {
		return reportPersonName;
	}
	public void setReportPersonName(String reportPersonName) {
		this.reportPersonName = reportPersonName;
	}
	public void setReportPerson(Long reportPerson) {
		this.reportPerson = reportPerson;
	}
    public String getSituationType() {
		return situationType;
	}

	public void setSituationType(String situationType) {
		this.situationType = situationType;
	}
	
	
	
}
