package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.repository.MyBatisCrudRepository;
import com.github.quick4j.core.service.Criteria;
import com.github.quick4j.core.service.CrudService;
import com.github.quick4j.core.service.PagingCriteria;
import com.github.quick4j.core.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Service
public class SimpleCrudService<T extends Entity, P> implements CrudService<T, P>{
    private static final Logger logger = LoggerFactory.getLogger(SimpleCrudService.class);

    @Resource
    private MyBatisCrudRepository<T, P> myBatisCrudRepository;

    @Override
    public Criteria<T, P> createCriteria(Class<T> clazz) {
        return new MyBatisCriteria<T, P>(clazz, myBatisCrudRepository);
    }

    @Override
    public PagingCriteria<T, P> createPagingCriteria(Class<T> clazz) {
        return new MyBatisPagingCriteria<T, P>(clazz, myBatisCrudRepository);
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
                entity.setId(UUIDGenerator.generate32RandomUUID());
                inserting.add(entity);
            }else{
                updating.add(entity);
            }
        }

        insert(inserting);
        update(updating);

        return entities;
    }

    @Override
    public void delete(T entity) {
        myBatisCrudRepository.delete((Class<T>) entity.getClass(), entity.getId());
    }

    protected MyBatisCrudRepository<T, P> getMyBatisCrudRepository() {
        return myBatisCrudRepository;
    }


    private T insert(T entity){
        entity.setId(UUIDGenerator.generate32RandomUUID());
        myBatisCrudRepository.insert(entity);
        return myBatisCrudRepository.findOne((Class<T>) entity.getClass(), entity.getId());
    }

    private void insert(List<T> entities){
        myBatisCrudRepository.insert(entities);
    }

    private T update(T entity){
        myBatisCrudRepository.update(entity);
        return myBatisCrudRepository.findOne((Class<T>) entity.getClass(), entity.getId());
    }

    private void update(List<T> entities){
        myBatisCrudRepository.update(entities);
    }

//    protected Class getGenericType(){
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        return (Class) params[0];
//    }
}
