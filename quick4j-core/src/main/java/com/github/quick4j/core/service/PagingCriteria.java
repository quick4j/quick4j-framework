package com.github.quick4j.core.service;

import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;

/**
 * @author zhaojh
 */
public interface PagingCriteria<T, P> extends Criteria<T, P>{
    DataPaging<T> findAll(Pageable<P> pageable);
}
