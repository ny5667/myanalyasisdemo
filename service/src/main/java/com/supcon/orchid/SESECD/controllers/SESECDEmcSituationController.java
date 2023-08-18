package com.supcon.orchid.SESECD.controllers;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.criterion.Restrictions;
import com.supcon.orchid.foundation.entities.Document;
import com.supcon.orchid.foundation.services.DocumentService;
import com.supcon.orchid.ec.entities.EchartsData;
import com.supcon.orchid.ec.services.EchartsDataService;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.i18n.InternationalResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.audit.entities.SignatureLog;
import com.supcon.orchid.container.mvc.support.BaseController;
import com.supcon.orchid.container.mvc.utils.XmlUtils;
import com.supcon.orchid.ec.entities.CreatorInfo;
import com.supcon.orchid.ec.entities.DataGrid;
import com.supcon.orchid.ec.entities.Sql;
import com.supcon.orchid.utils.JsonUtils;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituationEditEntity;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.services.SESECDEmcSituationService;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.ec.services.ModelServiceFoundation;
import com.supcon.orchid.ec.services.ViewServiceFoundation;
import com.supcon.orchid.ec.services.PendingService;
import com.supcon.orchid.ec.services.CreatorService;
import com.supcon.orchid.foundation.services.ImportService;
import com.supcon.orchid.foundation.services.ExportService;
import com.supcon.orchid.orm.entities.ICookie;
import com.supcon.orchid.foundation.entities.Cookie;
import com.supcon.orchid.foundation.services.CookieService;
import com.supcon.orchid.ec.entities.View;
import com.supcon.orchid.ec.entities.Model;
import com.supcon.orchid.ec.entities.Property;
import com.supcon.orchid.ec.enums.DbColumnType;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.services.QueryEntity;
import java.util.regex.Pattern;
import java.lang.reflect.Field;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import com.supcon.orchid.utils.ReflectUtils;
import org.apache.commons.lang3.StringUtils;
import com.supcon.orchid.ec.services.PrintClientService;
import com.supcon.orchid.ec.services.PrintService;
import com.supcon.supfusion.systemconfig.api.tenantconfig.annotation.ClassSystemConfigAnno;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.http.HttpStatus;
import com.supcon.orchid.services.BAPException.Code;
import com.supcon.orchid.foundation.exception.handler.BapErrorViewResolver;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.greendill.foundation.vo.BulkSubmitVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.supcon.orchid.utils.JSONPlainSerializer;
import com.supcon.orchid.container.mvc.results.BAPEntityTransformer;
import com.supcon.orchid.ec.entities.CustomPropertyModelMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import com.supcon.supfusion.framework.scaffold.auditlog.annotation.AuditBusinessLog;
import com.supcon.supfusion.framework.scaffold.auditlog.constant.OperateType;
import com.supcon.supfusion.framework.scaffold.auditlog.constant.AuditLogType;
import com.supcon.supfusion.framework.scaffold.auditlog.cache.ModelAuditLogCache;
import com.supcon.supfusion.framework.scaffold.auditlog.pojo.bo.AuditBusinessLogBO;
import com.supcon.supfusion.framework.scaffold.auditlog.strategy.AuditLogStrategy;
import com.supcon.supfusion.framework.scaffold.auditlog.cache.AuditDataLogModelCache;
import com.supcon.supfusion.framework.scaffold.auditlog.pojo.bo.AuditDataLogBO;
/* CUSTOM CODE START(controller,import,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ClassSystemConfigAnno
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Api
public class SESECDEmcSituationController extends BaseController<SESECDEmcSituation> {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认的导出工作流处理信息个数
	 */
	private static final Integer dealDefaulCount = 50;
	private static final Integer findExportDataInfosCount = 1;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private SESECDEmcSituationService emcSituationService;

	@Resource
	protected SystemCodeService systemCodeService;
	@Resource
	private ViewServiceFoundation viewServiceFoundation;
	@Autowired
	private DocumentService documentService;
	@Resource
	private CreatorService creatorService;
	@Resource
	private EchartsDataService echartsDataService;
	@Resource
	private ModelServiceFoundation modelServiceFoundation;
	@Resource
	private ImportService importService;
	@Autowired
	private ExportService exportService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	ObjectMapper mapper;
	@Autowired
    private PrintClientService printClientService;
    @Autowired
	private AuditLogStrategy<AuditBusinessLogBO> auditBusinessLogStrategy;
	@Autowired
	private BapErrorViewResolver bapErrorViewResolver;
	@Autowired
	private PendingService pendingService;

	//=================以下是controller请求的方法========================
	/**
	 * 列表视图查询
	 * @param queryEntity
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/SESECD/emcSituation/emcSituation/situationList-query", produces = "application/json")
	@ResponseBody
	public String situationListListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
		page.setPageSize(queryEntity.getPageSize());
		page.setPageNo(queryEntity.getPageNo());
		page.setPaging(queryEntity.isPaging());
		if(queryEntity.isExportFlag()) {
			page.setExportFlag(queryEntity.isExportFlag());
			page.setExportAuxiliaryModelFlag(queryEntity.isExportAuxiliaryModelFlag());
			page.setUseForImportFlag(queryEntity.isUseForImportFlag());
			page.setProperties(queryEntity.getProperties());
		}
		if(null != queryEntity.getPageNos()){
			page.setPageNos(queryEntity.getPageNos());
			page.setAll(false);
		}
		DataGrid dg = null;
		String viewCode = "";
		String datagridCode = queryEntity.getDatagridCode();
		if (datagridCode != null && !"".equals(datagridCode)) {
			if ((datagridCode.split(",")).length > 1) {
				datagridCode = datagridCode.split(",")[0];
			}
			dg = viewServiceFoundation.getDataGrid(datagridCode, "runtime");
			viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
		} else {
			if (request.getRequestURI().endsWith("-query")) {
				viewCode = request.getAttribute("ViewCode").toString();
			} else if (request.getRequestURI().replaceAll("^.*/data-", "").length() > 0) {
				String dataGridName = request.getRequestURI().replaceAll("^.*/data-", "");
				dg = viewServiceFoundation.getDataGridByName(dataGridName, "runtime");
				datagridCode = dg.getCode();
				viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
			} else {
				viewCode = request.getAttribute("ViewCode").toString();
			}
		}
		bapBeforeCustomMethod(viewCode, "situationListListQuery", queryEntity);
		int currentSqlType = Sql.TYPE_LIST_QUERY;
		emcSituationService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
				 queryEntity.getMainBusinessId(),
				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
			bapAfterCustomMethod(viewCode, "situationListListQuery", page);
		}
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.reportPerson.name,result.occursTime,result.commonAddress.name,result.position,result.describtion,result.situationType.id,result.situationType.value,result.woundedPerson,result.deathPerson,result.source.id,result.source.value,result.eventId.accidentName,result.reportPerson.id,result.commonAddress.id,result.eventId.id,result.pending.processDescription,result.pending.processId,result.pending.deploymentId,result.pending.bulkDealFlag,result.pending.activityType,result.pending.activityName,result.pending.processKey,result.pending.processVersion,result.commonAddress.attrMap.*,result.createStaff.attrMap.*,result.deleteStaff.attrMap.*,result.effectStaff.attrMap.*,result.enclosureAttachementInfo,result.enclosureDocument.id,result.eventId.attrMap.*,result.iconAttachementInfo,result.iconDocument.id,result.iconObjGis.attrMap.*,result.modifyStaff.attrMap.*,result.objparama.attrMap.*,result.objparamb.attrMap.*,result.ownerDepartment.attrMap.*,result.ownerPosition.attrMap.*,result.ownerStaff.attrMap.*,result.reportPerson.attrMap.*,result.*.attrMap.*";
		String excludes = "*";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		if(queryEntity.isExportFlag()){
			getFullObject(page);
			bapAfterCustomMethod(viewCode, "situationListListQuery", page);
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
			exportService.exportExcel(out,response, model, page, viewCode);
			emcSituationService.excelExport();
			return null;
		}
		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
	}
        /**
    	 * 列表视图查询
    	 * @param queryEntity
    	 * @return
    	 * @throws Exception
    	 */
    	@PostMapping(value = "/SESECD/emcSituation/emcSituation/proj/{viewName}-query", produces = "application/json")
    	@ResponseBody
    	public String projListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
    		page.setPageSize(queryEntity.getPageSize());
    		page.setPageNo(queryEntity.getPageNo());
    		page.setPaging(queryEntity.isPaging());
    		if(queryEntity.isExportFlag()) {
    			page.setExportFlag(queryEntity.isExportFlag());
    			page.setExportAuxiliaryModelFlag(queryEntity.isExportAuxiliaryModelFlag());
    			page.setUseForImportFlag(queryEntity.isUseForImportFlag());
    			page.setProperties(queryEntity.getProperties());
    		}
    		if(null != queryEntity.getPageNos()){
    			page.setPageNos(queryEntity.getPageNos());
    			page.setAll(false);
    		}
    		DataGrid dg = null;
    		String viewCode = "";
    		String datagridCode = queryEntity.getDatagridCode();
    		if (datagridCode != null && !"".equals(datagridCode)) {
    			if ((datagridCode.split(",")).length > 1) {
    				datagridCode = datagridCode.split(",")[0];
    			}
    			dg = viewServiceFoundation.getDataGrid(datagridCode, "runtime");
    			viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
    		} else {
    			if (request.getRequestURI().endsWith("-query")) {
    				viewCode = request.getAttribute("ViewCode").toString();
    			} else if (request.getRequestURI().replaceAll("^.*/data-", "").length() > 0) {
    				String dataGridName = request.getRequestURI().replaceAll("^.*/data-", "");
    				dg = viewServiceFoundation.getDataGridByName(dataGridName, "runtime");
    				datagridCode = dg.getCode();
    				viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
    			} else {
    				viewCode = request.getAttribute("ViewCode").toString();
    			}
    		}
    		int currentSqlType = Sql.TYPE_LIST_QUERY;
    		emcSituationService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
    				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
    				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
    				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
    				 queryEntity.getMainBusinessId(),
    				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
    				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
    				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
    		if(!queryEntity.isExportFlag()){
    		}
    		String includes = viewServiceFoundation.generateProjectViewResultField(viewCode);
    		String excludes = "*";
    		if(queryEntity.isExportFlag()){
    			getFullObject(page);
    			response.setContentType("application/binary;charset=UTF-8");
    			ServletOutputStream out = response.getOutputStream();
    			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
    			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
    			exportService.exportExcel(out,response,model, page, viewCode);
    			emcSituationService.excelExport();
    			return null;
    		}
    		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
    	}
	private void getFullObject(Page<SESECDEmcSituation> page){
		List<SESECDEmcSituation> emcSituations = page.getResult();
		List<Long> ids = emcSituations.stream().map(SESECDEmcSituation::getId).collect(Collectors.toList());
        page.setResult(emcSituationService.findSESECDEmcSituationByIds(ids));
	}





	/**
	 *  参照视图公用数据查询方法
	*/
	@PostMapping(value = "/SESECD/emcSituation/emcSituation/situationListRef-query", produces = "application/json")
	@ResponseBody
	public String situationListRefListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
		page.setPageSize(queryEntity.getPageSize());
		page.setPageNo(queryEntity.getPageNo());
		page.setPaging(queryEntity.isPaging());
		if(queryEntity.isExportFlag()) {
			page.setExportFlag(queryEntity.isExportFlag());
			page.setExportAuxiliaryModelFlag(queryEntity.isExportAuxiliaryModelFlag());
			page.setUseForImportFlag(queryEntity.isUseForImportFlag());
			page.setProperties(queryEntity.getProperties());
		}
		if(null != queryEntity.getPageNos()){
			page.setPageNos(queryEntity.getPageNos());
			page.setAll(false);
		}
		String viewCode = request.getAttribute("ViewCode").toString();
		bapBeforeCustomMethod(viewCode, "situationListRefListQuery", queryEntity);
		// 加上currentSqlType，用于区分列表查询和参照查询
		// 参照查询需要传crossCompanyFlag
		// 参照查询不传datagridCode
		// 启用工作流时传processKey和entityCode,不启用工作流不传
		int currentSqlType = Sql.TYPE_LIST_REFERENCE;
		// 实体未启用工作流
		emcSituationService.listQuery(page, currentSqlType, viewCode, null, queryEntity.getCrossCompanyFlag(), null,
			null, queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
			queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
			queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
			queryEntity.getMainBusinessId(),
			queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
			queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
			queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
            bapAfterCustomMethod(viewCode, "situationListRefListQuery", page);
        }
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.reportPerson.name,result.occursTime,result.position,result.describtion,result.situationType.id,result.situationType.value,result.woundedPerson,result.deathPerson,result.source.id,result.source.value,result.eventId.accidentName,result.reportPerson.id,result.eventId.id,result.enclosureAttachementInfo,result.enclosureDocument.id,result.iconAttachementInfo,result.iconDocument.id,result.*.attrMap.*,data.id,data.value";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		String ext = queryEntity.getIncludes();
        if(null != ext){
            String[] includeArr = ext.split(",");
            for(int i =0; i < includeArr.length; i++){
                String temp = "result." + includeArr[i];
                if(includes.indexOf(temp) == -1){
                    includes += "," + temp;
                }
            }
        }
        if(queryEntity.isExportFlag()){
            getFullObject(page);
			bapAfterCustomMethod(viewCode, "situationListRefListQuery", page);
            response.setContentType("application/binary;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
            Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
            exportService.exportExcel(out, response,model, page, viewCode);
            emcSituationService.excelExport();
            return null;
        }
		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
	}
