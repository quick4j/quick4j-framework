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
@Target(ElementType.TYPE)
public @interface Auditable {
    String value() default "";
    public StorageMedium to() default StorageMedium.DB;
}
