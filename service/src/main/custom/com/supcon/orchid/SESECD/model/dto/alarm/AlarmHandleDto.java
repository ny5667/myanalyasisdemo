package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.List;

public class AlarmHandleDto {
	
	private List<Long> alarmIds;
	private String processState;
	private String process;
	private Boolean isIncident;
    private String  eventType;
	private String alarmLevel;
    private Long drillPlanId;

	private Long voiceConfigId;

    private List<String> mesCode;

	public List<String> getMesCode() {
		return mesCode;
	}

	public void setMesCode(List<String> mesCode) {
		this.mesCode = mesCode;
	}

	public Long getVoiceConfigId() {
		return voiceConfigId;
	}

	public void setVoiceConfigId(Long voiceConfigId) {
		this.voiceConfigId = voiceConfigId;
	}

	public Long getDrillPlanId() {
		return drillPlanId;
	}

	public void setDrillPlanId(Long drillPlanId) {
		this.drillPlanId = drillPlanId;
	}
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public List<Long> getAlarmIds() {
		return alarmIds;
	}
	public void setAlarmIds(List<Long> alarmIds) {
		this.alarmIds = alarmIds;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}

	public Boolean getIncident() {
		return isIncident;
	}

	public void setIncident(Boolean incident) {
		isIncident = incident;
	}
}
