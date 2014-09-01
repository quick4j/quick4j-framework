package com.github.quick4j.plugin.logging.support;

import com.github.quick4j.core.repository.MyBatisCrudRepository;
import com.github.quick4j.core.util.UUIDGenerator;
import com.github.quick4j.plugin.logging.LogRecorder;
import com.github.quick4j.plugin.logging.entity.OperationLog;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhaojh
 */
@Component("logDBRecorder")
public class DBRecorder implements LogRecorder{
    @Resource
    private MyBatisCrudRepository<OperationLog, Map<String, Object>> myBatisCrudRepository;

    @Override
    public void writeLog(OperationLog log) {
        log.setId(UUIDGenerator.generate32RandomUUID());
        myBatisCrudRepository.insert(log);
    }
}
