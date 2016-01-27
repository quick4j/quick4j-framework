package com.github.quick4j.plugin.logging.builder;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.plugin.logging.entity.DefaultLogging;
import com.github.quick4j.plugin.logging.entity.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class EntityLogBuilder extends AbstractLogBuilder {

  private List<Logging> loggings;

  public EntityLogBuilder(String content, Object[] extraData) {
    super(content, extraData);
  }

  @Override
  public List<Logging> getLoggings() {
    return loggings;
  }

  @Override
  protected void buildLogging() {
    loggings = new ArrayList<Logging>();
    Object[] extraData = getExtraData();
    for (Object object : extraData) {
      BaseEntity entity = (BaseEntity) object;
      loggings.add(new DefaultLogging(
          "123456",
          "guest",
          getCreateTime(),
          String.format(getContent(), entity.getMetaData()),
          JsonUtils.toJson(entity)
      ));
    }
  }
}
