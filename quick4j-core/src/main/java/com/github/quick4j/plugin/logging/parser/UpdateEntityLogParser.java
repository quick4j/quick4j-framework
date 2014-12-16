package com.github.quick4j.plugin.logging.parser;

import com.github.quick4j.plugin.logging.builder.EntityLogBuilder;
import com.github.quick4j.plugin.logging.LogBuilder;
import com.github.quick4j.plugin.logging.exception.NotFoundLogException;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;

/**
 * @author zhaojh.
 */
public class UpdateEntityLogParser extends AbstractEntityLogParser{
    private static final String UPDATE_ENTITY_LOG_CONTENT_TEMPLATE = "编辑[%s]";

    public UpdateEntityLogParser(Object[] methodArgs) {
        super(methodArgs);
    }

    @Override
    public LogBuilder parse() {
        Object[] methodArgs = getMethodArgs();

        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Update Entity.");

        if (!isWrittenLog()){
            throw new NotFoundLogException("");
        }

        Object[] entities = getEntities();
        return new EntityLogBuilder(UPDATE_ENTITY_LOG_CONTENT_TEMPLATE, entities);
    }
}
