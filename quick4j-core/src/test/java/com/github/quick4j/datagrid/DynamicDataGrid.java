package com.github.quick4j.datagrid;

import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.entity.Action;
import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.entity.DynamicColumnDataGrid;
import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;
import com.github.quick4j.plugin.datagrid.meta.Toolbutton;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
//@Component
public class DynamicDataGrid extends DynamicColumnDataGrid {
    @Resource
    private Repository repository;

    public DynamicDataGrid() {
        super("dynamic", "com.github.quick4j.entity.Path");
        newToolbar().addToolbutton("Add", "icon-add", "doAdd");
        repository = getRepository();
    }

    public DynamicDataGrid(String name, String entity, Repository repository) {
        super(name, entity);
        this.repository = repository;
    }

    @Override
    public List<Header> getColumns() {
        List<Action> list = repository.findAll(Action.class);
        List<Header> columns = new ArrayList<Header>();
        Header header = new Header();
        columns.add(header);
        for(Action action : list){
            header.addColumn(action.getName(), action.getCode(), 100);
        }

        return columns;
    }

    @Override
    public List<Header> getFrozenColumns() {
        return null;
    }

    @Override
    public DataGrid copySelf(){
        try{
            DynamicDataGrid dataGrid = new DynamicDataGrid(getName(), getEntity(), getRepository());

            if(isExistToolbar()){
                Toolbar toolbar = dataGrid.newToolbar();
                for (Toolbutton toolbutton : getToolbar()){
                    toolbar.add(toolbutton.clone());
                }
            }

            return dataGrid;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
