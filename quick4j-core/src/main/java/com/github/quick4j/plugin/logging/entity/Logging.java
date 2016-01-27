package com.github.quick4j.plugin.logging.entity;

import com.github.quick4j.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import java.util.List;

/**
 * @author zhaojh
 */
@MappedSuperclass
public class Logging extends BaseEntity {

  @Id
  @Column(name = "id", length = 32)
  private String id;
  @Column(name = "user_id", length = 32)
  private String userid;
  @Column(name = "user_name", length = 200)
  private String username;
  @Column(name = "create_time")
  private long createTime;
  @Column(name = "content", length = 2000)
  private String content;

  public Logging(String userid, String username, long createTime, String content) {
    this.userid = userid;
    this.username = username;
    this.createTime = createTime;
    this.content = content;
  }

  @Override
  public String getChineseName() {
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
  public List<? extends BaseEntity> getSlave() {
    return null;
  }

  @Override
  public String getName() {
    return "";
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
