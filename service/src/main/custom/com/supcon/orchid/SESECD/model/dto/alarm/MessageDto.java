package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.List;

public class MessageDto {
	private Long eventId;
	private List<String> staffCodes;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public List<String> getStaffCodes() {
		return staffCodes;
	}
	public void setStaffCodes(List<String> staffCodes) {
		this.staffCodes = staffCodes;
	}
	
	
}
