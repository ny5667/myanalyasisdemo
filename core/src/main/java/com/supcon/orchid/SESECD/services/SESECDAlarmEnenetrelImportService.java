package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;

public interface SESECDAlarmEnenetrelImportService {

	void saveImportDataLog(SESECDAlarmEnenetrel bizObj, SESECDAlarmEnenetrel oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}