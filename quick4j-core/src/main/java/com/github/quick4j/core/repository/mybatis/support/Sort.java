package com.github.quick4j.core.repository.mybatis.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class Sort {

  private List<Order> orders;

  public Sort(String property) {
    orders = new ArrayList<Order>();
    orders.add(new Order(property, Order.ASC));
  }

  public Sort(String property, String direction) {
    orders = new ArrayList<Order>();
    orders.add(new Order(property, direction));
  }

  public Sort(List<Order> orders) {
    this.orders = orders;
  }

  public List<Order> getOrders() {
    return orders;
  }
}
