package com.github.quick4j.core.entity;

import java.util.List;

/**
 * @author zhaojh
 */
public interface Entity{
    void setId(String id);
    String getId();
    void setMasterId(String id);
    String getMasterId();
    String getMetaData();
    List<? extends Entity> getSlave();
    boolean isNew();
    String getChineseName();
    String getName();
}
