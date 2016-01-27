package com.github.quick4j.core.mybatis.mapping;

import com.github.quick4j.core.exception.BizException;

/**
 * @author zhaojh.
 */
public class BuildSqlException extends BizException {

  public BuildSqlException(String message) {
    super(message);
  }

  public BuildSqlException(String message, Object[] args) {
    super(message, args);
  }

  public BuildSqlException(String message, Object[] args, Throwable cause) {
    super(message, args, cause);
  }
}
