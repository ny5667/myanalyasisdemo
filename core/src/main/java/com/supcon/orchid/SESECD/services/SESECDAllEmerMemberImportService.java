package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDAllEmerMember;

public interface SESECDAllEmerMemberImportService {

	void saveImportDataLog(SESECDAllEmerMember bizObj, SESECDAllEmerMember oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}