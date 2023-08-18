package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDSourceTerminal;

public interface SESECDSourceTerminalImportService {

	void saveImportDataLog(SESECDSourceTerminal bizObj, SESECDSourceTerminal oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}