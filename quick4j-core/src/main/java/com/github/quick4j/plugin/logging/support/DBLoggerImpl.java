package com.github.quick4j.plugin.logging.support;

import com.github.quick4j.core.repository.mybatis.Repository;
import com.github.quick4j.core.util.UUIDGenerator;
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
    private Repository mybatisRepository;

    @Override
    public void writeLog(Logging log) {
        log.setId(UUIDGenerator.generate32RandomUUID());
        mybatisRepository.insert(log);
    }
}
