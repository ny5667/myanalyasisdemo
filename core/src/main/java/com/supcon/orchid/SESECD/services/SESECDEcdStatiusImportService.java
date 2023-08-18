package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdStatius;

public interface SESECDEcdStatiusImportService {

	void saveImportDataLog(SESECDEcdStatius bizObj, SESECDEcdStatius oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}