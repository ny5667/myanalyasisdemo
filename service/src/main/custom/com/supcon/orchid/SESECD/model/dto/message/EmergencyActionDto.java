package com.supcon.orchid.SESECD.model.dto.message;

public class EmergencyActionDto {
	
	private String actionAddr;
	private String actionTime;
	private String description;
	private String actionState;
	private String accidentName;
	public String getActionAddr() {
		return actionAddr;
	}
	public void setActionAddr(String actionAddr) {
		this.actionAddr = actionAddr;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActionState() {
		return actionState;
	}
	public void setActionState(String actionState) {
		this.actionState = actionState;
	}
	public String getAccidentName() {
		return accidentName;
	}
	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
	}
	
	
}
