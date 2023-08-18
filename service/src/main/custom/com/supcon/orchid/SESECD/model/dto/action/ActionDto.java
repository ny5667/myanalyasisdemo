package com.supcon.orchid.SESECD.model.dto.action;

import java.util.List;

public class ActionDto {
	
	private List<Long> ids;
	private String actionState;
	private String trackRecord;
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public String getActionState() {
		return actionState;
	}
	public void setActionState(String actionState) {
		this.actionState = actionState;
	}
	public String getTrackRecord() {
		return trackRecord;
	}
	public void setTrackRecord(String trackRecord) {
		this.trackRecord = trackRecord;
	}
	
	
}
