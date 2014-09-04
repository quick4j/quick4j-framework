package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public List<? extends Entity> getSlave(){
        return null;
    }
}
