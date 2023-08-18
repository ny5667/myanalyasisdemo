package com.supcon.orchid.SESECD.entities;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import com.supcon.orchid.orm.entities.AbstractDealInfoEntity;
import com.supcon.orchid.foundation.entities.Staff;

/**
 * SESECD.emcAction.EmcAction.
 * 
 */
@javax.persistence.Entity(name=SESECDEmcActionDealInfo.JPA_NAME)
@Table(name = SESECDEmcActionDealInfo.TABLE_NAME)
public class SESECDEmcActionDealInfo extends AbstractDealInfoEntity implements com.supcon.orchid.orm.entities.IDealInfo {
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SES_ECD_EMC_ACTIONS_DI";
	public static final String JPA_NAME = "SESECDEmcActionDealInfo";
	
	protected SESECDEmcAction mainObj;
	protected Integer sort;

	@JoinColumn(name = "MAIN_OBJ")
	@ManyToOne(fetch = FetchType.EAGER)
	public SESECDEmcAction getMainObj() {
		return mainObj;
	}
	public void setMainObj(SESECDEmcAction mainObj) {
		this.mainObj = mainObj;
	}
	
	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	protected Staff staff;

	@JoinColumn(name = "STAFF")
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Staff.class)
	@Fetch(FetchMode.SELECT)
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	protected Boolean recalledFlag = false;

	public void setRecalledFlag(Boolean recalledFlag) {
		this.recalledFlag = recalledFlag;
	}

	public Boolean getRecalledFlag() {
		return recalledFlag;
	}

	protected String userAgent;

	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	protected String _getEntityName() {
		return SESECDEmcActionDealInfo.class.getName();
	}

	@Override
	@Index(name="IDX_EMCACTION_DiTABLEID")
	public Long getTableInfoId() {
		return tableInfoId;
	}
}