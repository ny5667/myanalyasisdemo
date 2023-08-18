package com.supcon.orchid.SESECD.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义本地缓存注解
 * 基于本地内存，用于提高响应较慢接口，请勿滥用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(20)
public @interface LocalCache {
    /**
     * 缓存有效期  单位：s 默认60s
     * @return
     */
    long period() default 60;

    /**
     * 前缀
     * @return
     */
    String prefix() default "";
}
