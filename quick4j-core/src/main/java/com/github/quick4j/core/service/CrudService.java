package com.github.quick4j.core.service;

import java.util.List;

/**
 * @author zhaojh
 */
public interface CrudService<T, P> {
    /**
     *
     * @param clazz
     * @return Criteria
     */
    Criteria<T, P> createCriteria(Class<T> clazz);

    /**
     *
     * @param clazz
     * @return Criteria
     */
    PagingCriteria<T, P> createPagingCriteria(Class<T> clazz);

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
