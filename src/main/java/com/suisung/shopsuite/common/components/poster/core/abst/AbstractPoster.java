package com.suisung.shopsuite.common.components.poster.core.abst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 海报抽象类(部分常用属性)
 * 完全自定义属性, 可以直接 继承 空白 AbstractDefaultPoster
 * 或实现Poster 接口
 *
 * @author quaint
 * 21 February 2020
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPoster implements Poster {


    /**
     * 背景图
     */
    protected BufferedImage backgroundImage;

    /**
     * logo
     */
    protected BufferedImage logo;

    /**
     * 广告语
     */
    protected String slogan;

    /**
     * 主图
     */
    protected BufferedImage mainImage;

    /**
     * 二维码
     */
    protected BufferedImage qrcode;


}
