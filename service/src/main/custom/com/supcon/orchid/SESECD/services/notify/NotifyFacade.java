package com.supcon.orchid.SESECD.services.notify;

import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.services.notify.request.CommonEmailRequest;
import com.supcon.orchid.SESECD.services.notify.request.CommonSmsRequest;
import com.supcon.orchid.SESECD.config.SpringApplicationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息通知对外提供服务唯一出口
 */
@Component
public class NotifyFacade {

    @Autowired
    private CustomSESECDParamConfigService paramConfigService;

    /**
     * 处理通知服务
     * 实现方与通知方式完全由配置决定  推荐使用方式
     * @param modelDTO
     */
    public String handleNotify(MsgModelDTO modelDTO) {
        //是否开启三方消息配置
        String beanName = paramConfigService.enableThirdNotifyAndGet();
        NotifyStrategy notifyHandle;
        if (StringUtils.isEmpty(beanName)) {
            notifyHandle = SpringApplicationContext.getBean("defaultNotify");
        } else {
            notifyHandle = SpringApplicationContext.getBean(beanName);
        }
        return notifyHandle.notify(modelDTO);
    }

    /**
     * 当调用者知道所以实现返回参数结构且需要该结果时，可使用该办法获取返回值并解析未对象
     * @param modelDTO
     * @param clazz 返回值类型
     * @param <T>
     * @return
     */
    public <T> T handleNotify(MsgModelDTO modelDTO,Class<T> clazz) {
        //是否开启三方消息配置
        String ret = handleNotify(modelDTO);
        return  JSONObject.parseObject(ret, clazz);
    }


    /**
     * 手动指定发短信 仅应用于一张图融合融信发消息 其他地方不推荐
     * @param smsRequest
     * @return
     */
    public String handleSMS(CommonSmsRequest smsRequest) {
        //是否开启三方消息配置
        String beanName = paramConfigService.enableThirdNotifyAndGet();
        NotifyWay notifyHandle;
        if (StringUtils.isEmpty(beanName)) {
            notifyHandle = SpringApplicationContext.getBean("defaultNotify");
        } else {
            notifyHandle = SpringApplicationContext.getBean(beanName);
        }

        return notifyHandle.sendSMS(smsRequest);
    }

    /**
     * 手动指定发email 仅应用于一张图融合融信发邮件 其他地方不推荐
     * @param emailRequest
     * @return
     */
    public String handleEmail(CommonEmailRequest emailRequest) {
        //是否开启三方消息配置
        String beanName = paramConfigService.enableThirdNotifyAndGet();
        NotifyWay notifyHandle;
        if (StringUtils.isEmpty(beanName)) {
            notifyHandle = SpringApplicationContext.getBean("defaultNotify");
        } else {
            notifyHandle = SpringApplicationContext.getBean(beanName);
        }

        return notifyHandle.sendEmail(emailRequest);
    }

}
