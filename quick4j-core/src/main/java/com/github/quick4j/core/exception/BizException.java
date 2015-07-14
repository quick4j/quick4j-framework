package com.github.quick4j.core.exception;

/**
 * @author zhaojh
 */
public class BizException extends BaseException{
    private String code;
    private Object[] args;

    public BizException(String message) {
        super(message);
        this.code = message;
    }

    public BizException(String message, Object[] args){
        super(message);
        this.code = message;
        this.args = args;
    }

    public BizException(String message, Object[] args, Throwable cause) {
        super(message, cause);
        this.code = message;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}
