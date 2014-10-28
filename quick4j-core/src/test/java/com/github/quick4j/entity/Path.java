package com.github.quick4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.upm.paths.entity.PathMapper")
@Auditable
public class Path extends Entity {
    private String name;
    private String path;
    private String icon;
    private String pid;
    private int index;
    private String applicationId;
    private String[] actions;
    private Map<String, Action> actionMap;

    @Override
    @JsonIgnore
    public String getMetaData() {
        return "资源-" + this.name;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public Map<String, Action> getActionMap() {
        return actionMap;
    }

    public void setActionMap(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }
}
