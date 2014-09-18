package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author zhaojh
 */
public abstract class Entity extends AbstractEntity{
    private String id;
    private String masterId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public String getMasterId() {
        return masterId;
    }

    @Override
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public abstract List<? extends Entity> getSlave();
}
