package com.github.quick4j.plugin.datagrid.entity;

import com.github.quick4j.core.repository.mybatis.Repository;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
public abstract class DynamicColumnDataGrid extends AbstractDataGrid{
    @Resource
    private Repository repository;

    protected DynamicColumnDataGrid(String name, String entity) {
        super(name, entity);
    }

    protected Repository getRepository(){
        return repository;
    }
}
