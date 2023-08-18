package com.supcon.orchid.SESECD.model.dto.alarm;

public class EmcActionDto {

	private String actionAddr;
	private String actionStateId;
	private String actionStateValue;
	private String description;
	private String beginTime;
	private Long actionId;
	private Long eventId;
	private String owners;
	private String actionCatogoryId
			;// 行动分类
	private String actionCatogoryName
			;// 行动分类
	private String trackRecord ; // 跟踪记录

	public String getActionAddr() {
		return actionAddr;
	}

	public void setActionAddr(String actionAddr) {
		this.actionAddr = actionAddr;
	}

	public String getActionStateId() {
		return actionStateId;
	}

	public void setActionStateId(String actionStateId) {
		this.actionStateId = actionStateId;
	}

	public String getActionStateValue() {
		return actionStateValue;
	}

	public void setActionStateValue(String actionStateValue) {
		this.actionStateValue = actionStateValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getOwners() {
		return owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
	}

	public String getActionCatogoryId() {
		return actionCatogoryId;
	}

	public void setActionCatogoryId(String actionCatogoryId) {
		this.actionCatogoryId = actionCatogoryId;
	}

	public String getActionCatogoryName() {
		return actionCatogoryName;
	}

	public void setActionCatogoryName(String actionCatogoryName) {
		this.actionCatogoryName = actionCatogoryName;
	}

	public String getTrackRecord() {
		return trackRecord;
	}

	public void setTrackRecord(String trackRecord) {
		this.trackRecord = trackRecord;
	}
}
