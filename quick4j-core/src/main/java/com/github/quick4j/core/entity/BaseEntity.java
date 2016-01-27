package com.github.quick4j.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

/**
 * @author zhaojh.
 */
public abstract class BaseEntity implements Serializable {

  @Transient
  private String masterId;

  public abstract void setId(String id);

  public abstract String getId();

  @JsonIgnore
  public abstract List<? extends BaseEntity> getSlave();

  @JsonIgnore
  public abstract String getChineseName();

  public abstract String getName();

  @JsonIgnore
  public String getMasterId() {
    return masterId;
  }

  public void setMasterId(String masterId) {
    this.masterId = masterId;
  }

  @JsonIgnore
  public String getMetaData() {
    return String.format("%s|%s", getChineseName(), getName());
  }

  @JsonIgnore
  public boolean isNew() {
    return StringUtils.isBlank(getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (null == obj || getClass() != obj.getClass()) {
      return false;
    }

    BaseEntity that = (BaseEntity) obj;
    return null == this.getId() ? false : this.getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    hashCode += null == getId() ? 0 : getId().hashCode() * 31;
    return hashCode;
  }
}
