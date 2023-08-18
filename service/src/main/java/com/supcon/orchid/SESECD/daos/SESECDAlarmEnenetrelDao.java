package com.supcon.orchid.SESECD.daos;

import com.supcon.orchid.ec.entities.DataClassific;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.orm.dao.ExtendedGenericDao;
import com.supcon.orchid.tree.TreeDao;
/* CUSTOM CODE START(dao,import,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public interface SESECDAlarmEnenetrelDao extends
	ExtendedGenericDao<SESECDAlarmEnenetrel, Long>
 {	DataClassific getDataClassific(String code);
	/* CUSTOM CODE START(dao,functions,SESECD_1.0.0_alarmRecord_AlarmEnenetrel,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}