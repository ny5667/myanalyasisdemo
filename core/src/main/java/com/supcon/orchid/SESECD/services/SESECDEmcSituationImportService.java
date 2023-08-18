package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;

public interface SESECDEmcSituationImportService {

	void saveImportDataLog(SESECDEmcSituation bizObj, SESECDEmcSituation oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}