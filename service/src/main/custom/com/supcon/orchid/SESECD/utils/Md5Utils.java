package com.supcon.orchid.SESECD.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *    Md5加密工具类
 * </p>
 *
 * @author lufengdong
 * @create 2023-04-16 16:55
 */
@Slf4j
public class Md5Utils {
    // 加密后返回字符串长度32
    public static final String RESPONSE_LENGTH_32 = "32";
    // 加密后返回字符串长度36
    public static final String RESPONSE_LENGTH_36 = "36";
    // 加密后返回字符串形式-不区分大小写
    public static final int RESPONSE_TYPE_IGNORE = 0;
    // 加密后返回字符串形式-全部大写
    public static final int RESPONSE_TYPE_UPPER = 1;
    // 加密后返回字符串形式-全部小写
    public static final int RESPONSE_TYPE_LOWER = 2;


    /**
     * Md5加密
     * <p>
     *    缺省长度36， 不区分大小写
     * </p>
     * @param plainText 待加密明文数据
     * @return 加密后字符串
     */
    public static String encrypt(String plainText){
        return encrypt(plainText, RESPONSE_LENGTH_32, RESPONSE_TYPE_IGNORE);
    }

    /**
     * Md5加密
     * @param plainText    待加密明文数据
     * @param length       加密后字符串长度
     * @param responseMode 待加后字符串响应类型
     * @return 加密后字符串
     */
    public static String encrypt(String plainText, String length, int responseMode){
        StringBuilder md5Code = new StringBuilder();
        //获得MD5加密对象;
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance("Md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = instance.digest(plainText.getBytes()); // 获得MD5加密的byte摘要;
        for(byte b: digest) {
            String hexString = Integer.toHexString(b & 0xff);
            if (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            md5Code.append(hexString);
        }
        if(StringUtils.isBlank(length)  || RESPONSE_LENGTH_36.equals(length)){
            md5Code.insert(8, "-");
            md5Code.insert(13,"-");
            md5Code.insert(18,"-");
            md5Code.insert(23,"-");
        }
        String response;
        if(RESPONSE_TYPE_UPPER == responseMode){
            response =  StringUtils.upperCase(md5Code.toString());
        }else if(RESPONSE_TYPE_LOWER == responseMode){
            response =  StringUtils.lowerCase(md5Code.toString());
        }else{
            response = md5Code.toString();
        }
        log.info("加密后字符串：{}", response);
        return response;
    }
}
