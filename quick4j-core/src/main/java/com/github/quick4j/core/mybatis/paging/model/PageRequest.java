package com.github.quick4j.core.mybatis.paging.model;

import com.github.quick4j.core.repository.mybatis.support.Sort;

/**
 * @author zhaojh
 */
public class PageRequest<T> implements Pageable<T> {
    private int page;
    private int size;
    private T parameters;
    private Sort sort;

    public PageRequest(int page, int size) {
        this(page, size, null);
    }

    public PageRequest(int page, int size, T parameters) {
        this.page = page < 1 ? 1 : page;
        this.size = size < 1 ? 10 : size;
        this.parameters = parameters;
    }

    @Override
    public int getOffset() {
        return (page - 1) * size ;
    }

    @Override
    public int getLimit() {
        return size;
    }

    @Override
    public T getParameters() {
        return parameters;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
