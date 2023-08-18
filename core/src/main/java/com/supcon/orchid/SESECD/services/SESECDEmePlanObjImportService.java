package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;

public interface SESECDEmePlanObjImportService {

	void saveImportDataLog(SESECDEmePlanObj bizObj, SESECDEmePlanObj oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}