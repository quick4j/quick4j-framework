package com.github.quick4j.plugin.logging;

import com.github.quick4j.core.entity.BaseEntity;
import com.github.quick4j.core.repository.mybatis.MybatisRepository;
import com.github.quick4j.plugin.logging.annontation.WriteLog;
import com.github.quick4j.plugin.logging.entity.DefaultLogging;
import com.github.quick4j.plugin.logging.entity.Logging;
import com.github.quick4j.plugin.logging.exception.NotFoundLogException;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;
import com.github.quick4j.plugin.logging.parser.CreateEntityLogParser;
import com.github.quick4j.plugin.logging.parser.MethodLogParser;
import com.github.quick4j.plugin.logging.parser.DeleteEntityLogParser;
import com.github.quick4j.plugin.logging.parser.UpdateEntityLogParser;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * @author zhaojh
 */
@Aspect
@Component
public class LogAspect {

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);

  @Resource
  private MybatisRepository mybatisRepository;
  @Resource
  private Logger dbLogger;

  @Around(
      value = "execution(* com.github.quick4j.core.repository.mybatis.MybatisRepository.insert(*))")
  public Object doRecordEntityCreate(ProceedingJoinPoint pjp) throws Throwable {
    try {
      Object rtnValue = pjp.proceed();

      LogParser logParser = new CreateEntityLogParser(pjp.getArgs());
      writeLog(logParser);

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  @Around(
      value = "execution(* com.github.quick4j.core.repository.mybatis.MybatisRepository.updateById(*))")
  public Object doRecordEntityModify(ProceedingJoinPoint pjp) throws Throwable {
    try {

      Object rtnValue = pjp.proceed();

      LogParser logParser = new UpdateEntityLogParser(pjp.getArgs());
      writeLog(logParser);

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  @Around(
      value = "execution(* com.github.quick4j.core.repository.mybatis.MybatisRepository.deleteById(Class, *))")
  public Object doRecordEntityDeleteById(ProceedingJoinPoint pjp) throws Throwable {
    try {
      List<BaseEntity> entities = findDeletedEntity(pjp.getArgs());

      Object rtnValue = pjp.proceed();

      LogParser logParser = new DeleteEntityLogParser(new Object[]{entities});
      writeLog(logParser);

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  @Around(
      value = "execution(* com.github.quick4j.core.repository.mybatis.MybatisRepository.deleteByIds(Class, *))")
  public Object doRecordEntityDeleteByIds(ProceedingJoinPoint pjp) throws Throwable {
    try {
      List<BaseEntity> entities = findDeletedEntity(pjp.getArgs());

      Object rtnValue = pjp.proceed();

      LogParser logParser = new DeleteEntityLogParser(new Object[]{entities});
      writeLog(logParser);

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  @Around(
      value = "execution(* com.github.quick4j.core.repository.mybatis.MybatisRepository.deleteByParams(com.github.quick4j.core.entity.BaseEntity))&&args(entity)")
  public Object doRecordEntityDelete(ProceedingJoinPoint pjp, BaseEntity entity) throws Throwable {
    try {
      List<BaseEntity> entities = findDeletedEntity(entity);

      Object rtnValue = pjp.proceed();

      LogParser logParser = new DeleteEntityLogParser(new Object[]{entities});
      writeLog(logParser);

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  @Around("@annotation(writeLog)")
  public Object doRecordOtherOperation(ProceedingJoinPoint pjp, WriteLog writeLog)
      throws Throwable {
    try {
      Object rtnValue = pjp.proceed();

      try {
        LogParser logParser = new MethodLogParser(writeLog, pjp.getArgs());
        writeLog(logParser);
      } catch (ParseLogFailureException e) {
        logger.error(e.getMessage());
      }

      return rtnValue;
    } catch (Throwable throwable) {
      throw throwable;
    }
  }

  private void writeLog(LogParser logParser) {
    try {
      LogBuilder logBuilder = logParser.parse();
      List<Logging> loggings = logBuilder.getLoggings();
      if (null == loggings) {
        return;
      }

      for (Logging logging : loggings) {
        logger.info("===> 操作时间：{}", new Date(logging.getCreateTime()));
        logger.info("===> 日志内容：{}", logging.getContent());
        if (logging instanceof DefaultLogging) {
          logger.info("===> 操作数据：{}", ((DefaultLogging) logging).getExtraData());
        }

        dbLogger.writeLog(logging);
      }

    } catch (ParseLogFailureException e) {
      // do nothing
      e.printStackTrace();
    } catch (NotFoundLogException e) {
    }
  }

  private List<BaseEntity> findDeletedEntity(Object[] args) {
    Class entityClass = (Class) args[0];
    Object params = args[1];
    String[] ids;

    if (params instanceof String) {
      ids = new String[]{(String) params};
    } else {
      ids = (String[]) params;
    }

    return mybatisRepository.selectByIds(entityClass, ids);
  }

  private List<BaseEntity> findDeletedEntity(BaseEntity entity) {
    return (List<BaseEntity>) mybatisRepository.selectList(entity.getClass(), entity);
  }
}
