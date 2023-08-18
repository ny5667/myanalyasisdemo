package com.supcon.orchid.SESECD.daos.impl;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import com.supcon.orchid.ec.entities.DataClassific;
import com.supcon.orchid.ec.entities.DealInfo;
import org.springframework.stereotype.Repository;
import com.supcon.orchid.orm.enums.DealInfoType;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.orm.dao.hibernate.ExtendedGenericDaoImpl;
import com.supcon.orchid.tree.TreeDaoImpl;
/* CUSTOM CODE START(daoimpl,import,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Repository
public class SESECDEmePlanObjDaoImpl extends ExtendedGenericDaoImpl<SESECDEmePlanObj, Long> implements SESECDEmePlanObjDao {
	
	@Override
	public DataClassific getDataClassific(String code) {
		return (DataClassific) getSession().createQuery("from DataClassific where code = ?").setParameter(0, code).uniqueResult();
	}
	/* CUSTOM CODE START(daoimpl,functions,SESECD_1.0.0_alarmRecord_EmePlanObj,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}