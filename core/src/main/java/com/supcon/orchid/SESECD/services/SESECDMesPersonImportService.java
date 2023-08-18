package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDMesPerson;

public interface SESECDMesPersonImportService {

	void saveImportDataLog(SESECDMesPerson bizObj, SESECDMesPerson oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}