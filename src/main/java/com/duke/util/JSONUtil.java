package com.duke.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * json工具类
 */
public class JSONUtil {

	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}

	public static <T> T toBean(String json,Class<T> clz){
		if(CheckUtil.stringIsNull(json)){
			return null;
		}
		return JSON.parseObject(json,clz);
	}

	public static Map<String, Object> toMap(String json){
		 return JSON.parseObject(json, new TypeReference<Map<String,Object>>(){});
	}

	public static <T> List<T> toList(String json,Class<T> clz){
		return JSON.parseArray(json, clz);
	}

}
