package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.MyBatisCrudRepository;
import com.github.quick4j.core.service.Criteria;

import java.util.List;

/**
 * @author zhaojh
 */
public class MyBatisCriteria<T extends Entity, P> implements Criteria<T, P>{
    private Class<T> entity;
    private MyBatisCrudRepository<T, P> myBatisCrudRepository;

    MyBatisCriteria(Class<T> entity, MyBatisCrudRepository<T, P> myBatisCrudRepository) {
        this.entity = entity;
        this.myBatisCrudRepository = myBatisCrudRepository;
    }

    @Override
    public T findOne(String id) {
        return myBatisCrudRepository.findOne(entity, id);
    }

    @Override
    public List<T> list() {
        return myBatisCrudRepository.findAll(entity);
    }

    @Override
    public List<T> list(P parameters) {
        return myBatisCrudRepository.findAll(entity, parameters);
    }

    @Override
    public void delete(String id) {
        myBatisCrudRepository.delete(entity, id);
    }

    @Override
    public void delete(String[] ids) {
        myBatisCrudRepository.delete(entity, ids);
    }

    protected MyBatisCrudRepository<T, P> getMyBatisCrudRepository(){
        return myBatisCrudRepository;
    }
}
