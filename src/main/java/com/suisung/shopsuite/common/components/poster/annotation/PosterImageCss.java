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
public @interface PosterImageCss {


    /**
     * positionX,positionY
     */
    int[] position() default {0, 0};

    /**
     * 0 为原图, 或者原字体
     */
    int width() default 0;

    int height() default 0;

    /**
     * 默认不改变为圆形(限正方形图使用)
     */
    boolean circle() default false;


}
