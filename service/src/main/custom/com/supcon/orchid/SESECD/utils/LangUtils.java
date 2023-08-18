package com.supcon.orchid.SESECD.utils;


import com.supcon.orchid.SESECD.services.impl.action.CustomSESECDActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 当前语言环境
 */
@Component
public class LangUtils {

    /**
     * 任选一serviceImpl均可
     */
    @Autowired
    private CustomSESECDActionServiceImpl sesecdActionService;
    private static CustomSESECDActionServiceImpl staticCustomVxSECDActionService = null;

    @PostConstruct
    public void init() {
        staticCustomVxSECDActionService = sesecdActionService;
    }

    public static String getCurrLang() {
        return staticCustomVxSECDActionService.getCurrentLanguage();
    }
}
