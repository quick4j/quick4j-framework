package com.github.quick4j.plugin.logging;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.plugin.logging.entity.Logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojh.
 */
public class LogConfig {
    public enum LogType{ENTITY, OTHER}

    private boolean isWritten;
    private String content;
    private Object[] datas;
    private List<Logging> loggings;
    private long createTime;
    private LogType logType;

    public LogConfig() {}

    public LogConfig(boolean isWritten, String content, Object[] datas, LogType logType) {
        this.isWritten = isWritten;
        this.content = content;
        this.datas = datas;
        this.createTime = new Date().getTime();
        this.logType = logType;
        buildLogging();
    }

    public boolean isWritten() {
        return isWritten;
    }

    public List<Logging> getLoggings() {
        return loggings;
    }

    private void buildLogging(){
        if (!this.isWritten) return;

        switch (logType){
            case ENTITY:
                buildEntityProcessLog();
                break;
            default:
                buildMethodInvokeLog();
        }
    }

    private void buildEntityProcessLog(){
        loggings = new ArrayList<Logging>();
        for (Object object : datas){
            Logging logging;
            if(object instanceof Entity){
                Entity entity = (Entity) object;
                logging = new Logging(
                        "123456",
                        "guest",
                        createTime,
                        String.format(content, entity.getMetaData()),
                        JsonUtils.toJson(entity)
                );

            }else{
                logging = new Logging(
                        "123456",
                        "guest",
                        createTime,
                        content,
                        JsonUtils.toJson(object)
                );
            }
            loggings.add(logging);
        }
    }

    private void buildMethodInvokeLog(){
        loggings = new ArrayList<Logging>();
        loggings.add(new Logging(
                "123456",
                "guest",
                createTime,
                content,
                JsonUtils.toJson(datas)
        ));
    }
}
