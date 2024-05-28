package com.suisung.shopsuite.common.components.poster;

import com.suisung.shopsuite.common.components.poster.annotation.PosterBackground;
import com.suisung.shopsuite.common.components.poster.annotation.PosterFontCss;
import com.suisung.shopsuite.common.components.poster.annotation.PosterImageCss;
import com.suisung.shopsuite.common.components.poster.core.abst.AbstractDefaultPoster;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.awt.image.BufferedImage;

/**
 * 推广海报
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PromotePoster extends AbstractDefaultPoster {

    /**
     * 背景图
     */
    @PosterBackground(width = 750, height = 1335)
    private BufferedImage backgroundImage;

    /**
     * 产品图片
     */
    @PosterImageCss(position = {-25, 0}, width = 800, height = 800)
    private BufferedImage productImage;

    /**
     * 平台Logo图片
     */
    @PosterImageCss(position = {300, 50}, width = 150, height = 150)
    private BufferedImage plantformImage;

    /**
     * 二维码图片
     */
    @PosterImageCss(position = {40, 980}, width = 300, height = 300)
    private BufferedImage qrcodeImage;

    /**
     * 产品名称
     */
    @PosterFontCss(position = {40, 820}, size = 35, color = {3, 3, 3}, canNewLine = {1, 675, 1})
    private String productName;

    /**
     * 产品价格
     */
    @PosterFontCss(position = {40, 900}, size = 35, color = {255, 0, 0})
    private String productPrice;

    @Tolerate
    public PromotePoster() {
    }
}
