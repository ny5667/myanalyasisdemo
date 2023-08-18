package com.supcon.orchid.SESECD.services.notify;

import com.supcon.orchid.SESECD.services.notify.request.CommonEmailRequest;
import com.supcon.orchid.SESECD.services.notify.request.CommonSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义顶层发送方式  实现由实现类按需实现
 * <p>
 *    顶层定义功能，三方实现，每一种实现方式携带一个参数配置方法
 *    ex：若a现场使用三方实现，且只需要发送短信，则实现sendEmail与emailConfigInit  配置时配置只发送方短信即可
 *    若出现三方有其他通知方式，如微信小程序等，则在此扩展通知类型
 * </p>
 */
public interface NotifyWay {
    Logger logger = LoggerFactory.getLogger(NotifyWay.class);

    default String sendEmail(CommonEmailRequest emailRequest) {
        logger.error("默认顶层邮件通知方式");
        throw new UnsupportedOperationException("no apply this operation");
    }

    default String sendSMS(CommonSmsRequest smsRequest) {
        logger.error("默认顶层短信通知方式");
        throw new UnsupportedOperationException("no apply this operation");
    }

    default void emailConfigInit() {
        logger.error("默认顶层邮件配置初始化");
        throw new UnsupportedOperationException("no apply this operation");
    }

    default void smsConfigInit() {
        logger.error("默认顶层短信配置初始化");
        throw new UnsupportedOperationException("no apply this operation");
    }

}
