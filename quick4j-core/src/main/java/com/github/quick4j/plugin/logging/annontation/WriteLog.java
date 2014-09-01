package com.github.quick4j.plugin.logging.annontation;

import com.github.quick4j.plugin.logging.StorageMedium;

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
    public String value() default "";
    public StorageMedium to() default StorageMedium.DB;
    public String data() default "";
}
