package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急小组.
 */
public class SESECDEmerMembersDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcPartEntityDTO {
	private static final long serialVersionUID = 1L;
	private String mobile ; // 手机号码
	private String name ; // 姓名
	private String onlineStatus ; // 在线状态
	private String role ; // 岗位
	private String sectionName ; // 通讯组名称
	private String staffCode ; // 人员编码
	private Long staffId ; // 人员ID
	private String telephone ; // 应急电话

	private List<SESECDAllEmerMemberDTO> allEmerMemberList;

	public void setAllEmerMemberList(List<SESECDAllEmerMemberDTO> allEmerMemberList){
		this.allEmerMemberList = allEmerMemberList;
	}
	public List<SESECDAllEmerMemberDTO> getAllEmerMemberList(){
		return allEmerMemberList;
	}

	private Document document;

	private String bapAttachmentInfo;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company =company;
	}
	private String emerMembersBelongDepartmentDeleteIds;
	private String emerMembersBelongDepartmentAddIds;
	private String emerMembersBelongDepartmentmultiselectIDs;
	private String emerMembersBelongDepartmentmultiselectNames;

	public String getEmerMembersBelongDepartmentDeleteIds(){
		return this.emerMembersBelongDepartmentDeleteIds;
	}

	public void setEmerMembersBelongDepartmentDeleteIds(String deleteIds){
		this.emerMembersBelongDepartmentDeleteIds = deleteIds;
	}

	public String getEmerMembersBelongDepartmentmultiselectIDs(){
		return this.emerMembersBelongDepartmentmultiselectIDs;
	}

	public void setEmerMembersBelongDepartmentmultiselectIDs(String multiselectIDs){
		this.emerMembersBelongDepartmentmultiselectIDs = multiselectIDs;
	}

	public String getEmerMembersBelongDepartmentmultiselectNames(){
		return this.emerMembersBelongDepartmentmultiselectNames;
	}

	public void setEmerMembersBelongDepartmentmultiselectNames(String multiselectNames){
		this.emerMembersBelongDepartmentmultiselectNames = multiselectNames;
	}

	public String getEmerMembersBelongDepartmentAddIds(){
		return this.emerMembersBelongDepartmentAddIds;
	}

	public void setEmerMembersBelongDepartmentAddIds(String addIds){
		this.emerMembersBelongDepartmentAddIds = addIds;
	}
	private String emerMembersPersonIdDeleteIds;
	private String emerMembersPersonIdAddIds;
	private String emerMembersPersonIdmultiselectIDs;
	private String emerMembersPersonIdmultiselectNames;

	public String getEmerMembersPersonIdDeleteIds(){
		return this.emerMembersPersonIdDeleteIds;
	}

	public void setEmerMembersPersonIdDeleteIds(String deleteIds){
		this.emerMembersPersonIdDeleteIds = deleteIds;
	}

	public String getEmerMembersPersonIdmultiselectIDs(){
		return this.emerMembersPersonIdmultiselectIDs;
	}

	public void setEmerMembersPersonIdmultiselectIDs(String multiselectIDs){
		this.emerMembersPersonIdmultiselectIDs = multiselectIDs;
	}

	public String getEmerMembersPersonIdmultiselectNames(){
		return this.emerMembersPersonIdmultiselectNames;
	}

	public void setEmerMembersPersonIdmultiselectNames(String multiselectNames){
		this.emerMembersPersonIdmultiselectNames = multiselectNames;
	}

	public String getEmerMembersPersonIdAddIds(){
		return this.emerMembersPersonIdAddIds;
	}

	public void setEmerMembersPersonIdAddIds(String addIds){
		this.emerMembersPersonIdAddIds = addIds;
	}


	private String virtualId;
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}

	@Override
	protected String _getEntityName() {
		return null;
	}

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
