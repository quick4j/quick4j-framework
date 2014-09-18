package com.github.quick4j.plugin.datagrid;

import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.PageRequest;

import java.util.Map;

/**
 * @author zhaojh
 */
public interface DataGridPostProcessor {
    String getName();
    DataPaging process(DataPaging dataPaging, PageRequest<Map<String, Object>> pageRequest) throws DataGridPostProcessException;
}
