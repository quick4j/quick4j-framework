package com.github.quick4j.plugin.dictionary.entity;

import com.github.quick4j.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

/**
 * @author zhaojh
 */
@javax.persistence.Entity
@Table(name = "sys_dictionary")
public class DicItem extends BaseEntity {

  @Id
  @Column(length = 32)
  private String id;
  @Column(name = "dic_code")
  private String code;
  @Column(name = "dic_name")
  private String name;
  @Column(name = "dic_item_value")
  private String value;
  @Column(name = "dic_item_text")
  private String text;
  @Column(name = "dic_item_index")
  private int index = 1;

  public DicItem() {
  }

  public DicItem(String code, String name, String text, String value) {
    this.code = code;
    this.name = name;
    this.value = value;
    this.text = text;
  }

  public DicItem(String code, String name, String text, String value, Integer index) {
    this.code = code;
    this.name = name;
    this.value = value;
    this.text = text;
    this.index = index;
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
  public List<? extends BaseEntity> getSlave() {
    return null;
  }

  @Override
  public String getChineseName() {
    return "字典项";
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

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
