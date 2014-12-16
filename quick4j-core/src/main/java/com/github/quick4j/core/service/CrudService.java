package com.github.quick4j.core.service;

import com.github.quick4j.core.entity.Entity;

import java.util.List;

/**
 * @author zhaojh
 */
public interface CrudService<T> {
    /**
     *
     * @param entityClass
     * @return Criteria
     */
    <P extends Entity> Criteria<P> createCriteria(Class<P> entityClass);

    /**
     * insert and update
     * @param entity
     * @return Entity
     */
    T save(T entity);

    /**
     * insert and update
     * @param entites
     * @return entites
     */
    List<T> save(List<T> entites);
}
