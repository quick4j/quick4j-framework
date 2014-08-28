package com.github.quick4j.plugin.datagrid.meta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaojh
 */
public class Header extends ArrayList<Column> {
    public Header addColumn(String title, String field){
        this.add(new Column(title, field));
        return this;
    }

    public Header addColumn(String title, String field, int width){
        this.add(new Column(title, field, width));
        return this;
    }

    public Column getColumn(String field){
        for(Column column : this){
            if(field.equals(column.getField())){
                return column;
            }
        }

        return null;
    }

    public boolean removeColumn(String field){
        Iterator<Column> iterator = this.iterator();
        while (iterator.hasNext()){
            Column column = iterator.next();
            if(column.getField().equals(field)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void removeColumns(String[] fields){
        Iterator<Column> iterator = this.iterator();
        List<String> removedFieldList = Arrays.asList(fields);
        while (iterator.hasNext()){
            Column column = iterator.next();
            if(removedFieldList.contains(column.getField())){
                iterator.remove();
            }
        }
    }
}
