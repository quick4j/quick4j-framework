package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.core.service.PagingCriteria;

/**
 * @author zhaojh
 */
public class MyBatisPagingCriteria <T extends Entity, P> extends MyBatisCriteria<T, P> implements PagingCriteria<T, P>{
    private Class<T> clazz;

    MyBatisPagingCriteria(Class<T> clazz, MyBatisRepository myBatisRepository) {
        super(clazz, myBatisRepository);
        this.clazz = clazz;
    }

    @Override
    public DataPaging<T> findAll(Pageable<P> pageable) {
        return getMyBatisRepository().findAll(clazz, pageable);
    }
}
