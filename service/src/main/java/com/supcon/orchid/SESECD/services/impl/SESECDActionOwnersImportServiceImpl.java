package com.supcon.orchid.SESECD.services.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.orm.entities.AbstractAuditEntity;
import com.supcon.orchid.utils.StringUtils;
import com.supcon.orchid.audit.annotation.AuditLog;
import com.supcon.orchid.audit.entities.AuditUtil;
import com.supcon.orchid.audit.service.DataAuditLogService;
import com.supcon.orchid.SESECD.entities.SESECDActionOwners;
import com.supcon.orchid.SESECD.services.SESECDActionOwnersImportService;

@Service("sESECDActionOwnersImportService")
public class SESECDActionOwnersImportServiceImpl implements SESECDActionOwnersImportService {
	
	@Autowired
	private DataAuditLogService dataAuditLogService;
	
	private static final String[] baseTypeArrs = new String[] {
		"java.lang.String", "java.lang.Long", "java.lang.Integer",
		"java.lang.Double", "java.lang.Float", "java.lang.Boolean",
		"java.lang.Short", "java.lang.Byte", "java.lang.Character",
		"int", "double", "long", "short", "byte", "boolean", "char", "float"
	};
	
	@Override
	@AuditLog(entity="SESECD_1.0.0_alarmRecord",model="SESECD_1.0.0_alarmRecord_ActionOwners",desc="保存业务信息",operType="1")
	public void saveImportDataLog(SESECDActionOwners bizObj, SESECDActionOwners oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName) {
		String dataLogOpType = null;
		if (null != oldBizObj) {
			AuditUtil.setAuditOperationType("2");
			AuditUtil.setAuditDes("修改" + InternationalResource.get("SESECD.entityname.randon1576460940310") + "信息");
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringA())){
				AuditUtil.setColumnStringA(AuditUtil.getColumnStringA() + (null == oldBizObj.getId() ? "" : "," + oldBizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringA(null == oldBizObj.getId() ? "" : oldBizObj.getId().toString());
			}
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringB())){
				AuditUtil.setColumnStringB(AuditUtil.getColumnStringB() + (null == oldBizObj.getId() ? "" : "," + oldBizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringB(null == oldBizObj.getId() ? "" : oldBizObj.getId().toString());
			}
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringC())){
				AuditUtil.setColumnStringC(AuditUtil.getColumnStringC() + (null == oldBizObj.getId() ? "" : "," + oldBizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringC(null == oldBizObj.getId() ? "" : oldBizObj.getId().toString());
			}
			//setBussinessKeyAndMainDispalyField(oldBizObj, bussinesskeyFieldName, mainDispalyFieldName);
			bizObj.setModifyTime(new Date());
			dataLogOpType = "修改";
		} else {
			AuditUtil.setAuditOperationType("1");
			AuditUtil.setAuditDes("新增" + InternationalResource.get("SESECD.entityname.randon1576460940310") + "信息");
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringA())){
				AuditUtil.setColumnStringA(AuditUtil.getColumnStringA() + (null == bizObj.getId() ? "" : "," + bizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringA(null == bizObj.getId() ? "" : bizObj.getId().toString());
			}
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringB())){
				AuditUtil.setColumnStringB(AuditUtil.getColumnStringB() + (null == bizObj.getId() ? "" : "," + bizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringB(null == bizObj.getId() ? "" : bizObj.getId().toString());
			}
			if(!StringUtils.isEmpty(AuditUtil.getColumnStringC())){
				AuditUtil.setColumnStringC(AuditUtil.getColumnStringC() + (null == bizObj.getId() ? "" : "," + bizObj.getId().toString()));
			} else {
				AuditUtil.setColumnStringC(null == bizObj.getId() ? "" : bizObj.getId().toString());
			}
			//setBussinessKeyAndMainDispalyField(bizObj, bussinesskeyFieldName, mainDispalyFieldName);
			bizObj.setCreateTime(new Date());
			dataLogOpType = "新增";
		}
		if (null != dataLogOpType) {
			try {
				dataAuditLogService.saveCustomerAuditForClass(dataLogOpType, bizObj.getId(), bizObj, oldBizObj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setBussinessKeyAndMainDispalyField(SESECDActionOwners bizObj, String bussinesskeyFieldName,
			String mainDispalyFieldName) {
		try {
			if (null != bussinesskeyFieldName) {
				Field bussinesskeyField = bizObj.getClass().getDeclaredField(bussinesskeyFieldName);
				bussinesskeyField.setAccessible(true);
				String bussinesskeyFieldType = bussinesskeyField.getType().getName();
				String bussinesskey = null;
				if (Arrays.asList(baseTypeArrs).contains(bussinesskeyFieldType)) {
					bussinesskey = null == bussinesskeyField.get(bizObj) ? "" : bussinesskeyField.get(bizObj).toString();
				} else {
					Object propObj = bussinesskeyField.get(bizObj);
					if (null != propObj && propObj instanceof AbstractAuditEntity) {
						Field subObjIdField = propObj.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
						subObjIdField.setAccessible(true);
						bussinesskey = subObjIdField.get(propObj).toString();
					}
				}
				AuditUtil.setColumnStringA(bussinesskey);
			}
			if (null != mainDispalyFieldName) {
				Field mainDispalyField = bizObj.getClass().getDeclaredField(mainDispalyFieldName);
				mainDispalyField.setAccessible(true);
				String mainDispalyFieldType = mainDispalyField.getType().getName();
				String mainDispaly = null;
				if (Arrays.asList(baseTypeArrs).contains(mainDispalyFieldType)) {
					mainDispaly = null == mainDispalyField.get(bizObj) ? "" : mainDispalyField.get(bizObj).toString();
				} else {
					Object propObj = mainDispalyField.get(bizObj);
					if (null != propObj && propObj instanceof AbstractAuditEntity) {
						Field subObjIdField = propObj.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
						subObjIdField.setAccessible(true);
						mainDispaly = subObjIdField.get(propObj).toString();
					}
				}
				AuditUtil.setColumnStringB(mainDispaly);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}