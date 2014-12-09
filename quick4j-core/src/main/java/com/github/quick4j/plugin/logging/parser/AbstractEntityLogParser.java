package com.github.quick4j.plugin.logging.parser;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.LogParser;
import com.github.quick4j.plugin.logging.annontation.Auditable;

import java.util.List;

/**
 * @author zhaojh.
 */
public abstract class AbstractEntityLogParser implements LogParser{
    private Object[] methodArgs;

    protected AbstractEntityLogParser(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }

    protected Object[] getMethodArgs() {
        return methodArgs;
    }

    protected boolean isWrittenLog(){
        Entity entity = null;
        Object operatingData = methodArgs[0];

        if(operatingData == null) return false;

        if(operatingData instanceof List){
            List list = (List) operatingData;
            if(list.isEmpty()){
                return false;
            }else{
                entity = (Entity) list.get(0);
            }
        }

        if(operatingData instanceof Entity){
            entity = (Entity) operatingData;
        }

        if(entity == null) return false;

        Auditable auditable = entity.getClass().getAnnotation(Auditable.class);
        return auditable != null;
    }

    protected Object[] getEntities(){
        Object[] entities;
        Object operatingData = methodArgs[0];

        if(operatingData instanceof List){
            List list = (List) operatingData;
            entities = list.toArray();
        }else{
            entities = new Object[]{operatingData};
        }

        return entities;
    }
}
