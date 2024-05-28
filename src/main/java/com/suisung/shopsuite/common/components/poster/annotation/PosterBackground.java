package com.suisung.shopsuite.common.components.poster.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author quaint
 * 30 March 2020
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PosterBackground {

    /**
     * 0 取原图大小
     */
    int width() default 0;

    int height() default 0;

}
