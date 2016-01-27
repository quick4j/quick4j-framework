package com.github.quick4j.plugin.datagrid;

import com.github.quick4j.core.exception.BizException;

/**
 * @author zhaojh
 */
public class DataSetProcessException extends BizException {

  public DataSetProcessException(String message) {
    super(message);
  }

  public DataSetProcessException(String message, Object[] args) {
    super(message, args);
  }

  public DataSetProcessException(String message, Object[] args, Throwable cause) {
    super(message, args, cause);
  }
}
