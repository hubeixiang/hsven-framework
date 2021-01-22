package org.framework.hsven.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.framework.hsven.utils.view.DicView;
import org.framework.hsven.utils.view.IdView;
import org.framework.hsven.utils.view.TextView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * @author sven
 */
public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final boolean useFastJson;
    private static final ObjectMapper mapper;
    private static final ObjectMapper mapperCompatible;
    private static ObjectMapper mapperBean;

    static {
    }

    static {
        useFastJson = false;
        mapper = new ObjectMapper();
        //发序列化时忽略不识别的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapperCompatible = new ObjectMapper();
        //发序列化时忽略不识别的属性
        mapperCompatible.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperCompatible.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapperCompatible.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static ObjectMapper getJacksonObjectMapper() {
        //        if (mapperBean == null) {
        //            try {
        //                mapperBean = SpringContextUtil.getBean(ObjectMapper.class);
        //            } catch (BeansException ignored) {
        //            }
        //        }
        return mapperBean != null ? mapperBean : mapper;
    }

    public static String toJson(Object obj, boolean useFastJson) {
        try {
            if (useFastJson) {
                return JSON.toJSONString(obj);
            } else {
                return getJacksonObjectMapper().writeValueAsString(obj);
            }
        } catch (Exception e) {
            log.warn("Fail to serialize object to json.", e);
            return null;
        }
    }

    public static String toJson(Object obj) {
        return toJson(obj, JsonUtils.useFastJson);
    }

    public static String toJson(Object obj, boolean useFastJson, Class<?> serializationView) {
        try {
            if (useFastJson) {
                return JSON.toJSONString(obj);
            } else {
                return getJacksonObjectMapper().writerWithView(serializationView).writeValueAsString(obj);
            }
        } catch (Exception e) {
            log.warn("Fail to serialize object to json.", e);
            return null;
        }
    }

    public static String toJson(Object obj, Class<?> serializationView) {
        return toJson(obj, JsonUtils.useFastJson, serializationView);
    }

    public static String toIdJson(Object obj, boolean useFastJson) {
        return toJson(obj, useFastJson, IdView.class);
    }

    public static String toIdJson(Object obj) {
        return toIdJson(obj, JsonUtils.useFastJson);
    }

    public static String toTextJson(Object obj, boolean useFastJson) {
        return toJson(obj, useFastJson, TextView.class);
    }

    public static String toTextJson(Object obj) {
        return toIdJson(obj, JsonUtils.useFastJson);
    }

    public static String toDicJson(Object obj, boolean useFastJson) {
        return toJson(obj, useFastJson, DicView.class);
    }

    public static String toDicJson(Object obj) {
        return toIdJson(obj, JsonUtils.useFastJson);
    }

    public static <T> T fromJson(String json, Class<T> valueType, boolean useFastJson) {
        if (json == null) {
            return null;
        }
        try {
            if (useFastJson) {
                return JSON.parseObject(json, valueType);
            } else {
                return getJacksonObjectMapper().readValue(json, valueType);
            }
        } catch (Exception e) {
            log.warn("Fail to parse json to object.", e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> valueType) {
        return fromJson(json, valueType, JsonUtils.useFastJson);
    }

    /**
     * TODO: Use {org.springframework.core.PrioritizedParameterNameDiscoverer} instead.
     */
    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef, boolean useFastJson) {
        if (json == null) {
            return null;
        }
        try {
            if (useFastJson) {
                Type type = valueTypeRef.getType();
                return JSON.parseObject(json, type);
            } else {
                return getJacksonObjectMapper().readValue(json, valueTypeRef);
            }
        } catch (Exception e) {
            log.warn("Fail to parse json to object.", e);
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
        return fromJson(json, valueTypeRef, JsonUtils.useFastJson);
    }

    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        return getJacksonObjectMapper().convertValue(fromValue, toValueType);
    }

    public static <T> T convert(Object fromValue, TypeReference<T> toValueTypeRef) {
        return getJacksonObjectMapper().convertValue(fromValue, toValueTypeRef);
    }

    public static JavaType constructType(Class<?> type) {
        return getJacksonObjectMapper().constructType(type);
    }
}
