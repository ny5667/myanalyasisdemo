package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDSentence;

public interface SESECDSentenceImportService {

	void saveImportDataLog(SESECDSentence bizObj, SESECDSentence oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}