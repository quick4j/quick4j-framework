package com.github.quick4j.plugin.logging.annontation;

import com.github.quick4j.plugin.logging.builder.AbstractLogBuilder;
import com.github.quick4j.plugin.logging.entity.Logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhaojh.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Builder {
    Class<? extends AbstractLogBuilder> type();
    Class<? extends Logging> build();
}
