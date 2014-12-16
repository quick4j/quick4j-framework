package com.github.quick4j.plugin.logging.exception;

import com.github.quick4j.core.exception.BizException;

/**
 * @author zhaojh.
 */
public class NotFoundLogException extends BizException {
    public NotFoundLogException(String message) {
        super(message);
    }

    public NotFoundLogException(String message, Object[] args) {
        super(message, args);
    }

    public NotFoundLogException(String message, Object[] args, Throwable cause) {
        super(message, args, cause);
    }
}
