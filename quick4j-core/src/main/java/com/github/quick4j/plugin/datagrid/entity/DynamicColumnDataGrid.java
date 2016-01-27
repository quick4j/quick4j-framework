package com.github.quick4j.plugin.datagrid.entity;


import com.github.quick4j.core.repository.mybatis.MybatisRepository;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
public abstract class DynamicColumnDataGrid extends AbstractDataGrid {

  @Resource
  private MybatisRepository mybatisRepository;

  protected DynamicColumnDataGrid(String name, Class entity) {
    super(name, entity);
  }

  protected MybatisRepository getRepository() {
    return mybatisRepository;
  }
}
