package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDDispatConfig;

public interface SESECDDispatConfigImportService {

	void saveImportDataLog(SESECDDispatConfig bizObj, SESECDDispatConfig oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}