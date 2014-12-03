package com.github.quick4j.core.service;

import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;

import java.util.List;

/**
 * @author zhaojh
 */
public interface Criteria<T> {
    T findOne(String id);
    List<T> findAll();
    List<T> findAll(String[] ids);
    List<T> findAll(Object parameters);
    List<T> findAll(String statement, Object paramerters);
    DataPaging<T> findAll(Pageable pageable);
    void delete(String id);
    void delete(String[] ids);
    void delete(T entity);
    void delete(List<T> entities);
    void delete(String statement, Object parameters);
}
