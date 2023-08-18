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
import com.supcon.orchid.foundation.entities.Department;		
import com.supcon.orchid.foundation.entities.Staff;		
import com.supcon.orchid.foundation.entities.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditLogModel;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessKey;
/* CUSTOM CODE START(entity,import,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

/**
 * 所有应急小组.
 */
@javax.persistence.Entity(name=SESECDAllEmerMember.JPA_NAME)
@Table(name = SESECDAllEmerMember.TABLE_NAME)
@BAPEntity(entityCode="SESECD_1.0.0_addrBook")
@XmlRootElement
@BAPModelCode(code="SESECD_1.0.0_addrBook_AllEmerMember")
@AttributeOverrides({
		@AttributeOverride(name="fullPathName", column=@Column(name = "FULL_PATH_NAME")),
		@AttributeOverride(name="id", column=@Column(name = "ID")),
		@AttributeOverride(name="layNo", column=@Column(name = "LAY_NO")),
		@AttributeOverride(name="layRec", column=@Column(name = "LAY_REC")),
		@AttributeOverride(name="sort", column=@Column(name = "SORT")),
		@AttributeOverride(name="version", column=@Column(name = "VERSION")),
})
@BAPCustomComponent
@DataAudit
@XmlSeeAlso({SESECDAllEmerMemberConvertor.class})
@JsonInclude(Include.NON_NULL)
@ApiModel
@AuditLogModel(modelCode = "SESECD_1.0.0_addrBook_AllEmerMember", modelName = "SESECD.addrBook.AllEmerMember", entityCode = "SESECD_1.0.0_addrBook", entityName = "SESECD.entityname.randon1578313293886")
public class SESECDAllEmerMember extends com.supcon.orchid.ec.entities.abstracts.AbstractEcTreeEntity<SESECDAllEmerMember> {
	private static final long serialVersionUID = 1L;
	public static final String MODULE_CODE = "SESECD_1.0.0";
	public static final String ENTITY_CODE = "SESECD_1.0.0_addrBook";
	public static final String MODEL_CODE = "SESECD_1.0.0_addrBook_AllEmerMember";
	public static final String DOC_TYPE = "SESECD_addrBook_AllEmerMember";
	public static final String TABLE_NAME = "ses_all_emer_members";
	public static final String JPA_NAME = "SESECDAllEmerMember";
	private Department belongDepartment;



	private String departMentName ; // 部门名称
	private SESECDEmerMembers emerMembers;



	private String mobile ; // 手机
	private Staff personId;



	private String personName ; // 人员姓名



	private String sectionName ; // 通讯组名称



	private String staffCode ; // 人员编码



	private String telephone ; // 应急电话

	@JsonBackReference("emerMembersList")
	@JSONField(serialize = false)
	private List<SESECDEmerMembers> emerMembersList;
	
	public void setEmerMembersList(List<SESECDEmerMembers> emerMembersList){
		this.emerMembersList = emerMembersList;
	}
	@Transient
	public List<SESECDEmerMembers> getEmerMembersList(){
		return emerMembersList;
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
	
	@OneToOne
	@JoinColumn(name = "BELONG_DEPARTMENT", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Department getBelongDepartment() {
        return belongDepartment;
    }
    public void setBelongDepartment(Department belongDepartment) {
        this.belongDepartment = belongDepartment;
    }
	/**
	 * 获取部门名称.
	 * 
	 * @return 部门名称
	 */
	@Column(nullable=true

		,length = 256
		,name="DEPART_MENT_NAME"
	)
    public String getDepartMentName() {
        return departMentName;
    }
	/**
	 * 设置部门名称.
	 * @param departMentName 部门名称
	 */
    public void setDepartMentName(String departMentName) {
        this.departMentName = departMentName;
    }
	@ManyToOne
	@JoinColumn(name = "EMER_MEMBERS", referencedColumnName="ID")
	//@XmlJavaTypeAdapter(BAPAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public SESECDEmerMembers getEmerMembers() {
        return emerMembers;
    }
    public void setEmerMembers(SESECDEmerMembers emerMembers) {
        this.emerMembers = emerMembers;
    }
	/**
	 * 获取手机.
	 * 
	 * @return 手机
	 */
	@Column(nullable=true

		,length = 256
		,name="MOBILE"
	)
    public String getMobile() {
        return mobile;
    }
	/**
	 * 设置手机.
	 * @param mobile 手机
	 */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
	@OneToOne
	@JoinColumn(name = "PERSON_ID", referencedColumnName="ID")
	@XmlJavaTypeAdapter(BAPFoundationAdapter.class)
	@Fetch(FetchMode.SELECT)
						
    public Staff getPersonId() {
        return personId;
    }
    public void setPersonId(Staff personId) {
        this.personId = personId;
    }
	/**
	 * 获取人员姓名.
	 * 
	 * @return 人员姓名
	 */
	@Column(nullable=true

		,length = 256
		,name="PERSON_NAME"
	)
    public String getPersonName() {
        return personName;
    }
	/**
	 * 设置人员姓名.
	 * @param personName 人员姓名
	 */
    public void setPersonName(String personName) {
        this.personName = personName;
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
		return SESECDAllEmerMember.class.getName();
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

	
	private String parentNodeBusinessKey;
	@Transient
	public String getParentNodeBusinessKey() {
		return parentNodeBusinessKey;
	}

	public void setParentNodeBusinessKey(String parentNodeBusinessKey) {
		this.parentNodeBusinessKey = parentNodeBusinessKey;
	}
	
	private String parentNodeMainDisplay;
	@Transient
	public String getParentNodeMainDisplay() {
		return parentNodeMainDisplay;
	}

	public void setParentNodeMainDisplay(String parentNodeMainDisplay) {
		this.parentNodeMainDisplay = parentNodeMainDisplay;
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
	/* CUSTOM CODE START(entity,functions,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
