package com.supcon.orchid.SESECD.DTO;

import java.util.*;
import java.math.BigDecimal;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.foundation.ws.entities.DepartmentDTO;
import com.supcon.orchid.foundation.ws.entities.StaffDTO;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.Document;
/* CUSTOM CODE START(dto,import,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 所有应急小组.
 */
public class SESECDAllEmerMemberDTO extends com.supcon.orchid.ec.entities.abstracts.DTO.AbstractEcTreeEntityDTO<SESECDAllEmerMemberDTO> {
	private static final long serialVersionUID = 1L;

	private DepartmentDTO belongDepartment;
	private String departMentName ; // 部门名称

	private SESECDEmerMembersDTO emerMembers;
	private String mobile ; // 手机

	private StaffDTO personId;
	private String personName ; // 人员姓名
	private String sectionName ; // 通讯组名称
	private String staffCode ; // 人员编码
	private String telephone ; // 应急电话

	private List<SESECDEmerMembersDTO> emerMembersList;

	public void setEmerMembersList(List<SESECDEmerMembersDTO> emerMembersList){
		this.emerMembersList = emerMembersList;
	}
	public List<SESECDEmerMembersDTO> getEmerMembersList(){
		return emerMembersList;
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

    public DepartmentDTO getBelongDepartment() {
        return belongDepartment;
    }
    public void setBelongDepartment(DepartmentDTO belongDepartment) {
        this.belongDepartment = belongDepartment;
    }

    public String getDepartMentName() {
        return departMentName;
    }

    public void setDepartMentName(String departMentName) {
        this.departMentName = departMentName;
    }
    public SESECDEmerMembersDTO getEmerMembers() {
        return emerMembers;
    }
    public void setEmerMembers(SESECDEmerMembersDTO emerMembers) {
        this.emerMembers = emerMembers;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public StaffDTO getPersonId() {
        return personId;
    }
    public void setPersonId(StaffDTO personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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


	private String parentNodeBusinessKey;
	public String getParentNodeBusinessKey() {
		return parentNodeBusinessKey;
	}

	public void setParentNodeBusinessKey(String parentNodeBusinessKey) {
		this.parentNodeBusinessKey = parentNodeBusinessKey;
	}

	private String parentNodeMainDisplay;
	public String getParentNodeMainDisplay() {
		return parentNodeMainDisplay;
	}

	public void setParentNodeMainDisplay(String parentNodeMainDisplay) {
		this.parentNodeMainDisplay = parentNodeMainDisplay;
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

	/* CUSTOM CODE START(dto,functions,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
