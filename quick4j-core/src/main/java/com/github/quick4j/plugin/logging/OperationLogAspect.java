package com.github.quick4j.plugin.logging;

import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.plugin.logging.annontation.WriteLog;
import com.github.quick4j.plugin.logging.entity.OperationLog;
import com.github.quick4j.plugin.logging.support.OperationType;
import com.github.quick4j.plugin.logging.support.parse.LogConfig;
import com.github.quick4j.plugin.logging.support.parse.EntityLogParser;
import com.github.quick4j.plugin.logging.support.parse.MethodLogParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhaojh
 */
@Aspect
@Component
public class OperationLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    private EntityLogParser entityLogParser = new EntityLogParser();
    private MethodLogParser methodLogParser = new MethodLogParser();

    @Resource
    private LogRecorder logDBRecorder;

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.MyBatisRepository.insert(*))&&args(entity)")
    public Object doRecordEntityCreate(ProceedingJoinPoint pjp, Object entity) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();
            writeLog(entityLogParser.parse(entity, OperationType.NEW));
            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.MyBatisRepository.update(*))&&args(entity)")
    public Object doRecordEntityModify(ProceedingJoinPoint pjp, Object entity) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();
            writeLog(entityLogParser.parse(entity, OperationType.UPDATE));
            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around(value = "execution(* com.github.quick4j.core.repository.mybatis.MyBatisRepository.delete(Class, *))&&args(clazz, id)")
    public Object doRecordEntityDelete(ProceedingJoinPoint pjp, Class clazz, Object id) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();
            writeLog(entityLogParser.parse(clazz, OperationType.DELETE, id));
            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    @Around("@annotation(writeLog)")
    public Object doRecordOtherOperation(ProceedingJoinPoint pjp, WriteLog writeLog) throws Throwable {
        try {
            Object rtnValue = pjp.proceed();
            writeLog(methodLogParser.parse(writeLog, pjp.getArgs()));
            return rtnValue;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    private void writeLog(LogConfig logConfig){
        if(!logConfig.isAudit()) return;
//        System.out.println("日志内容：" + logConfig.getAuditContent());
//        System.out.println("操作数据：" + JsonUtils.toJson(logConfig.getArgs()));
//        System.out.println("存储介质：" + logConfig.getStorageMedium());

        logger.info("日志内容：{}", logConfig.getAuditContent());
        logger.info("操作数据：{}", JsonUtils.toJson(logConfig.getArgs()));
        logger.info("存储介质：{}", logConfig.getStorageMedium());

        OperationLog log = new OperationLog("123456", "guset", new Date().getTime(), logConfig.getAuditContent(), JsonUtils.toJson(logConfig.getArgs()));

        logDBRecorder.writeLog(log);
    }
}
