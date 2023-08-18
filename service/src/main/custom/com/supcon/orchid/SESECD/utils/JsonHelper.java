package com.supcon.orchid.SESECD.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ludunyue on 2020/4/16.
 */
public class JsonHelper {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String writeValue(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static  <T>  T  parseJson(String json,Class<T> cls){
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return mapper.readValue(json,cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json转list
     * @param json
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> convertToList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<List<T>>() {
                // empty on purpose
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串转换成 List 集合
     *
     * @param json JSON 字符串
     * @param type 类型引用
     * @param <T>  集合元素类型
     * @return 解析后的集合
     * @throws IOException 如果解析失败
     */
    public static <T> List<T> parseArray(String json, com.fasterxml.jackson.core.type.TypeReference<List<T>> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象转成一个map形式
     * @param obj
     * @return
     */
    public static Map<String, String> convertMap(Object obj) {
        String jsonString = JSON.toJSONString(obj);
        Map<String, Object> map = JSON.parseObject(jsonString, new TypeReference<Map<String, Object>>() {});
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().toString());
        }
        return resultMap;
    }
}
