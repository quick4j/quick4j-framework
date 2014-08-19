package com.github.quick4j.core.exception;

/**
 * @author zhaojh
 */
public class NotFoundException extends BizException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object[] args) {
        super(message, args);
    }

    public NotFoundException(String message, Object[] args, Throwable cause) {
        super(message, args, cause);
    }
}
