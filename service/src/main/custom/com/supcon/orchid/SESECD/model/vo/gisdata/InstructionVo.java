package com.supcon.orchid.SESECD.model.vo.gisdata;

public class InstructionVo {
	
	private String id;
	private String spatialId;
	private String actionName;
	private String actionAddress;
	private String actionCatogory;
	private String actionDescription;
	private String commomState;
	private String owners;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionAddress() {
		return actionAddress;
	}

	public void setActionAddress(String actionAddress) {
		this.actionAddress = actionAddress;
	}

	public String getActionCatogory() {
		return actionCatogory;
	}

	public void setActionCatogory(String actionCatogory) {
		this.actionCatogory = actionCatogory;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public String getCommomState() {
		return commomState;
	}

	public void setCommomState(String commomState) {
		this.commomState = commomState;
	}

	public String getOwners() {
		return owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
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

}
