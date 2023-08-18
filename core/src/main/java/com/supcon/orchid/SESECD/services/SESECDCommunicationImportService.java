package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDCommunication;

public interface SESECDCommunicationImportService {

	void saveImportDataLog(SESECDCommunication bizObj, SESECDCommunication oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}