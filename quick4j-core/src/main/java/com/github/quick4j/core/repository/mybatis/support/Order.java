package com.github.quick4j.core.repository.mybatis.support;

/**
 * @author zhaojh.
 */
public class Order {
    private Direction direction;
    private String property;

    public Order(Direction direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }
}
