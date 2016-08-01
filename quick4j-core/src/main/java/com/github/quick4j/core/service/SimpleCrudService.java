package com.github.quick4j.core.service;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.core.repository.mybatis.MybatisRepository;

import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * @author zhaojh.
 */
@Service
public class SimpleCrudService<T extends BaseEntity> {

  @Resource
  private MybatisRepository mybatisRepository;

  public <P extends BaseEntity> Criteria<P> newCriteria(Class<P> entityClass) {
    return new Criteria<P>(entityClass, mybatisRepository);
  }

  public T save(T entity) {
    if (entity.isNew()) {
      return insert(entity);
    } else {
      return update(entity);
    }
  }

  public List<T> save(List<T> entities) {
    List<T> inserting = new ArrayList<T>();
    List<T> updating = new ArrayList<T>();

    for (T entity : entities) {
      if (entity.isNew()) {
        inserting.add(entity);
      } else {
        updating.add(entity);
      }
    }

    insert(inserting);
    update(updating);

    return entities;
  }

  protected MybatisRepository getMybatisRepository() {
    return mybatisRepository;
  }

  protected T insert(T entity) {
    mybatisRepository.insert(entity);

    List<BaseEntity> slaveList = (List<BaseEntity>) entity.getSlave();
    if (null != slaveList) {
      for (BaseEntity slave : slaveList) {
        slave.setMasterId(entity.getId());
        mybatisRepository.insert(slave);
      }
    }

    return (T) mybatisRepository.selectById(entity.getClass(), entity.getId());
  }

  protected void insert(List<T> entities) {
    mybatisRepository.insert(entities);
  }

  protected T update(T entity) {
    mybatisRepository.updateById(entity);
    return (T) mybatisRepository.selectById(entity.getClass(), entity.getId());
  }

  protected void update(List<T> entities) {
    mybatisRepository.updateById(entities);
  }

//  private Class getGenericType() {
//    Type genType = getClass().getGenericSuperclass();
//    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//    System.out.println("===>" + ((Class) params[0]).getName());
//    return (Class) params[0];
//  }
}
