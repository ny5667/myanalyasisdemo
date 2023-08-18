package com.supcon.orchid.SESECD.services;

/**
 * 用户自定义的下载附件的权限控制
 * 
 * @version $Id$
 */
public interface SESECDPreviewPermissionService {

	/**
	 * 是否需要自定义下载附件权限控制
	 * 
	 * @param entityCode
	 *            实体的编码
	 * @return
	 */
	public boolean needCheckPermission(String entityCode, Long linkId, String id, String linkType);

	/**
	 * 自定义下载附件权限控制
	 * 
	 * @param entityCode
	 *            实体的编码
	 * @param linkId
	 *            附件所对应的关联id
	 * @return
	 */
	public boolean checkPermission(String entityCode, Long linkId, String id, String linkType);
}
