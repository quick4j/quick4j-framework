package com.github.quick4j.core.repository.mybatis.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class Sort {
    private List<Order> orders;

    public Sort() {
        orders = new ArrayList<Order>();
    }

    public Sort(Order order) {
        orders = new ArrayList<Order>();
        orders.add(order);
    }

    public Sort(List<Order> orders){
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
