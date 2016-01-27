package com.github.quick4j.core.mybatis.mapping.builder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class EntityPersistentInfo {

  private static final Logger logger = LoggerFactory.getLogger(EntityPersistentInfo.class);
  private String tableName;
  private Class entityClass;
  private List<MappedColumn> mappedColumns = new ArrayList<MappedColumn>();
  private String[] columns = null;

  EntityPersistentInfo(Class entityClass) {
    this.entityClass = entityClass;
    parse();
  }

  public class MappedColumn {

    private String name;
    private String property;
    private Class javaType;
    private Field field;

    public MappedColumn(String name, Field field) {
      this.name = name;
      this.property = field.getName();
      this.javaType = field.getType();
      this.field = field;
    }

    public String getName() {
      return name;
    }

    public String getProperty() {
      return property;
    }

    public Class getJavaType() {
      return javaType;
    }

    public Object getValue(Object entity) {
      Object value = null;
      try {
        field.setAccessible(true);
        value = field.get(entity);
      } catch (Exception e) {
        e.printStackTrace();
      }

      return value;
    }

    @Override
    public String toString() {
      return "{" +
             "name='" + name + '\'' +
             ", property='" + property + '\'' +
             '}';
    }
  }

  public String getTableName() {
    if (null != tableName) {
      return tableName;
    }

    if (!entityClass.isAnnotationPresent(Table.class)) {
      throw new RuntimeException("没有找到对应的物理表");
    }

    Table table = (Table) entityClass.getAnnotation(Table.class);
    tableName = table.name();
    return tableName;
  }

  public String[] getColumns() {
    return columns;
  }

  public List<MappedColumn> getMappedColumns() {
    return mappedColumns;
  }

  public String getColumnByProperty(String property) {
    for (MappedColumn mappedColumn : mappedColumns) {
      if (mappedColumn.getProperty().equals(property)) {
        return mappedColumn.getName();
      }
    }
    return null;
  }

  private void parse() {
    List<String> columnNames = new ArrayList<String>();

    Field[] fields = getAllFields(entityClass).toArray(new Field[]{});
    for (Field field : fields) {
      if (field.isAnnotationPresent(Id.class) || !field.isAnnotationPresent(Column.class)) {
        continue;
      }

      Column column = field.getAnnotation(Column.class);
      if (StringUtils.isNotBlank(column.name())) {
        mappedColumns.add(new MappedColumn(column.name(), field));
        columnNames.add(column.name());
      }
    }

    columns = columnNames.toArray(new String[]{});
  }

  private List<Field> getAllFields(Class entityClass) {
    return getAllFields(entityClass, new ArrayList<Field>());
  }

  private List<Field> getAllFields(Class entityClass, List<Field> fieldList) {
    if (entityClass.equals(Object.class)) {
      return fieldList;
    }

    Field[] fields = entityClass.getDeclaredFields();
    for (Field field : fields) {
      if (!Modifier.isStatic(field.getModifiers())) {
        fieldList.add(field);
      }
    }

    if (entityClass.getSuperclass() != null
        && !entityClass.getSuperclass().equals(Object.class)) {
      return getAllFields(entityClass.getSuperclass(), fieldList);
    }

    return fieldList;
  }
}
