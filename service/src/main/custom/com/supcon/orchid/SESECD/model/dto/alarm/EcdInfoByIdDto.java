package com.supcon.orchid.SESECD.model.dto.alarm;

public class EcdInfoByIdDto {

	private String accidentName;
	private String happenTime;
	private String hpnCompany;
	private String actionAddr;
	private String actionTime;
	private String aDescription;
	private EcdActionState actionState;
	private String sDescribtion;
	private String occursTime;
	private String position;
	private String aImgUrl;
	private String sImgUrl;

	public static class EcdActionState {
		private String id;
		private String code;
		private String value;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public EcdActionState getActionState() {
		return actionState;
	}

	public void setActionState(EcdActionState actionState) {
		this.actionState = actionState;
	}

	public String getAccidentName() {
		return accidentName;
	}

	public void setAccidentName(String accidentName) {
		this.accidentName = accidentName;
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

	public String getaDescription() {
		return aDescription;
	}

	public void setaDescription(String aDescription) {
		this.aDescription = aDescription;
	}

	public String getsDescribtion() {
		return sDescribtion;
	}

	public void setsDescribtion(String sDescribtion) {
		this.sDescribtion = sDescribtion;
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

	public String getaImgUrl() {
		return aImgUrl;
	}

	public void setaImgUrl(String aImgUrl) {
		this.aImgUrl = aImgUrl;
	}

	public String getsImgUrl() {
		return sImgUrl;
	}

	public void setsImgUrl(String sImgUrl) {
		this.sImgUrl = sImgUrl;
	}

}
