package com.github.quick4j.core.repository.mybatis;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.core.mybatis.mapping.builder.MappedStatementAssistant;
import com.github.quick4j.core.mybatis.mapping.builder.MapperAssistant;
import com.github.quick4j.core.mybatis.mapping.builder.SqlBuilder;
import com.github.quick4j.core.mybatis.mapping.mapper.BaseMapper;
import com.github.quick4j.core.mybatis.paging.PaginationInterceptor;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import com.github.quick4j.core.util.UUID;

import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author zhaojh.
 */
@Repository
public class MybatisRepository {

  private static final Logger logger = LoggerFactory.getLogger(MybatisRepository.class);

  @Resource
  private SqlSessionTemplate sqlSessionTemplate;
  private MapperAssistant mapperAssistant;

  public SqlSessionTemplate getSqlSessionTemplate() {
    return sqlSessionTemplate;
  }

  @Resource
  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
    this.mapperAssistant = new MapperAssistant(this.sqlSessionTemplate);
  }

  public <T extends BaseEntity> void insert(T entity) {
    if (null == entity) {
      return;
    }

    Class entityClass = entity.getClass();
    String statementName = String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.INSERT);

    if (entity.isNew()) {
      entity.setId(UUID.getUUID32());
    }

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)) {
      sqlSessionTemplate.insert(statementName, entity);
      return;
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    mapper.insert(entity);
  }

  public <T extends BaseEntity> void insert(List<T> entities) {
    if (null == entities || entities.isEmpty()) {
      return;
    }

    if (entities.size() > 50) {
      throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");
    }

    for (T entity : entities) {
      insert(entity);
    }
  }

  public <T extends BaseEntity> T selectById(Class<T> entityClass, String id) {
    String statementName = String.format("%sMapper.%s", entityClass.getName(),
                                         SqlBuilder.SELECT_BY_ID);
    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      return sqlSessionTemplate.selectOne(statementName, id);
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    return mapper.selectById(entityClass, id);
  }

  public <T extends BaseEntity> List<T> selectByIds(Class<T> entityClass, String[] ids, Sort sort) {
    String entityClassName = entityClass.getName();
    String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_BY_IDS);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      return sqlSessionTemplate.selectList(statementName, ids);
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    return mapper.selectByIds(entityClass, ids, sort);
  }

  public <T extends BaseEntity> List<T> selectByIds(Class<T> entityClass, String[] ids) {
    return selectByIds(entityClass, ids, null);
  }

  public <T extends BaseEntity> List<T> selectList(Class<T> entityClass, Object parameters,
                                                   Sort sort) {
    String entityClassName = entityClass.getName();
    String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_LIST);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      return sqlSessionTemplate.selectList(statementName, parameters);
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    return mapper.selectList(entityClass, parameters, sort);
  }

  public <T extends BaseEntity> List<T> selectList(Class<T> entityClass, Object parameters) {
    return selectList(entityClass, parameters, null);
  }

  public <T> List<T> selectList(Class<T> entityClass, String statement, Object parameters) {
    String statementId = String.format("%sMapper.%s", entityClass.getName(), statement);
    return sqlSessionTemplate.selectList(statementId, parameters);
  }

  public <T extends BaseEntity> DataPaging<T> selectPaging(Class<T> entityClass,
                                                           Pageable pageable) {
    String entityClassName = entityClass.getName();
    String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_PAGING);
    List<T> rows = null;

    RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getLimit());
    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      rows = sqlSessionTemplate.selectList(statementName, pageable.getParameters(), rowBounds);
    } else {
      BaseMapper<T> mapper = getEntityMapper(entityClass);
      rows =
          mapper.selectPaging(entityClass, pageable.getParameters(), pageable.getSort(), rowBounds);
    }

    int total = rows.size();
    if (pageable.getOffset() != 0 || pageable.getLimit() != Integer.MAX_VALUE) {
      total = PaginationInterceptor.getPaginationTotal();
      PaginationInterceptor.clean();
    }
    return new DataPaging<T>(rows, total);
  }

  public <T extends BaseEntity> void updateById(T entity) {
    if (null == entity) {
      return;
    }

    Class entityClass = entity.getClass();
    String
        statementName =
        String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.UPDATE_BY_ID);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)) {
      sqlSessionTemplate.update(statementName, entity);
      return;
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    mapper.updateById(entity);
  }

  public <T extends BaseEntity> void updateById(List<T> entities) {
    if (null == entities || entities.isEmpty()) {
      return;
    }

    if (entities.size() > 50) {
      throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");
    }

    for (T entity : entities) {
      updateById(entity);
    }
  }

  public <T extends BaseEntity> void deleteById(Class<T> entityClass, String id) {
    String entityClassName = entityClass.getName();
    String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.DELETE_BY_ID);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      sqlSessionTemplate.delete(statementName, id);
      return;
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    mapper.deleteById(entityClass, id);
  }

  public <T extends BaseEntity> void deleteByIds(Class<T> entityClass, String[] ids) {
    String entityClassName = entityClass.getName();
    String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.DELETE_BY_IDS);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      sqlSessionTemplate.delete(statementName, ids);
      return;
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    mapper.deleteByIds(entityClass, ids);
  }

  public <T extends BaseEntity> void deleteByParams(T entity) {
    if (null == entity) {
      return;
    }

    Class entityClass = entity.getClass();
    String entityClassName = entityClass.getName();
    String
        statementName =
        String.format("%sMapper.%s", entityClassName, SqlBuilder.DELETE_BY_PARAMETERS);

    if (MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
        && isNotProviderSqlSource(statementName)) {
      sqlSessionTemplate.delete(statementName, entity);
      return;
    }

    BaseMapper<T> mapper = getEntityMapper(entityClass);
    mapper.deleteByParameters(entity);
  }

  public <T> void delete(Class<T> entityClass, String statement, Object parameters) {
    String statementId = String.format("%sMapper.%s", entityClass.getName(), statement);
    sqlSessionTemplate.delete(statementId, parameters);
  }

  private BaseMapper getEntityMapper(Class entityClass) {
    String mapperName = String.format("%sMapper", entityClass.getName());
    Class mapperClass = null;
    if (mapperAssistant.hasMapper(mapperName)) {
      mapperClass = mapperAssistant.getMapper(mapperName);
    } else {
      String aliasName = String.format("%sMapper-InLine", entityClass.getName());
      mapperClass = mapperAssistant.buildAndRegistMapper(aliasName, BaseMapper.class);
    }

    return (BaseMapper) sqlSessionTemplate.getMapper(mapperClass);
  }

  private boolean isNotProviderSqlSource(String statementName) {
    return !(MappedStatementAssistant.getMappedStatement(statementName, sqlSessionTemplate)
                 .getSqlSource() instanceof ProviderSqlSource);
  }
}
