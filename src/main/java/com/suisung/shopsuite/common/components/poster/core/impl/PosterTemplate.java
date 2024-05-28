package com.suisung.shopsuite.common.components.poster.core.impl;

import com.suisung.shopsuite.common.components.poster.core.abst.Poster;

/**
 * @author quaint
 * 30 March 2020
 * @since 1.0
 */
public interface PosterTemplate<E> {

    /**
     * 基于注解的绘制
     *
     * @param content content 类
     * @return 注解绘制
     * @throws IllegalAccessException ex
     */
    Poster annotationDrawPoster(E content) throws IllegalAccessException;

}
