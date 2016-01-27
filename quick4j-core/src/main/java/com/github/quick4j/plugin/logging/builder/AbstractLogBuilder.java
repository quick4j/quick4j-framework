package com.github.quick4j.plugin.logging.builder;

import com.github.quick4j.plugin.logging.LogBuilder;

/**
 * @author zhaojh.
 */
public abstract class AbstractLogBuilder implements LogBuilder {

  private String content;
  private Object[] extraData;
  private long createTime;

  protected AbstractLogBuilder(String content, Object[] extraData) {
    this.content = content;
    this.extraData = extraData;
    this.createTime = System.currentTimeMillis();
    buildLogging();
  }

  protected abstract void buildLogging();

  protected String getContent() {
    return content;
  }

  protected Object[] getExtraData() {
    return extraData;
  }

  protected long getCreateTime() {
    return createTime;
  }
}
