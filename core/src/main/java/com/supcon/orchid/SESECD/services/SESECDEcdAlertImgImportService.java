package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;

public interface SESECDEcdAlertImgImportService {

	void saveImportDataLog(SESECDEcdAlertImg bizObj, SESECDEcdAlertImg oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}