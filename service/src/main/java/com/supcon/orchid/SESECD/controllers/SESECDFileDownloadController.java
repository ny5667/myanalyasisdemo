package com.supcon.orchid.SESECD.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.ec.entities.View;
import com.supcon.orchid.ec.enums.ViewType;
import com.supcon.orchid.ec.services.ViewServiceFoundation;
import com.supcon.orchid.SESECD.services.SESECDDownloadPermissionService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import feign.FeignException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.supcon.orchid.utils.DocumentUtils;
import org.apache.commons.lang.StringUtils;
import com.supcon.orchid.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supcon.orchid.foundation.entities.Document;
import com.supcon.orchid.foundation.entities.DocumentDownloadInfo;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.internal.services.BAPPathManageServiceImpl;
import com.supcon.orchid.foundation.services.DocumentService;
import com.supcon.orchid.services.*;
import com.alibaba.fastjson.JSON;

import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.SESECD.client.SESECDIPreviewClient;
import com.supcon.orchid.SESECD.services.SESECDPreviewPermissionService;
import com.supcon.orchid.SESECD.services.SESECDUploadListPermissionService;
import com.supcon.orchid.utils.IPUtils;

@Controller
@RequestMapping("/SESECD/baseService/workbench")
public class SESECDFileDownloadController extends BaseServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(SESECDFileDownloadController.class);

	@Autowired
	BAPPathManageServiceImpl bapPathManageService;
	@Autowired
	DocumentService documentService;
	@Value("${bap.home}")
	private String BAP_HOME;
	@Value("${spring.application.name}")
	private String serverName;
	@Value("${filePreview.IsFileView:true}")
	private Boolean isFileView;
	@Autowired
	private ViewServiceFoundation viewServiceFoundation;
	@Autowired
	private ObjectMapper mapper;
	
	/**
	 * 获取下载权限校验必要信息
	 */
	@GetMapping("/download/getAuthenticationMessage")
	@ResponseBody
	public Map<String, String> getAuthenticationMess() {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("serverName", serverName);
		resultMap.put("url", "/SESECD/baseService/workbench/download/validate");
		resultMap.put("methodType", "GET");
		return resultMap;
	}
	
    @Autowired
    private BAPDownloadPermissionService bapDownloadPermissionService;
    @Autowired
    private ApplicationContext applicationContext;
	
	/**
     * 下载权限校验
     */
    @GetMapping("/download/validate")
    @ResponseBody
    public Boolean downloadValidate(String entityCode, Long linkId, String id, String type, Long mainModelId) {
    	Map<String, SESECDDownloadPermissionService> customDownloadPermissionServices = applicationContext.getBeansOfType(SESECDDownloadPermissionService.class);
        Long newLinkId = null;
        String newType = null;
        newLinkId = linkId;
        newType = type;
        if (null != newLinkId && newLinkId < 0) {
            return true;
        }
        if (!newType.startsWith("imageFile") && !newType.startsWith("com.supcon.orchid.foundation.entities.Staff.")) {
            if (null == entityCode || entityCode.length() == 0) {
                return false;
            }
            Boolean flag = false;
            if (null != customDownloadPermissionServices) {
                for (SESECDDownloadPermissionService cdps : customDownloadPermissionServices.values()) {
                    if (cdps.needCheckPermission(entityCode, newLinkId, id, newType)) {
                        flag = true;
                        Boolean cdpsFlag = cdps.checkPermission(entityCode, newLinkId, id, newType);
                        if (!cdpsFlag) {
                            return false;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (!flag) {
                Boolean bapFlag = bapDownloadPermissionService.checkPermission(entityCode, newLinkId, mainModelId);
                if (!bapFlag) {
                    return false;
                }
            }
        }
		return true;
    }


	@Autowired
	private SESECDIPreviewClient previewSESECDClient;

	@GetMapping("/preview")
	public String preview(HttpServletRequest request, String entityCode, String linkId, String id, String type, Boolean isMainModel){
		previewValidate(entityCode, (linkId != null) ? Long.valueOf(linkId) : null, id, type, isMainModel);
		Map<String,String> uriMap = new HashMap<String,String>();
		try {
			 uriMap =  (Map<String, String>)JSON.parse(previewSESECDClient.fileViewUrl(id,type)) ;
		}catch (FeignException e ){
			log.info(e.getMessage(),e);
			String result = e.contentUTF8();
			Map<String,String> resultMap =  (Map<String, String>)JSON.parse(result) ;
			String resultmsg = "null error";
			if(resultMap!=null && resultMap.size()>0){
				resultmsg = resultMap.get("msg");
			}
			throw new BAPException(resultmsg);
		}catch (Exception e){
			log.info(e.getMessage(),e);
		}
		downloadLog(request, id, true);
		if(uriMap!=null && "200".equals( String.valueOf(uriMap.get("code")))){
		    return  "redirect:"+uriMap.get("data");
		}else{
		    return uriMap.get("data");
		}
	}

	@GetMapping("/preview/picture")
    public String picViewUrl(HttpServletRequest request, String entityCode, String linkId, String id, String type, Boolean isMainModel){
        previewValidate(entityCode, (linkId != null) ? Long.valueOf(linkId) : null, id, type, isMainModel);
        Map<String,String> uriMap = new HashMap<String,String>();
		try {
			uriMap =  (Map<String, String>)JSON.parse(previewSESECDClient.picViewUrl(id)) ;
		}catch (FeignException e ){
			log.info(e.getMessage(),e);
			String result = e.contentUTF8();
			Map<String,String> resultMap =  (Map<String, String>)JSON.parse(result) ;
			String resultmsg = "null error";
			if(resultMap!=null && resultMap.size()>0){
				resultmsg = resultMap.get("msg");
			}
			throw new BAPException(resultmsg);
		}catch (Exception e){
			log.info(e.getMessage(),e);
		}
        if(uriMap!=null && "200".equals( String.valueOf(uriMap.get("code")))){
            return  "redirect:"+uriMap.get("data");
        }else{
            return uriMap.get("data");
        }
    }

	private void previewValidate(String entityCode, Long linkId, String id, String type, Boolean isMainModel){
		Map<String, SESECDPreviewPermissionService> customPreviewSerivce = applicationContext.getBeansOfType(SESECDPreviewPermissionService.class);
		Long newLinkId = null;
		String newType = null;
		Long mainModelId = null;
		if (linkId != null && type != null && !type.isEmpty()) {
			newLinkId = linkId;
			newType = type;
		} else {
			Document document = documentService.load(Long.valueOf(id));
			newLinkId = document.getLinkId();
			newType = document.getType();
			mainModelId = document.getMainModelId();
		}
		Boolean flag = false;
		if (null != customPreviewSerivce) {
			for (SESECDPreviewPermissionService cpps : customPreviewSerivce.values()) {
				if (cpps.needCheckPermission(entityCode, newLinkId, id, newType)) {
					flag = true;
					Boolean cdpsFlag = cpps.checkPermission(entityCode, newLinkId, id, newType);
					if (!cdpsFlag) {
						throw new BAPException(InternationalResource.get("ec.msModule.preview.nopermission"));
					} else {
						break;
					}
				}
			}
		}
		//借用BAP下载的权限逻辑
		if (!flag) {
            Boolean bapFlag = bapDownloadPermissionService.checkPermission(entityCode, newLinkId, mainModelId);
            if (!bapFlag) {
                throw new BAPException(InternationalResource.get("ec.msModule.preview.nopermission"));
            }
        }
	}

	/**
     * 查询附件
     *
     * @param linkId       不为空
     * @param type         不为空
     * @param propertyCode 可空
     * @return
     */
    @GetMapping("/upload-list")
    @ResponseBody
    public String list(String linkId, String type, String propertyCode, String viewCode, String isPic) throws IOException{
		Map<String, String> attPermissionMap = new HashMap<>();
    	if(null!=viewCode && !StringUtils.isEmpty(viewCode)){
			Map isShadow = viewServiceFoundation.findShadowCode(viewCode);
			String attViewCode = viewCode;
			if(null != isShadow.get("IS_SHADOW") && (Integer.parseInt(isShadow.get("IS_SHADOW").toString()) == 1 || Boolean.valueOf(isShadow.get("IS_SHADOW").toString()))){
				if(null != isShadow.get("SHADOW_VIEW_CODE")){
					attViewCode = (String) isShadow.get("SHADOW_VIEW_CODE");
				}
			}
			String resultStr = viewServiceFoundation.findViewJsonByXML(attViewCode);
			String viewType = null;
			if(null != isShadow.get("TYPE")){
				viewType = (String)isShadow.get("TYPE");
				if (ViewType.EDIT.equals(getViewType(viewType)) || ViewType.VIEW.equals(getViewType(viewType))
						|| ViewType.EXTRA.equals(getViewType(viewType))) {
					mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
					Map resultMap = mapper.readValue(resultStr, Map.class);
					attPermission(resultMap, attPermissionMap);
				}
			}
		}
        if (null != isPic && Boolean.valueOf(isPic)) {
            List<Document> picDocs = null;
            if (null != propertyCode && !StringUtils.isEmpty(propertyCode) && null != linkId && linkId.length() > 0 && null != type && type.length() > 0) {
                picDocs = documentService.getPictureByLinkIdAndType(Long.valueOf(linkId), type, propertyCode);
                for (Document doc : picDocs) {
                    doc.setName(doc.getName());
                    doc.setSizeDis(DocumentUtils.sizeConversion(doc.getSize()));
                    doc.setFileIcon(DocumentUtils.getIcon(doc.getName()));
                    doc.setIsFileView(isFileView);
                }
            }
            String includes = "id,path,name,sizeDis,createTime,createStaff.name,memo,downloadTimes,propertyCode,fileIcon,isFileView,previewTimes";
            return null == picDocs ? "" : JsonUtils.listToJson(picDocs, includes, "*");
        } else {
            Page<Document> page = new Page<>();
            // 设置不分页在findByPage中无效
            page.setPageSize(Integer.MAX_VALUE);
            page.setAutoCount(false);
            if (null != linkId && linkId.length() > 0 && null != type && type.length() > 0) {
                if (!StringUtils.isEmpty(propertyCode)) {
                    documentService.getByLinkIdAndTypePage(page, Long.valueOf(linkId), type, propertyCode);
                } else {
                    documentService.getByLinkIdAndTypePage(page, Long.valueOf(linkId), type);
                }
                listValidate((linkId != null) ? Long.valueOf(linkId) : null, page, type, propertyCode, viewCode);
                if (page.getResult() != null) {
                    for (Document doc : page.getResult()) {
                        doc.setName(doc.getName());
                        doc.setSizeDis(DocumentUtils.sizeConversion(doc.getSize()));
                        doc.setFileIcon(DocumentUtils.getIcon(doc.getName()));
                        doc.setIsFileView(isFileView);
                        if(null!=viewCode && !StringUtils.isEmpty(viewCode)){
							if(!org.springframework.util.StringUtils.isEmpty(doc.getPropertyCode()) && null!=attPermissionMap.get(doc.getPropertyCode())){
								doc.setAttPermission(attPermissionMap.get(doc.getPropertyCode()));
							}else{
								doc.setAttPermission("all");
							}
						}
                    }
                }
            }
            String includes = "id,path,name,sizeDis,createTime,createStaff.name,memo,downloadTimes,propertyCode,fileIcon,isFileView,attPermission,previewTimes";
            return null == page.getResult() ? "" : JsonUtils.listToJson(page.getResult(), includes, "*");
        }
    }

	private ViewType getViewType(String viewType) {
		if (viewType == null) {
			return null;
		}
		return ViewType.valueOf(viewType);
	}

    private void attPermission(Map resultMap, Map attPermissionMap){
    	if(null==resultMap || resultMap.size()<1){
    		return;
		}
    	if(null != resultMap.get("components")){
			List<Map> list = (ArrayList<Map>)resultMap.get("components");
			for (Map subMap : list) {
				attPermission(subMap, attPermissionMap);
			}
		}
    	if(null != resultMap.get("cells")){
			List<Map> cells = (ArrayList) resultMap.get("cells");
			for (Map cell : cells) {
				if(null != cell.get("element")){
					Map element = (Map) cell.get("element");
					if(null!=element.get("columnType") && element.get("columnType").equals("PROPERTYATTACHMENT")
							&& null != element.get("attPermission") && null != element.get("propertyCode")){
						attPermissionMap.put(element.get("propertyCode"), element.get("attPermission"));
					}
				}
			}
		}
    	return;
	}
    private void listValidate(Long linkId, Page<Document> page, String type, String propertyCode,String viewCode) {
    	Map<String, SESECDUploadListPermissionService> customUploadListService = applicationContext.getBeansOfType(SESECDUploadListPermissionService.class);
    	Long newLinkId = null;
    	String newType = null;
    	if (linkId != null && type != null && !type.isEmpty()) {
    		newLinkId = linkId;
    		newType = type;
    	}
    	for (SESECDUploadListPermissionService ups : customUploadListService.values()) {
    		if (ups.needCheckPermission(Collections.singletonList(newLinkId), page.getResult(), newType, propertyCode,viewCode)) {
    			ups.checkPermission(Collections.singletonList(newLinkId), page.getResult(), newType, propertyCode,viewCode);
    		}
    	}
    }
    /**
     * 查询附件数量
     *
     * @return
     */
    @GetMapping("/countFile")
    @ResponseBody
    public Object attachmentCount(long linkId, String type,String viewCode) {
        List<Document> documents = documentService.getByTypeAndLinkIdRange(type, Collections.singletonList(linkId));
        Map<String, SESECDUploadListPermissionService> customUploadListService = applicationContext.getBeansOfType(SESECDUploadListPermissionService.class);
        for (SESECDUploadListPermissionService ups : customUploadListService.values()) {
			if (ups.needCheckPermission(Collections.singletonList(linkId), documents, type, null,viewCode)) {
				ups.checkPermission(Collections.singletonList(linkId), documents, type, null,viewCode);
			}
        }
        return documents.size();
    }

	/***
	 * 生成下载记录
	 * @param request
	 * @param id
	 */
	private void downloadLog(HttpServletRequest request,String id, Boolean ispreview){
		try {
			//create  file preview log
			Document document = documentService.load(Long.valueOf(id));
			DocumentDownloadInfo ddi = new DocumentDownloadInfo();
			ddi.setDocument(document);
			ddi.setIpAddr(IPUtils.getClinetIpByReq(request));
			ddi.setDownloadStaff((Staff) getCurrentStaff());
			ddi.setDownloadTime(new Date());
			if(ispreview) {
			    ddi.setRecordType("preview");
			}else {
				ddi.setRecordType("download");
			}
			documentService.saveDocumentDownloadInfo(ddi);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}