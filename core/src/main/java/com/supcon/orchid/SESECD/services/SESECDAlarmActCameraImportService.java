package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAlarmActCamera;

public interface SESECDAlarmActCameraImportService {

	void saveImportDataLog(SESECDAlarmActCamera bizObj, SESECDAlarmActCamera oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}