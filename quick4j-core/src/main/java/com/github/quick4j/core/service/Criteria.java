package com.github.quick4j.core.service;

import java.util.List;

/**
 * @author zhaojh
 */
public interface Criteria<T, P> {
    T findOne(String id);
    List<T> findAll();
    List<T> findAll(P parameters);
    List<T> findAll(List<String> ids);
    List<T> findAll(String statementShortName, P paramerters);
    void delete(String id);
    void delete(String[] ids);
}
