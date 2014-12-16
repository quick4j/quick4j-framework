package com.github.quick4j.core.util;

import java.util.UUID;

/**
 * @author zhaojh
 */
public final class UUIDGenerator {
    private UUIDGenerator(){}

    public static String generate32RandomUUID(){
        String uuid = generateRandomUUID();
        return uuid.replaceAll("-", "").toUpperCase();
    }

    public static String generateRandomUUID(){
        return UUID.randomUUID().toString();
    }
}
