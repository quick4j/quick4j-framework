package com.github.quick4j.plugin.logging.entity;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.mybatis.annotation.MapperNamespace;

/**
 * @author zhaojh
 */
@MapperNamespace("com.github.quick4j.plugin.logging.entity.OperationLogMapper")
public class OperationLog extends Entity{
    private String userid;
    private String username;
    private long createTime;
    private String content;
    private String data;

    public OperationLog(String userid, String username, long createTime, String content, String data) {
        this.userid = userid;
        this.username = username;
        this.createTime = createTime;
        this.content = content;
        this.data = data;
    }

    @Override
    public String getMetaData() {
        return "操作日志";
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public String getData() {
        return data;
    }
}
