package com.github.quick4j.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaojh
 */
public final class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    private JsonUtils() {}

    public static String toJson(Object value){
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T formJson(String value, Class<T> valueType){
        try {
            return mapper.readValue(value, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T formJson(String value, TypeReference typeReference){
        try {
            return mapper.readValue(value, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
