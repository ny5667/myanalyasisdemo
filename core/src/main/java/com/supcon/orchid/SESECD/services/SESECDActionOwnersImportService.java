package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDActionOwners;

public interface SESECDActionOwnersImportService {

	void saveImportDataLog(SESECDActionOwners bizObj, SESECDActionOwners oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}