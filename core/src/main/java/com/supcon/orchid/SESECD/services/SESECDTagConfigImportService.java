package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDTagConfig;

public interface SESECDTagConfigImportService {

	void saveImportDataLog(SESECDTagConfig bizObj, SESECDTagConfig oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}