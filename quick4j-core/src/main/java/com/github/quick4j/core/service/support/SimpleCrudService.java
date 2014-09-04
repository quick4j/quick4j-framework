package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.service.PagingCriteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Service
public class SimpleCrudService<T extends Entity, P> implements CrudService<T, P>{
    @Resource
    private MyBatisRepository mybatisRepository;

    protected MyBatisRepository getCrudRepository() {
        return mybatisRepository;
    }

    @Override
    public Criteria<T, P> createCriteria(Class<T> clazz) {
        return new MyBatisCriteria<T, P>(clazz, mybatisRepository);
    }

    @Override
    public PagingCriteria<T, P> createPagingCriteria(Class<T> clazz) {
        return new MyBatisPagingCriteria<T, P>(clazz, mybatisRepository);
    }

    @Override
    public T save(T entity) {
        if(entity.isNew()){
            return insert(entity);
        }else{
            return update(entity);
        }
    }

    @Override
    public List<T> save(List<T> entities) {
        List<T> inserting = new ArrayList<T>();
        List<T> updating = new ArrayList<T>();

        for(T entity : entities){
            if(entity.isNew()){
                inserting.add(entity);
            }else{
                updating.add(entity);
            }
        }

        insert(inserting);
        update(updating);

        return entities;
    }

    private T insert(T entity){
        mybatisRepository.insert(entity);

        List<Entity> slaveList = (List<Entity>) entity.getSlave();
        if(null != slaveList){
            for (Entity slave : slaveList){
                mybatisRepository.insert(slave);
            }
        }

        return (T) mybatisRepository.findOne(entity.getClass(), entity.getId());
    }

    private void insert(List<T> entities){
        mybatisRepository.insert(entities);
    }

    public T update(T entity){
        mybatisRepository.update(entity);
        return (T) mybatisRepository.findOne(entity.getClass(), entity.getId());
    }

    private void update(List<T> entities){
        mybatisRepository.update(entities);
    }

//    protected Class getGenericType(){
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        return (Class) params[0];
//    }
}
