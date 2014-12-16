package com.github.quick4j.plugin.datagrid.support;

import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.plugin.datagrid.DataSetProcessor;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
public abstract class AbstractDataSetProcessor implements DataSetProcessor {
    @Resource
    private Repository repository;

    protected Repository getRepository() {
        return repository;
    }
}
