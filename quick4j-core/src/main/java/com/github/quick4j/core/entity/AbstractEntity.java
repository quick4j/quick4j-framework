package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author zhaojh
 */
public abstract class AbstractEntity implements Entity{
    private String masterId;

    @Override
    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    @Override
    @JsonIgnore
    public String getMasterId() {
        return masterId;
    }

    @Override
    @JsonIgnore
    public String getMetaData() {
        return String.format("%s|%s", getChineseName(), getName());
    }

    @JsonIgnore
    public boolean isNew(){
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(null == obj || getClass() != obj.getClass()) return false;

        Entity that = (Entity) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

}
