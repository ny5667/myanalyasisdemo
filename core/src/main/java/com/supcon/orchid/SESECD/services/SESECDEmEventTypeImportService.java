package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmEventType;

public interface SESECDEmEventTypeImportService {

	void saveImportDataLog(SESECDEmEventType bizObj, SESECDEmEventType oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}