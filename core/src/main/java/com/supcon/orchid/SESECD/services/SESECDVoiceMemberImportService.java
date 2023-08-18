package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDVoiceMember;

public interface SESECDVoiceMemberImportService {

	void saveImportDataLog(SESECDVoiceMember bizObj, SESECDVoiceMember oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}