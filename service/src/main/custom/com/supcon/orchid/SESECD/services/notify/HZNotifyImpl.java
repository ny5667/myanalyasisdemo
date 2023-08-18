package com.supcon.orchid.SESECD.services.notify;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.constant.enums.NotifyWayEnum;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.services.notify.request.CommonEmailRequest;
import com.supcon.orchid.SESECD.services.notify.request.CommonSmsRequest;
import com.supcon.orchid.SESECD.services.notify.request.ThirdEmailReceiverRequest;
import com.supcon.orchid.SESECD.utils.*;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.services.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 三方通知实现-惠州
 */
@Service("HZNotify")
public class HZNotifyImpl extends BaseServiceImpl implements NotifyStrategy, NotifyWay {


    @Autowired
    private CustomSESECDParamConfigService paramConfigService;

    /**
     * 配置方法执行标志
     * <p>
     * 鉴于之前的经验，在spring生命周期期间进行读取表操作，在新环境平台在spring启动完成后可能还没有创建好表，此时会报错启动失败
     * 所以放在代码中第一次调用时执行
     * </p>
     */
    private static boolean initFlag = false;

    //*****************************************************短信配置参数*******************************************************//
    //接口请求地址
    private static String URL;
    /**
     * 企业名称
     */
    private static String ENTERPRISE_NAME;
    /**
     * 接口账号用户名。
     */
    private static String APP_ID;
    /**
     * 接口账号密码
     */
    private static String SECRET_KEY;
    /**
     * 签名编码。在云MAS平台『管理』→『接口管理』→『短信接入用户管理』获取。。
     * 【惠州石化】
     */
    private static String SIGN;
    /**
     * 扩展码。依据申请开户的服务代码匹配类型而定，
     * 如为精确匹配，此项填写空字符串（""）；
     * 如为模糊匹配，此项可填写空字符串或自定义的扩展码，
     * 注：服务代码加扩展码总长度不能超过20位。
     */
    private static String ADD_SERIAL = "";
    /**
     * 单次允许发送的最大短信数量。
     */
    private static int MAX_ALLOW_MOBILES = 5000;

    private static boolean intiFlag = false;
    //*****************************************************邮件配置参数*******************************************************//
    private static String REQUEST_URL;


    @Autowired
    private NotifyTemplate notifyTemplate;


    @Override
    public String notify(MsgModelDTO msgModelDTO) {
        construct();

        //三方实现直接读取模板并解析
        String msgType = msgModelDTO.getMsgType();
        //通知内容
        String msg = null;
        //邮件标题
        String title = null;
        //使用平台模板内容 否则使用指定原内容
        if (StringUtils.isNotEmpty(msgType)) {
            String text = notifyTemplate.getTemplateTextByMsgType(msgType);
            msg = notifyTemplate.parseTemplate(text, msgModelDTO.getParam());
        }else {
            if (msgModelDTO.getExtraParams() != null) {
                msg = (String) msgModelDTO.getExtraParams().getOrDefault("content", null);
                title = (String) msgModelDTO.getExtraParams().getOrDefault("title", null);
            }
        }
        if (StringUtils.isEmpty(msg)){
            log.error("HZNotifyImpl=====notify=====： 无法根据消息主题找到消息模板 或 指定发送内容为空 msgType： " + msgType + " content: " + msg);
            return StringUtils.EMPTY;
        }
        //获取发送方式
        List<String> notifyWay = notifyTemplate.getNotifyWay(msgType);
        List<Staff> staffInfos = notifyTemplate.getStaffInfos(msgModelDTO.getReceivers());
        for (String way : notifyWay) {
            //发送短信
            if (way.equalsIgnoreCase(NotifyWayEnum.SMS.getType())) {
                CommonSmsRequest smsRequest = new CommonSmsRequest();
                //接收人电话
                List<String> mobiles = staffInfos.stream()
                        .filter(staff -> StringUtils.isNotBlank(staff.getMobile()))
                        .map(Staff::getMobile).distinct().collect(Collectors.toList());
                if (org.apache.commons.collections4.CollectionUtils.isEmpty(mobiles)) {
                    log.error("待发送手机号码集合为空.");
                    return StringUtils.EMPTY;
                }
                smsRequest.setMobileList(mobiles);
                smsRequest.setContent(msg);
                return sendSMS(smsRequest);
            }
            //发送邮件
            else if (way.equalsIgnoreCase(NotifyWayEnum.EMAIL.getType())) {
                CommonEmailRequest commonEmailRequest = new CommonEmailRequest();
                //接收人邮箱
                List<ThirdEmailReceiverRequest> collect = staffInfos.stream()
                        .filter(staff -> StringUtils.isNotBlank(staff.getName()) && StringUtils.isNotBlank(staff.getEmail()))
                        .map(staff -> {
                            ThirdEmailReceiverRequest receiver = new ThirdEmailReceiverRequest();
                            receiver.setReceiver(staff.getName());
                            receiver.setAddress(staff.getEmail());
                            return receiver;
                        }).collect(Collectors.toList());
                if (org.apache.commons.collections4.CollectionUtils.isEmpty(collect)) {
                    log.error("待发送邮件信息集合为空.");
                    return StringUtils.EMPTY;
                }
                commonEmailRequest.setTitle(title);
                commonEmailRequest.setContent(msg);
                commonEmailRequest.setToList(collect);
                commonEmailRequest.setCcList(null);
                return sendEmail(commonEmailRequest);
            }
        }

        return null;
    }

