package com.supcon.orchid.SESECD.services;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import org.hibernate.criterion.Criterion;
import java.util.List;
import java.util.Locale;
import com.supcon.orchid.services.IBusinessKeyService;
import com.supcon.orchid.services.IModelTreeLayRecService;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.services.QueryEntity;
import com.supcon.orchid.ec.services.DataGridService;
import com.supcon.orchid.ec.entities.Event;
import com.supcon.orchid.ec.entities.View;
import com.supcon.orchid.ec.entities.DataGrid;
import com.supcon.orchid.ec.entities.Model;
import com.supcon.orchid.ec.entities.Property;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.audit.entities.SignatureLog;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;

import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecordDealInfo;
import com.supcon.orchid.utils.Param;
import com.supcon.orchid.ec.entities.EntityTableInfo;
import com.supcon.orchid.SESECD.entities.SESECDAllEmerMember;
import com.supcon.orchid.SESECD.entities.SESECDEmerExperts;
import com.supcon.orchid.SESECD.entities.SESECDEmerMembers;
import com.supcon.orchid.SESECD.entities.SESECDAccident;
import com.supcon.orchid.SESECD.entities.SESECDActionOwners;
import com.supcon.orchid.SESECD.entities.SESECDAlarmActCamera;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDCctvRecord;
import com.supcon.orchid.SESECD.entities.SESECDCommunication;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.entities.SESECDMesPerson;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfo;
import com.supcon.orchid.SESECD.entities.SESECDChangeLog;
import com.supcon.orchid.SESECD.entities.SESECDDispatConfig;
import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;
import com.supcon.orchid.SESECD.entities.SESECDEcdAction;
import com.supcon.orchid.SESECD.entities.SESECDEcdCommom;
import com.supcon.orchid.SESECD.entities.SESECDEcdStatius;
import com.supcon.orchid.SESECD.entities.SESECDActVideoCamera;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;
import com.supcon.orchid.SESECD.entities.SESECDMainPeople;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveL;
import com.supcon.orchid.SESECD.entities.SESECDEmEventType;
import com.supcon.orchid.SESECD.entities.SESECDEventDescribe;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.entities.SESECDSourceTerminal;
import com.supcon.orchid.SESECD.entities.SESECDSentence;
import com.supcon.orchid.SESECD.entities.SESECDTagConfig;
import com.supcon.orchid.SESECD.entities.SESECDVoiceConfig;
import com.supcon.orchid.SESECD.entities.SESECDVoiceMember;
/* CUSTOM CODE START(service,import,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
public interface SESECDAlmAlarmRecordService extends IBusinessKeyService {

	/**
	 * controller直接调用的列表查询
	 *
	 * @param page
	 * @param viewCode
	 * @param datagridCode
	 * @param processKey
	 * @param entityCode
	 * @param fastQueryCond
	 * @param advQueryCond
	 * @param classifyCodes
	 * @param dataTableSortColKey
	 * @param dataTableSortColName
	 * @param dataTableSortColOrder
	 * @param mainBusinessId
	 * @param tree_listView_assTreeModelCode_id
	 * @param tree_listView_assTreeModelCode_layRec
	 * @param tree_listView_assTreeModelCode_layNo
	 * @param tree_listView_assTreeModelCode_parentId
	 * @param tree_listView_assTreeModelCode_companyId
	 * @param reason_layRec
	 * @param department_layRec
	 * @param position_layRec
	 * @param listView_assModel_modelName_layRec
	 * @param model_layRec
	 * @param split
	 * @param searchObjects
	 * @param tableProcessKey
	 * @param permissionCode
	 * @param flowBulkFlag
	 * @param noQueryFlag
	 * @param exportSql
	 * @param findExportDataInfosCount
	 * @param queryEntity
	 * @return
	 */
	void listQuery(Page<SESECDAlmAlarmRecord> page, int currentSqlType, String viewCode, String datagridCode, Boolean crossCompanyFlag,
			String processKey, String entityCode, String fastQueryCond, String advQueryCond, String classifyCodes,
			String dataTableSortColKey, String dataTableSortColName, String dataTableSortColOrder, Long mainBusinessId,
			String split, Object[] searchObjects,
			String tableProcessKey, String permissionCode, Boolean flowBulkFlag, Boolean noQueryFlag, String exportSql,
			Integer findExportDataInfosCount, Map<String,String> dynamicFieldsMap, QueryEntity queryEntity);


	/**
	 * controller直接调用的编辑视图提交
	 *
	 * @param expenseBil
	 * @param workFlowVar
	 * @param pendingId
	 * @param deploymentId
	 * @param operateType
	 * @param pendingActivityType
	 * @param webSignetFlag
	 * @param signatureLog
	 * @param orchid_ass_fileuploads
	 * @param superEdit
	 * @param dgLists
	 * @param dgDeletedIds
	 * @param viewCode
	 * @param activityName
	 * @param locale
	 * @return
	 */
	Map<String, Object> submit(SESECDAlmAlarmRecord almAlarmRecord, WorkFlowVar workFlowVar, Long pendingId,
			Long deploymentId, String operateType, String pendingActivityType, Boolean webSignetFlag,
			SignatureLog signatureLog, Map<String, Object> orchid_ass_fileuploads, Boolean superEdit,
			Map<String, String> dgLists, Map<String, String> dgDeletedIds, String viewCode, String activityName,
			Locale locale, String viewSelect);

	/**
	 * controller直接调用的获取datagrid
	 *
	 * @param dgPage
	 * @param datagridCode
	 * @param dataGridName
	 * @param parameters
	 * @param refId
	 * @param expenseBil
	 * @throws Exception
	 */
	void dataGridData(Page dgPage, String datagridCode, String dataGridName, Map<String, String[]> parameters,
			Long refId, Long expenseBilId, QueryEntity queryEntity) throws Exception;


	/**
	 * 获取父节点对象
	*/
	public Map<String, Object> getParentNodeMap(Serializable mainDisplayName, Serializable businessKeyName, List<Serializable> serial, String queryParam);
	
	/**
	 * 根据ID，获取 接警记录 对象
	 * @param id
	 * @return
	 */
	SESECDAlmAlarmRecord getAlmAlarmRecord(long id);

	/**
	 * 根据ID，获取 接警记录 对象
	 * @param id
	 * @return
	 */
	SESECDAlmAlarmRecord getAlmAlarmRecord(long id, String viewCode);

	/**
	 * 根据ID，获取 接警记录 对象,不包含多选控件信息
	 * @param id
	 * @return
	 */
	SESECDAlmAlarmRecord getAlmAlarmRecordWithoutMultiselect(long id);

	/**
	 * 根据ID，获取 接警记录 对象 并转化为JSON
	 * @param id
	 * @return
	 */
	String getAlmAlarmRecordAsJSON(long id, String include);
	
	/**
	 * 根据ID，获取 接警记录 对象的创建人信息map
	 * @param id
	 * @return
	 */
	Map<String, Long> getCreateInfoMap(long id);

	/**
	 * 删除  接警记录 对象
	 * @param almAlarmRecord 接警记录
	 */
	void deleteAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord);

		
	/**
	 * 根据ID，删除  接警记录 对象
	 * @param id
	 */
	void deleteAlmAlarmRecord(long almAlarmRecordId, int almAlarmRecordVersion);

	/**
	 * 根据ID，删除  接警记录 对象
	 * @param id
	 */
	void deleteAlmAlarmRecord(long almAlarmRecordId, int almAlarmRecordVersion,SignatureLog signatureLog);

	/**
	 * 根据ID串，删除多个  接警记录 对象
	 * @param almAlarmRecordIds
	 */
	void deleteAlmAlarmRecord(String almAlarmRecordIds);

	/**
	 * 根据ID串，删除多个  接警记录 对象
	 * @param almAlarmRecordIds
	 */
	void deleteAlmAlarmRecord(String almAlarmRecordIds,SignatureLog signatureLog);

	/**
	 * 根据ID，删除多个  接警记录 对象
	 * @param almAlarmRecordIds
	 */
	void deleteAlmAlarmRecord(List<Long> almAlarmRecordIds);
	
	/**
	 * 根据ID，删除多个  接警记录 对象
	 * @param almAlarmRecordIds
	 */
	void deleteAlmAlarmRecord(List<Long> almAlarmRecordIds, String eventTopic);

	/**
	 * 根据ID，还原  接警记录 对象
	 * @param almAlarmRecordId
	 */
	void restoreAlmAlarmRecord(String almAlarmRecordIds);
	/**
	 * 根据ID，还原  接警记录 对象
	 * @param almAlarmRecordId
	 */
	void restoreAlmAlarmRecord(long almAlarmRecordId);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode);
	
	void batchImportBaseAlmAlarmRecord(List<SESECDAlmAlarmRecord>  almAlarmRecords);
	
	void excelBatchImportBaseAlmAlarmRecord(List<SESECDAlmAlarmRecord>  almAlarmRecords);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);

	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode, String eventTopic,boolean... isImport);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode, String eventTopic,SignatureLog signatureLog,boolean... isImport);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 * 电子签名接口
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService, String viewCode, String eventTopic,SignatureLog signatureLog,boolean... isImport);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void mergeAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);

	/**
	 * 根据条件(多条件)获取指定页码的 接警记录 集合
	 * 
	 * @param page
	 * @param criterions
	 * @return
	 */
	Page<SESECDAlmAlarmRecord> findAlmAlarmRecords(Page<SESECDAlmAlarmRecord> page,Criterion...criterions);
	
	/*
	 * 解析主模型的过滤条件
	 *
	 */
	public List<Object> generateParamExcelSql(List<Param> params, Integer type, String viewCode);
	
	/**
	 * 根据模型名称获得视图名称
	 * @param modelCode
	 * @return
	 */
	public String getViewName(String modelCode);
	
	/**
	 * 保存处理意见
	 * 
	 * @param AlmAlarmRecordDealInfo 接警记录DealInfo
	 * @return
	 */
	void saveDealInfo(SESECDAlmAlarmRecordDealInfo dealInfo);

	/**
	 * 根据 tableInfoId 获取处理意见
	 * 
	 * @param tableInfoId
	 * @return
	 */
	int getDealInfoCount(Long tableInfoId);

	/**
	 * 根据 tableInfoId 获取处理意见
	 * 
	 * @param tableInfoId
	 * @param expandFlag 是否展现没有详细描述的处理意见
	 * @return
	 */
	List<Object[]> findDealInfos(Long tableInfoId, boolean expandFlag);
	
	/**
	 * @param tableInfoId
	 * @param expandFlag
	 * @return
	 */
	Map<String, List<Object[]>> findDealInfosGroup(Long tableInfoId, boolean expandFlag);

	/**
	 * 根据菜单编码获取菜单对应的工作流的processKey（仅工作流）
	 * 
	 * @param menuCode 菜单编码
	 * @return
	 */
	public String getWorkFlowInfo(String menuCode);
	
	/**
	 * 根据条件(多条件)获取指定页码的 接警记录 集合
	 * 
	 * @param page
	 * @param criterions
	 * @param viewCode
	 * @param type
	 * @param processKey
	 * @param params 条件参数信息
	 * @param permissionCode 权限code
	 * @return
	 */
	void findAlmAlarmRecords(Page<SESECDAlmAlarmRecord> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity);

	/**
	 * 根据条件(多条件)获取指定页码的 接警记录 集合
	 * 
	 * @param page
	 * @param criterions
	 * @param viewCode
	 * @param type
	 * @param processKey
	 * @param params 条件参数信息
	 * @param permissionCode 权限code
	 * @param objects
	 * @return
	 */
	void findAlmAlarmRecords(Page<SESECDAlmAlarmRecord> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap, QueryEntity queryEntity, Object... objects);

	/**
	 * 根据条件(多条件)获取指定页码的 接警记录 集合
	 * 
	 * @param page
	 * @param criterions
	 * @param viewCode
	 * @param type
	 * @param processKey
	 * @param params 条件参数信息
	 * @return
	 */
	void findAlmAlarmRecords(Page<SESECDAlmAlarmRecord> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment,Boolean noQueryFlag, String exportSql, Map exportMap, List<Param> params,QueryEntity queryEntity);
	/**
	 * 根据条件(多条件)获取指定页码的数据集合(针对编辑页面datagrid)
	 * 
	 * @param dg
	 * @param dgClass
	 * @param page
	 * @param orgObj
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	void findDataGridPage(DataGrid dg,Class dgClass,Page dgPage,Object orgObj, String condition, List<Object> params);

	/**
	 * 助记码查找
	 * 
	 * @param viewCode
	 * @param params 查询条件
	 * @param showNumber 显示记录数
	 * @param condition 配置时配置的自定义SQL
	 */
	List<Object[]> mneCodeSearch( String viewCode,  int showNumber, boolean cross,List<Param> params,String  refViewCode,String currentViewCode,String sqlType,String realPermissionCode,QueryEntity queryEntity);
	
	List<SESECDAlmAlarmRecord> getAlmAlarmRecords(String sql , List<Object> params);
	
	/**
	 * 通过sql获取实体list
	 * @param sql
	 * @param params
	 */
	public List<SESECDAlmAlarmRecord> findAlmAlarmRecordsBySql(String sql, List<Object> params);
	
	/**
	 * 通过hql获取实体list
	 * @param hql
	 * @param Object...
	 */
	public List<SESECDAlmAlarmRecord> findAlmAlarmRecordsByHql(String hql, Object... objects);
	
	SESECDAlmAlarmRecord loadAlmAlarmRecordByBussinessKey(SESECDAlmAlarmRecord almAlarmRecord);
	
	Page<SESECDAlmAlarmRecord> getAlmAlarmRecords(Page<SESECDAlmAlarmRecord> page, String sql , List<Object> params, String sort);
	//==============DataGrid多选控件使用 start================

	
	
	
	/**
	 * 根据业务主键查询对象
	 * @param bussinessKey
	 */
	public SESECDAlmAlarmRecord loadAlmAlarmRecordByBussinessKey(Serializable bussinessKey);
	
	/**
	 * 根据字段查询对象列表
	 * @param propertyName
	 * @param object
	 */
	public List<SESECDAlmAlarmRecord> findByProperty(String propertyName, Object object);
	/**
	 * 根据字段查询唯一对象
	 * @param propertyName
	 * @param object
	 */
	public SESECDAlmAlarmRecord findEntityByProperty(String propertyName, Object object);
	
	/**
	 * 根据业务主键删除对象 以逗号分隔
	 * @param bussinessKey
	 */
	public void deleteByBussinessKeys(String bussinessKeys);
	
	/**
	 * 动态查询bap_validate_datagrids变量
	 */
	public String findValidateDatagrids(Map<String,Class> dgclass,String viewCode);
	
	public String findValidateDatagrids(Map<String,Class> dgclass);
	
	/**
	 * 动态查询MainViewPath变量
	 */
	public String findMainViewPath();
	
	/**
	 * 动态查询datagrids
	 */
	public List<DataGrid> findDatagrids();
	
		/**
	 * 根据业务主键名称获取所有有效的业务主键数据
	 * @param bussinessKeyName
	 */
	public List<Object> getBusinessKeyData(String businessKeyName);
	
	/**
	 * 根据业务主键名称获取所有业务主键数据
	 * @param bussinessKeyName
	 */
	public List<Object> getBusinessKeyDataByBusinessKeyName(String businessKeyName);
	
	/**
	 * 根据字段名获取该字段在表中数据数
	 * @param propertyName
	 */
	public Map<Object, Object> getReplaceProperty(String propertyName,String businessKey);
	
	/**
     * Excel 主辅模型导入前
     * @param testPTs
     */
    public void beforeImportAlmAlarmRecord(List<SESECDAlmAlarmRecord> insertObjs, List<SESECDAlmAlarmRecord> updateObjs,
        List<Map<String,String>> columnInfo,List<Map<String, Map<Integer, Map<Integer, String>>>> errMsgSheet, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);
    /**
     * Excel 主辅模型导入后
     * @param testPTs
     */
    public void afterImportAlmAlarmRecord(List<SESECDAlmAlarmRecord> insertObjs, List<SESECDAlmAlarmRecord> updateObjs,
        List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);

	/**
	 * 批量导入excel数据(表单)
	 * @param insertObjs 新增数据
	 * @param updateObjs 修改数据
	 * @param columnInfo excel中的列信息
	 */
	public Map<Object, Long> importBatchAlmAlarmRecord(List<SESECDAlmAlarmRecord> insertObjs, List<SESECDAlmAlarmRecord> updateObjs, List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);
	
	/**
	 * 批量导入excel数据(表单)
	 * @param insertObjs 新增数据
	 * @param updateObjs 修改数据
	 * @param columnInfo excel中的列信息
	 */
	public Map<Object, Long> importBatchAlmAlarmRecord(List<SESECDAlmAlarmRecord> insertObjs, List<SESECDAlmAlarmRecord> updateObjs, List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo,SignatureLog signatureLog);
	
	
	public Map<String, Object> getMainDisplayMap(Serializable mainDisplayName, Serializable mainBusName, List<Serializable> mainDisplayKeys);
	
	public List<String> getSystemCodeFullPathNameByEntityCode(String entityCode);
	
	/**
	 * 获取已启用的自定义字段对象.
	 * 
	 * @param entityCode 模型code
	 * @return Property Code
	 */
	List<String> getRunningCustomProperties(String entityCode);
	
	/**
	 * 根据模型字段名称获取引用的字段
	 * @param propertyCode
	 * @return
	 */
	public String getAssProperty(String propertyCode);
	
	/**
	 * 根据字段code获取模型code 
	 * @param propertyCode
	 * @return
	 */
	public String getPropertyModelCode(String propertyCode);

	
	/**
	 * 以下为兼容视图热部署之前代码的方法
	 */
	 
	/**
	 * 根据条件(多条件)获取指定页码的 录像回放 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1599831895630Page(Page<SESECDCctvRecord> dg1599831895630Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联预案 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1577068896875Page(Page<SESECDEmePlanObj> dg1577068896875Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联指令 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1577068896913Page(Page<SESECDAlarmAction> dg1577068896913Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联态势 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1615989219992Page(Page<SESECDRecorSituation> dg1615989219992Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联行动 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1615989227167Page(Page<SESECDRecordAction> dg1615989227167Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 通信记录 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1686622566471Page(Page<SESECDCommunication> dg1686622566471Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联预案 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1576924369718Page(Page<SESECDEmePlanObj> dg1576924369718Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联指令 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1576924369782Page(Page<SESECDAlarmAction> dg1576924369782Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联预案 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1577068824933Page(Page<SESECDEmePlanObj> dg1577068824933Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 关联指令 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1577068825001Page(Page<SESECDAlarmAction> dg1577068825001Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	/**
	 * 根据条件(多条件)获取指定页码的 录像回放 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param almAlarmRecord  接警记录
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1600177870401Page(Page<SESECDCctvRecord> dg1600177870401Page,SESECDAlmAlarmRecord almAlarmRecord, String condition, List<Object> params);
	 
	 
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService, String viewCode);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService, String viewCode, String eventTopic,boolean... isImport);
	
	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 */
	void saveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService);

	/**
	 * 导入保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 */
	Map<Object, Object> almAlarmRecordDataGridImport(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService, String viewCode, String eventTopic,Property businessKey, boolean isImport);
	
	public void batchSaveAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService, View view,List<Event>  events, String eventTopic, boolean... isImport);

	/**
	 * 保存  接警记录 对象
	 * @param almAlarmRecord  接警记录
	 * @param dataGridService DataGridService对象
	 */
	void mergeAlmAlarmRecord(SESECDAlmAlarmRecord almAlarmRecord, DataGridService dataGridService);
	
	/**
	 * Excel导出 
	 */
	void excelExport();
	
	/**
	 * 页面打印 
	 */
	void print(int printType);
	
	public  Object generateObjectFromJson(String jsonStr, Class clazz);
	
	/**
	 * 获取分页
	 */
	Page<SESECDAlmAlarmRecord> getByPage(Page<SESECDAlmAlarmRecord> page,
			DetachedCriteria detachedCriteria);
	
	/**
	 * 获取关联指令列表
	 */
	public List<SESECDAlarmAction> getAlarmActionList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取事故类型列表
	 */
	public List<SESECDAlarmEnenetrel> getAlarmEnenetrelList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取通信记录列表
	 */
	public List<SESECDCommunication> getCommunicationList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取关联预案列表
	 */
	public List<SESECDEmePlanObj> getEmePlanObjList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取推送人员列表
	 */
	public List<SESECDMesPerson> getMesPersonList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取关联行动列表
	 */
	public List<SESECDRecordAction> getRecordActionList(SESECDAlmAlarmRecord almAlarmRecord);
	/**
	 * 获取关联态势列表
	 */
	public List<SESECDRecorSituation> getRecorSituationList(SESECDAlmAlarmRecord almAlarmRecord);
	
	
	
	void findBusinessKeyUsed(long almAlarmRecordId);
	
	void generateAlmAlarmRecordCodes(SESECDAlmAlarmRecord almAlarmRecord) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateAlmAlarmRecordCodes(SESECDAlmAlarmRecord almAlarmRecord, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateAlmAlarmRecordSummarys(SESECDAlmAlarmRecord almAlarmRecord) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateAlmAlarmRecordSummarys(SESECDAlmAlarmRecord almAlarmRecord, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	String generateCustomPropertySql(String viewCode, String datagridCode, String sql);
	
	void commonQuery(Page<SESECDAlmAlarmRecord> page, String viewCode, int type, String processKey, Boolean flowBulkFlag,
			Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity, Object... objects);
			
	String fLTL(String str);
	
	String fLTU(String str);
	
	void checkUniqueConstraint(SESECDAlmAlarmRecord almAlarmRecord);
	
	void dealDatagridsSave(SESECDAlmAlarmRecord almAlarmRecord,String viewCode,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);
	
	String generateTableNo();

    List<SESECDAlmAlarmRecord> findSESECDAlmAlarmRecordByIds(List<Long> ids);

    Map<String,SESECDAlmAlarmRecord> findAlmAlarmRecordByBussinessKeys(String bussinessKeys);

	void generateHtmlFile(String url, String projViewPath) throws Exception;

	/* CUSTOM CODE START(service,functions,SESECD_1.0.0_alarmRecord_AlmAlarmRecord,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}
