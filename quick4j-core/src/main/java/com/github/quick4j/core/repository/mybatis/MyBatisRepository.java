package com.github.quick4j.core.repository.mybatis;


import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @author zhaojh
 */
public interface MyBatisRepository {
    SqlSessionTemplate getSqlSessionTemplate();
    <T extends Entity> T findOne(Class<T> clazz, String id);
    <T extends Entity> List<T> findAll(Class<T> clazz);
    <T extends Entity> List<T> findAll(Class<T> clazz, List<String> ids);
    <T extends Entity, P> List<T> findAll(Class<T> clazz, P parameter);
    <T extends Entity> List<T> findAll(Class<T> clazz, String statement, Object parameter);
    <T extends Entity> DataPaging<T> findAll(Class<T> clazz, Pageable pageable);
    <T extends Entity> void insert(T entity);
    <T extends Entity> void insert(List<T> entities);
    <T extends Entity> void update(T entity);
    <T extends Entity> void update(List<T> entities);
    <T extends Entity> void delete(Class<T> clazz, String id);
    <T extends Entity> void delete(Class<T> clazz, String[] ids);
    <T extends Entity> void delete(T entity);
    <T extends Entity> void delete(List<T> entities);
    <T extends Entity> void delete(Class<T> clazz, Object parameter);
    <T> List<T> selectList(Class<? extends Entity> clazz, String statement, Object parameter);
    void delete(Class<? extends Entity> clazz, String statement, Object parameter);
}
