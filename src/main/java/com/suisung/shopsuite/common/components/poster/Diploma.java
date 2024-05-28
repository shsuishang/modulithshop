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
 * 毕业证书
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Diploma extends AbstractDefaultPoster {


    @PosterBackground(width = 690, height = 978)
    private BufferedImage backgroundImage;

    /**
     * icon
     */
    @PosterImageCss(position = {121, 720}, width = 120, height = 120)
    private BufferedImage icon;

    @PosterFontCss(center = true, posterWidth = 690, position = {0, 340}, size = 45, color = {16, 13, 14,})
    private String name;

    @PosterFontCss(center = true, posterWidth = 690, position = {0, 220}, size = 60, color = {59, 58, 76})
    private String text1;

    @PosterFontCss(center = true, posterWidth = 690, position = {0, 410}, size = 20, color = {0, 0, 0})
    private String text2;

    @PosterFontCss(center = true, posterWidth = 690, position = {0, 450}, size = 40, color = {0, 0, 0})
    private String text3;

    @PosterFontCss(position = {400, 730}, size = 25, color = {0, 0, 0})
    private String text4;

    @PosterFontCss(position = {390, 785}, size = 11, color = {0, 0, 0})
    private String text5;

    @PosterFontCss(position = {390, 805}, size = 11, color = {0, 0, 0})
    private String text6;

    @Tolerate
    public Diploma() {
    }
}
