package com.github.quick4j.core.mybatis.paging.model;

import com.github.quick4j.core.repository.mybatis.support.Sort;

/**
 * @author zhaojh
 */
public interface Pageable<T> {

  int getOffset();

  int getLimit();

  T getParameters();

  Sort getSort();
}
