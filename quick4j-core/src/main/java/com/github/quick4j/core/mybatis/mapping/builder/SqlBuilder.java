package com.github.quick4j.core.mybatis.mapping.builder;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.support.Order;
import com.github.quick4j.core.repository.mybatis.support.Sort;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojh.
 */
public class SqlBuilder {
    private static Logger logger = LoggerFactory.getLogger(SqlBuilder.class);

    public static final String SELECT_BY_ID = "selectById";
    public static final String SELECT_BY_IDS = "selectByIds";
    public static final String SELECT_LIST = "selectList";
    public static final String SELECT_PAGING = "selectPaging";
    public static final String INSERT = "insert";
    public static final String UPDATE_BY_ID = "updateById";
    public static final String DELETE_BY_ID = "deleteById";
    public static final String DELETE_BY_IDS = "deleteByIds";
    public static final String DELETE_BY_PARAMETERS = "deleteByParameters";

    public static final String BUILD_SELECT_BY_ID_SQL = "buildSelectByIdSql";
    public static final String BUILD_SELECT_BY_IDS_SQL = "buildSelectByIdsSql";
    public static final String BUILD_SELECT_LIST_SQL = "buildSelectListSql";
    public static final String BUILD_INSERT_SQL = "buildInsertSql";
    public static final String BUILD_UPDATE_BY_ID_SQL = "buildUpdateByIdSql";
    public static final String BUILD_DELETE_BY_ID_SQL = "buildDeleteByIdSql";
    public static final String BUILD_DELETE_BY_IDS_SQL = "buildDeleteByIdsSql";
    public static final String BUILD_DELETE_BY_PARAMETERS = "buildDeleteByParameters";

    public String buildSelectByIdSql(Map<String, Object> parameter){
        Class entityClass = (Class) parameter.get("type");
        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entityClass);
        final String tableName = entityPersistentInfo.getTableName();
        final String[] columnNames = entityPersistentInfo.getColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            SELECT(convertQueriedColumns(columnNames));
            FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();

