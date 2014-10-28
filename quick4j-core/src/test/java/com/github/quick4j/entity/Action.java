package com.github.quick4j.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.List;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.entity.ActionMapper")
@Auditable
public class Action extends Entity {
    private String code;
    private String name;
    private String icon;
    private int index;
    
    @Override
    public String getMetaData() {
        return "操作按钮-" + this.name;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
