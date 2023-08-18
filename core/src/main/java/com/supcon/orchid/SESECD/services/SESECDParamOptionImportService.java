package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDParamOption;

public interface SESECDParamOptionImportService {

	void saveImportDataLog(SESECDParamOption bizObj, SESECDParamOption oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}