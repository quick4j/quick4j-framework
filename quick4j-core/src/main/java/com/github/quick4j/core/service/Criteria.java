package com.github.quick4j.core.service;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.MybatisRepository;
import com.github.quick4j.core.repository.mybatis.support.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojh.
 */
public class Criteria<T extends BaseEntity> {

  private Class<T> entityClass;
  private MybatisRepository mybatisRepository;

  Criteria(Class<T> entityClass,
           MybatisRepository mybatisRepository) {
    this.entityClass = entityClass;
    this.mybatisRepository = mybatisRepository;
  }


  public T selectOne(String id) {
    return mybatisRepository.selectById(entityClass, id);
  }

  public List<T> selectList(Map<String, Object> params) {
    return mybatisRepository.selectList(entityClass, params);
  }

  public List<T> selectList(Map<String, Object> params, Sort sort) {
    return mybatisRepository.selectList(entityClass, params, sort);
  }

  public List<T> selectList(T paramsTemplate) {
    return mybatisRepository.selectList(entityClass, paramsTemplate);
  }

  public List<T> selectList(T paramsTemplate, Sort sort) {
    return mybatisRepository.selectList(entityClass, paramsTemplate, sort);
  }

  public List<T> selectList(String[] ids) {
    return mybatisRepository.selectByIds(entityClass, ids);
  }

  public List<T> selectList(String[] ids, Sort sort) {
    return mybatisRepository.selectByIds(entityClass, ids, sort);
  }

  public List<T> selectList() {
    return mybatisRepository.selectList(entityClass, Collections.emptyMap());
  }

  public List<T> selectList(Sort sort) {
    return mybatisRepository.selectList(entityClass, Collections.emptyMap(), sort);
  }

  public DataPaging<T> selectList(Pageable pageable) {
    return mybatisRepository.selectPaging(entityClass, pageable);
  }

  public void delete(String id) {
    mybatisRepository.deleteById(entityClass, id);
  }

  public void delete(String[] ids) {
    mybatisRepository.deleteByIds(entityClass, ids);
  }

  public void delete(T entity) {
    mybatisRepository.deleteByParams(entity);
  }

  public void delete(List<T> entities) {
    if (null == entities || entities.isEmpty()) {
      return;
    }

    List<String> ids = new ArrayList<String>();
    for (T entity : entities) {
      ids.add(entity.getId());
    }
    mybatisRepository.deleteByIds(entityClass, ids.toArray(new String[]{}));
  }

  public void delete(String statement, Object parameters) {
    mybatisRepository.delete(entityClass, statement, parameters);
  }
}
