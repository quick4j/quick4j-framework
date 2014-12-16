package com.github.quick4j.logging;

import com.github.quick4j.plugin.logging.builder.AbstractLogBuilder;
import com.github.quick4j.plugin.logging.entity.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojh.
 */
public class CustomLogBuilder extends AbstractLogBuilder {
    private List<Logging> loggings;

    public CustomLogBuilder(String content, Object[] extraData) {
        super(content, extraData);
    }

    @Override
    protected void buildLogging() {
        loggings = new ArrayList<Logging>();
        System.out.println("=====> say~~~~~~~ Goodbye~~~~~~~~~~~~");
    }

    @Override
    public List<Logging> getLoggings() {
        return loggings;
    }
}
