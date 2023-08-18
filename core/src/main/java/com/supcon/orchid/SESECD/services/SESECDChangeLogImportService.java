package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDChangeLog;

public interface SESECDChangeLogImportService {

	void saveImportDataLog(SESECDChangeLog bizObj, SESECDChangeLog oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}