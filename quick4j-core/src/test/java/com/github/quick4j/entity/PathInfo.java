package com.github.quick4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;

import java.util.List;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.upm.paths.entity.PathInfoMapper")
public class PathInfo extends Entity {
    private String id;
    private String name;
    private String icon;
    private String pid;
    private List<Action> actions;

    @Override
    public String getMetaData() {
        return null;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
