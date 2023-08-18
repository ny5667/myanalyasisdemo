package com.supcon.orchid.SESECD.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     Spring容器
 * </p>
 * @author lufengdong
 * @create 2022-09-19 13:13
 */
@Slf4j
@Configuration
public class SpringApplicationContext implements ApplicationContextAware, DisposableBean {
    public static ApplicationContext APPLICATION_CONTEXT = null;

    public SpringApplicationContext() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContext.APPLICATION_CONTEXT = applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) APPLICATION_CONTEXT.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return APPLICATION_CONTEXT.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return APPLICATION_CONTEXT.getBean(requiredType);
    }

    private static void assertContextInjected() {
        if (APPLICATION_CONTEXT == null) {
            throw new IllegalStateException("applicationContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder.");
        }
    }

    public static String getActiveProfile() {
        assertContextInjected();
        return APPLICATION_CONTEXT.getEnvironment().getActiveProfiles()[0];
    }

    public static void clearHolder() {
        APPLICATION_CONTEXT = null;
    }

    public static boolean containsBean(String name) {
        return APPLICATION_CONTEXT.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return APPLICATION_CONTEXT.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return APPLICATION_CONTEXT.getType(name);
    }

    @Override
    public void destroy() throws Exception {
        clearHolder();
    }
}