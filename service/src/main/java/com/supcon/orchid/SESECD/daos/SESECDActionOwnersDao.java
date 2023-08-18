package com.supcon.orchid.SESECD.daos;

import com.supcon.orchid.ec.entities.DataClassific;
import com.supcon.orchid.SESECD.entities.SESECDActionOwners;
import com.supcon.orchid.orm.dao.ExtendedGenericDao;
import com.supcon.orchid.tree.TreeDao;
/* CUSTOM CODE START(dao,import,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

public interface SESECDActionOwnersDao extends
	ExtendedGenericDao<SESECDActionOwners, Long>
 {	DataClassific getDataClassific(String code);
	/* CUSTOM CODE START(dao,functions,SESECD_1.0.0_alarmRecord_ActionOwners,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}