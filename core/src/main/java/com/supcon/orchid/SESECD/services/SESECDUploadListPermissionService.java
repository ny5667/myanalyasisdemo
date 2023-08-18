package com.supcon.orchid.SESECD.services;

import com.supcon.orchid.foundation.entities.Document;

import java.util.List;
/**
 * 用户自定义的下载附件的权限控制
 * 
 * @version $Id$
 */
public interface SESECDUploadListPermissionService {

	/**
	 * 是否需要附件列表权限控制
	 *
	 * @param documents
	 *            查询到的附件数组
	 * @return
	 */
	public boolean needCheckPermission(List<Long> linkIds, List<Document> documents,String type,String propertyCode,String viewCode);

	/**
	 * 自定义附件列表权限控制
	 *
	 * @param documents
	 *            查询到的附件数组
	 * @param linkId
	 *            附件所对应的关联id
	 * @return
	 */
	public void checkPermission(List<Long> linkIds, List<Document> documents,String type,String propertyCode,String viewCode);
}
