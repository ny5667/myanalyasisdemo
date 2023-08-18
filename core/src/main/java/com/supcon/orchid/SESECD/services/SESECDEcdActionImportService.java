package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdAction;

public interface SESECDEcdActionImportService {

	void saveImportDataLog(SESECDEcdAction bizObj, SESECDEcdAction oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}