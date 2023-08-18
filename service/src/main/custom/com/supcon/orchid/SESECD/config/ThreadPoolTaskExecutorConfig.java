package com.supcon.orchid.SESECD.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * <p>
 * 线程池
 * </p>
 *
 * @author lufengdong
 * @create 2023-05-06 15:36
 */
@EnableAsync
@Slf4j
@Configuration
public class ThreadPoolTaskExecutorConfig  {
    // 核心线程数 66

    // 最大线程数
    private static  int maxPoolSize = 50;
    // 允许线程空闲时间（单位：默认为秒）
    private static final int keepAliveTime = 300;
    // 缓冲队列数
    private static final int queueCapacity = 1000;
    // 线程名前缀
    private static final String threadNamePrefix = "SESECD-Thread-";

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        log.info("Initializing thread pool after application ready...");
        int corePoolSize;
        corePoolSize = Runtime.getRuntime().availableProcessors() + 2;
        maxPoolSize = corePoolSize  + 10;
        log.error("核心线程 ->" + corePoolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
