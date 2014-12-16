package com.github.quick4j.plugin.logging.parser;

import com.github.quick4j.plugin.logging.builder.EntityLogBuilder;
import com.github.quick4j.plugin.logging.LogBuilder;
import com.github.quick4j.plugin.logging.exception.NotFoundLogException;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;

/**
 * @author zhaojh.
 */
public class DeleteEntityLogParser extends AbstractEntityLogParser{
    private static final String DELETE_ENTITY_LOG_CONTENT_TEMPLATE = "删除[%s]";

    public DeleteEntityLogParser(Object[] methodArgs) {
        super(methodArgs);
    }

    @Override
    public LogBuilder parse() {
        Object[] methodArgs = getMethodArgs();

        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Delete Entity.");

        if(!isWrittenLog()){
            throw new NotFoundLogException("");
        }

        Object[] entities = getEntities();
        return new EntityLogBuilder(DELETE_ENTITY_LOG_CONTENT_TEMPLATE, entities);
    }
}
