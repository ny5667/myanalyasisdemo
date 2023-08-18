package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfo;

public interface SESECDBroadcastInfoImportService {

	void saveImportDataLog(SESECDBroadcastInfo bizObj, SESECDBroadcastInfo oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}