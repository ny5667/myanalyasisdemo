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
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecordEditEntity;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService;
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
/* CUSTOM CODE START(controller,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ClassSystemConfigAnno
@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Api
public class SESECDAlmAlarmRecordController extends BaseController<SESECDAlmAlarmRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * 默认的导出工作流处理信息个数
	 */
	private static final Integer dealDefaulCount = 50;
	private static final Integer findExportDataInfosCount = 1;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private SESECDAlmAlarmRecordService almAlarmRecordService;

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
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmHandleList-query", produces = "application/json")
	@ResponseBody
	public String alarmHandleListListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
		bapBeforeCustomMethod(viewCode, "alarmHandleListListQuery", queryEntity);
		int currentSqlType = Sql.TYPE_LIST_QUERY;
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
				 queryEntity.getMainBusinessId(),
				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
			bapAfterCustomMethod(viewCode, "alarmHandleListListQuery", page);
		}
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.eventNo,result.accidentName,result.eventDataSource.id,result.eventDataSource.value,result.description,result.emEventType.emEventType,result.alarmPerson.name,result.happenTime,result.emEventLeveL.emEventLevel,result.receiver.name,result.rectime,result.processState.id,result.processState.value,result.locationIncident.name,result.alarmLevel.id,result.alarmLevel.value,result.alarmPhone,result.process,result.twoEPInfo,result.twoCompany.name,result.threeEPInfo,result.threeCompany.name,result.position,result.emEventType.id,result.alarmPerson.id,result.emEventLeveL.id,result.receiver.id,result.locationIncident.id,result.twoCompany.id,result.threeCompany.id,result.pending.processDescription,result.pending.processId,result.pending.deploymentId,result.pending.bulkDealFlag,result.pending.activityType,result.pending.activityName,result.pending.processKey,result.pending.processVersion,result.accidentType.attrMap.*,result.alarmPerson.attrMap.*,result.alertRecordId.attrMap.*,result.createStaff.attrMap.*,result.deleteStaff.attrMap.*,result.drillPlanId.attrMap.*,result.effectStaff.attrMap.*,result.emEventLeveL.attrMap.*,result.emEventType.attrMap.*,result.enclosureAttachementInfo,result.enclosureDocument.id,result.hpmDepartment.attrMap.*,result.hpnCompany.attrMap.*,result.locationIncident.attrMap.*,result.mesPerson.attrMap.*,result.modifyStaff.attrMap.*,result.objparama.attrMap.*,result.objparamb.attrMap.*,result.ownerDepartment.attrMap.*,result.ownerPosition.attrMap.*,result.ownerStaff.attrMap.*,result.receiver.attrMap.*,result.threeCompany.attrMap.*,result.twoCompany.attrMap.*,result.voiceConfigId.attrMap.*,result.*.attrMap.*";
		String excludes = "*";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		if(queryEntity.isExportFlag()){
			getFullObject(page);
			bapAfterCustomMethod(viewCode, "alarmHandleListListQuery", page);
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
			exportService.exportExcel(out,response, model, page, viewCode);
			almAlarmRecordService.excelExport();
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
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventList-query", produces = "application/json")
	@ResponseBody
	public String alarmEventListListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
		bapBeforeCustomMethod(viewCode, "alarmEventListListQuery", queryEntity);
		int currentSqlType = Sql.TYPE_LIST_QUERY;
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
				 queryEntity.getMainBusinessId(),
				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
			bapAfterCustomMethod(viewCode, "alarmEventListListQuery", page);
		}
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.eventType.id,result.eventType.value,result.accidentName,result.description,result.locationIncident.name,result.happenTime,result.receiver.name,result.hpnCompany.name,result.isOver,result.overTime,result.rectime,result.eventSource.id,result.eventSource.value,result.twoEPInfo,result.twoCompany.name,result.threeEPInfo,result.threeCompany.name,result.position,result.locationIncident.id,result.receiver.id,result.hpnCompany.id,result.twoCompany.id,result.threeCompany.id,result.pending.processDescription,result.pending.processId,result.pending.deploymentId,result.pending.bulkDealFlag,result.pending.activityType,result.pending.activityName,result.pending.processKey,result.pending.processVersion,result.accidentType.attrMap.*,result.alarmPerson.attrMap.*,result.alertRecordId.attrMap.*,result.createStaff.attrMap.*,result.deleteStaff.attrMap.*,result.drillPlanId.attrMap.*,result.effectStaff.attrMap.*,result.emEventLeveL.attrMap.*,result.emEventType.attrMap.*,result.enclosureAttachementInfo,result.enclosureDocument.id,result.hpmDepartment.attrMap.*,result.hpnCompany.attrMap.*,result.locationIncident.attrMap.*,result.mesPerson.attrMap.*,result.modifyStaff.attrMap.*,result.objparama.attrMap.*,result.objparamb.attrMap.*,result.ownerDepartment.attrMap.*,result.ownerPosition.attrMap.*,result.ownerStaff.attrMap.*,result.receiver.attrMap.*,result.threeCompany.attrMap.*,result.twoCompany.attrMap.*,result.voiceConfigId.attrMap.*,result.*.attrMap.*";
		String excludes = "*";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		if(queryEntity.isExportFlag()){
			getFullObject(page);
			bapAfterCustomMethod(viewCode, "alarmEventListListQuery", page);
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
			exportService.exportExcel(out,response, model, page, viewCode);
			almAlarmRecordService.excelExport();
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
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventMapList-query", produces = "application/json")
	@ResponseBody
	public String alarmEventMapListListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
		bapBeforeCustomMethod(viewCode, "alarmEventMapListListQuery", queryEntity);
		int currentSqlType = Sql.TYPE_LIST_QUERY;
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
				 queryEntity.getMainBusinessId(),
				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
			bapAfterCustomMethod(viewCode, "alarmEventMapListListQuery", page);
		}
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.hpnCompany.name,result.accidentName,result.happenTime,result.position,result.isIncident,result.isOver,result.hpnCompany.id,result.pending.processDescription,result.pending.processId,result.pending.deploymentId,result.pending.bulkDealFlag,result.pending.activityType,result.pending.activityName,result.pending.processKey,result.pending.processVersion,result.accidentType.attrMap.*,result.alarmPerson.attrMap.*,result.alertRecordId.attrMap.*,result.createStaff.attrMap.*,result.deleteStaff.attrMap.*,result.drillPlanId.attrMap.*,result.effectStaff.attrMap.*,result.emEventLeveL.attrMap.*,result.emEventType.attrMap.*,result.enclosureAttachementInfo,result.enclosureDocument.id,result.hpmDepartment.attrMap.*,result.hpnCompany.attrMap.*,result.locationIncident.attrMap.*,result.mesPerson.attrMap.*,result.modifyStaff.attrMap.*,result.objparama.attrMap.*,result.objparamb.attrMap.*,result.ownerDepartment.attrMap.*,result.ownerPosition.attrMap.*,result.ownerStaff.attrMap.*,result.receiver.attrMap.*,result.threeCompany.attrMap.*,result.twoCompany.attrMap.*,result.voiceConfigId.attrMap.*,result.*.attrMap.*";
		String excludes = "*";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		if(queryEntity.isExportFlag()){
			getFullObject(page);
			bapAfterCustomMethod(viewCode, "alarmEventMapListListQuery", page);
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
			exportService.exportExcel(out,response, model, page, viewCode);
			almAlarmRecordService.excelExport();
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
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmRecordList-query", produces = "application/json")
	@ResponseBody
	public String alarmRecordListListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
		bapBeforeCustomMethod(viewCode, "alarmRecordListListQuery", queryEntity);
		int currentSqlType = Sql.TYPE_LIST_QUERY;
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
				 queryEntity.getEntityCode(), queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
				 queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
				 queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
				 queryEntity.getMainBusinessId(),
				 queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
				 queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
				 queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
			bapAfterCustomMethod(viewCode, "alarmRecordListListQuery", page);
		}
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.eventNo,result.accidentName,result.eventDataSource.id,result.eventDataSource.value,result.description,result.emEventType.emEventType,result.alarmPerson.name,result.happenTime,result.emEventLeveL.emEventLevel,result.receiver.name,result.rectime,result.processState.id,result.processState.value,result.locationIncident.name,result.eventType.id,result.eventType.value,result.isSimulation,result.drillPlanId.name,result.position,result.alarmLevel.id,result.alarmLevel.value,result.emEventType.id,result.alarmPerson.id,result.emEventLeveL.id,result.receiver.id,result.locationIncident.id,result.drillPlanId.id,result.pending.processDescription,result.pending.processId,result.pending.deploymentId,result.pending.bulkDealFlag,result.pending.activityType,result.pending.activityName,result.pending.processKey,result.pending.processVersion,result.accidentType.attrMap.*,result.alarmPerson.attrMap.*,result.alertRecordId.attrMap.*,result.createStaff.attrMap.*,result.deleteStaff.attrMap.*,result.drillPlanId.attrMap.*,result.effectStaff.attrMap.*,result.emEventLeveL.attrMap.*,result.emEventType.attrMap.*,result.enclosureAttachementInfo,result.enclosureDocument.id,result.hpmDepartment.attrMap.*,result.hpnCompany.attrMap.*,result.locationIncident.attrMap.*,result.mesPerson.attrMap.*,result.modifyStaff.attrMap.*,result.objparama.attrMap.*,result.objparamb.attrMap.*,result.ownerDepartment.attrMap.*,result.ownerPosition.attrMap.*,result.ownerStaff.attrMap.*,result.receiver.attrMap.*,result.threeCompany.attrMap.*,result.twoCompany.attrMap.*,result.voiceConfigId.attrMap.*,result.*.attrMap.*";
		String excludes = "*";
		View view = viewServiceFoundation.getViewForMethodCheckInherit(viewCode);
        if(view != null){
            if(null!=view.getProjFlag() && view.getProjFlag() && null != view.getInheritType() && view.getInheritType()==2){
               includes= viewServiceFoundation.generateProjectViewResultField(viewCode);
            }
        }
		if(queryEntity.isExportFlag()){
			getFullObject(page);
			bapAfterCustomMethod(viewCode, "alarmRecordListListQuery", page);
			response.setContentType("application/binary;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
			exportService.exportExcel(out,response, model, page, viewCode);
			almAlarmRecordService.excelExport();
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
    	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/proj/{viewName}-query", produces = "application/json")
    	@ResponseBody
    	public String projListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
    		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
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
    			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
    			Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
    			exportService.exportExcel(out,response,model, page, viewCode);
    			almAlarmRecordService.excelExport();
    			return null;
    		}
    		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
    	}
	private void getFullObject(Page<SESECDAlmAlarmRecord> page){
		List<SESECDAlmAlarmRecord> almAlarmRecords = page.getResult();
		List<Long> ids = almAlarmRecords.stream().map(SESECDAlmAlarmRecord::getId).collect(Collectors.toList());
        page.setResult(almAlarmRecordService.findSESECDAlmAlarmRecordByIds(ids));
	}





	/**
	 *  参照视图公用数据查询方法
	*/
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventRef-query", produces = "application/json")
	@ResponseBody
	public String alarmEventRefListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
		bapBeforeCustomMethod(viewCode, "alarmEventRefListQuery", queryEntity);
		// 加上currentSqlType，用于区分列表查询和参照查询
		// 参照查询需要传crossCompanyFlag
		// 参照查询不传datagridCode
		// 启用工作流时传processKey和entityCode,不启用工作流不传
		int currentSqlType = Sql.TYPE_LIST_REFERENCE;
		// 实体未启用工作流
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, null, queryEntity.getCrossCompanyFlag(), null,
			null, queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
			queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
			queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
			queryEntity.getMainBusinessId(),
			queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
			queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
			queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		if(!queryEntity.isExportFlag()){
            bapAfterCustomMethod(viewCode, "alarmEventRefListQuery", page);
        }
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.accidentName,result.description,result.locationIncident.name,result.happenTime,result.receiver.name,result.hpnCompany.name,result.isIncident,result.isOver,result.overTime,result.locationIncident.id,result.receiver.id,result.hpnCompany.id,result.enclosureAttachementInfo,result.enclosureDocument.id,result.*.attrMap.*,data.id,data.value";
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
			bapAfterCustomMethod(viewCode, "alarmEventRefListQuery", page);
            response.setContentType("application/binary;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
            Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
            exportService.exportExcel(out, response,model, page, viewCode);
            almAlarmRecordService.excelExport();
            return null;
        }
		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
	}
