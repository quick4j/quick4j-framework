package com.github.quick4j.plugin.datagrid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.plugin.datagrid.meta.Column;
import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;
import com.github.quick4j.plugin.datagrid.meta.Toolbutton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
public class DataGrid implements Cloneable{
    private String name;
    private String dataModel;
    private List<Header> columns;
    private Toolbar toolbar;

    public DataGrid(String name, String dataModel) {
        this.name = name;
        this.dataModel = dataModel;
        columns = new ArrayList<Header>();
    }

    public DataGrid(String name, String dataModel, List<Header> columns, Toolbar toolbar) {
        this.name = name;
        this.dataModel = dataModel;
        this.columns = columns == null ? new ArrayList<Header>() : columns;
        this.toolbar = toolbar;
    }

    public String getName(){
        return name;
    }

    @JsonIgnore
    public String getDataModel() {
        return dataModel;
    }

    public List<Header> getColumns(){
        return columns;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public Header newHeader(){
        Header header = new Header();
        columns.add(header);
        return header;
    }

    public Toolbar newToolbar(){
        if(null == toolbar){
            toolbar = new Toolbar();
        }
        return toolbar;
    }

    @JsonIgnore
    public boolean isExistToolbar(){
        return null != toolbar;
    }

    public void removeColumn(String field){
        for (Header header : columns){
            if(header.removeColumn(field)) break;
        }
    }

    public void removeColumns(String[] fields){
        for (Header header : columns){
            header.removeColumns(fields);
        }
    }

    @Override
    public String toString() {
        return "DataGrid{" +
                "name='" + name + '\'' +
                ", columns=" + columns +
                ", toolbar=" + toolbar +
                '}';
    }

    @Override
    public DataGrid clone(){
        try{
            List<Header> headers = new ArrayList<Header>();
            for (Header header: columns){
                Header h = new Header();
                for (Column column : header){
                    h.add(column.clone());
                }
                headers.add(h);
            }

            Toolbar tbar = null;
            if(this.isExistToolbar()){
                tbar = new Toolbar();
                for (Toolbutton toolbutton : toolbar){
                    tbar.add(toolbutton.clone());
                }
            }

            return new DataGrid(this.getName(), this.getDataModel(), headers, tbar);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
