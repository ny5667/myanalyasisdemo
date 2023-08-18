package com.supcon.orchid.SESECD.services.impl.paramconfig;

import com.supcon.orchid.SESECD.daos.SESECDEcdParamConfigDao;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.entities.SESECDParamOption;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.constant.enums.EnableEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomSESECDParamConfigServiceImpl implements CustomSESECDParamConfigService {

    @Autowired
    private SESECDEcdParamConfigDao paramConfigDao;

    @Override
    public SESECDEcdParamConfig getConfigByConfigCode(String configCode) {
        String hql = "from " + SESECDEcdParamConfig.JPA_NAME + " where valid = 1 and configCode = ?0";
        List<SESECDEcdParamConfig> paramConfigs = paramConfigDao.findByHql(hql, configCode);
        if (CollectionUtils.isEmpty(paramConfigs)) {
            return null;
        }
        SESECDEcdParamConfig paramConfig = paramConfigs.get(0);
        return paramConfig;
    }

    /**
     * 是否启用三方通知实现
     *
     * @return
     */
    @Override
    public String enableThirdNotifyAndGet() {
        //是否有三方实现
        String hql = "from " + SESECDEcdParamConfig.JPA_NAME + " where valid = 1 and configCode = ?0";
        List<SESECDEcdParamConfig> paramConfigs = paramConfigDao.findByHql(hql, ConstDict.NOTIFY_THIRD);
        if (CollectionUtils.isEmpty(paramConfigs)) {
            return null;
        }
        SESECDEcdParamConfig paramConfig = paramConfigs.get(0);
        if (StringUtils.isEmpty(paramConfig.getConfigValue())) {
            return null;
        }
        //查询配置子项 确定是否启用
        Map<String, String> parameterOptions = getParameterOptions(paramConfig);
        //配置但未启动
        if (parameterOptions.getOrDefault(ConstDict.SMS_THIRD_ENABLE, "0").equalsIgnoreCase(EnableEnum.disable.getCode())) {
            return null;
        }
        return paramConfig.getConfigValue();
    }


    @Override
    public List<SESECDParamOption> getParameterOptionList(SESECDEcdParamConfig paramConfig) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDParamOption.JPA_NAME).append(" where valid = 1 and paramConfig = ?0");
        List<SESECDParamOption> options = paramConfigDao.findByHql(builder.toString(), new Object[]{paramConfig});
        return options;
    }

    @Override
    public Map<String, String> getParameterOptions(String configCode) {
        SESECDEcdParamConfig configByConfigCode = getConfigByConfigCode(configCode);
        Assert.notNull(configByConfigCode,configCode + " can not find config");
        Map<String, String> parameterOptions = getParameterOptions(configByConfigCode);
        return parameterOptions;
    }

    /*---------------------------------------------公共方法-------------------------------------------------*/



    /**
     * 根据主配置项获取该配置子项
     *
     * @param paramConfig
     * @return
     */
    public Map<String, String> getParameterOptions(SESECDEcdParamConfig paramConfig) {
        List<SESECDParamOption> options = getParameterOptionList(paramConfig);
        Map<String, String> map = new HashMap<>();
        for (SESECDParamOption item : options) {
            map.put(item.getOptionCode(), item.getOptionValue());

        }
        return map;
    }

}
