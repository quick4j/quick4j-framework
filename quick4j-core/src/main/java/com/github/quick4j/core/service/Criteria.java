package com.github.quick4j.core.service;

import java.util.List;

/**
 * @author zhaojh
 */
public interface Criteria<T, P> {
    T findOne(String id);

    List<T> list();

    List<T> list(P parameters);

    void delete(String id);

    void delete(String[] ids);
}
