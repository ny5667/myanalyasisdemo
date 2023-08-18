package com.supcon.orchid.SESECD.model.vo.gisdata;

public class AlarmEventVo {

	private String id;
	private String spatialId;
	private String accidentName;
	private String receiver;
	private String rectime;
	private String position;
	private String description;
	private String happenTime;
	private String hpnCompany;
	private String accidentType;
	private String processState;
	/**
	 * 是否应急事件
	 */
	private Boolean isIncident;
	private String alarmPerson;
	private String alarmPhone;
	private String alarmLevel;
	private Integer wounderPerson;
	private Integer deathPerson;
	private String process;

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

	public String getAccidentName() {
		return accidentName;
	}

	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRectime() {
		return rectime;
	}

	public void setRectime(String rectime) {
		this.rectime = rectime;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}

	public String getHpnCompany() {
		return hpnCompany;
	}

	public void setHpnCompany(String hpnCompany) {
		this.hpnCompany = hpnCompany;
	}

	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public Boolean getIsIncident() {
		return isIncident;
	}

	public void setIsIncident(Boolean isIncident) {
		this.isIncident = isIncident;
	}

	public String getAlarmPerson() {
		return alarmPerson;
	}

	public void setAlarmPerson(String alarmPerson) {
		this.alarmPerson = alarmPerson;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {

		this.alarmLevel = alarmLevel;
	}

	public Integer getWounderPerson() {
		return wounderPerson;
	}

	public void setWounderPerson(Integer wounderPerson) {
		this.wounderPerson = wounderPerson;
	}

	public Integer getDeathPerson() {
		return deathPerson;
	}

	public void setDeathPerson(Integer deathPerson) {
		this.deathPerson = deathPerson;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}
