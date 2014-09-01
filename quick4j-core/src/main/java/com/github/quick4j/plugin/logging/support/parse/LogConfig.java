package com.github.quick4j.plugin.logging.support.parse;

import com.github.quick4j.plugin.logging.StorageMedium;

/**
 * @author zhaojh
 */
public class LogConfig {
    private boolean isAudit = false;
    private StorageMedium storageMedium;
    private String auditContent;
    private Object[] args;

    public LogConfig(){}

    public LogConfig(boolean isAudit, StorageMedium storageMedium,
                     String auditContent, Object[] args) {
        this.isAudit = isAudit;
        this.storageMedium = storageMedium;
        this.auditContent = auditContent;
        this.args = args;
    }

    public boolean isAudit() {
        return isAudit;
    }

    public StorageMedium getStorageMedium() {
        return storageMedium;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public Object[] getArgs() {
        return args;
    }
}
