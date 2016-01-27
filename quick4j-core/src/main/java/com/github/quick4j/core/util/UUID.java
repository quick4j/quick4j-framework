package com.github.quick4j.core.util;

/**
 * @author zhaojh
 */
public final class UUID {

  private UUID() {
  }

  public static String getUUID32() {
    String uuid = getUUID();
    return uuid.replaceAll("-", "").toUpperCase();
  }

  public static String getUUID() {
    return java.util.UUID.randomUUID().toString();
  }
}
