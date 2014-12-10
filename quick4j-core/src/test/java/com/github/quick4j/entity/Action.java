package com.github.quick4j.entity;

import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@Table(name = "upm_actions")
public class Action extends AbstractEntity {
    @Id
    private String id;
    @Column(name = "action_code")
    private String code;
    @Column(name = "action_name")
    private String name;
    @Column(name = "action_icon")
    private String icon;
    @Column(name = "action_index")
    private int index;

    @Override
    public String getChineseName() {
        return "操作按钮";
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
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
