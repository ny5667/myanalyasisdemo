package com.supcon.orchid.SESECD.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateLocalUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     *  日期类型转成yyyyMMdd格式字符串.
     * @param date      日期
     * @param pattern   需要转换的文字类型
     * @return
     */
    public static String date2String(Date date, String pattern){
        if (null == date) {
            date = new Date();
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        try{
            LocalDateTime dateTime = dateToDateTime(date);
            return getDateTimeFormat(pattern).format(dateTime);
        }catch (Exception e){
            throw new RuntimeException("日期转换异常");
        }
    }

    public static String dateTime2Strung(LocalDateTime dateTime,String pattern){
        if(null == dateTime){
            dateTime = LocalDateTime.now();
        }
        if(StringUtils.isEmpty(pattern)){
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        try{
            return getDateTimeFormat(pattern).format(dateTime);
        }catch (Exception e){
            throw new RuntimeException("日期转换异常");
        }
    }

    /**
     * 字符串转换为日期类型
     * 根据字符串长度转换
     * @param date  字符串类型的格式
     * @return
     */
    public static Date string2Date(String date,String pattern){
        LocalDateTime dateTime = string2LocalDateTime(date,pattern);
        return dateTimeToDate(dateTime);
    }

    /**
     * 字符串转localDateTime类型
     * @param date  字符串格式的日期
     * @return
     */
    public static LocalDateTime string2LocalDateTime(String date,String pattern){
        try{
            if(StringUtils.isEmpty(pattern)){
                pattern = YYYY_MM_DD_HH_MM_SS;
            }
            DateTimeFormatter format = getDateTimeFormat(pattern);
            return LocalDate.parse(date,format).atStartOfDay();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("日期转换异常");
        }
    }

    /**
     * 以date为基准获取n分钟后的日期，并转换成字符串格式
     * @param date  long型日期
     * @param n     -1表示date的前一分钟，1表示date的后一分钟。
     * @return
     */
    public static String getNextMinute2String(Date date, int n) {
        LocalDateTime dateTime = dateToDateTime(date);
        LocalDateTime time = dateTime.plusMinutes(n);
        return dateTime2Strung(time,null);
    }


    /**
     * 以date为基准获取n天后的日期，
     * @param date  long型日期
     * @param n     -1表示date的前一天，1表示date的后一天。
     * @return
     */
    public static String getNextDat2String(Date date, int n) {
        LocalDateTime dateTime = dateToDateTime(date);
        LocalDateTime time = dateTime.plusDays(n);
        return dateTime2Strung(time,null);
    }


    /**
     * 以date为基准获取n天后的日期，
     * @param date  long型日期
     * @param n     -1表示date的前一天，1表示date的后一天。
     * @return
     */
    public static Date getNextDate(Date date, int n) {
        LocalDateTime dateTime = dateToDateTime(date);
        LocalDateTime time = dateTime.plusDays(n);
        return dateTimeToDate(time);
    }

    /**
     * 以date为基准获取n分钟后的日期，
     * @param date
     * @param n     -1表示date的前一分钟，1表示date的后一分钟。
     * @return
     */
    public static Date getNextMinute(Date date, int n) {
        LocalDateTime dateTime = dateToDateTime(date);
        LocalDateTime time = dateTime.plusMinutes(n);
        return dateTimeToDate(time);
    }

    /**
     * 以date为基准获取n秒后的日期，
     * @param date
     * @param n     -1表示date的前一秒，1表示date的后一秒。
     * @return
     */
    public static Date getNextSeconds(Date date, int n) {
        LocalDateTime dateTime = dateToDateTime(date);
        LocalDateTime time = dateTime.plusSeconds(n);
        return dateTimeToDate(time);
    }


    /**
     * 将localDateTime转换为Date格式
     * @param dateTime  LocalDateTime
     * @return
     */
    public static Date dateTimeToDate(LocalDateTime dateTime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 将日期转换为LocalDateTime格式
     * @param date  日期
     * @return
     */
    public static LocalDateTime dateToDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant,zoneId);
    }

    /**
     * 获取指定模式pattern的DateTimeFormatter对象.
     * DateTimeFormatter是线程安全的
     * @param str
     * @return
     */
    private static DateTimeFormatter getDateTimeFormat(String str){
        return DateTimeFormatter.ofPattern(str);
    }
}
