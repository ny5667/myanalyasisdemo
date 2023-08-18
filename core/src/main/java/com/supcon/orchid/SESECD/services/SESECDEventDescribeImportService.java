package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDEventDescribe;

public interface SESECDEventDescribeImportService {

	void saveImportDataLog(SESECDEventDescribe bizObj, SESECDEventDescribe oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}