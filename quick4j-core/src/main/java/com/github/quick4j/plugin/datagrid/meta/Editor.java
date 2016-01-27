package com.github.quick4j.plugin.datagrid.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojh
 */
public class Editor implements Cloneable {

  private String type;
  private Map<String, Object> options;

  public Editor(String type) {
    this.type = type;
    this.options = new HashMap<String, Object>();
  }

  public String getType() {
    return type;
  }

  public Map<String, Object> getOptions() {
    return options;
  }

  public void addOption(String key, Object value) {
    options.put(key, value);
  }

  @Override
  protected Editor clone() throws CloneNotSupportedException {
    Editor editor = new Editor(this.type);
    Map<String, Object> options = new HashMap<String, Object>(getOptions());
    editor.setOptions(options);
    return editor;
  }

  private void setOptions(Map<String, Object> options) {
    this.options = options;
  }
}
