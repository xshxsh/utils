package com.example.utils.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谢仕海
 * createDate: 2019-7-29 14:41
 * description:
 *  在springboot2.1.6.RELEASE之后，spring-boot-starter-web依赖包已经包含了jackson的所需依赖，无需在手动引入
 *  配置jackson，可以在配置文件中配置，也可以在ObjectMapper中配置，优先使用ObjectMapper的配置
 */

public class JackSonUtils {

    /**
     * Jackson 最常用的 API 就是基于"对象绑定" 的 ObjectMapper
     */
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 在序列化时忽略值为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 禁用序列化日期为timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 对于空的对象转json的时候不抛出错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 设置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 适用JDK8
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new ParameterNamesModule());
    }

    /**
     * 将java-bean对象转换成json字符串
     *
     * @param bean java-bean
     * @return String
     */
    public static String toJsonStr(Object bean) {

        if (bean == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将java-bean对象转换成json byte
     *
     * @param bean java-bean
     * @return String
     */
    public static byte[] toJsonByte(Object bean) {

        if (bean == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsBytes(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list转换成json字符串
     *
     * @param list list
     * @return String
     */
    public static String toJsonStr(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将map转换成json字符串
     *
     * @param map map
     * @return String
     */
    public static String toJsonStr(Map<?, ?> map) {

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将java字符串转换成java对象
     *
     * @param jsonString json 字符串
     * @param beanClass  class
     * @return <T>
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {

        if (StringUtils.isEmpty(jsonString) || beanClass == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, beanClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将java byte 转换成java对象
     *
     * @param jsonByte  json byte
     * @param beanClass class
     * @return <T>
     */
    public static <T> T toBean(byte[] jsonByte, Class<T> beanClass) {

        if (jsonByte == null || jsonByte.length == 0 || beanClass == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonByte, beanClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将jsonString 转换成Map对象
     *
     * @param jsonString
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T toMap(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(jsonString) || typeReference == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将jsonString 转换成Map
     *
     * @param jsonString
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T toMap(String jsonString, Class<T> beanClass) {
        if (StringUtils.isEmpty(jsonString) || beanClass == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, beanClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将jsonString 转换成Map对象
     *
     * @param jsonString
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(String jsonString,
                                         Class<K> keyClass, Class<V> valueClass) {
        if (StringUtils.isEmpty(jsonString) ||
                keyClass == null || valueClass == null) {
            return null;
        }

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, keyClass, valueClass);
            return objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将jsonString 转换成List对象
     *
     * @param jsonString
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(jsonString) || typeReference == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将jsonString 转换成List对象
     *
     * @param jsonString
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String jsonString, Class<T> beanClass) {
        if (StringUtils.isEmpty(jsonString) || beanClass == null) {
            return null;
        }

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanClass);
            return objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从jsonString中获取某个节点
     *
     * @param jsonString
     * @param key
     * @return
     */
    public static JsonNode getValue(String jsonString, String key) {
        if (StringUtils.isEmpty(jsonString) || StringUtils.isEmpty(key)) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(jsonString);
            return root.get(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
