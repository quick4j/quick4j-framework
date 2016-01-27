package com.github.quick4j.core.repository.mybatis.support;


import org.springframework.util.Assert;

/**
 * @author zhaojh.
 */
public class Order {

  public static final String DESC = "desc";
  public static final String ASC = "asc";

  private String direction;
  private String property;

  public Order(String property) {
    Assert.hasText(property);
    this.direction = ASC;
    this.property = property;
  }

  public Order(String property, String direction) {
    Assert.hasText(direction);
    Assert.hasText(property);
    this.direction = direction;
    this.property = property;
  }

  public String getDirection() {
    return direction;
  }

  public String getProperty() {
    return property;
  }
}
