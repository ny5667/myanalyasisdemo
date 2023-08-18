package com.supcon.orchid.SESECD.constant.consist;

/**
 * @author ludunyue
 * @date 2020/4/18
 */
public class ConstDict {
    /**
     * 默认公司
     */
    public static final String DEFAULT_ORG_COMPANY = "default_org_company";
    /**
     * 应急预案 服务编码
     */
    public static final String MODULE_CODE_EP = "SESWssEP";

    /**
     * webSocket的Group值
     */
    public static final String ECD_WEB_SOCKET = "ecdWsMsg";

    /**
     * 应急行动
     */
    public static final String MSG_TYPE_EMC_ACTION = "ReceiveEmcAction";

    /**
     * 应急态势
     */
    public static final String MSG_TYPE_EMC_SITUATION = "ReceiveEmcSituation";

    /**
     * 指令下达
     */
    public static final String MSG_TYPE_ALARM_ACTION = "AlarmActionNotice";

    /**
     * 事件生成
     */
    public static final String MSG_TYPE_EMERGENCY_EVENT_CREATE = "EmergencyEventCreate";

    /**
     * 事件关闭
     */
    public static final String MSG_TYPE_EMERGENCY_EVENT_END = "EmergencyEventEnd";

    /**
     * 截图发送
     */
    public static final String SCREENSHOT = "Screenshot";
    //------------------------------------------参数配置：消息通知模板配置项 弱关联，手动维护------------------------------------------//
    public static final String MSG_TEMPLATE = "MSG_TEMPLATE";

    //------------------------------------------参数配置：三方通知实现类项 弱关联，手动维护------------------------------------------//
    public static final String NOTIFY_THIRD = "NOTIFY";
    //------------------------------------------参数配置：三方短信配置项 弱关联，手动维护------------------------------------------//
    public static final String SMS_THIRD = "SMS_THIRD";
    //短信请求地址
    public static final String SMS_THIRD_URL = "URL";
    public static final String SMS_THIRD_ENTERPRISE_NAME = "ENTERPRISE_NAME";
    public static final String SMS_THIRD_APP_ID = "APP_ID";
    public static final String SMS_THIRD_SECRET_KEY = "SECRET_KEY";
    public static final String SMS_THIRD_SIGN = "SIGN";
    public static final String SMS_THIRD_ADD_SERIAL = "ADD_SERIAL";
    public static final String SMS_THIRD_MAX_ALLOW_MOBILES = "MAX_ALLOW_MOBILES";
    public static final String SMS_THIRD_ENABLE = "ENABLE";

    //------------------------------------------参数配置：三方邮件配置项 弱关联，手动维护------------------------------------------//
    public static final String EMAIL_THIRD = "EMAIL_THIRD";
    //短信请求地址
    public static final String EMAIL_THIRD_REQUEST_URL = "REQUEST_URL";
    public static final String EMAIL_THIRD_ENABLE = "ENABLE";

    //------------------------------------------参数配置：三方大屏指令配置项 弱关联，手动维护------------------------------------------//
    public static final String SCREEN_COMMAND = "SCREEN_COMMAND";
    public static final String SCREEN_COMMAND_IP = "IP";
    public static final String SCREEN_COMMAND_PORT = "PORT";
    public static final String SCREEN_COMMAND_ON = "ON";
    public static final String SCREEN_COMMAND_OFF = "OFF";
    public static final String SCREEN_COMMAND_ENABLE = "ENABLE";
    public static final String SCREEN_COMMAND_PLAN_NAME = "PLAN_NAME";



    //--------------------------------------------融合通信--------------------------------------------------------------//

    /**
     * 融合通信 应用消息（站内信）主题编号
     */
    public static final String MSG_TYPE_VXECD_APPLICATION_MESSAGE = "VxECDApplicationMessage";

    //--------------------------------------------报警中心--------------------------------------------------------------//

    /**
     * 历史报警记录最近一天数据
     */
    public static final String SQL_AM_HISTORY = "SELECT ah.ALARMID alarmId, ah.POLICYID policyId, RECORD_ID recordId, (SELECT ALARM_NAME from AM_POINTS ap where id = ALARMID) alarmName, ah.DURATION_SECOND durationSecond, ah.RELATION_TAGS relationTags, (SELECT display_name from sys_code sc where CONCAT(CONCAT(entity_code,'/'),code) = ah.ALARM_STATE AND valid = 1) alarmState, ah.ALARM_TIME alarmTime, ah.LIFE_TIME lifeTime, ah.DURATION_DAYS durationDays, ah.DURATION_TIME durationTime, (select typename from AM_TYPES ats where ats.id = ah.ALARM_TYPE ) alarmType, (select levelname from AM_RANKS ar where ar.id = ah.ALARM_LEVEL) alarmLevel, ah.ALARM_VALUE alarmValue, ah.DESCRIPTION description, (select display_name from sys_code sc where CONCAT(CONCAT(entity_code,'/'),code) = ah.ENDTYPE AND valid = 1) endType, ah.END_VALUE endValue, ah.LIFE_VALUE lifeValue, (select ao.APPNAME from AM_ORIGINS ao where ao.id = ah.ALARM_ORIGIN) amOrigin,ah.LIMIT_VALUE limitValue from AM_HISTORIES ah left join AM_ORIGINS aon on aon.id = ah.ALARM_ORIGIN where aon.APPCODE = '015' and ah.ALARM_TIME > :alarmTime and ah.cid = :cid";

    /**
     * 报警策略查询
     */
    public static final String SQL_AM_POLICY = "select ap.id id, ap.ALARMID alarmId, ap.HV hv, ap.HHV hhv, ap.LV lv, ap.LLV llv,(SELECT name FROM BASESET_UNITS WHERE id = ap.UNIT_ID) unitName from AM_POLICIES ap WHERE ap.ALARMID in (:alarmIds)";

    //--------------------------------------------地图配置中心--------------------------------------------------------------//

    /**
     * 地图图标库查询SQL
     */
    public static final String SQL_ICON_LIBRARY = "SELECT il.ID id,il.NAME name,il.ICON_URL url,il.ICON_TYPE typeId,(SELECT name FROM SESGIS_LIBRARY_TYPES WHERE id = il.ICON_TYPE) typeName,il.CID cid FROM SESGIS_ICON_LIBRARIES il WHERE il.valid = 1";

    //--------------------------------------------地图图层编码--------------------------------------------------------------//

    /**
     * 接警图层
     */
    public static final String warningLayer = "warningLayer";

    /**
     * 应急事件
     */
    public static final String incidentLayer = "incidentLayer";

}
