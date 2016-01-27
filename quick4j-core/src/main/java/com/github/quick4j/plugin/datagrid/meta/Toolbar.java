package com.github.quick4j.plugin.datagrid.meta;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author zhaojh
 */
public class Toolbar extends ArrayList<Toolbutton> {

  public Toolbar addToolbutton(String text, String iconCls, String handler) {
    Toolbutton button = new Toolbutton(text, iconCls, handler);
    this.add(button);
    return this;
  }

  public Toolbar addToolbutton(Toolbutton button) {
    this.add(button);
    return this;
  }

  public void removeToolbutton(String code) {
    Iterator<Toolbutton> iterator = this.iterator();
    while (iterator.hasNext()) {
      if (code.equals(iterator.next().getId())) {
        iterator.remove();
        break;
      }
    }
  }
}
