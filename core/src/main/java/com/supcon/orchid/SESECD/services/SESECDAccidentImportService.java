package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAccident;

public interface SESECDAccidentImportService {

	void saveImportDataLog(SESECDAccident bizObj, SESECDAccident oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}