package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDCctvRecord;

public interface SESECDCctvRecordImportService {

	void saveImportDataLog(SESECDCctvRecord bizObj, SESECDCctvRecord oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}