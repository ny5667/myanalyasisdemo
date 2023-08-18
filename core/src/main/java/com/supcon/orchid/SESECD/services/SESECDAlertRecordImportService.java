package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;

public interface SESECDAlertRecordImportService {

	void saveImportDataLog(SESECDAlertRecord bizObj, SESECDAlertRecord oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}