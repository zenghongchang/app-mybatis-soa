package edu.hnust.application.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hnust.application.common.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

/**
 * JSON工具类
 * 
 * @author Henry(fba02)
 * @version [版本号, 2017年11月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Json {
    public static String toJson(Object object) {
        String result = "";
        if (null == object) {
            return result;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static Object fromJson(String requestStr, Class<?> clazz) {
        if (StringUtils.isEmpty(requestStr)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        Object object = null;
        try {
            object = objectMapper.readValue(requestStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    
    public static Object fromJson(String requestStr, Class<?> collectionClazz, Class<?>... elementClazzes) {
        if (StringUtils.isEmpty(requestStr)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Object object = null;
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
            object = objectMapper.readValue(requestStr, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    
    public static boolean getBooleanParameter(JsonNode body, String name, boolean defaults) {
        JsonNode jsonNode = body.get(name);
        if (jsonNode != null) {
            return jsonNode.asBoolean();
        }
        return defaults;
    }
    
    public static String getEncodeWMLStringParameter(JsonNode body, String name, String defaults) {
        return StringUtil.encodeWML(Json.getStringParameter(body, name, defaults));
    }
    
    public static int getIntParameter(JsonNode body, String name, int defaults) {
        JsonNode jsonNode = body.get(name);
        if (jsonNode != null) {
            try {
                return jsonNode.asInt();
            } catch (Exception e) {
            }
        }
        return defaults;
    }
    
    public static int getIntParameterCompatiable(JsonNode body, String[] paramNames, int discardvalue) {
        for (String paramName : paramNames) {
            int paramvalue = Json.getIntParameter(body, paramName, discardvalue);
            if (paramvalue != discardvalue) {
                return paramvalue;
            }
        }
        return discardvalue;
    }
    
    public static long getLongParameter(JsonNode body, String name, long defaults) {
        try {
            JsonNode jsonNode = body.get(name);
            if (jsonNode != null) {
                return jsonNode.asLong();
            }
        } catch (Throwable e) {
            return defaults;
        }
        return defaults;
    }
    
    public static String getSafeStringParameter(JsonNode body, String name, String defaults) {
        return StringUtil.removeInvalidWML(Json.getStringParameter(body, name, defaults));
    }
    
    public static String getStringParameter(JsonNode body, String name, String defaults) {
        JsonNode jsonNode = body.get(name);
        if (jsonNode != null) {
            return jsonNode.asText();
        }
        return defaults;
    }
}