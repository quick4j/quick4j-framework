package com.github.quick4j.plugin.logging.annontation;


import com.github.quick4j.plugin.logging.builder.AbstractLogBuilder;
import com.github.quick4j.plugin.logging.builder.DefaultMethodLogBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhaojh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WriteLog {
    String value();
    String data() default "";
    Class<? extends AbstractLogBuilder> builder() default DefaultMethodLogBuilder.class;
}
