package com.github.quick4j.core.repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhaojh
 */
public interface MyBatisCrudRepository<T, P> {
    T findOne(Class<T> clazz, String id);

    List<T> findAll(Class<T> clazz);

    List<T> findAll(Class<T> clazz, P parameters);

    void save(T entity);

    void save(List<T> entities);

    void update(T entity);

    void update(List<T> entities);

    void delete(Class<T> clazz, String id);

    void delete(Class<T> clazz, String[] ids);

    void delete(Class<T> clazz, P parameters);
}
