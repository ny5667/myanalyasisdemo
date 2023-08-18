package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;

public interface SESECDAlarmActionImportService {

	void saveImportDataLog(SESECDAlarmAction bizObj, SESECDAlarmAction oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}