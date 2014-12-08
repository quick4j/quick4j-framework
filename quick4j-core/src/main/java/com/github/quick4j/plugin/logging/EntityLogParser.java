package com.github.quick4j.plugin.logging;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;

import java.util.List;

/**
 * @author zhaojh.
 */
public class EntityLogParser implements LogParser{
    private static final String CREATE_ENTITY_LOG_CONTENT_TEMPLATE = "新建[%s]";
    private static final String UPDATE_ENTITY_LOG_CONTENT_TEMPLATE = "编辑[%s]";
    private static final String DELETE_ENTITY_LOG_CONTENT_TEMPLATE = "删除[%s]";
    private HandleType handleType;
    private Object[] methodArgs;

    public EntityLogParser(HandleType handleType, Object[] methodArgs) {
        this.handleType = handleType;
        this.methodArgs = methodArgs;
    }

    public static enum HandleType{
        CREATE_ENTITY, UPDATE_ENTITY, DELETE_ENTITY
    }

    @Override
    public LogConfig parse() {
        switch (handleType){
            case CREATE_ENTITY:
                return buildCreateLog();
            case UPDATE_ENTITY:
                return buildUpdateLog();
            case DELETE_ENTITY:
                return buildDeleteLog();
        }
        return new LogConfig();
    }

    private LogConfig buildCreateLog(){
        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Create Entity.");

        boolean isWritten;
        Entity entity = null;
        Object[] entities;
        Object operatingData = methodArgs[0];

        if(null != operatingData && operatingData instanceof List){
            List list = (List) operatingData;
            if(!list.isEmpty()){
                entity = (Entity)list.get(0);
            }
            entities = list.toArray();
        }else{
            entity = (Entity) operatingData;
            entities = new Object[]{entity};
        }

        if(null == entity) throw new ParseLogFailureException("When Create Entity.");

        Auditable auditable = entity.getClass().getAnnotation(Auditable.class);
        isWritten = auditable != null;
        return new LogConfig(isWritten, CREATE_ENTITY_LOG_CONTENT_TEMPLATE, entities, LogConfig.LogType.ENTITY);
    }

    private LogConfig buildUpdateLog(){
        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Update Entity.");

        boolean isWritten;
        Entity entity = null;
        Object[] entities;
        Object operatingData = methodArgs[0];

        if(null != operatingData && operatingData instanceof List){
            List list = (List) operatingData;
            if(!list.isEmpty()){
                entity = (Entity)list.get(0);
            }
            entities = list.toArray();
        }else{
            entity = (Entity) operatingData;
            entities = new Object[]{entity};
        }

        if(null == entity) throw new ParseLogFailureException("When Update Entity.");

        Auditable auditable = entity.getClass().getAnnotation(Auditable.class);
        isWritten = auditable != null;
        return new LogConfig(isWritten, UPDATE_ENTITY_LOG_CONTENT_TEMPLATE, entities, LogConfig.LogType.ENTITY);
    }

    private LogConfig buildDeleteLog(){
        if(null == methodArgs || methodArgs.length == 0)
            throw new ParseLogFailureException("When Delete Entity.");

        boolean isWritten;
        Object operatingData = methodArgs[0];
        Entity entity = (Entity) operatingData;
        Object[] entities = methodArgs;

        Auditable auditable = entity.getClass().getAnnotation(Auditable.class);
        isWritten = auditable != null;
        return new LogConfig(isWritten, DELETE_ENTITY_LOG_CONTENT_TEMPLATE, entities, LogConfig.LogType.ENTITY);
    }
}
