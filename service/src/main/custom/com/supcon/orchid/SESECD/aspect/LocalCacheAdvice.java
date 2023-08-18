package com.supcon.orchid.SESECD.aspect;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.supcon.orchid.SESECD.annotation.LocalCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存切面
 */
@Aspect
@Component
@Slf4j
public class LocalCacheAdvice {
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static Map<String, Object> localCache = new ConcurrentHashMap<>();

    @Around("@annotation(com.supcon.orchid.SESECD.annotation.LocalCache)")
    public Object localCache(ProceedingJoinPoint joinPoint) throws Throwable {
        log.error("localCache====进入本地缓存切面");
        // 反射获取目标类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        String params = Arrays.toString(joinPoint.getArgs());
        // 合成键值
        String key = className + ";;;" + methodName + ";;;" + params;
        // 获取注解的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LocalCache annotation = method.getAnnotation(LocalCache.class);
        // 获取缓存有效期
        long validityPeriod = annotation.period();
        // 获取前缀
        String prefix = annotation.prefix();
        key = prefix + key;

        // 获取缓存的接口返回值
        Object response = localCache.get(key);
        if (response != null) {
            log.error("localCache====获取本地缓存，无需统计耗时，返回");
            return response;
        }
        //执行原方法逻辑
        Object proceed = joinPoint.proceed();
        //设置缓存
        localCache.put(key, proceed);
        //自动过期
        String finalKey = key;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> localCache.remove(finalKey), validityPeriod, TimeUnit.SECONDS);
        return proceed;
    }
}
