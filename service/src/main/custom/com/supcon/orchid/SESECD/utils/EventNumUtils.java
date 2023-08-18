package com.supcon.orchid.SESECD.utils;

import com.supcon.greendill.common.cache.redis.util.RedisUtil;

public class EventNumUtils {

    /**
     * 事件编号
     *
     * @return
     */
    public static String getEventNum() {
        //格式：JQBG-00001
        Long eventNum = (Long) RedisUtil.get("EventNum");
        if (null == eventNum) {
            eventNum = 1L;
            RedisUtil.set("EventNum", eventNum);
        } else {
            eventNum = RedisUtil.incr("EventNum", 1);
        }
        String formattedNumber = String.format("'%05d'", eventNum);
        // 输出随机数
        return "JQBG-" + formattedNumber;
    }
}
