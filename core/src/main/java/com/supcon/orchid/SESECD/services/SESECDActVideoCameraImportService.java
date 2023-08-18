package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.SESECD.entities.SESECDActVideoCamera;

public interface SESECDActVideoCameraImportService {

	void saveImportDataLog(SESECDActVideoCamera bizObj, SESECDActVideoCamera oldBizObj, String bussinesskeyFieldName, String mainDispalyFieldName);

}