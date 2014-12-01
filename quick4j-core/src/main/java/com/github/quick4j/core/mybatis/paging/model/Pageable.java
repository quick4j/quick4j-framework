package com.github.quick4j.core.mybatis.paging.model;

/**
 * @author zhaojh
 */
public interface Pageable<T> {
    int getOffset();

    int getLimit();

    T getParameters();
}
