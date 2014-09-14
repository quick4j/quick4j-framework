package com.github.quick4j.plugin.datagrid;

import java.util.List;

/**
 * @author zhaojh
 */
public interface DataGridPostProcessor {
    String getName();
    void process(List rows) throws DataGridPostProcessException;
}
