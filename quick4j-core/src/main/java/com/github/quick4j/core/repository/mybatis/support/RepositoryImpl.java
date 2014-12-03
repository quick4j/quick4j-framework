package com.github.quick4j.core.repository.mybatis.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.annotation.Mapper;
import com.github.quick4j.core.mybatis.mapping.builder.MappedStatementAssistant;
import com.github.quick4j.core.mybatis.mapping.builder.MapperAssistant;
import com.github.quick4j.core.mybatis.mapping.builder.SqlBuilder;
import com.github.quick4j.core.mybatis.mapping.mapper.BaseMapper;
import com.github.quick4j.core.mybatis.paging.PaginationInterceptor;
import com.github.quick4j.core.mybatis.paging.model.DataPaging;
import com.github.quick4j.core.mybatis.paging.model.Pageable;
import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.util.UUIDGenerator;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 此类仅支持最大50（包含50）条数据的批处理，
 * 大量数据的批处理请使用JdbcTemplate处理。
 * 注意：因为MyBatis的批处理会产生一个新的数据库
 * 连接，影响Spring事务。
 *
 * @author zhaojh
 */
@org.springframework.stereotype.Repository("myBatisCrudRepository")
public class RepositoryImpl implements Repository {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryImpl.class);

    private SqlSessionTemplate sqlSessionTemplate;
    private MapperAssistant mapperAssistant;

    @Resource
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.mapperAssistant = new MapperAssistant(this.sqlSessionTemplate);
    }

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    public <T extends Entity> T find(Class<T> entityClass, String id) {
        String statementName = String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.SELECT_BY_ID);
        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            return sqlSessionTemplate.selectOne(statementName, id);
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        return mapper.selectById(entityClass, id);
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> entityClass) {
        return findByParams(entityClass, null);
    }

    @Override
    public <T extends Entity> List<T> findByIds(Class<T> entityClass, String[] ids) {
        return findByIdsAndSorting(entityClass, ids, new Sort());
    }

    @Override
    public <T extends Entity> List<T> findByIdsAndSorting(Class<T> entityClass, String[] ids, Sort sort) {
        String entityClassName = entityClass.getName();
        String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_BY_IDS);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            return sqlSessionTemplate.selectList(statementName, ids);
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        return mapper.selectByIds(entityClass, ids, sort);
    }

    @Override
    public <T extends Entity> List<T> findByParams(Class<T> entityClass, Object parameters) {
        return findByParamsAndSorting(entityClass, parameters, new Sort());
    }

    @Override
    public <T extends Entity> List<T> findByParamsAndSorting(Class<T> entityClass, Object parameters, Sort sort) {
        String entityClassName = entityClass.getName();
        String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_LIST);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            return sqlSessionTemplate.selectList(statementName, parameters);
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        return mapper.selectList(entityClass, parameters, sort);
    }

    @Override
    public <T extends Entity> DataPaging<T> findPaging(Class<T> entityClass, Pageable pageable) {
        String entityClassName = entityClass.getName();
        String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.SELECT_PAGING);
        List<T> rows = null;

        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getLimit());
        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            rows = sqlSessionTemplate.selectList(statementName, pageable.getParameters(), rowBounds);
        }else{
            BaseMapper<T> mapper = getEntityMapperFor(entityClass);
            rows = mapper.selectPaging(entityClass, pageable.getParameters(), pageable.getSort(), rowBounds);
        }

        int total = rows.size();
        if(rowBounds.getOffset() != 0 || rowBounds.getLimit() != Integer.MAX_VALUE){
            total = PaginationInterceptor.getPaginationTotal();
            PaginationInterceptor.clean();
        }
        return new DataPaging<T>(rows, total);
    }

    @Override
    public <T extends Entity> void insert(T entity) {
        Class entityClass = entity.getClass();
        String statementName = String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.INSERT);

        if(entity.isNew()){
            entity.setId(UUIDGenerator.generate32RandomUUID());
        }

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)){
            sqlSessionTemplate.insert(statementName, entity);
            return;
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        mapper.insert(entity);
    }

    @Override
    public <T extends Entity> void insert(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        if(entities.size() > 50) throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");

        for(T entity : entities){
            insert(entity);
        }
    }

    @Override
    public <T extends Entity> void updateById(T entity) {
        Class entityClass = entity.getClass();
        String statementName = String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.UPDATE_BY_ID);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)){
            sqlSessionTemplate.update(statementName, entity);
            return;
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        mapper.updateById(entity);
    }

    @Override
    public <T extends Entity> void updateById(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        if(entities.size() > 50) throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");

        for (T entity : entities){
            updateById(entity);
        }
    }

    @Override
    public <T extends Entity> void delete(Class<T> entityClass, String id) {
        String entityClassName = entityClass.getName();
        String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.DELETE_BY_ID);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            sqlSessionTemplate.delete(statementName, id);
            return;
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        mapper.deleteById(entityClass, id);
    }

    @Override
    public <T extends Entity> void delete(Class<T> entityClass, List<String> ids) {
        String entityClassName = entityClass.getName();
        String statementName = String.format("%sMapper.%s", entityClassName, SqlBuilder.DELETE_BY_IDS);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)
                && isNotProviderSqlSource(statementName)){
            sqlSessionTemplate.delete(statementName, ids);
            return;
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        mapper.deleteByIds(entityClass, ids);
    }

    @Override
    public <T extends Entity> void delete(T entity) {
        Class entityClass = entity.getClass();
        String statementName = String.format("%sMapper.%s", entityClass.getName(), SqlBuilder.DELETE_BY_PARAMETERS);

        if(MappedStatementAssistant.hasStatementInSqlSession(statementName, sqlSessionTemplate)){
            sqlSessionTemplate.delete(statementName, entity);
            return;
        }

        BaseMapper<T> mapper = getEntityMapperFor(entityClass);
        mapper.deleteByParameters(entity);
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass, String statement, Object parameters) {
        String statementId = String.format("%sMapper.%s", entityClass.getName(), statement);
        return sqlSessionTemplate.selectList(statementId, parameters);
    }

    @Override
    public <T> void delete(Class<T> entityClass, String statement, Object parameters) {
        String statementId = String.format("%sMapper.%s", entityClass.getName(), statement);
        sqlSessionTemplate.delete(statementId, parameters);
    }

    private BaseMapper getEntityMapperFor(Class entityClass){
        String mapperName = String.format("%sMapper", entityClass.getName());
        Class mapperClass = null;
        if(mapperAssistant.hasMapper(mapperName)){
            mapperClass = mapperAssistant.getMapper(mapperName);
        }else{
            String aliasName = String.format("%sMapper-InLine", entityClass.getName());
            mapperClass = mapperAssistant.buildAndRegistMapper(aliasName, BaseMapper .class);
        }

        return (BaseMapper) sqlSessionTemplate.getMapper(mapperClass);
    }

    private boolean isNotProviderSqlSource(String statementName){
        return !(MappedStatementAssistant.getMappedStatement(statementName, sqlSessionTemplate).getSqlSource() instanceof ProviderSqlSource);
    }
}
