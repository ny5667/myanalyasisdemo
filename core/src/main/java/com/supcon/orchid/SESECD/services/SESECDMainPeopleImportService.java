package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDMainPeople;

public interface SESECDMainPeopleImportService {

	void saveImportDataLog(SESECDMainPeople bizObj, SESECDMainPeople oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}