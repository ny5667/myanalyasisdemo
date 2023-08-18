package com.supcon.orchid.SESECD.daos;

import com.supcon.orchid.ec.entities.DataClassific;
import com.supcon.orchid.ec.entities.DealInfo;
import com.supcon.orchid.foundation.entities.Staff;
import java.util.List;
import java.util.Date;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.orm.dao.ExtendedGenericDao;
import com.supcon.orchid.tree.TreeDao;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecordDealInfo;
import com.supcon.orchid.ec.entities.EntityTableInfo;
/* CUSTOM CODE START(dao,import,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public interface SESECDAlertRecordDao extends
	ExtendedGenericDao<SESECDAlertRecord, Long>
 {
	void saveDealInfo(SESECDAlertRecordDealInfo dealInfo);
	void saveTableInfo(EntityTableInfo tableInfo);
	EntityTableInfo getTableInfo(Long tableInfoId);
	DataClassific getDataClassific(String code);
	SESECDAlertRecordDealInfo copyAndSaveDealInfo(DealInfo di);
	SESECDAlertRecordDealInfo copyAndSaveDealInfo(DealInfo di, SESECDAlertRecord mainObj, Staff dealStaff);
	Date findLastDealInfo(Long tableInfoId, String activityName);
	/* CUSTOM CODE START(dao,functions,SESECD_1.0.0_alertRecord_AlertRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}