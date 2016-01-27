package com.github.quick4j.core.mybatis.paging.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhaojh
 */
public class DataPaging<T> {

  private final List<T> rows = new ArrayList<T>();
  private final int total;

  public DataPaging(Collection<T> rows, int total) {
    this.rows.addAll(rows);
    this.total = total;
  }

  public List<T> getRows() {
    return rows;
  }

  public int getTotal() {
    return total;
  }

  @Override
  public String toString() {
    return "DataPaging{" +
           " total=" + total +
           ", rows=" + rows +
           '}';
  }
}
