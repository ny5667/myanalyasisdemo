package com.supcon.orchid.SESECD.model.dto.alarm;

import java.util.List;

public class EcdInfosDto {
	
	private String accidentName;
	private String happenTime;
	private String hpnCompany;
	private Long id;
	private String gisAccidentId;
	private Boolean isOver ;
	private List<Instruction> instruction;
	private List<EcdAction> ecdAction;
	private List<EcdSituation> ecdSituation;
	private String imgUrl;
	private Boolean isPls;
	private Boolean isRoute;
	private Boolean isWind;
	
	public static class Instruction{
		
		private Long id;
		private String gisInstructionId;
		private String name;
		private String description;
		private String catogory;
		private String address;
		private CommonState commonState;
		private Integer rowNum;
		private String imgUrl;
		
		public static class CommonState{
			
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

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGisInstructionId() {
			return gisInstructionId;
		}

		public void setGisInstructionId(String gisInstructionId) {
			this.gisInstructionId = gisInstructionId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCatogory() {
			return catogory;
		}

		public void setCatogory(String catogory) {
			this.catogory = catogory;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public CommonState getCommonState() {
			return commonState;
		}

		public void setCommonState(CommonState commonState) {
			this.commonState = commonState;
		}

		public Integer getRowNum() {
			return rowNum;
		}

		public void setRowNum(Integer rowNum) {
			this.rowNum = rowNum;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		
		
	}
	
	public static class EcdAction{
		
		private Long id;
		private String gisEcdActionId;
		private String actionTime;
		private String actionAddr;
		private String description;
		private String owners;
		private String ownDepartments;
		private ActionState actionState;
		private Integer rowNum;
		private String imgUrl;
		
		public static class ActionState{
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

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGisEcdActionId() {
			return gisEcdActionId;
		}

		public void setGisEcdActionId(String gisEcdActionId) {
			this.gisEcdActionId = gisEcdActionId;
		}

		public String getActionTime() {
			return actionTime;
		}

		public void setActionTime(String actionTime) {
			this.actionTime = actionTime;
		}

		public String getActionAddr() {
			return actionAddr;
		}

		public void setActionAddr(String actionAddr) {
			this.actionAddr = actionAddr;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getOwners() {
			return owners;
		}

		public void setOwners(String owners) {
			this.owners = owners;
		}

		public String getOwnDepartments() {
			return ownDepartments;
		}

		public void setOwnDepartments(String ownDepartments) {
			this.ownDepartments = ownDepartments;
		}

		public ActionState getActionState() {
			return actionState;
		}

		public void setActionState(ActionState actionState) {
			this.actionState = actionState;
		}

		public Integer getRowNum() {
			return rowNum;
		}

		public void setRowNum(Integer rowNum) {
			this.rowNum = rowNum;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		
		
	}
	
	public static class EcdSituation{
		
		private Long id;
		private String gisSituationId;
		private String description;
		private String occursTime;
		private String position;
		private String reportPerson;
		private Integer woundedPerson;
		private Integer deathPerson;
		private Integer rowNum;
		private String imgUrl;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getGisSituationId() {
			return gisSituationId;
		}
		public void setGisSituationId(String gisSituationId) {
			this.gisSituationId = gisSituationId;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		public String getReportPerson() {
			return reportPerson;
		}
		public void setReportPerson(String reportPerson) {
			this.reportPerson = reportPerson;
		}
		public Integer getWoundedPerson() {
			return woundedPerson;
		}
		public void setWoundedPerson(Integer woundedPerson) {
			this.woundedPerson = woundedPerson;
		}
		public Integer getDeathPerson() {
			return deathPerson;
		}
		public void setDeathPerson(Integer deathPerson) {
			this.deathPerson = deathPerson;
		}
		public Integer getRowNum() {
			return rowNum;
		}
		public void setRowNum(Integer rowNum) {
			this.rowNum = rowNum;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		
		
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGisAccidentId() {
		return gisAccidentId;
	}

	public void setGisAccidentId(String gisAccidentId) {
		this.gisAccidentId = gisAccidentId;
	}


	public List<Instruction> getInstruction() {
		return instruction;
	}

	public void setInstruction(List<Instruction> instruction) {
		this.instruction = instruction;
	}

	public List<EcdAction> getEcdAction() {
		return ecdAction;
	}

	public void setEcdAction(List<EcdAction> ecdAction) {
		this.ecdAction = ecdAction;
	}

	public List<EcdSituation> getEcdSituation() {
		return ecdSituation;
	}

	public void setEcdSituation(List<EcdSituation> ecdSituation) {
		this.ecdSituation = ecdSituation;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Boolean getIsPls() {
		return isPls;
	}

	public void setIsPls(Boolean pls) {
		isPls = pls;
	}

	public Boolean getIsRoute() {
		return isRoute;
	}

	public void setIsRoute(Boolean isRoute) {
		this.isRoute = isRoute;
	}

	public Boolean getIsWind() {
		return isWind;
	}

	public void setIsWind(Boolean isWind) {
		this.isWind = isWind;
	}

	public Boolean getIsOver() {
		return isOver;
	}

	public void setIsOver(Boolean isOver) {
		this.isOver = isOver;
	}
}
