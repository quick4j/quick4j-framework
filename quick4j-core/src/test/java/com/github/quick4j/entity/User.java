package com.github.quick4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@Auditable
@Table(name = "hr_user_info")
public class User extends AbstractEntity{
    @Id
    private String id;
    @Column(name = "user_loginName")
    private String loginName;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_pwd")
    private String password;
    private String masterId;

    @Override
    @JsonIgnore
    public String getMetaData() {
        return String.format("User|%s", name);
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

    @Override
    public void setMasterId(String id) {
        this.masterId = id;
    }

    @Override
    @JsonIgnore
    public String getMasterId() {
        return masterId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
