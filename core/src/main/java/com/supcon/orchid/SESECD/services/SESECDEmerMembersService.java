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

import com.supcon.orchid.utils.Param;
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
/* CUSTOM CODE START(service,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
public interface SESECDEmerMembersService extends IBusinessKeyService {

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
	void listQuery(Page<SESECDEmerMembers> page, int currentSqlType, String viewCode, String datagridCode, Boolean crossCompanyFlag,
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
	Map<String, Object> submit(SESECDEmerMembers emerMembers, WorkFlowVar workFlowVar, Long pendingId,
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
	 * 根据ID，获取 应急小组 对象
	 * @param id
	 * @return
	 */
	SESECDEmerMembers getEmerMembers(long id);

	/**
	 * 根据ID，获取 应急小组 对象
	 * @param id
	 * @return
	 */
	SESECDEmerMembers getEmerMembers(long id, String viewCode);

	/**
	 * 根据ID，获取 应急小组 对象,不包含多选控件信息
	 * @param id
	 * @return
	 */
	SESECDEmerMembers getEmerMembersWithoutMultiselect(long id);

	/**
	 * 根据ID，获取 应急小组 对象 并转化为JSON
	 * @param id
	 * @return
	 */
	String getEmerMembersAsJSON(long id, String include);
	
	/**
	 * 根据ID，获取 应急小组 对象的创建人信息map
	 * @param id
	 * @return
	 */
	Map<String, Long> getCreateInfoMap(long id);

	/**
	 * 删除  应急小组 对象
	 * @param emerMembers 应急小组
	 */
	void deleteEmerMembers(SESECDEmerMembers emerMembers);

		
	/**
	 * 根据ID，删除  应急小组 对象
	 * @param id
	 */
	void deleteEmerMembers(long emerMembersId, int emerMembersVersion);

	/**
	 * 根据ID，删除  应急小组 对象
	 * @param id
	 */
	void deleteEmerMembers(long emerMembersId, int emerMembersVersion,SignatureLog signatureLog);

	/**
	 * 根据ID串，删除多个  应急小组 对象
	 * @param emerMembersIds
	 */
	void deleteEmerMembers(String emerMembersIds);

	/**
	 * 根据ID串，删除多个  应急小组 对象
	 * @param emerMembersIds
	 */
	void deleteEmerMembers(String emerMembersIds,SignatureLog signatureLog);

	/**
	 * 根据ID，删除多个  应急小组 对象
	 * @param emerMembersIds
	 */
	void deleteEmerMembers(List<Long> emerMembersIds);
	
	/**
	 * 根据ID，删除多个  应急小组 对象
	 * @param emerMembersIds
	 */
	void deleteEmerMembers(List<Long> emerMembersIds, String eventTopic);

	/**
	 * 根据ID，还原  应急小组 对象
	 * @param emerMembersId
	 */
	void restoreEmerMembers(String emerMembersIds);
	/**
	 * 根据ID，还原  应急小组 对象
	 * @param emerMembersId
	 */
	void restoreEmerMembers(long emerMembersId);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode);
	
	void batchImportBaseEmerMembers(List<SESECDEmerMembers>  emerMemberss);
	
	void excelBatchImportBaseEmerMembers(List<SESECDEmerMembers>  emerMemberss);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);

	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode, String eventTopic,boolean... isImport);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode, String eventTopic,SignatureLog signatureLog,boolean... isImport);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 * 电子签名接口
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService, String viewCode, String eventTopic,SignatureLog signatureLog,boolean... isImport);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dgLists DataGrid对象集合
	 * @param dgDeleteIDs DataGrid对象删除ID集合
	 * @param assFileUploads DataGrid关联附件集合
	 */
	void mergeEmerMembers(SESECDEmerMembers emerMembers, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);

	/**
	 * 根据条件(多条件)获取指定页码的 应急小组 集合
	 * 
	 * @param page
	 * @param criterions
	 * @return
	 */
	Page<SESECDEmerMembers> findEmerMemberss(Page<SESECDEmerMembers> page,Criterion...criterions);

	/**
	 * 根据菜单编码获取菜单对应的工作流的processKey（仅工作流）
	 * 
	 * @param menuCode 菜单编码
	 * @return
	 */
	public String getWorkFlowInfo(String menuCode);
	
	/**
	 * 根据条件(多条件)获取指定页码的 应急小组 集合
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
	void findEmerMemberss(Page<SESECDEmerMembers> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity);

	/**
	 * 根据条件(多条件)获取指定页码的 应急小组 集合
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
	void findEmerMemberss(Page<SESECDEmerMembers> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap, QueryEntity queryEntity, Object... objects);

	/**
	 * 根据条件(多条件)获取指定页码的 应急小组 集合
	 * 
	 * @param page
	 * @param criterions
	 * @param viewCode
	 * @param type
	 * @param processKey
	 * @param params 条件参数信息
	 * @return
	 */
	void findEmerMemberss(Page<SESECDEmerMembers> page, String viewCode, int type,String processKey,Boolean flowBulkFlag, Boolean hasAttachment,Boolean noQueryFlag, String exportSql, Map exportMap, List<Param> params,QueryEntity queryEntity);
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
	
	List<SESECDEmerMembers> getEmerMemberss(String sql , List<Object> params);
	
	/**
	 * 通过sql获取实体list
	 * @param sql
	 * @param params
	 */
	public List<SESECDEmerMembers> findEmerMemberssBySql(String sql, List<Object> params);
	
	/**
	 * 通过hql获取实体list
	 * @param hql
	 * @param Object...
	 */
	public List<SESECDEmerMembers> findEmerMemberssByHql(String hql, Object... objects);
	
	SESECDEmerMembers loadEmerMembersByBussinessKey(SESECDEmerMembers emerMembers);
	
	Page<SESECDEmerMembers> getEmerMemberss(Page<SESECDEmerMembers> page, String sql , List<Object> params, String sort);
	//==============DataGrid多选控件使用 start================
	//===================树形PT=======================
	List<SESECDEmerMembers> findByTreeDataGridEmerMembers(List<SESECDEmerMembers> treeList);
	//=======================普通PT==================================
	void findByNormalDataGridEmerMembers(Page<SESECDEmerMembers> dgPage);

	
	
	
	/**
	 * 根据业务主键查询对象
	 * @param bussinessKey
	 */
	public SESECDEmerMembers loadEmerMembersByBussinessKey(Serializable bussinessKey);
	
	/**
	 * 根据字段查询对象列表
	 * @param propertyName
	 * @param object
	 */
	public List<SESECDEmerMembers> findByProperty(String propertyName, Object object);
	/**
	 * 根据字段查询唯一对象
	 * @param propertyName
	 * @param object
	 */
	public SESECDEmerMembers findEntityByProperty(String propertyName, Object object);
	
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
    public void beforeImportEmerMembers(List<SESECDEmerMembers> insertObjs, List<SESECDEmerMembers> updateObjs,
        List<Map<String,String>> columnInfo,List<Map<String, Map<Integer, Map<Integer, String>>>> errMsgSheet, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);
    /**
     * Excel 主辅模型导入后
     * @param testPTs
     */
    public void afterImportEmerMembers(List<SESECDEmerMembers> insertObjs, List<SESECDEmerMembers> updateObjs,
        List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);

	/**
	 * 批量导入excel数据(表单)
	 * @param insertObjs 新增数据
	 * @param updateObjs 修改数据
	 * @param columnInfo excel中的列信息
	 */
	public Map<Object, Long> importBatchEmerMembers(List<SESECDEmerMembers> insertObjs, List<SESECDEmerMembers> updateObjs, List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo);
	
	/**
	 * 批量导入excel数据(表单)
	 * @param insertObjs 新增数据
	 * @param updateObjs 修改数据
	 * @param columnInfo excel中的列信息
	 */
	public Map<Object, Long> importBatchEmerMembers(List<SESECDEmerMembers> insertObjs, List<SESECDEmerMembers> updateObjs, List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo,SignatureLog signatureLog);
	
	//excel辅模型查询
	public void excelAuxModelQuery(Page<SESECDEmerMembers> page,  String viewCode, int type, String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode, Boolean noQueryFlag,String exportSql,List paramMain, Map assosiatedKey, Model model,Map excelParamsMap);
	
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
	 * 根据条件(多条件)获取指定页码的 所有应急小组 集合(针对编辑页面datagrid)
	 * 
	 * @param page
	 * @param emerMembers  应急小组
	 * @param condition 配置datagrid时配置的自定义SQL
	 */
	 void findDg1616433002245Page(Page<SESECDAllEmerMember> dg1616433002245Page,SESECDEmerMembers emerMembers, String condition, List<Object> params);
	 
	 
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService, String viewCode);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService, String viewCode, String eventTopic,boolean... isImport);
	
	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 */
	void saveEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService);

	/**
	 * 导入保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 */
	Map<Object, Object> emerMembersDataGridImport(SESECDEmerMembers emerMembers, DataGridService dataGridService, String viewCode, String eventTopic,Property businessKey, boolean isImport);
	
	public void batchSaveEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService, View view,List<Event>  events, String eventTopic, boolean... isImport);

	/**
	 * 保存  应急小组 对象
	 * @param emerMembers  应急小组
	 * @param dataGridService DataGridService对象
	 */
	void mergeEmerMembers(SESECDEmerMembers emerMembers, DataGridService dataGridService);
	
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
	Page<SESECDEmerMembers> getByPage(Page<SESECDEmerMembers> page,
			DetachedCriteria detachedCriteria);
	
	
	
	
	void findBusinessKeyUsed(long emerMembersId);
	
	void generateEmerMembersCodes(SESECDEmerMembers emerMembers) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateEmerMembersCodes(SESECDEmerMembers emerMembers, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateEmerMembersSummarys(SESECDEmerMembers emerMembers) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	void generateEmerMembersSummarys(SESECDEmerMembers emerMembers, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	String generateCustomPropertySql(String viewCode, String datagridCode, String sql);
	
	void commonQuery(Page<SESECDEmerMembers> page, String viewCode, int type, String processKey, Boolean flowBulkFlag,
			Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity, Object... objects);
			
	String fLTL(String str);
	
	String fLTU(String str);
	
	void checkUniqueConstraint(SESECDEmerMembers emerMembers);
	
	void dealDatagridsSave(SESECDEmerMembers emerMembers,String viewCode,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads);
	
	String generateTableNo();

    List<SESECDEmerMembers> findSESECDEmerMembersByIds(List<Long> ids);

    Map<String,SESECDEmerMembers> findEmerMembersByBussinessKeys(String bussinessKeys);

	void generateHtmlFile(String url, String projViewPath) throws Exception;

	/* CUSTOM CODE START(service,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

}