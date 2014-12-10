package com.github.quick4j.plugin.datagrid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.DataGridPostProcessor;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;

/**
 * @author zhaojh
 */
public abstract class AbstractDataGrid implements DataGrid {
    private String name;
    private Class entityClass;
    private Toolbar toolbar;
    private DataGridPostProcessor postProcessor;

    protected AbstractDataGrid(String name, Class entityClass) {
        this.name = name;
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @JsonIgnore
    public Class getEntity() {
        return entityClass;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void setPostProcessor(DataGridPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    @JsonIgnore
    public DataGridPostProcessor getPostProcessor() {
        return postProcessor;
    }

    @Override
    public boolean isSupportPostProcess() {
        return null != postProcessor;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public Toolbar newToolbar(){
        if(!isExistToolbar()){
            toolbar = new Toolbar();
        }
        return toolbar;
    }

    protected boolean isExistToolbar(){
        return toolbar != null;
    }

//    @Override
//    public List<Header> getColumns() {
//        return null;
//    }
//
//    @Override
//    public List<Header> getFrozenColumns() {
//        return null;
//    }
//
//    @Override
//    public DataGrid copySelf() {
//        return null;
//    }
}
