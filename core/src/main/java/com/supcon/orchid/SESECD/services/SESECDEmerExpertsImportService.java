package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmerExperts;

public interface SESECDEmerExpertsImportService {

	void saveImportDataLog(SESECDEmerExperts bizObj, SESECDEmerExperts oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}