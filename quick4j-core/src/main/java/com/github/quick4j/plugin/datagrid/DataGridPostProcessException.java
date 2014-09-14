package com.github.quick4j.plugin.datagrid;

import com.github.quick4j.core.exception.BizException;

/**
 * @author zhaojh
 */
public class DataGridPostProcessException extends BizException{
    public DataGridPostProcessException(String message) {
        super(message);
    }

    public DataGridPostProcessException(String message, Object[] args) {
        super(message, args);
    }

    public DataGridPostProcessException(String message, Object[] args, Throwable cause) {
        super(message, args, cause);
    }
}
