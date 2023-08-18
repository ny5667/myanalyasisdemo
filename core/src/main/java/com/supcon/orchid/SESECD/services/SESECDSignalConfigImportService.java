package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;

public interface SESECDSignalConfigImportService {

	void saveImportDataLog(SESECDSignalConfig bizObj, SESECDSignalConfig oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}