package com.github.quick4j.plugin.logging.support.parse;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.plugin.logging.annontation.Auditable;
import com.github.quick4j.plugin.logging.support.OperationType;

import java.util.List;

/**
 * @author zhaojh
 */
public class EntityLogParser {

    public LogConfig parse(final Object object, OperationType type){
        LogConfig logConfig = parseAuditInfo(object);
        String content = getLogContent(type, logConfig.getAuditContent());
        logConfig.setAuditContent(content);
        return logConfig;
    }

    private String getLogContent(OperationType type, final String value){
        String content;
        switch (type){
            case NEW:
                content = "创建[%s]";
                break;
            case UPDATE:
                content = "编辑[%s]";
                break;
            case DELETE:
                content = "删除[%s]";
                break;
            default:
                content = "未知操作";
        }
        return String.format(content, value);
    }

    private LogConfig parseAuditInfo(Object object){
        if(object instanceof List){
            List list = (List)object;
            if(!list.isEmpty() && (list.get(0) instanceof Entity)){
                Entity entity = (Entity)list.get(0);
                return buidEntityInfo(entity);
            }
        }else{
            if(object instanceof Entity){
                Entity entity = (Entity)object;
                return buidEntityInfo(entity);
            }
        }

        return new LogConfig();
    }

    private LogConfig buidEntityInfo(Entity entity){
        Auditable auditable = entity.getClass().getAnnotation(Auditable.class);
        boolean isAudit = auditable != null;
        String metaData = entity.getMetaData();

        if(isAudit){
            LogConfig logConfig = new LogConfig(isAudit, auditable.to(), metaData, new Object[]{entity});
            return logConfig;
        }else{
            return new LogConfig();
        }
    }
}