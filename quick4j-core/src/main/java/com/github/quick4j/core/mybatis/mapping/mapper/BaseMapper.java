package com.github.quick4j.core.mybatis.mapping.mapper;

import com.github.quick4j.core.mybatis.mapping.builder.SqlBuilder;
import com.github.quick4j.core.repository.mybatis.support.Sort;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author zhaojh.
 */
public interface BaseMapper<T> {

  @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_SELECT_BY_ID_SQL)
  T selectById(@Param("type") Class<T> entityClass, @Param("id") String id);

  @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_SELECT_BY_IDS_SQL)
  List<T> selectByIds(@Param("type") Class<T> entityClass, @Param("ids") String[] ids,
                      @Param("sort") Sort sort);

  @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_SELECT_LIST_SQL)
  List<T> selectPaging(@Param("type") Class<T> entityClass, @Param("parameters") Object parameter,
                       @Param("sort") Sort sort, RowBounds rowBounds);

  @SelectProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_SELECT_LIST_SQL)
  List<T> selectList(@Param("type") Class<T> entityClass, @Param("parameters") Object parameter,
                     @Param("sort") Sort sort);

  @InsertProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_INSERT_SQL)
  void insert(T entity);

  @UpdateProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_UPDATE_BY_ID_SQL)
  void updateById(T entity);

  @DeleteProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_DELETE_BY_ID_SQL)
  void deleteById(@Param("type") Class<T> entityClass, @Param("id") String id);

  @DeleteProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_DELETE_BY_IDS_SQL)
  void deleteByIds(@Param("type") Class<T> entityClass, @Param("ids") String[] ids);

  @DeleteProvider(type = SqlBuilder.class, method = SqlBuilder.BUILD_DELETE_BY_PARAMETERS)
  void deleteByParameters(T entity);
}
