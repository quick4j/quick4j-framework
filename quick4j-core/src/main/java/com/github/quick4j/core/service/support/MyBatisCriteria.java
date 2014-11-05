package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.core.service.Criteria;

import java.util.List;

/**
 * @author zhaojh
 */
public class MyBatisCriteria<T extends Entity, P> implements Criteria<T, P>{
    private Class<T> clazz;
    private MyBatisRepository myBatisRepository;

    MyBatisCriteria(Class<T> clazz, MyBatisRepository myBatisCrudRepository) {
        this.clazz = clazz;
        this.myBatisRepository = myBatisCrudRepository;
    }

    @Override
    public T findOne(String id) {
        return myBatisRepository.findOne(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return myBatisRepository.findAll(clazz);
    }

    @Override
    public List<T> findAll(P parameters) {
        return myBatisRepository.findAll(clazz, parameters);
    }

    @Override
    public List<T> findAll(List<String> ids) {
        return myBatisRepository.findAll(clazz, ids);
    }

    @Override
    public List<T> findAll(String statementId, P paramerters) {
        return myBatisRepository.findAll(clazz, statementId, paramerters);
    }

    @Override
    public void delete(String id) {
        myBatisRepository.delete(clazz, id);
    }

    @Override
    public void delete(String[] ids) {
        myBatisRepository.delete(clazz, ids);
    }

    @Override
    public void delete(T entity) {
        myBatisRepository.delete(entity);
    }

    @Override
    public void delete(List<T> entities) {
        myBatisRepository.delete(entities);
    }

    @Override
    public void deleteByParameter(Object parameter) {
        myBatisRepository.delete(clazz, parameter);
    }

    protected MyBatisRepository getMyBatisRepository(){
        return myBatisRepository;
    }
}
