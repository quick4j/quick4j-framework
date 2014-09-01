package com.github.quick4j.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.plugin.logging.annontation.Auditable;

/**
 * @author zhaojh
 */
@Auditable
@MapperNamespace("com.github.quick4j.entity.UserMapper")
public class User extends Entity{
    private String loginName;
    private String name;
    private String password;

    @Override
    public String getMetaData() {
        return "User ";
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
