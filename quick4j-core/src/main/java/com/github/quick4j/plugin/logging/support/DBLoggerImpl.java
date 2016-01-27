package com.github.quick4j.plugin.logging.support;

import com.github.quick4j.core.repository.mybatis.MybatisRepository;
import com.github.quick4j.core.util.UUID;
import com.github.quick4j.plugin.logging.Logger;
import com.github.quick4j.plugin.logging.entity.Logging;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
@Component("dbLogger")
public class DBLoggerImpl implements Logger {

  @Resource
  private MybatisRepository mybatisRepository;

  @Override
  public void writeLog(Logging log) {
    log.setId(UUID.getUUID32());
    mybatisRepository.insert(log);
  }
}
