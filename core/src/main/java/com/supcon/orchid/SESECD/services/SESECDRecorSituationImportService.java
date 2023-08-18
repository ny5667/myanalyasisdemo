package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;

public interface SESECDRecorSituationImportService {

	void saveImportDataLog(SESECDRecorSituation bizObj, SESECDRecorSituation oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}