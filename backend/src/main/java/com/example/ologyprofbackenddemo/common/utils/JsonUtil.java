package com.example.ologyprofbackenddemo.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper WRITER;

    private static final ObjectWriter SENSITIVE_WRITER;

    static {
        WRITER = new ObjectMapper();
        WRITER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        WRITER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        WRITER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WRITER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        WRITER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
//        mapper.registerModule(new SimpleModule().addSerializer(String.class, new SensitiveSerializer()));
        SENSITIVE_WRITER = mapper.writer();
    }

    public static <T> T parseObject(String str, Class<T> t) {
        if (str == null) {
            return null;
        }
        try {
            return WRITER.readValue(str, t);
        } catch (Exception e) {
            throw new RuntimeException("convert from String happens exception", e);
        }
    }

    public static <T> List<T> parseArray(String str, Class<T> t) {
        if (str == null) {
            return null;
        }
        try {
            return WRITER.readerForListOf(t).readValue(str);
        } catch (Exception e) {
            throw new RuntimeException("convert from array happens exception", e);
        }
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> t) {
        if (map == null) {
            return null;
        }
        try {
            return WRITER.readValue(toJsonString(map), t);
        } catch (Exception e) {
            throw new RuntimeException("convert from Map happens exception", e);
        }
    }

    public static String toJsonString(Object obj) {
        try {
            return WRITER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("to json String happens exception", e);
        }
    }

    /**
     * 脱敏日志使用此方法打印
     *
     * @param object object
     * @return String
     */
    public static String toSensitiveString(Object object) {
        try {
            return SENSITIVE_WRITER.writeValueAsString(object);
        } catch (IOException e) {
            log.error("sensitive serialize log fail", e);
        }
        return object.toString();
    }
}
