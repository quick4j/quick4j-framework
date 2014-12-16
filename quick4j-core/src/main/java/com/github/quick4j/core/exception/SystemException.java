package com.github.quick4j.core.exception;

/**
 * @author zhaojh
 */
public class SystemException extends BaseException{
    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
