package com.github.quick4j.plugin.logging;

import com.github.quick4j.core.entity.Entity;
import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.plugin.logging.annontation.WriteLog;
import com.github.quick4j.plugin.logging.entity.Logging;
import com.github.quick4j.plugin.logging.exception.ParseLogFailureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh
 */
@Aspect
@Component
public class LogAspect {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private Repository repository;
    @Resource
    private Logger dbLogger;

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.Repository.insert(*))")
    public Object doRecordEntityCreate(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();

            try{
                LogParser logParser = new EntityLogParser(EntityLogParser.HandleType.CREATE_ENTITY, pjp.getArgs());
                writeLog(logParser);
            }catch (ParseLogFailureException e){
                logger.error(e.getMessage());
            }

            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.Repository.updateById(*))")
    public Object doRecordEntityModify(ProceedingJoinPoint pjp) throws Throwable {
        try {

            Object rtnValue = pjp.proceed();

            try{
                LogParser logParser = new EntityLogParser(EntityLogParser.HandleType.UPDATE_ENTITY, pjp.getArgs());
                writeLog(logParser);
            }catch (ParseLogFailureException e){
                logger.error(e.getMessage());
            }

            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.Repository.delete(Class, *))")
    public Object doRecordEntityDelete(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] entities = findDeletedEntity(pjp.getArgs());

            Object rtnValue = pjp.proceed();

            try{
                LogParser logParser = new EntityLogParser(EntityLogParser.HandleType.DELETE_ENTITY, entities);
                writeLog(logParser);
            }catch (ParseLogFailureException e){
                logger.error(e.getMessage());
            }

            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around("@annotation(writeLog)")
    public Object doRecordOtherOperation(ProceedingJoinPoint pjp, WriteLog writeLog) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();

            try{
                LogParser logParser = new MethodLogParser(writeLog, pjp.getArgs());
                writeLog(logParser);
            }catch (ParseLogFailureException e){
                logger.error(e.getMessage());
            }

            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void writeLog(LogParser logParser){
        LogConfig logConfig = logParser.parse();
        if(!logConfig.isWritten()) return;

        List<Logging> loggings = logConfig.getLoggings();
        for(Logging logging : loggings){
            logger.info("===> 日志内容：{}", logging.getContent());
            logger.info("===> 操作数据：{}", logging.getData());
        }
    }

    private Object[] findDeletedEntity(Object[] args){
        Class entityClass = (Class) args[0];
        Object params = args[1];
        String[] ids ;

        if(params instanceof String){
            ids = new String[]{(String) params};
        }else{
            ids = (String[]) params;
        }

        return repository.findByIds(entityClass, ids).toArray();
    }
}
