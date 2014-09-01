package com.github.quick4j.plugin.logging;

import com.github.quick4j.plugin.logging.entity.OperationLog;

/**
 * @author zhaojh
 */
public interface LogRecorder {
    void writeLog(OperationLog log);
}
