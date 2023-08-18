package com.supcon.orchid.SESECD.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;


/**
 * @Description 对象转json工具类 
 */
public class JSONUtil {
	private static final Gson gson = new GsonBuilder().serializeNulls().create();	
	
private JSONUtil() {}
	
	/**
	 * @Description 对象转换成json字符串
	 * @return
	 */
	public static String formatToJson(Object object) {
	 	return gson.toJson(object);
	}
	
	/**
	 * @Description json字符串转对象
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

    /**
     * 将对象转换成map
     * @param object 需要转换的对象
     * @return 返回map
     */
	public static Map<?, ?> objectToMap(Object object) {
		String jsonStr = formatToJson(object);
		return jsonToObject(jsonStr, Map.class);
	}

}