@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/projref/{viewName}-query", produces = "application/json")
    	@ResponseBody
    	public String projRefListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
    		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, null, queryEntity.getCrossCompanyFlag(), null,
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
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
                Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
                exportService.exportExcel(out,response, model, page, viewCode);
                almAlarmRecordService.excelExport();
                return null;
            }
    		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
    	}

	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/selectData", produces = "application/json")
	@ResponseBody
	public String selectData(String includes) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
		page.setPageSize(page.getMaxPageSize());
		List<SESECDAlmAlarmRecord> result = almAlarmRecordService.findAlmAlarmRecordsByHql("from " + SESECDAlmAlarmRecord.JPA_NAME + " where valid =1");
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
	@PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventRef__mobile__-query", produces = "application/json")
	@ResponseBody
	public String alarmEventRef__mobile__ListQuery(@RequestBody(required = false) QueryEntity queryEntity, HttpServletRequest request) throws Exception {
		Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
		page.setPageSize(queryEntity.getPageSize());
		page.setPageNo(queryEntity.getPageNo());
		page.setPaging(queryEntity.isPaging());
		String viewCode = request.getAttribute("ViewCode").toString();
		bapBeforeCustomMethod(viewCode, "alarmEventRef__mobile__ListQuery", queryEntity);
		// 加上currentSqlType，用于区分列表查询和参照查询
		// 参照查询需要传crossCompanyFlag
		// 参照查询不传datagridCode
		// 启用工作流时传processKey和entityCode,不启用工作流不传
		int currentSqlType = Sql.TYPE_LIST_REFERENCE;
		// 实体未启用工作流
		almAlarmRecordService.listQuery(page, currentSqlType, viewCode, null, queryEntity.getCrossCompanyFlag(), null,
			null, queryEntity.getFastQueryCond(), queryEntity.getAdvQueryCond(),
			queryEntity.getClassifyCodes(), queryEntity.getDataTableSortColKey(),
			queryEntity.getDataTableSortColName(), queryEntity.getDataTableSortColOrder(),
			queryEntity.getMainBusinessId(),
			queryEntity.getSplit(), queryEntity.getSearchObjects(), queryEntity.getTableProcessKey(),
			queryEntity.getPermissionCode(), queryEntity.isFlowBulkFlag(), queryEntity.getNoQueryFlag(),
			queryEntity.getExportSql(), findExportDataInfosCount, queryEntity.getDynamicFieldsMap(),queryEntity);
		bapAfterCustomMethod(viewCode, "alarmEventRef__mobile__ListQuery", page);
		String includes = "first,hasNext,hasPre,nextPage,pageSize,pageNo,prePage,totalCount,totalPages,treeToSurfaceMode,result.pending.id,result.pending.taskDescription,result.pending.openUrl,result.pending.userId,result.tableInfoId,result.id,result.version,result.valid,result.cid,result.cidName,result.layRec,result.status,result.tableNo,result.createStaff.name,result.createTime,result.accidentName,result.accidentClass.id,result.accidentClass.value,result.accidentLevel.id,result.accidentLevel.value,result.description,result.effectTime,result.*.attrMap.*,data.id,data.value";
		return JSONPlainSerializer.serializeAsJSON(page, includes, new BAPEntityTransformer());
	}


	/**
	 * 获取编辑视图的数据，向对象的pending加入taskDescription,防止无待办时缺少单据状态信息
	 *
	 * @param id
	 * @param pendingId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = {"/SESECD/alarmRecord/almAlarmRecord/data/{id}",
		"/SESECD/alarmRecord/almAlarmRecord/proj/data/{id}"}, produces = "application/json")
	@ResponseBody
	public Object getData(@PathVariable long id, Long pendingId, String includes, HttpServletRequest request) throws Exception {
		SESECDAlmAlarmRecord result = almAlarmRecordService.getAlmAlarmRecord(id,pendingService.getViewCodeByPending(pendingId));
		// 返回图片类型字段信息
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
		"/SESECD/alarmRecord/almAlarmRecord/cctvRecordList/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmEventView/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit__mobile__/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordView/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmCheck/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmTurnEdit/save",
		"/SESECD/alarmRecord/almAlarmRecord/messageSend/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmHandleView/save",
		"/SESECD/alarmRecord/almAlarmRecord/alarmHandleEdit/save",
		"/SESECD/alarmRecord/almAlarmRecord/eventEditMobile/save",
		"/SESECD/alarmRecord/almAlarmRecord/cctvRecordList/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmEventView/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit__mobile__/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordView/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmCheck/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmTurnEdit/submit",
		"/SESECD/alarmRecord/almAlarmRecord/messageSend/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmHandleView/submit",
		"/SESECD/alarmRecord/almAlarmRecord/alarmHandleEdit/submit",
		"/SESECD/alarmRecord/almAlarmRecord/eventEditMobile/submit",
		}, produces = "application/json")
	@ResponseBody
	public Object submit(Long id, HttpServletRequest request) throws Exception {
		SESECDAlmAlarmRecordEditEntity editEntity;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		bapBeforeCustomMethod("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", "submit", null);
		if (null != id) {
			SESECDAlmAlarmRecord ordinaryAlmAlarmRecord = almAlarmRecordService.getAlmAlarmRecordWithoutMultiselect(id);
			// 前端传的json
			//byte[] bytes = (byte[]) request.getAttribute("bytes");
			String body =  request.getAttribute("body").toString();
			Map<String,Object> editEntityMap = objectMapper.readValue(body, Map.class);
			Map<String,Object> almAlarmRecordMap = (Map<String, Object>) editEntityMap.get("almAlarmRecord");
			editEntity = objectMapper.readValue(body, SESECDAlmAlarmRecordEditEntity.class);
			SESECDAlmAlarmRecord almAlarmRecord = editEntity.getAlmAlarmRecord();
			// 只更新前端传的，其他按数据库里原有的数据
			for (Field field : ReflectUtils.getDeepDeclaredFields(SESECDAlmAlarmRecord.class)) {
				// 前端没传，继续遍历下一个字段
				if (null == almAlarmRecordMap.get(field.getName())) {
					continue;
				}
				field.setAccessible(true);
				// 实际更新
				field.set(ordinaryAlmAlarmRecord, field.get(almAlarmRecord));
			}
			editEntity.setAlmAlarmRecord(ordinaryAlmAlarmRecord);
		} else {
			editEntity = objectMapper.readValue(request.getAttribute("body").toString(), SESECDAlmAlarmRecordEditEntity.class);
		}
		SESECDAlmAlarmRecord almAlarmRecord = editEntity.getAlmAlarmRecord();
		String operateType = editEntity.getOperateType();
		Map<String, Object> responseMap = null;
        boolean isSuccess = true;
        String exceptionDescription = null;
        try {
            responseMap = almAlarmRecordService.submit(almAlarmRecord, editEntity.getWorkFlowVar(),
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
            boolean modelNeedAudit = ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", AuditLogType.BUSINESS);
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
                auditBusinessLogBO.setMainModelCode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
                auditBusinessLogBO.setMainModelName("SESECD.alarmRecord.AlmAlarmRecord");
                auditBusinessLogBO.setMainEntityCode("SESECD_1.0.0_alarmRecord");
                auditBusinessLogBO.setMainEntityName("SESECD.entityname.randon1576460940310");
                auditBusinessLogStrategy.publishAuditLogLazy(auditBusinessLogBO);
            }
        }
        afterSubmitAtNewTransactionCustomMethod(editEntity.getViewCode(), "submit", almAlarmRecord);
        saveLastAssignUser(editEntity.getWorkFlowVar());
		Map<String, Object> fileuploads = (Map<String, Object>) request.getAttribute("__orchid_fileuploads__");
		if (null == fileuploads) {
			fileuploads = new HashMap<String, Object>();
		}
		fileuploads.put("linkId", almAlarmRecord.getId());
		request.setAttribute("__orchid_fileuploads__", fileuploads);
		bapAfterCustomMethod("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", "submit", null);
		return responseMap;
	}

/**
    	 * 视图保存/提交
    	 * @param id
    	 * @return
    	 * @throws Exception
    	 */
    	@PostMapping(value = {
    		"/SESECD/alarmRecord/almAlarmRecord/proj/{viewName}/save",
    		"/SESECD/alarmRecord/almAlarmRecord/proj/{viewName}/submit"
    		}, produces = "application/json")
    	@ResponseBody
    	public Object projsubmit(Long id, HttpServletRequest request) throws Exception {
    		SESECDAlmAlarmRecordEditEntity editEntity;
    		ObjectMapper objectMapper = new ObjectMapper();
    		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
    		objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    		bapBeforeCustomMethod("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", "submit", null);
    		if (null != id) {
    			SESECDAlmAlarmRecord ordinaryAlmAlarmRecord = almAlarmRecordService.getAlmAlarmRecordWithoutMultiselect(id);
    			// 前端传的json
    			//byte[] bytes = (byte[]) request.getAttribute("bytes");
    			String body =  request.getAttribute("body").toString();
    			Map<String,Object> editEntityMap = objectMapper.readValue(body, Map.class);
    			Map<String,Object> almAlarmRecordMap = (Map<String, Object>) editEntityMap.get("almAlarmRecord");
    			editEntity = objectMapper.readValue(body, SESECDAlmAlarmRecordEditEntity.class);
    			SESECDAlmAlarmRecord almAlarmRecord = editEntity.getAlmAlarmRecord();
    			// 只更新前端传的，其他按数据库里原有的数据
    			for (Field field : ReflectUtils.getDeepDeclaredFields(SESECDAlmAlarmRecord.class)) {
    				// 前端没传，继续遍历下一个字段
    				if (null == almAlarmRecordMap.get(field.getName())) {
    					continue;
    				}
    				field.setAccessible(true);
    				// 实际更新
    				field.set(ordinaryAlmAlarmRecord, field.get(almAlarmRecord));
    			}
    			editEntity.setAlmAlarmRecord(ordinaryAlmAlarmRecord);
    		} else {
    			editEntity = objectMapper.readValue(request.getAttribute("body").toString(), SESECDAlmAlarmRecordEditEntity.class);
    		}
    		SESECDAlmAlarmRecord almAlarmRecord = editEntity.getAlmAlarmRecord();
    		String operateType = editEntity.getOperateType();
    		Map<String, Object> responseMap = null;
            boolean isSuccess = true;
            String exceptionDescription = null;
            try {
                responseMap = almAlarmRecordService.submit(almAlarmRecord, editEntity.getWorkFlowVar(),
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
                boolean modelNeedAudit = ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", AuditLogType.BUSINESS);
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
                    auditBusinessLogBO.setMainModelCode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
                    auditBusinessLogBO.setMainModelName("SESECD.alarmRecord.AlmAlarmRecord");
                    auditBusinessLogBO.setMainEntityCode("SESECD_1.0.0_alarmRecord");
                    auditBusinessLogBO.setMainEntityName("SESECD.entityname.randon1576460940310");
                    auditBusinessLogStrategy.publishAuditLogLazy(auditBusinessLogBO);
                }
            }
            afterSubmitAtNewTransactionCustomMethod(editEntity.getViewCode(), "submit", almAlarmRecord);
            saveLastAssignUser(editEntity.getWorkFlowVar());
    		Map<String, Object> fileuploads = (Map<String, Object>) request.getAttribute("__orchid_fileuploads__");
    		if (null == fileuploads) {
    			fileuploads = new HashMap<String, Object>();
    		}
    		fileuploads.put("linkId", almAlarmRecord.getId());
    		request.setAttribute("__orchid_fileuploads__", fileuploads);
    		bapAfterCustomMethod("SESECD_1.0.0_alarmRecord_AlmAlarmRecord", "submit", null);
    		return responseMap;
    	}

	/**
	 * 判断进入submit接口的操作类型
	 */
	private OperateType getSubmitOperateType(Long id, SESECDAlmAlarmRecordEditEntity editEntity) {
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
	 * 打开页面cctvRecordList
	 * url:/SESECD/alarmRecord/almAlarmRecord/cctvRecordList
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/cctvRecordList")
	public String cctvRecordListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_cctvRecordList", "cctvRecordListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_cctvRecordList")){
			return "cctvRecordList-mobile.html";
		}
		return "cctvRecordList.html";
	}
	/**
	 * 打开页面alarmHandleList
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmHandleList
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmHandleList")
	public String alarmHandleListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmHandleList", "alarmHandleListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmHandleList")){
			return "alarmHandleList-mobile.html";
		}
		return "alarmHandleList.html";
	}
	/**
	 * 打开页面alarmEventView
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmEventView
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventView")
	public String alarmEventViewView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmEventView", "alarmEventViewView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmEventView")){
			return "alarmEventView-mobile.html";
		}
		return "alarmEventView.html";
	}
	/**
	 * 打开页面alarmEventList
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmEventList
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventList")
	public String alarmEventListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmEventList", "alarmEventListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmEventList")){
			return "alarmEventList-mobile.html";
		}
		return "alarmEventList.html";
	}
	/**
	 * 打开页面alarmEventRef
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmEventRef
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventRef")
	public String alarmEventRefView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmEventRef", "alarmEventRefView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmEventRef")){
			return "alarmEventRef-mobile.html";
		}
		return "alarmEventRef.html";
	}
	/**
	 * 打开页面alarmEventMapList
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmEventMapList
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmEventMapList")
	public String alarmEventMapListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmEventMapList", "alarmEventMapListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmEventMapList")){
			return "alarmEventMapList-mobile.html";
		}
		return "alarmEventMapList.html";
	}
	/**
	 * 打开页面alarmRecordView
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmRecordView
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmRecordView")
	public String alarmRecordViewView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmRecordView", "alarmRecordViewView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmRecordView")){
			return "alarmRecordView-mobile.html";
		}
		return "alarmRecordView.html";
	}
	/**
	 * 打开页面alarmRecordEdit
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmRecordEdit")
	public String alarmRecordEditView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmRecordEdit", "alarmRecordEditView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmRecordEdit")){
			return "alarmRecordEdit-mobile.html";
		}
		return "alarmRecordEdit.html";
	}
	/**
	 * 打开页面alarmCheck
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmCheck
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmCheck")
	public String alarmCheckView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmCheck", "alarmCheckView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmCheck")){
			return "alarmCheck-mobile.html";
		}
		return "alarmCheck.html";
	}
	/**
	 * 打开页面alarmTurnEdit
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmTurnEdit
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmTurnEdit")
	public String alarmTurnEditView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmTurnEdit", "alarmTurnEditView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmTurnEdit")){
			return "alarmTurnEdit-mobile.html";
		}
		return "alarmTurnEdit.html";
	}
	/**
	 * 打开页面messageSend
	 * url:/SESECD/alarmRecord/almAlarmRecord/messageSend
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/messageSend")
	public String messageSendView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_messageSend", "messageSendView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_messageSend")){
			return "messageSend-mobile.html";
		}
		return "messageSend.html";
	}
	/**
	 * 打开页面alarmRecordList
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmRecordList
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmRecordList")
	public String alarmRecordListView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmRecordList", "alarmRecordListView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmRecordList")){
			return "alarmRecordList-mobile.html";
		}
		return "alarmRecordList.html";
	}
	/**
	 * 打开页面alarmHandleView
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmHandleView
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmHandleView")
	public String alarmHandleViewView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmHandleView", "alarmHandleViewView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmHandleView")){
			return "alarmHandleView-mobile.html";
		}
		return "alarmHandleView.html";
	}
	/**
	 * 打开页面alarmHandleEdit
	 * url:/SESECD/alarmRecord/almAlarmRecord/alarmHandleEdit
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/alarmHandleEdit")
	public String alarmHandleEditView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_alarmHandleEdit", "alarmHandleEditView", clientType);
        if (viewName != null) {
            return viewName;
        }
		if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_alarmHandleEdit")){
			return "alarmHandleEdit-mobile.html";
		}
		return "alarmHandleEdit.html";
	}
    /**
	 * 打开页面
	 * url:
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/proj/{viewName}")
	public String projView(@RequestParam(value = "clientType",required = false) String clientType,@PathVariable String viewName,HttpServletRequest request) throws Exception {
		String projViewRelevantPath = "/" + RpcContext.getContext().getTenantId() + request.getRequestURI() + "/../../"+viewName+"-proj.html";
		String url = request.getRequestURI();
		String tenantId = RpcContext.getContext().getTenantId();
		if (viewServiceFoundation.checkProjectHtml(url, tenantId)) {
			return projViewRelevantPath;
		}
		almAlarmRecordService.generateHtmlFile(request.getRequestURI(), projViewRelevantPath);
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
	 * 打开页面eventEditMobile
	 * url:/SESECD/alarmRecord/almAlarmRecord/eventEditMobile
	 * @return
	 */
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/eventEditMobile")
	public String eventEditMobileView(@RequestParam(value = "clientType",required = false) String clientType) {
	    String viewName = bapBeforeReturnHtmlMethod("SESECD_1.0.0_alarmRecord_eventEditMobile", "eventEditMobileView", clientType);
        if (viewName != null) {
            return viewName;
        }
	    if("mobile".equals(clientType)&&hasMobileView("SESECD_1.0.0_alarmRecord_eventEditMobile")){
    			return "eventEditMobile__mobile__/index.html";
    		}
		return "eventEditMobile/index.html";
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
	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/editStates", produces = "application/json")
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
			Map<String, Long> createInfoMap = almAlarmRecordService.getCreateInfoMap(id);
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
	@PostMapping("/SESECD/alarmRecord/almAlarmRecord/delete")
	@ResponseBody
	@AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.DELETE,
                      modelCodes = {"SESECD_1.0.0_alarmRecord_AlmAlarmRecord"},
                      mainModelCode = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord",
                      mainModelName = "SESECD.alarmRecord.AlmAlarmRecord",
                      mainEntityCode = "SESECD_1.0.0_alarmRecord",
                      mainEntityName = "SESECD.entityname.randon1576460940310",
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
		almAlarmRecordService.deleteAlmAlarmRecord(ids, signatureLog);
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
	    "/SESECD/alarmRecord/almAlarmRecord/data-{dg.name}",
		"/SESECD/alarmRecord/almAlarmRecord/proj/data-{dg.name}"
	},  produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String dataGridData(Long id, Long refId, String datagridCode, @RequestBody(required = false)QueryEntity queryEntity,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
			if (queryEntity == null) {
			    queryEntity = new QueryEntity();
			}
			DataGrid dg = viewServiceFoundation.getDataGrid(datagridCode);
            		if(dg !=null && dg.getDataGridType()==1){
                        Page<SESECDAlmAlarmRecord> page = new Page<SESECDAlmAlarmRecord>();
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
                        almAlarmRecordService.listQuery(page, currentSqlType, viewCode, datagridCode, null, queryEntity.getProcessKey(),
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
                             response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
                             Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
                             exportService.exportExcel(out, response,model, page, null);
                             almAlarmRecordService.excelExport();
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
                        almAlarmRecordService.dataGridData(dgPage, datagridCode, dataGridName, request.getParameterMap(), refId,id,queryEntity);
						bapAfterCustomMethod(datagridCode, "dataGridData" + dg.getName(), dgPage);
						String includes = viewServiceFoundation.generateProjectDataGridResultField(datagridCode);
                        log.info("includes=" + includes);
                        if(queryEntity.isExportFlag()){
                            response.setContentType("application/binary;charset=UTF-8");
                            ServletOutputStream out = response.getOutputStream();
                            Model dgTargetModel = viewServiceFoundation.getTargetModelFromDg(dg.getCode());
                            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(dgTargetModel.getCode() + ".xls", "UTF-8"));
                            exportService.exportExcel(out, response, dgTargetModel, dgPage, null, datagridCode);
                            almAlarmRecordService.excelExport();
                            return null;
                        }
						// 可处理国际化
                        return JSONPlainSerializer.serializeAsJSON(dgPage, includes, new BAPEntityTransformer());
            	}
	}



	@GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/echartsData", produces = "application/json")
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
	@GetMapping(value = {"/SESECD/alarmRecord/almAlarmRecord/importMainXls","/SESECD/alarmRecord/almAlarmRecord/proj/importMainXls"}, produces = "application/json")
	@ResponseBody
	public Object importMainXls(String filePath, String viewCode, boolean isReplace, boolean isIgnore, HttpServletRequest request) {
		Object[] importServices = new Object[]{"com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService"
						, "com.supcon.orchid.SESECD.services.SESECDAlertRecordService"
						, "com.supcon.orchid.SESECD.services.SESECDEmEventLeveLService"
						, "com.supcon.orchid.SESECD.services.SESECDEmEventTypeService"
						, "com.supcon.orchid.SESECD.services.SESECDVoiceConfigService"
						, "com.supcon.orchid.SESED.services.SESEDPlanService"
						, "com.supcon.orchid.SESGISConfig.services.SESGISConfigFrequentplaceService"
						, "com.supcon.orchid.SESWssER.services.SESWssERAccidentClassService"
						, "com.supcon.orchid.foundation.services.CompanyService"
						, "com.supcon.orchid.foundation.services.DepartmentService"
						, "com.supcon.orchid.foundation.services.PositionService"
						, "com.supcon.orchid.foundation.services.StaffService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
						, "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
						, "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
						, "com.supcon.orchid.SESWssEP.services.SESWssEPEmergencyPlanService"
						, "com.supcon.orchid.foundation.services.CompanyService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
						, "com.supcon.orchid.SESEAB.services.SESEABEabSetionService"
						, "com.supcon.orchid.SESEAB.services.SESEABSetionmemberService"
						, "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
						, "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
						, "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
						, "com.supcon.orchid.SESWssEP.services.SESWssEPEmcPlanActionService"
						, "com.supcon.orchid.SESECD.services.SESECDAccidentService"
						, "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
						, "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
						, "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
						, "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
						, "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
						, "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
						, "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
						, "com.supcon.orchid.SESECD.services.SESECDAccidentService"
						, "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
						, "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
						, "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
						, "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
						, "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
						, "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
						, "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
						, "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
		};
		//过滤重复的Service
		List<Object> objects = new ArrayList<Object>();
		for (int i=0; i<importServices.length; i++) {
			if(!objects.contains(importServices[i])){
				objects.add(importServices[i]);
			}
		}

		List<CustomPropertyModelMapping> list = modelServiceFoundation.getCustomPropertiesWithType("SESECD_1.0.0_alarmRecord", DbColumnType.OBJECT);
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
		List<Model> models = modelServiceFoundation.getModels("SESECD_1.0.0_alarmRecord");
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
				            xlsResult = importService.batchImports(viewCode, "SESECD_1.0.0_alarmRecord_AlmAlarmRecord", dataFile, time, name, isReplace, isIgnore, true, "xls", signatureLog, newService);
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

    @GetMapping(value = {"/SESECD/alarmRecord/almAlarmRecord/importXls","/SESECD/alarmRecord/almAlarmRecord/proj/importXls"}, produces = "application/json")
	@ResponseBody
    public String imports(String filePath, String datagridCode, String tid, boolean isReplace, boolean isIgnore, HttpServletRequest request) throws Exception {
        Object[] importServices = new Object[]{"com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService"
                        , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                        , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                        , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                        , "com.supcon.orchid.SESWssEP.services.SESWssEPEmergencyPlanService"
                        , "com.supcon.orchid.foundation.services.CompanyService"
                        , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                        , "com.supcon.orchid.SESEAB.services.SESEABEabSetionService"
                        , "com.supcon.orchid.SESEAB.services.SESEABSetionmemberService"
                        , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                        , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                        , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                        , "com.supcon.orchid.SESWssEP.services.SESWssEPEmcPlanActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDAccidentService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDActionOwnersService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCctvRecordService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDCommunicationService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDEmePlanObjService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDMesPersonService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecordActionService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
                    , "com.supcon.orchid.SESECD.services.SESECDRecorSituationService"
        };
        //过滤重复的Service
        List<Object> objects = new ArrayList<Object>();
        for (int i=0; i<importServices.length; i++) {
            if(!objects.contains(importServices[i])){
                objects.add(importServices[i]);
            }
        }

        List<CustomPropertyModelMapping> list = modelServiceFoundation.getCustomPropertiesWithType("SESECD_1.0.0_alarmRecord", DbColumnType.OBJECT);
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

        List<Model> models = modelServiceFoundation.getModels("SESECD_1.0.0_alarmRecord");
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
	@GetMapping(value = {"/SESECD/alarmRecord/almAlarmRecord/downloadXls","/SESECD/alarmRecord/almAlarmRecord/proj/downloadXls"})
	@ResponseBody
	public String downloadXls(String datagridCode, HttpServletResponse response) {
		try{
		    response.setContentType("application/binary;charset=UTF-8");
        	ServletOutputStream out = response.getOutputStream();
        	Model model = modelServiceFoundation.getModel("SESECD_1.0.0_alarmRecord_AlmAlarmRecord");
        	exportService.exportExcel(out,response, model, null, null, datagridCode);
        	response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("SESECD_1.0.0_alarmRecord_AlmAlarmRecord.xls", "UTF-8"));
        	almAlarmRecordService.excelExport();

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

    @GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/networkPrint", produces = "application/json")
	@ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.PRINT,
                      modelCodes = {"SESECD_1.0.0_alarmRecord_AlmAlarmRecord"},
                      mainModelCode = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord",
                      mainModelName = "SESECD.alarmRecord.AlmAlarmRecord",
                      mainEntityCode = "SESECD_1.0.0_alarmRecord",
                      mainEntityName = "SESECD.entityname.randon1576460940310",
                      isLazy = true
    )
	public String print(String printerName,String templateCode,Long id,Integer count,String modelCode,HttpServletRequest request){
		SESECDAlmAlarmRecord almAlarmRecord=almAlarmRecordService.getAlmAlarmRecord(id);
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
				result = printClientService.doPrintInfo(almAlarmRecord,templateCode, Long.valueOf(id), modelCode, printerName, Integer.valueOf(count),paramMap);
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
    @GetMapping(value = "/SESECD/alarmRecord/almAlarmRecord/printOnServer", produces = "application/json")
    @ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.PRINT,
                      modelCodes = {"SESECD_1.0.0_alarmRecord_AlmAlarmRecord"},
                      mainModelCode = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord",
                      mainModelName = "SESECD.alarmRecord.AlmAlarmRecord",
                      mainEntityCode = "SESECD_1.0.0_alarmRecord",
                      mainEntityName = "SESECD.entityname.randon1576460940310",
                      isLazy = true
    )
    public Map<String, Object> printOnServer(Long id, String templateCode, String viewCode, HttpServletRequest request) throws Exception {
    	SESECDAlmAlarmRecord almAlarmRecord = null;
    	if(null != id) {
    		almAlarmRecord = almAlarmRecordService.getAlmAlarmRecord(id);
    	}
    //		String templateCode = request.getParameter("templateCode");
    	SignatureLog signatureLog = (SignatureLog)request.getAttribute("signatureLog");
    	String pdfFileName=printService.printOnServer(almAlarmRecord,templateCode,viewCode, null,signatureLog);
    	printXmlInfo.put("pdfFileName", pdfFileName);
    	return printXmlInfo;
    }
    @PostMapping(value = "/SESECD/alarmRecord/almAlarmRecord/batchPrintOnServer", produces = "application/json")
    @ResponseBody
    @AuditBusinessLog(moduleCode = "SESECD",
                      moduleName = "SESECD.modulename.randon1594690070190",
                      operateType = OperateType.BATCH_PRINT,
                      modelCodes = {"SESECD_1.0.0_alarmRecord_AlmAlarmRecord"},
                      mainModelCode = "SESECD_1.0.0_alarmRecord_AlmAlarmRecord",
                      mainModelName = "SESECD.alarmRecord.AlmAlarmRecord",
                      mainEntityCode = "SESECD_1.0.0_alarmRecord",
                      mainEntityName = "SESECD.entityname.randon1576460940310",
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
                objs.add(almAlarmRecordService.getAlmAlarmRecord(Long.parseLong(arrId[i])));
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
    /* CUSTOM CODE START(controller,beforeReturnHtmlMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
        return viewName;
    }

	private void bapBeforeCustomMethod(String code, String methodName, QueryEntity queryEntity) throws Exception {
	/* CUSTOM CODE START(controller,beforeMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	private void bapAfterCustomMethod(String code, String methodNam, Page page) throws Exception {
	/* CUSTOM CODE START(controller,afterMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码
	//alarmRecordService.SetPageLonLatHeiInfo(page);
/* CUSTOM CODE END */
	}

	private void bapGetDataAfterCustomMethod(String methodName, SESECDAlmAlarmRecord result, String includes) throws Exception {
    /* CUSTOM CODE START(controller,getDataAfterMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
    private void bapBeforeRecallMethod(Long tableinfoId) throws Exception {
	/* CUSTOM CODE START(controller,beforeRecallMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	private void bapAfterRecallMethod(Long tableinfoId) throws Exception {
    /* CUSTOM CODE START(controller,afterRecallMethods,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
    private void afterSubmitAtNewTransactionCustomMethod(String viewCode, String methodName, SESECDAlmAlarmRecord result) throws Exception {
    /* CUSTOM CODE START(controller,afterSubmitAtNewTransaction,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
    }
	/* CUSTOM CODE START(controller,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

	@Autowired
	private com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService alarmRecordService;
/* CUSTOM CODE END */
}