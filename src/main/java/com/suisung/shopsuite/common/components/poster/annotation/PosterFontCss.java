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
public @interface PosterFontCss {


    /**
     * positionX,positionY
     */
    int[] position() default {0, 0};

    /**
     * 字体大小
     */
    int size() default 18;

    /**
     * 颜色 默认黑色
     */
    int[] color() default {0, 0, 0};

    /**
     * 是否可以换行
     * 第一个参数 0 false 1 true
     * 第二个参数 换行的限制宽度, 如果小于字体宽度 则不会换行
     * 第三个参数 换行次数
     */
    int[] canNewLine() default {0, 0, 1};

    /**
     * 优先级  设置该属性时 position x 失效 (暂未实现)
     * center right  left
     */
    boolean center() default false;

    /**
     * 设置center 一定要设置背景宽度用于计算
     *
     * @return
     */
    int posterWidth() default 0;

    boolean right() default false;


}
