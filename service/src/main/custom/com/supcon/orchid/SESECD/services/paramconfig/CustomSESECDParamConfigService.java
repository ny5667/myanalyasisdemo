package com.supcon.orchid.SESECD.services.paramconfig;

import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;

import java.util.List;
import java.util.Map;

/**
 * 参数配置服务类
 */
public interface CustomSESECDParamConfigService {

    /**
     * 判断是否启用三方通知实现 有则返回实现
     *
     * @return
     */
    String enableThirdNotifyAndGet();

    /**
     * 通过编码获取该配置项
     * @param configCode 配置编码
     * @return  配置项
     */
    SESECDEcdParamConfig getConfigByConfigCode(String configCode);

    /**
     * 根据主项获取子项
     *
     * @param paramConfig 主配置项
     * @return 每一个子项 k v 形式
     */
    Map<String, String> getParameterOptions(SESECDEcdParamConfig paramConfig);


    /**
     * 根据主项编码获取子项
     * @param configCode 主配置项编码
     * @return 每一个子项 k v 形式
     */
    Map<String, String> getParameterOptions(String configCode);

    /**
     * 根据主项获取子项
     *
     * @param paramConfig 主配置项
     * @return 每一个子项 k v 形式
     */
    List<SESECDParamOption> getParameterOptionList(SESECDEcdParamConfig paramConfig);
}
