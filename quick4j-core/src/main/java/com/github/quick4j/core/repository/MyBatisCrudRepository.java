package com.github.quick4j.core.repository;


import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @author zhaojh
 */
public interface MyBatisCrudRepository<T, P> {
    SqlSessionTemplate getSqlSessionTemplate();

    T findOne(Class<T> clazz, String id);

    List<T> findAll(Class<T> clazz);

    List<T> findAll(Class<T> clazz, P parameters);

    DataPaging<T> findAll(Class<T> clazz, Pageable pageable);

    void insert(T entity);

    void insert(List<T> entities);

    void update(T entity);

    void update(List<T> entities);

    void delete(Class<T> clazz, String id);

    void delete(Class<T> clazz, String[] ids);

}
