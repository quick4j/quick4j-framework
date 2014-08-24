package com.github.quick4j.core.service.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import com.github.quick4j.core.repository.MyBatisCrudRepository;
import com.github.quick4j.core.service.PagingCriteria;

/**
 * @author zhaojh
 */
public class MyBatisPagingCriteria <T extends Entity, P> extends MyBatisCriteria<T, P> implements PagingCriteria<T, P>{
    private Class<T> entity;

    MyBatisPagingCriteria(Class<T> entity, MyBatisCrudRepository<T, P> myBatisCrudRepository) {
        super(entity, myBatisCrudRepository);
        this.entity = entity;
    }

    @Override
    public DataPaging<T> list(Pageable<P> pageable) {
        return getMyBatisCrudRepository().findAll(entity, pageable);
    }
}
