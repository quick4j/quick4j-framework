package com.github.quick4j.plugin.logging.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.quick4j.core.entity.AbstractEntity;
import com.github.quick4j.core.entity.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import java.util.List;

/**
 * @author zhaojh
 */
@Inheritance
public class Logging extends AbstractEntity{
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

    private String masterId;

    public Logging(String userid, String username, long createTime, String content) {
        this.userid = userid;
        this.username = username;
        this.createTime = createTime;
        this.content = content;
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
        return id;
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
}
