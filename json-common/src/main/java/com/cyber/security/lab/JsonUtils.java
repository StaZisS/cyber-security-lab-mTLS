package com.cyber.security.lab;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(Object json, Class<T> clazz) {
        try {
            String jsonString = objectMapper.writeValueAsString(json);
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseTypeEnum getTypeResponseEnum(String value) {
        try {
            return ResponseTypeEnum.valueOf(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
