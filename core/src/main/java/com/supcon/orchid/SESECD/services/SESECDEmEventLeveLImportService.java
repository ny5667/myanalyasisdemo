package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveL;

public interface SESECDEmEventLeveLImportService {

	void saveImportDataLog(SESECDEmEventLeveL bizObj, SESECDEmEventLeveL oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}