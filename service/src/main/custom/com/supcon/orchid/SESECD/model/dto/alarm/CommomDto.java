package com.supcon.orchid.SESECD.model.dto.alarm;


public class CommomDto {

	private String actionAddress;
	private String actionCatogoryId;
	private String actionCatogoryValue;
	private String actionDescription;
	private String actionName;
	private Long commomId;
	private String commomStateId;
	private String commomStateValue;
	private Long eventId;
	private Long planId;
	private String owners;
	private Boolean isMapPoint;
    private  String mapPoint;

	public String getMapPoint() {
		return mapPoint;
	}

	public void setMapPoint(String mapPoint) {
		this.mapPoint = mapPoint;
	}

	public String getActionAddress() {
		return actionAddress;
	}

	public void setActionAddress(String actionAddress) {
		this.actionAddress = actionAddress;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Long getCommomId() {
		return commomId;
	}

	public void setCommomId(Long commomId) {
		this.commomId = commomId;
	}

	public String getActionCatogoryId() {
		return actionCatogoryId;
	}

	public void setActionCatogoryId(String actionCatogoryId) {
		this.actionCatogoryId = actionCatogoryId;
	}

	public String getActionCatogoryValue() {
		return actionCatogoryValue;
	}

	public void setActionCatogoryValue(String actionCatogoryValue) {
		this.actionCatogoryValue = actionCatogoryValue;
	}

	public String getCommomStateId() {
		return commomStateId;
	}

	public void setCommomStateId(String commomStateId) {
		this.commomStateId = commomStateId;
	}

	public String getCommomStateValue() {
		return commomStateValue;
	}

	public void setCommomStateValue(String commomStateValue) {
		this.commomStateValue = commomStateValue;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getOwners() {
		return owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
	}

	public Boolean getIsMapPoint() {
		return isMapPoint;
	}

	public void setIsMapPoint(Boolean mapPoint) {
		isMapPoint = mapPoint;
	}
}
