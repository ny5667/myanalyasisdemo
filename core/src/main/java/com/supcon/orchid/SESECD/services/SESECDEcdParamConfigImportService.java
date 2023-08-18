package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;

public interface SESECDEcdParamConfigImportService {

	void saveImportDataLog(SESECDEcdParamConfig bizObj, SESECDEcdParamConfig oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}