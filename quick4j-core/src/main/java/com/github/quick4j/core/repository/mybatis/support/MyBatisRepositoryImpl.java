package com.github.quick4j.core.repository.mybatis.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.core.mybatis.interceptor.PaginationInterceptor;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import com.github.quick4j.core.repository.mybatis.MyBatisRepository;
import com.github.quick4j.core.util.UUIDGenerator;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 此类仅支持最大50（包含50）条数据的批处理，
 * 大量数据的批处理请使用JdbcTemplate处理。
 * 注意：因为MyBatis的批处理会产生一个新的数据库
 * 连接，影响Spring事务。
 *
 * @author zhaojh
 */
@Repository("myBatisCrudRepository")
public class MyBatisRepositoryImpl implements MyBatisRepository {
    public static final String SELECT_ONE_STATEMENT_ID = ".selectOne";
    public static final String SELECT_LIST_STATEMENT_ID = ".selectList";
    public static final String SELECT_LIST_BY_IDS_STATEMENT_ID = ".selectListByIds";
    public static final String SELECT_PAGING_STATEMENT_ID = ".selectPaging";
    public static final String INSERT_STATEMENT_ID = ".insert";
    public static final String UPDATE_ONE_STATEMENT_ID = ".updateOne";
    public static final String DELETE_ONE_STATEMENT_ID = ".deleteOne";
    public static final String DELETE_MANY_STATEMENT_ID = ".deleteMany";
    public static final String DELETE_MANY_BY_PARAMETER_STATEMENT_ID = ".deleteManyByParameter";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    public <T extends Entity> T findOne(Class<T> clazz, String id) {
        return sqlSessionTemplate.selectOne(getSelectOneStatementId(clazz), id);
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz) {
        return sqlSessionTemplate.selectList(getSelectListStatementId(clazz));
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz, List<String> ids) {
        if(null == ids || ids.isEmpty()) return new ArrayList<T>();
        return sqlSessionTemplate.selectList(getSelectListByIdsStatementId(clazz), ids);
    }

    @Override
    public <T extends Entity, P> List<T> findAll(Class<T> clazz, P parameter) {
        return sqlSessionTemplate.selectList(getSelectListStatementId(clazz), parameter);
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz, String statement, Object parameter) {
        return sqlSessionTemplate.selectList(getOtherStatementId(clazz, statement), parameter);
    }

    @Override
    public <T extends Entity> DataPaging<T> findAll(Class<T> clazz, Pageable pageable) {
        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getLimit());
        List<T> rows = sqlSessionTemplate.selectList(getSelectPagingStatementId(clazz), pageable.getParameters(), rowBounds);

        int total = rows.size();
        if(rowBounds.getOffset() != 0 || rowBounds.getLimit() != Integer.MAX_VALUE){
            total = PaginationInterceptor.getPaginationTotal();
            PaginationInterceptor.clean();
        }

        DataPaging<T> dataPaging = new DataPaging<T>(rows, total);
        return dataPaging;
    }

    @Override
    public <T> List<T> selectList(Class<? extends Entity> clazz, String statement, Object parameter) {
        return sqlSessionTemplate.selectList(getOtherStatementId(clazz, statement), parameter);
    }

    @Override
    public <T extends Entity> void insert(T entity) {
        entity.setId(UUIDGenerator.generate32RandomUUID());
        sqlSessionTemplate.insert(getInsertStatementId(entity), entity);
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
    public <T extends Entity> void update(T entity) {
        sqlSessionTemplate.update(getUpdateOneStatementId(entity), entity);
    }

    @Override
    public <T extends Entity> void update(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        if(entities.size() > 50) throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");

        for (T entity : entities){
            update(entity);
        }
    }

    @Override
    public <T extends Entity> void delete(Class<T> clazz, String id) {
        sqlSessionTemplate.delete(getDeleteOneStatementId(clazz), id);
    }

    @Override
    public <T extends Entity> void delete(Class<T> clazz, String[] ids) {
        if(null == ids || ids.length == 0) return;
        sqlSessionTemplate.delete(getDeleteManyStatementId(clazz), Arrays.asList(ids));
    }

    @Override
    public <T extends Entity> void delete(T entity) {
        if(null == entity) return;
        sqlSessionTemplate.delete(getDeleteOneStatementId(entity), entity.getId());
    }

    @Override
    public <T extends Entity> void delete(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        List<String> ids = new ArrayList<String>();
        T target = null;
        for (T entity : entities){
            if(null == target){
                target = entity;
            }

            ids.add(entity.getId());
        }
        sqlSessionTemplate.delete(getDeleteManyStatementId(target), ids);
    }

    @Override
    public <T extends Entity> void delete(Class<T> clazz, Object parameter) {
        sqlSessionTemplate.delete(getDeleteManyByParameterStatementId(clazz), parameter);
    }

    @Override
    public void delete(Class<? extends Entity> clazz, String statement, Object parameter) {
        sqlSessionTemplate.delete(getOtherStatementId(clazz, statement), parameter);
    }

    protected String getMapperNamespace(Class clazz){
        MapperNamespace mapperNamespace = (MapperNamespace) clazz.getAnnotation(MapperNamespace.class);
        if(null != mapperNamespace){
            return mapperNamespace.value();
        }else{
            throw new NotFoundException("entity.mappernamespace.notfound", new Object[]{clazz.getName()});
        }
    }

    private String getSelectOneStatementId(Class clazz){
        return getMapperNamespace(clazz) + SELECT_ONE_STATEMENT_ID;
    }

    private String getSelectListStatementId(Class clazz){
        return getMapperNamespace(clazz) + SELECT_LIST_STATEMENT_ID;
    }

    private String getSelectPagingStatementId(Class clazz){
        return getMapperNamespace(clazz) + SELECT_PAGING_STATEMENT_ID;
    }

    private String getInsertStatementId(Entity entity){
        return entity.getMapperNamespace() + INSERT_STATEMENT_ID;
    }

    private String getUpdateOneStatementId(Entity entity){
        return entity.getMapperNamespace() + UPDATE_ONE_STATEMENT_ID;
    }

    private String getDeleteOneStatementId(Class clazz){
        return getMapperNamespace(clazz) + DELETE_ONE_STATEMENT_ID;
    }

    private String getDeleteOneStatementId(Entity entity){
        return entity.getMapperNamespace() + DELETE_ONE_STATEMENT_ID;
    }

    private String getDeleteManyStatementId(Class clazz){
        return getMapperNamespace(clazz) + DELETE_MANY_STATEMENT_ID;
    }

    private String getDeleteManyStatementId(Entity entity){
        return entity.getMapperNamespace() + DELETE_MANY_STATEMENT_ID;
    }

    private String getDeleteManyByParameterStatementId(Class clazz){
        return getMapperNamespace(clazz) + DELETE_MANY_BY_PARAMETER_STATEMENT_ID;
    }

    private String getOtherStatementId(Class clazz, String statementShortName){
        if(statementShortName.startsWith("\\.")){
            return getMapperNamespace(clazz) + statementShortName;
        }else{
            return getMapperNamespace(clazz) + '.' + statementShortName;
        }

    }

    private String getSelectListByIdsStatementId(Class clazz){
        return getMapperNamespace(clazz) + SELECT_LIST_BY_IDS_STATEMENT_ID;
    }
}
