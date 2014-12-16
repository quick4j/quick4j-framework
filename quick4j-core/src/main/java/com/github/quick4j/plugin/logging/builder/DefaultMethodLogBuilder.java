package com.github.quick4j.plugin.logging.builder;

import com.github.quick4j.core.util.JsonUtils;
import com.github.quick4j.plugin.logging.LogBuilder;
import com.github.quick4j.plugin.logging.entity.DefaultLogging;
import com.github.quick4j.plugin.logging.entity.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class DefaultMethodLogBuilder extends AbstractLogBuilder {
    private List<Logging> loggings;

    public DefaultMethodLogBuilder(String content, Object[] extraData) {
        super(content, extraData);
    }

    @Override
    public List<Logging> getLoggings() {
        return loggings;
    }

    @Override
    protected void buildLogging() {
        loggings = new ArrayList<Logging>();
        loggings.add(new DefaultLogging(
                "123456",
                "guest",
                getCreateTime(),
                getContent(),
                JsonUtils.toJson(getExtraData())
        ));
    }
}