        return sql;
    }

    public String buildSelectByIdsSql(Map<String, Object> parameter){
        Class entityClass = (Class) parameter.get("type");
        final String[] ids = (String[]) parameter.get("ids");
        final Sort sort = (Sort) parameter.get("sort");

        final EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entityClass);
        final String tableName = entityPersistentInfo.getTableName();
        final String[] columnNames = entityPersistentInfo.getColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            SELECT(convertQueriedColumns(columnNames));
            FROM(tableName);

            StringBuilder where = new StringBuilder();
            for(int i=0, length = ids.length; i<length; i++){
                where.append(String.format("id = #{ids[%d]}", i));
                if(i < length - 1){
                    where.append(" or ");
                }
            }

            String whereString = where.toString();
            if(whereString.length() > 0){
                WHERE(where.toString());
            }else{
                WHERE("id=null");
            }

            if(null != sort){
                List<Order> orders = sort.getOrders();
                for (Order order : orders){
                    String column = entityPersistentInfo.getColumnByProperty(order.getProperty());
                    if(null != column){
                        ORDER_BY(column + " " + order.getDirection());
                    }
                }
            }
        }}.toString();
        return sql;
    }

    public String buildSelectListSql(Map<String, Object> parameter){
        Class entityClass = (Class) parameter.get("type");
        final Object queriedParams = parameter.get("parameters");
        final Sort sort = (Sort) parameter.get("sort");
        final EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entityClass);
        final String tableName = entityPersistentInfo.getTableName();
        final String[] columnNames = entityPersistentInfo.getColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            SELECT(convertQueriedColumns(columnNames));
            FROM(tableName);

            if(null != queriedParams){
                List<EntityPersistentInfo.MappedColumn> mappedColumns = entityPersistentInfo.getMappedColumns();
                for (EntityPersistentInfo.MappedColumn mappedColumn : mappedColumns){
                    String column = mappedColumn.getName();
                    String key = mappedColumn.getProperty();

                    if(queriedParams instanceof Map){
                        Object value = ((Map)queriedParams).get(key);
                        if(null != value){
                            String variable = String.format("#{parameters[%s]}", mappedColumn.getProperty());
                            WHERE(String.format("%s = %s", column, variable));
                        }
                    }else{
                        if(null != mappedColumn.getValue(queriedParams)){
                            String variable = String.format("#{parameters.%s}", mappedColumn.getProperty());
                            WHERE(String.format("%s = %s", column, variable));
                        }
                    }
                }
            }

            if(null != sort){
                List<Order> orders = sort.getOrders();
                for (Order order : orders){
                    String column = entityPersistentInfo.getColumnByProperty(order.getProperty());
                    if(null != column){
                        ORDER_BY(column + " " + order.getDirection());
                    }
                }
            }
        }}.toString();
        return sql;
    }

    public String buildInsertSql(final Entity entity){
        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entity.getClass());
        final String tableName = entityPersistentInfo.getTableName();
        final List<EntityPersistentInfo.MappedColumn> mappedColumns = entityPersistentInfo.getMappedColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entity.getClass().getName()
            );
            throw new RuntimeException(message);
        }

        if (mappedColumns.isEmpty()){
            String message = String.format(
                    "为[%s]构建Insert语句失败。原因：缺少待插入的列，请检查该类中有关Column的定义。",
                    entity.getClass().getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            INSERT_INTO(tableName);
            VALUES("id", "#{id}");
            for (EntityPersistentInfo.MappedColumn mappedColumn : mappedColumns){
                if(null != mappedColumn.getValue(entity)){
                    String column = mappedColumn.getName();
                    String variable = String.format("#{%s}", mappedColumn.getProperty());
                    VALUES(column, variable);
                }
            }
        }}.toString();

        return sql;
    }

    public String buildUpdateByIdSql(final Entity entity){
        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entity.getClass());
        final String tableName = entityPersistentInfo.getTableName();
        final List<EntityPersistentInfo.MappedColumn> mappedColumns = entityPersistentInfo.getMappedColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entity.getClass().getName()
            );
            throw new RuntimeException(message);
        }

        if (mappedColumns.isEmpty()){
            String message = String.format(
                    "为[%s]构建Update语句失败。原因：缺少待更新的列，请检查该类中有关Column的定义。",
                    entity.getClass().getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            UPDATE(tableName);
            for (EntityPersistentInfo.MappedColumn mappedColumn : mappedColumns){
                if(null != mappedColumn.getValue(entity)){
                    String column = mappedColumn.getName();
                    String variable = String.format("#{%s}", mappedColumn.getProperty());
                    SET(String.format("%s=%s", column, variable));
                }
            }
            WHERE("id = #{id}");
        }}.toString();

        return sql;
    }

    public String buildDeleteByIdSql(Map<String, Object> parameter){
        Class entityClass = (Class) parameter.get("type");
        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entityClass);
        final String tableName = entityPersistentInfo.getTableName();
        final String[] columnNames = entityPersistentInfo.getColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
        return sql;
    }

    public String buildDeleteByIdsSql(Map<String, Object> parameter){
        Class entityClass = (Class) parameter.get("type");
        final String[] ids = (String[]) parameter.get("ids");

        EntityPersistentInfo entityPersistentInfo = EntityAssistant.parse(entityClass);
        final String tableName = entityPersistentInfo.getTableName();
        final String[] columnNames = entityPersistentInfo.getColumns();

        if(!StringUtils.hasText(tableName)){
            String message = String.format(
                    "找不到[%s]在数据库中对应的表名，请检查该类中有关Table的定义。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        if(null == ids || ids.length == 0){
            String message = String.format(
                    "不建议使用[%s.deleteByIds]执行清表操作，请单独使用不带条件的delete from 语句。如果不是请检查调用参数。",
                    entityClass.getName()
            );
            throw new RuntimeException(message);
        }

        String sql = new SQL(){{
            DELETE_FROM(tableName);

            StringBuilder where = new StringBuilder();
            for(int i=0, length = ids.length; i<length; i++){
                where.append(String.format("id = #{ids[%d]}", i));
                if(i < length - 1){
                    where.append(" or ");
                }
            }
            WHERE(where.toString());
        }}.toString();
        return sql;
    }

    private String convertQueriedColumns(String[] columnNames){
        final StringBuilder queriedColumns = new StringBuilder();
        queriedColumns.append("id");
        if(columnNames.length > 0){
            queriedColumns.append(',').append(StringUtils.arrayToDelimitedString(columnNames, ","));
        }
        return queriedColumns.toString();
    }
}
