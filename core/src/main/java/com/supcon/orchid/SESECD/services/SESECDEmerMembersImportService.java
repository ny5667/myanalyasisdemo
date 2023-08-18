package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEmerMembers;

public interface SESECDEmerMembersImportService {

	void saveImportDataLog(SESECDEmerMembers bizObj, SESECDEmerMembers oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}