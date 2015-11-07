package com.f2boy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.NullNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class JsonUtils {

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper defaultMapper;
    private static Map<DateFormat, ObjectMapper> dateFormatMappers = new ConcurrentHashMap<>();

    static {

        logger.warn("没有定义bean[objectMapper]，将new一个新的作为默认的的ObjectMapper.");

        defaultMapper = new ObjectMapper();
        defaultMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        defaultMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 配置为true表示mapper接受只有一个元素的数组的反序列化
        defaultMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        // mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
        defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static String object2Json(Object obj) {

        return object2Json(obj, null);
    }

    /**
     * 对象转为json
     *
     * @param obj        要转换的对象
     * @param dataFormat 转换的日期格式化类型
     * @return 转换后的json字符串
     */
    public static String object2Json(Object obj, DateFormat dataFormat) {
        String str = "";
        if (obj != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }

            try {
                str = om.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("转为json格式出错 -- object=" + obj, e);
            }
        }

        return str;
    }

    /**
     * json字符串转为对象（默认日期格式化类型）
     *
     * @param json  json字符串
     * @param clazz 要转换的目标对象的class
     * @param <T>   要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, Class<T> clazz) {

        return json2Object(json, clazz, null);
    }

    /**
     * json字符串转为对象
     *
     * @param json       json字符串
     * @param clazz      要转换的目标对象的class
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, Class<T> clazz, DateFormat dataFormat) {
        T t = null;
        if (json != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }

            try {
                t = om.readValue(json, clazz);
            } catch (IOException e) {
                throw new RuntimeException("json转class出错 -- json=" + json + ", class=" + clazz.getName(), e);
            }
        }

        return t;
    }

    /**
     * json字符串转为对象（默认日期格式化类型）
     *
     * @param json json字符串
     * @param tr   要转换的目标对象的TypeReference
     * @param <T>  要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, TypeReference<T> tr) {

        return json2Object(json, tr, null);
    }

    /**
     * json字符串转为对象
     *
     * @param json       json字符串
     * @param tr         要转换的目标对象的TypeReference
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Object(String json, TypeReference<T> tr, DateFormat dataFormat) {
        T t = null;
        if (json != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }

            try {
                t = om.readValue(json, tr);
            } catch (IOException e) {
                throw new RuntimeException("json转typeReference出错 -- json=" + json + ", typeReference=" + tr.getType(),
                        e);
            }
        }

        return t;
    }

    /**
     * json字符串转为对象（默认日期格式化类型）
     *
     * @param json     json字符串
     * @param javaType 要转换的目标对象的JavaType
     * @param <T>      要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, JavaType javaType) {

        return json2Object(json, javaType, null);
    }

    /**
     * json字符串转为对象
     *
     * @param json       json字符串
     * @param javaType   要转换的目标对象的JavaType
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Object(String json, JavaType javaType, DateFormat dataFormat) {
        T t = null;
        if (json != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }

            try {
                t = om.readValue(json, javaType);
            } catch (IOException e) {
                throw new RuntimeException("json转javaType出错 -- json=" + json + ", javaType=" + javaType, e);
            }
        }

        return t;
    }

    /**
     * json序列化后的字节流转为对象（默认日期格式化类型）
     *
     * @param is    json序列化后的字节流
     * @param clazz 要转换的目标对象的class
     * @param <T>   要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T is2Object(InputStream is, Class<T> clazz) {
        return is2Object(is, clazz, null);
    }

    /**
     * json序列化后的字节流转为对象
     *
     * @param is         json序列化后的字节流
     * @param clazz      要转换的目标对象的class
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T is2Object(InputStream is, Class<T> clazz, DateFormat dataFormat) {
        T o = null;
        if (is != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }
            try {
                o = om.readValue(is, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return o;
    }

    /**
     * json序列化后的字节流转为对象（默认日期格式化类型）
     *
     * @param is  json序列化后的字节流
     * @param tr  要转换的目标对象的TypeReference
     * @param <T> 要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T is2Object(InputStream is, TypeReference<T> tr) {
        return is2Object(is, tr, null);
    }

    /**
     * json序列化后的字节流转为对象
     *
     * @param is         json序列化后的字节流
     * @param tr         要转换的目标对象的TypeReference
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T is2Object(InputStream is, TypeReference<T> tr, DateFormat dataFormat) {
        T o = null;
        if (is != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }
            try {
                o = om.readValue(is, tr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return o;
    }

    /**
     * json序列化后的字节流转为对象（默认日期格式化类型）
     *
     * @param reader json序列化后的字节流
     * @param clazz  要转换的目标对象的class
     * @param <T>    要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T reader2Object(Reader reader, Class<T> clazz) {
        return reader2Object(reader, clazz, null);
    }

    /**
     * json序列化后的字节流转为对象
     *
     * @param reader     json序列化后的字节流
     * @param clazz      要转换的目标对象的class
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T reader2Object(Reader reader, Class<T> clazz, DateFormat dataFormat) {
        T o = null;
        if (reader != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }
            try {
                o = om.readValue(reader, clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return o;
    }

    /**
     * json序列化后的字节流转为对象（默认日期格式化类型）
     *
     * @param reader json序列化后的字节流
     * @param tr     要转换的目标对象的TypeReference
     * @param <T>    要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T reader2Object(Reader reader, TypeReference<T> tr) {
        return reader2Object(reader, tr, null);
    }

    /**
     * json序列化后的字节流转为对象
     *
     * @param reader     json序列化后的字节流
     * @param tr         要转换的目标对象的TypeReference
     * @param dataFormat 转换的日期格式化类型
     * @param <T>        要转换的目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T reader2Object(Reader reader, TypeReference<T> tr, DateFormat dataFormat) {
        T o = null;
        if (reader != null) {
            ObjectMapper om;
            if (dataFormat == null) {
                om = defaultMapper;
            } else {
                om = getFromMap(dataFormat);
            }
            try {
                o = om.readValue(reader, tr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return o;
    }

    /**
     * 获取泛型的JavaType
     *
     * @param objectClass    泛型的objectClass
     * @param elementClasses 元素类
     * @return JavaType Java类型
     */
    public static JavaType getJavaType(Class<?> objectClass, Class<?>... elementClasses) {
        return defaultMapper.getTypeFactory().constructParametrizedType(objectClass, objectClass, elementClasses);
    }

    /**
     * 从json序列化后的字节流中读出JsonNode
     */
    public static JsonNode readTree(InputStream in) {
        JsonNode node = NullNode.getInstance();
        try {
            node = defaultMapper.readTree(in);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return node;
    }

    private static ObjectMapper getFromMap(DateFormat key) {
        ObjectMapper om = dateFormatMappers.get(key);
        if (om == null) {
            om = new ObjectMapper();
            om.setDateFormat(key);

            om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 配置为true表示mapper接受只有一个元素的数组的反序列化
            om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            // mapper在遇到mapper对象中存在json对象中没有的数据变量时不报错，可以进行反序列化
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            dateFormatMappers.put(key, om);
        }

        return om;
    }

    /**
     * 判断字符串是否json格式
     */
    public static boolean isJson(String json) {

        try {
            json2Object(json, Object.class);
            return true;
        } catch (RuntimeException e) {
            logger.error("不是json格式的字符串：" + json);
            return false;
        }
    }

}
