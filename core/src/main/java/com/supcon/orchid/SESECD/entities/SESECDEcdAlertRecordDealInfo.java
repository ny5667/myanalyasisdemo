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
 * SESECD.ecdAlertRecord.EcdAlertRecord.
 * 
 */
@javax.persistence.Entity(name=SESECDEcdAlertRecordDealInfo.JPA_NAME)
@Table(name = SESECDEcdAlertRecordDealInfo.TABLE_NAME)
public class SESECDEcdAlertRecordDealInfo extends AbstractDealInfoEntity implements com.supcon.orchid.orm.entities.IDealInfo {
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "ECD_ECD_ALERT_RECORDS_DI";
	public static final String JPA_NAME = "SESECDEcdAlertRecordDealInfo";
	
	protected SESECDEcdAlertRecord mainObj;
	protected Integer sort;

	@JoinColumn(name = "MAIN_OBJ")
	@ManyToOne(fetch = FetchType.EAGER)
	public SESECDEcdAlertRecord getMainObj() {
		return mainObj;
	}
	public void setMainObj(SESECDEcdAlertRecord mainObj) {
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
		return SESECDEcdAlertRecordDealInfo.class.getName();
	}

	@Override
	@Index(name="IDX_ECDALERTRECORD_DiTABLEID")
	public Long getTableInfoId() {
		return tableInfoId;
	}
}