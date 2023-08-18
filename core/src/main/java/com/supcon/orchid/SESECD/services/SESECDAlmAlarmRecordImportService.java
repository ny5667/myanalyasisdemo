package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;

public interface SESECDAlmAlarmRecordImportService {

	void saveImportDataLog(SESECDAlmAlarmRecord bizObj, SESECDAlmAlarmRecord oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}