    private void construct() {
        //初始化配置 只需要初始化一遍
        if (!initFlag) {
            //初始化配置
            emailConfigInit();
            smsConfigInit();
            initFlag = true;
        }
    }


    @Override
    public String sendEmail(CommonEmailRequest request) {
        //初始化配置 只需要初始化一遍
        construct();

        if (CollectionUtils.isEmpty(request.getToList())) {
            log.error("未获取收件人信息.");
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("title", request.getTitle());
        body.add("body", request.getContent());
        body.add("timex", DateUtils.localDateTime2Str(LocalDateTime.now()));
        body.add("task-to", request.getToList().get(0).getReceiver());
        body.add("Eurl", request.getToList().get(0).getAddress());
        body.add("priority", 1);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        String result = null;
        try {
            result = HttpInvokeUtils.invoke(REQUEST_URL, HttpMethod.POST, httpEntity, String.class);
            System.err.println("邮件接口响应值值：" + result);
        } catch (Exception var) {
            log.error("请求调用第三方邮件接口失败, 原因：{0}", var);
        }
        return JsonHelper.writeValue(result);
    }

    @Override
    public String sendSMS(CommonSmsRequest smsRequest) {
        //初始化配置 只需要初始化一遍
        construct();
        List<String> mobileList = smsRequest.getMobileList().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(mobileList)) {
            log.error("待发送短信的手机号码为空.");
            return StringUtils.EMPTY;
        }
        if (mobileList.size() > MAX_ALLOW_MOBILES) {
            log.error("待发送短信的手机号码数量[{}]超过最大限制5000条.", mobileList.size());
            return StringUtils.EMPTY;
        }
        String mobiles = StringUtils.join(mobileList, ",");
        Map<String, Object> variables = new HashMap<>();
        variables.put("ecName", ENTERPRISE_NAME);
        variables.put("apId", APP_ID);
        variables.put("mobiles", mobiles);
        variables.put("content", smsRequest.getContent());
        variables.put("sign", SIGN);
        variables.put("secretKey", SECRET_KEY);
        variables.put("addSerial", ADD_SERIAL);
        variables.put("mac", this.getMd5EncryptMac(mobiles, smsRequest.getContent()));
        String encryptBase64 = Base64.encodeBase64String(JSON.toJSONString(variables).getBytes());
        HttpEntity<String> body = new HttpEntity<>(encryptBase64);
        try {
            return JsonHelper.writeValue(HttpInvokeUtils.invoke(URL, HttpMethod.POST, body, String.class));
        } catch (Exception ex) {
            log.error("请求发送短信接口失败：{0}.", ex);
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取参数校验序列
     * <p>
     * 生成方法：将ecName、apId、secretKey、mobiles、content、sign、addSerial按序拼接（无间隔符），
     * 通过MD5（32位小写）计算得出值。
     * </p>
     *
     * @param mobiles 待发送手机号码
     * @param content 待发送短信内容
     * @return 参数校验序列
     */
    private String getMd5EncryptMac(String mobiles, String content) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ENTERPRISE_NAME);
        buffer.append(APP_ID);
        buffer.append(SECRET_KEY);
        buffer.append(mobiles);
        buffer.append(content);
        buffer.append(SIGN);
        buffer.append(ADD_SERIAL);
        return Md5Utils.encrypt(buffer.toString(), Md5Utils.RESPONSE_LENGTH_32, Md5Utils.RESPONSE_TYPE_LOWER);
    }


    /**
     * 邮件配置参数
     */
    @Override
    public void emailConfigInit() {
        SESECDEcdParamConfig emailConfigParam = paramConfigService.getConfigByConfigCode(ConstDict.EMAIL_THIRD);
        if (null == emailConfigParam) {
            log.error("没有配置三方邮件");
            //没有配置三方邮件
            return;
        }
        Map<String, String> parameterOptions = paramConfigService.getParameterOptions(emailConfigParam);
        for (Map.Entry<String, String> entry : parameterOptions.entrySet()) {
            String optionCode = entry.getKey();
            if (optionCode.equalsIgnoreCase(ConstDict.EMAIL_THIRD_REQUEST_URL)) {
                REQUEST_URL = entry.getValue();
                break;
            }
        }
    }

    /**
     * 短信配置参数
     */
    @Override
    public void smsConfigInit() {
        SESECDEcdParamConfig configParam = paramConfigService.getConfigByConfigCode(ConstDict.SMS_THIRD);
        if (null == configParam) {
            //没有配置该三方参数配置
            log.error("没有配置三方短信");
            return;
        }
        Map<String, String> parameterOptions = paramConfigService.getParameterOptions(configParam);
        for (Map.Entry<String, String> entry : parameterOptions.entrySet()) {
            String optionCode = entry.getKey();
            switch (optionCode) {
                case ConstDict.SMS_THIRD_URL:
                    URL = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_ENTERPRISE_NAME:
                    ENTERPRISE_NAME = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_APP_ID:
                    APP_ID = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_SECRET_KEY:
                    SECRET_KEY = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_SIGN:
                    SIGN = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_ADD_SERIAL:
                    ADD_SERIAL = entry.getValue();
                    break;
                case ConstDict.SMS_THIRD_MAX_ALLOW_MOBILES:
                    MAX_ALLOW_MOBILES = Integer.parseInt(entry.getValue());
                    break;
            }
        }
    }
}
