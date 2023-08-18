package com.supcon.orchid.SESECD.controllers.taskdispatch;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 平台任务调度
 */
@Controller
public class CustomSESECDTaskDispatchController {
    static Cache<String, String> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    public static void main(String[] args) {
        cache.policy().expireVariably().ifPresent(e->{
            e.put("aaa", "2", 1, TimeUnit.SECONDS);
            e.put("bbb", "2", 3, TimeUnit.SECONDS);
        });
        String ret = cache.asMap().put("key", "value");
        System.out.println(ret);

    }
}
