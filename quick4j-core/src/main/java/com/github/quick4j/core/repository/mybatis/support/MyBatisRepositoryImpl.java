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
import java.util.List;

/**
 * 此类仅支持最大50（包含50）条数据的批处理，
 * 大量数据的批处理请使用JdbcTemplate处理。
 * 注意MyBatis的批处理回产生一个新的数据库
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

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    @Override
    public <T extends Entity> T findOne(Class<T> clazz, String id) {
        return sqlSessionTemplate.selectOne(getSelectOneSql(clazz), id);
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz) {
        return sqlSessionTemplate.selectList(getSelectListSql(clazz));
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz, List<String> ids) {
        if(null == ids || ids.isEmpty()) return new ArrayList<T>();
        return sqlSessionTemplate.selectList(getSelectListByIdsSql(clazz), ids);
    }

    @Override
    public <T extends Entity, P> List<T> findAll(Class<T> clazz, P parameter) {
        return sqlSessionTemplate.selectList(getSelectListSql(clazz), parameter);
    }

    @Override
    public <T extends Entity> List<T> findAll(Class<T> clazz, String statement, Object parameter) {
        return sqlSessionTemplate.selectList(getOtherSql(clazz, statement), parameter);
    }

    @Override
    public <T extends Entity> DataPaging<T> findAll(Class<T> clazz, Pageable pageable) {
        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getLimit());
        List<T> rows = sqlSessionTemplate.selectList(getSelectPagingSql(clazz), pageable.getParameters(), rowBounds);
        int total = PaginationInterceptor.getPaginationTotal();
        PaginationInterceptor.clean();
        DataPaging<T> dataPaging = new DataPaging<T>(rows, total);
        return dataPaging;
    }

    @Override
    public <T> List<T> selectList(Class<? extends Entity> clazz, String statement, Object parameter) {
        return sqlSessionTemplate.selectList(getOtherSql(clazz, statement), parameter);
    }

    @Override
    public <T extends Entity> void insert(T entity) {
        entity.setId(UUIDGenerator.generate32RandomUUID());
        sqlSessionTemplate.insert(getInsertSql(entity), entity);
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
        sqlSessionTemplate.update(getUpdateOneSql(entity), entity);
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
        sqlSessionTemplate.delete(getDeleteOneSql(clazz), id);
    }

    @Override
    public <T extends Entity> void delete(Class<T> clazz, String[] ids) {
        if(null == ids || ids.length == 0) return;

        if(ids.length > 50) throw new RuntimeException("仅支持不超过50(包括50)条数据的批处理。");

        for(String id : ids){
            delete(clazz, id);
        }
    }

    protected String getMapperNamespace(Class clazz){
        MapperNamespace mapperNamespace = (MapperNamespace) clazz.getAnnotation(MapperNamespace.class);
        if(null != mapperNamespace){
            return mapperNamespace.value();
        }else{
            throw new NotFoundException("entity.mappernamespace.notfound", new Object[]{clazz.getName()});
        }
    }

    private String getSelectOneSql(Class clazz){
        return getMapperNamespace(clazz) + SELECT_ONE_STATEMENT_ID;
    }

    private String getSelectListSql(Class clazz){
        return getMapperNamespace(clazz) + SELECT_LIST_STATEMENT_ID;
    }

    private String getSelectPagingSql(Class clazz){
        return getMapperNamespace(clazz) + SELECT_PAGING_STATEMENT_ID;
    }

    private String getInsertSql(Entity object){
        return object.getMapperNamespace() + INSERT_STATEMENT_ID;
    }

    private String getUpdateOneSql(Entity object){
        return object.getMapperNamespace() + UPDATE_ONE_STATEMENT_ID;
    }

    private String getDeleteOneSql(Class clazz){
        return getMapperNamespace(clazz) + DELETE_ONE_STATEMENT_ID;
    }

    private String getOtherSql(Class clazz, String statementShortName){
        if(statementShortName.startsWith("\\.")){
            return getMapperNamespace(clazz) + statementShortName;
        }else{
            return getMapperNamespace(clazz) + '.' + statementShortName;
        }

    }

    private String getSelectListByIdsSql(Class clazz){
        return getMapperNamespace(clazz) + SELECT_LIST_BY_IDS_STATEMENT_ID;
    }
}
