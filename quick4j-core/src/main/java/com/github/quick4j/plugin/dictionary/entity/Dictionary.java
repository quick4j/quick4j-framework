package com.github.quick4j.plugin.dictionary.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
public class Dictionary {

  private String code;
  private String name;
  private List<DicItem> items;
  private String type = "dynamic";

  public Dictionary(String code, String name) {
    this.code = code;
    this.name = name;
    items = new ArrayList<DicItem>();
  }

  public Dictionary(String code, String name, List<DicItem> items) {
    this.code = code;
    this.name = name;
    this.items = items;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public List<DicItem> getItems() {
    return items;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
