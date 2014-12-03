package com.github.quick4j.plugin.logging.support;

import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.util.UUIDGenerator;
import com.github.quick4j.plugin.logging.LogRecorder;
import com.github.quick4j.plugin.logging.entity.OperationLog;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaojh
 */
@Component("logDBRecorder")
public class DBRecorder implements LogRecorder{
    @Resource
    private Repository mybatisRepository;

    @Override
    public void writeLog(OperationLog log) {
        log.setId(UUIDGenerator.generate32RandomUUID());
        mybatisRepository.insert(log);
    }
}
