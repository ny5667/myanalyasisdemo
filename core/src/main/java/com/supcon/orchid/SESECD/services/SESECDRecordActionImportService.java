package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDRecordAction;

public interface SESECDRecordActionImportService {

	void saveImportDataLog(SESECDRecordAction bizObj, SESECDRecordAction oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}