package com.supcon.orchid.SESECD.entities;

import javax.persistence.Table;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import org.hibernate.annotations.Index;
import javax.persistence.Column;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Lob;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.supcon.orchid.annotation.BAPEntity;
import javax.persistence.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Where;
import com.supcon.orchid.audit.annotation.DataAudit;
import com.supcon.orchid.orm.entities.IAuditEntity;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.ws.adapter.DateAdapter;
//import com.supcon.orchid.foundation.adapter.BAPAdapter;
import com.supcon.orchid.foundation.adapter.DocumentAdaptor;
import com.supcon.orchid.orm.entities.ICompany;
import com.supcon.orchid.orm.entities.ICId;
import com.supcon.orchid.annotation.BAPCustomComponent;
import com.supcon.orchid.annotation.BAPIsMainDisplay;
import com.supcon.orchid.annotation.BAPIsMneCode;
import com.supcon.orchid.annotation.BAPModelCode;
import com.supcon.orchid.annotation.BAPPicture;
import com.supcon.orchid.annotation.BAPSystemCodeMultable;
import com.supcon.orchid.annotation.BAPSeniorSystemCode;
import com.supcon.orchid.orm.entities.jaxb.BAPFoundationAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.alibaba.fastjson.annotation.JSONField;
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 应急小组.
 */
