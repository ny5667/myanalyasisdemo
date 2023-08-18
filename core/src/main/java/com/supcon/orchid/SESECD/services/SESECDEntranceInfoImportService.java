package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;

public interface SESECDEntranceInfoImportService {

	void saveImportDataLog(SESECDEntranceInfo bizObj, SESECDEntranceInfo oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}