package com.github.quick4j.plugin.datagrid;

import com.github.quick4j.plugin.datagrid.meta.Header;
import com.github.quick4j.plugin.datagrid.meta.Toolbar;

import java.util.List;

/**
 * @author zhaojh
 */
public interface DataGrid{
    String getName();
    String getEntity();
    List<Header> getColumns();
    List<Header> getFrozenColumns();
    Toolbar getToolbar();
    DataGrid copySelf();
}
