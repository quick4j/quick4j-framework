package com.github.quick4j.plugin.logging.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author zhaojh.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "sys_operation_log")
public class DefaultLogging extends Logging {

  @Column(name = "method_args", length = 2000)
  private String extraData;

  public DefaultLogging(String userid, String username, long createTime, String content,
                        String extraData) {
    super(userid, username, createTime, content);
    this.extraData = extraData;
  }

  public String getExtraData() {
    return extraData;
  }
}
