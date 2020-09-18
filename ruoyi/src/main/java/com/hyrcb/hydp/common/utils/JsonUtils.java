/**
 * File Name: JsonUtils.java
 * Date: 2020-09-18 17:12:42
 */
package com.hyrcb.hydp.common.utils;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Description: JSON工具类
 * @author shenzulun
 * @date 2020-09-18
 * @version 1.0
 */
public class JsonUtils {
	protected static Logger log = LoggerFactory.getLogger(JsonUtils.class);	
	
	/**
	 * json字符串 -> 对象
	 * @param <T>
	 * @param jsonStr
	 * @param parametrizedClass
	 * @param parameterClasses
	 * @return
	 */
	public static <T> T toObject(String jsonStr, Class<T> parametrizedClass, Class<?>... parameterClasses) {
		T result = null;
		try {
			if(parameterClasses != null && parameterClasses.length > 0) {
				JavaType javaType = TypeFactory.defaultInstance().constructParametricType(parametrizedClass, parameterClasses);
				result = new ObjectMapper().readValue(jsonStr, javaType);
			}else {
				result = new ObjectMapper().readValue(jsonStr, parametrizedClass);
			}
		} catch (JsonMappingException e) {
			log.error("json转换出错", e);
		} catch (JsonProcessingException e) {
			log.error("json转换出错", e);
		} catch (IOException e) {
			log.error("json转换出错", e);
		}
		return result;
	}
	
	/**
	 * object -> json
	 * @param obj
	 * @return
	 * @throws IOException 
	 */
	public static String toJson(Object obj) throws IOException {
		return new ObjectMapper().writeValueAsString(obj);
	}

}