@PostMapping(value = "/SESECD/emcSituation/emcSituation/projref/{viewName}-query", produces = "application/json")
    	@ResponseBody
    	public String projRefListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
    		page.setPageSize(queryEntity.getPageSize());
    		page.setPageNo(queryEntity.getPageNo());
    		page.setPaging(queryEntity.isPaging());
    		if(queryEntity.isExportFlag()) {
    			page.setExportFlag(queryEntity.isExportFlag());
    			page.setExportAuxiliaryModelFlag(queryEntity.isExportAuxiliaryModelFlag());
    			page.setUseForImportFlag(queryEntity.isUseForImportFlag());
    			page.setProperties(queryEntity.getProperties());
    		}
    		if(null != queryEntity.getPageNos()){
    			page.setPageNos(queryEntity.getPageNos());
    			page.setAll(false);
    		}
    		String viewCode = request.getAttribute("ViewCode").toString();
    		// 加上currentSqlType，用于区分列表查询和参照查询
    		// 参照查询需要传crossCompanyFlag
    		// 参照查询不传datagridCode
    		// 启用工作流时传processKey和entityCode,不启用工作流不传
    		int currentSqlType = Sql.TYPE_LIST_REFERENCE;
    		// 实体未启用工作流
    		emcSituationService.listQuery(page, currentSqlType, viewCode, null, queryEntity.getCrossCompanyFlag(), null,
    			null, queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
    			queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
    			queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
    			queryEntity.getMainBusinessId(),
    			queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
    			queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
    			queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
    		if(!queryEntity.isExportFlag()){
            }
            String includes = viewServiceFoundation.generateProjectViewResultField(viewCode);
            String ext = queryEntity.getIncludes();
            if(null != ext){
                String[] includeArr = ext.split(",");
                for(int i =0; i < includeArr.length; i++){
                    String temp = "result." + includeArr[i];
                    if(includes.indexOf(temp) == -1){
                        includes += "," + temp;
                    }
                }
            }
            if(queryEntity.isExportFlag()){
                getFullObject(page);
                response.setContentType("application/binary;charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
                Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
                exportService.exportExcel(out,response, model, page, viewCode);
                emcSituationService.excelExport();
                return null;
            }
    		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
    	}

	@GetMapping(value = "/SESECD/emcSituation/emcSituation/selectData", produces = "application/json")
	@ResponseBody
	public String selectData(String includes) throws Exception {
		Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
		page.setPageSize(page.getMaxPageSize());
		List<SESECDEmcSituation> result = emcSituationService.findEmcSituationsByHql("from " + SESECDEmcSituation.JPA_NAME + " where valid =1");
		page.setResult(result);
		bapAfterCustomMethod("selectData", "selectData", page);
		String includesResult = "result.id";
		if(null != includes){
			String[] includeArr = includes.split(",");
			for(int i =0; i < includeArr.length; i++){
				includesResult += ",result." + includeArr[i];
			}
		}
		return JSONPlainSerializer.serializeAsJSON(page, includesResult, new BAPEntityTransformer());
	}
/**
	 *  移动参照视图公用数据查询方法
	*/


	/**
	 * 获取编辑视图的数据，向对象的pending加入taskDescription,防止无待办时缺少单据状态信息
	 *
	 * @param id
	 * @param pendingId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = {"/SESECD/emcSituation/emcSituation/data/{id}",
		"/SESECD/emcSituation/emcSituation/proj/data/{id}"}, produces = "application/json")
	@ResponseBody
	public Object getData(@PathVariable long id, Long pendingId, String includes, HttpServletRequest request) throws Exception {
		SESECDEmcSituation result = emcSituationService.getEmcSituation(id,pendingService.getViewCodeByPending(pendingId));
		// 返回图片类型字段信息
		List<Document> iconDocumentList = documentService.getPictureByLinkIdAndType(result.getId(), "SESECD_emcSituation_EmcSituation", "SESECD_1.0.0_emcSituation_EmcSituation_icon");
		if (!iconDocumentList.isEmpty()) {
		    for(Document iconDocument : iconDocumentList){
			    iconDocument.setPath(documentService.getDocumentAbsolutePath(iconDocument));
		    }
			result.setIconDocumentList(iconDocumentList);
		}
		bapGetDataAfterCustomMethod("getData", result, includes);
		return StringUtils.isEmpty(includes) ? mapper.writeValueAsString(result) : JSONPlainSerializer.serializeAsJSON(result, includes, new BAPEntityTransformer());
	}
	/**
	 * 视图保存/提交
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = {
		"/SESECD/emcSituation/emcSituation/situationEdit/save",
		"/SESECD/emcSituation/emcSituation/situationEdit__mobile__/save",
		"/SESECD/emcSituation/emcSituation/situationView/save",
		"/SESECD/emcSituation/emcSituation/situationMapView/save",
		"/SESECD/emcSituation/emcSituation/situationEdit/submit",
		"/SESECD/emcSituation/emcSituation/situationEdit__mobile__/submit",
		"/SESECD/emcSituation/emcSituation/situationView/submit",
		"/SESECD/emcSituation/emcSituation/situationMapView/submit",
		}, produces = "application/json")
	@ResponseBody
	public Object submit(Long id, HttpServletRequest request) throws Exception {
		SESECDEmcSituationEditEntity editEntity;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		bapBeforeCustomMethod("SESECD_1.0.0_emcSituation_EmcSituation", "submit", null);
		if (null != id) {
			SESECDEmcSituation ordinaryEmcSituation = emcSituationService.getEmcSituationWithoutMultiselect(id);
			// 前端传的json
			//byte[] bytes = (byte[]) request.getAttribute("bytes");
			String body =  request.getAttribute("body").toString();
			Map<String,Object> editEntityMap = objectMapper.readValue(body, Map.class);
			Map<String,Object> emcSituationMap = (Map<String, Object>) editEntityMap.get("emcSituation");
			editEntity = objectMapper.readValue(body, SESECDEmcSituationEditEntity.class);
			SESECDEmcSituation emcSituation = editEntity.getEmcSituation();
			// 只更新前端传的，其他按数据库里原有的数据
			for (Field field : ReflectUtils.getDeepDeclaredFields(SESECDEmcSituation.class)) {
				// 前端没传，继续遍历下一个字段
				if (null == emcSituationMap.get(field.getName())) {
					continue;
				}
				field.setAccessible(true);
				// 实际更新
				field.set(ordinaryEmcSituation, field.get(emcSituation));
			}
			editEntity.setEmcSituation(ordinaryEmcSituation);
		} else {
			editEntity = objectMapper.readValue(request.getAttribute("body").toString(), SESECDEmcSituationEditEntity.class);
		}
		SESECDEmcSituation emcSituation = editEntity.getEmcSituation();
		String operateType = editEntity.getOperateType();
		Map<String, Object> responseMap = null;
        boolean isSuccess = true;
        String exceptionDescription = null;
        try {
            responseMap = emcSituationService.submit(emcSituation, editEntity.getWorkFlowVar(),
                    editEntity.getPendingId(), editEntity.getDeploymentId(), operateType,
                    editEntity.getPendingActivityType(), editEntity.getWebSignetFlag(),
                    (SignatureLog) request.getAttribute("signatureLog"),
                    (Map<String, Object>) request.getAttribute("__orchid_ass_fileuploads__"), editEntity.getSuperEdit(),
                    editEntity.getDgList(), editEntity.getDgDeletedIds(), editEntity.getViewCode(),
                    editEntity.getActivityName(), getLocale(), editEntity.getViewSelect());
         } catch (Exception e) {
            isSuccess = false;
            exceptionDescription = e.getMessage() == null ?  e.toString() : e.getMessage();
            throw e;
        } finally {
            // 如果模型需要审计
            boolean modelNeedAudit = ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_emcSituation_EmcSituation", AuditLogType.BUSINESS);
            if (modelNeedAudit) {
                AuditBusinessLogBO auditBusinessLogBO = auditBusinessLogStrategy.buildAuditLog();
                auditBusinessLogBO.setOperateType(getSubmitOperateType(id, editEntity));
                if (OperateType.OTHER.equals(auditBusinessLogBO.getOperateType())) {
                    auditBusinessLogBO.setDescription("超级编辑");
                }
                auditBusinessLogBO.setSuccess(isSuccess);
                auditBusinessLogBO.setExceptionDescription(exceptionDescription);
                auditBusinessLogBO.setModuleCode("SESECD");
                auditBusinessLogBO.setModuleName("SESECD.modulename.randon1594690070190");
                auditBusinessLogBO.setMainModelCode("SESECD_1.0.0_emcSituation_EmcSituation");
                auditBusinessLogBO.setMainModelName("SESECD.emcSituation.EmcSituation");
                auditBusinessLogBO.setMainEntityCode("SESECD_1.0.0_emcSituation");
                auditBusinessLogBO.setMainEntityName("SESECD.entityname.randon1577176777829");
                auditBusinessLogStrategy.publishAuditLogLazy(auditBusinessLogBO);
            }
        }
        afterSubmitAtNewTransactionCustomMethod(editEntity.getViewCode(), "submit", emcSituation);
        saveLastAssignUser(editEntity.getWorkFlowVar());
		Map<String, Object> fileuploads = (Map<String, Object>) request.getAttribute("__orchid_fileuploads__");
		if (null == fileuploads) {
			fileuploads = new HashMap<String, Object>();
		}
		fileuploads.put("linkId", emcSituation.getId());
		request.setAttribute("__orchid_fileuploads__", fileuploads);
		bapAfterCustomMethod("SESECD_1.0.0_emcSituation_EmcSituation", "submit", null);
		return responseMap;
	}

/**
    	 * 视图保存/提交
    	 * @param id
    	 * @return
    	 * @throws Exception
    	 */
    	@PostMapping(value = {
    		"/SESECD/emcSituation/emcSituation/proj/{viewName}/save",
    		"/SESECD/emcSituation/emcSituation/proj/{viewName}/submit"
    		}, produces = "application/json")
    	@ResponseBody
    	public Object projsubmit(Long id, HttpServletRequest request) throws Exception {
    		SESECDEmcSituationEditEntity editEntity;
    		ObjectMapper objectMapper = new ObjectMapper();
    		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
    		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    		bapBeforeCustomMethod("SESECD_1.0.0_emcSituation_EmcSituation", "submit", null);
    		if (null != id) {
    			SESECDEmcSituation ordinaryEmcSituation = emcSituationService.getEmcSituationWithoutMultiselect(id);
    			// 前端传的json
    			//byte[] bytes = (byte[]) request.getAttribute("bytes");
    			String body =  request.getAttribute("body").toString();
    			Map<String,Object> editEntityMap = objectMapper.readValue(body, Map.class);
    			Map<String,Object> emcSituationMap = (Map<String, Object>) editEntityMap.get("emcSituation");
    			editEntity = objectMapper.readValue(body, SESECDEmcSituationEditEntity.class);
    			SESECDEmcSituation emcSituation = editEntity.getEmcSituation();
    			// 只更新前端传的，其他按数据库里原有的数据
    			for (Field field : ReflectUtils.getDeepDeclaredFields(SESECDEmcSituation.class)) {
    				// 前端没传，继续遍历下一个字段
    				if (null == emcSituationMap.get(field.getName())) {
    					continue;
    				}
    				field.setAccessible(true);
    				// 实际更新
    				field.set(ordinaryEmcSituation, field.get(emcSituation));
    			}
    			editEntity.setEmcSituation(ordinaryEmcSituation);
    		} else {
    			editEntity = objectMapper.readValue(request.getAttribute("body").toString(), SESECDEmcSituationEditEntity.class);
    		}
    		SESECDEmcSituation emcSituation = editEntity.getEmcSituation();
    		String operateType = editEntity.getOperateType();
    		Map<String, Object> responseMap = null;
            boolean isSuccess = true;
            String exceptionDescription = null;
            try {
                responseMap = emcSituationService.submit(emcSituation, editEntity.getWorkFlowVar(),
                        editEntity.getPendingId(), editEntity.getDeploymentId(), operateType,
                        editEntity.getPendingActivityType(), editEntity.getWebSignetFlag(),
                        (SignatureLog) request.getAttribute("signatureLog"),
                        (Map<String, Object>) request.getAttribute("__orchid_ass_fileuploads__"), editEntity.getSuperEdit(),
                        editEntity.getDgList(), editEntity.getDgDeletedIds(), editEntity.getViewCode(),
                        editEntity.getActivityName(), getLocale(), editEntity.getViewSelect());
             } catch (Exception e) {
                isSuccess = false;
                exceptionDescription = e.getMessage() == null ?  e.toString() : e.getMessage();
                throw e;
            } finally {
                // 如果模型需要审计
                boolean modelNeedAudit = ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_emcSituation_EmcSituation", AuditLogType.BUSINESS);
                if (modelNeedAudit) {
                    AuditBusinessLogBO auditBusinessLogBO = auditBusinessLogStrategy.buildAuditLog();
                    auditBusinessLogBO.setOperateType(getSubmitOperateType(id, editEntity));
                    if (OperateType.OTHER.equals(auditBusinessLogBO.getOperateType())) {
                        auditBusinessLogBO.setDescription("超级编辑");
                    }
                    auditBusinessLogBO.setSuccess(isSuccess);
                    auditBusinessLogBO.setExceptionDescription(exceptionDescription);
                    auditBusinessLogBO.setModuleCode("SESECD");
                    auditBusinessLogBO.setModuleName("SESECD.modulename.randon1594690070190");
                    auditBusinessLogBO.setMainModelCode("SESECD_1.0.0_emcSituation_EmcSituation");
                    auditBusinessLogBO.setMainModelName("SESECD.emcSituation.EmcSituation");
                    auditBusinessLogBO.setMainEntityCode("SESECD_1.0.0_emcSituation");
                    auditBusinessLogBO.setMainEntityName("SESECD.entityname.randon1577176777829");
                    auditBusinessLogStrategy.publishAuditLogLazy(auditBusinessLogBO);
                }
            }
            afterSubmitAtNewTransactionCustomMethod(editEntity.getViewCode(), "submit", emcSituation);
            saveLastAssignUser(editEntity.getWorkFlowVar());
    		Map<String, Object> fileuploads = (Map<String, Object>) request.getAttribute("__orchid_fileuploads__");
    		if (null == fileuploads) {
    			fileuploads = new HashMap<String, Object>();
    		}
    		fileuploads.put("linkId", emcSituation.getId());
    		request.setAttribute("__orchid_fileuploads__", fileuploads);
    		bapAfterCustomMethod("SESECD_1.0.0_emcSituation_EmcSituation", "submit", null);
    		return responseMap;
    	}

	/**
	 * 判断进入submit接口的操作类型
	 */
	private OperateType getSubmitOperateType(Long id, SESECDEmcSituationEditEntity editEntity) {
        // 超级编辑
        if (editEntity.getSuperEdit() != null && editEntity.getSuperEdit()) {
            return OperateType.OTHER;
        }
		// 如果id为空，则代表新增
		if (id == null) {
			return OperateType.ADD;
		}
		// 工作流相关
		WorkFlowVar workFlowVar = editEntity.getWorkFlowVar();
		if ("submit".equals(editEntity.getOperateType()) && workFlowVar != null) {
			// 驳回
			if ("reject".equals(workFlowVar.getOutcomeType())) {
				return OperateType.REJECT;
			}
			// 作废
			if ("cancel".equals(workFlowVar.getOutcomeType())) {
				return OperateType.INVALID;
			}
			// 提交
            if ("normal".equals(workFlowVar.getOutcomeType())) {
                return OperateType.SUBMIT;
            }
		}
		return OperateType.MODIFY;
	}



	/**
	 * 打开页面situationEdit
	 * url:/SESECD/emcSituation/emcSituation/situationEdit
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/situationEdit")
	public String situationEditView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_emcSituation_situationEdit", "situationEditView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_emcSituation_situationEdit")){
			return "situationEdit-mobile.html";
		}
		return "situationEdit.html";
	}
	/**
	 * 打开页面situationView
	 * url:/SESECD/emcSituation/emcSituation/situationView
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/situationView")
	public String situationViewView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_emcSituation_situationView", "situationViewView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_emcSituation_situationView")){
			return "situationView-mobile.html";
		}
		return "situationView.html";
	}
	/**
	 * 打开页面situationMapView
	 * url:/SESECD/emcSituation/emcSituation/situationMapView
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/situationMapView")
	public String situationMapViewView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_emcSituation_situationMapView", "situationMapViewView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_emcSituation_situationMapView")){
			return "situationMapView-mobile.html";
		}
		return "situationMapView.html";
	}
	/**
	 * 打开页面situationList
	 * url:/SESECD/emcSituation/emcSituation/situationList
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/situationList")
	public String situationListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_emcSituation_situationList", "situationListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_emcSituation_situationList")){
			return "situationList-mobile.html";
		}
		return "situationList.html";
	}
	/**
	 * 打开页面situationListRef
	 * url:/SESECD/emcSituation/emcSituation/situationListRef
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/situationListRef")
	public String situationListRefView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_emcSituation_situationListRef", "situationListRefView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_emcSituation_situationListRef")){
			return "situationListRef-mobile.html";
		}
		return "situationListRef.html";
	}
    /**
	 * 打开页面
	 * url:
	 * @return
	 */
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/proj/{viewName}")
	public String projView(@RequestParam(value = "clientType",required = false) String clientType,@PathVariable String viewName,HttpServletRequest request) throws Exception {
		String projViewRelevantPath = "/" + RpcContext.getContext().getTenantId() + request.getRequestURI() + "/../../"+viewName+"-proj.html";
		String url = request.getRequestURI();
		String tenantId = RpcContext.getContext().getTenantId();
		if (viewServiceFoundation.checkProjectHtml(url, tenantId)) {
			return projViewRelevantPath;
		}
		emcSituationService.generateHtmlFile(request.getRequestURI(), projViewRelevantPath);
		return projViewRelevantPath;
	}
	private boolean hasMobileView(String viewCode){
    	View mobileView=viewServiceFoundation.getView(viewCode+"__mobile__");
    	if(null==mobileView){
    		return false;
    	}
    	return true;
    }

	/**
	 * 进入编辑视图时请求的视图参数
	 *
	 * @param id		  id
	 * @param viewCode
	 * @param tableInfoId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/SESECD/emcSituation/emcSituation/editStates", produces = "application/json")
	@ResponseBody
	public String editStates(Long id, String viewCode, Long tableInfoId) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		Boolean hasSupervision = false;
		Boolean hasPayCloseAttention = false;
		CreatorInfo creatorInfo = null;
		// 新增
		if (null == id) {
			creatorInfo = creatorService.getCurrent();
		} else {
			// 修改
			Map<String, Long> createInfoMap = emcSituationService.getCreateInfoMap(id);
			creatorInfo = creatorService.get(createInfoMap.get("createStaffId"), createInfoMap.get("createPositionId"), createInfoMap.get("createDepartmentId"));
		}
		responseMap.put("hasSupervision", hasSupervision);
		responseMap.put("hasPayCloseAttention", hasPayCloseAttention);
		responseMap.put("creatorInfo", creatorInfo);
		String includes = "creatorInfo.staff.id, creatorInfo.staff.name, creatorInfo.positions.id,"
				+ "creatorInfo.positions.name, creatorInfo.positions.layRec, creatorInfo.positions.cid, creatorInfo.positions.isVirtual,"
				+ "creatorInfo.mainPosition.id, creatorInfo.mainPosition.cid, creatorInfo.position.id, creatorInfo.position.cid, creatorInfo.position.name";
		String excludes = "creatorInfo.*";
		return JsonUtils.objectToJson(responseMap, includes, excludes);
	}

	/**
	 * 删除
	 *
	 * @param ids	 id@version，删除多行以逗号隔开
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/SESECD/emcSituation/emcSituation/delete")
	@ResponseBody
	@AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.DELETE,
                      modelCodes = {"SESECD_1.0.0_emcSituation_EmcSituation"},
                      mainModelCode = "SESECD_1.0.0_emcSituation_EmcSituation",
                      mainModelName = "SESECD.emcSituation.EmcSituation",
                      mainEntityCode = "SESECD_1.0.0_emcSituation",
                      mainEntityName = "SESECD.entityname.randon1577176777829",
                      isLazy = true
    )		
	public Map<String, Object> delete(String ids, HttpServletRequest request) throws Exception {
		SignatureLog signatureLog = (SignatureLog) request.getAttribute("signatureLog");
		String body = request.getAttribute("body").toString();
		if (StringUtils.isNotEmpty(body)) {
            Map<String, Object> parameters = mapper.readValue(body, Map.class);
            if (null != parameters.get("ids")) {
                ids = (String) parameters.get("ids");
            }
        }
		emcSituationService.deleteEmcSituation(ids, signatureLog);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("dealSuccessFlag", true);
		responseMap.put("operateType", "delete");
		responseMap.put("operate", "delete");
		return responseMap;
	}

	/**
	 * 获取datagrid
	 *
	 * @param id			ID
	 * @param refId		 参考复制ID
	 * @param datagridCode  表格编码
	 * @return
	 */
	@PostMapping(value = {
	    "/SESECD/emcSituation/emcSituation/data-{dg.name}",
		"/SESECD/emcSituation/emcSituation/proj/data-{dg.name}"
	},  produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dataGridData(Long id, Long refId, String datagridCode, @RequestBody(required = false)QueryEntity queryEntity,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
			if (queryEntity == null) {
			    queryEntity = new QueryEntity();
			}
			DataGrid dg = viewServiceFoundation.getDataGrid(datagridCode);
            		if(dg !=null && dg.getDataGridType()==1){
                        Page<SESECDEmcSituation> page = new Page<SESECDEmcSituation>();
                        page.setPageSize(queryEntity.getPageSize());
                        page.setPageNo(queryEntity.getPageNo());
                        page.setPaging(queryEntity.isPaging());
                        if(queryEntity.isExportFlag()) {
                            page.setExportFlag(queryEntity.isExportFlag());
                            page.setExportAuxiliaryModelFlag(queryEntity.isExportAuxiliaryModelFlag());
                            page.setUseForImportFlag(queryEntity.isUseForImportFlag());
                            page.setProperties(queryEntity.getProperties());
                        }
                        if(null != queryEntity.getPageNos()){
                            page.setPageNos(queryEntity.getPageNos());
                            page.setAll(false);
                        }
                            String viewCode = "";
                            datagridCode = queryEntity.getDatagridCode();
                            if (datagridCode != null && !"".equals(datagridCode)) {
                            if ((datagridCode.split(",")).length > 1) {
                                datagridCode = datagridCode.split(",")[0];
                            }
                                dg = viewServiceFoundation.getDataGrid(datagridCode, "runtime");
                                viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
                            } else {
                                if (request.getRequestURI().endsWith("-query")) {
                                	viewCode = request.getAttribute("ViewCode").toString();
                            } else if (request.getRequestURI().replaceAll("^.*/data-", "").length() > 0) {
                                String dataGridName = request.getRequestURI().replaceAll("^.*/data-", "");
                                dg = viewServiceFoundation.getDataGridByName(dataGridName, "runtime");
                                datagridCode = dg.getCode();
                                queryEntity.setDatagridCode(datagridCode);
                                viewCode = viewServiceFoundation.getViewFromDg(datagridCode).getCode();
                            } else {
                                viewCode = request.getAttribute("ViewCode").toString();
                            }
                        }
                        bapBeforeCustomMethod(viewCode, "dataGridData" + dg.getName(), queryEntity);
                        int currentSqlType = Sql.TYPE_LIST_QUERY;
                        emcSituationService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
                        queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
                        queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
                        queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
                        queryEntity.getMainBusinessId(),
                        queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
                        queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
                        queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
                        if(!queryEntity.isExportFlag()){
                            bapAfterCustomMethod(viewCode, "dataGridData" + dg.getName(), page);
                        }
                        String includes = viewServiceFoundation.generateProjectDataTableResultField(datagridCode);
                        if(queryEntity.isExportFlag()){
                            getFullObject(page);
                             response.setContentType("application/binary;charset=UTF-8");
                             ServletOutputStream out = response.getOutputStream();
                             response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
                             Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
                             exportService.exportExcel(out, response,model, page, null);
                             emcSituationService.excelExport();
                             return null;
                        }
                        return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
            		}else{
                        Page dgPage = null;
                        if (null == dgPage && queryEntity!=null) {
                            dgPage = new Page(queryEntity.getPageNo(), queryEntity.getPageSize());
                            dgPage.setExportFlag(queryEntity.isExportFlag());
                            dgPage.setUseForImportFlag(queryEntity.isUseForImportFlag());
                        }else{
                            dgPage = new Page(1,20);
                        }
                        String dataGridName = request.getRequestURI().replaceAll("^.*/data-", "");
                        bapBeforeCustomMethod(datagridCode, "dataGridData" + dg.getName(), queryEntity);
                        emcSituationService.dataGridData(dgPage, datagridCode, dataGridName, request.getParameterMap(), refId,id,queryEntity);
						bapAfterCustomMethod(datagridCode, "dataGridData" + dg.getName(), dgPage);
						String includes = viewServiceFoundation.generateProjectDataGridResultField(datagridCode);
                        log.info("includes=" + includes);
                        if(queryEntity.isExportFlag()){
                            response.setContentType("application/binary;charset=UTF-8");
                            ServletOutputStream out = response.getOutputStream();
                            Model dgTargetModel = viewServiceFoundation.getTargetModelFromDg(dg.getCode());
                            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(dgTargetModel.getCode() + ".xls", "UTF-8"));
                            exportService.exportExcel(out, response, dgTargetModel, dgPage, null, datagridCode);
                            emcSituationService.excelExport();
                            return null;
                        }
						// 可处理国际化
                        return JSONPlainSerializer.serializeAsJSON(dgPage, includes, new BAPEntityTransformer());
            	}
	}



	@GetMapping(value = "/SESECD/emcSituation/emcSituation/echartsData", produces = "application/json")
	@ResponseBody
	public Object echartsData(String echartsCode) throws Exception {
		if (echartsCode == null || "".equals(echartsCode)) {
			return null;
		}
		//bapBeforeCustomMethod(echartsCode, "echarts");
		EchartsData echartsData = echartsDataService.getEchartsWithData(echartsCode);
		//bapAfterCustomMethod(echartsCode, "echarts");
		return echartsData;
	}

    private ReadWriteLock STATE_READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private volatile static int TASK_STATE = 0;
	private volatile static String IMPORT_USER = "";
	public int getSTATE() {
		Lock readLock = STATE_READ_WRITE_LOCK.readLock();
		readLock.lock();
		int state = TASK_STATE;
		readLock.unlock();
		return state;
	}
	public void changeState(int operationType) {
		Lock lock = STATE_READ_WRITE_LOCK.writeLock();
		lock.lock();
		try {
			if (operationType == 0) {
				TASK_STATE += 1;
				IMPORT_USER = getCurrentUser().getUsername();
			} else {
				TASK_STATE -= 1;
				IMPORT_USER="";
			}
		} finally {
			lock.unlock();
		}
	}
	@GetMapping(value = {"/SESECD/emcSituation/emcSituation/importMainXls","/SESECD/emcSituation/emcSituation/proj/importMainXls"}, produces = "application/json")
	@ResponseBody
	public Object importMainXls(String filePath, String viewCode, boolean isReplace, boolean isIgnore, HttpServletRequest request) {
		Object[] importServices = new Object[]{"com.supcon.orchid.SESECD.services.SESECDEmcSituationService"
						, "com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService"
						, "com.supcon.orchid.SESGISConfig.services.SESGISConfigFrequentplaceService"
						, "com.supcon.orchid.SESGISConfig.services.SESGISConfigIconLibraryService"
						, "com.supcon.orchid.foundation.services.DepartmentService"
						, "com.supcon.orchid.foundation.services.PositionService"
						, "com.supcon.orchid.foundation.services.StaffService"
		};
		//过滤重复的Service
		List<Object> objects = new ArrayList<Object>();
		for (int i=0; i<importServices.length; i++) {
			if(!objects.contains(importServices[i])){
				objects.add(importServices[i]);
			}
		}

		List<CustomPropertyModelMapping> list = modelServiceFoundation.getCustomPropertiesWithType("SESECD_1.0.0_emcSituation", DbColumnType.OBJECT);
		if(null != list && !list.isEmpty()){
			for(CustomPropertyModelMapping mapping : list){
				if(null != mapping.getAssociatedProperty() && null != mapping.getAssociatedProperty().getModel()){
					String moduleArtifact = mapping.getAssociatedProperty().getModel().getEntity().getModule().getArtifact();
					String serviceName = "com.supcon.orchid." + moduleArtifact + ".services.";
					if("1.0".equals(mapping.getAssociatedProperty().getModel().getEcVersion())){
						serviceName += mapping.getAssociatedProperty().getModel().getModelName() + "Service";
					} else {
						serviceName += mapping.getAssociatedProperty().getModel().getJpaName() + "Service";
					}
					if(!objects.contains(serviceName)){
						objects.add(serviceName);
					}
				}
			}
		}

		View mainView = viewServiceFoundation.getView(viewCode);
		Model mainModel = viewServiceFoundation.getAssModelFromView(mainView.getCode());
		List<Model> models = modelServiceFoundation.getModels("SESECD_1.0.0_emcSituation");
		for(Model m : models){
			if(!m.getCode().equals(mainModel.getCode())){
				Set<Property> props = modelServiceFoundation.findProperties(m.getCode());
				for(Property p : props){
					if(!p.getIsInherent() && !p.getIsCustom() && p.getType().toString().equals("OBJECT")){
						Property associatedProperty = modelServiceFoundation.findAssociatedProperty(p.getCode());
						Model associatedModel = modelServiceFoundation.findModelFromProperty(associatedProperty.getCode());
						String moduleName = associatedModel.getModuleCode();
						if(associatedModel.getModuleCode().indexOf("_") != -1){
							moduleName = associatedModel.getModuleCode().substring(0,associatedModel.getModuleCode().indexOf("_"));
						}
						String serviceStr = "com.supcon.orchid."+ moduleName + ".services." + com.supcon.orchid.utils.StringUtils.firstLetterToUpper(moduleName) + associatedModel.getModelName() + "Service";
						if(!objects.contains(serviceStr)){
							objects.add(serviceStr);
						}
					}
				}
			}
		}
		Iterator<Object> it = objects.iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if(obj == null){
				it.remove();
			}
		}
		Object[] newService = new Object[objects.size()];
		objects.toArray(newService);
		File dataFile = new File(filePath);
		long startTime = System.currentTimeMillis();
		String xlsResult = "";
		try {
			if(dataFile != null){
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
				String time = sdFormat.format(new Date());
				String name = request.getParameter("dataFileFileName");
				if(name == null || name == ""){
					name = "default";
				}
				SignatureLog signatureLog=(SignatureLog)request.getAttribute("signatureLog");
                if (getSTATE() == 0) {
					synchronized (this) {
						changeState(0);
						try {
				            xlsResult = importService.batchImports(viewCode, "SESECD_1.0.0_emcSituation_EmcSituation", dataFile, time, name, isReplace, isIgnore, true, "xls", signatureLog, newService);
                        } finally {
                            changeState(1);
                        }
                    }
                }else{
                    xlsResult="用户"+IMPORT_USER+"正在导入，请稍后再试";
                }
			}
		} catch (Exception e) {
		    xlsResult = e.getMessage();
			log.error(xlsResult, e);
		}
		long endTime = System.currentTimeMillis();
		log.info("action upload cast {} ms", (endTime - startTime));
		return xlsResult;
	}

    @GetMapping(value = {"/SESECD/emcSituation/emcSituation/importXls","/SESECD/emcSituation/emcSituation/proj/importXls"}, produces = "application/json")
	@ResponseBody
    public String imports(String filePath, String datagridCode, String tid, boolean isReplace, boolean isIgnore, HttpServletRequest request) throws Exception {
        Object[] importServices = new Object[]{"com.supcon.orchid.SESECD.services.SESECDEmcSituationService"
        };
        //过滤重复的Service
        List<Object> objects = new ArrayList<Object>();
        for (int i=0; i<importServices.length; i++) {
            if(!objects.contains(importServices[i])){
                objects.add(importServices[i]);
            }
        }

        List<CustomPropertyModelMapping> list = modelServiceFoundation.getCustomPropertiesWithType("SESECD_1.0.0_emcSituation", DbColumnType.OBJECT);
        if(null != list && !list.isEmpty()){
            for(CustomPropertyModelMapping mapping : list){
                if(null != mapping.getAssociatedProperty() && null != mapping.getAssociatedProperty().getModel()){
                    String moduleArtifact = mapping.getAssociatedProperty().getModel().getEntity().getModule().getArtifact();
                    String serviceName = "com.supcon.orchid." + moduleArtifact + ".services.";
                    if("1.0".equals(mapping.getAssociatedProperty().getModel().getEcVersion())){
                        serviceName += mapping.getAssociatedProperty().getModel().getModelName() + "Service";
                    } else {
                        serviceName += mapping.getAssociatedProperty().getModel().getJpaName() + "Service";
                    }
                    if(!objects.contains(serviceName)){
                        objects.add(serviceName);
                    }
                }
            }
        }

        List<Model> models = modelServiceFoundation.getModels("SESECD_1.0.0_emcSituation");
        for(Model m : models){
            //if(!m.getCode().equals(mainModel.getCode())){
                List<Property> props = modelServiceFoundation.findProperties(m);
                for(Property p : props){
                    if(!p.getIsInherent() && !p.getIsCustom() && p.getType().toString().equals("OBJECT")){
                        Property associatedProperty = modelServiceFoundation.findAssociatedProperty(p.getCode());
                        Model associatedModel = modelServiceFoundation.findModelFromProperty(associatedProperty.getCode());
                        String moduleName = associatedModel.getModuleCode();
                        if(associatedModel.getModuleCode().indexOf("_") != -1){
                            moduleName = associatedModel.getModuleCode().substring(0,associatedModel.getModuleCode().indexOf("_"));
                        }
                        String serviceStr = null;
                        if(moduleName.equals("sysbase")){
                            serviceStr = "com.supcon.orchid.foundation.services." + associatedModel.getModelName() + "Service";
                        }else{
                            serviceStr = "com.supcon.orchid."+ moduleName + ".services." + com.supcon.orchid.utils.StringUtils.firstLetterToUpper(moduleName) + associatedModel.getModelName() + "Service";
                        }
                        if(!objects.contains(serviceStr)){
                            objects.add(serviceStr);
                        }
                    }else if(p.getIsCustom() && p.getType().toString().equals("OBJECT")){
                        Model model = modelServiceFoundation.findModelFromProperty(p.getCode());
                        List<CustomPropertyModelMapping> modelMappings = viewServiceFoundation.findCustomPropertyForAsso(model.getCode(), p.getCode());
                        if(modelMappings != null && modelMappings.size()>0){
                            Property associatedProperty = viewServiceFoundation.getAssociatedPropertyByCustomPropertyModelMapping(modelMappings.get(0).getId());
                            Model associatedModel = modelServiceFoundation.findModelFromProperty(associatedProperty.getCode());
                            String moduleName = associatedModel.getModuleCode();
                            if(moduleName.indexOf("_") != -1){
                                moduleName = moduleName.substring(0,moduleName.indexOf("_"));
                            }
                            String serviceStr = null;
                            if(moduleName.equals("sysbase")){
                                serviceStr = "com.supcon.orchid.foundation.services." + associatedModel.getModelName() + "Service";
                            }else{
                                serviceStr = "com.supcon.orchid."+ moduleName + ".services." + com.supcon.orchid.utils.StringUtils.firstLetterToUpper(moduleName) + associatedModel.getModelName() + "Service";
                            }
                            if(!objects.contains(serviceStr)){
                                objects.add(serviceStr);
                            }
                        }
                    }
                }
            //}
        }
        Iterator<Object> it = objects.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            if(obj == null){
                it.remove();
            }
        }
        Object[] newService = new Object[objects.size()];
        objects.toArray(newService);

        File dataFile = new File(filePath);
        long startTime = System.currentTimeMillis();
        String xlsResult = "";
        try {
            if(dataFile != null && datagridCode != null && datagridCode.length() > 0){
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                String time = sdFormat.format(new Date());
                String name = request.getParameter("dataFileFileName");
                if(name == null || name == ""){
                    name = "default";
                }
                SignatureLog signatureLog=(SignatureLog)request.getAttribute("signatureLog");
                if (getSTATE() == 0) {
                    synchronized (this) {
                        changeState(0);
                        try {
                            xlsResult = importService.imports(tid, dataFile, datagridCode, time, name, isReplace, isIgnore, "xls", signatureLog,newService);
                        } finally {
                            changeState(1);
                        }
                    }
                } else {
                    xlsResult="用户"+IMPORT_USER+"正在导入，请稍后再试";
                }
            }
        } catch (Exception e) {
            xlsResult = e.getMessage();
            log.error(e.getMessage(), e);
            return xlsResult;
        }
        long endTime = System.currentTimeMillis();
        log.info("action upload cast {} ms", (endTime - startTime));
        return xlsResult;
    }

	/**URLEncoder
	 * 下载导入模板
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = {"/SESECD/emcSituation/emcSituation/downloadXls","/SESECD/emcSituation/emcSituation/proj/downloadXls"})
	@ResponseBody
	public String downloadXls(String datagridCode, HttpServletResponse response) {
		try{
		    response.setContentType("application/binary;charset=UTF-8");
        	ServletOutputStream out = response.getOutputStream();
        	Model model = modelServiceFoundation.getModel("SESECD_1.0.0_emcSituation_EmcSituation");
        	exportService.exportExcel(out,response, model, null, null, datagridCode);
        	response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_emcSituation_EmcSituation.xls", "UTF-8"));
        	emcSituationService.excelExport();

		}catch (Exception e){
         	response.setHeader("content-type", "application/json");
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(500);
            log.error("100117002:文件下载失败:",e);
         	return e.getMessage();
        }


		return "SUCCESS";
	}

    @GetMapping(value = "/SESECD/emcSituation/emcSituation/networkPrint", produces = "application/json")
	@ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.PRINT,
                      modelCodes = {"SESECD_1.0.0_emcSituation_EmcSituation"},
                      mainModelCode = "SESECD_1.0.0_emcSituation_EmcSituation",
                      mainModelName = "SESECD.emcSituation.EmcSituation",
                      mainEntityCode = "SESECD_1.0.0_emcSituation",
                      mainEntityName = "SESECD.entityname.randon1577176777829",
                      isLazy = true
    )
	public String print(String printerName,String templateCode,Long id,Integer count,String modelCode,HttpServletRequest request){
		SESECDEmcSituation emcSituation=emcSituationService.getEmcSituation(id);
		log.info("网络打印-接收打印请求，参数：[templateCode=" + templateCode + ",id=" +
				id + ",modelCode=" + modelCode + ",printerName=" + printerName + ",count=" + count);
		long start = System.currentTimeMillis();
		boolean isSuccess = true;
		String result =printClientService.checkQueryParam(templateCode, id.toString(), modelCode, printerName, count.toString());
		if("SUCCESS".equals(result)) {
			String checkResult =printClientService.checkPrinter(printerName);
			if (!"SUCCESS".equals(checkResult)) {
				isSuccess = false;
				result =  checkResult;
			}
		} else {
			isSuccess = false;
		}
		if (isSuccess) {
			Map requestMap=request.getParameterMap();
			Map<String,String> paramMap=new HashMap<String, String>();
			for(Object key:requestMap.keySet()){
				if(!key.equals("templateCode")&&!key.equals("id")&&!key.equals("modelCode")&&!key.equals("printerName")&&!key.equals("count")){
					paramMap.put(key.toString(), ((String[])requestMap.get(key))[0]);
				}
			}
			try {
				result = printClientService.doPrintInfo(emcSituation,templateCode, Long.valueOf(id), modelCode, printerName, Integer.valueOf(count),paramMap);
				if (!"SUCCESS".equals(result)) {
					isSuccess = false;
				}
			} catch (Exception e) {
				log.error("网络打印-打印失败，原因", e.getMessage(), e);
				isSuccess = false;
				result = e.getMessage();
			}
		}
		log.info("网络打印-打印请求结束，总用时：" + (System.currentTimeMillis() - start));

		return result;
	}
	private Map<String, Object> printXmlInfo = new HashMap<String, Object>();
    @Autowired
    private PrintService printService;
    @GetMapping(value = "/SESECD/emcSituation/emcSituation/printOnServer", produces = "application/json")
    @ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.PRINT,
                      modelCodes = {"SESECD_1.0.0_emcSituation_EmcSituation"},
                      mainModelCode = "SESECD_1.0.0_emcSituation_EmcSituation",
                      mainModelName = "SESECD.emcSituation.EmcSituation",
                      mainEntityCode = "SESECD_1.0.0_emcSituation",
                      mainEntityName = "SESECD.entityname.randon1577176777829",
                      isLazy = true
    )
    public Map<String, Object> printOnServer(Long id, String templateCode, String viewCode, HttpServletRequest request) throws Exception {
    	SESECDEmcSituation emcSituation = null;
    	if(null != id) {
    		emcSituation = emcSituationService.getEmcSituation(id);
    	}
    //		String templateCode = request.getParameter("templateCode");
    	SignatureLog signatureLog = (SignatureLog)request.getAttribute("signatureLog");
    	String pdfFileName=printService.printOnServer(emcSituation,templateCode,viewCode, null,signatureLog);
    	printXmlInfo.put("pdfFileName", pdfFileName);
    	return printXmlInfo;
    }
    @PostMapping(value = "/SESECD/emcSituation/emcSituation/batchPrintOnServer", produces = "application/json")
    @ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.BATCH_PRINT,
                      modelCodes = {"SESECD_1.0.0_emcSituation_EmcSituation"},
                      mainModelCode = "SESECD_1.0.0_emcSituation_EmcSituation",
                      mainModelName = "SESECD.emcSituation.EmcSituation",
                      mainEntityCode = "SESECD_1.0.0_emcSituation",
                      mainEntityName = "SESECD.entityname.randon1577176777829",
                      isLazy = true
    )
    public Map<String, Object> batchPrintOnServer(@RequestParam(value = "ids") String ids,
                                                  @RequestParam(value = "tableInfoIds", required = false) String tableInfoIds,
                                                  @RequestParam(value = "viewCode", required = false) String viewCode,
                                                  @RequestParam(value = "templateCode", required = false) String templateCode,
                                                  HttpServletRequest request) throws Exception {
        List<Object> objs = new ArrayList<Object>();
        if (ids != null && ids.length() > 0) {
            String[] arrId = ids.split(",");
            for (int i = 0; i < arrId.length; i++) {
                objs.add(emcSituationService.getEmcSituation(Long.parseLong(arrId[i])));
            }
        }
        List<Long> tableInfoId = null;
        if(tableInfoIds !=null && tableInfoIds.length()>0){
	        tableInfoId = new ArrayList<>();
            String[] arrTableInfoId = tableInfoIds.split(",");
            for (int i = 0; i < arrTableInfoId.length; i++) {
                tableInfoId.add(Long.parseLong(arrTableInfoId[i]));
            }
        }
        //		String templateCode = request.getParameter("templateCode");
        //		SignatureLog signatureLog = (SignatureLog)request.getAttribute("signatureLog");
        SignatureLog signatureLog = (SignatureLog) request
                .getAttribute("signatureLog");
        String pdfFileName = printService.batchPrintOnServer(objs,
                templateCode, viewCode, null, signatureLog);
        printXmlInfo.put("pdfFileName", pdfFileName);
        return printXmlInfo;
    }
    
    protected void saveLastAssignUser(WorkFlowVar workFlowVar) {
		if (workFlowVar != null && workFlowVar.getOutcomeMap() != null && workFlowVar.getOutcomeMap().size() > 0) {
			for (Map<String, ?> map : workFlowVar.getOutcomeMap()) {
				String outcome = (map.get("outcome") != null) ? map.get("outcome").toString() : "";
				String assignUser = (map.get("assignUser") != null) ? map.get("assignUser").toString() : "";

				if (assignUser != null && !assignUser.isEmpty()) {

					List<ICookie> cookies = cookieService.findCookieByCriteria(Restrictions.eq("user", getCurrentUser()),
							Restrictions.eq("type", "DEFAULT_SELECTSTAFF_" + outcome));
					Cookie cookie = null;
					if (cookies == null || cookies.isEmpty()) {
						cookie = new Cookie();
						cookie.setType("DEFAULT_SELECTSTAFF_" + outcome);
						cookie.setUser(getCurrentUser());
					} else {
						cookie = (Cookie) cookies.get(0);
					}
					cookie.setValue(assignUser);
					cookieService.save(cookie);

				}
			}
		}
	}


    /**
    * 批量处理单据
    * @param processId
    * @param activityName
    * @param pendingIds
    * @param viewCode
    * @throws Exception
    */


    private String bapBeforeReturnHtmlMethod(String viewCode, String methodName, String clientType) {
        String viewName = null;
    /* CUSTOM CODE START(controller,beforeReturnHtmlMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
        return viewName;
    }

	private void bapBeforeCustomMethod(String code, String methodName, QueryEntity queryEntity) throws Exception {
	/* CUSTOM CODE START(controller,beforeMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	private void bapAfterCustomMethod(String code, String methodNam, Page page) throws Exception {
	/* CUSTOM CODE START(controller,afterMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	private void bapGetDataAfterCustomMethod(String methodName, SESECDEmcSituation result, String includes) throws Exception {
    /* CUSTOM CODE START(controller,getDataAfterMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
    private void bapBeforeRecallMethod(Long tableinfoId) throws Exception {
	/* CUSTOM CODE START(controller,beforeRecallMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	private void bapAfterRecallMethod(Long tableinfoId) throws Exception {
    /* CUSTOM CODE START(controller,afterRecallMethods,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
    private void afterSubmitAtNewTransactionCustomMethod(String viewCode, String methodName, SESECDEmcSituation result) throws Exception {
    /* CUSTOM CODE START(controller,afterSubmitAtNewTransaction,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
	/* CUSTOM CODE START(controller,functions,SESECD_1.0.0_emcSituation_EmcSituation,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}