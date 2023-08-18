package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;

public interface SESECDEcdAlertVideoImportService {

	void saveImportDataLog(SESECDEcdAlertVideo bizObj, SESECDEcdAlertVideo oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}