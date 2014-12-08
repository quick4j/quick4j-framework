package com.github.quick4j.plugin.logging.exception;

import com.github.quick4j.core.exception.BizException;

/**
 * @author zhaojh.
 */
public class ParseLogFailureException extends BizException {

    public ParseLogFailureException(String message) {
        super(message);
    }

    public ParseLogFailureException(String message, Object[] args) {
        super(message, args);
    }

    public ParseLogFailureException(String message, Object[] args, Throwable cause) {
        super(message, args, cause);
    }
}
