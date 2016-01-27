package com.github.quick4j.core.mybatis.mapping.builder;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojh.
 */
public class MapperAssistant {

  private Map<String, Class> knownMappers = new HashMap<String, Class>();
  private SqlSession mybatisSqlSession;
  private MapperBuilder mapperBuilder;
  private Configuration configuration;

  public MapperAssistant(SqlSession mybatisSqlSession) {
    this.mybatisSqlSession = mybatisSqlSession;
    this.configuration = this.mybatisSqlSession.getConfiguration();
    mapperBuilder = new MapperBuilder();
  }

  public Class buildAndRegistMapper(String mapperName, Class superInterface) {
    synchronized (this) {
      if (hasMapper(mapperName)) {
        return getMapper(mapperName);
      }

      Class mapperClass = mapperBuilder.build(mapperName, superInterface);
      registMapper(mapperClass);
      return mapperClass;
    }
  }

  public Class getMapper(String mapperName) {
    return knownMappers.get(mapperName);
  }

  public boolean hasMapper(String mapperName) {
    return knownMappers.containsKey(mapperName)
           || hasMapperInSqlSession(mapperName);
  }

  private void registMapper(Class mapperClass) {
    String mapperName = mapperClass.getName();
    if (!knownMappers.containsKey(mapperName)) {
      knownMappers.put(mapperName, mapperClass);
    }

    if (!hasMapperInSqlSession(mapperName)
        && !hasCommonStatementInSqlSessionFor(mapperName)) {
      configuration.addMapper(mapperClass);
    }
  }

  private boolean hasMapperInSqlSession(String mapperName) {
    Collection<Class<?>> collection = configuration.getMapperRegistry().getMappers();
    for (Class clazz : collection) {
      if (clazz.getName().equals(mapperName)
          && null != configuration.getMapper(clazz, mybatisSqlSession)) {
        knownMappers.put(clazz.getName(), clazz);
        return true;
      }
    }
    return false;
  }

  private boolean hasStatementInSqlSession(String statementName) {
    return configuration.hasStatement(statementName, false);
  }

  private boolean hasCommonStatementInSqlSessionFor(String mapperName) {
    return hasStatementInSqlSession(String.format("%s.%s", mapperName, SqlBuilder.SELECT_BY_ID))
           || hasStatementInSqlSession(String.format("%s.%s", mapperName, SqlBuilder.SELECT_BY_IDS))
           || hasStatementInSqlSession(String.format("%s.%s", mapperName, SqlBuilder.INSERT))
           || hasStatementInSqlSession(String.format("%s.%s", mapperName, SqlBuilder.UPDATE_BY_ID))
           || hasStatementInSqlSession(String.format("%s.%s", mapperName, SqlBuilder.DELETE_BY_ID))
           || hasStatementInSqlSession(
        String.format("%s.%s", mapperName, SqlBuilder.DELETE_BY_IDS));
  }
}
