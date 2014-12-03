package com.github.quick4j.plugin.logging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@Table(name = "sys_operation_log")
public class OperationLog extends AbstractEntity{
    @Id
    private String id;
    @Column(name = "user_id")
    private String userid;
    @Column(name = "user_name")
    private String username;
    @Column(name = "create_time")
    private long createTime;
    @Column(name = "content")
    private String content;
    @Column(name = "method_args")
    private String data;
    private String masterId;

    public OperationLog(String userid, String username, long createTime, String content, String data) {
        this.userid = userid;
        this.username = username;
        this.createTime = createTime;
        this.content = content;
        this.data = data;
    }

    @Override
    @JsonIgnore
    public String getMetaData() {
        return "操作日志";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public List<? extends Entity> getSlave() {
        return null;
    }

    @Override
    public void setMasterId(String id) {
        this.masterId = id;
    }

    @Override
    public String getMasterId() {
        return masterId;
    }
}
