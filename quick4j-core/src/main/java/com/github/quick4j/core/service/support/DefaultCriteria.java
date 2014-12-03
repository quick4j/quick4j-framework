package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.core.service.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhaojh
 */
public class DefaultCriteria<T extends Entity> implements Criteria<T>{
    private Class<T> entityClass;
    private Repository repository;

    DefaultCriteria(Class<T> clazz, Repository myBatisCrudRepository) {
        this.entityClass = clazz;
        this.repository = myBatisCrudRepository;
    }

    @Override
    public T findOne(String id) {
        return repository.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return repository.findByParams(entityClass, null);
    }

    @Override
    public List<T> findAll(Object parameters) {
        return repository.findByParams(entityClass, parameters);
    }

    @Override
    public List<T> findAll(String[] ids) {
        return repository.findByIds(entityClass, ids);
    }

    @Override
    public List<T> findAll(String statementId, Object paramerters) {
        return repository.findAll(entityClass, statementId, paramerters);
    }

    @Override
    public DataPaging<T> findAll(Pageable pageable) {
        return repository.findPaging(entityClass, pageable);
    }

    @Override
    public void delete(String id) {
        repository.delete(entityClass, id);
    }

    @Override
    public void delete(String[] ids) {
        repository.delete(entityClass, Arrays.asList(ids));
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(List<T> entities) {
        if (null == entities || entities.isEmpty()) return;

        List<String> ids = new ArrayList<String>();
        for (T entity : entities){
            ids.add(entity.getId());
        }
        repository.delete(entityClass, ids);
    }

    @Override
    public void delete(String statement, Object parameters) {
        repository.delete(entityClass, statement, parameters);
    }

    protected Repository getRepository(){
        return repository;
    }
}
