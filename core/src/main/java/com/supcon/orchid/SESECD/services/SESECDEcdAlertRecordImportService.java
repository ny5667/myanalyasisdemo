package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;

public interface SESECDEcdAlertRecordImportService {

	void saveImportDataLog(SESECDEcdAlertRecord bizObj, SESECDEcdAlertRecord oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}