@javax.persistence.Entity(name=SESECDEmerMembers.JPA_NAME)
@Table(name = SESECDEmerMembers.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_addrBook")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_addrBook_EmerMembers")
@AttributeOverrides({
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDEmerMembersConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_addrBook_EmerMembers", modelName = "SESECD.addrBook.EmerMembers", entityCode = "SESECD_1.0.0_addrBook", entityName = "SESECD.entityname.randon1578313293886")
public class SESECDEmerMembers extends com.supcon.orchid.ec.entities.abstracts.AbstractEcPartEntity {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_addrBook";
	public static final String MODEL_CODE = "SESECD_1.0.0_addrBook_EmerMembers";
	public static final String DOC_TYPE = "SESECD_addrBook_EmerMembers";
	public static final String TABLE_NAME = "ses_ecd_emer_memberss";
	public static final String JPA_NAME = "SESECDEmerMembers";



	private String mobile ; // 手机号码



	private String name ; // 姓名



	private String onlineStatus ; // 在线状态



	private String role ; // 岗位



	private String sectionName ; // 通讯组名称



	private String staffCode ; // 人员编码



	private Long staffId ; // 人员ID



	private String telephone ; // 应急电话

	@JsonBackReference("allEmerMemberList")
	@JSONField(serialize = false)
	private List<SESECDAllEmerMember> allEmerMemberList;
	
	public void setAllEmerMemberList(List<SESECDAllEmerMember> allEmerMemberList){
		this.allEmerMemberList = allEmerMemberList;
	}
	@Transient
	public List<SESECDAllEmerMember> getAllEmerMemberList(){
		return allEmerMemberList;
	}
	
	private Document document;
	
	private String bapAttachmentInfo;
	
	@Transient
	@XmlJavaTypeAdapter(DocumentAdaptor.class)
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Transient
	public String getBapAttachmentInfo() {
		return bapAttachmentInfo;
	}

	public void setBapAttachmentInfo(String bapAttachmentInfo) {
		this.bapAttachmentInfo = bapAttachmentInfo;
	}
	
	/**
	 * 获取手机号码.
	 * 
	 * @return 手机号码
	 */
	@Column(nullable=true

		,length = 256
		,name="MOBILE"
	)
    public String getMobile() {
        return mobile;
    }
	/**
	 * 设置手机号码.
	 * @param mobile 手机号码
	 */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
	/**
	 * 获取姓名.
	 * 
	 * @return 姓名
	 */
	@Column(nullable=true

		,length = 256
		,name="NAME"
	)
    public String getName() {
        return name;
    }
	/**
	 * 设置姓名.
	 * @param name 姓名
	 */
    public void setName(String name) {
        this.name = name;
    }
	/**
	 * 获取在线状态.
	 * 
	 * @return 在线状态
	 */
	@Column(nullable=true

		,length = 256
		,name="ONLINE_STATUS"
	)
    public String getOnlineStatus() {
        return onlineStatus;
    }
	/**
	 * 设置在线状态.
	 * @param onlineStatus 在线状态
	 */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
	/**
	 * 获取岗位.
	 * 
	 * @return 岗位
	 */
	@Column(nullable=true

		,length = 256
		,name="ROLE"
	)
    public String getRole() {
        return role;
    }
	/**
	 * 设置岗位.
	 * @param role 岗位
	 */
    public void setRole(String role) {
        this.role = role;
    }
	/**
	 * 获取通讯组名称.
	 * 
	 * @return 通讯组名称
	 */
	@Column(nullable=true

		,length = 256
		,name="SECTION_NAME"
	)
    public String getSectionName() {
        return sectionName;
    }
	/**
	 * 设置通讯组名称.
	 * @param sectionName 通讯组名称
	 */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
	/**
	 * 获取人员编码.
	 * 
	 * @return 人员编码
	 */
	@Column(nullable=true

		,length = 256
		,name="STAFF_CODE"
	)
    public String getStaffCode() {
        return staffCode;
    }
	/**
	 * 设置人员编码.
	 * @param staffCode 人员编码
	 */
    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }
	/**
	 * 获取人员ID.
	 * 
	 * @return 人员ID
	 */
	@Column(nullable=true

		,name="STAFF_ID"
	)
    public Long getStaffId() {
        return staffId;
    }
	/**
	 * 设置人员ID.
	 * @param staffId 人员ID
	 */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
	/**
	 * 获取应急电话.
	 * 
	 * @return 应急电话
	 */
	@Column(nullable=true

		,length = 256
		,name="TELEPHONE"
	)
    public String getTelephone() {
        return telephone;
    }
	/**
	 * 设置应急电话.
	 * @param telephone 应急电话
	 */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
	

	@Override
	protected String _getEntityName() {
		return SESECDEmerMembers.class.getName();
	}
	
	
	private Company company;

	@Transient
	private String cidName;
	@Transient
	public String getCidName() {
        return cidName;
    }

    public void setCidName(String cidName) {
        this.cidName = cidName;
    }
	
	@Override
	@OneToOne(fetch=FetchType.EAGER, targetEntity=Company.class, optional=true)
	@JoinColumn(name=ICId.P_CID, insertable=false, updatable=false)
	@Fetch(FetchMode.SELECT)
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	public Company getCompany() {
		return this.company;
	}
	
	@Override
	public void setCompany(Company company) {
		this.company =company;
	}
	private String emerMembersBelongDepartmentDeleteIds;
	private String emerMembersBelongDepartmentAddIds;
	private String emerMembersBelongDepartmentmultiselectIDs;
	private String emerMembersBelongDepartmentmultiselectNames;

	@Transient
	public String getEmerMembersBelongDepartmentDeleteIds(){
		return this.emerMembersBelongDepartmentDeleteIds;
	}

	public void setEmerMembersBelongDepartmentDeleteIds(String deleteIds){
		this.emerMembersBelongDepartmentDeleteIds = deleteIds;
	}

	@Transient
	public String getEmerMembersBelongDepartmentmultiselectIDs(){
		return this.emerMembersBelongDepartmentmultiselectIDs;
	}

	public void setEmerMembersBelongDepartmentmultiselectIDs(String multiselectIDs){
		this.emerMembersBelongDepartmentmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getEmerMembersBelongDepartmentmultiselectNames(){
		return this.emerMembersBelongDepartmentmultiselectNames;
	}

	public void setEmerMembersBelongDepartmentmultiselectNames(String multiselectNames){
		this.emerMembersBelongDepartmentmultiselectNames = multiselectNames;
	}

	@Transient
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

	@Transient
	public String getEmerMembersPersonIdDeleteIds(){
		return this.emerMembersPersonIdDeleteIds;
	}

	public void setEmerMembersPersonIdDeleteIds(String deleteIds){
		this.emerMembersPersonIdDeleteIds = deleteIds;
	}

	@Transient
	public String getEmerMembersPersonIdmultiselectIDs(){
		return this.emerMembersPersonIdmultiselectIDs;
	}

	public void setEmerMembersPersonIdmultiselectIDs(String multiselectIDs){
		this.emerMembersPersonIdmultiselectIDs = multiselectIDs;
	}

	@Transient
	public String getEmerMembersPersonIdmultiselectNames(){
		return this.emerMembersPersonIdmultiselectNames;
	}

	public void setEmerMembersPersonIdmultiselectNames(String multiselectNames){
		this.emerMembersPersonIdmultiselectNames = multiselectNames;
	}

	@Transient
	public String getEmerMembersPersonIdAddIds(){
		return this.emerMembersPersonIdAddIds;
	}

	public void setEmerMembersPersonIdAddIds(String addIds){
		this.emerMembersPersonIdAddIds = addIds;
	}

	
	private String virtualId;
	@Transient
	public String getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(String virtualId) {
		this.virtualId = virtualId;
	}
	// 在此区域内自行添加JAVA代码,此处添加的JAVA代码必须不涉及持久化操作,
	// 如果加入get方法,请添加@javax.persistence.Transient注解
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
