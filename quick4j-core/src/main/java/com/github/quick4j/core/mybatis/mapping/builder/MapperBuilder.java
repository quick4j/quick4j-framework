package com.github.quick4j.core.mybatis.mapping.builder;

import com.github.quick4j.core.mybatis.mapping.mapper.BaseMapper;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

/**
 * @author zhaojh.
 */
public class MapperBuilder {

  private ClassPool pool;

  public MapperBuilder() {
    pool = ClassPool.getDefault();
    pool.insertClassPath(new ClassClassPath(BaseMapper.class));
  }

  public Class build(String className, Class superInterface) {
    Class mapper = null;
    try {
      CtClass superclass = pool.get(superInterface.getName());
      CtClass ctMapper = pool.makeInterface(className, superclass);
      mapper = ctMapper.toClass();
    } catch (Exception e) {
      String message = String.format("创建[%s]失败。原因:%s", className, e.getMessage());
      throw new RuntimeException(message, e);
    }
    return mapper;
  }
}
