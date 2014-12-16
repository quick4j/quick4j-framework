package com.github.quick4j.core.mybatis.paging.dialect.support;

import com.github.quick4j.core.mybatis.paging.dialect.Dialect;

/**
 * @author zhaojh
 */
public abstract class DialectFactory {
    public static Dialect buildDialect(Dialect.Type dialectType){
        switch (dialectType){
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
                return new OracleDialect();
            default:
                throw new UnsupportedOperationException();
        }
    }
}
