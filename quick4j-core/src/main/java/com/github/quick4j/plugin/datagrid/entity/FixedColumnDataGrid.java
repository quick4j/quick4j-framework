package com.github.quick4j.plugin.datagrid.entity;

import com.github.quick4j.plugin.datagrid.DataGrid;
import com.github.quick4j.plugin.datagrid.meta.Column;
import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;
import com.github.quick4j.plugin.datagrid.meta.Toolbutton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
public class FixedColumnDataGrid extends AbstractDataGrid{
    private List<Header> normalHeaderContainer;
    private List<Header> frozenHeaderContainer;

    public FixedColumnDataGrid(String name, String entity){
        super(name, entity);
        this.normalHeaderContainer = new ArrayList<Header>();
        this.frozenHeaderContainer = new ArrayList<Header>();
    }

    @Override
    public List<Header> getColumns() {
        return normalHeaderContainer;
    }

    @Override
    public List<Header> getFrozenColumns() {
        return frozenHeaderContainer;
    }

    public Header newHeader(){
        Header header = new Header();
        normalHeaderContainer.add(header);
        return header;
    }

    public Header newFrozenHeader(){
        Header header = new Header();
        frozenHeaderContainer.add(header);
        return header;
    }

    public void removeColumn(String field){
        for (Header header : frozenHeaderContainer){
            if(header.removeColumn(field)) break;
        }

        for (Header header : normalHeaderContainer){
            if(header.removeColumn(field)) break;
        }
    }

    public void removeColumns(String[] fields){
        for (Header header : frozenHeaderContainer){
            header.removeColumns(fields);
        }

        for (Header header : normalHeaderContainer){
            header.removeColumns(fields);
        }
    }

    @Override
    public DataGrid copySelf(){
        try{
            FixedColumnDataGrid dataGrid = new FixedColumnDataGrid(getName(), getEntity());

            //frozen columns
            for (Header frozenHeader : dataGrid.getFrozenColumns()){
                Header header = dataGrid.newFrozenHeader();
                for(Column column : frozenHeader){
                    header.addColumn(column.clone());
                }
            }

            //normal columns
            for (Header normalHeader : dataGrid.getColumns()){
                Header header = dataGrid.newHeader();
                for(Column column : normalHeader){
                    header.addColumn(column.clone());
                }
            }

            //toolbar
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
