package com.github.quick4j.entity;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

/**
 * @author zhaojh
 */
@Auditable
@Entity
@Table(name = "hr_user_info")
public class User extends BaseEntity {

  @Id
  private String id;
  @Column(name = "user_loginName")
  private String loginName;
  @Column(name = "user_name")
  private String name;
  @Column(name = "user_pwd")
  private String password;

  @Override
  public List<? extends BaseEntity> getSlave() {
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
  public String getChineseName() {
    return "User";
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
