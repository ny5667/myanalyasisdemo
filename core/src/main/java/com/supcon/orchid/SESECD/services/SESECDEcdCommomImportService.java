package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdCommom;

public interface SESECDEcdCommomImportService {

	void saveImportDataLog(SESECDEcdCommom bizObj, SESECDEcdCommom oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}