package com.github.quick4j.core.entity;

import java.util.List;

/**
 * @author zhaojh
 */
public abstract class Entity extends AbstractEntity{
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public abstract List<? extends Entity> getSlave();
}
