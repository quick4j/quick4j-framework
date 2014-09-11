package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.core.service.Criteria;

import java.util.List;

/**
 * @author zhaojh
 */
public class MyBatisCriteria<T extends Entity, P> implements Criteria<T, P>{
    private Class<T> entity;
    private MyBatisRepository myBatisRepository;

    MyBatisCriteria(Class<T> entity, MyBatisRepository myBatisCrudRepository) {
        this.entity = entity;
        this.myBatisRepository = myBatisCrudRepository;
    }

    @Override
    public T findOne(String id) {
        return myBatisRepository.findOne(entity, id);
    }

    @Override
    public List<T> findAll() {
        return myBatisRepository.findAll(entity);
    }

    @Override
    public List<T> findAll(P parameters) {
        return myBatisRepository.findAll(entity, parameters);
    }

    @Override
    public List<T> findAll(List<String> ids) {
        return myBatisRepository.findAll(entity, ids);
    }

    @Override
    public List<T> findAll(String statementShortName, P paramerters) {
        return myBatisRepository.findAll(entity, statementShortName, paramerters);
    }

    @Override
    public void delete(String id) {
        myBatisRepository.delete(entity, id);
    }

    @Override
    public void delete(String[] ids) {
        myBatisRepository.delete(entity, ids);
    }

    protected MyBatisRepository getMyBatisRepository(){
        return myBatisRepository;
    }
}
