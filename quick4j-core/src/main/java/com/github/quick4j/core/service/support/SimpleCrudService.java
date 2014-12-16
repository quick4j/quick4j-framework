package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Service
public class SimpleCrudService<T extends Entity> implements CrudService<T>{
    @Resource
    private Repository mybatisRepository;

    protected Repository getCrudRepository() {
        return mybatisRepository;
    }

    @Override
    public <P extends Entity> Criteria<P> createCriteria(Class<P> clazz) {
        return new DefaultCriteria<P>(clazz, mybatisRepository);
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
                slave.setMasterId(entity.getId());
                mybatisRepository.insert(slave);
            }
        }

        return (T) mybatisRepository.find(entity.getClass(), entity.getId());
    }

    private void insert(List<T> entities){
        mybatisRepository.insert(entities);
    }

    public T update(T entity){
        mybatisRepository.updateById(entity);
        return (T) mybatisRepository.find(entity.getClass(), entity.getId());
    }

    private void update(List<T> entities){
        mybatisRepository.updateById(entities);
    }

//    protected Class getGenericType(){
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        return (Class) params[0];
//    }
}
