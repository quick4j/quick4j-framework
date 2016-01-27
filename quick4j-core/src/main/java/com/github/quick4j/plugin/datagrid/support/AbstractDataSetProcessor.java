package com.github.quick4j.plugin.datagrid.support;

import com.github.quick4j.core.repository.mybatis.MybatisRepository;
import com.github.quick4j.plugin.datagrid.DataSetProcessor;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
public abstract class AbstractDataSetProcessor implements DataSetProcessor {

  @Resource
  private MybatisRepository mybatisRepository;

  protected MybatisRepository getRepository() {
    return mybatisRepository;
  }
}
