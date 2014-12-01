package com.github.quick4j.core.mybatis.mapping.builder;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class MappedStatementAssistant {
    private MappedStatementAssistant(){}

    public static boolean hasStatementInSqlSession(String statementName, SqlSession sqlSession){
        return sqlSession.getConfiguration().hasStatement(statementName, false);
    }

    public static MappedStatement getMappedStatement(String statementName, SqlSession sqlSession){
        return sqlSession.getConfiguration().getMappedStatement(statementName);
    }

    public static MappedStatement newSelectMappedStatement(final MappedStatement ms, Class entityClass){
        String statementId = ms.getId() + "-InLine";
        Configuration configuration = ms.getConfiguration();

        if(configuration.hasStatement(statementId, false)){
            return configuration.getMappedStatement(statementId);
        }

        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(
                configuration,
                statementId,
                ms.getSqlSource(),
                SqlCommandType.SELECT
        );

        statementBuilder.resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .timeout(ms.getTimeout())
                .resultSetType(ms.getResultSetType())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache())
                .parameterMap(ms.getParameterMap());

        String[] keyProperties = ms.getKeyProperties();
        statementBuilder.keyProperty(keyProperties == null ? null : keyProperties[0]);


        //构建resultMap
        ResultMap resultMap = newResultMap(entityClass, configuration);
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(resultMap);
        statementBuilder.resultMaps(resultMaps);
        MappedStatement statement = statementBuilder.build();
        if(!configuration.hasStatement(statement.getId(), false)){
            configuration.addMappedStatement(statement);
        }

        return statement;
    }

    private static ResultMap newResultMap(Class reslutMapType, Configuration configuration){
        String resultMapId = String.format("%s-Inline", reslutMapType.getSimpleName());
        if(configuration.hasResultMap(resultMapId)){
            return configuration.getResultMap(resultMapId);
        }

        List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(reslutMapType);
        List<EntityPersistentInfo.MappedColumn> mappedColumns = entityPersistentInfo.getMappedColumns();
        for (EntityPersistentInfo.MappedColumn mappedColumn : mappedColumns){
            resultMappings.add(
                    new ResultMapping.Builder(
                            configuration,
                            mappedColumn.getProperty(),
                            mappedColumn.getName(),
                            mappedColumn.getJavaType()
                    ).build()
            );
        }

        ResultMap.Builder resultMapBuilder = new ResultMap.Builder(
                configuration,
                resultMapId,
                reslutMapType,
                resultMappings
        );

        return resultMapBuilder.build();
    }
}
