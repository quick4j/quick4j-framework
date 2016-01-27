package com.github.quick4j.core.exception;

/**
 * @author zhaojh
 */
public class ParameterException extends BizException {

  public ParameterException(String message) {
    super(message);
  }

  public ParameterException(String message, Object[] args) {
    super(message, args);
  }

  public ParameterException(String message, Object[] args, Throwable cause) {
    super(message, args, cause);
  }
}
