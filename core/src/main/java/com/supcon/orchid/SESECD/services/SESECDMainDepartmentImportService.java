package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;

public interface SESECDMainDepartmentImportService {

	void saveImportDataLog(SESECDMainDepartment bizObj, SESECDMainDepartment oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}