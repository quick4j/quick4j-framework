package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.annotation.Mapper;

/**
 * @author zhaojh
 */
public abstract class AbstractEntity implements Entity{

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
