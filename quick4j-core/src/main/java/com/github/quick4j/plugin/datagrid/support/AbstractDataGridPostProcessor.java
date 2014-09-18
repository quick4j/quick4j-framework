package com.github.quick4j.plugin.datagrid.support;

import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.plugin.datagrid.DataGridPostProcessor;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
public abstract class AbstractDataGridPostProcessor implements DataGridPostProcessor{
    @Resource
    private MyBatisRepository myBatisRepository;

    protected MyBatisRepository getMyBatisRepository() {
        return myBatisRepository;
    }
}
