package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;

/**
 * @author zhaojh
 */
public abstract class AbstractEntity {

    public abstract String getId();

    public abstract void setId(String id);

    @JsonIgnore
    public abstract String getMetaData();

    @JsonIgnore
    public boolean isNew(){
        return null == getId();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(null == obj || getClass() != obj.getClass()) return false;

        AbstractEntity that = (AbstractEntity) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    @JsonIgnore
    public String getMapperNamespace(){
        String namespace ;
        MapperNamespace annotation = this.getClass().getAnnotation(MapperNamespace.class);
        if(null != annotation){
            namespace = annotation.value();
        }else{
            String className = this.getClass().getName();
            throw new NotFoundException("model.notfound.mappernamespace", new Object[]{className});
        }
        return namespace;
    }
}
