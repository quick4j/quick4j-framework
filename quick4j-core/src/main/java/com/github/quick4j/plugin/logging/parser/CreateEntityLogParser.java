package com.github.quick4j.plugin.logging.parser;

import com.github.quick4j.plugin.logging.builder.EntityLogBuilder;
import com.github.quick4j.plugin.logging.LogBuilder;
import com.github.quick4j.plugin.logging.exception.NotFoundLogException;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;

/**
 * @author zhaojh.
 */
public class CreateEntityLogParser extends AbstractEntityLogParser{
    private static final String CREATE_ENTITY_LOG_CONTENT_TEMPLATE = "新建[%s]";

    public CreateEntityLogParser(Object[] methodArgs) {
        super(methodArgs);
    }

    @Override
    public LogBuilder parse() {
        Object[] methodArgs = getMethodArgs();

        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Create Entity.");

        if(!isWrittenLog()){
            throw new NotFoundLogException("");
        }

        Object[] entities = getEntities();
        return new EntityLogBuilder(CREATE_ENTITY_LOG_CONTENT_TEMPLATE, entities);
    }
}
