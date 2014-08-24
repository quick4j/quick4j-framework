package com.github.quick4j.core.repository.support;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.exception.NotFoundException;
import com.github.quick4j.core.exception.SystemException;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;
import com.github.quick4j.core.mybatis.interceptor.PaginationInterceptor;
import com.github.quick4j.core.mybatis.interceptor.model.DataPaging;
import com.github.quick4j.core.mybatis.interceptor.model.Pageable;
import com.github.quick4j.core.repository.MyBatisCrudRepository;
import com.github.quick4j.core.util.JsonUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaojh
 */
@Repository
public class MyBatisCrudRepositoryImpl<T extends Entity, P> implements MyBatisCrudRepository<T, P> {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisCrudRepositoryImpl.class);

    public static final String SELECT_ONE_STATEMENT_ID = ".selectOne";
    public static final String SELECT_LIST_STATEMENT_ID = ".selectList";
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
    public T findOne(Class<T> clazz, String id) {
        return sqlSessionTemplate.selectOne(getSelectOneSql(clazz), id);
    }

    @Override
    public List<T> findAll(Class<T> clazz) {
        return sqlSessionTemplate.selectList(getSelectListSql(clazz));
    }

    @Override
    public List<T> findAll(Class<T> clazz, P parameters) {
        return sqlSessionTemplate.selectList(getSelectListSql(clazz), parameters);
    }

    @Override
    public DataPaging<T> findAll(Class<T> clazz, Pageable pageable) {
        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getLimit());
        List<T> rows = sqlSessionTemplate.selectList(getSelectPagingSql(clazz), pageable.getParameters(), rowBounds);
        int total = PaginationInterceptor.getPaginationTotal();
        PaginationInterceptor.clean();
        DataPaging<T> dataPaging = new DataPaging<T>(rows, total);
        return dataPaging;
    }

    @Override
    public void insert(T entity) {
        sqlSessionTemplate.insert(getInsertSql(entity), entity);
    }

    @Override
    public void insert(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        String mapperNamespace = getInsertSql(entities.get(0));
        for(Entity entity : entities){
            sqlSessionTemplate.insert(mapperNamespace, entity);
        }
    }

    @Override
    public void update(T entity) {
        sqlSessionTemplate.update(getUpdateOneSql(entity), entity);
    }

    @Override
    public void update(List<T> entities) {
        if(null == entities || entities.isEmpty()) return;

        String mapperNamespace = getUpdateOneSql(entities.get(0));
        for (Entity entity : entities){
            sqlSessionTemplate.update(mapperNamespace, entity);
        }
    }

    @Override
    public void delete(Class<T> clazz, String id) {
        sqlSessionTemplate.delete(getDeleteOneSql(clazz), id);
    }

    @Override
    public void delete(Class<T> clazz, String[] ids) {
        if(null == ids || ids.length == 0) return;

        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            String mapperNamespace = getDeleteOneSql(clazz);
            for(String id : ids){
                sqlSessionTemplate.delete(mapperNamespace, id);
            }

            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            throw new SystemException("delete data failure", e);
        }finally {
            sqlSession.close();
        }
    }

    protected String getMapperNamespace(Class<T> clazz){
        MapperNamespace mapperNamespace = clazz.getAnnotation(MapperNamespace.class);
        if(null != mapperNamespace){
//            logger.info("mapperNamespace: {}", mapperNamespace);
            return mapperNamespace.value();
        }else{
            throw new NotFoundException("entity.mappernamespace.notfound", new Object[]{clazz.getName()});
        }
    }

    private String getSelectOneSql(Class<T> clazz){
        return getMapperNamespace(clazz) + SELECT_ONE_STATEMENT_ID;
    }

    private String getSelectListSql(Class<T> clazz){
        return getMapperNamespace(clazz) + SELECT_LIST_STATEMENT_ID;
    }

    private String getSelectPagingSql(Class<T> clazz){
        return getMapperNamespace(clazz) + SELECT_PAGING_STATEMENT_ID;
    }

    private String getInsertSql(T entity){
        return entity.getMapperNamespace() + INSERT_STATEMENT_ID;
    }

    private String getUpdateOneSql(T entity){
        return entity.getMapperNamespace() + UPDATE_ONE_STATEMENT_ID;
    }

    private String getDeleteOneSql(Class<T> clazz){
        return getMapperNamespace(clazz) + DELETE_ONE_STATEMENT_ID;
    }

}
