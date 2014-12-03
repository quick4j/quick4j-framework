package com.github.quick4j.core.repository.mybatis;


import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @author zhaojh
 */
public interface Repository {
    SqlSessionTemplate getSqlSessionTemplate();

    <T extends Entity> T find(Class<T> clazz, String id);
    <T extends Entity> List<T> findAll(Class<T> entityClass);
    <T extends Entity> List<T> findByIds(Class<T> entityClass, String[] ids);
    <T extends Entity> List<T> findByIdsAndSorting(Class<T> entityClass, String[] ids, Sort sort);
    <T extends Entity> List<T> findByParams(Class<T> entityClass, Object parameters);
    <T extends Entity> List<T> findByParamsAndSorting(Class<T> entityClass, Object parameters, Sort sort);
    <T extends Entity> DataPaging<T> findPaging(Class<T> entityClass, Pageable pageable);

    <T extends Entity> void insert(T entity);
    <T extends Entity> void insert(List<T> entities);

    <T extends Entity> void updateById(T entity);
    <T extends Entity> void updateById(List<T> entities);

    <T extends Entity> void delete(Class<T> entityClass, String id);
    <T extends Entity> void delete(Class<T> entityClass, List<String> ids);
    <T extends Entity> void delete(T entity);

    <T> List<T> findAll(Class<T> entityClass, String statement, Object parameters);
    <T> void delete(Class<T> entityClass, String statement, Object parameters);
}
