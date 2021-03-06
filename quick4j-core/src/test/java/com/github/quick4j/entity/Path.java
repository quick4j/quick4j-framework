package com.github.quick4j.entity;

import com.github.quick4j.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojh
 */
@Entity
@Table(name = "upm_paths")
public class Path extends BaseEntity {

  @Id
  private String id;
  @Column(name = "path_name")
  private String name;
  @Column(name = "path")
  private String path;
  @Column(name = "path_icon")
  private String icon;
  @Column(name = "pid")
  private String pid;
  @Column(name = "path_index")
  private int index;
  @Column(name = "application_id")
  private String applicationId;

  @Transient
  private String[] actions;
  @Transient
  private Map<String, Action> actionMap;

  @Override
  public String getChineseName() {
    return "资源";
  }

  @Override
  public String getName() {
    return name;
  }

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
