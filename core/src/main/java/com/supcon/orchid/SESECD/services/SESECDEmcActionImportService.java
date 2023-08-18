package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmcAction;

public interface SESECDEmcActionImportService {

	void saveImportDataLog(SESECDEmcAction bizObj, SESECDEmcAction oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}