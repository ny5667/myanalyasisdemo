package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDVoiceConfig;

public interface SESECDVoiceConfigImportService {

	void saveImportDataLog(SESECDVoiceConfig bizObj, SESECDVoiceConfig oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}