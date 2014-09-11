package com.github.quick4j.plugin.datagrid.entity;

import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;
import com.github.quick4j.plugin.datagrid.meta.Toolbutton;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaojh
 */
public abstract class DynamicColumnDataGrid extends AbstractDataGrid{
    @Resource
    private MyBatisRepository myBatisRepository;

    protected DynamicColumnDataGrid(String name, String entity) {
        super(name, entity);
    }

    protected MyBatisRepository getMyBatisRepository(){
        return myBatisRepository;
    }
}
