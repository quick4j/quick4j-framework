package com.github.quick4j.plugin.datagrid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;

/**
 * @author zhaojh
 */
public abstract class AbstractDataGrid implements DataGrid {
    private String name;
    private String entity;
    private Toolbar toolbar;

    protected AbstractDataGrid(String name, String entity) {
        this.name = name;
        this.entity = entity;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public String getEntity() {
        return entity;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
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
