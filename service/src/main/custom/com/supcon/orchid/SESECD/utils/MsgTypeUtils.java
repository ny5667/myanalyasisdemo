package com.supcon.orchid.SESECD.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MsgTypeUtils {
    public static final Map<Integer,String> MSG_TYPE = new ConcurrentHashMap();

    static {
        MSG_TYPE.put(1,"短信");
        MSG_TYPE.put(2,"邮件");
        MSG_TYPE.put(3,"站内信");
    }
}
