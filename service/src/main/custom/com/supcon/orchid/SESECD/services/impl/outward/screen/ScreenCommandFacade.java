package com.supcon.orchid.SESECD.services.impl.outward.screen;

import com.supcon.orchid.SESECD.config.SpringApplicationContext;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.constant.enums.EnableEnum;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.services.outward.screen.CustomSESECDScreenCommandService;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.utils.SocketInvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 大屏指令服务
 */
@Component
@Slf4j
public class ScreenCommandFacade {

    @Autowired
    private CustomSESECDParamConfigService paramConfigService;

    /**
     * 打开大屏
     */
    public void on() {
        String beanName = checkScreenConfigAndGetBeanName();
        if (StringUtils.isEmpty(beanName)) {
            return;
        }
        CustomSESECDScreenCommandService commandService = SpringApplicationContext.getBean(beanName);
        commandService.on();
    }

    /**
     * 退出大屏
     */
    public void off() {
        String beanName = checkScreenConfigAndGetBeanName();
        if (StringUtils.isEmpty(beanName)) {
            return;
        }
        CustomSESECDScreenCommandService commandService = SpringApplicationContext.getBean(beanName);
        commandService.off();
    }

    /**
     * 检查发送大屏指令配置
     *
     * @return
     */
    private String checkScreenConfigAndGetBeanName() {
        SESECDEcdParamConfig code = paramConfigService.getConfigByConfigCode(ConstDict.SCREEN_COMMAND);
        if (null == code || StringUtils.isEmpty(code.getConfigValue())) {
            log.error("配置表无此大屏指令配置项，配置编码");
            return null;
        }
        Map<String, String> parameterOptions = paramConfigService.getParameterOptions(code);
        //配置但未启动
        if (parameterOptions.getOrDefault(ConstDict.SCREEN_COMMAND_ENABLE, "0").equalsIgnoreCase(EnableEnum.disable.getCode())) {
            log.error("配置但未启动该配置项");
            return null;
        }
        return code.getConfigValue();
    }

}
