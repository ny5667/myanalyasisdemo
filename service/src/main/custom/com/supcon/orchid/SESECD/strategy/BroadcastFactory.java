package com.supcon.orchid.SESECD.strategy;

import com.supcon.orchid.SESECD.strategy.AbstratcStrategy.BroadcastStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BroadcastFactory implements ApplicationContextAware {

    private static Map<String, BroadcastStrategy> serviceMap = new HashMap<>();

    /**
     * 获取策略对象
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, BroadcastStrategy> beans = applicationContext.getBeansOfType(BroadcastStrategy.class);
        if (!CollectionUtils.isEmpty(beans)){
            beans.forEach((k,v) -> serviceMap.put(v.getType(),v));
        }
    }

    public static BroadcastStrategy createStrategy( String companyType){
        log.error("根据公司类型获取执行策略，策略类型：",companyType);
        return serviceMap.get(companyType);
    }

}
