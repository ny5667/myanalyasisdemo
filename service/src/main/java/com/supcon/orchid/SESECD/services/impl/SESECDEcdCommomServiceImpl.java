package com.supcon.orchid.SESECD.services.impl;
import com.supcon.greendill.common.cache.redis.util.RedisUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.supcon.orchid.annotation.BAPIsMneCode;
import com.supcon.orchid.annotation.BAPMneField;
import com.supcon.orchid.audit.entities.AuditUtil;
import com.supcon.orchid.foundation.utils.StringUtil;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.hibernate.criterion.DetachedCriteria;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import com.supcon.orchid.container.mvc.utils.XmlUtils;
import org.hibernate.query.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Disjunction;
import org.hibernate.transform.Transformers;
import com.supcon.orchid.SESECD.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supcon.orchid.workflow.engine.transformers.PendingResultTransformer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;
import com.supcon.orchid.services.BAPException;
import com.supcon.orchid.services.BAPException.Code;
import com.supcon.orchid.utils.DateUtils;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.supcon.supfusion.framework.cloud.common.context.UserContext;
import com.supcon.supfusion.systemconfig.api.tenantconfig.annotation.ClassSystemConfigAnno;
import com.supcon.orchid.services.IModelTreeLayRecService;
import com.supcon.orchid.container.mvc.results.BAPEntityTransformer;
import com.supcon.orchid.counter.Counter;
import com.supcon.orchid.counter.CounterManager;
import com.supcon.orchid.counter.CounterType;
import com.supcon.orchid.utils.DocumentUtils;
import com.supcon.orchid.id.SnowFlakeIdWorker;
import com.supcon.orchid.utils.GreendillSqlUtils;
import org.springframework.util.ObjectUtils;
import java.io.File;
import com.supcon.orchid.SESECD.services.SESECDEcdCommomService;
import com.supcon.orchid.SESECD.services.SESECDEcdCommomImportService;
import com.supcon.orchid.utils.DateUtils;
import com.supcon.orchid.utils.Inflector;
import com.supcon.orchid.utils.OrchidUtils;
import com.supcon.orchid.utils.Param;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.entities.Document;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.entities.User;
import com.supcon.orchid.foundation.entities.Position;
import com.supcon.orchid.foundation.entities.MenuOperate;
import com.supcon.orchid.db.DbUtils;
import com.supcon.orchid.utils.JSONPlainSerializer;
import com.supcon.orchid.utils.MneCodeGenterate;
import com.supcon.orchid.ec.entities.AdvQueryCondition;
import com.supcon.orchid.ec.entities.CustomPropertyModelMapping;
import com.supcon.orchid.ec.entities.CustomPropertyViewMapping;
import com.supcon.orchid.ec.entities.CustomerCondition;
import com.supcon.orchid.ec.entities.DataClassific;
import com.supcon.orchid.ec.entities.Sql;
import com.supcon.orchid.ec.entities.View;
import com.supcon.orchid.ec.entities.DataGrid;
import com.supcon.orchid.ec.entities.DealInfo;
import com.supcon.orchid.ec.entities.Entity;
import com.supcon.orchid.ec.entities.Event;
import com.supcon.orchid.ec.entities.ExtraView;
import com.supcon.orchid.ec.entities.DataGroup;
import com.supcon.orchid.ec.services.ConditionService;
import com.supcon.orchid.ec.services.SqlService;
import com.supcon.orchid.ec.services.ViewServiceFoundation;
import com.supcon.orchid.ec.services.DataGridService;
import com.supcon.orchid.ec.services.EntityServiceFoundation;
import com.supcon.orchid.utils.ReflectUtils;
import com.supcon.orchid.ec.services.CreatorService;
import com.supcon.orchid.foundation.services.DataPermissionService;
import com.supcon.orchid.foundation.services.DocumentService;
import com.supcon.orchid.foundation.services.SynchronizeInfoService;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.orchid.utils.ClobUtils;
import com.supcon.orchid.utils.FileUtil;
import com.supcon.orchid.script.entities.Script;
import com.supcon.orchid.script.EngineScriptExecutor;
import com.supcon.orchid.script.services.ScriptService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.orm.dao.BaseDao.DBTYPE;
import com.supcon.orchid.orm.enums.DealInfoType;
import com.supcon.orchid.orm.hibernate.types.TypeFactory;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import com.supcon.orchid.ec.enums.ViewType;
import com.supcon.orchid.ec.enums.ShowType;
import com.supcon.orchid.SESECD.entities.SESECDAllEmerMember;
import com.supcon.orchid.SESECD.daos.SESECDAllEmerMemberDao;
import com.supcon.orchid.SESECD.services.SESECDAllEmerMemberService;
import com.supcon.orchid.SESECD.entities.SESECDEmerExperts;
import com.supcon.orchid.SESECD.daos.SESECDEmerExpertsDao;
import com.supcon.orchid.SESECD.services.SESECDEmerExpertsService;
import com.supcon.orchid.SESECD.entities.SESECDEmerMembers;
import com.supcon.orchid.SESECD.daos.SESECDEmerMembersDao;
import com.supcon.orchid.SESECD.services.SESECDEmerMembersService;
import com.supcon.orchid.SESECD.entities.SESECDAccident;
import com.supcon.orchid.SESECD.daos.SESECDAccidentDao;
import com.supcon.orchid.SESECD.services.SESECDAccidentService;
import com.supcon.orchid.SESECD.entities.SESECDActionOwners;
import com.supcon.orchid.SESECD.daos.SESECDActionOwnersDao;
import com.supcon.orchid.SESECD.services.SESECDActionOwnersService;
import com.supcon.orchid.SESECD.entities.SESECDAlarmActCamera;
import com.supcon.orchid.SESECD.daos.SESECDAlarmActCameraDao;
import com.supcon.orchid.SESECD.services.SESECDAlarmActCameraService;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESECD.daos.SESECDAlarmActionDao;
import com.supcon.orchid.SESECD.services.SESECDAlarmActionService;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.daos.SESECDAlarmEnenetrelDao;
import com.supcon.orchid.SESECD.services.SESECDAlarmEnenetrelService;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.services.SESECDAlmAlarmRecordService;
import com.supcon.orchid.SESECD.entities.SESECDCctvRecord;
import com.supcon.orchid.SESECD.daos.SESECDCctvRecordDao;
import com.supcon.orchid.SESECD.services.SESECDCctvRecordService;
import com.supcon.orchid.SESECD.entities.SESECDCommunication;
import com.supcon.orchid.SESECD.daos.SESECDCommunicationDao;
import com.supcon.orchid.SESECD.services.SESECDCommunicationService;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.services.SESECDEmePlanObjService;
import com.supcon.orchid.SESECD.entities.SESECDMesPerson;
import com.supcon.orchid.SESECD.daos.SESECDMesPersonDao;
import com.supcon.orchid.SESECD.services.SESECDMesPersonService;
import com.supcon.orchid.SESECD.entities.SESECDRecordAction;
import com.supcon.orchid.SESECD.daos.SESECDRecordActionDao;
import com.supcon.orchid.SESECD.services.SESECDRecordActionService;
import com.supcon.orchid.SESECD.entities.SESECDRecorSituation;
import com.supcon.orchid.SESECD.daos.SESECDRecorSituationDao;
import com.supcon.orchid.SESECD.services.SESECDRecorSituationService;
import com.supcon.orchid.SESECD.entities.SESECDAlertRecord;
import com.supcon.orchid.SESECD.daos.SESECDAlertRecordDao;
import com.supcon.orchid.SESECD.services.SESECDAlertRecordService;
import com.supcon.orchid.SESECD.entities.SESECDBroadcastInfo;
import com.supcon.orchid.SESECD.daos.SESECDBroadcastInfoDao;
import com.supcon.orchid.SESECD.services.SESECDBroadcastInfoService;
import com.supcon.orchid.SESECD.entities.SESECDChangeLog;
import com.supcon.orchid.SESECD.daos.SESECDChangeLogDao;
import com.supcon.orchid.SESECD.services.SESECDChangeLogService;
import com.supcon.orchid.SESECD.entities.SESECDDispatConfig;
import com.supcon.orchid.SESECD.daos.SESECDDispatConfigDao;
import com.supcon.orchid.SESECD.services.SESECDDispatConfigService;
import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;
import com.supcon.orchid.SESECD.daos.SESECDEntranceInfoDao;
import com.supcon.orchid.SESECD.services.SESECDEntranceInfoService;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertImg;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertImgDao;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertImgService;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertRecord;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertRecordDao;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertRecordService;
import com.supcon.orchid.SESECD.entities.SESECDEcdAlertVideo;
import com.supcon.orchid.SESECD.daos.SESECDEcdAlertVideoDao;
import com.supcon.orchid.SESECD.services.SESECDEcdAlertVideoService;
import com.supcon.orchid.SESECD.entities.SESECDEcdAction;
import com.supcon.orchid.SESECD.daos.SESECDEcdActionDao;
import com.supcon.orchid.SESECD.services.SESECDEcdActionService;
import com.supcon.orchid.SESECD.entities.SESECDEcdCommom;
import com.supcon.orchid.SESECD.daos.SESECDEcdCommomDao;
import com.supcon.orchid.SESECD.services.SESECDEcdCommomService;
import com.supcon.orchid.SESECD.entities.SESECDEcdStatius;
import com.supcon.orchid.SESECD.daos.SESECDEcdStatiusDao;
import com.supcon.orchid.SESECD.services.SESECDEcdStatiusService;
import com.supcon.orchid.SESECD.entities.SESECDActVideoCamera;
import com.supcon.orchid.SESECD.daos.SESECDActVideoCameraDao;
import com.supcon.orchid.SESECD.services.SESECDActVideoCameraService;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.daos.SESECDEmcActionDao;
import com.supcon.orchid.SESECD.services.SESECDEmcActionService;
import com.supcon.orchid.SESECD.entities.SESECDMainDepartment;
import com.supcon.orchid.SESECD.daos.SESECDMainDepartmentDao;
import com.supcon.orchid.SESECD.services.SESECDMainDepartmentService;
import com.supcon.orchid.SESECD.entities.SESECDMainPeople;
import com.supcon.orchid.SESECD.daos.SESECDMainPeopleDao;
import com.supcon.orchid.SESECD.services.SESECDMainPeopleService;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.daos.SESECDEmcSituationDao;
import com.supcon.orchid.SESECD.services.SESECDEmcSituationService;
import com.supcon.orchid.SESECD.entities.SESECDEmEventLeveL;
import com.supcon.orchid.SESECD.daos.SESECDEmEventLeveLDao;
import com.supcon.orchid.SESECD.services.SESECDEmEventLeveLService;
import com.supcon.orchid.SESECD.entities.SESECDEmEventType;
import com.supcon.orchid.SESECD.daos.SESECDEmEventTypeDao;
import com.supcon.orchid.SESECD.services.SESECDEmEventTypeService;
import com.supcon.orchid.SESECD.entities.SESECDEventDescribe;
import com.supcon.orchid.SESECD.daos.SESECDEventDescribeDao;
import com.supcon.orchid.SESECD.services.SESECDEventDescribeService;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.daos.SESECDEcdParamConfigDao;
import com.supcon.orchid.SESECD.services.SESECDEcdParamConfigService;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.SESECD.daos.SESECDParamOptionDao;
import com.supcon.orchid.SESECD.services.SESECDParamOptionService;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.daos.SESECDSignalConfigDao;
import com.supcon.orchid.SESECD.services.SESECDSignalConfigService;
import com.supcon.orchid.SESECD.entities.SESECDSourceTerminal;
import com.supcon.orchid.SESECD.daos.SESECDSourceTerminalDao;
import com.supcon.orchid.SESECD.services.SESECDSourceTerminalService;
import com.supcon.orchid.SESECD.entities.SESECDSentence;
import com.supcon.orchid.SESECD.daos.SESECDSentenceDao;
import com.supcon.orchid.SESECD.services.SESECDSentenceService;
import com.supcon.orchid.SESECD.entities.SESECDTagConfig;
import com.supcon.orchid.SESECD.daos.SESECDTagConfigDao;
import com.supcon.orchid.SESECD.services.SESECDTagConfigService;
import com.supcon.orchid.SESECD.entities.SESECDVoiceConfig;
import com.supcon.orchid.SESECD.daos.SESECDVoiceConfigDao;
import com.supcon.orchid.SESECD.services.SESECDVoiceConfigService;
import com.supcon.orchid.SESECD.entities.SESECDVoiceMember;
import com.supcon.orchid.SESECD.daos.SESECDVoiceMemberDao;
import com.supcon.orchid.SESECD.services.SESECDVoiceMemberService;
import com.supcon.orchid.utils.BapCodeGenerator;
import com.supcon.orchid.services.Page;
import com.supcon.orchid.tree.PrepareData;
import com.supcon.orchid.services.QueryEntity;
import com.supcon.orchid.workflow.engine.services.ProcessService;
import com.supcon.orchid.workflow.engine.services.TaskService;
import com.supcon.orchid.workflow.engine.entities.Pending;
import com.supcon.orchid.tree.Tree;
import com.supcon.orchid.tree.TreeDao;
import javax.annotation.Resource;
import javax.persistence.Table;
import com.supcon.orchid.ec.entities.Field;
import com.supcon.orchid.ec.entities.Model;
import com.supcon.orchid.ec.entities.Module;
import com.supcon.orchid.ec.entities.Property;
import com.supcon.orchid.ec.services.IBAPBaseService;
import com.supcon.orchid.ec.services.ModelServiceFoundation;
import com.supcon.orchid.ec.enums.RegionType;
import com.supcon.orchid.utils.SerializeUitls;
import com.supcon.orchid.utils.SpringUtil;
import com.supcon.orchid.SESECD.daos.impl.SESECDEcdCommomDaoImpl;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.query.NativeQuery;
import org.hibernate.jdbc.Work;
import com.supcon.orchid.audit.service.DataAuditLogService;
import com.supcon.orchid.audit.entities.SignatureLog;
import com.supcon.orchid.workflow.engine.services.TransitionService;
import com.supcon.orchid.foundation.services.ReliableMessageSenderService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.services.BAPMneCodeService;
import com.supcon.orchid.ec.utils.JSONUtil;
import com.supcon.orchid.ec.services.FieldService;
import com.supcon.orchid.ec.services.MsModuleRelationService;
import com.supcon.orchid.SESECD.client.SESECDMapCenterClient;
import com.supcon.orchid.ec.enums.FieldType;
import com.supcon.orchid.ec.enums.DbColumnType;
import com.supcon.supfusion.framework.scaffold.auditlog.strategy.AuditLogStrategy;
import com.supcon.supfusion.framework.scaffold.auditlog.pojo.bo.AuditDataLogBO;
import com.supcon.supfusion.framework.scaffold.auditlog.constant.OperateType;
import com.supcon.supfusion.framework.scaffold.auditlog.constant.AuditLogType;
import com.supcon.supfusion.framework.scaffold.auditlog.cache.ModelAuditLogCache;
/* CUSTOM CODE START(serviceimpl,import,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@Service("sESECD_ecdCommomService")
@ClassSystemConfigAnno
@Transactional
public class SESECDEcdCommomServiceImpl  extends BaseServiceImpl<SESECDEcdCommom> implements SESECDEcdCommomService, InitializingBean, DisposableBean {
	private static final Pattern pattern = Pattern.compile("( AS | as )((\"?)(.*?)(\"?))(,| )");
	private static final Pattern p = Pattern.compile("( JOIN | join )((.*?)) ((\"?)(.*?)(\"?))( ON | on )");
	private static final Pattern countPattern = Pattern.compile("(SUM\\()((\"?)(.*?)(\"?))(\\) AS | as )");
	private static final Pattern sumPattern = Pattern.compile("(SUM\\()((\"?)(.*?)(\"?))(\\))");
	private static Pattern oneToManyPattern = Pattern.compile("(\"value\":\")(\\$\\$.*?\\$\\$)(\")");
	private static Pattern p4 = Pattern.compile("\\$\\{(.+?),(.+?)\\}");
	private static Pattern p5 = Pattern.compile("\\$\\{(.*?)\\}");
	@Autowired
	private SESECDEcdCommomService self;
	@Autowired
	ReliableMessageSenderService  reliableMessageSenderService;//消息发送服务
	@Autowired
	private SqlService sqlService;
	@Autowired
	BapCodeGenerator bapCodeGenerator;
	@Autowired
	ScriptService scriptService;
	@Autowired
	private DataAuditLogService dataAuditLogService;
	
	@Autowired
	private DocumentService documentService;
	@Autowired
	private SESECDAllEmerMemberDao allEmerMemberDao;
	@Autowired
	private SESECDAllEmerMemberService allEmerMemberService;
	@Autowired
	private SESECDEmerExpertsDao emerExpertsDao;
	@Autowired
	private SESECDEmerExpertsService emerExpertsService;
	@Autowired
	private SESECDEmerMembersDao emerMembersDao;
	@Autowired
	private SESECDEmerMembersService emerMembersService;
	@Autowired
	private SESECDAccidentDao accidentDao;
	@Autowired
	private SESECDAccidentService accidentService;
	@Autowired
	private SESECDActionOwnersDao actionOwnersDao;
	@Autowired
	private SESECDActionOwnersService actionOwnersService;
	@Autowired
	private SESECDAlarmActCameraDao alarmActCameraDao;
	@Autowired
	private SESECDAlarmActCameraService alarmActCameraService;
	@Autowired
	private SESECDAlarmActionDao alarmActionDao;
	@Autowired
	private SESECDAlarmActionService alarmActionService;
	@Autowired
	private SESECDAlarmEnenetrelDao alarmEnenetrelDao;
	@Autowired
	private SESECDAlarmEnenetrelService alarmEnenetrelService;
	@Autowired
	private SESECDAlmAlarmRecordDao almAlarmRecordDao;
	@Autowired
	private SESECDAlmAlarmRecordService almAlarmRecordService;
	@Autowired
	private SESECDCctvRecordDao cctvRecordDao;
	@Autowired
	private SESECDCctvRecordService cctvRecordService;
	@Autowired
	private SESECDCommunicationDao communicationDao;
	@Autowired
	private SESECDCommunicationService communicationService;
	@Autowired
	private SESECDEmePlanObjDao emePlanObjDao;
	@Autowired
	private SESECDEmePlanObjService emePlanObjService;
	@Autowired
	private SESECDMesPersonDao mesPersonDao;
	@Autowired
	private SESECDMesPersonService mesPersonService;
	@Autowired
	private SESECDRecordActionDao recordActionDao;
	@Autowired
	private SESECDRecordActionService recordActionService;
	@Autowired
	private SESECDRecorSituationDao recorSituationDao;
	@Autowired
	private SESECDRecorSituationService recorSituationService;
	@Autowired
	private SESECDAlertRecordDao alertRecordDao;
	@Autowired
	private SESECDAlertRecordService alertRecordService;
	@Autowired
	private SESECDBroadcastInfoDao broadcastInfoDao;
	@Autowired
	private SESECDBroadcastInfoService broadcastInfoService;
	@Autowired
	private SESECDChangeLogDao changeLogDao;
	@Autowired
	private SESECDChangeLogService changeLogService;
	@Autowired
	private SESECDDispatConfigDao dispatConfigDao;
	@Autowired
	private SESECDDispatConfigService dispatConfigService;
	@Autowired
	private SESECDEntranceInfoDao entranceInfoDao;
	@Autowired
	private SESECDEntranceInfoService entranceInfoService;
	@Autowired
	private SESECDEcdAlertImgDao ecdAlertImgDao;
	@Autowired
	private SESECDEcdAlertImgService ecdAlertImgService;
	@Autowired
	private SESECDEcdAlertRecordDao ecdAlertRecordDao;
	@Autowired
	private SESECDEcdAlertRecordService ecdAlertRecordService;
	@Autowired
	private SESECDEcdAlertVideoDao ecdAlertVideoDao;
	@Autowired
	private SESECDEcdAlertVideoService ecdAlertVideoService;
	@Autowired
	private SESECDEcdActionDao ecdActionDao;
	@Autowired
	private SESECDEcdActionService ecdActionService;
	@Autowired
	private SESECDEcdCommomDao ecdCommomDao;
	@Autowired
	private SESECDEcdStatiusDao ecdStatiusDao;
	@Autowired
	private SESECDEcdStatiusService ecdStatiusService;
	@Autowired
	private SESECDActVideoCameraDao actVideoCameraDao;
	@Autowired
	private SESECDActVideoCameraService actVideoCameraService;
	@Autowired
	private SESECDEmcActionDao emcActionDao;
	@Autowired
	private SESECDEmcActionService emcActionService;
	@Autowired
	private SESECDMainDepartmentDao mainDepartmentDao;
	@Autowired
	private SESECDMainDepartmentService mainDepartmentService;
	@Autowired
	private SESECDMainPeopleDao mainPeopleDao;
	@Autowired
	private SESECDMainPeopleService mainPeopleService;
	@Autowired
	private SESECDEmcSituationDao emcSituationDao;
	@Autowired
	private SESECDEmcSituationService emcSituationService;
	@Autowired
	private SESECDEmEventLeveLDao emEventLeveLDao;
	@Autowired
	private SESECDEmEventLeveLService emEventLeveLService;
	@Autowired
	private SESECDEmEventTypeDao emEventTypeDao;
	@Autowired
	private SESECDEmEventTypeService emEventTypeService;
	@Autowired
	private SESECDEventDescribeDao eventDescribeDao;
	@Autowired
	private SESECDEventDescribeService eventDescribeService;
	@Autowired
	private SESECDEcdParamConfigDao ecdParamConfigDao;
	@Autowired
	private SESECDEcdParamConfigService ecdParamConfigService;
	@Autowired
	private SESECDParamOptionDao paramOptionDao;
	@Autowired
	private SESECDParamOptionService paramOptionService;
	@Autowired
	private SESECDSignalConfigDao signalConfigDao;
	@Autowired
	private SESECDSignalConfigService signalConfigService;
	@Autowired
	private SESECDSourceTerminalDao sourceTerminalDao;
	@Autowired
	private SESECDSourceTerminalService sourceTerminalService;
	@Autowired
	private SESECDSentenceDao sentenceDao;
	@Autowired
	private SESECDSentenceService sentenceService;
	@Autowired
	private SESECDTagConfigDao tagConfigDao;
	@Autowired
	private SESECDTagConfigService tagConfigService;
	@Autowired
	private SESECDVoiceConfigDao voiceConfigDao;
	@Autowired
	private SESECDVoiceConfigService voiceConfigService;
	@Autowired
	private SESECDVoiceMemberDao voiceMemberDao;
	@Autowired
	private SESECDVoiceMemberService voiceMemberService;
	@Autowired
	private DataPermissionService dataPermissionService;
	@Autowired
	private CounterManager counterManager;
	@Autowired
	private ConditionService conditionService;
	@Autowired
	private ViewServiceFoundation viewServiceFoundation;
	private Counter counter;
	@Autowired
	private CreatorService creatorService;
	@Autowired
	private SynchronizeInfoService synchronizeInfoService;
	@Autowired
	private ModelServiceFoundation modelServiceFoundation;
	@Autowired
	private EntityServiceFoundation entityServiceFoundation;
	@Autowired
	private SESECDEcdCommomImportService ecdCommomImportService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private BAPMneCodeService bapMneCodeService;

    @Autowired
    private SESECDMapCenterClient sESECDMapCenterClient;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private MsModuleRelationService msModuleRelationService;
    @Autowired
    private AuditLogStrategy<AuditDataLogBO> auditDataLogStrategy;
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${project.staticPath}")
    private String greendillProjectStaticPath;

    private static ThreadLocal<String> cidTcl = new ThreadLocal<>();

	private static ThreadLocal<Map<String, String>> stateInfoLocal = new ThreadLocal<>();

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
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void listQuery(Page<SESECDEcdCommom> page, int currentSqlType, String viewCode, String datagridCode, Boolean crossCompanyFlag,
			String processKey, String entityCode, String fastQueryCond, String advQueryCond, String classifyCodes,
			String dataTableSortColKey, String dataTableSortColName, String dataTableSortColOrder, Long mainBusinessId,
			String split, Object[] searchObjects, String tableProcessKey, String permissionCode, Boolean flowBulkFlag, Boolean noQueryFlag, String exportSql,
			Integer findExportDataInfosCount, Map<String,String> dynamicFieldsMap, QueryEntity queryEntity) {
		View listView = viewServiceFoundation.getView(viewCode);
		List<Param> params = new ArrayList<Param>();
		if (null != crossCompanyFlag) {
			params.add(new Param("crossCompanyFlag", crossCompanyFlag, Param.EQUAL_LIKELEFT, "BOOLEAN"));
		}
		params.add(new Param("\"ecdCommom\".VALID", true, Param.LIKE_UNSUPPORT, "BOOLEAN"));
		if (null != advQueryCond && advQueryCond.trim().length() > 0) {
			params.add(new Param("advQueryCond", advQueryCond, Param.LIKE_ALL));
		}
		if (null != classifyCodes && classifyCodes.trim().length() > 0) {
			params.add(new Param("classifyCodes", classifyCodes));
		}
		if (null != dataTableSortColKey && dataTableSortColKey.length() > 0) {
		    if(StringUtils.equals("cidName", dataTableSortColKey)){
                dataTableSortColKey = "cid";
                dataTableSortColName = "cid";
                queryEntity.setDataTableSortColKey("cid");
                queryEntity.setDataTableSortColName("cid");
            }
			String sortColKey = dataTableSortColKey;
			if (null != dataTableSortColName && dataTableSortColName.length() > 0) {
				sortColKey += "::" + dataTableSortColName;
			}
			params.add(new Param("dataTable-sortColKey", sortColKey));
		}
		if (null != dataTableSortColOrder && dataTableSortColOrder.length() > 0) {
			params.add(new Param("dataTable-sortColOrder", dataTableSortColOrder));
		}
		stateInfoLocal.remove();
		if (!(null != advQueryCond && advQueryCond.trim().length() > 0)) {
			if (null != fastQueryCond && fastQueryCond.trim().length() > 0) {
			    //---------------------------fastQueryCond改造--------------------------
            	fastQueryCond = getFastQueryCondCidStr(fastQueryCond, queryEntity);
				if(!StringUtils.isEmpty(listView.getCode()) && listView.getCode().indexOf('_')>0
						&& !StringUtils.isEmpty(listView.getCode().substring(0,listView.getCode().indexOf('_')))){
					String moduleCode = listView.getCode().substring(0,listView.getCode().indexOf('_'));
					fastQueryCond = getFastQueryCondPendState(fastQueryCond, queryEntity, Sql.TYPE_LIST_QUERY, moduleCode);
				}else{
					fastQueryCond = getFastQueryCondPendState(fastQueryCond, queryEntity, Sql.TYPE_LIST_QUERY, null);
				}
				params.add(new Param("fastQueryCond", fastQueryCond, Param.LIKE_ALL));
			}
		}

		Model assmodel = viewServiceFoundation.getAssModelFromView(listView.getCode());
		Set<Property> properties = modelServiceFoundation.findProperties(assmodel.getCode());
		Map<String, String> fieldMap = new HashMap<String, String>();
		for (Property p : properties) {
			fieldMap.put(p.getName(), p.getColumnName());
		}
		/// TODO 测试树形参数
		if(listView.getUsedForTree()&&listView.getAssTreeModelCode()!=null){
			if (null != dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".id") && dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".id").trim().length() > 0) {
				params.add(new Param("\"tree-id-"+listView.getAssTreeModelCode()+"\"."+fieldMap.get("id"),dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".id"),Param.LIKE_UNSUPPORT,"LONG"));
			}
			if (null != dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layRec") && dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layRec").trim().length() > 0) {
				if(listView.getIncludeChildren()){
					params.add(new Param("\"tree-layRec-"+listView.getAssTreeModelCode()+"\"."+fieldMap.get("layRec"),dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layRec"),Param.EQUAL_LIKELEFT));
				}else{
					params.add(new Param("\"tree-layRec-"+listView.getAssTreeModelCode()+"\"."+fieldMap.get("layRec"),dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layRec"),Param.LIKE_UNSUPPORT));
				}
			}
			if (null != dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layNo") && dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layNo").trim().length() > 0) {
				params.add(new Param("\"tree-layNo-"+listView.getAssTreeModelCode()+"\"."+fieldMap.get("layNo"),dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".layNo"),Param.LIKE_UNSUPPORT,"LONG"));
			}
			if (null != dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".parentId") && dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".parentId").trim().length() > 0) {
				params.add(new Param("\"tree-"+listView.getAssTreeModelCode()+"\"."+Inflector.getInstance().columnize("parentId"),dynamicFieldsMap.get("tree-"+listView.getAssTreeModelCode()+".parentId"),Param.LIKE_UNSUPPORT,"LONG"));
			}
		}


		if (assmodel.getDataType() == 2) {
			if (null != dynamicFieldsMap.get(fLTL(assmodel.getModelName())+".layRec") && dynamicFieldsMap.get(fLTL(assmodel.getModelName())+".layRec").trim().length() > 0) {
				params.add(new Param("\"" + fLTL(assmodel.getModelName()) + "\"."
						+ fieldMap.get("layRec"), dynamicFieldsMap.get(fLTL(assmodel.getModelName())+".layRec"), Param.EQUAL_LIKELEFT));
			}
		}
		if (currentSqlType == Sql.TYPE_LIST_REFERENCE) {
			Map confMap=null;
			if(listView.getIsShadow()){
				View shadowView = viewServiceFoundation.getShadowViewFromView(listView.getCode());
				confMap=viewServiceFoundation.getExtraViewFromView(shadowView.getCode()).getConfigMap();
			}else{
				confMap=viewServiceFoundation.getExtraViewFromView(listView.getCode()).getConfigMap();
			}
			if(confMap!=null){
				Map listProperty=(Map)confMap.get("listProperty");
				if(listProperty!=null&&listProperty.get("isTransCondition")!=null&&listProperty.get("isTransCondition").equals("true")){
					if(listProperty.get("conditionContent")!=null&&!listProperty.get("conditionContent").equals("")){
						params.add(new Param("referenceCondition",listProperty.get("conditionContent").toString(), Param.LIKE_ALL));
					}
				}
			}
		}
		if (datagridCode != null && !"".equals(datagridCode)) {
			generateCustomerConditionByDg(params, datagridCode, split, queryEntity);
		} else {
			generateCustomerCondition(params, viewCode, split, queryEntity);
		}
		generateClassificCondition(params, classifyCodes, split, queryEntity);
		int validPosition = 0; // 出现valid字段的下标
		for (int i = 0; i < params.size(); i++) {
			Object param = params.get(i);
			if (param.toString().indexOf("\"ecdCommom\".VALID = true") != -1) {
				validPosition = i;
			}
			if (param.toString().indexOf("\"columnName\":\"VALID\"") > 0
					|| param.toString().indexOf("\"ecdCommom\".VALID = ?") > 0) {
				params.set(validPosition, new Param("1", 1, Param.LIKE_UNSUPPORT, "int"));
				break;
			}
		}
		findExportDataInfos(page, viewCode, datagridCode, searchObjects, currentSqlType,
				tableProcessKey, flowBulkFlag, listView.getHasAttachment(), params, permissionCode, noQueryFlag,
				exportSql, findExportDataInfosCount,queryEntity);
	}


	/**
	 * controller直接调用的编辑视图提交
	 *
	 * @param ecdCommom
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
	@Override
	public Map<String, Object> submit(SESECDEcdCommom ecdCommom, WorkFlowVar workFlowVar, Long pendingId,
			Long deploymentId, String operateType, String pendingActivityType, Boolean webSignetFlag,
			SignatureLog signatureLog, Map<String, Object> fileuploads, Boolean superEdit, Map<String, String> dgLists,
			Map<String, String> dgDeletedIds, String viewCode, String activityName, Locale locale, String viewSelect) {
		// 将ecdCommom转为游离态，避免保存前查询时version重置为数据库的version，导致乐观锁失效
		ecdCommomDao.evict(ecdCommom);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (null == ecdCommom.getId()){
			responseMap.put("operate", "add");
		}else{
			responseMap.put("operate", "edit");
		}
		self.saveEcdCommom(ecdCommom,dgLists,dgDeletedIds,fileuploads ,viewCode,null,signatureLog);
		responseMap.put("dealSuccessFlag", true);
		responseMap.put("operateType", "save");
		responseMap.put("viewselect", viewSelect);
		responseMap.put("tableInfoId", ecdCommom.getTableInfoId());
		responseMap.put("id", ecdCommom.getId());
		return responseMap;
	}

	/**
	 * controller直接调用的获取datagrid
	 *
	 * @param dgPage
	 * @param datagridCode
	 * @param dataGridName
	 * @param parametersrequest的传入参数
	 * @param refId
	 * @param ecdCommom
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void dataGridData(Page dgPage, String datagridCode, String dataGridName, Map<String, String[]> parameters,
			Long refId, Long id, QueryEntity queryEntity) throws Exception {
		DataGrid dg = null;
		if (!com.supcon.orchid.utils.StringUtils.isEmpty(datagridCode)) {
			dg = viewServiceFoundation.getDataGrid(datagridCode);
		} else {
			dg = viewServiceFoundation.getDataGridByName(dataGridName, "runtime");
			datagridCode = dg.getCode();
		}
		if (null == dg) {
			throw new BAPException("未找到DataGrid" + datagridCode);
		}
		Model model = viewServiceFoundation.getTargetModelFromDg(datagridCode);
		if (refId != null && refId > 0) {
			// dataGridRefCopy(dg);
		} else {
//			setFieldPermissionModelCode(model.getCode());
			String searchCondition = "";
			List<Object> params = new ArrayList<Object>();
			CustomerCondition ccon = viewServiceFoundation.findCustomerConditionByDatagridCode(datagridCode);
			if (null != ccon && ccon.getSql() != null && ccon.getSql().length() > 0) {
				String customerSql = ccon.getSql();
				if (customerSql.indexOf("return") > -1) {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 把queryEntity转为map
                    Map<String, Object> parameters1 = objectMapper.convertValue(queryEntity, Map.class);
                    for (String key : parameters1.keySet()) {
                        variables.put(key, parameters1.get(key));
                    }
                    variables.put("parameters", parameters1);
                    customerSql = EngineScriptExecutor.eval(customerSql, variables).toString();
                }
				Pattern p = Pattern.compile("\\$\\{(.+?),(.+?)\\}");
				Matcher m = p.matcher(customerSql);
				List<Object> list = new ArrayList<Object>();
				while (m.find()) {
					String str = m.group();
					String value = String.valueOf(queryEntity.getCustomCondition().get(m.group(1).toString()));
					String type = m.group(2);
					if ("int".equals(type)) {
						Integer integer = new Integer(value);
						list.add(integer);
					} else if ("date".equals(type)) {
						Date date = new Date(value);
						list.add(date);
					} else if ("double".equals(type)) {
						Double doubles = new Double(value);
						list.add(doubles);
					} else if ("long".equals(type)) {
						Long longs = new Long(value);
						list.add(longs);
					} else if ("string".equals(type)) {
						list.add(value);
					/*} else if ("method".equals(type)) {
						value = m.group(1);
						try {
							list.add(Ognl.getValue(value, this));
						} catch (Exception e) {
							log.info(e.getMessage());
						}*/
					} else {
						list.add(value);
					}
					customerSql = customerSql.replace(str, "?");
				}
				searchCondition += customerSql;
				params.addAll(list);
			} else if (null != ccon && ccon.getJsonCondition() != null && ccon.getJsonCondition().length() > 0) {
				String jsonString = ccon.getJsonCondition();
				Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
				Matcher m = p.matcher(jsonString);
				while (m.find()) {
					String str = m.group();
					String value = parameters.get(m.group(1))[0];
					if (value != null) {
						jsonString = jsonString.replace(str, value);
					} else {
						jsonString = jsonString.replace(str, "");
					}
				}
				AdvQueryCondition advQuery = conditionService.toSql(jsonString);
				String s = advQuery.getSql();
				if (advQuery.getSql() != null) {
					searchCondition += advQuery.getSql();
				}
				params.addAll(advQuery.getValues());
			}
			Entity entity  = modelServiceFoundation.findEntityFromModel(model.getCode());
			Module module = entityServiceFoundation.getModuleFromEntity(entity.getCode());
			String artifact = module.getArtifact();
			Class dgclass = Class.forName("com.supcon.orchid." + artifact + ".entities." + model.getJpaName());
			SESECDEcdCommom ecdCommom = new SESECDEcdCommom();
			if (id != null && id != -1) {
				ecdCommom = getEcdCommom(id);
			}else{
				ecdCommom.setId(-1L);
			}
			findDataGridPage(dg, dgclass, dgPage, ecdCommom, searchCondition, params);
			if (null != dgPage && null != dgPage.getResult() && !dgPage.getResult().isEmpty()) {
				List<String> attachKey = new ArrayList<String>();
				Map<String,String[]> refAttachKey = new HashMap<>();
				//List<String> picKey = new ArrayList<String>();
				Map confMap = dg.getConfigMap();
				Map layoutMap = (Map) confMap.get("layout");
				List<Map> ls = (List<Map>) layoutMap.get("sections");
				for (Map lm : ls) {
					if (lm.get("regionType").equals("DATAGRID")) {
						List<Map> lc = (List<Map>) lm.get("cells");
						for (Map lk : lc) {
						    if(( "PROPERTYATTACHMENT".equals(lk.get("columnType")) ||  "PICTURE".equals(lk.get("columnType")) ||  "SIGNATURE".equals(lk.get("columnType")) ) && lk.get("key")!=null){
								if(lk.get("referenceview")!=null || lk.get("key").toString().contains(".")) {
								    String[] data = lk.get("key").toString().split("\\.");
                                	refAttachKey.put(lk.get("key").toString(), data);
                                }
								attachKey.add(lk.get("key").toString());
							}

						}
					}
				}

				if (attachKey.size() > 0) {
                    //准备批量查询所需数据
					String docType = dgclass.getDeclaredField("DOC_TYPE").get(null).toString();
					List<Long> idList = new ArrayList<>();
					Map<String, List<Long>> TypeWithLinkIdMap = new HashMap<>();
				    for (Object item : dgPage.getResult()) {
						idList.add((Long) dgclass.getMethod("getId").invoke(item));
                        //处理参照附件，目前仅支持两层
						if (refAttachKey.size() > 0) {
							for (String[] data : refAttachKey.values()) {
								String key = data[0];
								Object refObject = dgclass.getMethod("get" + fLTU(key)).invoke(item);
								if(refObject == null){
								   continue;
								}
								Class refClass = refObject.getClass();
								if (refClass.getName().equals(Staff.class.getName())) {
									continue;
								}
								Long linkId = (Long) refClass.getMethod("getId").invoke(refObject);
								String documentType = refClass.getDeclaredField("DOC_TYPE").get(null).toString();
								if (TypeWithLinkIdMap.containsKey(documentType)) {
									List<Long> linkIds = TypeWithLinkIdMap.get(documentType);
									linkIds.add(linkId);
									TypeWithLinkIdMap.replace(documentType, linkIds);
								} else {
									List<Long> linkIdList = new ArrayList<>();
									linkIdList.add(linkId);
									TypeWithLinkIdMap.put(documentType, linkIdList);
								}
							}
						}

					}
					Map<Long, Map<String, List<Document>>> docMap = documentService.getByLinkIdAndTypeAndPropertyCode(idList, docType, null);
					HashMap<String, Map<Long, Map<String, List<Document>>>> refDocMap = new HashMap<>();
					if(!ObjectUtils.isEmpty(TypeWithLinkIdMap)){
                        TypeWithLinkIdMap.forEach((key, value) -> {
                            Map<Long, Map<String, List<Document>>> documentMap = documentService.getByLinkIdAndTypeAndPropertyCode(value, key, null);
                            refDocMap.put(key,documentMap);
                        });
					}

					//准备批量查询所需创建人数据 (staffid -> List<staff>)
					List<Long> createStaffIds = new ArrayList<>();
					if(!ObjectUtils.isEmpty(docMap)){
					    for (Map<String, List<Document>> value : docMap.values()) {
                    		 for (List<Document> documents : value.values()) {
                    		     List<Long> creatids = documents.stream().map(Document::getCreateStaffId).collect(Collectors.toList());
                    			 createStaffIds.addAll(creatids);
                    		}
                    	}
					}

					List<Long> ids = createStaffIds.stream().distinct().collect(Collectors.toList());
					Map<Long, List<Staff>> staffMap = null;
					if(null!= ids && ids.size()>0){
						List<Staff> staffs = staffService.findByCriteria(Restrictions.in("user.id", ids), Restrictions.eq("valid", true));
						staffMap = staffs.stream().collect(Collectors.groupingBy(Staff::getId));
					}
					Map<String,List<Long>> paramFileIds=new HashMap<String, List<Long>>();
                    Map<String,List<String>> paramFileNames=new HashMap<String, List<String>>();
                    Map<String,List<String>> paramFileIcons=new HashMap<String, List<String>>();
                    Map<String,List<String>> paramFileUrls=new HashMap<String, List<String>>();
					Map<String,List<String>> paramFileCreators=new HashMap<String, List<String>>();
					Map<String,List<Date>> paramFileCreateTimes=new HashMap<String, List<Date>>();
					Map<String,List<String>> paramFileSizes=new HashMap<String, List<String>>();
					Map<String,List<String>> paramFilePreviewTimes=new HashMap<String, List<String>>();
					Map<String,List<String>> paramFileDownloadTimes=new HashMap<String, List<String>>();
					for (Object item : dgPage.getResult()) {
						if (!ObjectUtils.isEmpty(docMap) || !ObjectUtils.isEmpty(refDocMap)) {

							for (String key : attachKey) {
							    if (refAttachKey.get(key) != null){
                                  String[] data = refAttachKey.get(key);
							      key = data[0];
							      String property = data[data.length-1];
							      Object refObject = dgclass.getMethod("get" + fLTU(key)).invoke(item);
							      if(refObject == null){
                                  	 continue;
                                  }
							      Class refClass = refObject.getClass();
							      if(refClass.getName().equals(Staff.class.getName())){
                                  	continue;
                                  }
							      Long linkeId = (Long) refClass.getMethod("getId").invoke(refObject);
							      String documentType = refClass.getDeclaredField("DOC_TYPE").get(null).toString();
                                  if(refDocMap.containsKey(documentType)){
									  Map<Long, Map<String, List<Document>>> documentMap = refDocMap.get(documentType);
									  if(!ObjectUtils.isEmpty(documentMap) && documentMap.containsKey(linkeId)){
	                                      Map<String, List<Document>> refDocuments = documentMap.get(linkeId);
     									  List<Document> docList = refDocuments.get(refClass.getDeclaredField(property.toUpperCase() + "_PROPERTY_CODE").get(null));
     									  if (null != docList && docList.size()>0) {
     										  List<Long> refOrgIds = new ArrayList<>();
     										  List<String> refOrgNames = new ArrayList<>();
     										  List<String> refFileIcons = new ArrayList<>();
     										  List<String> refFileUrls = new ArrayList<>();
     										  List<String> refFileCreators = new ArrayList<>();
     										  List<Date> refFileCreateTimes = new ArrayList<>();
     										  List<String> refFileSizes = new ArrayList<>();
     										  List<String> refFilePreviewTimes = new ArrayList<>();
     										  List<String> refFileDownloadTimes =new ArrayList<>();
     										  for (Document doc : docList) {
     											  refOrgIds.add(doc.getId());
     											  refOrgNames.add(doc.getName());
     											  refFileIcons.add(DocumentUtils.getIcon(doc.getName()));
     											  refFileUrls.add(doc.getPath());
     											  if(null!=doc.getCreateStaffId()){
     												  if(null!= staffMap && null != staffMap.get(doc.getCreateStaffId()) && staffMap.get(doc.getCreateStaffId()).size()>0){
     													  refFileCreators.add(staffMap.get(doc.getCreateStaffId()).get(0).getName());
     												  }
     											  }
     											  refFileCreateTimes.add(doc.getCreateTime());
     											  refFileSizes.add(doc.getSizeDis());
     											  refFilePreviewTimes.add(String.valueOf(doc.getPreviewTimes()));
     											  refFileDownloadTimes.add(String.valueOf(doc.getDownloadTimes()));
     										  }
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiFileIds", List.class).invoke(refObject,refOrgIds);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiFileNames", List.class).invoke(refObject, refOrgNames);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiFileIcons", List.class).invoke(refObject, refFileIcons);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiFileUrls", List.class).invoke(refObject, refFileUrls);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiCreators", List.class).invoke(refObject, refFileCreators);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiCreateTimes", List.class).invoke(refObject, refFileCreateTimes);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiSizes", List.class).invoke(refObject, refFileSizes);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiPreviewTimes", List.class).invoke(refObject, refFilePreviewTimes);
     										  refClass.getDeclaredMethod("set" + fLTU(property) + "MultiDownloadTimes", List.class).invoke(refObject, refFileDownloadTimes);
     										  dgclass.getDeclaredMethod("set" + fLTU(key) , refClass).invoke(item, refObject);
                                           }
									  }

                                  }
							    } else {
                                     List<Long> orgIds = (List<Long>)dgclass.getMethod("get" + fLTU(key) + "MultiFileIds").invoke(item);
                                     List<String> orgNames = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiFileNames").invoke(item);
                                     List<String> fileIcons = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiFileIcons").invoke(item);
                                     List<String> fileUrls = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiFileUrls").invoke(item);
								     List<String> fileCreators = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiCreators").invoke(item);
								     List<Date> fileCreateTimes = (List<Date>)dgclass.getMethod("get" + fLTU(key) + "MultiCreateTimes").invoke(item);
								     List<String> fileSizes = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiSizes").invoke(item);
								     List<String> filePreviewTimes = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiPreviewTimes").invoke(item);
								     List<String> fileDownloadTimes = (List<String>)dgclass.getMethod("get" + fLTU(key) + "MultiDownloadTimes").invoke(item);

								if (orgIds == null) {
									paramFileIds.put(key + "MultiFileIds", new ArrayList<Long>());
								} else {
									paramFileIds.put(key + "MultiFileIds", orgIds);
								}
								if (orgNames == null) {
									paramFileNames.put(key + "MultiFileNames", new ArrayList<String>());
								} else {
									paramFileNames.put(key + "MultiFileNames", orgNames);
								}
								if (fileIcons == null) {
									paramFileIcons.put(key + "MultiFileIcons", new ArrayList<String>());
								} else {
									paramFileIcons.put(key + "MultiFileIcons", fileIcons);
								}
								if (fileUrls == null) {
                                	paramFileUrls.put(key + "MultiFileUrls", new ArrayList<String>());
                                } else {
                                	paramFileUrls.put(key + "MultiFileUrls", fileUrls);
                                }
                                if (fileCreators == null) {
									paramFileCreators.put(key + "MultiCreators", new ArrayList<String>());
								} else {
									paramFileCreators.put(key + "MultiCreators", fileCreators);
								}
								if (fileCreateTimes == null) {
									paramFileCreateTimes.put(key + "MultiCreateTimes", new ArrayList<Date>());
								} else {
									paramFileCreateTimes.put(key + "MultiCreateTimes", fileCreateTimes);
								}
								if (fileSizes == null) {
									paramFileSizes.put(key + "MultiSizes", new ArrayList<String>());
								} else {
									paramFileSizes.put(key + "MultiSizes", fileSizes);
								}
								if (filePreviewTimes == null) {
									paramFilePreviewTimes.put(key + "MultiPreviewTimes", new ArrayList<String>());
								} else {
									paramFilePreviewTimes.put(key + "MultiPreviewTimes", filePreviewTimes);
								}
								if (fileDownloadTimes == null) {
									paramFileDownloadTimes.put(key + "MultiDownloadTimes", new ArrayList<String>());
								} else {
									paramFileDownloadTimes.put(key + "MultiDownloadTimes", fileDownloadTimes);
								}
								}
							}

                            if(!ObjectUtils.isEmpty(docMap)){
                               Map<String, List<Document>> documents = docMap.get((Long) dgclass.getMethod("getId").invoke(item));
                               for (String key : attachKey) {
   								   if (refAttachKey.get(key) != null){
                                   	continue;
                                   }
                                   if(!ObjectUtils.isEmpty(documents)){
                                       List<Document> docList = documents.get(dgclass.getDeclaredField(key.toUpperCase() + "_PROPERTY_CODE").get(null));
                                       if (null != docList && docList.size()>0) {
                                               for (Document doc : docList) {
                                                   paramFileIds.get(key+"MultiFileIds").add(doc.getId());
                                                   paramFileNames.get(key + "MultiFileNames").add(doc.getName());
                                                   paramFileIcons.get(key + "MultiFileIcons").add(DocumentUtils.getIcon(doc.getName()));
                                                   paramFileUrls.get(key + "MultiFileUrls").add(doc.getPath());
       											if(null!=doc.getCreateStaffId()){
       												if(null!= staffMap && null != staffMap.get(doc.getCreateStaffId()) && staffMap.get(doc.getCreateStaffId()).size()>0){
       													paramFileCreators.get(key+"MultiCreators").add(staffMap.get(doc.getCreateStaffId()).get(0).getName());
       												}
       											}
       											paramFileCreateTimes.get(key+"MultiCreateTimes").add(doc.getCreateTime());
       											paramFileSizes.get(key+"MultiSizes").add(doc.getSizeDis());
       											paramFilePreviewTimes.get(key+"MultiPreviewTimes").add(String.valueOf(doc.getPreviewTimes()));
       											paramFileDownloadTimes.get(key+"MultiDownloadTimes").add(String.valueOf(doc.getDownloadTimes()));
       										}
       								   }
                                   }

   							    }

   	                           for (String key : attachKey) {
    							    if (refAttachKey.get(key) != null){
                                		continue;
                                	}
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiFileIds", List.class).invoke(item,paramFileIds.get(key + "MultiFileIds"));
                                    dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiFileNames", List.class).invoke(item, paramFileNames.get(key + "MultiFileNames"));
                                    dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiFileIcons", List.class).invoke(item, paramFileIcons.get(key + "MultiFileIcons"));
                                    dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiFileUrls", List.class).invoke(item, paramFileUrls.get(key + "MultiFileUrls"));
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiCreators", List.class).invoke(item, paramFileCreators.get(key + "MultiCreators"));
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiCreateTimes", List.class).invoke(item, paramFileCreateTimes.get(key + "MultiCreateTimes"));
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiSizes", List.class).invoke(item, paramFileSizes.get(key + "MultiSizes"));
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiPreviewTimes", List.class).invoke(item, paramFilePreviewTimes.get(key + "MultiPreviewTimes"));
    								dgclass.getDeclaredMethod("set" + fLTU(key) + "MultiDownloadTimes", List.class).invoke(item, paramFileDownloadTimes.get(key + "MultiDownloadTimes"));
    							}
                            }
						}
					}
				}
			}
		}
	}

	/**
	 * 根据主显示字段列表获取Map
	 * @param mainDisplayKeys 主显示字段
	 * @return Map<String,String> key：count  value：数据数量；  key：idMap  value：id
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> getMainDisplayMap(Serializable mainDisplayName, Serializable businessKeyName,List<Serializable> mainDisplayKeys) {
		String hql = "select " + mainDisplayName + ",id,"+ businessKeyName + " from " + SESECDEcdCommom.JPA_NAME + " where " + mainDisplayName + " in (:mainDisplayKeys)"  + "and VALID = 1";
		Query query =  ecdCommomDao.createQuery(hql);
		query.setParameterList("mainDisplayKeys", mainDisplayKeys);
		List<Object[]> list = query.list();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("count", list.size());
		Map<Serializable, Serializable> map = new HashMap<Serializable, Serializable>();
		if (list != null && !list.isEmpty()) {
			for (Object[] objs : list) {
				map.put((Serializable) (String.valueOf(objs[0])), (Serializable) objs[1]);
				map.put(businessKeyName, (Serializable) objs[2]);
			}
		}
		m.put("idMap", map);
		return m;
	}
	/**
	 * 根据业务主键列表获取Map
	 * @param businessKeys 业务主键列表
	 * @return Map<String,String> key：businessKey  value：id
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<Serializable,Serializable> getBusinessKeyMap(Serializable businessKeyName,List<Serializable> businessKeys){
		if(businessKeyName!=null && String.valueOf(businessKeyName).length() > 0){
			String hql = "select " + businessKeyName + ",id from " + SESECDEcdCommom.JPA_NAME + " where " + businessKeyName + " in (:businessKeys)";
			Query query = ecdCommomDao.createQuery(hql);
			query.setParameterList("businessKeys", businessKeys);
			List<Object[]> list = query.list();
			Map<Serializable,Serializable> map = new HashMap<Serializable,Serializable>();
			if(list!=null && !list.isEmpty()){
				for (Object[] objs : list) {
					map.put((Serializable)(String.valueOf(objs[0])), (Serializable)objs[1]);
				}
			}
			return map;
		}
		return null;

	}

	/**
	 * 获取父节点对象
	*/
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<String, Object> getParentNodeMap(Serializable mainDisplayName, Serializable businessKeyName, List<Serializable> serial, String queryParam) {
		String hql = null;
		if(queryParam!=null && queryParam.equals("md")){
			hql = "select " + mainDisplayName + ",id,"+ businessKeyName + ",fullPathName,layNo,layRec,parentId,leaf from " + SESECDEcdCommom.JPA_NAME + " where " + mainDisplayName + " in (:serial)"+" and valid = 1";
		}else if(queryParam!=null && queryParam.equals("bk")){
			hql = "select " + mainDisplayName + ",id,"+ businessKeyName + ",fullPathName,layNo,layRec,parentId,leaf from " + SESECDEcdCommom.JPA_NAME + " where " + businessKeyName + " in (:serial)"+" and valid = 1";
		}
		Query query =  ecdCommomDao.createQuery(hql);
		query.setParameterList("serial", serial);
		List<Object[]> list = query.list();
		/*String sql = null;
		if(queryParam!=null && queryParam.equals("md")){
			sql = "select * from " + SESECDEcdCommom.TABLE_NAME + " where " + mainDisplayName + " = ?";
		}else if(queryParam!=null && queryParam.equals("bk")){
			sql = "select * from " + SESECDEcdCommom.TABLE_NAME + " where " + businessKeyName + " = ?";
		}
		List<SESECDEcdCommom> list = (List<SESECDEcdCommom>) ecdCommomDao.createNativeQuery(sql,serial.get(0)).list();
		*/
		Map<String, Object> m = new HashMap<String, Object>();
		if(queryParam!=null && queryParam.equals("md")){
			m.put("count", list.size());
		}
		if (list != null && !list.isEmpty()) {
			for (Object[] obj : list) {
				m.put(serial.get(0).toString(), obj);
			}
		}

		return m;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SESECDEcdCommom getEcdCommom(long id){
		return getEcdCommom(id, null);
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SESECDEcdCommom getEcdCommom(long id, String viewCode){
		SESECDEcdCommom ecdCommom = ecdCommomDao.load(id);

		// 一对多情况处理
		if(ecdCommom != null){
		}
		return ecdCommom;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SESECDEcdCommom getEcdCommomWithoutMultiselect(long id){
		SESECDEcdCommom ecdCommom = ecdCommomDao.load(id);
		return ecdCommom;
	}
	
	@Override
	public Map<String, Long> getCreateInfoMap(long id) {
		Map<String, Long> resultMap = new HashMap<>();
		String sql = "select CREATE_DEPARTMENT_ID,CREATE_POSITION_ID,CREATE_STAFF_ID from " + SESECDEcdCommom.TABLE_NAME + " where id = ?1";
		Object[] r = (Object[]) ecdCommomDao.createNativeQuery(sql, id).uniqueResult();
		if (null != r[0]) {
			resultMap.put("createDepartmentId", ((Number) r[0]).longValue());
		}
		if (null != r[1]) {
			resultMap.put("createPositionId", ((Number) r[1]).longValue());
		}
		if (null != r[2]) {
			resultMap.put("createStaffId", ((Number) r[2]).longValue());
		}
		return resultMap;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String getEcdCommomAsJSON(long id, String include){
		SESECDEcdCommom ecdCommom = ecdCommomDao.load(id);
		// 一对多情况处理

		if(ecdCommom == null) {
			return "";
		}
		return JSONPlainSerializer.serializeAsJSON(ecdCommom, include, new BAPEntityTransformer());
	}


	@Override
	public void deleteEcdCommom(SESECDEcdCommom ecdCommom){
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		beforeDeleteEcdCommom(ecdCommom);
		ecdCommomDao.deletePhysical(ecdCommom);
		afterDeleteEcdCommom(ecdCommom);
		ecdCommomDao.flush();
		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		params.add(ecdCommom);
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		props.put("eventType", "delete");
		String delIds = "";
		// 一对多情况处理
			// TODO delete
	}

	@Override
	public void deleteEcdCommom(List<Long> ecdCommomIds){
		deleteEcdCommom(ecdCommomIds, null);
	}

	@Override
	public void deleteEcdCommom(List<Long> ecdCommomIds, String eventTopic) {
		List<SESECDEcdCommom> ecdCommoms = new ArrayList<SESECDEcdCommom>();
		for(Long ecdCommomId : ecdCommomIds){
			SESECDEcdCommom ecdCommom = getEcdCommom(ecdCommomId);
			ecdCommoms.add(ecdCommom);
			if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
				if(!StringUtils.isEmpty(AuditUtil.getColumnStringA())){
					AuditUtil.setColumnStringA(AuditUtil.getColumnStringA() + (null == ecdCommom.getId() ? "" : "," + ecdCommom.getId().toString()));
				} else {
					AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
				}
				if(!StringUtils.isEmpty(AuditUtil.getColumnStringB())){
					AuditUtil.setColumnStringB(AuditUtil.getColumnStringB() + (null == ecdCommom.getId() ? "" : "," + ecdCommom.getId().toString()));
				} else {
					AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
				}
				if(!StringUtils.isEmpty(AuditUtil.getColumnStringC())){
					AuditUtil.setColumnStringC(AuditUtil.getColumnStringC() + (null == ecdCommom.getId() ? "" : "," + ecdCommom.getId().toString()));
				} else {
					AuditUtil.setColumnStringC(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
				}
			}
		}

		for(SESECDEcdCommom ecdCommom : ecdCommoms){
			beforeDeleteEcdCommom(ecdCommom);
		}

		/**
		 * 真删
		 * 增加SQL/HQL执行的数据日志记录
		 */
		if(ecdCommoms != null && ecdCommoms.size() > 0 ){
			for(SESECDEcdCommom ecdCommom : ecdCommoms){
				List<String> propertyNames = new ArrayList<String>();
				List<Object> previousState = new ArrayList<Object>();
				java.lang.reflect.Field[] fields = SESECDEcdCommom.class.getDeclaredFields();
				try {
					for(java.lang.reflect.Field field : fields){
						field.setAccessible(true);
						try{
							field.get(null);
							continue;
						}catch(NullPointerException e){
							if(field.getName().equalsIgnoreCase("bapAttachmentInfo")
									||field.getName().equalsIgnoreCase("document")
									||field.getName().equalsIgnoreCase("company")){
								continue;
							}
							propertyNames.add(field.getName());
							previousState.add(field.get(ecdCommom));
						}
					}
					dataAuditLogService.saveCustomerAudit(InternationalResource.get("foundation.common.delete"),ecdCommom, ecdCommom.getId(), null, previousState.toArray(), propertyNames.toArray(new String[propertyNames.size()]));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
		}
		if(ecdCommomIds != null && ecdCommomIds.size() > 0) {
		    if (ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_ecdPanel_EcdCommom", AuditLogType.DATA)) {
                // 发布数据删除审计日志
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("ids", ecdCommomIds);
                List<SESECDEcdCommom> details = ecdCommomDao.findByHql("from " + SESECDEcdCommom.JPA_NAME + " where id in(:ids)", queryParams);
                if (!details.isEmpty()) {
                    List<AuditDataLogBO> auditDataLogList = getAuditDataLogs(details, OperateType.DELETE);
                    for (AuditDataLogBO auditDataLog : auditDataLogList) {
                        auditDataLogStrategy.publishAuditLogLazy(auditDataLog);
                    }
                }
		    }
			String hql = "delete " + SESECDEcdCommom.JPA_NAME + " where id in(:ids)";
			Query query = ecdCommomDao.createQuery(hql);
			query.setParameterList("ids", ecdCommomIds);
			query.executeUpdate();
		}

		for(SESECDEcdCommom ecdCommom : ecdCommoms){
			afterDeleteEcdCommom(ecdCommom);
		}

		if(eventTopic==null){
			for(SESECDEcdCommom ecdCommomz : ecdCommoms){
				ecdCommomz.setValid(false);
				List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
				params.add(ecdCommomz);
				Map<String, Object> props = new HashMap<String, Object>();
				props.put("callType", "service");
				props.put("entityCode", "SESECD_1.0.0_ecdPanel");
				props.put("eventType", "delete");
			}
		}
	}

	/**
	 * 获取审计数据日志
	 */
	private List<AuditDataLogBO> getAuditDataLogs(List<SESECDEcdCommom> list, OperateType operateType) {
	    Long cid = UserContext.getUserContext().getCompanyId();
		List<AuditDataLogBO> auditDataLogs = list.stream().map(item -> {
			AuditDataLogBO auditDataLog = auditDataLogStrategy.buildAuditLog();
			auditDataLog.setOperateType(operateType);
			if (item.getCid() == null) {
				item.setCid(cid);
			}
			auditDataLog.setModel(item);
            auditDataLog.setModelCode("SESECD_1.0.0_ecdPanel_EcdCommom");
            auditDataLog.setModelName("SESECD.ecdPanel.EcdCommom");
            auditDataLog.setEntityCode("SESECD_1.0.0_ecdPanel");
            auditDataLog.setEntityName("SESECD.entityname.randon1578365471050");
			return auditDataLog;
		}).collect(Collectors.toList());
		return auditDataLogs;
	}

	@Override
	public void deleteEcdCommom(long ecdCommomId, int ecdCommomVersion){
		this.deleteEcdCommom(ecdCommomId, ecdCommomVersion,null);
	}
	@Override
	public void deleteEcdCommom(long ecdCommomId, int ecdCommomVersion,SignatureLog signatureLog){
		// 批量删除时获取最新数据
		ecdCommomDao.flush();
		ecdCommomDao.clear();
		SESECDEcdCommom ecdCommom = getEcdCommom(ecdCommomId);
		if(ecdCommom != null && !ecdCommom.isValid()){
			throw new BAPException(BAPException.Code.OBJECT_HAVE_BEAN_DELETED);
		}
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		beforeDeleteEcdCommom(ecdCommom);
		ecdCommomDao.delete(ecdCommomId, ecdCommomVersion);
		afterDeleteEcdCommom(ecdCommom);
		ecdCommomDao.flush();
		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		params.add(ecdCommom);
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		props.put("eventType", "delete");

		if(signatureLog != null ) {
			Object businessKey=null;
			businessKey=ecdCommom.getId();
			signatureLog.setTableId(ecdCommom.getId());
			if(businessKey != null) {
				if(StringUtils.isEmpty(signatureLog.getBusinessKey())){
					signatureLog.setBusinessKey(businessKey.toString());
				} else {
					signatureLog.setBusinessKey(signatureLog.getBusinessKey() + "," + businessKey.toString());
				}
			}
			if(null != AuditUtil.getCurrentAudit() && null != AuditUtil.getCurrentAudit().getOperationAudit()){
				signatureLog.setOperateLogUuid(AuditUtil.getCurrentAudit().getOperationAudit().getUuid());
			}
		}
	}


	@Override
	public void deleteEcdCommom(String ecdCommomIds){
		this.deleteEcdCommom(ecdCommomIds,null);
	}

	@Override
	public void deleteEcdCommom(String ecdCommomIds,SignatureLog signatureLog){
		deleteCollection(ecdCommomIds,signatureLog);
	}

	private void deleteCollection(String ids,SignatureLog signatureLog) {
		if(ids.isEmpty()){
			throw new BAPException("ec.common.checkselected");
		}
		StringBuilder deleteNotLeafArgs = new StringBuilder();
		String[] idst = ids.split(",");
		for(String idVersion : idst) {
			String id = idVersion.split("@")[0];
			String version = idVersion.split("@")[1];
			if (id != null && id.trim().length() > 0 && version != null && version.trim().length() > 0) {
				deleteEcdCommom(Long.valueOf(id), Integer.valueOf(version),signatureLog);
			}
		}
		if (deleteNotLeafArgs.length() > 0) {
			deleteNotLeafArgs.deleteCharAt(deleteNotLeafArgs.length() - 1);
			throw new BAPException(BAPException.Code.CURR_NODE_IS_NOT_LEAF, deleteNotLeafArgs.toString());
		}
		
		if(null != signatureLog){
			if(null != AuditUtil.getCurrentAudit() && null != AuditUtil.getCurrentAudit().getOperationAudit()){
				signatureLog.setOperateLogUuid(AuditUtil.getCurrentAudit().getOperationAudit().getUuid());
			}
			String msgId="moduleCode:SESECD_1.0.0#entityCode:SESECD_1.0.0_ecdPanel#modelCode:SESECD_1.0.0_ecdPanel_EcdCommom#timeStamp:"+String.valueOf(System.currentTimeMillis());
			reliableMessageSenderService.sendQueue(msgId,signatureLog);
		}
        if(getMapServiceEnable()) {
            // 地图空间数据删除
            try {
                delLayerInfo(idst, null, null);
            } catch (Exception e) {
                log.error("主模型地图数据删除失败：" + e.getMessage(), e);
                throw new BAPException(InternationalResource.get("greendill.service.ditu.zhumoxingerror",this.getCurrentLanguage()) + e.getMessage());
            }
        }
	}

	@Override
	public void restoreEcdCommom(String ecdCommomIds){
		restoreCollection(ecdCommomIds);
	}

	public void restoreCollection(String ids) {
		String[] idst = ids.split(",");
		for(String idVersion : idst) {
			String id = idVersion.split("@")[0];
			if (id != null && id.trim().length() > 0) {
				restoreEcdCommom(Long.valueOf(id));
			}
		}
	}

	@Override
	public void restoreEcdCommom(long ecdCommomId){

		findBusinessKeyUsed(ecdCommomId);	//判断业务主键是否重复

		SESECDEcdCommom ecdCommom = getEcdCommom(ecdCommomId);
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		beforeRestoreEcdCommom(ecdCommom);
		ecdCommom.setValid(true);
		ecdCommomDao.update(ecdCommom);
		afterRestoreEcdCommom(ecdCommom);
		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		params.add(ecdCommom);
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		props.put("eventType", "restore");
	}

	@Override
	public void findBusinessKeyUsed(long ecdCommomId){
		Property property = modelServiceFoundation.getBussinessProperty("SESECD_1.0.0_ecdPanel_EcdCommom");
		if(property != null){
			String propertyName = property.getColumnName();
			String sql  = "select * from " + SESECDEcdCommom.TABLE_NAME + " where valid = 1 and " +
					propertyName + " =  (select "+ propertyName +" from "+ SESECDEcdCommom.TABLE_NAME +" where id = ? )";
			List<Object> list =  ecdCommomDao.createNativeQuery(sql,ecdCommomId).list();
			if(list.size() > 0){
				throw new BAPException("foundation.bussinessKey.repeat");
			}

		}
	}


	@Override
	public  void batchImportBaseEcdCommom(List<SESECDEcdCommom>  ecdCommoms){
		for(SESECDEcdCommom ecdCommom:ecdCommoms)  {
			saveEcdCommom(ecdCommom, null, null, null);
		}
	}

	@Override
	public  void excelBatchImportBaseEcdCommom(List<SESECDEcdCommom>  ecdCommoms){
		for(SESECDEcdCommom ecdCommom:ecdCommoms)  {
			saveEcdCommom(ecdCommom, null);
		}
	}


	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads){
		saveEcdCommom(ecdCommom, dgLists,dgDeleteIDs,assFileUploads, null);
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads, String viewCode){
		saveEcdCommom(ecdCommom, dgLists,dgDeleteIDs,assFileUploads, viewCode, null,null);
	}

	@Override
	public void generateEcdCommomCodes(SESECDEcdCommom ecdCommom) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		generateEcdCommomCodes(ecdCommom, false);
	}

	@Override
	public void generateEcdCommomCodes(SESECDEcdCommom ecdCommom, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	}

	@Override
	public void generateEcdCommomSummarys(SESECDEcdCommom ecdCommom) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		generateEcdCommomSummarys(ecdCommom, false);
	}

	@Override
	public void generateEcdCommomSummarys(SESECDEcdCommom ecdCommom, Boolean viewIsView) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads,String viewCode, String eventTopic,boolean... isImport){
		saveEcdCommom(ecdCommom, dgLists,dgDeleteIDs,assFileUploads, viewCode, eventTopic,null,isImport);
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads,String viewCode, String eventTopic, SignatureLog signatureLog,boolean... isImport){
		boolean isNew = false;
		String entityCode = "SESECD_1.0.0_ecdPanel";
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		String url = null;
		if(ecdCommom.getId() != null && ecdCommom.getId() > 0){
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.pending.edit");
				AuditUtil.setAuditOperationType("2");
			}
			props.put("eventType", "modify");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/modify";
		}else{
			isNew = true;
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.print.template.add");
				AuditUtil.setAuditOperationType("1");
			}
			props.put("eventType", "add");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/add";
		}
		Boolean viewIsView = false;
		if(viewCode != null && !viewCode.trim().isEmpty()){
			View view = viewServiceFoundation.getViewForMethodCheckViewType(viewCode);
			if(null != view) {
				viewIsView = (view.getType() == ViewType.VIEW);
			}
		}
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom, viewIsView);

		if (viewIsView) {
			ecdCommomDao.saveWithRevertVersion(ecdCommom);
		} else {
			if(isNew){
				ecdCommomDao.save(ecdCommom);
			}else{
				ecdCommomDao.merge(ecdCommom);
			}
		}
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		dealDatagridsSave(ecdCommom,viewCode,dgLists,dgDeleteIDs,assFileUploads);
		// 一对多情况处理

		// 根据配置规则生成编码
		try {
			generateEcdCommomCodes(ecdCommom, viewIsView);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}
		// 根据配置规则生成摘要
		try {
			generateEcdCommomSummarys(ecdCommom, viewIsView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}

		afterSaveEcdCommom(ecdCommom, viewIsView);


		ecdCommomDao.flush();

		if(signatureLog != null) {
			Object businessKey=null;
			businessKey=ecdCommom.getId();
			if(businessKey != null) {
				signatureLog.setBusinessKey(businessKey.toString());
			}
			if(null != AuditUtil.getCurrentAudit() && null != AuditUtil.getCurrentAudit().getOperationAudit()){
				signatureLog.setOperateLogUuid(AuditUtil.getCurrentAudit().getOperationAudit().getUuid());
			}
			signatureLog.setTableId(ecdCommom.getId());
			String msgId="moduleCode:SESECD_1.0.0#entityCode:SESECD_1.0.0_ecdPanel#modelCode:SESECD_1.0.0_ecdPanel_EcdCommom#timeStamp:"+String.valueOf(System.currentTimeMillis());
			reliableMessageSenderService.sendQueue(msgId,signatureLog);
		}

		// 调用地图接口，保存数据
		if (getMapServiceEnable()) {
            try{
                addLayerInfo(ecdCommom, viewCode, null);
            } catch (Exception e) {
                log.error("地图接口调用失败：" + e.getMessage(), e);
                throw new BAPException(InternationalResource.get("greendill.service.ditu.ditudiaoyongerror",this.getCurrentLanguage()) + e.getMessage());
            }
		}
	}


	@Override
	public void mergeEcdCommom(SESECDEcdCommom ecdCommom, Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads){
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom);
		ecdCommomDao.merge(ecdCommom);
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		dealDatagridsSave(ecdCommom,null,dgLists,dgDeleteIDs,assFileUploads);

		afterSaveEcdCommom(ecdCommom);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Page<SESECDEcdCommom> findEcdCommoms(Page<SESECDEcdCommom> page, Criterion... criterions) {
		return ecdCommomDao.findByPage(page, criterions);
	}
	private static String matchSql(Pattern p, String sql, String prefix, Map<String, String> maps, int n1, int n2) {
		Matcher matcher = p.matcher(sql);
		int i = 1;
		if (null == maps){
			maps = new HashMap<String, String>();
		}
		while (matcher.find()) {
			String tag = (matcher.group(n1));// 含引号
			String tag2 = (matcher.group(n2));// 不含引号
			String symbol = prefix + i++;
			String regexString =  "";
			if(tag.equals(tag2)){
				regexString =  "\\b"+ tag2 +"\\b";
			}else{
				regexString =  "\"\\b"+ tag2 +"\\b\"";
			}


			Pattern pattern = Pattern.compile(regexString);
			Matcher matcher1 = pattern.matcher(sql);
			sql = matcher1.replaceAll(symbol);
			maps.put(symbol, tag2);
		}
		return sql;
	}

	private static String replaceSql(Pattern p, String sql, Map<String, String> maps, int n1, int n2) {
		Matcher matcher = p.matcher(sql);
		while (matcher.find()) {
			String tag = matcher.group(n1);// 不含引号
			String tag2 = matcher.group(n2);// 含引号
			String regexString =  "";
			for(Map.Entry<String, String> entry : maps.entrySet()) {
				if(entry.getValue().equals(tag)) {
					if(tag.equals(tag2)){
						regexString =  "\\b"+ tag +"\\b";
					}else{
						regexString =  "\"\\b"+ tag +"\\b\"";
					}

					Pattern pattern = Pattern.compile(regexString);
					Matcher matcher1 = pattern.matcher(sql);
					sql = matcher1.replaceAll(entry.getKey());
				}
			}
		}
		return sql;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void findEcdCommoms(Page<SESECDEcdCommom> page,  String viewCode, int type, String processKey,Boolean flowBulkFlag, Boolean hasAttachment,Boolean noQueryFlag,String exportSql, Map exportMap, List<Param> params,QueryEntity queryEntity) {
		findEcdCommoms(page,  viewCode, type, processKey, flowBulkFlag, hasAttachment, params, viewCode, noQueryFlag, exportSql, exportMap, queryEntity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void findEcdCommoms(Page<SESECDEcdCommom> page,  String viewCode, int type, String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity) {
		commonQuery(page, viewCode, type, processKey, flowBulkFlag, hasAttachment, params, permissionCode, noQueryFlag, exportSql, exportMap, queryEntity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void findEcdCommoms(Page<SESECDEcdCommom> page,  String viewCode, int type, String processKey,Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode, Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity, Object... objects) {
		commonQuery(page, viewCode, type, processKey, flowBulkFlag, hasAttachment, params, permissionCode, noQueryFlag, exportSql, exportMap, queryEntity ,objects);
	}

	/**
	 * 生成自定义字段查询sql
	 * @param viewCode 视图code
	 * @param sql 查询sql
	 * @return
	 */
	@Override
	public String generateCustomPropertySql(String viewCode, String datagridCode, String sql) {
		View v = viewServiceFoundation.getView(viewCode);
		if (v != null && v.getHasCustomSection() != null && v.getHasCustomSection()) {
			StringBuilder selectSql = new StringBuilder();
			StringBuilder joinSql = new StringBuilder();
			List<CustomPropertyViewMapping> viewMapppingList = null;
			if(null != datagridCode){
				viewMapppingList= viewServiceFoundation.findDgCustomePropertyByDgCode(datagridCode, getCurrentLanguage());
			}else{
				viewMapppingList= viewServiceFoundation.getCustomPropertyViewMappings(viewCode);
			}
			if (viewMapppingList != null && viewMapppingList.size() > 0) {
				Set<String> propLayRecSet = new HashSet<String>();
				for (CustomPropertyViewMapping viewMapping : viewMapppingList) {
					if (null !=  viewMapping.getId() && viewMapping.getPropertyLayRec() != null && viewMapping.getPropertyLayRec().length() > 0) {
						Property property = modelServiceFoundation.findPropertyByCode(viewMapping.getProperty().getCode());
						String[] layRecArr = viewMapping.getPropertyLayRec().split("\\|\\|");
						if (!layRecArr[0].contains(".")) {
							if (null != sql && sql.indexOf(property.getColumnName()) == -1) {
								selectSql.append(",\"").append(layRecArr[0]).append("\".").append(property.getColumnName()).append(" AS ")
										.append("\"").append(property.getName()).append("\"");
							}
						} else {
							propLayRecSet.add(viewMapping.getPropertyLayRec());
							String modelAlias = layRecArr[0].substring(layRecArr[0].indexOf(".") + 1);
							if (null != sql && sql.indexOf(property.getColumnName()) == -1) {
								selectSql.append(",\"cp_").append(modelAlias).append("\".").append(property.getColumnName()).append(" AS ")
										.append("\"cp_").append(modelAlias).append(".").append(property.getName()).append("\"");
							}
						}
					}
				}
				if (propLayRecSet != null && propLayRecSet.size() > 0) {
					Set<String> tmpPropLayRecSet = new HashSet<String>();
					tmpPropLayRecSet.addAll(propLayRecSet);
					for (Iterator<String> iter = propLayRecSet.iterator(); iter.hasNext();) {
						String propLayRec = iter.next();
						String alias1 = propLayRec.split("\\|\\|")[0];
						for (String tmpPropLayRec : tmpPropLayRecSet) {
							String alias2 = tmpPropLayRec.split("\\|\\|")[0];
							if (!alias2.equals(alias1) && alias2.startsWith(alias1)) {
								iter.remove();
								break;
							}
						}
					}
					for (String propLayrec : propLayRecSet) {
						String[] layRecArr = propLayrec.split("\\|\\|");
						String modelAlias = layRecArr[0].substring(layRecArr[0].indexOf(".") + 1);
						String mainModelAlias = layRecArr[0].substring(0, layRecArr[0].indexOf("."));
						String[] modelAliasTuple = modelAlias.split("\\.");
						String[] relations = layRecArr[1].split("\\-");
						String modelAlias1 = "";
						String modelAlias2 = "";
						for (int i = 0; i < relations.length; i++) {
							String[] relationArr = relations[i].split(",");
							String tableName1 = relationArr[0];
							String colName1 = relationArr[1];
							// String tableName2 = relationArr[2];
							String colName2 = relationArr[3];
							modelAlias1 += (i > 0 ? "." + modelAliasTuple[i] : modelAliasTuple[i]);
							if (i > 0) {
								modelAlias2 += (i > 1 ? "." + modelAliasTuple[i - 1] : modelAliasTuple[i - 1]);
							}
							joinSql.append(" LEFT OUTER JOIN ").append(tableName1).append(" \"cp_").append(modelAlias1).append("\"").append(" ON \"cp_")
									.append(modelAlias1).append("\".").append(colName1).append(" = \"");
							if (i > 0) {
								joinSql.append("cp_").append(modelAlias2);
							} else {
								joinSql.append(mainModelAlias);
							}
							joinSql.append("\".").append(colName2);
						}
					}
				}
				if (selectSql.length() > 0) {
					sql = sql.substring(0, sql.indexOf(" FROM ")) + selectSql.toString() + sql.substring(sql.indexOf(" FROM "));
					if (joinSql.length() > 0) {
						sql += joinSql.toString();
					}
				}
			}
		}
		return sql;
	}

	/**
	 * @param page
	 * @param viewCode
	 * @param type
	 * @param processKey
	 * @param flowBulkFlag
	 * @param hasAttachment
	 * @param params
	 * @param permissionCode
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void commonQuery(Page<SESECDEcdCommom> page, String viewCode, int type, String processKey, Boolean flowBulkFlag,
			Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, Map exportMap,QueryEntity queryEntity, Object... objects) {
		int queryType = 0;
		if(objects.length > 0) {
			queryType = (int) objects[0];
		}
		String dgCode = "";
		String[] splits = viewCode.split(",");
		if(splits.length >= 2){
			viewCode = splits[0];
			dgCode = splits[1];
		}
		Map<Integer, String> runtimeSqlMap = StringUtils.isEmpty(dgCode) ? sqlService.getSqlByViewCode(viewCode) : sqlService.getSqlByDgCode(dgCode);
        String sql = runtimeSqlMap.get(type);
        //关联公司SQL
        sql = getRefCompanySql(sql, "\"ecdCommom\"");
		//如果存在导出下配置的sql则进行替换
		if(!exportSql.trim().isEmpty() ) {
			if(page.isExportFlag() && sql != null && sql.contains("LEFT OUTER JOIN")){//如果用于导出，sql里的left outer join的内容拼到exportSql里
				String sql2 = sql;
				if(sql2.contains("LEFT OUTER JOIN")){
					sql2 = sql2.substring(sql2.indexOf("LEFT OUTER JOIN"), sql2.length());
					String tableNames[] = sql2.split("LEFT OUTER JOIN");
					for(String str : tableNames){
						if(str!=null && !str.equals("") && str.contains(" ON ")){
							String tableNames2[] = str.split(" ON ");
							if(tableNames2[0]!=null && !tableNames2[0].equals("") && !exportSql.contains(tableNames2[0].trim())){
								exportSql += " LEFT OUTER JOIN" + str;
							}
						}
					}
					sql = exportSql;
				}
			}else{//如果存在导出下配置的sql则进行替换
				sql=exportSql;
			}
		}

		// 自定义字段sql拼接
		sql = generateCustomPropertySql(viewCode, queryEntity.getDatagridCode(), sql);
		String countSql = runtimeSqlMap.get(Sql.TYPE_USED_TOTALS);
		if(!exportSql.trim().isEmpty())  {
			//FIXME  有小节配置时,需要将小节信息加入
			countSql="SELECT COUNT(*) count FROM ";
		}
		User currentUser=(User)getCurrentUser();
		if(sql == null){
			sql = "";
		}
		StringBuilder totalSql = new StringBuilder(sql);
		Boolean crossCompanyFlag = false;
		if (null != sql && sql.length() > 0) {
			StringBuilder s = new StringBuilder();
			StringBuilder queryCond = new StringBuilder();
			List<Object> list = new ArrayList<Object>();
			Map<String,Object> customerSqlListMap = new LinkedHashMap<String,Object>();
			boolean hasWhere = false;
			String referenceCondition = "";
			String customerSql = "";
			String customerCondition = "";
			String classifySql = "";
			if (null != params && !params.isEmpty()) {
				Param param = params.get(0);
				if("crossCompanyFlag".equals(param.getName())){
					crossCompanyFlag = Boolean.parseBoolean(param.getValue().toString());
					params.remove(0);
				}
			}
			StringBuilder sortOrderByStr = new StringBuilder("");
			if (null != params && !params.isEmpty()) {
				s.append(" WHERE ((");
				hasWhere = true;
				String advQuery = "";
				String classifyCodes = "";
				List<Object> advValues = null;
				String fastQuery = "";
				List<Object> fastValues = null;
				String extraQuery = "";
				List<Object> customerValues = null;
				List<Object> extraQueryValues = null;
				List<Object> customerSqlValues = null;
				List<Object> classifySqlValues = null;
				//组合where条件
				for (int i = 0; i < params.size(); i++) {
					Param param = params.get(i);

					if("classifySql".equals(param.getName())){
						classifySql = (String) param.getValue();
						continue;
					}

					if("classifySqlValues".equals(param.getName())){
						classifySqlValues = (List<Object>) param.getValue();
						continue;
					}

					if(type == Sql.TYPE_LIST_REFERENCE && "referenceCondition".equals(param.getName())) {
						referenceCondition = (String) param.getValue();
						continue;
					}

					if("customerValues".equals(param.getName())){
						customerValues = (List<Object>) param.getValue();
						continue;
					}
					if("customerSqlValues".equals(param.getName())){
						customerSqlValues = (List<Object>) param.getValue();
						continue;
					}
					if("customerSqlListMap".equals(param.getName())){
						customerSqlListMap = (Map<String,Object>) param.getValue();
						continue;
					}
					if("customerCondition".equals(param.getName())){
						customerCondition = (String) param.getValue();
						continue;
					}
					if("customerSql".equals(param.getName())){
						customerSql = (String)param.getValue();
						continue;
					}

					if ("advQueryCond".equals(param.getName())) {
						AdvQueryCondition cond = conditionService.toSql((String) param.getValue());
						if (cond != null) {
							advValues = cond.getValues();
							advQuery = cond.getSql();
						}
						continue;
					}
					//数据分类内的高级查询
					if ("classifyCodes".equals(param.getName())) {
						classifyCodes = (String)param.getValue();
						continue;
					}

					if ("fastQueryCond".equals(param.getName())) {
						AdvQueryCondition cond = conditionService.toSql((String) param.getValue());
						if (cond != null) {
							fastValues = cond.getValues();
							fastQuery = cond.getSql();
						}
						continue;
					}

					if ("extraQueryCond".equals(param.getName())) {
						Map<String,String> oneToManyParams = new HashMap<String,String>();
						oneToManyParams = (Map<String, String>) param.getValue();
						String json = sqlService.getExtraQueryJson(viewCode);
						Matcher matcher = oneToManyPattern.matcher(json);
						while (matcher.find()) {
							String tag = (matcher.group(2));
							String subTag = tag.substring(2, tag.length()-2);
							if(null != oneToManyParams.get(subTag) && ((String)(oneToManyParams.get(subTag))).length() > 0) {
								json = json.replace(tag, oneToManyParams.get(subTag).replace("\"", "SYMBOL_DOUBLE_QUOTE"));
							} else {
								json = json.replace(tag, "");
							}
						}
						if(null != json && json.length() > 0) {
							AdvQueryCondition cond = conditionService.toSql(json);
							if (cond != null) {
								extraQueryValues = cond.getValues();
								extraQuery = cond.getSql();
							}
						}
						continue;
					}
					if ("dataTable-sortColKey".equals(param.getName())) {
						String sortValue = (String) param.getValue();
						String key = null, columnName = null,customKey = null;
						if(sortValue.indexOf("::") > 0) {
							key = sortValue.split("::")[0];
							columnName = sortValue.split("::")[1];
							if(key.indexOf(".") > 0 && key.contains("attrMap")){//自定义字段
								key = key.split("\\.")[1];
								if(key.startsWith("cp_")){//判断排序字段是否是关联模型的字段
									key = key.substring(3).replace("_", ".");
								}
							}
						} else if (sortValue.indexOf(".") > 0){
							customKey = sortValue.split("\\.")[0];
							if(customKey.contains("attrMap")){
								key =customKey;
								columnName = sortValue.split("\\.")[1];
							}else{
								key = sortValue;
							}
						} else {
							key = sortValue;
						}
						int lastDotPos = key.lastIndexOf('.');
						if(null == columnName) {
							columnName = modelServiceFoundation.getPropertyColumnName("SESECD_1.0.0_ecdPanel_EcdCommom", key.substring(lastDotPos + 1), false);
						}
						String tableAlias;
						String getMethodName = "get" + fLTU(key);
						Class<?> clazz = null;
						try {
						    // 系统编码按sort排序
                            Method method = SESECDEcdCommom.class.getMethod(getMethodName, null);
                            clazz = method.getReturnType();
                        } catch (NoSuchMethodException e) {
                        }
                        if (clazz != null && SystemCode.class.equals(clazz)) {
                            tableAlias = "\"baseSystemCode_" + key + "\"";
                            String leftJoinSql = " LEFT OUTER JOIN BASE_SYSTEMCODE " + tableAlias;
                            if (!totalSql.toString().contains(leftJoinSql)) {
                                totalSql.append(leftJoinSql).append(" on \"ecdCommom\".")
                                        .append(key).append(" = ").append(tableAlias).append(".ID");
                            }
                            sortOrderByStr.append(tableAlias).append(".sort");
                        } else {
                            tableAlias = lastDotPos < 0 ? "\"ecdCommom\"" : "\"" + key.substring(0, lastDotPos) + "\"";
                            if("\"pending\"".equals(tableAlias)) {
                                tableAlias = "\"p\"";
                            }
                            sortOrderByStr.append(tableAlias).append(".").append(columnName);
                        }
                        continue;
                    }
                    if ("dataTable-sortColOrder".equals(param.getName())) {
                        sortOrderByStr.append(" ").append((String) param.getValue());
                        continue;
                    }
					if(param.getName()!=null&&param.getName().startsWith("\"tree-")){
						if(param.getName().startsWith("\"tree-layRec-")){
							String treeCondition = sqlService.getSqlQuery(viewCode,Sql.TYPE_USED_TREE);
							if (treeCondition!=null && treeCondition.trim().length() > 0) {
								if (hasWhere){
									s.append(" AND ");
								}else{
									s.append(" WHERE (");
									hasWhere = true;
								}

								s.append(treeCondition);
								if(param.getLikeType() == Param.EQUAL_LIKELEFT) {
									list.add(param.getValue());
									list.add((String) param.getValue()+"-%");
								}
								if(param.getLikeType() == Param.LIKE_UNSUPPORT) {
									list.add(param.getValue());
								}
							}
						}
						continue;
					}
					if(param.getName()!=null&&!param.getName().startsWith("\"tree-") && param.getLikeType() == Param.EQUAL_LIKELEFT){
						s.append(" AND ( ").append(param.getName()).append("= ? ").append(" OR ")
							.append(param.getName()).append(" like ? )");
						list.add(param.getValue());
						list.add(param.getValue()+"-%");
						continue;
					}
					if (i > 0){
						s.append(" AND ");
					}
					if((null != param.getContainLower() && param.getContainLower())) {
						s.append(" ( ");
					}
					if(!param.getCaseSensitive()) {
						if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
							if(!"DATE".equals(param.getColumnType()) && !"DATETIME".equals(param.getColumnType()) && !"LONG".equals(param.getColumnType()) && !"INTEGER".equals(param.getColumnType()) && !"DECIMAL".equals(param.getColumnType()) && !"BOOLEAN".equals(param.getColumnType())) {
								s.append(" UPPER (");
							}
						}
					}
					if(param.getLikeType() != Param.MULTI_LIKE) {
						s.append(param.getName());
					}
					if(!param.getCaseSensitive()) {
						if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
							if(!"DATE".equals(param.getColumnType()) && !"DATETIME".equals(param.getColumnType()) && !"LONG".equals(param.getColumnType()) && !"INTEGER".equals(param.getColumnType()) && !"DECIMAL".equals(param.getColumnType()) && !"BOOLEAN".equals(param.getColumnType())) {
								s.append(") ");
							}
						}
					}
					String exp = " = ?";
					if (param.getLikeType() == Param.LIKE_UNSUPPORT) {
						s.append(" = ?");
					} else if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_LEFT) {
						s.append(" LIKE ?");
						exp = " LIKE ?";
					} else if(param.getLikeType() == Param.GREATE_EQUAL) {
						s.append(" >= ?");
						exp = " >= ?";
					}else if(param.getLikeType() == Param.LESS_EQUAL) {
						s.append(" <= ?");
						exp = " <= ?";
					}else if (param.getLikeType() == Param.GREATE_THAN) {
						s.append(" > ?");
						exp = " > ?";
					}else if (param.getLikeType() == Param.LESS_THAN) {
						s.append(" < ?");
						exp = " < ?";
					}else if (param.getLikeType() == Param.NONE_EQUAL) {
						s.append(" <> ?");
						exp = " <> ?";
					}else if (param.getLikeType() == Param.MULTI_LIKE) {
						exp = " LIKE ?";
						String multiValue = param.getValue().toString();
						String[] values = multiValue.split(",");
						StringBuilder multiSb = new StringBuilder();
						for(int m = 0; m < values.length; m++) {
							if(null != values[m] && values[m].length() > 0) {
								multiSb.append(" OR ");
								multiSb.append(param.getName()).append(" LIKE ? ");
								list.add("%," + values[m] + ",%");
							}
						}
						if(multiSb.length() > 0) {
							s.append(" ( ");
							s.append(multiSb.toString().substring(4));
							s.append(" ) ");
						}
					}else {}
					if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT) {
						String upperStr = (String) param.getValue();
						if(!param.getCaseSensitive()) {
							upperStr = upperStr.toUpperCase();
						}
						if(param.getLikeType() == Param.LIKE_ALL) {
							param.setValue('%' + upperStr + '%');
						}
						if(param.getLikeType() == Param.LIKE_LEFT) {
							param.setValue(upperStr + '%');
						}
						if(param.getLikeType() == Param.LIKE_RIGHT) {
							param.setValue('%' + upperStr);
						}
					}
					if(param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
						if ("DATETIME".equals(param.getColumnType()) || "DATE".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(DateUtils.ecDateFormat((String) param.getValue()));
							}
						} else if ("LONG".equals(param.getColumnType())){
							if(param.getValue() instanceof String){
								param.setValue(Long.parseLong((String) param.getValue()));
							}
						} else if ("INTEGER".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(Integer.parseInt((String) param.getValue()));
							}
						} else if ("DECIMAL".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(new BigDecimal((String) param.getValue()));
							}
						} else {
							if(param.getValue() instanceof String){
								String upperStr = (String) param.getValue();
								if(!param.getCaseSensitive()) {
									upperStr = upperStr.toUpperCase();
								}
								param.setValue(upperStr);
							} else {
								param.setValue(param.getValue());
							}
						}
					}
					if(param.getLikeType() == Param.GREATE_EQUAL || param.getLikeType() == Param.GREATE_THAN || param.getLikeType() == Param.LESS_EQUAL || param.getLikeType() == Param.LESS_THAN) {
						if ("DATETIME".equals(param.getColumnType()) || "DATE".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(DateUtils.ecDateFormat((String) param.getValue()));
							}
						} else if ("LONG".equals(param.getColumnType())){
							if(param.getValue() instanceof String){
								param.setValue(Long.parseLong((String) param.getValue()));
							}
						} else if ("INTEGER".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(Integer.parseInt((String) param.getValue()));
							}
						} else if ("DECIMAL".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(new BigDecimal((String) param.getValue()));
							}
						}

					}

					if(param.getLikeType() != Param.MULTI_LIKE) {
						list.add(param.getValue());
					}
					if(null != param.getContainLower() && param.getContainLower()) {
						String prefix = param.getName().substring(0, param.getName().lastIndexOf("."));
						String columnName = param.getName().substring(param.getName().lastIndexOf(".") + 1);
						if(null != param.getModelInfo() && param.getModelInfo().length > 0) {
							String entityInfo = (param.getModelInfo())[0];
//							String serviceInfo = (param.getModelInfo())[1];
							String layRecColumnName = null;
							if(param.getModelInfo().length > 2) {
								layRecColumnName = (param.getModelInfo())[2];
							}
							//非树形定义getContainLower方法，如果是树形实现IModelTreeLayRecService接口的getContainLower方法
							List<String> layRecs = this.getContainLower(entityInfo, Inflector.getInstance().columnToField(columnName), exp, param.getValue(), false);
//							ServiceReference ref = bundleContext.getServiceReference(serviceInfo);
//							List<String> layRecs = null;
//							if(null != ref) {
//								IModelTreeLayRecService layRecService = (IModelTreeLayRecService) bundleContext.getService(ref);
//								layRecs = layRecService.getContainLower(entityInfo, Inflector.getInstance().columnToField(columnName), exp, param.getValue(), false);
//							}
							if(null != layRecs && !layRecs.isEmpty()) {
								String layRecCond = "";
								for (String layRec : layRecs) {
									layRecCond += " OR " + prefix + "." + (layRecColumnName == null ? "LAY_REC" : layRecColumnName) +  " = ?";
									layRecCond += " OR " + prefix + "." + (layRecColumnName == null ? "LAY_REC" : layRecColumnName) +  " like ?";
									list.add(layRec);
									list.add(layRec + "-%");
								}
								s.append(layRecCond);
							}
							s.append(" ) ");
						}
					}
				}
					if (advQuery != null && advQuery.length() > 0) {
						s.append(" AND ").append(advQuery);
						if (advValues != null) {
							list.addAll(advValues);
						}
					}
					if (fastQuery != null && fastQuery.length() > 0) {
						s.append(" AND ").append(fastQuery);
						if (fastValues != null) {
							list.addAll(fastValues);
						}
					}
					if (extraQuery != null && extraQuery.length() > 0) {
						s.append(" AND ").append(extraQuery);
						if (extraQueryValues != null) {
							list.addAll(extraQueryValues);
						}
					}
					if(customerCondition !=null && customerCondition.length()>0){
						s.append(" AND ").append(customerCondition);
						if(customerValues!=null && customerValues.size()>0){
							list.addAll(customerValues);
						}
					}
					if(customerSql != null && customerSql.length()>0){
						s.append(" AND ").append(customerSql);
						if(customerSqlValues!=null && customerSqlValues.size()>0){
							list.addAll(customerSqlValues);
						}
					}
					if(classifySql != null && classifySql.length() > 0){
						s.append(" AND (").append(classifySql).append(")");
						if(classifySqlValues != null && classifySqlValues.size() > 0){
							list.addAll(classifySqlValues);
						}
					}

					Entity entity = viewServiceFoundation.getEntityFromView(viewCode);
                    if(!entity.getCrossCompanyFlag()){
                        if(type == Sql.TYPE_LIST_REFERENCE){
                            if(null != crossCompanyFlag && !crossCompanyFlag){
                                String referencePowerCondition = dataPermissionService.getCompanyCondition(entity.getReffViewDataType(),"\"ecdCommom\"");
                                s.append(" AND ").append(referencePowerCondition);
                            }
                        }
                    }

	//				if(type == Sql.TYPE_LIST_QUERY || type == Sql.TYPE_LIST_PENDING || type == Sql.TYPE_LIST_REFERENCE) {
	//					if(type == Sql.TYPE_LIST_PENDING) {
	//						s.append(" AND \"p\".CID = ").append(getCurrentCompanyId());
	//					} else if(type == Sql.TYPE_LIST_QUERY){
	//						s.append(" AND \"ecdCommom\".CID = ").append(getCurrentCompanyId());
	//					} else if(type == Sql.TYPE_LIST_REFERENCE){
	//						if(null != crossCompanyFlag && !crossCompanyFlag){
	//							s.append(" AND \"ecdCommom\".CID = ").append(getCurrentCompanyId());
	//						}
	//					}
	//				}
					s.append(" ) ");
			}
			// ////PowerCondition

			//组合自定义条件
			String customCondition=getCustomCondition(page, viewCode, type, processKey, params,list,queryEntity);
			if (customCondition!=null&&customCondition.trim().length() > 0) {
				if (hasWhere){
					s.append(" AND ");
				}else{
					s.append(" WHERE (");
					hasWhere = true;
				}
				s.append(customCondition);
			}
			s.append(") ");

			//一个实体只有一个权限操作
			String powerCode = viewCode + "_self";
			View view = viewServiceFoundation.getView(viewCode);
			ExtraView extraView = null;
			boolean isTreeView=false;
			boolean needPermission = true;
			if(view.getType() == ViewType.REFERENCE || view.getType() == ViewType.REFTREE){
				if(view.getShowType().equals(ShowType.PART)){
					if(null == permissionCode){
						needPermission = false;
					}else{
						//取布局视图
						View layoutView = viewServiceFoundation.getView(permissionCode);
						if(null != layoutView && !layoutView.getIsPermission()){//不启用权限
							needPermission = false;
						}
						if(null == layoutView) {
							layoutView = getLayoutView(permissionCode);
							if(null != layoutView && !layoutView.getIsPermission()){//不启用权限
								needPermission = false;
							}
						}
					}
				} else {
					if(null != view && !view.getIsPermission()){//不启用权限
						needPermission = false;
					}
				}
			}
			if(needPermission){
				if(view.getType() == ViewType.REFERENCE || view.getType() == ViewType.REFTREE){
					if(view.getIsPermission() && view.getPermissionCode().trim().length() > 0){
						powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + view.getPermissionCode();
					}
				}else if(null != dgCode && !"".equals(dgCode)){
					DataGrid dataGrid = viewServiceFoundation.getDataGrid(dgCode);
					if(null != dataGrid.getDataGridType() && dataGrid.getDataGridType() == 1 && null != dataGrid.getIsPermission() && dataGrid.getIsPermission()){
						powerCode = dataGrid.getTargetModel().getCode() + "_" + dataGrid.getPermissionCode();
					}
				}
				String powerCodeSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.code = ? and m.valid = 1";
				List<Object> powerCodeList =  ecdCommomDao.createNativeQuery(powerCodeSql, powerCode).list();
				if(powerCodeList.size() > 0) {
					powerCode = powerCodeList.get(0).toString();
				} else {
					String powerOperateSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.entity_Code=? and m.valid=1 and Power_Flag=1";
					List<Object> checkList =  ecdCommomDao.createNativeQuery(powerOperateSql, "SESECD_1.0.0_ecdPanel").list();
					if(checkList.size()>0){
						powerCode = checkList.get(0).toString();
					}
				}
					String	pc = dataPermissionService.getBaseModelUnlimitedPowerCondition("\"ecdCommom\"",false,view.getType().toString());
				if (pc.trim().length() > 0) {
					if (hasWhere){
						s.append(" AND (");
					}else{
						s.append(" WHERE (");
						hasWhere = true;
					}
					s.append(pc);
					if(queryCond.length() > 0){
						s.append(queryCond);
					}
					s.append(")");
				} else if(queryCond.toString().trim().length() > 0){
					if(")".equals(queryCond.toString().trim())){
						s.append(queryCond);
					}else{
						s.append(" WHERE (");
						s.append(queryCond);
						s.append(")");
					}
				}
			}


			if (referenceCondition != null && referenceCondition.length() > 0) {
				s.append(" AND (").append(referenceCondition).append(")");
			}
            //包含OR条件，需要放到最后拼接
			if(null != stateInfoLocal.get() && null != stateInfoLocal.get().get("stateInfo") && Boolean.valueOf(stateInfoLocal.get().get("stateInfo"))) {
				Map<String, String> map = stateInfoLocal.get();
				if (null != map.get("operator") &&	((null != map.get("likeArr")) || (null != map.get("equalsArr")) || (null != map.get("isEffect") && Boolean.valueOf(map.get("isEffect")))
                		|| (null != map.get("isVoid") && Boolean.valueOf(map.get("isVoid"))))) {
					if (StringUtils.equals(map.get("operator"), "like") || StringUtils.equals(map.get("operator"), "=")) {
                        String likeArr = new String();
                        if (StringUtils.equals(map.get("operator"), "like")) {
                            likeArr = map.get("likeArr");
                        } else if (StringUtils.equals(map.get("operator"), "=")) {
                            likeArr = map.get("equalsArr");
                        }
						String isEffect = map.get("isEffect");
						String isVoid = map.get("isVoid");
						//模糊查询sql
						StringBuilder likeSql = new StringBuilder();
						if(null!= likeArr && null != likeArr) {
						    if(type!=Sql.TYPE_LIST_PENDING){
							    likeSql.append(" \"ecdCommom\".TABLE_INFO_ID IN (  SELECT   \"p\".TABLE_INFO_ID " +
									    "   FROM   " + Pending.TABLE_NAME + " \"p\" " +
									    "  WHERE    " + likeArr + " ) ");
						    }else{
						        likeSql.append(" " + likeArr + " ");
						    }
						}
						//生效状态sql
						StringBuilder effectSql = new StringBuilder();
						if(null != isEffect && Boolean.valueOf(isEffect)){
							effectSql.append(" \"ecdCommom\".status = 99 ");
						}
						//作废状态sql
						StringBuilder voidSql = new StringBuilder();
						if(null != isVoid && Boolean.valueOf(isVoid)){
							voidSql.append(" \"ecdCommom\".status = 0 ");
						}
						//sql拼接
						Boolean isWhere=false;
						if (hasWhere) {
							s.append(" AND ");
						} else {
							s.append(" WHERE (");
							hasWhere = true;
						}
                        if(!StringUtils.isEmpty(likeSql)){
							s.append(" ( "+ likeSql);
							if(!StringUtils.isEmpty(voidSql)){
								s.append(" OR " + voidSql);
							}
							if(!StringUtils.isEmpty(effectSql)){
								s.append(" OR " + effectSql);
							}
						}else{
							if(!StringUtils.isEmpty(voidSql)){
								s.append(" ( "+ voidSql);
								if(!StringUtils.isEmpty(effectSql)){
									s.append(" OR " + effectSql);
								}
							}else{
								if(!StringUtils.isEmpty(effectSql)){
									s.append(" ( " + effectSql);
								}
							}
						}
						s.append(" ) ");
						if(isWhere){
							s.append(" ) ");
						}
					}
				}
			}
			// 开始处理排序，避免在统计时带入排序条件
			StringBuilder orderPart = new StringBuilder();
			orderPart.append(" ORDER BY ");
			String colOrderByStr = runtimeSqlMap.get(Sql.TYPE_USED_ORDERBY);
			if(null != colOrderByStr && colOrderByStr.length() > 0) {
				if(sortOrderByStr.toString().length() > 0) {
					String[] colOrderByArr = colOrderByStr.substring(1).split(",");
					String[] sortOrderByArr = sortOrderByStr.toString().split(" ");
				    for(String colOrderBy : colOrderByArr) {
					    if(colOrderBy.toUpperCase().indexOf(sortOrderByArr[0].toUpperCase() + " ") > -1) {
						    colOrderByStr = colOrderByStr.replaceAll("(?i)," + colOrderBy, "");
						    break;
					    }
				    }
				    orderPart.append(sortOrderByStr).append(",");
					if (colOrderByStr.length() > 0) {
					    orderPart.append(colOrderByStr.substring(1)).append(",");
					}
				} else {
					orderPart.append(colOrderByStr.substring(1)).append(",");
				}
			} else {
				if(sortOrderByStr.toString().length() > 0) {
					orderPart.append(sortOrderByStr.toString()).append(",");
				}
			}
			if(type == Sql.TYPE_LIST_PENDING) {
				orderPart.append(" \"p\".ID DESC");
			} else {
				if(isTreeView) {
					orderPart.append(" \"ecdCommom\".lay_rec ASC, \"ecdCommom\".SORT ASC");
				} else {

					if(null != orderPart) {
						if(orderPart.indexOf("\"ecdCommom\".ID") == -1) {
							orderPart.append(" \"ecdCommom\".ID DESC");
						}else {
							orderPart = new StringBuilder(orderPart.substring(0, orderPart.length()-1));
						}
					}else {
							orderPart.append(" \"ecdCommom\".ID DESC");
					}
				}
			}
			Object[] arr = list.toArray();
			totalSql.append(s);
			String realSql = totalSql.toString() + orderPart.toString();
			Map<String, String> maps = new HashMap<String, String>();
			countSql += " ( " + totalSql.toString() + " ) T";
			// 突破ORACLE 30个字符限制
			if(DbUtils.getDbName().equals("oracle")){
				realSql = matchSql(pattern, realSql, "T", maps, 2, 4);
				countSql = replaceSql(pattern, countSql, maps, 4, 2);
				realSql = matchSql(p, realSql, "E", null, 4, 6);
				countSql = matchSql(p, countSql, "E", null, 4, 6);
				//组织总条数，合计SQL
				countSql = replaceSql(countPattern, countSql, maps, 4, 2);
				if(isTreeView &&  !page.isPaging()) {
					page.setPaging(false);
				}
			}
			// SUM数据强转decimal，防止溢出
			Matcher sumMatcher = sumPattern.matcher(countSql);
			while (sumMatcher.find()) {
				String tag = sumMatcher.group(2);
				Pattern sumPattern1 = Pattern.compile("SUM\\(" + tag + "\\)");
				Matcher matcher1 = sumPattern1.matcher(countSql);
				countSql = matcher1.replaceAll("SUM(CAST(" + tag + " AS DECIMAL(38,6)))");
			}
			//计算条数
			if (page.needCount()) {
				//String countSql = "SELECT COUNT(*) FROM (" + realSql + ")";
				//Long count = ((Number) ecdCommomDao.createNativeQuery(countSql, arr).uniqueResult()).longValue();
				Long count = 0l;
				Map<String, BigDecimal> resultTotals = new HashMap<String, BigDecimal>();
                //处理sql中混用占位符改为？+数字
                List<String> paramNameList = getParamNameList(countSql);
                Pattern pattern = Pattern.compile("\\?");
                int index = 0;
                StringBuffer buffer = new StringBuffer();
                Matcher matcher = pattern.matcher(countSql);
				while (matcher.find()) {
					matcher.appendReplacement(buffer, ":" + (index++));
				}
				matcher.appendTail(buffer);
				NativeQuery query=ecdCommomDao.createNativeQuery(buffer.toString());
				Integer k=0;
				for(;k<arr.length;k++){
                	query.setParameter(k.toString(),arr[k]);
                }
				if(customerSqlListMap!=null&&!customerSqlListMap.isEmpty()){
					for(String key:paramNameList){
						query.setParameterList(key, (Object[])customerSqlListMap.get(key));
					}
				}
				List<Map<String, Object>> resultCountList = (List<Map<String, Object>>)query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if (null != resultCountList && !resultCountList.isEmpty()) {
					Map<String, Object> resultCounts = resultCountList.get(0);
					for (Map.Entry<String, Object> entry : resultCounts.entrySet()) {
						String key = entry.getKey();
						if(maps.containsKey(key)) {
							key = (String) maps.get(key);
						}
						if (key.equalsIgnoreCase("count")) {
							count = ((Number) (null == entry.getValue() ? 0 : entry.getValue())).longValue();
						} else {
							resultTotals.put(key, BigDecimal.valueOf(((Number) (null == entry.getValue() ? 0 : entry.getValue())).doubleValue()));
						}
					}
				}
				//22.3.9 修改现场树结构单据查询超500报错问题
				//if(!page.isExportFlag() && !page.isPaging() && count > page.getMaxPageSize()) {
				//	  throw new BAPException(BAPException.Code.RESULT_COUNT_EXCEED_MAX_PAGE_SIZE);
				//}

				page.setTotalCount(count);
				page.setResultTotals(resultTotals);
			}
			if(!noQueryFlag)  {
			//处理sql中混用占位符改为？+数字
				List<String> paramNameList = getParamNameList(realSql);
                Pattern pattern = Pattern.compile("\\?");
                int index = 0;
                StringBuffer buffer = new StringBuffer();
                Matcher matcher = pattern.matcher(realSql);
                while (matcher.find()) {
                    matcher.appendReplacement(buffer, ":" + (index++));
                }
                matcher.appendTail(buffer);
				NativeQuery query=ecdCommomDao.createNativeQuery(buffer.toString());
				Integer k=0;
                for(;k<arr.length;k++){
                	query.setParameter(k.toString(),arr[k]);
                }
				if(customerSqlListMap!=null&&!customerSqlListMap.isEmpty()){
					for(String key:paramNameList){
						query.setParameterList(key, (Object[])customerSqlListMap.get(key));
					}
				}
				List<SESECDEcdCommom> result = new ArrayList<SESECDEcdCommom>();
				if(page.isExportFlag()) {
					result = getResult(page, query, new PendingResultTransformer(SESECDEcdCommom.class,ecdCommomDao,maps,true));
				}else {
					result = getResult(page, query, new PendingResultTransformer(SESECDEcdCommom.class,ecdCommomDao,maps));
				}
                modelServiceFoundation.initCacheData();
                page.setResult(result);

                Map<String, SESECDUploadListPermissionService> customUploadListService = applicationContext.getBeansOfType(SESECDUploadListPermissionService.class);
				if(null != hasAttachment && hasAttachment) {
					List<Long> ids = new ArrayList<>();
					for (SESECDEcdCommom ecdCommom : result) {
						ids.add(ecdCommom.getId());
					}
					List<Document> documents = documentService.getByTypeAndLinkIdRange("SESECD_ecdPanel_EcdCommom", ids);
                    for (SESECDUploadListPermissionService ups : customUploadListService.values()) {
                    	if (ups.needCheckPermission(ids, documents, "SESECD_ecdPanel_EcdCommom", null,viewCode)) {
                    		ups.checkPermission(ids, documents, "SESECD_ecdPanel_EcdCommom", null,viewCode);
                    	}
                    }
					for (SESECDEcdCommom ecdCommom : result) {
						int count = 0;
						Document document1 = null;
						for (Document document : documents) {
							if (ecdCommom.getId().equals(document.getLinkId())) {
								count++;
								if (null == document1) {
									document1 = document;
								}
							}
						}
						if (count > 0) {
							ecdCommom.setDocument(document1);
							ecdCommom.setBapAttachmentInfo(document1.getName() + "@_@BAP@_@" + InternationalResource.get("Button.text.more", getCurrentLanguage()) + "(" + count + ")");
						}
					}
				}


				if (null != result && result.size() > 0) {
                    // 列表拖出来的附件字段
                    List<String> attachPropertyCodeList = new ArrayList<>();
					// 列表拖出来的图片字段
                    List<String> picturePropertyCodeList = new ArrayList<>();
					// 列表拖出来的签名字段
                    List<String> signaturePropertyCodeList = new ArrayList<>();
					if(!StringUtils.isEmpty(dgCode)){
                        DataGrid dataGrid = viewServiceFoundation.getDataGrid(dgCode);
						String dgJson=viewServiceFoundation.getDataGrid(dgCode).getDataGridJson();
						com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSON.parseObject(dgJson);
						com.alibaba.fastjson.JSONArray fields = (com.alibaba.fastjson.JSONArray) jsonObj.get("fields");
						for(int i=0;i<fields.size();i++){
							com.alibaba.fastjson.JSONObject obj = (com.alibaba.fastjson.JSONObject) fields.get(i);
							if ("PROPERTYATTACHMENT".equals(obj.get("columnType")) && null != obj.get("propertyCode")) {
								attachPropertyCodeList.add(obj.get("propertyCode").toString());
							} else if ("PICTURE".equals(obj.get("columnType")) && null != obj.get("propertyCode")) {
								picturePropertyCodeList.add(obj.get("propertyCode").toString());
							} else if ("SIGNATURE".equals(obj.get("columnType")) && null != obj.get("propertyCode")) {
								signaturePropertyCodeList.add(obj.get("propertyCode").toString());
							}
						}
					}else{
						String shadowViewCode = viewCode;
						if (view.getIsShadow()) {
							shadowViewCode = viewServiceFoundation.getShadowViewFromView(viewCode).getCode();
						}
						Map _confMap=viewServiceFoundation.getExtraViewFromView(shadowViewCode).getConfigMap();
						Map _layoutMap = (Map) _confMap.get("layout");
						List<Map> ls = (List<Map>) _layoutMap.get("sections");
                    for(Map lm:ls){
                        if(lm.get("regionType").equals("LISTPT")){
                            List<Map> lc = (List<Map>) lm.get("cells");
                            for(Map lk:lc){
                                if ("PROPERTYATTACHMENT".equals(lk.get("columnType")) && null != lk.get("propertyCode")) {
                                    attachPropertyCodeList.add(lk.get("propertyCode").toString());
                                }else if("PICTURE".equals(lk.get("columnType")) && null != lk.get("propertyCode")){
									picturePropertyCodeList.add(lk.get("propertyCode").toString());
								}else if("SIGNATURE".equals(lk.get("columnType")) && null != lk.get("propertyCode")){
									signaturePropertyCodeList.add(lk.get("propertyCode").toString());
								}
                            }
                        }
                    }
                    }
                    if (null != attachPropertyCodeList && attachPropertyCodeList.size() > 0) {
                        List<Long> linkIdList = result.stream().map(item -> item.getId()).collect(Collectors.toList());
                        Map<Long, Map<String, List<Document>>> groupDocumentMap = documentService.getByLinkIdAndTypeAndPropertyCode(linkIdList, "SESECD_ecdPanel_EcdCommom", attachPropertyCodeList);
                        if (null != groupDocumentMap) {
                            for (SESECDEcdCommom ecdCommom : result) {
                                Long linkId = ecdCommom.getId();
                                if (groupDocumentMap.containsKey(linkId)) {
                                    Map<String, List<Document>> documentMap = groupDocumentMap.get(linkId);
                                    for (Map.Entry<String, List<Document>> entry : documentMap.entrySet()) {
                                        String filePropertyCode = entry.getKey();
                                        List<Document> documentList = entry.getValue();
                                        for (SESECDUploadListPermissionService ups : customUploadListService.values()) {
                                            if (ups.needCheckPermission(Collections.singletonList(linkId), documentList, "SESECD_ecdPanel_EcdCommom", filePropertyCode,viewCode)) {
                                                ups.checkPermission(Collections.singletonList(ecdCommom.getId()), documentList, "SESECD_ecdPanel_EcdCommom", filePropertyCode,viewCode);
                                            }
                                        }
                                        if (null == documentList || documentList.size() == 0) {
                                            continue;
                                        }
                                        String filePropertyName = modelServiceFoundation.getProperty(filePropertyCode).getName();
                                        try {
                                            String setDocumentMethodName = "set" + fLTU(filePropertyName) + "Document";
                                            Method setDocumentMethod = ecdCommom.getClass().getMethod(setDocumentMethodName, Document.class);
                                            setDocumentMethod.invoke(ecdCommom, documentList.get(0));

                                            String setAttachementInfoMethodName = "set" + fLTU(filePropertyName) + "AttachementInfo";
                                            Method setAttachementInfoMethod = ecdCommom.getClass().getMethod(setAttachementInfoMethodName, String.class);
                                            setAttachementInfoMethod.invoke(ecdCommom, documentList.get(0).getName() + "@_@BAP@_@" + InternationalResource.get("Button.text.more", getCurrentLanguage()) + "(" + documentList.size() + ")");
                                        } catch (Exception e) {
                                            log.error(e.getMessage(), e);
                                            throw new BAPException(e);
                                        }
                                    }
                                }
                            }
                        }

                    }


				if(null != picturePropertyCodeList && picturePropertyCodeList.size()>0){
				    List<Long> linkIdList = result.stream().map(item -> item.getId()).collect(Collectors.toList());
					Map<Long, Map<String, List<Document>>> groupDocumentMap = documentService.getByLinkIdAndTypeAndPropertyCode(linkIdList, "SESECD_ecdPanel_EcdCommom", "pic",  picturePropertyCodeList);
                    for (SESECDEcdCommom ecdCommom : result) {
                    }
                }
                if(null != signaturePropertyCodeList && signaturePropertyCodeList.size()>0){
				    List<Long> linkIdList = result.stream().map(item -> item.getId()).collect(Collectors.toList());
					Map<Long, Map<String, List<Document>>> groupDocumentMap = documentService.getByLinkIdAndTypeAndPropertyCode(linkIdList, "SESECD_ecdPanel_EcdCommom", "builtSignature",  signaturePropertyCodeList);
                for (SESECDEcdCommom ecdCommom : result) {
                }
                }
                }

				if(exportSql.trim().isEmpty()){
					getConfigAssoPropsResult(viewCode, dgCode, result); // 获取配置的关联模型字段的结果集
				}
			}
		}
		stateInfoLocal.remove();
	}

	/**
     * pc为空
     * @param s
     * @param hasWhere
     * @return
     */
    private boolean getPcWhereSql(StringBuilder s, boolean hasWhere, String modelName) {
        String cidStr = cidTcl.get();
        cidTcl.remove();
        if (StringUtils.isNotEmpty(cidStr)){
            StringBuffer sb = new StringBuffer();
            sb.append(modelName)
                    .append(".CID in (")
                    .append(cidStr)
                    .append(")");
            if (hasWhere){
                s.append(" AND ");
            }else{
                s.append(" WHERE (");
                hasWhere = true;
            }
            s.append(sb.toString());
        }
        return hasWhere;
    }

	/**
     * 快速查询条件改造
     * @param fastQueryCond
     * @param queryEntity
     * @return
     */
    private String getFastQueryCondCidStr(String fastQueryCond, QueryEntity queryEntity) {
        com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSON.parseObject(fastQueryCond);
        com.alibaba.fastjson.JSONArray subconds = (com.alibaba.fastjson.JSONArray) jsonObj.get("subconds");

        for (int i = 0; i < subconds.size(); i++) {
            com.alibaba.fastjson.JSONObject obj = (com.alibaba.fastjson.JSONObject) subconds.get(i);
            String joinInfo = (String) obj.get("joinInfo");
            if (StringUtils.isNotEmpty(joinInfo) && StringUtils.equals(joinInfo, "BASE_COMPANY,ID,XT_BASE_MODELVS,COMP_OBJ")){
                com.alibaba.fastjson.JSONArray subArr = (com.alibaba.fastjson.JSONArray) obj.get("subconds");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < subArr.size(); j++) {
                    if (0 == j) {
                        continue;
    				}
                    com.alibaba.fastjson.JSONObject cidObj = (com.alibaba.fastjson.JSONObject) subArr.get(j);
                    if (1 == j) {
                        stringBuilder.append((String) cidObj.get("value"));
                    } else {
                        stringBuilder.append(",").append(cidObj.get("value"));
			     	}
				}
                cidTcl.set(stringBuilder.toString());
                subconds.remove(obj);
            }
        }
        fastQueryCond = jsonObj.toString();
        queryEntity.setFastQueryCond(fastQueryCond);
        return fastQueryCond;
    }

    /**
     * 拼接where所属公司查询条件
     * @param pc
     * @return
     */
    private String getPcRefCidSql(String pc, String modelV) {
        StringBuffer pcSb = new StringBuffer(pc);
        String cidStr = cidTcl.get();
        cidTcl.remove();
        if (StringUtils.isNotEmpty(cidStr)){
            if(!pc.trim().endsWith("AND")){
                pcSb.append(" AND ");
            }
            pcSb.append(modelV).append(".CID in (").append(cidStr).append(")");
        }
        return pcSb.toString();
    }

    /**
     * 关联公司SQL
     * @param sql
     * @return
     */
    private String getRefCompanySql(String sql, String modelV) {
        String cidSql = sql.replace(" FROM ", ",\"baseCompany\".NAME AS \"cidName\" FROM ");
        StringBuilder totalSql = new StringBuilder(cidSql);
        totalSql.append(" LEFT OUTER JOIN BASE_COMPANY \"baseCompany\" ON ").append(modelV).append(".CID").append("=\"baseCompany\".ID");

        return totalSql.toString();
    }

	/**
	 * 快速查询单据状态 模块编码为null，则国际化查询所有   模块编码不为null，则查询该模块国际化
	 * @param fastQueryCond
	 * @param queryEntity
	 * @param currentSqlType
	 * @param moduleCode
	 * @return
	 */
	private String getFastQueryCondPendState(String fastQueryCond, QueryEntity queryEntity, int currentSqlType, String moduleCode) {
		com.alibaba.fastjson.JSONObject jsonObj = com.alibaba.fastjson.JSON.parseObject(fastQueryCond);
		com.alibaba.fastjson.JSONArray subconds = (com.alibaba.fastjson.JSONArray) jsonObj.get("subconds");

		for (int i = 0; i < subconds.size(); i++) {
			com.alibaba.fastjson.JSONObject obj = (com.alibaba.fastjson.JSONObject) subconds.get(i);
			String stateInfo = (String) obj.get("pendKey");
			if (StringUtils.isNotEmpty(stateInfo) && StringUtils.equals(stateInfo,"pendState")){//包含单据状态查询的快速查询条件
				Map<String, String> map = new HashMap<>();
				map.put("stateInfo","true");
				//查询方式 like
				if(StringUtils.equals((String)obj.get("operator"),"like")){
					map.put("operator","like");
					//处理模糊查询的国际化结果
					if(StringUtils.isEmpty((String)obj.get("value"))){
						break;
					}
					String value = (String) obj.get("value");
					//模糊查询的keys，通过关联pending查询符合的tableindfoid
					//List<String> keysByValueLike = new ArrayList<>(InternationalResource.getKeysByValueLike(filtrateSQLLike(value), getCurrentLanguage()));
					String likeArr = getStrByI18(value, moduleCode);
					map.put("likeArr", likeArr);
					if(currentSqlType == Sql.TYPE_LIST_QUERY){
						//生效状态的value，pending表中不包含，因此需要业务表中status=99进行判断
						if(InternationalResource.get("foundation.common.workflow.takeeffect",getCurrentLanguage()).indexOf(value)>=0){
							map.put("isEffect","true");
						}
						//作废状态的value，pending表中不包含，因此需要业务表中status=0进行判断
						if(InternationalResource.get("ec.common.void",getCurrentLanguage()).indexOf(value)>=0){
							map.put("isVoid","true");
						}
					}
				}else if(StringUtils.equals((String)obj.get("operator"),"=")){
					map.put("operator","=");
					//处理模糊查询的国际化结果
					if(StringUtils.isEmpty((String)obj.get("value"))){
						break;
					}
					String value = (String) obj.get("value");
					String equalsArr = getEqualsArr(value);
					map.put("equalsArr", equalsArr);
					if(currentSqlType == Sql.TYPE_LIST_QUERY){
						//生效状态的value，pending表中不包含，因此需要业务表中status=99进行判断
						if(value.indexOf("foundation.common.workflow.takeeffect")>=0){
							map.put("isEffect","true");
						}
						//作废状态的value，pending表中不包含，因此需要业务表中status=0进行判断
						if(value.indexOf("ec.common.void")>=0){
							map.put("isVoid","true");
						}
					}
				}
				stateInfoLocal.set(map);
				subconds.remove(obj);
			}
		}
		fastQueryCond = jsonObj.toString();
		queryEntity.setFastQueryCond(fastQueryCond);
		return fastQueryCond;
	}

    //单据状态精准查询，根据逗号隔开的国际化keys，进行长度1000处理后返回sql语句
	private String getEqualsArr(String value) {
		String[] keyArr = value.split(",");
		List<String> arr = Arrays.asList(keyArr);
		String result = " \"p\".TASK_DESCRIPTION IN ";
		if(arr.size()==0){
			return result + " (\'\') ";
		}
		//数据库类型
		DBTYPE dbtype = ecdCommomDao.getDBType();
		if(null!=arr && arr.size()>0){
			List activityNameList = new ArrayList();
			((List)activityNameList).addAll(arr);
			if(arr.size()>1000 && dbtype.equals(DBTYPE.MSSQL)){
				activityNameList =  activityNameList.subList(0,1000);
			}
			//
			int index = activityNameList.size()/1000;
			if(index == 0){
				result += listToSql(activityNameList);
			}else{
				for(int i=0;i<index;++i){
					List<String> subList = activityNameList.subList(1000 * i, 1000 * i + 1000);
					if(i==0){
						result += listToSql(subList);
					}else{
						result += " OR \"p\".TASK_DESCRIPTION IN " + listToSql(subList);
					}
				}
				if (activityNameList.size() % 1000 != 0) {
					List<String> subList = activityNameList.subList(index * 1000, activityNameList.size());
					result += " OR \"p\".TASK_DESCRIPTION IN " + listToSql(subList);
				}
			}
		}
		return result;
	}

	private String getStrByI18(String value, String moduleCode) {
		String result = " \"p\".TASK_DESCRIPTION IN ";
		if(StringUtils.isEmpty(result)){
			return result + " (\'\') ";
		}
		Set<String> keysByValueLike =new HashSet<>();
		if(StringUtils.isEmpty(moduleCode)){
			keysByValueLike = InternationalResource.getKeysByValueLike(filtrateSQLLike(value), getCurrentLanguage());
		}else{
			keysByValueLike = InternationalResource.getModuleKeysByValueLike(filtrateSQLLike(value),moduleCode);
		}
		if(keysByValueLike.size()==0){
			return result + " (\'\') ";
		}
        DBTYPE dbtype = ecdCommomDao.getDBType();
		if(null!=keysByValueLike && keysByValueLike.size()>0){
			List activityNameList = new ArrayList();
			((List)activityNameList).addAll(keysByValueLike);
			if(keysByValueLike.size()>1000 && dbtype.equals(DBTYPE.MSSQL)){
				activityNameList =  activityNameList.subList(0,1000);
			}
			//
			int index = activityNameList.size()/1000;
			if(index == 0){
				result += listToSql(activityNameList);
			}else{
				for(int i=0;i<index;++i){
					List<String> subList = activityNameList.subList(1000 * i, 1000 * i + 1000);
					if(i==0){
						result += listToSql(subList);
					}else{
						result += " OR \"p\".TASK_DESCRIPTION IN " + listToSql(subList);
					}
				}
				if (activityNameList.size() % 1000 != 0) {
					List<String> subList = activityNameList.subList(index * 1000, activityNameList.size());
					result += " OR \"p\".TASK_DESCRIPTION IN " + listToSql(subList);
				}
			}
		}
		return result;
	}

	private String listToSql(List<String> keysByValueLike) {
		String result = "\'\'";
		if(keysByValueLike.size()>0){
			for(int i=0;i<keysByValueLike.size();i++){
				if(i==0){
					result = " \'" + keysByValueLike.get(i) +"\' " ;
				}else{
					result += ", \'" + keysByValueLike.get(i) +"\' " ;
				}
			}
		}
		return " (" + result + ") ";
	}

	private static String filtrateSQLLike(String likeStr) {
		String str = "";
		str = StringUtils.replace(likeStr, "&", "&&");
		str = StringUtils.replace(str, "%", "&%");
		str = StringUtils.replace(str, "_", "&_");
		return str;
	}

	private View getLayoutView(String permissionCode) {
		if(null!=permissionCode&&permissionCode.indexOf("SESECD_1.0.0_ecdPanel")!=-1) {
				String personalCode=permissionCode.substring(permissionCode.indexOf("SESECD_1.0.0_ecdPanel")+"SESECD_1.0.0_ecdPanel".length()+1);
				if(null!=personalCode&&personalCode.trim().length()>0)  {
						return viewServiceFoundation.getViewWithPermissionCode("SESECD_1.0.0_ecdPanel",personalCode);
				}
		}
		return null;
	}

	private void getConfigAssoPropsResult(String viewCode, List<SESECDEcdCommom> result) {
		getConfigAssoPropsResult(viewCode, null, result);
	}

	private void getConfigAssoPropsResult(String viewCode,String dataGridCode, List<SESECDEcdCommom> result) {
		View view = null;
		DataGrid dataGrid = null;
		List<Field> fields = null;
		Model origModel = null;
		if (null != dataGridCode && !"".equals(dataGridCode)) {
			dataGrid = viewServiceFoundation.getDataGrid(dataGridCode);
			if(null == dataGrid){
				return;
			}
			fields = viewServiceFoundation.getFieldsFromDg(dataGridCode);
			origModel = viewServiceFoundation.getTargetModelFromDg(dataGridCode);
		} else {
			view = viewServiceFoundation.getView(viewCode);
			if(null == view){
				return;
			}
			if (view.getIsShadow()) {
				view = viewServiceFoundation.getShadowViewFromView(viewCode);
				viewCode = view.getCode();
			}
			fields = viewServiceFoundation.getFieldsFromView(view.getCode());
			origModel = viewServiceFoundation.getAssModelFromView(viewCode);
		}
		String origId = modelServiceFoundation.getPropertyColumnName(origModel, "id", false);

		List<Long> ids = new ArrayList<Long>();
		Map<Long, SESECDEcdCommom> rsMap = new HashMap<Long, SESECDEcdCommom>();
		for (SESECDEcdCommom ecdCommom : result) {
			ids.add(ecdCommom.getId());
			rsMap.put(ecdCommom.getId(), ecdCommom);
		}
		if (null != fields) {
			for (Field f : fields) {
				if (f.getRegionType() == RegionType.LISTPT && (f.getCode().startsWith(viewCode + "_LISTPT_ASSO_") || f.getCode().startsWith(dataGridCode + "_LISTPT_ASSO_"))) { // 关联模型字段
					Map<String, Object> map = (Map<String, Object>) SerializeUitls.deserialize(f.getConfig());
					Map<String, Object> fieldMap = (Map<String, Object>) map.get("field");
					if (fieldMap.get("assoFlag") != null && "true".equalsIgnoreCase(fieldMap.get("assoFlag").toString())) {
						String assoConfig = (String) fieldMap.get("assoConfig");
						Map<String, Object> configMap = (Map<String, Object>) SerializeUitls.deserialize(assoConfig);
						if (configMap != null && configMap.size() > 0) {
							String sepBegin1 = configMap.get("separatorBeginLevel1") == null ? "" : configMap.get("separatorBeginLevel1").toString();
							String sepEnd1 = configMap.get("separatorEndLevel1") == null ? "" : configMap.get("separatorEndLevel1").toString();
							String sepBegin2 = configMap.get("separatorBeginLevel2") == null ? "" : configMap.get("separatorBeginLevel2").toString();
							String sepEnd2 = configMap.get("separatorEndLevel2") == null ? "" : configMap.get("separatorEndLevel2").toString();

							List<Map<String, Object>> configList = (List<Map<String, Object>>) configMap.get("config");
							Map<String, Object> cfg1 = configList.get(0);
							String propertyCode1 = (String) cfg1.get("propertyCode");
							String[] propArr1 = propertyCode1.split("\\|\\|");
							Property ftProp1 = modelServiceFoundation.getProperty(propArr1[0]);
							Property prop1 = findAssoProperty(modelServiceFoundation.findModelFromProperty(ftProp1.getCode()).getCode(), origModel.getCode());
							Model prop1Model = modelServiceFoundation.findModelFromProperty(prop1.getCode());
							String id1 = modelServiceFoundation.getPropertyColumnName(prop1Model, "id", false);

							Property prop2 = null;
							Model prop2Model = null;
							String id2 = null;
							for (Map<String, Object> cfgMap : configList) {
								String level = String.valueOf(cfgMap.get("level"));
								if ("2".equals(level)) {
									String propertyCode2 = (String) cfgMap.get("propertyCode");
									String[] propArr2 = propertyCode2.split("\\|\\|");
									Property ftProp2 = modelServiceFoundation.getProperty(propArr2[0]);
									prop2 = findAssoProperty(modelServiceFoundation.findModelFromProperty(ftProp2.getCode()).getCode(), prop1Model.getCode());
									prop2Model = modelServiceFoundation.findModelFromProperty(prop2.getCode());
									id2 = modelServiceFoundation.getPropertyColumnName(prop2Model, "id", false);
									break;
								}
							}
							List<List<Map<String, Object>>> list1 = new ArrayList<List<Map<String, Object>>>();
							List<List<Map<String, Object>>> list2 = new ArrayList<List<Map<String, Object>>>();
							String lastSep2Level1 = "", lastSep2Level2 = "";
							for (int index = 0; index < configList.size(); index++) {
								Map<String, Object> cfg = configList.get(index);
								String level = String.valueOf(cfg.get("level"));
								String sep1 = cfg.get("separator1") == null ? "" : cfg.get("separator1").toString();
								String sep2 = cfg.get("separator2") == null ? "" : cfg.get("separator2").toString();
								String method = cfg.get("method") == null ? "" : cfg.get("method").toString().toUpperCase();
								String propertyCode = (String) cfg.get("propertyCode");
								String propertyType = String.valueOf(cfg.get("propertyType"));
								StringBuilder fromSql = new StringBuilder(" from ");
								StringBuilder whereSql = new StringBuilder(" where ");
								if ("1".equals(level)) {
									lastSep2Level1 = sep2;
									fromSql.append(prop1Model.getTableName());
									whereSql.append(prop1Model.getTableName()).append(".").append("VALID = 1");
								} else {
									lastSep2Level2 = sep2;
									fromSql.append(prop2Model.getTableName());
									whereSql.append(prop2Model.getTableName()).append(".").append("VALID = 1");
								}
								String[] propArr = propertyCode.split("\\|\\|");
								for (int i = 0; i < propArr.length - 1; i++) {
									Property tmpProp = modelServiceFoundation.getProperty(propArr[i]);
									Model tmpModel = modelServiceFoundation.findModelFromProperty(tmpProp.getCode());
									Property tmpAssoProp = modelServiceFoundation.findAssociatedProperty(tmpProp.getCode());
									Model tmpAssoModel = modelServiceFoundation.findModelFromProperty(tmpAssoProp.getCode());
									fromSql.append(" left join ").append(tmpAssoModel.getTableName());
									fromSql.append(" on ").append(tmpModel.getTableName()).append(".").append(tmpProp.getColumnName()).append(" = ")
											.append(tmpAssoModel.getTableName()).append(".").append(tmpAssoProp.getColumnName());
									whereSql.append(" and ").append(tmpAssoModel.getTableName()).append(".").append("VALID = 1").append(" ");
								}

								String selectSql = "select " + origModel.getTableName() + "." + origId + " AS OID";
								Property laProp = modelServiceFoundation.getProperty(propArr[propArr.length - 1]);
								Model laPropModel = modelServiceFoundation.findModelFromProperty(laProp.getCode());
								if (!"".equals(method)) {
									if ("2".equals(level)) {
										selectSql += "," + prop1Model.getTableName() + "." + id1 + " AS ID1";
									}
									if ("COUNT".equalsIgnoreCase(method)) {
										Property laPkProperty = modelServiceFoundation.getPKProperty(laPropModel.getCode());
										selectSql += ", " + method + " (" + laPropModel.getTableName() + "." + laPkProperty.getColumnName() + ") AS VAL";
									} else {
										selectSql += ", " + method + " (" + laPropModel.getTableName() + "." + laProp.getColumnName() + ") AS VAL";
									}
								} else {
									selectSql += ", " + prop1Model.getTableName() + "." + id1 + " AS ID1";
									if ("2".equals(level)) {
										selectSql += ", " + prop2Model.getTableName() + "." + id2 + " AS ID2";
									}
									selectSql += ", " + laPropModel.getTableName() + "." + laProp.getColumnName() + " AS VAL";
									if("SYSTEMCODE".equals(propertyType)){
										selectSql += ", BASE_SYSTEMCODE.VALUE AS REALVAL";
									}
								}

								if ("2".equals(level)) {
									fromSql.append(" left join ").append(prop1Model.getTableName()).append(" on ").append(prop2Model.getTableName())
											.append(".").append(prop2.getColumnName()).append(" = ").append(prop1Model.getTableName()).append(".")
											.append(prop2.getAssociatedProperty().getColumnName());
									whereSql.append(" and ").append(prop1Model.getTableName()).append(".").append("VALID = 1");
								}
								fromSql.append(" left join ").append(origModel.getTableName()).append(" on ").append(prop1Model.getTableName()).append(".")
										.append(prop1.getColumnName()).append(" = ").append(origModel.getTableName()).append(".")
										.append(prop1.getAssociatedProperty().getColumnName());
								if("SYSTEMCODE".equals(propertyType)){
									fromSql.append(",BASE_SYSTEMCODE");
								}
								whereSql.append(" and ").append(origModel.getTableName()).append(".").append("VALID = 1");
								whereSql.append(" and ").append(origModel.getTableName()).append(".").append(origId).append(" in (:ids)");
								if("SYSTEMCODE".equals(propertyType)){
									whereSql.append(" and ").append(laPropModel.getTableName() + "." + laProp.getColumnName()).append("= BASE_SYSTEMCODE.ID");
								}
								String orderSql = " order by ";
								if (!"".equals(method)) {
									whereSql.append(" group by ").append(origModel.getTableName() + "." + origId);
									if ("2".equals(level)) {
										whereSql.append(", ").append(prop1Model.getTableName() + "." + id1);
									}
									orderSql += "2".equals(level) ? "ID1 ASC" : "OID ASC";
								} else {
									orderSql += "2".equals(level) ? "ID2 ASC" : "ID1 ASC";
								}
								String sql = selectSql + fromSql.toString() + whereSql.toString() + orderSql;

								List<Map<String, Object>> rs = new ArrayList<Map<String, Object>>();
								final int PERTIME = 999;
								int count = (ids.size() / PERTIME) + (ids.size() % PERTIME == 0 ? 0 : 1);
								for (int i = 0; i < count; i++) {
									rs.addAll(ecdCommomDao.createNativeQuery(sql)
											.setParameterList("ids", ids.subList(PERTIME * i, PERTIME * i + (i < count - 1 ? PERTIME : (ids.size() % PERTIME))))
											.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
								}
								if (rs.size() > 0) {
									if("SYSTEMCODE".equals(propertyType)){
										for (Map<String, Object> m : rs) {
											String REALVAL = m.get("REALVAL") == null ? "" : String.valueOf(m.get("REALVAL"));
											m.put("VAL", sep1 + InternationalResource.get(REALVAL, getCurrentLanguage()) + sep2);
										}
									}else{
										for (Map<String, Object> m : rs) {
											if(null != m.get("VAL") && !"".equals(String.valueOf(m.get("VAL")))){
												String val = String.valueOf(m.get("VAL"));
												m.put("VAL", sep1 + val + sep2);
											}else{
												m.put("VAL", "");
											}
										}
									}
									if ("1".equals(level)) {
										list1.add(rs);
									} else if ("2".equals(level)) {
										list2.add(rs);
									}
								}
							}
							List<Map<String, Object>> fList1 = concatListValue(list1);
							List<Map<String, Object>> fList2 = concatListValue(list2);
							if (fList2 != null) {
								for (Map<String, Object> tmpMap1 : fList1) {
									long tmpId1 = ((Number) tmpMap1.get("ID1")).longValue();
									StringBuilder val2 = new StringBuilder();
									val2.append(sepBegin2);
									for (Map<String, Object> tmpMap2 : fList2) {
										long tmpID2 = ((Number) tmpMap2.get("ID1")).longValue();
										if (tmpId1 == tmpID2) {
											val2.append((String) tmpMap2.get("VAL"));
										}
									}
									String val = val2.toString();
									if (!val.equals(sepBegin2)) {
										val = val.substring(0, val.length() - lastSep2Level2.length());
									}
									val += sepEnd2;
									tmpMap1.put("VAL", tmpMap1.get("VAL") + val);
								}
							}
							if (fList1 != null) {
								for (Long tabId : rsMap.keySet()) {
									StringBuilder val1 = new StringBuilder(sepBegin1);
									for (Map<String, Object> tmpMap1 : fList1) {
										long tmpTabId = ((Number) tmpMap1.get("OID")).longValue();
										if (tmpTabId == tabId) {
											val1.append((String) tmpMap1.get("VAL"));
										}
									}
									if (!val1.toString().equals(sepBegin1)) {
										String val = val1.toString();
										if (fList2 == null || fList2.size() < 1){
											val = val.substring(0, val.length() - lastSep2Level1.length());
										}
										val += sepEnd1;
										rsMap.get(tabId).setAttrObject(f.getCode().replace(".", "_"), val);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private List<Map<String, Object>> concatListValue(List<List<Map<String, Object>>> list) {
		if (list.size() == 0) {
			return null;
		}
		List<Map<String, Object>> fList = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			List<Map<String, Object>> tmpList = list.get(i);
			for (int j = 0; j < tmpList.size(); j++) {
				String tmpVal = (String) tmpList.get(j).get("VAL");
				Map<String, Object> map = fList.get(j);
				String val = (String) map.get("VAL");
				map.put("VAL", val + tmpVal);
			}
		}
		return fList;
	}

	private Property findAssoProperty(String origModelCode, String assoModelCode) {
		String hql = "from Property p where p.valid = true and p.model.code = ?0 and p.associatedProperty.model.code = ?1 and (p.associatedType = ?2 or p.associatedType = ?3)";
		List<Property> list = ecdCommomDao.findByHql(hql, new Object[] {origModelCode, assoModelCode, Property.ONE_TO_ONE, Property.MANY_TO_ONE});
		return list.get(0);
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void findDataGridPage(DataGrid dg,final Class dgClass,Page dgPage,final Object orgObj,String condition, List<Object> params){
		Map confMap=dg.getConfigMap();
		Map layoutMap = (Map) confMap.get("layout");
		Map propertyMap = (Map) layoutMap.get("listProperty");
		Model targetModel = viewServiceFoundation.getTargetModelFromDg(dg.getCode());
		Property orgProperty = viewServiceFoundation.getOrgPropertyFromDg(dg.getCode());
		Property associatedProperty = modelServiceFoundation.findAssociatedProperty(orgProperty.getCode());
		Model associatedModel = modelServiceFoundation.findModelFromProperty(associatedProperty.getCode());
		if(dg.getDataGridType() ==0 && Boolean.TRUE.equals(propertyMap.get("isTreeView"))){
			String conditionSql = (null != condition && condition.trim().length() > 0) ? " and " + condition : "";
			List<Object> list = new ArrayList<Object>();
			list.add(orgObj);
			if(condition != null && condition.trim().length() > 0 && params != null && params.size() > 0){
				list.addAll(params);
			}
			//String key=dg.getTargetModel().getModelName()+fLTU(dg.getName())+((com.supcon.orchid.orm.entities.AbstractEcFullEntity)orgObj).getId();
			String orgObjId = "";
			try {
				Method method = orgObj.getClass().getMethod("getId");
				orgObjId = String.valueOf((Long) method.invoke(orgObj));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				log.error(e1.getMessage(), e1);
			}
			String key=targetModel.getModelName()+fLTU(dg.getName())+orgObjId;
			String orgPropertyName=orgProperty.getName();
			String targetModelName=fLTL(targetModel.getModelName());
			List treeList = new ArrayList();
			Object[] daoparams=new Object[]{dgClass,key,orgPropertyName,targetModelName,conditionSql,list,new boolean[]{false}};
			try {
				Class thisclass=this.getClass();
				java.lang.reflect.Field daoField=thisclass.getDeclaredField(fLTL(targetModel.getModelName())+"Dao");
				java.lang.reflect.Field serviceField=thisclass.getDeclaredField(fLTL(targetModel.getModelName())+"Service");
				Class serviceType=serviceField.getType();
				Class daoType=daoField.getType();
				Object tree=daoType.getMethod("buildTree",Class.class,String.class,String.class,String.class,String.class,List.class,boolean[].class).invoke(daoField.get(this),daoparams);
				Tree.treeToList((com.supcon.orchid.tree.TreeNode)tree, treeList, false);
				treeList=(List)serviceType.getMethod("findByTreeDataGrid"+associatedModel.getModelName(),new Class[]{List.class}).invoke(serviceField.get(this),new Object[]{treeList});
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			dgPage.setResult(treeList);
		}else{
			DetachedCriteria criteria = DetachedCriteria.forClass(dgClass);
			if(null != orgObj){
				criteria.add(Restrictions.eq(orgProperty.getName(), orgObj));
			}
			criteria.add(Restrictions.eq("valid", true));
			criteria.addOrder(Order.asc("sort"));
			if(!"".equals(condition) && condition.trim().length() > 0) {
				criteria.add(Restrictions.sqlRestriction(condition.replace("\""+fLTL(targetModel.getModelName())+"\".", "{alias}."), params.toArray(), com.supcon.orchid.jdbc.DbUtils.getHibernateTypeByJavaType(params)));
			}
			try {
				Class thisclass=this.getClass();
				java.lang.reflect.Field daoField=thisclass.getDeclaredField(fLTL(targetModel.getModelName())+"Dao");
				Class daoType=daoField.getType();
				daoType.getMethod("findByPage",new Class[]{Page.class,DetachedCriteria.class}).invoke(daoField.get(this),new Object[]{dgPage,criteria});
				java.lang.reflect.Field serviceField=thisclass.getDeclaredField(fLTL(targetModel.getModelName())+"Service");
				Class serviceType=serviceField.getType();
				serviceType.getMethod("findByNormalDataGrid"+associatedModel.getModelName(),new Class[]{Page.class}).invoke(serviceField.get(this),new Object[]{dgPage});
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public String fLTL(String str){
		return com.supcon.orchid.utils.StringUtils.firstLetterToLower(str);
	}

	@Override
	public String fLTU(String str){
		return com.supcon.orchid.utils.StringUtils.firstLetterToUpper(str);
	}


	public List<Object[]> mneCodeSearch( String viewCode,  int showNumber, boolean cross, List<Param> params){
		return mneCodeSearch(viewCode, showNumber, cross, params,null,null,null,null,null);
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Object[]> mneCodeSearch( String viewCode,  int showNumber, boolean cross, List<Param> params,String refViewCode,String currentViewCode,String sqlType,String realPermissionCode,QueryEntity queryEntity){
		List<Object[]> resultList = null;
		List<Object> totalValues = new ArrayList<Object>();
		String customerSql = null;
		List<Object> customerSqlValues = null;
		Map<String,Object> customerSqlListMap = new LinkedHashMap<String,Object>();
		String customerCondition = null;
		List<Object> customerValues = null;
		Object mneValues = null;
		Object notinValue = null;
		for(Param p : params){

			if("customerSql".equals(p.getName())){
				customerSql = (String) p.getValue();
				continue;
			}

			if("customerSqlValues".equals(p.getName())){
				customerSqlValues = (List<Object>) p.getValue();
				continue;
			}

			if("customerSqlListMap".equals(p.getName())){
				customerSqlListMap = (Map<String,Object>) p.getValue();
				continue;
			}

			if("customerCondition".equals(p.getName())){
				customerCondition = (String) p.getValue();
				continue;
			}

			if("customerValues".equals(p.getName())){
				customerValues = (List<Object>) p.getValue();
				continue;
			}

			if("mneValues".equals(p.getName())){
				mneValues =  (Object) p.getValue();
				continue;
			}

			if("notinValue".equals(p.getName())){
				notinValue =  (Object) p.getValue();
				continue;
			}
		}

		String sql = sqlService.getSqlQuery(viewCode, Sql.TYPE_USED_MNECODE);
		totalValues.add(getCurrentCompanyId());
		if (null != sql && sql.length() > 0) {
			StringBuilder mnecodeSql = new StringBuilder();
			mnecodeSql.append(sql);
			//助记码权限,一个实体只有一个权限操作
			String  powerSql="";
			Boolean searchRefView=false;
			Boolean innerJoinAppendFlag = false;
			String  permissionCode = viewCode;
			if(realPermissionCode!=null&&!realPermissionCode.isEmpty())  {
				permissionCode = realPermissionCode;
			}
			StringBuilder sb=new StringBuilder();
			if(refViewCode!=null&&!refViewCode.equals(""))  {
				View refView=viewServiceFoundation.getView(refViewCode);
				//判断是否有参照视图,没有则抛出异常
				if(null==refView)  {
					throw new BAPException(BAPException.Code.REFERVIEW_NOT_FOUND,refViewCode);
				}
				//未启用权限,使用主列表视图的权限
				String powerCode = permissionCode + "_self";
				boolean needPermission = true;
				if(refView.getType() == ViewType.REFERENCE || refView.getType() == ViewType.REFTREE){
					if(refView.getShowType().equals(ShowType.PART)){
						if(null == permissionCode){
							needPermission = false;
						}else{
							//取布局视图
							View layoutView = viewServiceFoundation.getView(permissionCode);
							if(null != layoutView && !layoutView.getIsPermission()){//未启用权限
								needPermission = false;
							}
							if(null == layoutView) {
								layoutView = getLayoutView(permissionCode);
								if(null != layoutView && !layoutView.getIsPermission()){//未启用权限
									needPermission = false;
								}
							}
						}
					} else {
						if(null != refView && !refView.getIsPermission()){//未启用权限
							needPermission = false;
						}
					}
				}
				if(needPermission)  {
					if(null == refView ){
						powerCode = permissionCode;
					}else if(refView.getShowType().equals(ShowType.PART))  {
						View layoutView = viewServiceFoundation.getView(permissionCode);
						if(null == layoutView  || !(layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0)){
							powerCode = permissionCode;
						}else if((layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0))  {
							powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + layoutView.getPermissionCode();
							searchRefView=true;
						}
						if(null == layoutView)  {
							layoutView = getLayoutView(permissionCode);
							if(null == layoutView  || !(layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0)){
									powerCode = permissionCode;
							}else if((layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0))  {
									powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + layoutView.getPermissionCode();
							}
						}
					} else if(refView.getIsPermission() && refView.getPermissionCode().trim().length() > 0){
						powerCode ="SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + refView.getPermissionCode();
						searchRefView=true;
					}
					String powerCodeSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.code = ? and m.valid = 1";
					List<Object> powerCodeList =  ecdCommomDao.createNativeQuery(powerCodeSql, powerCode).list();
					if(powerCodeList.size() > 0) {
						powerCode = powerCodeList.get(0).toString();
					} else {
						String powerOperateSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.entity_Code=? and m.valid=1 and Power_Flag=1";
						List<Object> checkList =  ecdCommomDao.createNativeQuery(powerOperateSql, "SESECD_1.0.0_ecdPanel").list();
						if(checkList.size()>0){
							powerCode = checkList.get(0).toString();
						}
					}
						powerSql  = dataPermissionService.getBaseModelPowerCondition(this.creatorService.getStaffFromSession().getUser(), powerCode, "\"ecdCommom\"","SESECD_1.0.0_ecdPanel","","SES_ECD_ECD_COMMOMS",false,refView.getType().toString() ,permissionCode);
					//if(!searchRefView&&refView.getAssModel().getEntity().getWorkflowEnabled())  {
						//查询主列表视图的权限(表单类型)
						//	sb.append(" INNER JOIN WF_PENDING \"p\" ON \"p\".TABLE_INFO_ID=\"ecdCommom\".TABLE_INFO_ID ");
						//	sb.append(" WHERE  \"ecdCommom\".STATUS <> 0  ");
					//}
					//powerSql = dataPermissionService.getBaseModelPowerCondition(this.creatorService.getStaffFromSession().getUser(), powerCode, "\"ecdCommom\"","SESECD_1.0.0_ecdPanel","","SES_ECD_ECD_COMMOMS",false);
					if(sb.length()>0) {
						sb.append(" AND ");
					}
					sb.append(powerSql);
				}
			}else {
				//如果未关联参照视图则使用当前列表的查询权限
				if(currentViewCode!=null&&currentViewCode.trim().length()>0)  {
					boolean needPermission=true;
					View view=viewServiceFoundation.getView(currentViewCode);
					String powerCode = permissionCode + "_self";
					if(view.getType() == ViewType.REFERENCE || view.getType() == ViewType.REFTREE){
						searchRefView=true;
						if(view.getShowType().equals(ShowType.PART)){
							View layoutView = viewServiceFoundation.getView(permissionCode);
							if(null == layoutView  || !(layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0)){
								powerCode = permissionCode;
								needPermission=false;
							}else if((layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0))  {
								powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + layoutView.getPermissionCode();
							}
							if(null == layoutView)  {
								layoutView = getLayoutView(permissionCode);
								if(null == layoutView  || !(layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0)){
									powerCode = permissionCode;
								}else if((layoutView.getIsPermission() && layoutView.getPermissionCode().trim().length() > 0))  {
									powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + layoutView.getPermissionCode();
									needPermission=true;
								}
							}
						}else  {
							if(view.getIsPermission() && view.getPermissionCode().trim().length() > 0){
								powerCode = "SESECD_1.0.0_ecdPanel_EcdCommom" + "_" + view.getPermissionCode();
							}else {
								needPermission=false;
							}
						}
					}
					if(needPermission)  {
						String powerCodeSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.code = ? and m.valid = 1";
						List<Object> powerCodeList =  ecdCommomDao.createNativeQuery(powerCodeSql, powerCode).list();
						if(powerCodeList.size() > 0) {
							powerCode = powerCodeList.get(0).toString();
						} else {
							String powerOperateSql = "select m.code as code from " + MenuOperate.TABLE_NAME + " m where m.entity_Code=? and m.valid=1 and Power_Flag=1";
							List<Object> checkList =  ecdCommomDao.createNativeQuery(powerOperateSql, "SESECD_1.0.0_ecdPanel").list();
							if(checkList.size()>0){
								powerCode = checkList.get(0).toString();
							}
						}
							powerSql  = dataPermissionService.getBaseModelPowerCondition(this.creatorService.getStaffFromSession().getUser(), powerCode, "\"ecdCommom\"","SESECD_1.0.0_ecdPanel","","SES_ECD_ECD_COMMOMS",false,view.getType().toString(), permissionCode);
						if(sb.length()>0) {
							sb.append(" AND ");
						}
						sb.append(powerSql);
					}
				}

			}



			if(!innerJoinAppendFlag)  {
				if(!sb.toString().trim().startsWith("WHERE"))  {
					mnecodeSql.append(" WHERE ");
				}
			}
			if(sb != null && sb.length() > 0 && !sb.toString().trim().endsWith(" AND")) {
				sb.append(" AND ");
			}
			mnecodeSql.append(sb);

			mnecodeSql.append("  (\"").append("ecdCommom.mc\"").append(".MNE_CODE) like (?) escape '&' AND \"ecdCommom\".VALID = 1");
			totalValues.add(mneValues);

			if(customerSql != null && customerSql.length() > 0){
				mnecodeSql.append(" AND (").append(customerSql).append(")");
				if(customerSqlValues!=null&&customerSqlValues.size()>0){
					totalValues.addAll(customerSqlValues);
				}
			}

			if(customerCondition != null && customerCondition.length() > 0){
				mnecodeSql.append(" AND (").append(customerCondition).append(")");
				if(customerValues!=null&&customerValues.size()>0){
					totalValues.addAll(customerValues);
				}
			}

			if(!cross){
				// 查询本公司及上级公司数据
				String referencePowerCondition = dataPermissionService.getReferencePowerCondition("\"ecdCommom\"");
				mnecodeSql.append(" AND ").append(referencePowerCondition);
			}

			//组合自定义条件
			String customCondition=getCustomMneCondition(viewCode,  showNumber, cross,  params, refViewCode, currentViewCode, sqlType, realPermissionCode, queryEntity);
			if (customCondition!=null&&customCondition.trim().length() > 0) {

				if(customCondition != null && customCondition.length() > 0 && !customCondition.toString().trim().startsWith("AND") ) {
					mnecodeSql.append(" AND ");
				}
				mnecodeSql.append(customCondition);
			}

			if(notinValue != null){
				mnecodeSql.append(" and (\"").append("ecdCommom.mc\"").append(".ecd_commom) not in ("+notinValue+")");
			}
			mnecodeSql.append(" ORDER BY \"ISCURRENTCOMPANY\" DESC");
			//处理sql中混用占位符改为？+数字
			List<String> paramNameList = getParamNameList(mnecodeSql.toString());
            String dealSql = mnecodeSql.toString().replaceAll("([:])\\w+","?");
            Pattern pattern = Pattern.compile("\\?");
            int index = 0;
            StringBuffer buffer = new StringBuffer();
            Matcher matcher = pattern.matcher(dealSql);
			while (matcher.find()) {
				matcher.appendReplacement(buffer, "?" + (index++));
			}
			matcher.appendTail(buffer);
			Object[] arr = totalValues.toArray(new Object[]{});
			// sqlServer数据库下，生成的sql， top关键字位置不对，应该在DISTINCT关键字后面，它生成在前面
			if(DbUtils.getDbName().equals("sqlserver")){
				String sqlServerMnecodeSql = buffer.toString().replace("SELECT DISTINCT " , "SELECT DISTINCT TOP (" + showNumber + " )");
				NativeQuery query = ecdCommomDao.createNativeQuery(sqlServerMnecodeSql.toString());
				int k=0;
				for(;k<arr.length;k++){
                	query.setParameter(k,arr[k]);
                }
				if(customerSqlListMap!=null&&!customerSqlListMap.isEmpty()){
					for(String key:paramNameList){
						query.setParameterList(k, (Object[])customerSqlListMap.get(key));
						k++;
					}
				}
				resultList = query.list();
			} else {
                NativeQuery query = ecdCommomDao.createNativeQuery(buffer.toString());
				int k=0;
				for(;k<arr.length;k++){
                	query.setParameter(k,arr[k]);
                }
				if(customerSqlListMap!=null&&!customerSqlListMap.isEmpty()){
					for(String key:paramNameList){
						query.setParameterList(k, (Object[])customerSqlListMap.get(key));
						k++;
					}
				}
				resultList = query.setMaxResults(showNumber).list();
			}
		}

		return resultList;

	}

	@Override
	public void destroy() throws Exception {
		beforeServiceDestroy();
		sqlService = null;
//		eventService = null;
		bapCodeGenerator = null;
		scriptService = null;
		documentService = null;
		allEmerMemberDao = null;
		allEmerMemberService = null;
		emerExpertsDao = null;
		emerExpertsService = null;
		emerMembersDao = null;
		emerMembersService = null;
		accidentDao = null;
		accidentService = null;
		actionOwnersDao = null;
		actionOwnersService = null;
		alarmActCameraDao = null;
		alarmActCameraService = null;
		alarmActionDao = null;
		alarmActionService = null;
		alarmEnenetrelDao = null;
		alarmEnenetrelService = null;
		almAlarmRecordDao = null;
		almAlarmRecordService = null;
		cctvRecordDao = null;
		cctvRecordService = null;
		communicationDao = null;
		communicationService = null;
		emePlanObjDao = null;
		emePlanObjService = null;
		mesPersonDao = null;
		mesPersonService = null;
		recordActionDao = null;
		recordActionService = null;
		recorSituationDao = null;
		recorSituationService = null;
		alertRecordDao = null;
		alertRecordService = null;
		broadcastInfoDao = null;
		broadcastInfoService = null;
		changeLogDao = null;
		changeLogService = null;
		dispatConfigDao = null;
		dispatConfigService = null;
		entranceInfoDao = null;
		entranceInfoService = null;
		ecdAlertImgDao = null;
		ecdAlertImgService = null;
		ecdAlertRecordDao = null;
		ecdAlertRecordService = null;
		ecdAlertVideoDao = null;
		ecdAlertVideoService = null;
		ecdActionDao = null;
		ecdActionService = null;
		ecdCommomDao = null;
		ecdStatiusDao = null;
		ecdStatiusService = null;
		actVideoCameraDao = null;
		actVideoCameraService = null;
		emcActionDao = null;
		emcActionService = null;
		mainDepartmentDao = null;
		mainDepartmentService = null;
		mainPeopleDao = null;
		mainPeopleService = null;
		emcSituationDao = null;
		emcSituationService = null;
		emEventLeveLDao = null;
		emEventLeveLService = null;
		emEventTypeDao = null;
		emEventTypeService = null;
		eventDescribeDao = null;
		eventDescribeService = null;
		ecdParamConfigDao = null;
		ecdParamConfigService = null;
		paramOptionDao = null;
		paramOptionService = null;
		signalConfigDao = null;
		signalConfigService = null;
		sourceTerminalDao = null;
		sourceTerminalService = null;
		sentenceDao = null;
		sentenceService = null;
		tagConfigDao = null;
		tagConfigService = null;
		voiceConfigDao = null;
		voiceConfigService = null;
		voiceMemberDao = null;
		voiceMemberService = null;

		dataPermissionService = null;
		counterManager = null;
		conditionService = null;
		viewServiceFoundation = null;
		creatorService = null;
		synchronizeInfoService = null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.counter = this.counterManager.addCounter("ecdPanel", "SESECD_1.0.0_ecdPanel", "ecdPanel_{1,date,yyyyMMdd}_{0,number,000}", CounterType.Daily);
		String formatStr = "";
		this.afterInitBean();
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String getWorkFlowInfo(String menuCode) {
		String sql="select d.PROCESS_KEY PROCESSKEY from WF_DEPLOYMENT d,BASE_MENUINFO m where d.MENU_INFO_ID=m.id and d.valid=1 and m.code=? group by d.PROCESS_KEY";
		NativeQuery query=ecdCommomDao.createNativeQuery(sql, menuCode);
		List<String> list=query.list();
		String keys="";
		for(String key:list){
			keys+=","+key;
		}
		if(!"".equals(keys)){
			keys=keys.substring(1);
		}

		return keys;
	}
	// ================ 生成多选控件数据保存方法 start====================
	// ================ 生成多选控件数据保存方法 end ====================

	protected String getEntityCode(){return SESECDEcdCommom.ENTITY_CODE;}
	protected String getModuleCode(){return SESECDEcdCommom.MODULE_CODE;}
	protected String getModelCode(){return SESECDEcdCommom.MODEL_CODE;}


	/**
	 * 检查模型字段的唯一性约束
	 * @param ecdCommom 模型实例
	 */
	 @Override
	public void checkUniqueConstraint(SESECDEcdCommom ecdCommom){
	}

	@Override
	public List<SESECDEcdCommom> getEcdCommoms(String sql, List<Object> params) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
		if(params!=null){
			List<Type> types = new ArrayList<Type>();
			for(Object obj : params){
				types.add(TypeFactory.basic(obj.getClass().getName()));
			}
			detachedCriteria.add(Restrictions.sqlRestriction(sql, params.toArray(new Object[]{}),types.toArray(new Type[]{})));
		}else{
			return ecdCommomDao.findByHql("from " + SESECDEcdCommom.JPA_NAME + " " + ((sql==null || sql.isEmpty()) ? "" : "where " + sql)) ;
		}
		return ecdCommomDao.findByCriteria(detachedCriteria);
	}



	@Override
	public List<SESECDEcdCommom> findEcdCommomsBySql(String sql, List<Object> params){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
		if(params!=null && params.size()>0){
			List<Type> types = new ArrayList<Type>();
			for(Object obj : params){
				types.add(TypeFactory.basic(obj.getClass().getName()));
			}
			detachedCriteria.add(Restrictions.sqlRestriction(sql, params.toArray(new Object[]{}),types.toArray(new Type[]{})));

		}else{
			detachedCriteria.add(Restrictions.sqlRestriction(sql));
		}

		return ecdCommomDao.findByCriteria(detachedCriteria);
	}
	@Override
	public List<SESECDEcdCommom> findEcdCommomsByHql(String hql, Object... objects){

		return ecdCommomDao.findByHql(hql, objects);
	}

	@Override
	public SESECDEcdCommom loadEcdCommomByBussinessKey(SESECDEcdCommom ecdCommom){
		return loadEcdCommomByBussinessKey(ecdCommom.getId());
	}

	@Override
	public Page<SESECDEcdCommom> getEcdCommoms(Page<SESECDEcdCommom> page, String sql, List<Object> params, String sort) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
		if(sort!=null && sort.length()>0){
			String[] sorts = sort.split("\\|");
			if(sorts!=null && sorts.length>0){
				for(String condition : sorts){
					if(condition!=null && condition.length()>0 && condition.contains(",")){
						String[] conditions = condition.split(",");
						if("asc".equals(conditions[1])){
							detachedCriteria.addOrder(Order.asc(conditions[0]));
						}else if("desc".equals(conditions[1])){
							detachedCriteria.addOrder(Order.desc(conditions[0]));
						}
					}
				}
			}
		}
		if(params!=null){
			List<Type> types = new ArrayList<Type>();
			for(Object obj : params){
				types.add(TypeFactory.basic(obj.getClass().getName()));
			}
			detachedCriteria.add(Restrictions.sqlRestriction(sql, params.toArray(new Object[]{}),types.toArray(new Type[]{})));
		}else{
			if(sql!=null){
				page = ecdCommomDao.findByPage(page, sql);
			}else{
				page = ecdCommomDao.findAllByPage(page);
			}
			return page;
		}
		page = ecdCommomDao.findByPage(page, detachedCriteria);
		return page;
	}
	//==============DataGrid多选控件使用 start================
	//===================树形PT=======================
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SESECDEcdCommom> findByTreeDataGridEcdCommom(List<SESECDEcdCommom> treeList){
		if(null != treeList && !treeList.isEmpty()){
			for(SESECDEcdCommom ecdCommom : treeList){
			}
		}
		return treeList;
	}
	//=======================普通PT==================================
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void findByNormalDataGridEcdCommom(Page<SESECDEcdCommom> dgPage){
		if(null != dgPage && null != dgPage.getResult()){
			List<SESECDEcdCommom> ecdCommoms = dgPage.getResult();
			for(SESECDEcdCommom ecdCommom : ecdCommoms){

			}
			dgPage.setResult(ecdCommoms);
		}

	}



	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SESECDEcdCommom loadEcdCommomByBussinessKey(Serializable bussinessKey){
		return ecdCommomDao.findEntityByCriteria(Restrictions.eq("id", Long.valueOf(bussinessKey.toString())),Restrictions.eq("valid", true));
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SESECDEcdCommom> findByProperty(String propertyName, Object object){
		return ecdCommomDao.findByProperty(propertyName, object);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SESECDEcdCommom findEntityByProperty(String propertyName, Object object){
		return ecdCommomDao.findEntityByProperty(propertyName, object);
	}



	@Override
	@Transactional
	public void deleteByBussinessKeys(String bussinessKeys){
		if(null != bussinessKeys && bussinessKeys.trim().length() > 0){
			List<Long> bussinessKeyList = new ArrayList<Long>();
			for(String key : bussinessKeys.split(",")){
				bussinessKeyList.add(Long.valueOf(key));
			}
			String sql = "update " + SESECDEcdCommom.JPA_NAME + " set valid=0 where id in (:businessKeys)";
			Query query = ecdCommomDao.createQuery(sql);
			query.setParameterList("businessKeys", bussinessKeyList);
			query.executeUpdate();
		}
	}

	@Override
	public String findValidateDatagrids(Map<String,Class> dgClassMap){
		/*String bap_validate_datagrids="";
		List<View> dgviews = viewServiceFoundation.findViewsByAssModelCode("SESECD_1.0.0_ecdPanel_EcdCommom","runtime");
		try {
			for(View dgv:dgviews){
				if (null == dgv.getIsShadow() || !dgv.getIsShadow()) {
					List<DataGrid> ldg=viewServiceFoundation.getDataGrids(dgv.getCode());
					for(DataGrid dg:ldg){
						bap_validate_datagrids+=dg.getName()+",";
						dgClassMap.put(dg.getName(),Class.forName("com.supcon.orchid.SESECD.entities."
							+viewServiceFoundation.getTargetModelFromDg(dg.getCode()).getJpaName()));
					}
				}
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		}
		return bap_validate_datagrids;*/
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String findValidateDatagrids(Map<String,Class> dgClassMap,String viewCode){
		/*String bap_validate_datagrids="";
		List<DataGrid> dgs=viewServiceFoundation.getDataGridsByViewCode(viewCode);
		try {
			for(DataGrid dg:dgs){
				bap_validate_datagrids+=dg.getName()+",";
				dg.setTargetModel(viewServiceFoundation.getTargetModelFromDg(dg.getCode()));
				dgClassMap.put(dg.getName(),Class.forName("com.supcon.orchid.SESECD.entities."+dg.getTargetModel().getJpaName()));
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(),e);
		}
		return bap_validate_datagrids;*/
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String findMainViewPath(){
		String mainViewPath="";
		List<View> views = viewServiceFoundation.findViewsByEntityCode("SESECD_1.0.0_ecdPanel", ViewType.VIEW);
		for(View v:views){
			if(v.getMainView()){
				mainViewPath=v.getUrl();
			}
		}
		return mainViewPath;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DataGrid> findDatagrids(){
		/*List<DataGrid> ldg=new ArrayList<DataGrid>();
		List<View> dgviews = viewServiceFoundation.findViewsByAssModelCode("SESECD_1.0.0_ecdPanel_EcdCommom","runtime");
		for(View dgv:dgviews){
			if (null == dgv.getIsShadow() || !dgv.getIsShadow()) {
				ldg.addAll(viewServiceFoundation.getDataGrids(dgv.getCode()));
			}
		}
		return ldg;*/
		return null;
	}

	@Override
	public void dealDatagridsSave(SESECDEcdCommom ecdCommom,String viewCode,Map<String,String> dgLists,Map<String,String> dgDeleteIDs,Map<String,Object> assFileUploads){
		if(dgLists==null&&dgDeleteIDs==null){
			return;
		}
		List<DataGrid> dgs=viewServiceFoundation.getDataGridsByViewCode(viewCode);
		if(dgs!=null&&dgs.size()>0){
			ecdCommomDao.flush();
			Class _reflect_thisclass=this.getClass();
			try {
			    // 查询地图服务是否已启动
			    boolean mapServiceEnable = getMapServiceEnable();
				for(DataGrid dg:dgs){
					if(null != dg.getDataGridType() && dg.getDataGridType() == 1){
						continue;
					}
					dg.setTargetModel(viewServiceFoundation.getTargetModelFromDg(dg.getCode()));
					Class _reflect_dgclass=Class.forName("com.supcon.orchid.SESECD.entities."+dg.getTargetModel().getJpaName());
					java.lang.reflect.Field _reflect_serviceField = _reflect_thisclass.getDeclaredField(fLTL(dg.getTargetModel().getModelName())+"Service");
					Class _reflect_serviceType=_reflect_serviceField.getType();
					Object _reflect_serviceObj=_reflect_serviceField.get(this);
					Model _targetModel=viewServiceFoundation.getTargetModelFromDg(dg.getCode());
					List<Long> _needDealIds = new ArrayList<>();
					Map<String, Long> _dgCodeMap = new HashMap<String, Long>();
					boolean isTree=(_targetModel.getDataType()==2);
					List<String> _attachKey =new ArrayList<String>();
					List<String> picKey = new ArrayList<String>();
					Map _confMap=dg.getConfigMap();
					Map _layoutMap = (Map) _confMap.get("layout");
					List<Map> ls = (List<Map>) _layoutMap.get("sections");
					for(Map lm:ls){
						if(lm.get("regionType").equals("DATAGRID")){
							List<Map> lc = (List<Map>) lm.get("cells");
							for(Map lk:lc){
								if(( "PROPERTYATTACHMENT".equals(lk.get("columnType")) ||  "PICTURE".equals(lk.get("columnType")) ||  "SIGNATURE".equals(lk.get("columnType")) ) && lk.get("key")!=null && !lk.get("key").toString().contains(".") ){
									_attachKey.add(lk.get("key").toString());
								}
							}
						}
					}
					boolean hasAttach=(_attachKey.size()>0);
					if(null == assFileUploads){
						assFileUploads = new HashMap<String,Object>();
					}
					if(hasAttach&&assFileUploads.get("staffId")==null){
						assFileUploads.put("staffId", UserContext.getUserContext().getStaffId());
					}
					Map<String,Object> dgAttachMap = (Map<String, Object>) assFileUploads.get(dg.getName());
					if(hasAttach){
						if(null == dgAttachMap){
							dgAttachMap = new HashMap<String,Object>();
						}
						for(String key:_attachKey){
							dgAttachMap.put(key+"_propertyCode",_reflect_dgclass.getDeclaredField(key.toUpperCase()+"_PROPERTY_CODE").get(null));
						}
						dgAttachMap.put("type", _reflect_dgclass.getDeclaredField("DOC_TYPE").get(null));
					}
					if(dgDeleteIDs!=null&&dgDeleteIDs.get(dg.getName())!=null){
						ArrayList<Long> param=new ArrayList<Long>();
						String[] arrs=dgDeleteIDs.get(dg.getName()).split(",");
						for(int i=0;i<arrs.length;i++){
							param.add(Long.valueOf(arrs[i]));
						}
						if(hasAttach){
							dgAttachMap.put("deleteIds",param);
						}
						if (mapServiceEnable) {
						    // 辅表地图删除
                            try {
                                delLayerInfo(arrs, _targetModel, dg);
                            } catch (Exception e) {
                                log.error("辅模型地图数据删除失败：" + e.getMessage(), e);
                                throw new BAPException(InternationalResource.get("greendill.service.ditu.fumoxingdelete",this.getCurrentLanguage())+ e.getMessage());
                            }
						}
						_reflect_serviceType.getDeclaredMethod("delete"+_targetModel.getModelName(),List.class,String.class).invoke(_reflect_serviceObj,param,"noEvent");

					}
					if(dgLists!=null&&dgLists.get(dg.getName())!=null){
						if(isTree){
							_needDealIds.clear();
							_dgCodeMap.clear();
						}
						List dglist=(List)com.supcon.orchid.ec.utils.JSONUtil.generateObjectFromJson(dgLists.get(dg.getName()).replaceAll("\r\n", "\\\\r\\\\n").replaceAll("\"valid\":null", "\"valid\":true"), _reflect_dgclass,ecdCommomDao.getSessionFactory());
						Method _reflect_setMethod=_reflect_dgclass.getMethod("set"+fLTU(viewServiceFoundation.getOrgPropertyFromDg(dg.getCode()).getName()), SESECDEcdCommom.class);
						Method _reflect_saveMethod=_reflect_serviceType.getMethod("save"+_targetModel.getModelName(),_reflect_dgclass,Map.class,Map.class,Map.class,String.class,String.class,boolean[].class);
						for(Object odg:dglist){
							_reflect_setMethod.invoke(odg, ecdCommom);
							Map<String,Object> paramMap = new HashMap<String,Object>();
							if(hasAttach){
                                for(String key:_attachKey){
                                    List<String> fileAddPath=(List<String>)_reflect_dgclass.getDeclaredMethod("get"+fLTU(key)+"FileAddPaths", null).invoke(odg, null);
                                    if(fileAddPath!=null&&fileAddPath.size()>0){
                                        paramMap.put(key+"_fileAddPaths",fileAddPath);
                                    }
                                    List<Long> fileDeleteIds=(List<Long>)_reflect_dgclass.getDeclaredMethod("get"+fLTU(key)+"FileDeleteIds", null).invoke(odg, null);
                                    if(fileDeleteIds!=null&&fileDeleteIds.size()>0){
                                        paramMap.put(key+"_fileDeleteIds",fileDeleteIds);
                                    }
                                    // 处理复制行的附件
                                    List<Long> fileIds=(List<Long>)_reflect_dgclass.getDeclaredMethod("get"+fLTU(key)+"MultiFileIds", null).invoke(odg, null);
                                    Long dgId = ((com.supcon.orchid.orm.entities.IdEntity)odg).getId();
                                    if (null != fileIds && fileIds.size()>0) {
                                        List<String> copyFileAddPaths = new ArrayList<String>();
                                        for (Long fileId : fileIds) {
                                            Document document = documentService.load(fileId);
                                            Long docLinkId = document.getLinkId();
                                            if (null != docLinkId && !docLinkId.equals(dgId)) {
                                                copyFileAddPaths.add(documentService.getDocumentAbsolutePath(document));
                                            }
                                        }
                                        paramMap.putIfAbsent(key+"_fileAddPaths",copyFileAddPaths);
                                    }
                                    // 删除原有附件
                                    if (null != dgId) {
                                        String docType = _reflect_dgclass.getDeclaredField("DOC_TYPE").get(null).toString();
                                        String propertyCode = (String) _reflect_dgclass.getDeclaredField(key.toUpperCase() + "_PROPERTY_CODE").get(null);
                                        if(dgId!=null && docType!=null && propertyCode!=null && fileIds != null) {
                                            List<Document> existDocuments = documentService.getByLinkIdAndTypeAndPropertyCode(dgId, docType, propertyCode);
                                            for (Document existDocument : existDocuments) {
                                                if (!fileIds.contains(existDocument.getId())) {
                                                    documentService.delete(existDocument);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
							if(isTree){
								String ppCode = ((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).getVirtualParentCode();
								String layRec = ((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).getLayRec();
								if(null != layRec && layRec.indexOf("-") > -1) {
									if(_dgCodeMap.containsKey(ppCode)) {
										((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).setParentId(_dgCodeMap.get(ppCode));
									}
								} else if(null != ppCode && !ppCode.isEmpty()) {
									((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).setParentId(Long.parseLong(ppCode));
								}
							}
							_reflect_saveMethod.invoke(_reflect_serviceObj, odg,null,null,null,viewCode,"noEvent",new boolean[]{});
							if (mapServiceEnable) {
                                // 辅表调用地图接口，保存数据
                                try{
                                    addLayerInfo(odg, "",dg);
                                } catch (Exception e) {
                                    log.error("辅表调用地图接口，地图接口调用失败：" + e.getMessage(), e);
                                    throw new BAPException(InternationalResource.get("greendill.service.ditu.fubiaoerror",this.getCurrentLanguage()) + e.getMessage());
                                }
							}
							if(isTree){
								_needDealIds.add(((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).getId());
								String pCode = ((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).getVirtualCode();
								if(!_dgCodeMap.containsKey(pCode)) {
									_dgCodeMap.put(pCode, ((com.supcon.orchid.ec.entities.abstracts.AbstractCidTreeNode)odg).getId());
								}
							}
							if(!paramMap.isEmpty()){
								paramMap.put("linkId",((com.supcon.orchid.orm.entities.IdEntity)odg).getId());
								paramMap.put("mainModelId",ecdCommom.getId());
								dgAttachMap.put(((com.supcon.orchid.orm.entities.IdEntity)odg).getId().toString(), paramMap);
							}
						}
						if(isTree){
							_reflect_serviceType.getMethod("deal"+_targetModel.getModelName()+"Leaf",List.class).invoke(_reflect_serviceObj, _needDealIds);
						}
					}
					if(hasAttach){
						if(null != dgAttachMap && !dgAttachMap.isEmpty()){
							assFileUploads.put(dg.getName(),dgAttachMap);
						}
					}
				}
			}catch (Exception e) {
				log.error(e.getMessage(),e);
				if(e instanceof InvocationTargetException){
					InvocationTargetException iteException=(InvocationTargetException)e;
					if(iteException.getTargetException()!=null){
						Throwable throwable=iteException.getTargetException();
						if(throwable instanceof BAPException){
							 throw (BAPException)throwable;
						}else{
							throw new BAPException(throwable.getMessage()!=null?throwable.getMessage():InternationalResource.get("foundation.error.message", getCurrentLanguage()));
						}
					}
				}
				throw new BAPException(InternationalResource.get("foundation.error.message", getCurrentLanguage()));
			}
		}
	}


		@Override
	@Transactional
	public List<Object> getBusinessKeyData(String businessKeyName){
		List<Object> list = new ArrayList<Object>();
		if(null != businessKeyName){
			String sql = "select " + businessKeyName +" from "+ SESECDEcdCommom.TABLE_NAME +" where valid = 1";
			list = ecdCommomDao.createNativeQuery(sql).list();
		}
		return list;
	}

	@Override
	@Transactional
	public List<Object> getBusinessKeyDataByBusinessKeyName(String businessKeyName){
		List<Object> list = new ArrayList<Object>();
		if(null != businessKeyName){
			String sql = "select " + businessKeyName +" from "+ SESECDEcdCommom.TABLE_NAME + " where valid = 1";
			list = ecdCommomDao.createNativeQuery(sql).list();
		}
		return list;
	}

	@Override
	@Transactional
	public Map<Object, Object> getReplaceProperty(String propertyName,String businessKey){
		List<Object[]> list = new ArrayList<Object[]>();
		Map<Object, Object> m = new HashMap<Object, Object>();
		if(null != propertyName && null != businessKey){
			String sql = "select " + businessKey + "," + propertyName +" from "+ SESECDEcdCommom.TABLE_NAME +" group by " + propertyName + "," + businessKey;
			list = ecdCommomDao.createNativeQuery(sql).list();
		}
		if (list != null && !list.isEmpty()) {
			for (Object[] obj : list) {
				if(null != obj[1]){
					m.put(obj[0], obj[1]);
				}
			}
		}
		return m;
	}

	@Autowired
	private SESECDEcdCommomDaoImpl ecdCommomDaoImpl;


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<Object, Long> importBatchEcdCommom(final List<SESECDEcdCommom> insertObjs, final List<SESECDEcdCommom> updateObjs,
				List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo){
		return this.importBatchEcdCommom(insertObjs, updateObjs, columnInfo, importNodeInfo, importPropInfo,null);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<Object, Long> importBatchEcdCommom(final List<SESECDEcdCommom> insertObjs, final List<SESECDEcdCommom> updateObjs,
				List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo,SignatureLog signatureLog){

		int importCount = 0;
		if (null != insertObjs) {
			importCount += insertObjs.size();
		}
		if (null != updateObjs) {
			importCount += updateObjs.size();
		}
		AuditUtil.setAuditDes(InternationalResource.get("foundation.import.bussiness.info.args",getCurrentLanguage(),InternationalResource.get("SESECD.ecdPanel.EcdCommom"),importCount));
		if(null != signatureLog && null == signatureLog.getOperateLogUuid()){
			if (null != AuditUtil.getCurrentAudit() && null != AuditUtil.getCurrentAudit().getOperationAudit()) {
				signatureLog.setOperateLogUuid((null == AuditUtil.getCurrentAudit().getOperationAudit().get_parentCode() || "-1".equals( AuditUtil
						.getCurrentAudit().getOperationAudit().get_parentCode())) ? AuditUtil.getCurrentAudit().getOperationAudit().getUuid() : AuditUtil
						.getCurrentAudit().getOperationAudit().get_parentCode());
			}
		}
		Map<Long, SESECDEcdCommom> oldUpdateObjsMap = new HashMap<Long, SESECDEcdCommom>();
		List<Long> updateObjIdsList = new ArrayList<Long>();
		for(int updateIndex = 0; updateIndex < updateObjs.size(); updateIndex++)  {
			SESECDEcdCommom bizObj = updateObjs.get(updateIndex);
			updateObjIdsList.add(bizObj.getId());
		}
		if (updateObjIdsList.size() > 0) {
            List<SESECDEcdCommom> bizObjList = new ArrayList<>();
			if(updateObjIdsList.size()>1000){
                for (int i = 0; i <= updateObjIdsList.size()/1000; i++) {
                    if(i==updateObjIdsList.size()/1000){
                        if(updateObjIdsList.size()%1000==0){
                            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
							detachedCriteria.add(Restrictions.in("id", updateObjIdsList.get(updateObjIdsList.size()-1)));
							bizObjList.addAll(ecdCommomDao.findByCriteria(detachedCriteria));
						}else{
						    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
							detachedCriteria.add(Restrictions.in("id", updateObjIdsList.subList(1000*i,updateObjIdsList.size())));
							bizObjList.addAll(ecdCommomDao.findByCriteria(detachedCriteria));
						}
					}else{
					    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
						detachedCriteria.add(Restrictions.in("id", updateObjIdsList.subList(1000*i,1000*(i+1)-1)));
						bizObjList.addAll(ecdCommomDao.findByCriteria(detachedCriteria));
					}
                }

			}else{
			    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SESECDEcdCommom.class);
			    detachedCriteria.add(Restrictions.in("id", updateObjIdsList));
			    bizObjList.addAll(ecdCommomDao.findByCriteria(detachedCriteria));
			}
			if (null != bizObjList) {
				for (int mIndex = 0; mIndex < bizObjList.size(); mIndex++) {
					SESECDEcdCommom bizObj = bizObjList.get(mIndex);
					if (null != bizObj && null != bizObj.getId()) {
						oldUpdateObjsMap.put(bizObj.getId(), bizObj);
					}
				}
			}
		}

		final List<Map<String,String>> columnMaps = new ArrayList<Map<String,String>>();
		List<String> columnName = new ArrayList<String>();
		final List<String> associatedModelNames = new ArrayList<String>();
		final List<String> associatedModelTypes = new ArrayList<String>();
		for(int i = 0 ; i<columnInfo.size(); i++){//封装excel中列名对应的模型中的字段，数据库中的字段，字段类型
			if(!columnName.contains(columnInfo.get(i).get("name")) && !columnInfo.get(i).get("name").equals("id")){
				Map<String,String> columnMap = new HashMap<String, String>();
				columnMap.put("name", columnInfo.get(i).get("name"));
				String name = columnInfo.get(i).get("name");
				if(name.equals("deleteStaff") || name.equals("createStaff") || name.equals("modifyStaff") || name.equals("effectStaff") || name.equals("ownerDepartment") ||
						name.equals("ownerPosition") || name.equals("createPosition") || name.equals("createDepartment") || name.equals("ownerStaff")){
					name = name + "Id";
				}
				columnMap.put("dbname", columnInfo.get(i).get("dbname"));
				columnMap.put("type", columnInfo.get(i).get("type"));
				columnMap.put("isCustom", columnInfo.get(i).get("isCustom"));
				columnMap.put("multable", columnInfo.get(i).get("multable"));
				columnMaps.add(columnMap);
				columnName.add(columnInfo.get(i).get("name"));
				associatedModelNames.add(columnInfo.get(i).get("associatedModelName"));
				associatedModelTypes.add(columnInfo.get(i).get("associatedModelType"));
			}
		}

		final List<SESECDEcdCommom> nmeObjs = new ArrayList<SESECDEcdCommom>();

		Map<Object, Long> m = new HashMap<Object, Long>();

		final String dbId = getDbIdName();
		final List<Long> insertIds = new ArrayList<Long>();
		int expLength=insertObjs.size();
		long id=SnowFlakeIdWorker.getInstance().nextId();
		for(SESECDEcdCommom ecdCommom:insertObjs)  {
			if(ecdCommom.getId() != null ){
				m.put(ecdCommom.getId(), id);
			}else{
				m.put(id, id);
			}
			insertIds.add(id);
			ecdCommom.setId(id);
			id++;
		}


		for(Map<String,String> map : columnMaps){
			if(map.get("type").equals("SYSTEMCODE")){
				String sql = "select senior_system_code,multable from runtime_property where code = ?";
				Object[] result = (Object[]) ecdCommomDao.createNativeQuery(sql, "SESECD_1.0.0_ecdPanel_EcdCommom"+"_"+map.get("name")).uniqueResult();
				map.put("isSenior", result[0].toString());
				//map.put("multable", result[1].toString());
			}
		}

		User currentUser=(User)getCurrentUser();
		Staff currentStaff = (Staff) getCurrentStaff();
		ecdCommomDaoImpl.getSession().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement updateps = null;
				PreparedStatement insertps = null;

				if(updateObjs != null && updateObjs.size()>0){
					List<String> updateMethods = new ArrayList<String>();
					String updateSql = "UPDATE " + SESECDEcdCommom.TABLE_NAME + " SET ";
					for(int i = 0;i<columnMaps.size();i++){
						String mName = columnMaps.get(i).get("name");
						String methodName = "get"+ mName.replaceFirst(mName.substring(0, 1), mName.substring(0, 1).toUpperCase());
						updateMethods.add(methodName);
						updateSql += columnMaps.get(i).get("dbname")+"=?,";
					}
					updateSql = updateSql.substring(0,updateSql.length()-1);
					updateSql += " where " + dbId + " =?";

					updateps = conn.prepareStatement(updateSql);
					for(int updateIndex =0;updateIndex<updateObjs.size();updateIndex++){
						SESECDEcdCommom ecdCommom = updateObjs.get(updateIndex);
						Long id = ecdCommom.getId();
						if(null != id){
							updateps.setLong(columnMaps.size()+1,id);
							for(int i=0;i<columnMaps.size();i++){
								Method method = null;
								try {
									method = ecdCommom.getClass().getMethod(updateMethods.get(i));
								} catch (NoSuchMethodException | SecurityException e) {
									log.error(e.getMessage(),e);
								}
								Object obj = null;
								if(null != method){
									try {
										obj = method.invoke(ecdCommom);
									} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
										log.error(e.getMessage(),e);
									}
								}
								if(null != obj){
									String type = columnMaps.get(i).get("type");
									if (type.equals("TEXT") || type.equals("PASSWORD") || type.equals("LONGTEXT") || type.equals("BAPCODE")
									    || type.equals("SUMMARY") || type.equals("COLOR") || type.equals("TIME")) {
										updateps.setString(i+1, obj.toString());
									} else if(type.equals("SYSTEMCODE")){
										if( null != columnMaps.get(i).get("isCustom") && columnMaps.get(i).get("isCustom").equals("true") ){
											updateps.setString(i+1, obj.toString());
										}else if( null != columnMaps.get(i).get("multable") && columnMaps.get(i).get("multable").equals("true") ){
											updateps.setString(i+1, obj.toString());
										}else if(null != columnMaps.get(i).get("isSenior") && columnMaps.get(i).get("isSenior").equals("0")){
											SystemCode sc = (SystemCode) obj;
											updateps.setString(i+1, sc.getId());
										}else{
											if(obj.toString().contains("SystemCode")){
												SystemCode sc = (SystemCode) obj;
												updateps.setString(i+1, sc.getId());
											}else{
												updateps.setString(i+1, obj.toString());
											}
										}
									} else if ("DATE".equals(type)) {
										java.sql.Date sqlDate=new java.sql.Date(((Date) obj).getTime());
										updateps.setDate(i+1, sqlDate);
									} else if ("DATETIME".equals(type)) {
										java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(((Date) obj).getTime());
										updateps.setTimestamp(i+1, sqlTimestamp);
									} else if ("INTEGER".equals(type)) {
										updateps.setInt(i+1, (Integer) obj);
									} else if ("LONG".equals(type)) {
										updateps.setLong(i+1, (Long) obj);
									} else if ("DECIMAL".equals(type) || "MONEY".equals(type)) {
										updateps.setBigDecimal(i+1, (BigDecimal) obj);
									} else if ("BOOLEAN".equals(type)) {
										updateps.setBoolean(i+1 , (Boolean) obj);
									} else if ("OBJECT".equals(type)) {
										if(null != columnMaps.get(i).get("isCustom") && columnMaps.get(i).get("isCustom").equals("true")){
											updateps.setLong(i+1, (Long) obj);
										}else{
											Method objmethod = null;
											try {
												String methodName = "get" + associatedModelNames.get(i).substring(0, 1).toUpperCase() +  associatedModelNames.get(i).substring(1, associatedModelNames.get(i).length());
												objmethod = obj.getClass().getMethod(methodName);
												if(associatedModelTypes.get(i).equals("LONG")){
													Long objId = null;
													objId = (Long) objmethod.invoke(obj);
													updateps.setObject(i+1, objId);
												}else if(associatedModelTypes.get(i).equals("TEXT")){
													String objCode = null;
													objCode = (String) objmethod.invoke(obj);
													updateps.setObject(i+1, objCode);
												}
											} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
												log.error(e.getMessage(),e);
											}

										}							}
								}else{
									updateps.setString(i+1, null);
								}
							}
							nmeObjs.add(ecdCommom);
							updateps.addBatch();
						}
					}
				}

				if(insertObjs != null && insertObjs.size()>0){//新增
					List<String> insertMethods = new ArrayList<String>();
					String insertSql = "INSERT INTO " + SESECDEcdCommom.TABLE_NAME + " (";
					for(int i = 0;i<columnMaps.size();i++){
						String mName = columnMaps.get(i).get("name");
						String methodName = "get"+ mName.replaceFirst(mName.substring(0, 1), mName.substring(0, 1).toUpperCase());
						insertMethods.add(methodName);
						insertSql += columnMaps.get(i).get("dbname")+",";
					}
					insertSql += dbId + ",CID,CREATE_STAFF_ID,CREATE_TIME) VALUES (";
					for(int i = 0;i<columnMaps.size();i++){
						insertSql += "?,";
					}
					insertSql += "?,?,?,?)";

//					String idSql = "select id from " + SESECDEcdCommom.TABLE_NAME + " order by id desc";
//					Query query = ecdCommomDao.createNativeQuery(idSql).setMaxResults(1);
//					Long id = new Long(1000);
//					if(null != query.uniqueResult()){
//						id = Long.valueOf(query.uniqueResult().toString());
//					}
					insertps = conn.prepareStatement(insertSql);
					for(int insertIndex = 0; insertIndex<insertObjs.size(); insertIndex++)  {
						SESECDEcdCommom ecdCommom = insertObjs.get(insertIndex);
						insertps.setLong(columnMaps.size() + 1, insertIds.get(insertIndex));
						insertps.setLong(columnMaps.size() + 2,getCurrentCompanyId());
						insertps.setLong(columnMaps.size() + 3,currentStaff.getId());
						java.util.Date date=new java.util.Date();
						insertps.setTimestamp(columnMaps.size() + 4,new java.sql.Timestamp(date.getTime()));
						for (int i = 0; i < columnMaps.size(); i++) {
							Method method = null;
							try {
								method = ecdCommom.getClass().getMethod(
										insertMethods.get(i));
							} catch (NoSuchMethodException | SecurityException e) {
								log.error(e.getMessage(),e);
							}
							Object obj = null;
							if (null != method) {
								try {
									obj = method.invoke(ecdCommom);
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
									log.error(e.getMessage(),e);
								}
							}
							if (null != obj) {
								String type = columnMaps.get(i).get("type");
								if (type.equals("TEXT") || type.equals("PASSWORD") || type.equals("LONGTEXT") || type.equals("BAPCODE")
								    || type.equals("SUMMARY")  || type.equals("COLOR") || type.equals("TIME")) {
									insertps.setString(i+1, obj.toString());
								} else if(type.equals("SYSTEMCODE")){
									if( null != columnMaps.get(i).get("isCustom") && columnMaps.get(i).get("isCustom").equals("true") ){
										insertps.setString(i+1, obj.toString());
									}else if( null != columnMaps.get(i).get("multable") && columnMaps.get(i).get("multable").equals("true") ){
										insertps.setString(i+1, obj.toString());
									}else if(null != columnMaps.get(i).get("isSenior") && columnMaps.get(i).get("isSenior").equals("0")){
										SystemCode sc = (SystemCode) obj;
										insertps.setString(i+1, sc.getId());
									}else{
										if(obj.toString().contains("SystemCode")){
											SystemCode sc = (SystemCode) obj;
											insertps.setString(i+1, sc.getId());
										}else{
											insertps.setString(i+1, obj.toString());
										}
									}
								} else if ("DATE".equals(type)) {
									java.sql.Date sqlDate=new java.sql.Date(((Date) obj).getTime());
									insertps.setDate(i+1, sqlDate);
								} else if ("DATETIME".equals(type)) {
									java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(((Date) obj).getTime());
									insertps.setTimestamp(i+1, sqlTimestamp);
								} else if ("INTEGER".equals(type)) {
									insertps.setInt(i+1, (Integer) obj);
								} else if ("LONG".equals(type)) {
									insertps.setLong(i+1, (Long) obj);
								} else if ("DECIMAL".equals(type) || "MONEY".equals(type)) {
									insertps.setBigDecimal(i+1, (BigDecimal) obj);
								} else if ("BOOLEAN".equals(type)) {
									insertps.setBoolean(i+1 , (Boolean) obj);
								} else if ("OBJECT".equals(type)) {
									if(null != columnMaps.get(i).get("isCustom") && columnMaps.get(i).get("isCustom").equals("true")){
										insertps.setLong(i+1, (Long) obj);
									}else{
										Method objmethod = null;
										try {
											String methodName = "get" + associatedModelNames.get(i).substring(0, 1).toUpperCase() +  associatedModelNames.get(i).substring(1, associatedModelNames.get(i).length());
											objmethod = obj.getClass().getMethod(methodName);
											if(associatedModelTypes.get(i).equals("LONG")){
												Long objId = null;
												objId = (Long) objmethod.invoke(obj);
												insertps.setObject(i+1, objId);
											}else if(associatedModelTypes.get(i).equals("TEXT")){
												String objCode = null;
												objCode = (String) objmethod.invoke(obj);
												insertps.setObject(i+1, objCode);
											}
										} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
											log.error(e.getMessage(),e);
										}

									}
								}
							} else {
								insertps.setString(i + 1, null);
							}
						}
						//ecdCommom.setId(id);
						nmeObjs.add(ecdCommom);
						insertps.addBatch();
					}
				}


				if(null != insertps){
					insertps.executeBatch();
					insertps.close();
				}
				if(null != updateps){
					updateps.executeBatch();
					updateps.close();
				}

			}
		});

		//ecdCommomDaoImpl.getSession().flush();
		//ecdCommomDaoImpl.getSession().clear();


		//记录导入数据日志（区分新增和修改）
        if (ModelAuditLogCache.isAuditLogEnabled("SESECD_1.0.0_ecdPanel_EcdCommom", AuditLogType.DATA)) {
            // 导入数据审计日志
            List<AuditDataLogBO> insertAuditDataLogList = getAuditDataLogs(insertObjs, OperateType.ADD);
            List<AuditDataLogBO> updateAuditDataLogList = getAuditDataLogs(updateObjs, OperateType.MODIFY);
            insertAuditDataLogList.addAll(updateAuditDataLogList);
            for (AuditDataLogBO auditDataLog : insertAuditDataLogList) {
                auditDataLogStrategy.publishAuditLogLazy(auditDataLog);
            }
        }

		return m;
	}




	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	//excel辅模型查询
	public void excelAuxModelQuery(Page<SESECDEcdCommom> page,  String viewCode, int type, String processKey,
		Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode, Boolean noQueryFlag,String exportSql,List paramMain, Map assosiatedKey, Model model, Map excelParamsMap) {
		this.excelModelMappings = excelParamsMap.get("excelModelMappings") != null?(Map<String, Object>) excelParamsMap.get("excelModelMappings"):new HashMap<String, Object>();
		this.excelModels = excelParamsMap.get("excelModels") != null?(Map<String, Object>) excelParamsMap.get("excelModels"):new HashMap<String, Object>();
		this.excelProperties = excelParamsMap.get("excelProperties") != null?(Map<String, Object>) excelParamsMap.get("excelProperties"):new HashMap<String, Object>();
		this.excelProperty = excelParamsMap.get("excelProperty") != null?(Map<String, Object>) excelParamsMap.get("excelProperty"):new HashMap<String, Object>();
		this.excelRunningCustomPropertyCode= excelParamsMap.get("excelRunningCustomPropertyCode") != null?(Map<String, Object>) excelParamsMap.get("excelRunningCustomPropertyCode"):new HashMap<String, Object>();

		auxModelQuery(page, viewCode, type, processKey, flowBulkFlag, hasAttachment, params, permissionCode, noQueryFlag, exportSql, paramMain, assosiatedKey, model, excelParamsMap);
	}

	Map<String, Object> excelModelMappings;//modelCode,CustomPropertyModelMapping
	Map<String, Object> excelModels;//modelCode,Model
	Map<String, Object> excelProperties;//modelCode,List<Property>
	Map<String, Object> excelProperty;//propertyCode,Property
	Map<String, Object> excelRunningCustomPropertyCode;//modelCode,RunningCustomPropertyCode

	@SuppressWarnings("unchecked")
	private void auxModelQuery(Page<SESECDEcdCommom> page, String viewCode, int type, String processKey, Boolean flowBulkFlag,
			Boolean hasAttachment, List<Param> params, String permissionCode,Boolean noQueryFlag,String exportSql, List paramMain, Map assosiatedKey, Model realModel, Map excelParamsMap, Object... objects) {
		int queryType = 0;
		if(objects.length > 0) {
			queryType = (int) objects[0];
		}
		String sql = sqlService.getSqlQuery(viewCode, type);
		//如果存在导出下配置的sql则进行替换
		if(!exportSql.trim().isEmpty())  {
				sql=exportSql;
		}
		// 自定义字段sql拼接
		//sql = generateCustomPropertySql(viewCode, sql);
		String countSql = "";
		//if(viewCode !=null && viewCode.endsWith("_")){
			countSql = "SELECT COUNT(*) count FROM ";
		//}else{
		//	countSql = sqlService.getSqlQuery(viewCode,Sql.TYPE_USED_TOTALS); 感觉没必要
		//}
		User currentUser=(User)getCurrentUser();
		if(sql == null){
			sql = "";
		}
		StringBuilder totalSql = new StringBuilder(sql);
		Boolean crossCompanyFlag = null;
		if (null != sql && sql.length() > 0) {
			StringBuilder s = new StringBuilder();
			List<Object> list = new ArrayList<Object>();
			boolean hasWhere = false;
			String referenceCondition = "";
			String customerSql = "";
			String customerCondition = "";
			String classifySql = "";
			if (null != params && !params.isEmpty()) {
				Param param = params.get(0);
				if("crossCompanyFlag".equals(param.getName())){
					crossCompanyFlag = Boolean.parseBoolean(param.getValue().toString());
					params.remove(0);
				}
			}
			StringBuilder sortOrderByStr = new StringBuilder("");
			if (null != params && !params.isEmpty()) {
				s.append(" WHERE ((");
				hasWhere = true;
				String advQuery = "";
				String classifyCodes = "";
				List<Object> advValues = null;
				String extraQuery = "";
				List<Object> customerValues = null;
				List<Object> extraQueryValues = null;
				List<Object> customerSqlValues = null;
				List<Object> classifySqlValues = null;
				//组合where条件
				for (int i = 0; i < params.size(); i++) {
					Param param = params.get(i);

					if("classifySql".equals(param.getName())){
						classifySql = (String) param.getValue();
						continue;
					}

					if("classifySqlValues".equals(param.getName())){
						classifySqlValues = (List<Object>) param.getValue();
						continue;
					}

					if(type == Sql.TYPE_LIST_REFERENCE && "referenceCondition".equals(param.getName())) {
						referenceCondition = (String) param.getValue();
						continue;
					}

					if("customerValues".equals(param.getName())){
						customerValues = (List<Object>) param.getValue();
						continue;
					}
					if("customerSqlValues".equals(param.getName())){
						customerSqlValues = (List<Object>) param.getValue();
						continue;
					}
					if("customerCondition".equals(param.getName())){
						customerCondition = (String) param.getValue();
						continue;
					}
					if("customerSql".equals(param.getName())){
						customerSql = (String)param.getValue();
						continue;
					}

					if ("advQueryCond".equals(param.getName())) {
						AdvQueryCondition cond = conditionService.toSql((String) param.getValue());
						if (cond != null) {
							advValues = cond.getValues();
							advQuery = cond.getSql();
						}
						continue;
					}
					//数据分类内的高级查询
					if ("classifyCodes".equals(param.getName())) {
						classifyCodes = (String)param.getValue();
						continue;
					}
					if ("extraQueryCond".equals(param.getName())) {
						Map<String,String> oneToManyParams = new HashMap<String,String>();
						oneToManyParams = (Map<String, String>) param.getValue();
						String json = sqlService.getExtraQueryJson(viewCode);
						Matcher matcher = oneToManyPattern.matcher(json);
						while (matcher.find()) {
							String tag = (matcher.group(2));
							String subTag = tag.substring(2, tag.length()-2);
							if(null != oneToManyParams.get(subTag) && ((String)(oneToManyParams.get(subTag))).length() > 0) {
								json = json.replace(tag, oneToManyParams.get(subTag).replace("\"", "SYMBOL_DOUBLE_QUOTE"));
							} else {
								json = json.replace(tag, "");
							}
						}
						if(null != json && json.length() > 0) {
							AdvQueryCondition cond = conditionService.toSql(json);
							if (cond != null) {
								extraQueryValues = cond.getValues();
								extraQuery = cond.getSql();
							}
						}
						continue;
					}
					if ("dataTable-sortColKey".equals(param.getName())) {
						String sortValue = (String) param.getValue();
						String key = null, columnName = null,customKey = null;
						if(sortValue.indexOf("::") > 0) {
							key = sortValue.split("::")[0];
							columnName = sortValue.split("::")[1];
						}else if(sortValue.indexOf(".") > 0){
							customKey = sortValue.split("\\.")[0];
							if(customKey.contains("attrMap")){
								key =customKey;
								columnName = sortValue.split("\\.")[1];
							}else{
								key = sortValue;
							}
						} else {
							key = sortValue;
						}
						int lastDotPos = key.lastIndexOf('.');
						if(null == columnName) {

							columnName = modelServiceFoundation.getPropertyColumnName("SESECD_1.0.0_ecdPanel_EcdCommom", key.substring(lastDotPos + 1), false);
						}
						String tableAlias = lastDotPos < 0 ? "\"ecdCommom\"" : "\"" + key.substring(0, lastDotPos) + "\"";
						if("\"pending\"".equals(tableAlias)) {
							tableAlias = "\"p\"";
						}
						sortOrderByStr.append(tableAlias).append(".").append(columnName);
						continue;
					}
					if ("dataTable-sortColOrder".equals(param.getName())) {
						sortOrderByStr.append(" ").append((String) param.getValue());
						continue;
					}
					if(param.getName().startsWith("\"tree-")){
						if(param.getName().startsWith("\"tree-layRec-")){
							String treeCondition = sqlService.getSqlQuery(viewCode,Sql.TYPE_USED_TREE);
							if (treeCondition!=null && treeCondition.trim().length() > 0) {
								if (hasWhere)
									s.append(" AND ");
								else
									s.append(" WHERE (");

								s.append(treeCondition);
								if(param.getLikeType() == Param.EQUAL_LIKELEFT) {
									list.add(param.getValue());
									list.add((String) param.getValue()+"-%");
								}
								if(param.getLikeType() == Param.LIKE_UNSUPPORT) {
									list.add(param.getValue());
								}
							}
						}
						continue;
					}
					if(!param.getName().startsWith("\"tree-") && param.getLikeType() == Param.EQUAL_LIKELEFT){
						s.append(" AND ( ").append(param.getName()).append("= ? ").append(" OR ")
							.append(param.getName()).append(" like ? )");
						list.add(param.getValue());
						list.add(param.getValue()+"-%");
						continue;
					}
					if (i > 0){
						s.append(" AND ");
					}
					if((null != param.getContainLower() && param.getContainLower())) {
						s.append(" ( ");
					}
					if(!param.getCaseSensitive()) {
						if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
							if(!"DATE".equals(param.getColumnType()) && !"DATETIME".equals(param.getColumnType()) && !"LONG".equals(param.getColumnType()) && !"INTEGER".equals(param.getColumnType()) && !"DECIMAL".equals(param.getColumnType()) && !"BOOLEAN".equals(param.getColumnType())) {
								s.append(" UPPER (");
							}
						}
					}
					if(param.getLikeType() != Param.MULTI_LIKE) {
						s.append(param.getName());
					}
					if(!param.getCaseSensitive()) {
						if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
							if(!"DATE".equals(param.getColumnType()) && !"DATETIME".equals(param.getColumnType()) && !"LONG".equals(param.getColumnType()) && !"INTEGER".equals(param.getColumnType()) && !"DECIMAL".equals(param.getColumnType()) && !"BOOLEAN".equals(param.getColumnType())) {
								s.append(") ");
							}
						}
					}
					String exp = " = ?";
					if (param.getLikeType() == Param.LIKE_UNSUPPORT) {
						s.append(" = ?");
					} else if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_RIGHT || param.getLikeType() == Param.LIKE_LEFT) {
						s.append(" LIKE ?");
						exp = " LIKE ?";
					} else if(param.getLikeType() == Param.GREATE_EQUAL) {
						s.append(" >= ?");
						exp = " >= ?";
					}else if(param.getLikeType() == Param.LESS_EQUAL) {
						s.append(" <= ?");
						exp = " <= ?";
					}else if (param.getLikeType() == Param.GREATE_THAN) {
						s.append(" > ?");
						exp = " > ?";
					}else if (param.getLikeType() == Param.LESS_THAN) {
						s.append(" < ?");
						exp = " < ?";
					}else if (param.getLikeType() == Param.NONE_EQUAL) {
						s.append(" <> ?");
						exp = " <> ?";
					}else if (param.getLikeType() == Param.MULTI_LIKE) {
						exp = " LIKE ?";
						String multiValue = param.getValue().toString();
						String[] values = multiValue.split(",");
						StringBuilder multiSb = new StringBuilder();
						for(int m = 0; m < values.length; m++) {
							if(null != values[m] && values[m].length() > 0) {
								multiSb.append(" OR ");
								multiSb.append(param.getName()).append(" LIKE ? ");
								list.add("%," + values[m] + ",%");
							}
						}
						if(multiSb.length() > 0) {
							s.append(" ( ");
							s.append(multiSb.toString().substring(4));
							s.append(" ) ");
						}
					}else {}
					if(param.getLikeType() == Param.LIKE_ALL || param.getLikeType() == Param.LIKE_LEFT || param.getLikeType() == Param.LIKE_RIGHT) {
						String upperStr = (String) param.getValue();
						if(!param.getCaseSensitive()) {
							upperStr = upperStr.toUpperCase();
						}
						if(param.getLikeType() == Param.LIKE_ALL) {
							param.setValue('%' + upperStr + '%');
						}
						if(param.getLikeType() == Param.LIKE_LEFT) {
							param.setValue(upperStr + '%');
						}
						if(param.getLikeType() == Param.LIKE_RIGHT) {
							param.setValue('%' + upperStr);
						}
					}
					if(param.getLikeType() == Param.LIKE_UNSUPPORT  || param.getLikeType() == Param.NONE_EQUAL) {
						if ("DATETIME".equals(param.getColumnType()) || "DATE".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(DateUtils.ecDateFormat((String) param.getValue()));
							}
						} else if ("LONG".equals(param.getColumnType())){
							if(param.getValue() instanceof String){
								param.setValue(Long.parseLong((String) param.getValue()));
							}
						} else if ("INTEGER".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(Integer.parseInt((String) param.getValue()));
							}
						} else if ("DECIMAL".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(new BigDecimal((String) param.getValue()));
							}
						} else {
							if(param.getValue() instanceof String){
								String upperStr = (String) param.getValue();
								if(!param.getCaseSensitive()) {
									upperStr = upperStr.toUpperCase();
								}
								param.setValue(upperStr);
							} else {
								param.setValue(param.getValue());
							}
						}
					}
					if(param.getLikeType() == Param.GREATE_EQUAL || param.getLikeType() == Param.GREATE_THAN || param.getLikeType() == Param.LESS_EQUAL || param.getLikeType() == Param.LESS_THAN) {
						if ("DATETIME".equals(param.getColumnType()) || "DATE".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(DateUtils.ecDateFormat((String) param.getValue()));
							}
						} else if ("LONG".equals(param.getColumnType())){
							if(param.getValue() instanceof String){
								param.setValue(Long.parseLong((String) param.getValue()));
							}
						} else if ("INTEGER".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(Integer.parseInt((String) param.getValue()));
							}
						} else if ("DECIMAL".equals(param.getColumnType())) {
							if(param.getValue() instanceof String){
								param.setValue(new BigDecimal((String) param.getValue()));
							}
						}

					}

					if(param.getLikeType() != Param.MULTI_LIKE) {
						list.add(param.getValue());
					}
					if(null != param.getContainLower() && param.getContainLower()) {
						String prefix = param.getName().substring(0, param.getName().lastIndexOf("."));
						String columnName = param.getName().substring(param.getName().lastIndexOf(".") + 1);
						if(null != param.getModelInfo() && param.getModelInfo().length > 0) {
							String entityInfo = (param.getModelInfo())[0];
//							String serviceInfo = (param.getModelInfo())[1];
							String layRecColumnName = null;
							if(param.getModelInfo().length > 2) {
								layRecColumnName = (param.getModelInfo())[2];
							}
							//非树形定义getContainLower方法，如果是树形实现IModelTreeLayRecService接口的IModelTreeLayRecService方法
							List<String> layRecs = this.getContainLower(entityInfo, Inflector.getInstance().columnToField(columnName), exp, param.getValue(), false);
//							ServiceReference ref = bundleContext.getServiceReference(serviceInfo);
//							List<String> layRecs = null;
//							if(null != ref) {
//								IModelTreeLayRecService layRecService = (IModelTreeLayRecService) bundleContext.getService(ref);
//								layRecs = layRecService.getContainLower(entityInfo, Inflector.getInstance().columnToField(columnName), exp, param.getValue(), false);
//							}
							if(null != layRecs && !layRecs.isEmpty()) {
								String layRecCond = "";
								for (String layRec : layRecs) {
									layRecCond += " OR " + prefix + "." + (layRecColumnName == null ? "LAY_REC" : layRecColumnName) +  " = ?";
									layRecCond += " OR " + prefix + "." + (layRecColumnName == null ? "LAY_REC" : layRecColumnName) +  " like ?";
									list.add(layRec);
									list.add(layRec + "-%");
								}
								s.append(layRecCond);
							}
							s.append(" ) ");
						}
					}
				}
				if (advQuery != null && advQuery.length() > 0) {
					s.append(" AND ").append(advQuery);
					if (advValues != null) {
						list.addAll(advValues);
					}
				}
				if (extraQuery != null && extraQuery.length() > 0) {
					s.append(" AND ").append(extraQuery);
					if (extraQueryValues != null) {
						list.addAll(extraQueryValues);
					}
				}
				if(customerCondition !=null && customerCondition.length()>0){
					s.append(" AND ").append(customerCondition);
					if(customerValues!=null && customerValues.size()>0){
						list.addAll(customerValues);
					}
				}
				if(customerSql != null && customerSql.length()>0){
					s.append(" AND ").append(customerSql);
					if(customerSqlValues!=null && customerSqlValues.size()>0){
						list.addAll(customerSqlValues);
					}
				}
				if(classifySql != null && classifySql.length() > 0){
					s.append(" AND (").append(classifySql).append(")");
					if(classifySqlValues != null && classifySqlValues.size() > 0){
						list.addAll(classifySqlValues);
					}
				}

				if(type == Sql.TYPE_LIST_REFERENCE){
					if(null != crossCompanyFlag && !crossCompanyFlag&& !getCurrentCompanyId().equals(1L)){
						s.append(" AND \"ecdCommom\".CID in (1,").append(getCurrentCompanyId()+")");
					}
				}

//				if(type == Sql.TYPE_LIST_QUERY || type == Sql.TYPE_LIST_PENDING || type == Sql.TYPE_LIST_REFERENCE) {
//					if(type == Sql.TYPE_LIST_PENDING) {
//						s.append(" AND \"p\".CID = ").append(getCurrentCompanyId());
//					} else if(type == Sql.TYPE_LIST_QUERY){
//						s.append(" AND \"ecdCommom\".CID = ").append(getCurrentCompanyId());
//					} else if(type == Sql.TYPE_LIST_REFERENCE){
//						if(null != crossCompanyFlag && !crossCompanyFlag){
//							s.append(" AND \"ecdCommom\".CID = ").append(getCurrentCompanyId());
//						}
//					}
//				}
				s.append(" ) ");
			}
			// ////PowerCondition

			s.append(") ");


			//辅模型,一个实体只有一个权限操作
			String powerCode = viewCode + "_self";
			if(permissionCode == null || permissionCode.length() == 0) {
				powerCode = permissionCode + "_self";
			}
			View view = viewServiceFoundation.getView(viewCode);
			if(view!=null && (view.getType() == ViewType.REFERENCE || view.getType() == ViewType.REFTREE)){
				if(view.getIsPermission() && view.getPermissionCode().trim().length() > 0){
					powerCode = "SESECD_1.0.0_ecdPanel" + "_" + view.getPermissionCode();
				}
			}
			String	pc = "";



			if (referenceCondition != null && referenceCondition.length() > 0) {
				s.append(" AND (").append(referenceCondition).append(")");
			}
			// 开始处理排序，避免在统计时带入排序条件
			StringBuilder orderPart = new StringBuilder();
			orderPart.append(" ORDER BY ");
			String colOrderByStr = sqlService.getSqlQuery(viewCode,Sql.TYPE_USED_ORDERBY);
			if(null != colOrderByStr && colOrderByStr.length() > 0) {
				if(sortOrderByStr.toString().length() > 0) {
					String[] colOrderByArr = colOrderByStr.substring(1).split(",");
					String[] sortOrderByArr = sortOrderByStr.toString().split(" ");
					Boolean flag = false;
					for(String colOrderBy : colOrderByArr) {
						if(colOrderBy.indexOf(sortOrderByArr[0] + " ") > -1) {
							colOrderByStr = colOrderByStr.replace(colOrderBy, sortOrderByStr.toString());
							flag = true;
							break;
						}
					}
					if(!flag) {
						orderPart.append(sortOrderByStr).append(",");
					}
					orderPart.append(colOrderByStr.substring(1)).append(",");
				} else {
					orderPart.append(colOrderByStr.substring(1)).append(",");
				}
			} else {
				if(sortOrderByStr.toString().length() > 0) {
					orderPart.append(sortOrderByStr).append(",");
				}
			}
			if(type == Sql.TYPE_LIST_PENDING) {
				orderPart.append(" \"p\".ID DESC");
			} else {
				orderPart.append(" \"ecdCommom\".ID DESC");
			}
			Object[] arr = list.toArray();
			totalSql.append(s);
			String realSql = totalSql.toString() + orderPart.toString();
			// 突破ORACLE 30个字符限制
			Map<String, String> maps = new HashMap<String, String>();
			realSql = matchSql(pattern, realSql, "T", maps, 2, 4);
			countSql += " ( " + totalSql.toString() + " ) T";
			countSql = replaceSql(pattern, countSql, maps, 4, 2);
			realSql = matchSql(p, realSql, "E", null, 4, 6);
			countSql = matchSql(p, countSql, "E", null, 4, 6);
			//组织总条数，合计SQL
			countSql = replaceSql(countPattern, countSql, maps, 4, 2);
			// SUM数据强转decimal，防止溢出
			Matcher sumMatcher = sumPattern.matcher(countSql);
			while (sumMatcher.find()) {
				String tag = sumMatcher.group(2);
				Pattern sumPattern1 = Pattern.compile("SUM\\(" + tag + "\\)");
				Matcher matcher1 = sumPattern1.matcher(countSql);
				countSql = matcher1.replaceAll("SUM(CAST(" + tag + " AS DECIMAL(38,6)))");
			}
			//计算条数
			if (page.needCount()) {
				//String countSql = "SELECT COUNT(*) FROM (" + realSql + ")";
				//Long count = ((Number) ecdCommomDao.createNativeQuery(countSql, arr).uniqueResult()).longValue();
				Long count = 0l;
				Map<String, BigDecimal> resultTotals = new HashMap<String, BigDecimal>();
				List<Map<String, Object>> resultCountList = (List<Map<String, Object>>) ecdCommomDao.createNativeQuery(countSql, arr)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if (null != resultCountList && !resultCountList.isEmpty()) {
					Map<String, Object> resultCounts = resultCountList.get(0);
					for (Map.Entry<String, Object> entry : resultCounts.entrySet()) {
						String key = entry.getKey();
						if(maps.containsKey(key)) {
							key = (String) maps.get(key);
						}
						if (key.equalsIgnoreCase("count")) {
							count = ((Number) (null == entry.getValue() ? 0 : entry.getValue())).longValue();
						} else {
							resultTotals.put(key, BigDecimal.valueOf(((Number) (null == entry.getValue() ? 0 : entry.getValue())).doubleValue()));
						}
					}
				}
				//22.3.9 修改现场树结构单据查询超500报错问题
				//if(!page.isExportFlag() && !page.isPaging() && count > page.getMaxPageSize()) {
				//	  throw new BAPException(BAPException.Code.RESULT_COUNT_EXCEED_MAX_PAGE_SIZE);
				//}
				page.setTotalCount(count);
				page.setResultTotals(resultTotals);
			}
			if(!noQueryFlag)  {
  			   NativeQuery query=ecdCommomDao.createNativeQuery(realSql.toString())  ;
				/*List<SESECDEcdCommom> result = new ArrayList<SESECDEcdCommom>();
				if(page.isExportFlag()) {
					result = getResult(page, query, new PendingResultTransformer(SESECDEcdCommom.class,ecdCommomDao,maps,true));
				}else {
					result = getResult(page, query, new PendingResultTransformer(SESECDEcdCommom.class,ecdCommomDao,maps));
				}
				modelServiceFoundation.initCacheData();*/
				List<SESECDEcdCommom> result = getResult(page, query, new PendingResultTransformer(SESECDEcdCommom.class,ecdCommomDao,maps));

				List<Map> propList = page.getProperties();
				for(Map m : propList){
					if(m.get("isCustom") != null && m.get("isCustom").equals("true") && m.get("columntype").equals("SYSTEMCODE") ){
						for(SESECDEcdCommom model : result){
							Map<String, Object> map1 = model.getAttrMap();
							if(map1!=null && map1.size()>0){
								for(Map.Entry<String, Object> entry : map1.entrySet()) {
									if(entry.getKey().equals(m.get("name")) && entry.getValue() != null && !entry.getValue().equals("")) {
										String[] strs = new String[2];
										strs[0] = entry.getValue().toString();
										strs[1] = entry.getValue().toString();
										map1.put(entry.getKey(), strs);
									}
								}
							}
						}
					}else if(m.get("isCustom") != null && m.get("isCustom").equals("true") && m.get("columntype").equals("OBJECT") ){
						for(SESECDEcdCommom model : result){
							Map<String, Object> map1 = model.getAttrMap();
							if(map1!=null && map1.size()>0){
								for(Map.Entry<String, Object> entry : map1.entrySet()) {
									if(entry.getKey().equals(m.get("name")) && entry.getValue() != null && !entry.getValue().equals("")) {
										String[] strs = new String[2];
										strs[0] = entry.getValue().toString();
										String methodName = "get" + entry.getKey().toString().substring(0, 1).toUpperCase() + entry.getKey().toString().substring(1);
										String businessKeyValue = "";
										Long idValue = null;

										try {
											Property prop = modelServiceFoundation.findPropertyByCode(m.get("propertyCode").toString(), null);
											String methodName2 = "get" + prop.getName().substring(0, 1).toUpperCase() + prop.getName().substring(1);
											Method idMethod = model.getClass().getMethod(methodName2);
											idValue = (Long) idMethod.invoke(model);
											List<CustomPropertyModelMapping> modelMappings = viewServiceFoundation.findCustomPropertyForAsso(
												modelServiceFoundation.findModelFromProperty(prop.getCode()).getCode(), prop.getCode());
											String serviceStr = "";
											if(modelMappings != null && modelMappings.size()>0){
												String moduleName = modelMappings.get(0).getAssociatedProperty().getModel().getModuleCode();
												if(modelMappings.get(0).getAssociatedProperty().getModel().getModuleCode().indexOf("_") != -1){
													moduleName = modelMappings.get(0).getAssociatedProperty().getModel().getModuleCode().substring(0,modelMappings.get(0).getAssociatedProperty().getModel().getModuleCode().indexOf("_"));
												}
												if(moduleName.equals("sysbase")){
													serviceStr = "com.supcon.orchid.foundation.services." + modelMappings.get(0).getAssociatedProperty().getModel().getModelName() + "Service";
												}else{
													if(modelMappings.get(0).getAssociatedProperty().getModel().getEcVersion().equals("1.0")){
														serviceStr = "com.supcon.orchid."+ moduleName + ".services." + modelMappings.get(0).getAssociatedProperty().getModel().getModelName() + "Service";
													}else{
														serviceStr = "com.supcon.orchid."+ moduleName + ".services." + modelMappings.get(0).getAssociatedProperty().getModel().getJpaName() + "Service";
													}
												}
												Class realServiceClazz = null;
												try {
													realServiceClazz = Class.forName(serviceStr);
												} catch (ClassNotFoundException e) {
													// TODO Auto-generated catch block
													log.error(e.getMessage(),e);
												}
												//通过反射代替bundle是否可行
												Object realService = SpringUtil.getBean(realServiceClazz);
//												BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
//												ServiceReference ref = bundleContext.getServiceReference(serviceStr);
//												Object realService = bundleContext.getService(ref);

												List<Property> props = modelServiceFoundation.findProperties(modelMappings.get(0).getAssociatedProperty().getModel(), null);
												Property bussinessKeyProp = null;
												for(Property p : props){
													if(p.getIsBussinessKey()){
														bussinessKeyProp = p;
														break;
													}
												}
												String getBussinessKeyMethodName = "get" + bussinessKeyProp.getName().substring(0, 1).toUpperCase() + bussinessKeyProp.getName().substring(1);

												if(moduleName.equals("sysbase")){
													Method loadMethod = realService.getClass().getMethod("load", new Class[] { Long.class });
													Object obj = loadMethod.invoke(realService, idValue);
													Method getBusKeyValueMethod = obj.getClass().getMethod(getBussinessKeyMethodName);
													businessKeyValue = (String) getBusKeyValueMethod.invoke(obj);
												}else{
													String methodName3 = "get" + modelMappings.get(0).getAssociatedProperty().getModel().getModelName();
													Method loadMethod = realService.getClass().getMethod(methodName3, new Class[] { long.class });
													Object obj = loadMethod.invoke(realService, idValue);
													Method getBusKeyValueMethod = obj.getClass().getMethod(getBussinessKeyMethodName);
													businessKeyValue = (String) getBusKeyValueMethod.invoke(obj);
												}
											}
										} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
											log.error(e.getMessage(), e);
										}
										strs[1] = businessKeyValue;
										map1.put(entry.getKey(), strs);
									}
								}
							}
						}
					}
				}

				page.setResult(result);
				if(null != hasAttachment && hasAttachment) {
					for (SESECDEcdCommom ecdCommom : result) {
						long count = 0l;
						count = documentService.getCountByLinkIdAndType(ecdCommom.getId(), "SESECD_ecdPanel_EcdCommom");
						if(count > 0) {
							List<Document> documents = documentService.getByLinkIdAndType(ecdCommom.getId(), "SESECD_ecdPanel_EcdCommom");
							if(null != documents && !documents.isEmpty()) {
								ecdCommom.setDocument(documents.get(0));
								ecdCommom.setBapAttachmentInfo(documents.get(0).getName() + "@_@BAP@_@" + InternationalResource.get("Button.text.more", getCurrentLanguage()) + "(" + count + ")");
							}
						}
					}
				}


				for (SESECDEcdCommom ecdCommom : result) {
				}
				if(exportSql.trim().isEmpty()){
					getConfigAssoPropsResult(viewCode, "", result); // 获取配置的关联模型字段的结果集
				}
			}
		}
	}

	private Map<String, Object> linkMainMap(String modelCode, String entityCode){
		Model mainModel = null;
		if(excelModels!=null && excelModels.get("mainModel")!=null ){
			mainModel = (Model) excelModels.get("mainModel");
		}else{
			mainModel = modelServiceFoundation.getMainModel(entityCode);
		}
		Model model = null;
		if(excelModels!=null && excelModels.get(modelCode)!=null ){
			model = (Model) excelModels.get(modelCode);
		}else{
			model = modelServiceFoundation.getModel(modelCode);
		}
		List<Property> props = null;
		if(excelProperties != null && excelProperties.get(model.getCode())!=null ){
			props = (List<Property>) excelProperties.get(model.getCode());
		}else{
			props = modelServiceFoundation.findProperties(model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("needChange", "no!!");
		for(Property p : props){
			if(!p.getIsCustom() && p.getType().toString().equals("OBJECT") && p.getAssociatedProperty() != null){
				if(p.getAssociatedProperty().getModel().getCode().equals(mainModel.getCode())){
					return map;
				}else{
					//List<Property> props2 = modelServiceFoundation.findProperties(p.getAssociatedProperty().getModel());
					List<Property> props2 = null;
					if(excelProperties != null && excelProperties.get(p.getAssociatedProperty().getModel().getCode())!=null ){
						props2 = (List<Property>) excelProperties.get(p.getAssociatedProperty().getModel().getCode());
					}else{
						props2 = modelServiceFoundation.findProperties(p.getAssociatedProperty().getModel());
					}
					for(Property p2 : props2){
						if(!p2.getIsCustom() && p2.getType().toString().equals("OBJECT") && p2.getAssociatedProperty() != null
								&& p2.getAssociatedProperty().getModel().getCode().equals(mainModel.getCode())){
							map.put("needChange", "yep");
							map.put("origin", p);
							map.put("target", p2);
							return map;
						}
					}
				}
			}
		}
		return map;
	}



	private String getDbIdName(){
		String sql = "select column_name from runtime_property where model_code = ? and name = 'id'";
		String dbIDName = ecdCommomDao.createNativeQuery(sql, "SESECD_1.0.0_ecdPanel_EcdCommom").uniqueResult().toString();
		return dbIDName;
	}
	
	private List<String> getParamNameList(String con){
		List<String> paramNameList=new ArrayList<String>();
		Pattern p=Pattern.compile(":(\\w+)");
		Matcher m=p.matcher(con);
		while(m.find()){
			paramNameList.add(m.group(1));
		}
		return paramNameList;
	}

	@Override
	public List<String> getSystemCodeFullPathNameByEntityCode(String entityCode) {
		String sql = "select value from base_systemcode where entity_code = ?";
		List<String> strs = ecdCommomDao.createNativeQuery(sql, entityCode).list();
		return strs;
	}

	@Override
	public List<String> getRunningCustomProperties(String entityCode){
		List<String> list = null;
		String sql = "select property_code from BASE_CP_MODEL_MAPPING where model_code = ? and enable_custom = 1";
		list = ecdCommomDao.createNativeQuery(sql, entityCode).list();
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getAssProperty(String propertyCode){
		String assProperty = null;
		String sql = "select associated_property_code from runtime_property where code = ?";
		assProperty = ecdCommomDao.createNativeQuery(sql,propertyCode).uniqueResult()!=null?ecdCommomDao.createNativeQuery(sql,propertyCode).uniqueResult().toString():"";
		return assProperty;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getPropertyModelCode(String propertyCode){
		String sql = "select model_code from runtime_property where code = ?";
		String modelCode =  ecdCommomDao.createNativeQuery(sql,propertyCode).uniqueResult().toString();
		return modelCode;
	}
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public  Object generateObjectFromJson(String jsonStr, Class clazz){
		return com.supcon.orchid.ec.utils.JSONUtil.generateObjectFromJson(jsonStr,clazz,ecdCommomDao.getSessionFactory());
	}

	/**
	 * 以下为兼容视图热部署之前代码的方法
	 */



	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService){
		saveEcdCommom(ecdCommom, dataGridService, null);
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService, String viewCode){
		saveEcdCommom(ecdCommom, dataGridService, viewCode, null);
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService, String viewCode, String eventTopic, boolean... isImport){
		this.saveEcdCommom(ecdCommom, dataGridService, viewCode, eventTopic,null,isImport);
	}

	@Override
	public void saveEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService, String viewCode, String eventTopic,SignatureLog signatureLog, boolean... isImport){
		boolean isNew = false;
		String entityCode = "SESECD_1.0.0_ecdPanel";
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		String url = null;
		if(null != ecdCommom.getId() && ecdCommom.getId() > 0){
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.pending.edit");
				AuditUtil.setAuditOperationType("2");
			}
			props.put("eventType", "modify");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/modify";
		}else{
			isNew = true;
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.print.template.add");
				AuditUtil.setAuditOperationType("1");
			}
			props.put("eventType", "add");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/add";
		}
		Boolean viewIsView = false;
		if(viewCode != null && !viewCode.trim().isEmpty()){
			View view = viewServiceFoundation.getViewForMethodCheckViewType(viewCode);
			if(null != view) {
				viewIsView = (view.getType() == ViewType.VIEW);
			}
		}
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom, viewIsView);

		if (viewIsView) {
			ecdCommomDao.saveWithRevertVersion(ecdCommom);
		} else {
			if(isNew){
				ecdCommomDao.save(ecdCommom);
			}else{
				ecdCommomDao.merge(ecdCommom);
			}
		}
		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}
		if (dataGridService != null) {
			ecdCommomDao.flush();
			dataGridService.execute();
		}

		// 一对多情况处理

		// 根据配置规则生成编码
		try {
			generateEcdCommomCodes(ecdCommom, viewIsView);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}
		// 根据配置规则生成摘要
		try {
			generateEcdCommomSummarys(ecdCommom, viewIsView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}

		afterSaveEcdCommom(ecdCommom, viewIsView);


		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		ecdCommomDao.flush();
		ecdCommomDao.clear();
		ecdCommom = ecdCommomDao.load(ecdCommom.getId());


		params.add(ecdCommom);
		if(signatureLog != null) {
			Object businessKey=null;
			businessKey=ecdCommom.getId();
			if(businessKey != null) {
				signatureLog.setBusinessKey(businessKey.toString());
			}
			if(null != AuditUtil.getCurrentAudit() && null != AuditUtil.getCurrentAudit().getOperationAudit()){
				signatureLog.setOperateLogUuid(AuditUtil.getCurrentAudit().getOperationAudit().getUuid());
			}
			signatureLog.setTableId(ecdCommom.getId());
			String msgId="moduleCode:SESECD_1.0.0#entityCode:SESECD_1.0.0_ecdPanel#modelCode:SESECD_1.0.0_ecdPanel_EcdCommom#timeStamp:"+String.valueOf(System.currentTimeMillis());
			reliableMessageSenderService.sendQueue(msgId,signatureLog);
		}
	}

	@Override
	public Map<Object, Object> ecdCommomDataGridImport(SESECDEcdCommom ecdCommom, DataGridService dataGridService, String viewCode, String eventTopic,Property businessKey, boolean isImport){
		Map<Object, Object> map = new HashMap<Object, Object>();
		boolean isNew = false;
		String entityCode = "SESECD_1.0.0_ecdPanel";
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		String url = null;
		if(ecdCommom.getId() != null && ecdCommom.getId() > 0){
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.pending.edit");
				AuditUtil.setAuditOperationType("2");
			}
			props.put("eventType", "modify");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/modify";
		}else{
			isNew = true;
			if(StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD())){
				AuditUtil.setAuditDes("ec.print.template.add");
				AuditUtil.setAuditOperationType("1");
			}
			props.put("eventType", "add");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/add";
		}
		Boolean viewIsView = false;
		if(viewCode != null && !viewCode.trim().isEmpty()){
			View view = viewServiceFoundation.getViewForMethodCheckViewType(viewCode);
			if(null != view) {
				viewIsView = (view.getType() == ViewType.VIEW);
			}
		}
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom, viewIsView);

		if (viewIsView) {
			ecdCommomDao.saveWithRevertVersion(ecdCommom);
		} else {
			if(isNew){
				ecdCommomDao.save(ecdCommom);
			}else{
				ecdCommomDao.merge(ecdCommom);
			}
		}

		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}

		if (dataGridService != null) {
			ecdCommomDao.flush();
			dataGridService.execute();
		}

		// 一对多情况处理

		// 根据配置规则生成编码
		try {
			generateEcdCommomCodes(ecdCommom, viewIsView);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}
		// 根据配置规则生成摘要
		try {
			generateEcdCommomSummarys(ecdCommom, viewIsView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}

		afterSaveEcdCommom(ecdCommom, viewIsView);


		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		ecdCommomDao.flush();
		ecdCommomDao.clear();

		String virturalId = ecdCommom.getVirtualId();

		ecdCommom = ecdCommomDao.load(ecdCommom.getId());

		if(virturalId != null && virturalId.length() > 0){
			map.put(virturalId, ecdCommom.getId());
		}else{
			String methodName = "get" + businessKey.getName().substring(0, 1).toUpperCase() + businessKey.getName().substring(1);
			Object bkValue = null;
			try {
				Method getBkValue = ecdCommom.getClass().getMethod(methodName);
				bkValue = getBkValue.invoke(ecdCommom);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error(e.getMessage(), e);
			}
			map.put(bkValue, ecdCommom.getId());
		}


		params.add(ecdCommom);
		return map;
	}


	@Override
	public void mergeEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService){
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom);
		ecdCommomDao.merge(ecdCommom);

		if(null != ecdCommom && (StringUtils.isEmpty(AuditUtil.getColumnStringD()) || SESECDEcdCommom.MODEL_CODE.equals(AuditUtil.getColumnStringD()))){
			AuditUtil.setColumnStringB(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringA(null == ecdCommom.getId() ? "" : ecdCommom.getId().toString());
			AuditUtil.setColumnStringC(ecdCommom.getId().toString());
		}

		if (dataGridService != null) {
			dataGridService.execute();
		}

		afterSaveEcdCommom(ecdCommom);
	}

	/**
	 * Excel导出
	 */
	@Override
	public void excelExport(){}

	/**
	 * 页面打印
	 */
	@Override
	public void print(int printType){
		if(printType == 0){
			AuditUtil.setAuditDes(InternationalResource.get("ec.print.page.print"));
		}else if(printType == 1){
			AuditUtil.setAuditDes(InternationalResource.get("ec.view.controlPrint"));
		}
	}

	@Override
	public void batchSaveEcdCommom(SESECDEcdCommom ecdCommom, DataGridService dataGridService,View view,List<Event>  events, String eventTopic, boolean... isImport){
		String entityCode = "SESECD_1.0.0_ecdPanel";
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("callType", "service");
		props.put("entityCode", "SESECD_1.0.0_ecdPanel");
		String url = null;
		if(ecdCommom.getId()!=null && ecdCommom.getId()>0){
			props.put("eventType", "modify");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/modify";
		}else{
			props.put("eventType", "add");
			url = "com/supcon/orchid/entities/sync/sESECD_100_ecdPanel/add";
		}
		Boolean viewIsView = false;
		if(view != null ){
			viewIsView = (view.getType() == ViewType.VIEW);
		}
		ReflectUtils.filterObjectIdIsNVL(ecdCommom);
		beforeSaveEcdCommom(ecdCommom, viewIsView);

		if (viewIsView) {
			ecdCommomDao.saveWithRevertVersion(ecdCommom);
		} else {
			ecdCommomDao.save(ecdCommom);
		}

		if (dataGridService != null) {
			ecdCommomDao.flush();
			dataGridService.execute();
		}

		// 一对多情况处理

		// 根据配置规则生成编码
		try {
			generateEcdCommomCodes(ecdCommom, viewIsView);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}
		// 根据配置规则生成摘要
		try {
			generateEcdCommomSummarys(ecdCommom, viewIsView);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BAPException(e.getMessage(), e);
		}

		afterSaveEcdCommom(ecdCommom, viewIsView);


		List<SESECDEcdCommom> params = new ArrayList<SESECDEcdCommom>();
		ecdCommomDao.flush();
		ecdCommomDao.clear();
		ecdCommom = ecdCommomDao.load(ecdCommom.getId());


		params.add(ecdCommom);
	}

	@Override
	public Page<SESECDEcdCommom> getByPage(Page<SESECDEcdCommom> page,DetachedCriteria detachedCriteria){
		return ecdCommomDao.findByPage(page, detachedCriteria);
	}



	/**
	 * 获取包含下级的LayRec
	 * @param modelName 模型名称
	 * @param fieldName 查询字段名
	 * @param exp  操作符
	 * @param value 值
	 * @param crossCompany 跨公司
	 * @return List<String> layRec
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> getContainLower(String modelName, String fieldName, String exp, Object value, Boolean crossCompany){
		return null;
	}

	@Override
	public String generateTableNo(){
		this.counter.setPattern(modelServiceFoundation.getEntity("SESECD_1.0.0_ecdPanel").getPrefix()+"_{1,date,yyyyMMdd}_{0,number,000}");
		return this.counter.incrementAndGetString(new Date(),new Date());
	}

	public String ClobToString(Clob clob) {
		String reString = "";
		Reader is = null;
		try {
			is = clob.getCharacterStream();
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}

		// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			try {
				s = br.readLine();
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
		reString = sb.toString();
		return reString;
	}

	private void generateClassificCondition(List<Param> params, String classifyCodes, String split,
			QueryEntity queryEntity) {
		if (null != classifyCodes) {
			String[] classifyCodeArray = classifyCodes.split(",");
			Map<String, List<Map<String, List<Object>>>> groupMap = new HashMap<String, List<Map<String, List<Object>>>>();
			// Map<String, List<Object>> classifyMap = new HashMap<String, List<Object>>();
			AdvQueryCondition advCondition = null;
			for (int i = 0; i < classifyCodeArray.length; i++) {
				if (!classifyCodeArray[i].startsWith("bap:adv:classific:")) {
					CustomerCondition cuscon = viewServiceFoundation
							.findCustomerConditionByClassificCode(classifyCodeArray[i]);
					DataClassific dc = viewServiceFoundation.getDataClassific(classifyCodeArray[i]);
					Map<String, List<Object>> classifyMap = new HashMap<String, List<Object>>();
					boolean flag = false;
					if (cuscon != null && cuscon.getSql() != null && cuscon.getSql().length() > 0) {
						flag = true;
						String customerSql = cuscon.getSql();
						List<Object> list = new ArrayList<Object>();
						customerSql = parseSqlCondition(customerSql, list, null, split, queryEntity.getCustomCondition());
						classifyMap.put(customerSql, list);

					} else if (cuscon != null && cuscon.getJsonCondition() != null
							&& cuscon.getJsonCondition().length() > 0) {
						flag = true;
						String jsonString = cuscon.getJsonCondition();
						AdvQueryCondition acon = parseJsonCondition(jsonString, queryEntity);
						String customer = acon.getSql();
						if (customer != null) {
							List<Object> customerValue = acon.getValues();
							classifyMap.put(customer, customerValue);
						}
					}
					if (flag) {
						DataGroup dataGroup = viewServiceFoundation.getDataGroupFromDataClassific(dc.getCode());
						if (groupMap.containsKey(dataGroup.getCode())) {
							List<Map<String, List<Object>>> temp = groupMap.get(dataGroup.getCode());
							temp.add(classifyMap);
						} else {
							List<Map<String, List<Object>>> temp = new ArrayList<Map<String, List<Object>>>();
							temp.add(classifyMap);
							groupMap.put(dataGroup.getCode(), temp);
						}
					}
				} else {
					String idStr = classifyCodeArray[i].substring(classifyCodeArray[i].lastIndexOf(":") + 1);
					advCondition = conditionService.getAdvQueryConditionAndSubs(Long.parseLong(idStr));
					advCondition = conditionService.toSql(advCondition);
				}
			}

			String totalClassifySql = "";
			List<Object> classObjs = new ArrayList<Object>();
			if (groupMap != null && groupMap.size() > 0) {
				for (Map.Entry<String, List<Map<String, List<Object>>>> bentry : groupMap.entrySet()) {
					if (bentry.getValue() != null) {
						for (Map<String, List<Object>> classifyMap : bentry.getValue()) {
							if (classifyMap != null && classifyMap.size() > 0) {
								if (totalClassifySql.length() > 0) {
									totalClassifySql += " AND ";
								}
								if (classifyMap.size() > 1) {
									totalClassifySql += " ( ";
								}
								String classifySql = "";
								for (Map.Entry<String, List<Object>> entry : classifyMap.entrySet()) {
									classifySql = classifySql + " OR " + entry.getKey();
									if (entry.getValue() != null && entry.getValue().size() > 0) {
										classObjs.addAll(entry.getValue());
									}
								}
								classifySql = classifySql.substring(3);
								totalClassifySql += classifySql;
								if (classifyMap.size() > 1) {
									totalClassifySql += " ) ";
								}
							}
						}
					}
				}
			}
			if (null != advCondition) {
				StringBuilder advSql = new StringBuilder();
				List<Object> advValue = new ArrayList<Object>();
				if (null != advCondition.getSql() && !advCondition.getSql().isEmpty()) {
					if (totalClassifySql.length() > 0) {
						advSql.append(" AND ");
					}
					advSql.append(advCondition.getSql());
					advValue.addAll(advCondition.getValues());
					totalClassifySql += advSql;
					classObjs.addAll(advValue);
				}
			}
			params.add(new Param("classifySql", totalClassifySql));
			params.add(new Param("classifySqlValues", classObjs));
		}
	}

	private void generateCustomerCondition(List<Param> params, String viewCode, String split, QueryEntity queryEntity) {
		View v = viewServiceFoundation.getView(viewCode);
		if (v != null && v.getIsShadow() != null && v.getIsShadow()) {
			View shadow = viewServiceFoundation.getShadowViewFromView(v.getCode());
			if (shadow != null) {
				viewCode = shadow.getCode();
			}
		}
		CustomerCondition ccon = null;
		if (v.getShowType().equals(ShowType.LAYOUT)) {
			// 如果是布局情况下在布局中获取center区域的viewCode
			Map viewConfig = viewServiceFoundation.getExtraViewFullConfigMap(v);
			if (viewConfig.get("layout") != null && ((Map) viewConfig.get("layout")).get("center") != null
					&& ((Map) ((Map) viewConfig.get("layout")).get("center")).get("vcode") != null) {
				ccon = viewServiceFoundation.findCustomerConditionByViewCode(
						((Map) ((Map) viewConfig.get("layout")).get("center")).get("vcode").toString());
			} else {
				ccon = viewServiceFoundation.findCustomerConditionByViewCode(viewCode);
			}
		} else {
			ccon = viewServiceFoundation.findCustomerConditionByViewCode(viewCode);
		}
		if (ccon != null && ccon.getSql() != null && ccon.getSql().length() > 0) {
			String customerSql = ccon.getSql();
			if (customerSql.indexOf("return") > -1) {
				Map<String, Object> variables = new HashMap<String, Object>();
				ObjectMapper objectMapper = new ObjectMapper();
				/// To Test
				// 把queryEntity转为map
				Map<String, Object> parameters = objectMapper.convertValue(queryEntity, Map.class);
				for (String key : parameters.keySet()) {
					variables.put(key, parameters.get(key));
				}
				/// ToDo 这里对variables中的parameters处理时应该需要改动，因为parameters的类型变了
				variables.put("parameters", parameters);
				customerSql = EngineScriptExecutor.eval(customerSql, variables).toString();
			}
			List<Object> list = new ArrayList<Object>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			customerSql = parseSqlCondition(customerSql, list, listMap, split, queryEntity.getCustomCondition());
			params.add(new Param("customerSql", customerSql));
			params.add(new Param("customerSqlValues", list));
			params.add(new Param("customerSqlListMap", listMap));
		} else if (ccon != null && ccon.getJsonCondition() != null && ccon.getJsonCondition().length() > 0) {
			String jsonString = ccon.getJsonCondition();
			AdvQueryCondition acon = parseJsonCondition(jsonString, queryEntity);
			String customer = acon.getSql();
			List<Object> customerValue = acon.getValues();
			params.add(new Param("customerCondition", customer));
			params.add(new Param("customerValues", customerValue));
		}
	}

	private void generateCustomerConditionByDg(List<Param> params, String dgCode, String split,
			QueryEntity queryEntity) {
		CustomerCondition ccon = viewServiceFoundation.findCustomerConditionByDatagridCode(dgCode);
		if (ccon != null && ccon.getSql() != null && ccon.getSql().length() > 0) {
			String customerSql = ccon.getSql();
			if (customerSql.indexOf("return") > -1) {
				Map<String, Object> variables = new HashMap<String, Object>();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> parameters = objectMapper.convertValue(queryEntity, Map.class);
				for (String key : parameters.keySet()) {
					variables.put(key, parameters.get(key));
				}
				variables.put("parameters", parameters);
				customerSql = EngineScriptExecutor.eval(customerSql, variables).toString();
			}
			List<Object> list = new ArrayList<Object>();
			Map<String, Object> listMap = new HashMap<String, Object>();
			customerSql = parseSqlCondition(customerSql, list, listMap, split, queryEntity.getCustomCondition());
			params.add(new Param("customerSql", customerSql));
			params.add(new Param("customerSqlValues", list));
			params.add(new Param("customerSqlListMap", listMap));
		} else if (ccon != null && ccon.getJsonCondition() != null && ccon.getJsonCondition().length() > 0) {
			String jsonString = ccon.getJsonCondition();
			AdvQueryCondition acon = parseJsonCondition(jsonString, queryEntity);
			String customer = acon.getSql();
			List<Object> customerValue = acon.getValues();
			params.add(new Param("customerCondition", customer));
			params.add(new Param("customerValues", customerValue));
		}
	}

	private String parseSqlCondition(String sql, List<Object> list, Map<String, Object> listMap, String split,
			Map<String, Object> customCondition) {
		// Pattern p = Pattern.compile("\\$\\{(.+?),(.+?)\\}");
		Matcher m = p4.matcher(sql);
		while (m.find()) {
			String str = m.group();
			/// To Test
			String value = String.valueOf(customCondition.get(m.group(1).toString()));
			String type = m.group(2);
			if ("int".equalsIgnoreCase(type)) {
				Integer integer = new Integer(value);
				list.add(integer);
			} else if ("date".equals(type)) {
				value = value.replaceAll("/", "-");
				SimpleDateFormat sdf = null;
				if (value.length() == 19) {
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				} else if (value.length() == 10) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					list.add(new Date(value));
				}
				if (sdf != null) {
					try {
						list.add(sdf.parse(value));
					} catch (Exception e) {
						log.info(e.getMessage());
					}
				}
			} else if ("double".equals(type)) {
				Double doubles = new Double(value);
				list.add(doubles);
			} else if ("long".equalsIgnoreCase(type)) {
				if ("CURRENT_COMPANY_ID".equals(m.group(1))) {
					list.add(getCurrentCompanyId());
				} else {
					Long longs = new Long(value);
					list.add(longs);
				}
			} else if ("string".equalsIgnoreCase(type)) {
				list.add(value);
			/*} else if ("method".equalsIgnoreCase(type)) {
				value = m.group(1);
				try {
					list.add(Ognl.getValue(value, this));
				} catch (Exception e) {
					log.info(e.getMessage());
				}*/
			} else if ("array".equalsIgnoreCase(type) || "list".equalsIgnoreCase(type)) {
				String splitChar = (split == null ? "," : split.toString());
				if (listMap != null) {
					listMap.put(m.group(1), value.split(splitChar));
					sql = sql.replace(str, ":" + m.group(1));
				}
			} else {
				list.add(value);
			}
			sql = sql.replace(str, "?");
		}
		return sql;
	}

	private AdvQueryCondition parseJsonCondition(String jsonString, QueryEntity queryEntity) {
		// Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher m = p5.matcher(jsonString);
		while (m.find()) {
			String str = m.group();
			String value = ReflectUtils.getPropertyValue(queryEntity, m.group(1), null); // request.getParameter(str.substring(2,
																							// str.length()-1));
			if (value != null) {
				jsonString = jsonString.replace(str, value);
			} else {
				jsonString = jsonString.replace(str, "");
			}
		}
		return conditionService.toSql(jsonString);
	}

	/**
	 * 实际执行查询
	 *
	 * @param page
	 * @param viewCode
	 * @param datagridCode
	 * @param searchObjects
	 * @param currentSqlType
	 * @param tableProcessKey
	 * @param flowBulkFlag
	 * @param hasAttachment
	 * @param params
	 * @param permissionCode
	 * @param noQueryFlag
	 * @param exportSql
	 * @return
	 */
	private Map<String, Object> findExportDataInfos(Page<SESECDEcdCommom> page, String viewCode,
			String datagridCode, Object[] searchObjects, int currentSqlType, String tableProcessKey,
			Boolean flowBulkFlag, Boolean hasAttachment, List<Param> params, String permissionCode, Boolean noQueryFlag,
			String exportSql, Integer findExportDataInfosCount,QueryEntity queryEntity) {
		Map<String, Object> exportMap = new HashMap<String, Object>();
		if (null != datagridCode && !datagridCode.equals("")) {
			viewCode = viewCode + "," + datagridCode; // 增强型视图中，列表ptcode
		}
		if (null != searchObjects && searchObjects.length > 0) {
			findEcdCommoms(page, viewCode, currentSqlType, tableProcessKey, flowBulkFlag, hasAttachment, params,
					permissionCode, noQueryFlag, exportSql, exportMap,queryEntity, searchObjects);
		} else {
			findEcdCommoms(page, viewCode, currentSqlType, tableProcessKey, flowBulkFlag, hasAttachment, params,
					permissionCode, noQueryFlag, exportSql, exportMap,queryEntity);
		}
		findExportDataInfosCount++;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("viewCode", viewCode);
		resultMap.put("page", page);
		resultMap.put("findExportDataInfosCount", findExportDataInfosCount);
		return resultMap;
	}

	/**
	 * 获取助记码中使用
	 *
	 * @param list
	 * @return
	 */
	private String getMneIDs(List<Object[]> list) {
		if (list.size() == 0) {
			return null;
		} else {
			String res = "";
			for (Object[] m : list) {
				if (m[0] != null) {
					res += "," + m[0];
				}
			}
			return res.substring(1);
		}
	}
	/**
	 * 新增地图空间数据——调用地图接口
	 * @param ecdCommom
	 * @param viewCode
	 * @return
	 */
	public void addLayerInfo(Object object, String viewCode, DataGrid dataGrid) throws Exception{
		if (null == viewCode && null == dataGrid) {
			return;
		}

		// 模型Code
		String modelCode="";
		if(null!=object){
			modelCode = (String) object.getClass().getField("MODEL_CODE").get(object);
		}else{
			modelCode = SESECDEcdCommom.MODEL_CODE;
		}
		// 如果是辅模型，判断视图关联的模型是否为辅模型
		if (!StringUtils.isBlank(viewCode) && dataGrid == null) {
			Model model = viewServiceFoundation.getAssModelFromView(viewCode);
			if (model != null && !modelCode.equals(model.getCode())) {
				log.info(viewCode + "视图关联模型与本模型不一致");
				return;
			}
		}
		// 获得视图所有Layer类型的field
		List<Field> fields = new ArrayList<Field>();
		if (dataGrid == null) {
			fields = fieldService.getLayerFields(viewCode);
		} else {
			// datagrid的field
			fields = fieldService.getFieldsByDataGridCode(dataGrid.getCode());
		}
		Long id = 0L;
		String points = "";
		String properyName = "";
		for (Field field : fields) {
			// 获得layer类型的字段内容
			if (field.getLayerType() == null || !DbColumnType.LAYER.equals(field.getColumnType())) {
				continue;
			}
			if (field.getKey() != null && field.getKey().split("\\.").length <= 2) {
				properyName = field.getKey().split("\\.").length == 1 ? field.getKey() : field.getKey().split("\\.")[1];
			}
			if (StringUtils.isBlank(properyName)) {
				continue;
			}
			try {

				points = (String) object.getClass().getMethod("get" + fLTU(properyName)).invoke(object);
				id = (Long) object.getClass().getMethod("getId").invoke(object);
				log.info("********获取坐标点信息:"+ points +"********");
			} catch (Exception e) {
				log.error(properyName + "通过反射获取坐标点信息或id出错：", e);
				continue;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", modelCode + "_" + id.toString());
			paramMap.put("pointInfos", points);
			log.info("****************");
			log.info(JSONPlainSerializer.serializeAsJSON(paramMap, "*"));
			Map<String, Object> resultMap = (Map<String, Object>)sESECDMapCenterClient.insertMutilGeometry(paramMap);
			if(null != resultMap) {
			Map<String, Object> dataMap = (Map<String, Object>)resultMap.get("data");
			Boolean successBoolean = Boolean.parseBoolean(dataMap.get("success").toString());
			if(!successBoolean) {
				throw new BAPException(dataMap.get("errMsg").toString());
			}
		}
		}
	}

	private boolean getMapServiceEnable() {
        Object flagString =RedisUtil.get("msModuleRelationService.enable");
        // 验证地图组件是否启用，若不启用，直接return
        if(null != flagString && "true".equals(flagString.toString().toLowerCase())) {
            return true;
        }
	    return false;
	}

	/**
	 * 删除图层空间数据
	 * @param ids
	 * @return
	 */
	public void delLayerInfo(String[] ids, Model targetModel, DataGrid dataGrid) throws Exception{
		String modelCode = targetModel == null ? SESECDEcdCommom.MODEL_CODE : targetModel.getCode();
		String tableName = targetModel == null ? SESECDEcdCommom.TABLE_NAME : targetModel.getTableName();
		//  获得该模型所有Layer类型的property
		List<Field> fields = new ArrayList<Field>();
		if (dataGrid == null) {
			fields = fieldService.getLayerFieldsByModel(SESECDEcdCommom.MODEL_CODE);
		} else {
			// 辅模型
			fields = fieldService.getFieldsByDataGridCode(dataGrid.getCode());
		}
		List<SystemCode> layerTypes = new ArrayList<SystemCode>();
		String columnNames = "id,";
		for (Field field : fields) {
			// 获得layer类型的字段内容
			if (field.getLayerType() == null) {
				continue;
			}
			layerTypes.add(field.getLayerType());
			Property property = fieldService.getPropertyByField(field.getCode());
			if (StringUtil.isNotBlank(property.getColumnName()) && !columnNames.contains(property.getColumnName() + ",") && modelCode.equals(modelServiceFoundation.findModelFromProperty(property.getCode()))) {
				columnNames += property.getColumnName() + ",";
			}
		}
		columnNames = columnNames.substring(0, columnNames.length() - 1);
		if(columnNames.indexOf(",") < 0){
			return;
		}
		List<String> idList = new ArrayList<String>();
		for(String idVersion : ids) {
			String id = idVersion.split("@")[0];
			if (!StringUtils.isBlank(id)) {
				idList.add(id);
			}
		}
		Query query = ecdCommomDao.createNativeQuery("select " + columnNames + " from " + tableName + " where id in (:ids)");
		query.setParameterList("ids", idList);
		List<Object[]> results = query.list();
		List<Map<String, Object>> delParms = new ArrayList<Map<String, Object>>();
		Map<String, List<String>> tempParams = new HashMap<String, List<String>> ();
		Map<String, Object> pointsInfoMap = new HashMap<String, Object>();
		for (Object[] result : results) {
			String modelId = modelCode + "_" + result[0].toString();
			for (int i = 1; i < result.length; i ++) {
				if (result[i] != null && !StringUtils.isBlank(result[i].toString())) {
					if (result[i] instanceof java.sql.Clob) {
						log.info("****************");
						log.info(ClobToString((Clob)result[i]));
						pointsInfoMap = (Map<String, Object>) JSONUtil.generateMapFromJson(ClobToString((Clob)result[i]));
					}else {
						log.info("****************");
						log.info(result[i].toString());
						pointsInfoMap = (Map<String, Object>) JSONUtil.generateMapFromJson(result[i].toString());
					}
				}
				if (tempParams.get((String) pointsInfoMap.get("layerCode")) != null) {
					if (tempParams.get((String) pointsInfoMap.get("layerCode")).contains(modelId)) {
						continue;
					}
					tempParams.get((String) pointsInfoMap.get("layerCode")).add(modelId);
					continue;
				}
				List<String> modelIds = new ArrayList<String>();
				modelIds.add(modelId);
				tempParams.put((String) pointsInfoMap.get("layerCode"), modelIds);
			}
		}
		for(Map.Entry<String, List<String>> entry : tempParams.entrySet()){
			Map<String, Object> layerCodeMap = new HashMap<String, Object>();
			layerCodeMap.put("layerCode", entry.getKey());
			layerCodeMap.put("ids", entry.getValue());
			delParms.add(layerCodeMap);
		}
		Map<String, Object> resultMap = (Map<String, Object>) sESECDMapCenterClient.deleteFeature(delParms);
		if(null != resultMap) {
			Map<String, Object> dataMap = (Map<String, Object>)resultMap.get("data");
			Boolean successBoolean = Boolean.parseBoolean(dataMap.get("success").toString());
			if(!successBoolean) {
				throw new BAPException(dataMap.get("errMsg").toString());
			}
		}
	}

    @Override
    public List<SESECDEcdCommom> findSESECDEcdCommomByIds(List<Long> ids) {
        String hql = "from SESECDEcdCommom where id in(:ids)";
        List<SESECDEcdCommom> result = new ArrayList<>();
        List<SESECDEcdCommom> resultTmp = new ArrayList<>();
        //解决数据库in超长的问题
        Integer max = 990;
        Integer limit = (ids.size() + max - 1) / max;
        List<List<Long>> limitIds = new ArrayList<List<Long>>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            limitIds.add(ids.stream().skip(i * max).limit(max).collect(Collectors.toList()));
        });
        limitIds.forEach(limitId -> {
            if(!limitId.isEmpty()){
                resultTmp.addAll(ecdCommomDao.createQuery(hql).setParameterList("ids", limitId).list());
            }
        });
        // 为了和原来顺序保持一致，select in无法保障顺序
        Map<Long, SESECDEcdCommom> map = new HashMap(result.size());
        resultTmp.forEach(obj -> {
            map.put(obj.getId(), obj);
        });
        for (Long id : ids) {
           result.add(map.get(id));
        }
        return result;
    }

    @Override
    public Map<String,SESECDEcdCommom> findEcdCommomByBussinessKeys(String bussinessKeys){
        String hql = "from SESECDEcdCommom where id in(:businessKeys) and valid = 1";
        List<SESECDEcdCommom> result = new ArrayList<>();
        Map<String,SESECDEcdCommom> map = new HashMap<>();
		if(null != bussinessKeys && bussinessKeys.trim().length() > 0){
			List<Long> bussinessKeyList = new ArrayList<Long>();
			for(String key : bussinessKeys.split(",")){
			    if(!StringUtils.isEmpty(key)){
			        bussinessKeyList.add(Long.valueOf(key));
			    }
			}
			List<List<Long>> limitBussinessKeys = new ArrayList<List<Long>>();
            Integer max = 990;
            Integer limit = (bussinessKeyList.size() + max - 1) / max;
			Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
				limitBussinessKeys.add(bussinessKeyList.stream().skip(i * max).limit(max).collect(Collectors.toList()));
			});
            limitBussinessKeys.forEach(limitBussinessKey -> {
				if(!limitBussinessKey.isEmpty()){
                    result.addAll(ecdCommomDao.createQuery(hql).setParameterList("businessKeys", limitBussinessKey).list());
                }
            });
            for(SESECDEcdCommom r:result){
				map.put(r.getId().toString(),r);
			}
		}
		return  map;
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void generateHtmlFile(String url, String projViewRelevantPath) throws Exception {
    	String projViewPath = greendillProjectStaticPath + projViewRelevantPath;
    	String sql = "select html from app_project_static where url = ?1 and tenant_id = ?2";
    	Object html = ecdCommomDao.createNativeQuery(sql, url, RpcContext.getContext().getTenantId()).uniqueResult();
    	if (null != html) {
    		String htmlStr = null;
    		if (html instanceof Clob) {
				htmlStr = ClobUtils.clob2String((Clob) html);
			} else if (html instanceof String) {
				htmlStr = (String) html;
			}
			FileUtil.makeDirectory(projViewPath);
	    	File file = new File(projViewPath);
	    	FileUtil.writerContentToFile(file.getCanonicalPath(), htmlStr);
	    	viewServiceFoundation.addProjectHtml(url, RpcContext.getContext().getTenantId());
		}
    }



	public void beforeSaveEcdCommom(SESECDEcdCommom ecdCommom, Object... objects){
		checkUniqueConstraint(ecdCommom);
	/* CUSTOM CODE START(serviceimpl,beforeSave,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void afterSaveEcdCommom(SESECDEcdCommom ecdCommom, Object... objects){
	/* CUSTOM CODE START(serviceimpl,afterSave,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void beforeDeleteEcdCommom(SESECDEcdCommom ecdCommom){
	/* CUSTOM CODE START(serviceimpl,beforeDelete,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void afterDeleteEcdCommom(SESECDEcdCommom ecdCommom){
	/* CUSTOM CODE START(serviceimpl,afterDelete,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void beforeRestoreEcdCommom(SESECDEcdCommom ecdCommom){
	/* CUSTOM CODE START(serviceimpl,beforeRestore,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void afterRestoreEcdCommom(SESECDEcdCommom ecdCommom){
	/* CUSTOM CODE START(serviceimpl,afterRestore,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void customGenerateCodes(SESECDEcdCommom ecdCommom, Object... objects) {
		/* CUSTOM CODE START(serviceimpl,customGenerateCodes,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	public void customGenerateSummarys(SESECDEcdCommom ecdCommom, Object... objects) {
		/* CUSTOM CODE START(serviceimpl,customGenerateSummarys,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void beforeServiceDestroy() {
	/* CUSTOM CODE START(serviceimpl,beforeServiceDestroy,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}

	public void beforeInitBean(){
		/* CUSTOM CODE START(serviceimpl,beforeInitBean,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	public void afterInitBean(){
		/* CUSTOM CODE START(serviceimpl,afterInitBean,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	@Override
	public void beforeImportEcdCommom(List<SESECDEcdCommom> insertObjs, List<SESECDEcdCommom> updateObjs,
			List<Map<String,String>> columnInfo,List<Map<String, Map<Integer, Map<Integer, String>>>> errMsgSheet, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo){//主辅模型导入before方法
		/* CUSTOM CODE START(serviceimpl,beforeImportEcdCommom,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	@Override
	public void afterImportEcdCommom(List<SESECDEcdCommom> insertObjs, List<SESECDEcdCommom> updateObjs,
			List<Map<String,String>> columnInfo, Map<String ,List<Map<String, Object>>> importNodeInfo, Map<String, Property> importPropInfo){//主辅模型导入after方法
		/* CUSTOM CODE START(serviceimpl,afterImportEcdCommom,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
	}
	/* CUSTOM CODE START(serviceimpl,functions,SESECD_1.0.0_ecdPanel_EcdCommom,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}
