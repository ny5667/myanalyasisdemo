package com.supcon.orchid.SESECD.aspect;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import java.util.Arrays;

/**
 * 接口耗时切面类
 */
@Aspect
@EnableAspectJAutoProxy
@Component
@Slf4j
public class StopWatchAdvice {

    @Around("@annotation(com.supcon.orchid.SESECD.annotation.StopWatchTime)")
    public Object stopWatchTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.error("stopWatchTime=====接口耗时切面：方法名： " + methodName + " 参数为：" + Arrays.toString(args) + " 开始执行...");

        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        Object result = joinPoint.proceed();
        stopwatch.stop();
        log.error("stopWatchTime=====接口耗时切面：方法名： " + methodName + " 参数为：" + Arrays.toString(args) + "  执行结束，耗时/秒： " + stopwatch.getTotalTimeSeconds());
        return result;
    }
}
